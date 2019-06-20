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

import com.spotify.dataenum.processor.data.OutputSpec;
import com.spotify.dataenum.processor.data.OutputValue;
import com.spotify.dataenum.processor.data.Parameter;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.ParameterSpec;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Modifier;

public class ValueMethods {

  private final OutputValue value;

  public ValueMethods(OutputValue value) {
    this.value = value;
  }

  public MethodSpec createFactoryMethod(OutputSpec spec) {
    MethodSpec.Builder factory =
        MethodSpec.methodBuilder(asCamelCase(value.name()))
            .addTypeVariables(spec.typeVariables())
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .returns(spec.parameterizedOutputClass());

    if (value.javadoc() != null) {
      factory.addJavadoc(value.javadoc() + "\n\n");
    }

    factory.addJavadoc(
        "@return a {@link $T} (see {@link $T#$L} for source)\n",
        value.outputClass(),
        spec.specClass(),
        value.name());

    StringBuilder newString = new StringBuilder();
    List<Object> newArgs = new ArrayList<>();

    newString.append("return new $T(");
    newArgs.add(value.parameterizedOutputClass());

    boolean first = true;

    for (Parameter parameter : value.parameters()) {
      ParameterSpec.Builder builder = ParameterSpec.builder(parameter.type(), parameter.name());
      if (!parameter.type().isPrimitive()) {
        if (parameter.canBeNull()) {
          builder.addAnnotation(Nullable.class);
        } else {
          builder.addAnnotation(Nonnull.class);
        }
      }

      factory.addParameter(builder.build());

      if (first) {
        newString.append("$L");
        first = false;
      } else {
        newString.append(", $L");
      }

      newArgs.add(parameter.name());
    }

    newString.append(")");

    if (spec.hasTypeVariables()) {
      newString.append(".as$L()");
      newArgs.add(spec.outputClass().simpleName());
    }

    factory.addStatement(newString.toString(), newArgs.toArray());

    return factory.build();
  }

  public MethodSpec createIsMethod() {
    return MethodSpec.methodBuilder("is" + value.name())
        .returns(boolean.class)
        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
        .addStatement("return (this instanceof $T)", value.outputClass())
        .build();
  }

  public MethodSpec createAsMethod(OutputSpec spec) {
    Builder builder =
        MethodSpec.methodBuilder("as" + value.name())
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .returns(value.parameterizedOutputClass())
            .addStatement("return ($T) this", value.parameterizedOutputClass());

    if (!ValueTypeFactory.extractMissingTypeVariablesForValue(value, spec).isEmpty()
        && value.hasTypeVariables()) {
      builder.addAnnotation(ValueTypeFactory.SUPPRESS_UNCHECKED_WARNINGS);
    }

    return builder.build();
  }

  private static String asCamelCase(String text) {
    return Character.toLowerCase(text.charAt(0)) + text.substring(1);
  }
}
