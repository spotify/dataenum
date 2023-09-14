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

package com.spotify.dataenum.processor.generator.method;

import com.squareup.javapoet.*;
import com.sun.source.tree.MethodTree;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;

public final class MethodMethods {
  private MethodMethods() {}

  public static MethodSpec.Builder builderFrom(
      ExecutableElement el,
      Function<Stream<Modifier>, Stream<Modifier>> adjustModifiers,
      Function<Stream<? extends AnnotationMirror>, Stream<? extends AnnotationMirror>>
          adjustAnnotations) {
    return MethodSpec.methodBuilder(el.getSimpleName().toString())
        .addModifiers(adjustModifiers.apply(el.getModifiers().stream()).collect(Collectors.toSet()))
        .addAnnotations(
            adjustAnnotations
                .apply(el.getAnnotationMirrors().stream())
                .map(AnnotationSpec::get)
                .collect(Collectors.toList()))
        .addTypeVariables(
            el.getTypeParameters().stream().map(TypeVariableName::get).collect(Collectors.toList()))
        .returns(TypeName.get(el.getReturnType()))
        .addParameters(
            el.getParameters().stream().map(ParameterSpec::get).collect(Collectors.toList()))
        .varargs(el.isVarArgs())
        .addExceptions(
            el.getThrownTypes().stream().map(TypeName::get).collect(Collectors.toList()));
  }

  public static CodeBlock codeBlockFrom(ExecutableElement el, Trees trees) {
    final MethodTree methodTree = MethodLookup.lookupTree(el, trees);
    return methodTree.getBody().getStatements().stream()
        .map(x -> CodeBlock.of(x.toString()))
        .reduce(CodeBlock.of(""), (a, b) -> a.toBuilder().add(b).build());
  }

  private static class MethodLookup extends TreePathScanner<Void, Void> {

    private final Consumer<MethodTree> onMethod;

    private MethodLookup(Consumer<MethodTree> onMethod) {
      this.onMethod = onMethod;
    }

    public static MethodTree lookupTree(ExecutableElement methodElement, Trees trees) {
      assert methodElement.getKind() == ElementKind.METHOD;

      AtomicReference<MethodTree> methodRef = new AtomicReference<>();
      new MethodLookup(methodRef::set).scan(trees.getPath(methodElement), null);
      MethodTree method = methodRef.get();
      assert method != null;

      return method;
    }

    @Override
    public Void visitMethod(MethodTree methodTree, Void v) {
      this.onMethod.accept(methodTree);
      return super.visitMethod(methodTree, v);
    }
  }
}
