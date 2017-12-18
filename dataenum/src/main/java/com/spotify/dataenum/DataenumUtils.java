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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Utilities copied from Guava in order to avoid depending directly on it. */
public final class DataenumUtils {
  private DataenumUtils() {}

  @Nonnull
  public static <T> T checkNotNull(T value) {
    if (value == null) {
      throw new NullPointerException();
    }
    return value;
  }

  public static boolean equal(@Nullable Object a, @Nullable Object b) {
    return a == b || (a != null && a.equals(b));
  }
}
