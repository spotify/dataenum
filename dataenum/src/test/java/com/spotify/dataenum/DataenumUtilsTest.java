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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Collections;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DataenumUtilsTest {

  @Rule public ExpectedException thrown = ExpectedException.none();

  @Test
  public void checkNotNullShouldThrowForNull() throws Exception {

    thrown.expect(NullPointerException.class);
    DataenumUtils.checkNotNull(null);
  }

  @Test
  public void checkNotNullShouldReturnInputOnNonNull() throws Exception {
    Object expected = new Object();

    assertThat(DataenumUtils.checkNotNull(expected), is(expected));
  }

  @Test
  public void equalsShouldSupportReferenceIdentity() throws Exception {
    Object o = new Object();

    assertThat(DataenumUtils.equal(o, o), is(true));
  }

  @Test
  public void equalsShouldSupportNulls() throws Exception {
    assertThat(DataenumUtils.equal(null, new Object()), is(false));
    assertThat(DataenumUtils.equal(new Object(), null), is(false));
    assertThat(DataenumUtils.equal(null, null), is(true));
  }

  @Test
  public void equalsShouldFallbackToEquals() throws Exception {
    List<Integer> one = Collections.singletonList(1);
    List<Integer> two = Collections.singletonList(1);

    assertThat(DataenumUtils.equal(one, two), is(true));
  }
}
