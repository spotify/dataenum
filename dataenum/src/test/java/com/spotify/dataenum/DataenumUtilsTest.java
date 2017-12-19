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
package com.spotify.dataenum;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collections;
import java.util.List;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.Test;

public class DataenumUtilsTest {

  @Test
  public void checkNotNullShouldThrowForNull() throws Exception {
    assertThatThrownBy(
            new ThrowingCallable() {
              @Override
              public void call() throws Throwable {
                DataenumUtils.checkNotNull(null);
              }
            })
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  public void checkNotNullShouldReturnInputOnNonNull() throws Exception {
    Object expected = new Object();

    assertThat(DataenumUtils.checkNotNull(expected)).isEqualTo(expected);
  }

  @Test
  public void equalsShouldSupportReferenceIdentity() throws Exception {
    Object o = new Object();

    assertThat(DataenumUtils.equal(o, o)).isTrue();
  }

  @Test
  public void equalsShouldSupportNulls() throws Exception {
    assertThat(DataenumUtils.equal(null, new Object())).isFalse();
    assertThat(DataenumUtils.equal(new Object(), null)).isFalse();
    assertThat(DataenumUtils.equal(null, null)).isTrue();
  }

  @Test
  public void equalsShouldFallbackToEquals() throws Exception {
    List<Integer> one = Collections.singletonList(1);
    List<Integer> two = Collections.singletonList(1);

    assertThat(DataenumUtils.equal(one, two)).isTrue();
  }
}
