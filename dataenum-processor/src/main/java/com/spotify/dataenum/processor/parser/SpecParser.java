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

import com.spotify.dataenum.processor.data.Spec;
import com.spotify.dataenum.processor.data.Value;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeVariableName;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.tools.Diagnostic;

public final class SpecParser {

  private SpecParser() {}

  public static Spec parse(Element element, ProcessingEnvironment processingEnv) {
    Messager messager = processingEnv.getMessager();

    if (element.getKind() != ElementKind.INTERFACE) {
      messager.printMessage(
          Diagnostic.Kind.ERROR, "@DataEnum can only be used on interfaces.", element);
      return null;
    }

    TypeElement dataEnum = (TypeElement) element;

    List<TypeVariableName> typeVariableNames = new ArrayList<>();
    for (TypeParameterElement typeParameterElement : dataEnum.getTypeParameters()) {
      typeVariableNames.add(TypeVariableName.get(typeParameterElement));
    }

    List<Value> values = ValuesParser.parse(dataEnum, processingEnv);
    if (values == null) {
      return null;
    }

    ClassName enumInterface = ClassName.get(dataEnum);
    return new Spec(enumInterface, typeVariableNames, values);
  }
}
