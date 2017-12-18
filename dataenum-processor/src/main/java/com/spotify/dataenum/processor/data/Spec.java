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
import com.squareup.javapoet.TypeVariableName;

/**
 * Represents the data represented in a 'MyValue_spec' class.
 *
 * <p>Contains a reference to the source _spec and its type variable, as well as all the possible
 * values.
 */
public class Spec {
  private final ClassName specClass;
  private final Iterable<TypeVariableName> typeVariables;
  private final Iterable<Value> values;

  public Spec(
      ClassName specClass, Iterable<TypeVariableName> typeVariables, Iterable<Value> values) {
    this.specClass = specClass;
    this.typeVariables = typeVariables;
    this.values = values;
  }

  public ClassName specClass() {
    return specClass;
  }

  public Iterable<TypeVariableName> typeVariables() {
    return typeVariables;
  }

  public boolean hasTypeVariables() {
    return !Iterables.isEmpty(typeVariables());
  }

  public Iterable<Value> values() {
    return values;
  }
}
