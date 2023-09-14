/*-
 * -\-\-
 * Dataenum Annotation Processor
 * --
 * Copyright (C) 2016 - 2023 Spotify AB
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

package com.spotify.dataenum.processor;

import com.spotify.dataenum.dataenum_case;
import com.sun.source.util.Trees;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public final class ProcessingContext {
  public final ProcessingEnvironment env;
  public final Trees trees;
  public final TypeMirror dataenum_class_element;

  public ProcessingContext(ProcessingEnvironment env) {
    this.env = env;
    this.trees = Trees.instance(env);

    Types types = env.getTypeUtils();
    Elements elements = env.getElementUtils();
    this.dataenum_class_element =
        elements.getTypeElement(dataenum_case.class.getCanonicalName()).asType();
  }
}
