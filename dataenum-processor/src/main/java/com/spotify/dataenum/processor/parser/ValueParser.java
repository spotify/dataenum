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

import com.spotify.dataenum.dataenum_case;
import com.spotify.dataenum.processor.data.Parameter;
import com.spotify.dataenum.processor.data.Value;
import com.squareup.javapoet.TypeName;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

final class ValueParser {

  private ValueParser() {}

  static Value parse(Element element, ProcessingEnvironment processingEnv) {
    Messager messager = processingEnv.getMessager();

    if (element.getKind() != ElementKind.METHOD) {
      messager.printMessage(
          Diagnostic.Kind.ERROR,
          String.format(
              "Value specs must be methods, found %s: %s",
              element.getKind().toString().toLowerCase(), element),
          element);
      return null;
    }

    ExecutableElement methodElement = (ExecutableElement) element;
    if (!isValueSpecMarker(methodElement.getReturnType(), processingEnv)) {
      messager.printMessage(
          Diagnostic.Kind.ERROR,
          String.format(
              "Value specs must return dataenum_case, found %s: %s",
              methodElement.getReturnType(), element),
          element);
      return null;
    }

    if (methodElement.getTypeParameters().size() != 0) {
      messager.printMessage(
          Diagnostic.Kind.ERROR,
          String.format(
              "Type parameters must be specified on the top-level interface, found: %s", element),
          element);
      return null;
    }

    List<Parameter> parameters = new ArrayList<>();
    for (VariableElement parameterElement : methodElement.getParameters()) {
      String parameterName = parameterElement.getSimpleName().toString();
      TypeName parameterType = TypeName.get(parameterElement.asType());

      boolean nullable = false;
      for (AnnotationMirror annotation : parameterElement.getAnnotationMirrors()) {
        if (isNullableAnnotation(annotation)) {
          nullable = true;
          break;
        }
      }

      parameters.add(new Parameter(parameterName, parameterType, nullable));
    }

    boolean varargs = methodElement.isVarArgs();

    String valueSimpleName = methodElement.getSimpleName().toString();
    return new Value(valueSimpleName, parameters, varargs);
  }

  private static boolean isValueSpecMarker(
      TypeMirror returnType, ProcessingEnvironment processingEnvironment) {
    Types types = processingEnvironment.getTypeUtils();
    Elements elements = processingEnvironment.getElementUtils();

    return types.isSameType(
        returnType, elements.getTypeElement(dataenum_case.class.getCanonicalName()).asType());
  }

  private static boolean isNullableAnnotation(AnnotationMirror annotation) {
    Element annotationElement = annotation.getAnnotationType().asElement();
    return "Nullable".contentEquals(annotationElement.getSimpleName());
  }
}
