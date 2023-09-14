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

import com.spotify.dataenum.processor.ProcessingContext;
import com.spotify.dataenum.processor.data.Value;
import com.squareup.javapoet.MethodSpec;
import com.sun.tools.javac.util.Pair;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.processing.Messager;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;

final class MembersParser {

  private static final String NOT_SUPPORTED_TYPE = "not-supported-type";
  private static final String NOT_SUPPORTED_METHOD = "not-supported-method";
  private static final String VALUE = "value";
  private static final String ADT_METHOD = "adt-method";

  private MembersParser() {}

  static Pair<List<Value>, List<MethodSpec>> parse(TypeElement enumElement, ProcessingContext ctx) {
    final Messager messager = ctx.env.getMessager();
    boolean error = false;
    List<Value> values = new ArrayList<>();
    Set<String> lowerCaseValueNames = new HashSet<>();

    final ClassifiedElements classified =
        new ClassifiedElementsFactory(() -> ctx).get(enumElement.getEnclosedElements());

    // report illegal member types
    if (classified.containsNotSupportedTypes()) {
      error = true;
      classified
          .notSupportedTypes()
          .forEach(
              element ->
                  messager.printMessage(
                      Diagnostic.Kind.ERROR,
                      String.format(
                          "Value specs must be methods, found %s: %s",
                          element.getKind().toString().toLowerCase(), element),
                      element));
    }

    // report illegal member methods
    if (classified.containsNotSupportedMethods()) {
      error = true;
      classified
          .notSupportedMethods()
          .forEach(
              element ->
                  messager.printMessage(
                      Kind.ERROR,
                      String.format(
                          "Neither Value spec, nor Method spec. "
                              + "Value spec must return dataenum_case, but found (%s). "
                              + "Method spec must be marked as `default`, but found (%s) "
                              + "Element: %s",
                          element.getReturnType(),
                          element.getModifiers().stream()
                              .map(Modifier::toString)
                              .collect(Collectors.joining(", ")),
                          element),
                      element));
    }

    // handle Values
    for (Element valueElement : classified.values()) {
      Value value = ValueParser.parse(valueElement, ctx);
      if (value == null) {
        error = true;
        continue;
      }
      if (!lowerCaseValueNames.add(value.name().toLowerCase())) {
        messager.printMessage(
            Kind.ERROR,
            "Duplicate case name '"
                + value.name().toLowerCase()
                + "' - lower-case case names must be unique.",
            valueElement);
      }

      values.add(value);
    }

    // handle adt methods
    return error
        ? null
        : new Pair<>(
            values,
            classified.adtMethods().stream()
                .map(x -> MethodParser.parse(x, ctx.trees))
                .collect(Collectors.toList()));
  }

  private static final class ClassifiedElementsFactory {
    private final Supplier<ProcessingContext> ctx;

    private ClassifiedElementsFactory(Supplier<ProcessingContext> ctx) {
      this.ctx = ctx;
    }

    public ClassifiedElements get(List<? extends Element> elements) {
      // predicate: isValue
      final Predicate<ExecutableElement> isValue =
          x -> ValueParser.isValueSpecMarker(x.getReturnType(), ctx.get());

      // predicate: isAdtMethod
      final Predicate<ExecutableElement> isAdtMethod =
          x -> x.getModifiers().contains(Modifier.DEFAULT);

      final Map<String, ? extends List<? extends Element>> xs =
          elements.stream()
              .collect(
                  Collectors.groupingBy(
                      e -> {
                        if (e instanceof ExecutableElement) {
                          final ExecutableElement el = (ExecutableElement) e;
                          if (isValue.test(el)) return VALUE;
                          else if (isAdtMethod.test(el)) return ADT_METHOD;
                          else return NOT_SUPPORTED_METHOD;
                        } else {
                          return NOT_SUPPORTED_TYPE;
                        }
                      }));

      return new ClassifiedElements(xs);
    }
  }

  private static final class ClassifiedElements {

    private final Map<String, ? extends List<? extends Element>> elements;

    private ClassifiedElements(Map<String, ? extends List<? extends Element>> elements) {
      this.elements = elements;
    }

    boolean containsNotSupportedTypes() {
      return elements.containsKey(NOT_SUPPORTED_TYPE);
    }

    List<? extends Element> notSupportedTypes() {
      if (elements.containsKey(NOT_SUPPORTED_TYPE)) {
        return elements.get(NOT_SUPPORTED_TYPE);
      } else return Collections.emptyList();
    }

    boolean containsNotSupportedMethods() {
      return elements.containsKey(NOT_SUPPORTED_METHOD);
    }

    List<ExecutableElement> notSupportedMethods() {
      if (elements.containsKey(NOT_SUPPORTED_METHOD)) {
        return elements.get(NOT_SUPPORTED_METHOD).stream()
            .map(x -> (ExecutableElement) x)
            .collect(Collectors.toList());
      } else return Collections.emptyList();
    }

    boolean containsValues() {
      return elements.containsKey(VALUE);
    }

    List<ExecutableElement> values() {
      if (elements.containsKey(VALUE)) {
        return elements.get(VALUE).stream()
            .map(x -> (ExecutableElement) x)
            .collect(Collectors.toList());
      } else return Collections.emptyList();
    }

    boolean containsAdtMethods() {
      return elements.containsKey(ADT_METHOD);
    }

    List<ExecutableElement> adtMethods() {
      if (elements.containsKey(ADT_METHOD)) {
        return elements.get(ADT_METHOD).stream()
            .map(x -> (ExecutableElement) x)
            .collect(Collectors.toList());
      } else return Collections.emptyList();
    }
  }
}
