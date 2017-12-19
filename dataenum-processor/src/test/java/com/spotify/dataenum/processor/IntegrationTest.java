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

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

import com.google.auto.value.processor.AutoValueProcessor;
import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Ignore;
import org.junit.Test;

public class IntegrationTest {

  private static void assertThatEnumGeneratedMatchingFile(String className) {
    Compilation compilation =
        javac()
            .withProcessors(new DataEnumProcessor(), new AutoValueProcessor())
            .compile(JavaFileObjects.forResource(className + "_dataenum.java"));

    assertThat(compilation).succeededWithoutWarnings();
    assertThat(compilation)
        .generatedSourceFile(className)
        .hasSourceEquivalentTo(JavaFileObjects.forResource(className + ".java"));
  }

  @Test
  public void emptyEnum() throws Exception {
    assertThatEnumGeneratedMatchingFile("Empty");
  }

  @Test
  public void emptyValueEnum() throws Exception {
    assertThatEnumGeneratedMatchingFile("EmptyValue");
  }

  @Test
  public void primitiveValueEnum() throws Exception {
    assertThatEnumGeneratedMatchingFile("PrimitiveValue");
  }

  @Test
  public void recursiveValueEnum() throws Exception {
    assertThatEnumGeneratedMatchingFile("RecursiveValue");
  }

  @Test
  public void nullableValueEnum() throws Exception {
    assertThatEnumGeneratedMatchingFile("NullableValue");
  }

  @Test
  public void multipleValuesEnum() throws Exception {
    assertThatEnumGeneratedMatchingFile("MultipleValues");
  }

  @Test
  public void genericValuesEnum() throws Exception {
    assertThatEnumGeneratedMatchingFile("GenericValues");
  }

  @Test
  public void recursiveGenericValueEnum() throws Exception {
    assertThatEnumGeneratedMatchingFile("RecursiveGenericValue");
  }

  @Test
  public void innerGenericValueEnum() throws Exception {
    assertThatEnumGeneratedMatchingFile("InnerGenericValue");
  }

  @Test
  public void outputHasTheSamePackage() throws Exception {
    assertThatEnumGeneratedMatchingFile("just/some/pkg/InPackage");
  }

  @Test
  public void varargValueEnum() throws Exception {
    assertThatEnumGeneratedMatchingFile("VarargValue");
  }

  @Ignore(
      "This test is unsound since it encourages creating generic arrays, which can cause "
          + "severe runtime problems (e.g. ClassCastExceptions).")
  @Test
  public void genericVarargValueEnum() throws Exception {
    assertThatEnumGeneratedMatchingFile("GenericVarargValue");
  }

  @Test
  public void enumAsInner() throws Exception {
    assertThatEnumGeneratedMatchingFile("EnumAsInner");
  }

  @Test
  public void referenceOtherDataenum() throws Exception {
    Compilation compilation =
        javac()
            .withOptions("-implicit:class")
            .withProcessors(new DataEnumProcessor(), new AutoValueProcessor())
            .compile(
                JavaFileObjects.forResource("ReferencesOther_dataenum.java"),
                JavaFileObjects.forResource("just/some/pkg/InPackage_dataenum.java"));

    assertThat(compilation).succeededWithoutWarnings();
    assertThat(compilation)
        .generatedSourceFile("ReferencesOther")
        .hasSourceEquivalentTo(JavaFileObjects.forResource("ReferencesOther" + ".java"));
  }

  @Test
  public void shouldWarnAboutPublicSpec() throws Exception {
    Compilation compilation =
        javac()
            .withProcessors(new DataEnumProcessor())
            .compile(JavaFileObjects.forResource("PublicSpec_dataenum.java"));
    assertThat(compilation).succeeded();
    assertThat(compilation).hadWarningCount(0);
  }

  @Test
  public void shouldReportBadValueSpecs() throws Exception {
    Compilation compilation =
        javac()
            .withProcessors(new DataEnumProcessor())
            .compile(JavaFileObjects.forResource("BadValueSpecs_dataenum.java"));
    assertThat(compilation).failed();
    assertThat(compilation).hadErrorCount(2);
    assertThat(compilation).hadErrorContaining("Value specs must be methods, found interface");
    assertThat(compilation).hadErrorContaining("Value specs must return dataenum_case, found void");
  }

  @Test
  public void shouldReportGenericsOnValueSpecs() throws Exception {
    Compilation compilation =
        javac()
            .withProcessors(new DataEnumProcessor())
            .compile(JavaFileObjects.forResource("GenericDeclarationOnValues_dataenum.java"));
    assertThat(compilation).failed();
    assertThat(compilation).hadErrorCount(1);
    assertThat(compilation)
        .hadErrorContaining(
            "Type parameters must be specified on the top-level interface, found: <T>Just(T)");
  }

  @Test
  public void shouldReportNonInterfaceSpecs() throws Exception {
    Compilation compilation =
        javac()
            .withProcessors(new DataEnumProcessor())
            .compile(JavaFileObjects.forResource("NonInterfaceSpec_dataenum.java"));
    assertThat(compilation).failed();
    assertThat(compilation).hadErrorCount(1);
    assertThat(compilation).hadErrorContaining("@DataEnum can only be used on interfaces");
  }
}
