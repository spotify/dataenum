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

/** Represents one of the possible values of a dataenum spec. */
public class Value {
  private final String simpleName;
  private final Iterable<Parameter> parameters;
  private final boolean varargs;

  public Value(String simpleName, Iterable<Parameter> parameters, boolean varargs) {
    this.simpleName = simpleName;
    this.parameters = parameters;
    this.varargs = varargs;
  }

  public String name() {
    return simpleName;
  }

  public Iterable<Parameter> parameters() {
    return parameters;
  }

  public boolean hasParameters() {
    return !Iterables.isEmpty(parameters());
  }

  public boolean hasVarargs() {
    return varargs;
  }
}
