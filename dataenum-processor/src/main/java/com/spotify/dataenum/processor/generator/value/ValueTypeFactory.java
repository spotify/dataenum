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

import com.google.auto.value.AutoValue;
import com.spotify.dataenum.processor.data.OutputSpec;
import com.spotify.dataenum.processor.data.OutputValue;
import com.spotify.dataenum.processor.data.Parameter;
import com.spotify.dataenum.processor.generator.match.MapMethods;
import com.spotify.dataenum.processor.generator.match.MatchMethods;
import com.spotify.dataenum.processor.parser.ParserException;
import com.spotify.dataenum.processor.util.Iterables;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Modifier;

public class ValueTypeFactory {

  private ValueTypeFactory() {}

  public static TypeSpec create(
      OutputValue value, OutputSpec spec, MatchMethods matchMethods, MapMethods mapMethods)
      throws ParserException {

    TypeSpec.Builder typeBuilder =
        TypeSpec.classBuilder(value.outputClass())
            .addTypeVariables(value.typeVariables())
            .superclass(getSuperclassForValue(value, spec))
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.ABSTRACT);

    typeBuilder.addMethod(createConstructor());
    typeBuilder.addAnnotation(AutoValue.class);
    typeBuilder.addMethod(createFactoryMethod(value));

    typeBuilder.addMethods(createGetters(value));

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

  private static MethodSpec createConstructor() {
    // Note: the constructor will have a default access of package-private which is intentional
    return MethodSpec.constructorBuilder().build();
  }

  private static MethodSpec createFactoryMethod(OutputValue value) {
    MethodSpec.Builder factoryMethod = MethodSpec.methodBuilder("create");

    factoryMethod.addModifiers(Modifier.PRIVATE, Modifier.STATIC);
    factoryMethod.returns(value.parameterizedOutputClass());

    for (TypeVariableName typeVariable : value.typeVariables()) {
      factoryMethod.addTypeVariable(typeVariable);
    }

    factoryMethod.addCode(
        "return new AutoValue_$N_$N(",
        value.outputClass().enclosingClassName().simpleName(),
        value.outputClass().simpleName());

    boolean needsComma = false;
    for (Parameter parameter : value.parameters()) {
      factoryMethod.addParameter(parameter.type(), parameter.name());

      if (needsComma) {
        factoryMethod.addCode(", ");
      }
      needsComma = true;

      factoryMethod.addCode("$N", parameter.name());
    }
    factoryMethod.addCode(");\n");

    return factoryMethod.build();
  }

  private static Iterable<MethodSpec> createGetters(OutputValue value) {
    List<MethodSpec> getters = new ArrayList<>();
    for (Parameter parameter : value.parameters()) {
      getters.add(createGetter(parameter));
    }
    return getters;
  }

  private static MethodSpec createGetter(Parameter parameter) {
    MethodSpec.Builder builder =
        MethodSpec.methodBuilder(parameter.name())
            .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
            .returns(parameter.type());

    // TODO(dflemstr): @AutoValue prohibits mutable getters so we need to suppress its lint.  Why do
    // we want to support this?
    if (parameter.type() instanceof ArrayTypeName) {
      builder.addAnnotation(
          AnnotationSpec.builder(SuppressWarnings.class).addMember("value", "\"mutable\"").build());
    }

    if (parameter.canBeNull()) {
      builder.addAnnotation(Nullable.class);
    } else if (!parameter.type().isPrimitive()) {
      builder.addAnnotation(Nonnull.class);
    }

    return builder.build();
  }

  private static MethodSpec createAsSpecMethod(OutputValue value, OutputSpec spec) {
    List<TypeVariableName> missingTypeVariables = new ArrayList<>();
    for (TypeVariableName typeVariableName : spec.typeVariables()) {
      if (!Iterables.contains(value.typeVariables(), typeVariableName)) {
        missingTypeVariables.add(typeVariableName);
      }
    }

    return MethodSpec.methodBuilder("as" + spec.outputClass().simpleName())
        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
        .returns(spec.parameterizedOutputClass())
        .addTypeVariables(missingTypeVariables)
        .addStatement("return ($T) this", spec.parameterizedOutputClass())
        .build();
  }
}
