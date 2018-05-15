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
package com.spotify.dataenum.processor.generator.data;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.spotify.dataenum.processor.parser.ParserException;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.Test;

public class OutputSpecFactoryTest {

  @Test
  public void shouldRemoveDataEnumInterfaceSuffix() throws Exception {
    assertThat(
        OutputSpecFactory.toOutputClass((ClassName) ClassName.get("com.spotify", "My_dataenum")),
        is(ClassName.get("com.spotify", "My")));
  }

  @Test
  public void shouldThrowForNonDataenumClassName() throws Exception {
    assertThatThrownBy(
            new ThrowingCallable() {
              @Override
              public void call() throws Throwable {
                OutputSpecFactory.toOutputClass((ClassName) ClassName.get("com.spotify", "My"));
              }
            })
        .isInstanceOf(ParserException.class)
        .hasMessageContaining("Bad name");
  }

  @Test
  public void shouldThrowForNonClassName() throws Exception {
    assertThatThrownBy(
            new ThrowingCallable() {
              @Override
              public void call() throws Throwable {
                OutputSpecFactory.toOutputClass((ClassName) TypeName.BOOLEAN);
              }
            })
        .isInstanceOf(ClassCastException.class);
  }
}
