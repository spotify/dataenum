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

/** Output-version of value. Contains derived information that wasn't available during parsing. */
public class OutputValue {
  private final ClassName outputClass;
  private final Iterable<TypeVariableName> typeVariables;
  private final String name;
  private final Iterable<Parameter> parameters;

  public OutputValue(
      ClassName outputClass,
      String name,
      Iterable<Parameter> parameters,
      Iterable<TypeVariableName> typeVariables) {
    this.outputClass = outputClass;
    this.name = name;
    this.parameters = parameters;
    this.typeVariables = typeVariables;
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

  public Iterable<TypeVariableName> typeVariables() {
    return typeVariables;
  }

  public boolean hasTypeVariables() {
    return !Iterables.isEmpty(typeVariables());
  }

  public String name() {
    return name;
  }

  public Iterable<Parameter> parameters() {
    return parameters;
  }

  public boolean hasParameters() {
    return !Iterables.isEmpty(parameters());
  }
}
