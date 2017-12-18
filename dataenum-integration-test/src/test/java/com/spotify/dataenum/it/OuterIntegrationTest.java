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
package com.spotify.dataenum.it;

import static com.spotify.dataenum.function.Cases.illegal;
import static com.spotify.dataenum.function.Cases.todo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.spotify.dataenum.it.Testing.Three;
import com.spotify.dataenum.it.Testing.Two;
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
    String s = t.map(one -> String.valueOf(one.i()), Two::s, Three::s);

    // use s to ensure this test class has zero warnings
    assertThat(s).isEqualTo("1");
  }

  @Test
  public void shouldAllowConvenientTodoForMap() throws Exception {
    assertThatThrownBy(() -> t.map(one -> todo(), Two::s, Three::s))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessageContaining("TODO");
  }

  @Test
  public void shouldAllowConvenientTodoForMatch() throws Exception {
    assertThatThrownBy(() -> t.match(one -> todo(), two -> {}, three -> {}))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessageContaining("TODO");
  }

  @Test
  public void shouldAllowConvenientIllegalStateForMap() throws Exception {
    assertThatThrownBy(() -> t.map(one -> illegal("nono: " + one.i()), Two::s, Three::s))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("nono: 1");
  }

  @Test
  public void shouldAllowConvenientIllegalStateForMatch() throws Exception {
    assertThatThrownBy(
            () ->
                t.match(
                    one -> illegal("nope, should be: " + (one.i() + 1)), two -> {}, three -> {}))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("nope, should be: 2");
  }
}
