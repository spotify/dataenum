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

import static com.spotify.dataenum.processor.generator.match.TypeVariableUtils.withoutMissingTypeVariables;

import com.spotify.dataenum.function.Consumer;
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

public class MatchMethods {

  private final Iterable<OutputValue> values;

  public MatchMethods(Iterable<OutputValue> values) {
    this.values = values;
  }

  private MethodSpec.Builder createFoldVoidSignature(
      Iterable<TypeVariableName> availableTypeVariables) throws ParserException {
    MethodSpec.Builder builder =
        MethodSpec.methodBuilder("match").addModifiers(Modifier.PUBLIC).returns(void.class);

    for (OutputValue arg : values) {
      TypeName visitor =
          ParameterizedTypeName.get(
              ClassName.get(Consumer.class),
              withoutMissingTypeVariables(arg.parameterizedOutputClass(), availableTypeVariables));

      builder.addParameter(
          ParameterSpec.builder(visitor, asCamelCase(arg.name()))
              .addAnnotation(Nonnull.class)
              .build());
    }

    return builder;
  }

  public MethodSpec createAbstractFoldVoidMethod() throws ParserException {
    return createFoldVoidSignature(null).addModifiers(Modifier.ABSTRACT).build();
  }

  public MethodSpec createFoldVoidMethod(OutputValue value) throws ParserException {
    MethodSpec.Builder builder =
        createFoldVoidSignature(value.typeVariables())
            .addAnnotation(Override.class)
            .addModifiers(Modifier.FINAL);

    builder.addStatement("$L.accept(this)", asCamelCase(value.name()));

    return builder.build();
  }

  private static String asCamelCase(String text) {
    return Character.toLowerCase(text.charAt(0)) + text.substring(1);
  }
}
