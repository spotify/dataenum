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
package com.spotify.dataenum.processor.generator.match;

import com.spotify.dataenum.processor.parser.ParserException;
import com.spotify.dataenum.processor.util.Iterables;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;
import java.util.ArrayList;
import java.util.List;

class TypeVariableUtils {
  static TypeName withoutMissingTypeVariables(
      TypeName typeName, Iterable<TypeVariableName> availableTypeVariables) throws ParserException {
    if (!(typeName instanceof ParameterizedTypeName) || availableTypeVariables == null) {
      return typeName;
    }

    ParameterizedTypeName parameterizedTypeName = (ParameterizedTypeName) typeName;

    List<TypeName> adjustedArguments = new ArrayList<>();
    for (TypeName argument : parameterizedTypeName.typeArguments) {
      if (argument instanceof ParameterizedTypeName) {
        // Recursive call
        adjustedArguments.add(withoutMissingTypeVariables(argument, availableTypeVariables));
      } else if (argument instanceof TypeVariableName) {
        TypeVariableName variable = (TypeVariableName) argument;
        if (Iterables.contains(availableTypeVariables, variable)) {
          adjustedArguments.add(variable);
        } else {
          if (variable.bounds.size() == 0) {
            adjustedArguments.add(TypeName.OBJECT);
          } else if (variable.bounds.size() == 1) {
            adjustedArguments.add(variable.bounds.get(0));
          } else {
            throw new ParserException("More than one generic type bound is not supported");
          }
        }
      } else {
        adjustedArguments.add(argument);
      }
    }

    TypeName[] adjustedArgumentsArr = adjustedArguments.toArray(new TypeName[] {});
    return ParameterizedTypeName.get(parameterizedTypeName.rawType, adjustedArgumentsArr);
  }
}
