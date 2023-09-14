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

import static com.spotify.dataenum.processor.util.Iterables.fromOptional;

import com.spotify.dataenum.processor.DataEnumProcessor;
import com.spotify.dataenum.processor.data.OutputSpec;
import com.spotify.dataenum.processor.data.OutputValue;
import com.spotify.dataenum.processor.generator.match.MapMethods;
import com.spotify.dataenum.processor.generator.match.MatchMethods;
import com.spotify.dataenum.processor.generator.value.ValueMethods;
import com.spotify.dataenum.processor.generator.value.ValueTypeFactory;
import com.spotify.dataenum.processor.parser.ParserException;
import com.spotify.dataenum.processor.util.Iterables;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

public final class SpecTypeFactory {

  private SpecTypeFactory() {}

  public static TypeSpec create(
      OutputSpec spec, Optional<Modifier> constructorAccessModifier, Element element)
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

    TypeSpec.Builder enumBuilder =
        TypeSpec.classBuilder(spec.outputClass())
            .addOriginatingElement(element)
            .addJavadoc("Generated from {@link $T}\n", spec.specClass())
            .addAnnotation(
                AnnotationSpec.builder(Generated.class)
                    .addMember(
                        "value", CodeBlock.of("$S", DataEnumProcessor.class.getCanonicalName()))
                    .build())
            .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
            .addTypeVariables(spec.typeVariables())
            .addSuperinterfaces(spec.superInterfaces());

    // add constructor with correct access
    enumBuilder.addMethod(
        MethodSpec.constructorBuilder()
            .addModifiers(fromOptional(constructorAccessModifier))
            .build());

    enumBuilder.addTypes(valueTypes);
    enumBuilder.addMethods(factoryMethods);
    enumBuilder.addMethods(isMethods);
    enumBuilder.addMethods(asMethods);

    if (!Iterables.isEmpty(spec.outputValues())) {
      enumBuilder.addMethod(matchMethods.createAbstractFoldVoidMethod());
      enumBuilder.addMethod(mapMethods.createAbstractFoldMethod());
    }

    enumBuilder.addMethods(spec.methods());

    return enumBuilder.build();
  }
}
