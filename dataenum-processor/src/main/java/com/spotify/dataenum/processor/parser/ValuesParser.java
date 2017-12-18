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
package com.spotify.dataenum.processor.parser;

import com.spotify.dataenum.processor.data.Value;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

final class ValuesParser {

  private ValuesParser() {}

  static List<Value> parse(TypeElement enumElement, ProcessingEnvironment processingEnv) {
    boolean error = false;
    List<Value> values = new ArrayList<>();
    for (Element valueElement : enumElement.getEnclosedElements()) {
      Value value = ValueParser.parse(valueElement, processingEnv);
      if (value == null) {
        error = true;
        continue;
      }
      values.add(value);
    }

    return error ? null : values;
  }
}
