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
package com.spotify.dataenum.processor;

import com.spotify.dataenum.ConstructorAccess;
import com.spotify.dataenum.DataEnum;
import com.spotify.dataenum.DataenumUtils;
import com.spotify.dataenum.processor.data.OutputSpec;
import com.spotify.dataenum.processor.data.Parameter;
import com.spotify.dataenum.processor.data.Spec;
import com.spotify.dataenum.processor.data.Value;
import com.spotify.dataenum.processor.generator.data.OutputSpecFactory;
import com.spotify.dataenum.processor.generator.spec.SpecTypeFactory;
import com.spotify.dataenum.processor.parser.ParserException;
import com.spotify.dataenum.processor.parser.SpecParser;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.sun.source.util.Trees;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

public class DataEnumProcessor extends AbstractProcessor {
  private Trees trees;

  @Override
  public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    this.trees = Trees.instance(processingEnv);
  }

  @Override
  public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
    Filer filer = processingEnv.getFiler();
    Messager messager = processingEnv.getMessager();

    AccessSelector accessSelector =
        new AccessSelector(roundEnvironment.getElementsAnnotatedWith(ConstructorAccess.class));

    for (Element element : roundEnvironment.getElementsAnnotatedWith(DataEnum.class)) {
      try {

        Spec spec = SpecParser.parse(element, new ProcessingContext(processingEnv));
        if (spec == null) {
          continue;
        }

        OutputSpec outputSpec = OutputSpecFactory.create(spec);
        TypeSpec outputTypeSpec =
            SpecTypeFactory.create(
                outputSpec,
                accessSelector.accessModifierFor(outputSpec.outputClass().packageName()),
                element);

        JavaFile.Builder javaFileBuilder =
            JavaFile.builder(outputSpec.outputClass().packageName(), outputTypeSpec);
        if (needsCheckNotNull(spec)) {
          javaFileBuilder.addStaticImport(DataenumUtils.class, "checkNotNull");
        }
        if (needsNullSafeEquals(spec)) {
          javaFileBuilder.addStaticImport(DataenumUtils.class, "equal");
        }

        JavaFile javaFile = javaFileBuilder.build();
        javaFile.writeTo(filer);

      } catch (IOException | ParserException e) {
        messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
      }
    }

    return false;
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return Collections.singleton(DataEnum.class.getCanonicalName());
  }

  private static boolean needsCheckNotNull(Spec enumDef) {
    for (Value value : enumDef.values()) {
      for (Parameter parameter : value.parameters()) {
        if (!parameter.type().isPrimitive() && !parameter.canBeNull()) {
          return true;
        }
      }
    }
    return false;
  }

  private static boolean needsNullSafeEquals(Spec enumDef) {
    for (Value value : enumDef.values()) {
      for (Parameter parameter : value.parameters()) {
        if (!parameter.type().isPrimitive()
            && !(parameter.type() instanceof ArrayTypeName)
            && parameter.canBeNull()) {
          return true;
        }
      }
    }
    return false;
  }
}
