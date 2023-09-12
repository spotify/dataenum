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
package com.spotify.dataenum.processor.data;

import com.spotify.dataenum.processor.util.Iterables;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;

/** Output-version of spec. Contains derived information that wasn't available during parsing. */
public class OutputSpec extends Spec {
  private final ClassName outputClass;
  private final Iterable<OutputValue> outputValues;

  public OutputSpec(Spec input, ClassName outputClass, Iterable<OutputValue> outputValues) {
    super(input.specClass(), input.typeVariables(), input.values(), input.methods());
    this.outputClass = outputClass;
    this.outputValues = outputValues;
  }

  public ClassName outputClass() {
    return outputClass;
  }

  public TypeName parameterizedOutputClass() {
    if (!hasTypeVariables()) {
      return outputClass();
    }

    TypeName[] typeNames = Iterables.toArray(typeVariables(), TypeVariableName.class);
    return ParameterizedTypeName.get(outputClass(), typeNames);
  }

  public Iterable<OutputValue> outputValues() {
    return outputValues;
  }
}
