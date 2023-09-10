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

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import java.util.ArrayList;
import java.util.List;
import javax.tools.JavaFileObject;
import org.junit.Test;

public class IntegrationTest {

  private static void assertThatEnumGeneratedMatchingFile(String className, String... extraFiles) {
    List<JavaFileObject> files = new ArrayList<>(extraFiles.length + 1);

    files.add(JavaFileObjects.forResource(className + "_dataenum.java"));

    for (String file : extraFiles) {
      files.add(JavaFileObjects.forResource(file));
    }

    Compilation compilation =
        javac()
            .withOptions("-implicit:class")
            .withProcessors(new DataEnumProcessor())
            .compile(files);

    assertThat(compilation).succeededWithoutWarnings();
    assertThat(compilation)
        .generatedSourceFile(className)
        .hasSourceEquivalentTo(JavaFileObjects.forResource(className + ".java"));
  }

  @Test
  public void conflictingFieldNames() throws Exception {
    assertThatEnumGeneratedMatchingFile("ConflictingFieldNames");
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
  public void privateConstructors() throws Exception {
    assertThatEnumGeneratedMatchingFile("hide/ctors/PrivateConstructors", "hide/package-info.java");
  }

  @Test
  public void efficientEquals() throws Exception {
    assertThatEnumGeneratedMatchingFile("EfficientEquals", "MyEnum.java");
  }

  @Test
  public void genericValuesGeneratedCodeCompiles() throws Exception {
    Compilation compilation =
        javac()
            .withOptions("-Xlint:all")
            .compile(JavaFileObjects.forResource("GenericValues.java"));

    assertThat(compilation).succeededWithoutWarnings();
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
    Compilation compilation =
        javac()
            .withProcessors(new DataEnumProcessor())
            .compile(JavaFileObjects.forResource("VarargValue_dataenum.java"));
    assertThat(compilation).failed();
    assertThat(compilation).hadErrorCount(1);
    assertThat(compilation).hadErrorContaining("Vararg parameters not permitted");
  }

  @Test
  public void enumAsInner() throws Exception {
    assertThatEnumGeneratedMatchingFile("EnumAsInner");
  }

  @Test
  public void arrayFields() throws Exception {
    assertThatEnumGeneratedMatchingFile("ArrayFields");
  }

  @Test
  public void redactedStrings() throws Exception {
    assertThatEnumGeneratedMatchingFile("Redacted");
  }

  @Test
  public void referenceOtherDataenum() throws Exception {
    Compilation compilation =
        javac()
            .withOptions("-implicit:class")
            .withProcessors(new DataEnumProcessor())
            .compile(JavaFileObjects.forResource("ReferencesOther_dataenum.java"));

    assertThat(compilation).succeededWithoutWarnings();
    assertThat(compilation)
        .generatedSourceFile("ReferencesOther")
        .hasSourceEquivalentTo(JavaFileObjects.forResource("ReferencesOther" + ".java"));
  }

  @Test
  public void shouldNotWarnAboutPublicSpec() throws Exception {
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

  @Test
  public void shouldReportDuplicateCaseNames() throws Exception {
    Compilation compilation =
        javac()
            .withProcessors(new DataEnumProcessor())
            .compile(JavaFileObjects.forResource("DuplicateCases_dataenum.java"));
    assertThat(compilation).failed();
    assertThat(compilation).hadErrorCount(2);
    assertThat(compilation).hadErrorContaining("Duplicate case name 'value'");
    assertThat(compilation).hadErrorContaining("Duplicate case name 'caseisimportant'");
  }

  @Test
  public void shouldGenerateLinkToSourceAsJavadocComment() {
    Compilation compilation =
        javac()
            .withOptions("-implicit:class")
            .withProcessors(new DataEnumProcessor())
            .compile(JavaFileObjects.forResource("javadoc/Javadoc_dataenum.java"));

    assertThat(compilation).succeededWithoutWarnings();
    assertThat(compilation)
        .generatedSourceFile("javadoc.Javadoc")
        .contentsAsUtf8String()
        .contains(" * Generated from {@link Javadoc_dataenum}");
  }

  @Test
  public void shouldGenerateLinkToCaseSourceAsJavadocCommentOnFactoryMethod() {
    Compilation compilation =
        javac()
            .withOptions("-implicit:class")
            .withProcessors(new DataEnumProcessor())
            .compile(JavaFileObjects.forResource("javadoc/Javadoc_dataenum.java"));

    assertThat(compilation).succeededWithoutWarnings();
    assertThat(compilation)
        .generatedSourceFile("javadoc.Javadoc")
        .contentsAsUtf8String()
        .contains("   * @return a {@link Value} (see {@link Javadoc_dataenum#Value} for source)");
  }

  @Test
  public void shouldCopyDocFromCaseSourceToJavadocCommentOnFactoryMethod() {
    Compilation compilation =
        javac()
            .withOptions("-implicit:class")
            .withProcessors(new DataEnumProcessor())
            .compile(JavaFileObjects.forResource("javadoc/Javadoc_dataenum.java"));

    assertThat(compilation).succeededWithoutWarnings();
    assertThat(compilation)
        .generatedSourceFile("javadoc.Javadoc")
        .contentsAsUtf8String()
        .contains(
            "/**\n"
                + "   * Some documentation about this case.\n"
                + "   *\n"
                + "   * @return a {@link Documented} (see {@link Javadoc_dataenum#Documented} for source)\n"
                + "   */\n");
  }

  @Test
  public void shouldCopyAnnotationsFromCaseSourceToFactoryMethod() {
    assertThatEnumGeneratedMatchingFile("annotation/Annotation", "annotation/MyAnnotation.java");
  }

  @Test
  public void superInterfaces() {
    assertThatEnumGeneratedMatchingFile("SuperInterfaces");
  }

}
