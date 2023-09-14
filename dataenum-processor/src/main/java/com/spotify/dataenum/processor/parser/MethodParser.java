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

package com.spotify.dataenum.processor.parser;

import com.spotify.dataenum.Static;
import com.spotify.dataenum.processor.generator.method.MethodMethods;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.sun.source.util.Trees;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;

public final class MethodParser {

  private static final TypeName STATIC_ANNOTATION = ClassName.get(Static.class);

  public static MethodSpec parse(ExecutableElement el, Trees trees) {
    // filter off DEFAULT modifier
    Function<Stream<Modifier>, Stream<Modifier>> adjustModifiers =
        mods -> mods.filter(x -> x != Modifier.DEFAULT);
    // populate STATIC modifier if required
    if (el.getAnnotation(Static.class) != null) {
      adjustModifiers =
          adjustModifiers.andThen(mods -> Stream.concat(Stream.of(Modifier.STATIC), mods));
    }

    // make sure internal @Static annotation doesn't sneak out
    Function<Stream<? extends AnnotationMirror>, Stream<? extends AnnotationMirror>>
        adjustAnnotations =
            annos -> annos.filter(x -> !"@com.spotify.dataenum.Static".equals(x.toString()));

    return MethodMethods.builderFrom(el, adjustModifiers, adjustAnnotations)
        .addCode(MethodMethods.codeBlockFrom(el, trees))
        .build();
  }
}
