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
package com.spotify.dataenum.processor.generator.data;

import static com.spotify.dataenum.processor.generator.data.OutputSpecFactory.isSpecClassName;
import static com.spotify.dataenum.processor.generator.data.OutputSpecFactory.toOutputClassName;

import com.spotify.dataenum.processor.data.OutputValue;
import com.spotify.dataenum.processor.data.Parameter;
import com.spotify.dataenum.processor.data.Spec;
import com.spotify.dataenum.processor.data.Value;
import com.spotify.dataenum.processor.parser.ParserException;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

final class OutputValueFactory {

  private OutputValueFactory() {}

  static OutputValue create(Value value, ClassName specOutputClass, Spec spec)
      throws ParserException {
    ClassName outputClass = specOutputClass.nestedClass(value.name());
    Iterable<TypeVariableName> typeVariables = getTypeVariables(value, spec.typeVariables());

    List<Parameter> parameters = new ArrayList<>();
    for (Parameter parameter : value.parameters()) {
      parameters.add(parameterWithoutDataEnumSuffix(parameter));
    }

    return new OutputValue(
        outputClass, value.name(), value.javadoc(), parameters, typeVariables, value.annotations());
  }

  private static Parameter parameterWithoutDataEnumSuffix(Parameter parameter)
      throws ParserException {
    return new Parameter(
        parameter.name(),
        typeWithoutDataEnumSuffixes(parameter.type()),
        parameter.canBeNull(),
        parameter.redacted(),
        parameter.isEnum());
  }

  private static TypeName typeWithoutDataEnumSuffixes(TypeName typeName) throws ParserException {
    if (typeName instanceof ParameterizedTypeName) {
      ParameterizedTypeName paramTypeName = (ParameterizedTypeName) typeName;

      ClassName outputClassName;
      if (isSpecClassName(paramTypeName.rawType)) {
        outputClassName = toOutputClassName(paramTypeName.rawType);
      } else {
        outputClassName = paramTypeName.rawType;
      }

      TypeName[] typeArgumentsArr = new TypeName[paramTypeName.typeArguments.size()];
      for (int i = 0; i < typeArgumentsArr.length; i++) {
        typeArgumentsArr[i] = typeWithoutDataEnumSuffixes(paramTypeName.typeArguments.get(i));
      }

      return ParameterizedTypeName.get(outputClassName, typeArgumentsArr);
    }

    if (typeName instanceof ClassName) {
      ClassName className = (ClassName) typeName;
      if (isSpecClassName(className)) {
        return toOutputClassName(className);
      }
      return className;
    }

    return typeName;
  }

  private static Iterable<TypeVariableName> getTypeVariables(
      Value value, Iterable<TypeVariableName> typeVariables) {
    Set<TypeVariableName> output = new LinkedHashSet<>();
    for (TypeVariableName typeVariable : typeVariables) {
      for (Parameter parameter : value.parameters()) {
        if (typeNeedsTypeVariable(parameter.type(), typeVariable)) {
          output.add(typeVariable);
        }
      }
    }
    return output;
  }

  private static boolean typeNeedsTypeVariable(TypeName type, TypeVariableName typeVariable) {
    if (typeVariable.equals(type)) {
      return true;
    }

    if (type instanceof ParameterizedTypeName) {
      ParameterizedTypeName parameterized = ((ParameterizedTypeName) type);
      for (TypeName typeArgument : parameterized.typeArguments) {
        if (typeVariable.equals(typeArgument)) {
          return true;
        }
        if (typeNeedsTypeVariable(typeArgument, typeVariable)) {
          return true;
        }
      }
    }

    if (type instanceof ArrayTypeName) {
      ArrayTypeName arrayType = (ArrayTypeName) type;
      if (typeVariable.equals(arrayType.componentType)) {
        return true;
      }
    }
    return false;
  }
}
