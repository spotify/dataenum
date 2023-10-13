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
package com.spotify.dataenum.integration;

import static com.spotify.dataenum.function.Cases.illegal;
import static com.spotify.dataenum.function.Cases.todo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.spotify.dataenum.integration.Testing.RedactedValue;
import com.spotify.dataenum.integration.Testing.Three;
import com.spotify.dataenum.integration.Testing.Two;
import org.junit.Before;
import org.junit.Test;

public class OuterIntegrationTest {

  private Testing t;

  @Before
  public void setUp() throws Exception {
    t = Testing.one(1);
  }

  @Test
  public void shouldAllowMapping() throws Exception {
    // should compile and not show warnings
    String s = t.map(one -> String.valueOf(one.i()), Two::s, Three::s, RedactedValue::shouldShow);

    // use s to ensure this test class has zero warnings
    assertThat(s).isEqualTo("1");
  }

  @Test
  public void shouldAllowConvenientTodoForMap() throws Exception {
    assertThatThrownBy(() -> t.map(one -> todo(), Two::s, Three::s, RedactedValue::shouldShow))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessageContaining("TODO");
  }

  @Test
  public void shouldAllowConvenientTodoForMatch() throws Exception {
    assertThatThrownBy(() -> t.match(one -> todo(), two -> {}, three -> {}, value -> {}))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessageContaining("TODO");
  }

  @Test
  public void shouldAllowConvenientIllegalStateForMap() throws Exception {
    assertThatThrownBy(
            () ->
                t.map(
                    one -> illegal("nono: " + one.i()),
                    Two::s,
                    Three::s,
                    RedactedValue::shouldShow))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("nono: 1");
  }

  @Test
  public void shouldAllowConvenientIllegalStateForMatch() throws Exception {
    assertThatThrownBy(
            () ->
                t.match(
                    one -> illegal("nope, should be: " + (one.i() + 1)),
                    two -> {},
                    three -> {},
                    value -> {}))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("nope, should be: 2");
  }

  @Test
  public void shouldRedactAnnotatedFieldsInToString() {
    assertThat(Testing.redactedValue("i want to see this", 866).toString())
        .contains("i want to see this")
        .doesNotContain("866");
  }

  @Test
  public void shouldSupportAnyRedactedAnnotation() {
    assertThat(RedactedTesting.redactMe("hide this").toString()).doesNotContain("hide this");
  }
}
