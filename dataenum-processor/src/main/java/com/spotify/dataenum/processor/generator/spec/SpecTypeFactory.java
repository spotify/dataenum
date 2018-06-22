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
package com.spotify.dataenum.processor.generator.spec;

import com.spotify.dataenum.processor.DataEnumProcessor;
import com.spotify.dataenum.processor.data.OutputSpec;
import com.spotify.dataenum.processor.data.OutputValue;
import com.spotify.dataenum.processor.generator.match.MapMethods;
import com.spotify.dataenum.processor.generator.match.MatchMethods;
import com.spotify.dataenum.processor.generator.value.ValueMethods;
import com.spotify.dataenum.processor.generator.value.ValueTypeFactory;
import com.spotify.dataenum.processor.parser.ParserException;
import com.spotify.dataenum.processor.util.Iterables;
import com.squareup.javapoet.*;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.spotify.dataenum.processor.util.Iterables.fromOptional;

public final class SpecTypeFactory {

  private static final String JAVA_8_GENERATED_ANNOTATION_CLASS_NAME = "javax.annotation.Generated";
  private static final String JAVA_9_GENERATED_ANNOTATION_CLASS_NAME = "javax.annotation.processing.Generated";

  private SpecTypeFactory() {}

  public static TypeSpec create(
          OutputSpec spec, Optional<Modifier> constructorAccessModifier, Element element, Elements elements)
      throws ParserException {
    List<TypeSpec> valueTypes = new ArrayList<>();
    List<MethodSpec> factoryMethods = new ArrayList<>();
    List<MethodSpec> isMethods = new ArrayList<>();
    List<MethodSpec> asMethods = new ArrayList<>();

    MatchMethods matchMethods = new MatchMethods(spec.outputValues());
    MapMethods mapMethods = new MapMethods(spec.outputValues());

    for (OutputValue value : spec.outputValues()) {
      valueTypes.add(
          ValueTypeFactory.create(
              value, spec, matchMethods, mapMethods, constructorAccessModifier));

      ValueMethods valueMethods = new ValueMethods(value);
      factoryMethods.add(valueMethods.createFactoryMethod(spec));
      isMethods.add(valueMethods.createIsMethod());
      asMethods.add(valueMethods.createAsMethod(spec));
    }

    TypeSpec.Builder enumBuilder  = TypeSpec.classBuilder(spec.outputClass())
            .addOriginatingElement(element)
            .addAnnotation(getAnnotationSpec(elements))
            .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
            .addTypeVariables(spec.typeVariables())
            .addMethod(
              MethodSpec.constructorBuilder()
                  .addModifiers(fromOptional(constructorAccessModifier))
                  .build())
            .addTypes(valueTypes)
            .addMethods(factoryMethods)
            .addMethods(isMethods)
            .addMethods(asMethods);

    if (!Iterables.isEmpty(spec.outputValues())) {
      enumBuilder.addMethod(matchMethods.createAbstractFoldVoidMethod())
              .addMethod(mapMethods.createAbstractFoldMethod());
    }

    return enumBuilder.build();
  }

  private static AnnotationSpec getAnnotationSpec(Elements elements) {
    TypeElement generatedAnnotationElement = getGeneratedAnnotationType(elements);

    if (generatedAnnotationElement == null) {
      throw new IllegalStateException("Could not find javax.annotation.Generated or javax.annotation.processing.Generated. Do you have javax.annotation:jsr250-api:1.0 as a dependency?");
    }

    ClassName generatedName = ClassName.get(generatedAnnotationElement);
    return AnnotationSpec.builder(generatedName)
            .addMember("value", CodeBlock.of("$S", DataEnumProcessor.class.getCanonicalName()))
            .build();
  }

  private static TypeElement getGeneratedAnnotationType(Elements elements) {
    TypeElement typeElement = elements.getTypeElement(JAVA_8_GENERATED_ANNOTATION_CLASS_NAME);
    if (typeElement != null) return typeElement;

    return elements.getTypeElement(JAVA_9_GENERATED_ANNOTATION_CLASS_NAME);
  }
}
