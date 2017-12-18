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
package com.spotify.dataenum.function;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.Test;

public class CasesTest {

  @Test
  public void shouldThrowIllegalStateExceptionForIllegal() throws Exception {
    assertThatThrownBy(
            new ThrowingCallable() {
              @Override
              public void call() throws Throwable {
                Cases.illegal("don't do this dude");
              }
            })
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("don't do this dude");
  }

  @Test
  public void shouldThrowUnsupportedOperationForTodo() throws Exception {
    assertThatThrownBy(() -> Cases.todo()).isInstanceOf(UnsupportedOperationException.class);
  }
}
