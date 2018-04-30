/*
 * -\-\-
 * DataEnum
 * --
 * Copyright (c) 2017 Spotify AB
 * --
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -/-/-
 */
package com.spotify.dataenum.processor.generator.value;

import static com.spotify.dataenum.processor.util.Iterables.fromOptional;

import com.spotify.dataenum.processor.data.OutputSpec;
import com.spotify.dataenum.processor.data.OutputValue;
import com.spotify.dataenum.processor.data.Parameter;
import com.spotify.dataenum.processor.generator.match.MapMethods;
import com.spotify.dataenum.processor.generator.match.MatchMethods;
import com.spotify.dataenum.processor.parser.ParserException;
import com.spotify.dataenum.processor.util.Iterables;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import com.squareup.javapoet.WildcardTypeName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Modifier;

public class ValueTypeFactory {

  static final AnnotationSpec SUPPRESS_UNCHECKED_WARNINGS =
      AnnotationSpec.builder(SuppressWarnings.class).addMember("value", "$S", "unchecked").build();

  private ValueTypeFactory() {}

  public static TypeSpec create(
      OutputValue value,
      OutputSpec spec,
      MatchMethods matchMethods,
      MapMethods mapMethods,
      Optional<Modifier> constructorAccessModifier)
      throws ParserException {

    TypeSpec.Builder typeBuilder =
        TypeSpec.classBuilder(value.outputClass())
            .addTypeVariables(value.typeVariables())
            .superclass(getSuperclassForValue(value, spec))
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);

    typeBuilder.addMethod(createConstructor(value, constructorAccessModifier));
    typeBuilder.addFields(createFields(value));
    typeBuilder.addMethods(createGetters(value));
    typeBuilder.addMethod(createEquals(value));

    typeBuilder.addMethod(createHashCode(value));

    typeBuilder.addMethod(createToString(value));
    typeBuilder.addMethod(matchMethods.createFoldVoidMethod(value));
    typeBuilder.addMethod(mapMethods.createFoldMethod(value));

    if (spec.hasTypeVariables()) {
      typeBuilder.addMethod(createAsSpecMethod(value, spec));
    }

    return typeBuilder.build();
  }

  private static TypeName getSuperclassForValue(OutputValue value, OutputSpec spec)
      throws ParserException {
    if (!spec.hasTypeVariables()) {
      return spec.outputClass();
    }

    List<TypeName> superParameters = new ArrayList<>();
    for (TypeVariableName typeVariable : spec.typeVariables()) {
      if (Iterables.contains(value.typeVariables(), typeVariable)) {
        superParameters.add(typeVariable);
      } else {
        if (typeVariable.bounds.size() == 0) {
          superParameters.add(TypeName.OBJECT);
        } else if (typeVariable.bounds.size() == 1) {
          superParameters.add(typeVariable.bounds.get(0));
        } else {
          throw new ParserException("More than one generic type bound is not supported ");
        }
      }
    }

    return ParameterizedTypeName.get(
        spec.outputClass(), superParameters.toArray(new TypeName[] {}));
  }

  private static MethodSpec createConstructor(
      OutputValue value, Optional<Modifier> constructorAccessModifier) {
    MethodSpec.Builder constructor =
        MethodSpec.constructorBuilder().addModifiers(fromOptional(constructorAccessModifier));
    for (Parameter parameter : value.parameters()) {
      constructor.addParameter(parameter.type(), parameter.name());

      if (parameter.type().isPrimitive() || parameter.canBeNull()) {
        constructor.addStatement("this.$1L = $1L", parameter.name());
      } else {
        constructor.addStatement("this.$1L = checkNotNull($1L)", parameter.name());
      }
    }
    return constructor.build();
  }

  private static Iterable<FieldSpec> createFields(OutputValue value) {
    List<FieldSpec> fields = new ArrayList<>();
    for (Parameter parameter : value.parameters()) {
      fields.add(createField(parameter));
    }
    return fields;
  }

  private static Iterable<MethodSpec> createGetters(OutputValue value) {
    List<MethodSpec> getters = new ArrayList<>();
    for (Parameter parameter : value.parameters()) {
      getters.add(createGetter(parameter));
    }
    return getters;
  }

  private static FieldSpec createField(Parameter parameter) {
    return FieldSpec.builder(parameter.type(), parameter.name())
        .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
        .build();
  }

  private static MethodSpec createGetter(Parameter parameter) {
    MethodSpec.Builder builder =
        MethodSpec.methodBuilder(parameter.name())
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .returns(parameter.type())
            .addStatement("return $L", parameter.name());

    if (parameter.canBeNull()) {
      builder.addAnnotation(Nullable.class);
    } else if (!parameter.type().isPrimitive()) {
      builder.addAnnotation(Nonnull.class);
    }

    return builder.build();
  }

  private static MethodSpec createEquals(OutputValue value) throws ParserException {
    MethodSpec.Builder result =
        MethodSpec.methodBuilder("equals")
            .addAnnotation(Override.class)
            .addModifiers(Modifier.PUBLIC)
            .returns(boolean.class)
            .addParameter(Object.class, "other");

    if (!value.parameters().iterator().hasNext()) {
      result.addStatement("return other instanceof $T", value.outputClass());
      return result.build();
    }

    result.addStatement("if (other == this) return true");
    result.addStatement("if (!(other instanceof $T)) return false", value.outputClass());

    TypeName wildCardTypeName = withWildCardTypeParameters(value);
    result.addStatement("$1T o = ($1T) other", wildCardTypeName);
    result.addCode("$[return ");
    boolean first = true;
    for (Parameter parameter : value.parameters()) {
      if (first) {
        first = false;
      } else {
        result.addCode("\n&& ");
      }

      String fieldName = parameter.name();
      if (parameter.type().isPrimitive()) {
        result.addCode("o.$1L == $1L", fieldName);
      } else {
        if (parameter.canBeNull()) {
          result.addCode("equal(o.$1L, this.$1L)", fieldName);
        } else {
          result.addCode("o.$1L.equals(this.$1L)", fieldName);
        }
      }
    }
    result.addCode(";\n$]");

    return result.build();
  }

  private static TypeName withWildCardTypeParameters(OutputValue value) {
    if (!value.hasTypeVariables()) {
      return value.outputClass();
    }

    TypeName[] wildCards = new TypeName[Iterables.sizeOf(value.typeVariables())];

    Arrays.fill(wildCards, WildcardTypeName.subtypeOf(TypeName.OBJECT));

    return ParameterizedTypeName.get(value.outputClass(), wildCards);
  }

  private static MethodSpec createHashCode(OutputValue value) {
    MethodSpec.Builder result =
        MethodSpec.methodBuilder("hashCode")
            .addAnnotation(Override.class)
            .addModifiers(Modifier.PUBLIC)
            .returns(int.class);

    if (!value.hasParameters()) {
      result.addStatement("return 0");
      return result.build();
    }

    result.addStatement("int result = 0");
    for (Parameter parameter : value.parameters()) {
      String fieldName = parameter.name();
      result.addCode("result = result * 31 + ");
      if (parameter.type().isPrimitive()) {
        TypeName boxedType = parameter.type().box();
        result.addStatement("$T.valueOf($L).hashCode()", boxedType, fieldName);
      } else {
        if (parameter.canBeNull()) {
          result.addStatement("($1L != null ? $1L.hashCode() : 0)", fieldName);
        } else {
          result.addStatement("$L.hashCode()", fieldName);
        }
      }
    }
    result.addStatement("return result");
    return result.build();
  }

  private static MethodSpec createToString(OutputValue value) {
    MethodSpec.Builder result =
        MethodSpec.methodBuilder("toString")
            .addAnnotation(Override.class)
            .addModifiers(Modifier.PUBLIC)
            .returns(String.class);

    if (!value.parameters().iterator().hasNext()) {
      result.addStatement("return \"$L{}\"", value.name());
      return result.build();
    }

    result.addStatement("$1T builder = new $1T()", StringBuilder.class);

    boolean first = true;
    for (Parameter parameter : value.parameters()) {
      String fieldName = parameter.name();

      String valueFormat = parameter.redacted() ? "\"***\"" : "$1L";

      if (first) {
        first = false;
        result.addStatement(
            String.format("builder.append(\"$2L{$1N=\").append(%s)", valueFormat),
            fieldName,
            value.name());
      } else {
        result.addStatement(
            String.format("builder.append(\", $1N=\").append(%s)", valueFormat), fieldName);
      }
    }

    result.addStatement("return builder.append('}').toString()", value.name());

    return result.build();
  }

  private static MethodSpec createAsSpecMethod(OutputValue value, OutputSpec spec) {
    List<TypeVariableName> missingTypeVariables = extractMissingTypeVariablesForValue(value, spec);

    Builder builder =
        MethodSpec.methodBuilder("as" + spec.outputClass().simpleName())
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .returns(spec.parameterizedOutputClass())
            .addTypeVariables(missingTypeVariables)
            .addStatement("return ($T) this", spec.parameterizedOutputClass());

    // if there are type variables that this sub-type doesn't use, they will lead to 'unchecked
    // cast'
    // warnings when compiling the generated code. These warnings are safe to suppress, since this
    // sub type will never use those type variables.
    if (!missingTypeVariables.isEmpty()) {
      builder.addAnnotation(SUPPRESS_UNCHECKED_WARNINGS);
    }

    return builder.build();
  }

  static List<TypeVariableName> extractMissingTypeVariablesForValue(
      OutputValue value, OutputSpec spec) {
    List<TypeVariableName> missingTypeVariables = new ArrayList<>();
    for (TypeVariableName typeVariableName : spec.typeVariables()) {
      if (!Iterables.contains(value.typeVariables(), typeVariableName)) {
        missingTypeVariables.add(typeVariableName);
      }
    }
    return missingTypeVariables;
  }
}
