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

import com.spotify.dataenum.function.Function;
import com.spotify.dataenum.processor.data.OutputValue;
import com.spotify.dataenum.processor.parser.ParserException;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;
import javax.annotation.Nonnull;
import javax.lang.model.element.Modifier;

public class MapMethods {

  private static final TypeVariableName FOLD_RETURN_TYPE = TypeVariableName.get("R_");
  private final Iterable<OutputValue> values;

  public MapMethods(Iterable<OutputValue> values) {
    this.values = values;
  }

  private MethodSpec.Builder createFoldSignature(Iterable<TypeVariableName> availableTypeVariables)
      throws ParserException {
    MethodSpec.Builder builder =
        MethodSpec.methodBuilder("map")
            .addTypeVariable(FOLD_RETURN_TYPE)
            .addModifiers(Modifier.PUBLIC)
            .returns(FOLD_RETURN_TYPE);

    for (OutputValue arg : values) {
      TypeName visitor =
          ParameterizedTypeName.get(
              ClassName.get(Function.class),
              TypeVariableUtils.withoutMissingTypeVariables(
                  arg.parameterizedOutputClass(), availableTypeVariables),
              FOLD_RETURN_TYPE);

      builder.addParameter(
          ParameterSpec.builder(visitor, asCamelCase(arg.name()))
              .addAnnotation(Nonnull.class)
              .build());
    }

    return builder;
  }

  public MethodSpec createAbstractFoldMethod() throws ParserException {
    return createFoldSignature(null).addModifiers(Modifier.ABSTRACT).build();
  }

  public MethodSpec createFoldMethod(OutputValue value) throws ParserException {
    MethodSpec.Builder builder =
        createFoldSignature(value.typeVariables())
            .addAnnotation(Override.class)
            .addModifiers(Modifier.FINAL);

    builder.addStatement("return $L.apply(this)", asCamelCase(value.name()));

    return builder.build();
  }

  private static String asCamelCase(String text) {
    return Character.toLowerCase(text.charAt(0)) + text.substring(1);
  }
}
