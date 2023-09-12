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

import com.spotify.dataenum.function.Function;
import com.spotify.dataenum.processor.ProcessingContext;
import com.spotify.dataenum.processor.data.Parameter;
import com.spotify.dataenum.processor.data.Value;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.AnnotationSpec.Builder;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;

final class ValueParser {

  private ValueParser() {}

  static Value parse(Element element, ProcessingContext ctx) {
    Messager messager = ctx.env.getMessager();

    ExecutableElement methodElement = (ExecutableElement) element;

    if (methodElement.getTypeParameters().size() != 0) {
      messager.printMessage(
          Diagnostic.Kind.ERROR,
          String.format(
              "Type parameters must be specified on the top-level interface, found: %s", element),
          element);
      return null;
    }

    if (methodElement.isVarArgs()) {
      messager.printMessage(
          Diagnostic.Kind.ERROR,
          String.format("Vararg parameters not permitted: %s", element),
          element);
      return null;
    }

    List<Parameter> parameters = new ArrayList<>();
    for (VariableElement parameterElement : methodElement.getParameters()) {
      String parameterName = parameterElement.getSimpleName().toString();
      TypeName parameterType = TypeName.get(parameterElement.asType());

      boolean nullable = isAnnotationPresent(parameterElement, ValueParser::isNullableAnnotation);
      boolean redacted = isAnnotationPresent(parameterElement, ValueParser::isRedactedAnnotation);
      Element parameterTypeElement = ctx.env.getTypeUtils().asElement(parameterElement.asType());
      boolean isEnum =
          parameterTypeElement != null && parameterTypeElement.getKind() == ElementKind.ENUM;

      parameters.add(new Parameter(parameterName, parameterType, nullable, redacted, isEnum));
    }

    String javadoc = ctx.env.getElementUtils().getDocComment(element);

    if (javadoc != null) {
      javadoc = javadoc.trim();
    }

    String valueSimpleName = methodElement.getSimpleName().toString();
    return new Value(
        valueSimpleName, javadoc, parameters, parseMethodAnnotations(methodElement, messager));
  }

  private static Iterable<AnnotationSpec> parseMethodAnnotations(
      ExecutableElement methodElement, Messager messager) {
    ArrayList<AnnotationSpec> annotations = new ArrayList<>();

    for (AnnotationMirror annotationMirror : methodElement.getAnnotationMirrors()) {
      TypeName annotationTypeName =
          ClassName.get(annotationMirror.getAnnotationType().asElement().asType());

      if (!(annotationTypeName instanceof ClassName)) {
        messager.printMessage(
            Kind.ERROR,
            "Annotation is not a class; this shouldn't happen",
            methodElement,
            annotationMirror);
        continue;
      }

      Builder builder = AnnotationSpec.builder(((ClassName) annotationTypeName));

      for (Entry<? extends ExecutableElement, ? extends AnnotationValue> entry :
          annotationMirror.getElementValues().entrySet()) {

        builder.addMember(entry.getKey().getSimpleName().toString(), entry.getValue().toString());
      }

      annotations.add(builder.build());
    }

    return annotations;
  }

  private static boolean isAnnotationPresent(
      VariableElement parameterElement, Function<AnnotationMirror, Boolean> criterion) {
    for (AnnotationMirror annotation : parameterElement.getAnnotationMirrors()) {
      if (criterion.apply(annotation)) {
        return true;
      }
    }
    return false;
  }

  public static boolean isValueSpecMarker(TypeMirror returnType, ProcessingContext ctx) {

    return ctx.env.getTypeUtils().isSameType(returnType, ctx.dataenum_class_element);
  }

  private static boolean isNullableAnnotation(AnnotationMirror annotation) {
    return "Nullable".contentEquals(annotation.getAnnotationType().asElement().getSimpleName());
  }

  private static boolean isRedactedAnnotation(AnnotationMirror annotationMirror) {
    return "Redacted"
        .contentEquals(annotationMirror.getAnnotationType().asElement().getSimpleName());
  }
}
