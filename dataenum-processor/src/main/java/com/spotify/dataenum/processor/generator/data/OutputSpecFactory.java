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
package com.spotify.dataenum.processor.generator.data;

import com.spotify.dataenum.processor.data.OutputSpec;
import com.spotify.dataenum.processor.data.OutputValue;
import com.spotify.dataenum.processor.data.Spec;
import com.spotify.dataenum.processor.data.Value;
import com.spotify.dataenum.processor.parser.ParserException;
import com.squareup.javapoet.ClassName;
import java.util.ArrayList;
import java.util.List;

public final class OutputSpecFactory {

  private OutputSpecFactory() {}

  static final String SUFFIX = "_dataenum";

  public static OutputSpec create(Spec spec) throws ParserException {
    ClassName specClass = spec.specClass();

    ClassName outputClass = toOutputClass(specClass);

    List<OutputValue> values = new ArrayList<>();
    for (Value value : spec.values()) {
      values.add(OutputValueFactory.create(value, outputClass, spec));
    }

    return new OutputSpec(spec, outputClass, values);
  }

  static ClassName toOutputClass(ClassName dataEnumClass) throws ParserException {

    String packageName = dataEnumClass.packageName();
    String name = dataEnumClass.simpleName();

    if (!name.endsWith(SUFFIX)) {
      throw new ParserException(
          String.format(
              "Bad name for DataEnum interface! Name must end with '%s', found: %s", SUFFIX, name));
    }

    String nameWithoutSuffix = name.substring(0, name.length() - SUFFIX.length());
    return ClassName.get(packageName, nameWithoutSuffix);
  }
}
