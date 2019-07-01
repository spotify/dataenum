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
import com.squareup.javapoet.AnnotationSpec;
import javax.annotation.Nullable;

/** Represents one of the possible values of a dataenum spec. */
public class Value {
  private final String simpleName;
  @Nullable private final String javadoc;
  private final Iterable<Parameter> parameters;
  private final Iterable<AnnotationSpec> annotations;

  public Value(
      String simpleName,
      String javadoc,
      Iterable<Parameter> parameters,
      Iterable<AnnotationSpec> annotations) {
    this.simpleName = simpleName;
    this.javadoc = javadoc;
    this.parameters = parameters;
    this.annotations = annotations;
  }

  public String name() {
    return simpleName;
  }

  @Nullable
  public String javadoc() {
    return javadoc;
  }

  public Iterable<Parameter> parameters() {
    return parameters;
  }

  public boolean hasParameters() {
    return !Iterables.isEmpty(parameters());
  }

  public Iterable<AnnotationSpec> annotations() {
    return annotations;
  }
}
