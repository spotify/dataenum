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
package annotation;

import com.spotify.dataenum.function.Consumer;
import com.spotify.dataenum.function.Function;
import java.lang.Deprecated;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.spotify.dataenum.processor.DataEnumProcessor")
public abstract class Annotation {
  Annotation() {
  }

  @SuppressWarnings({"floopity", "floop"})
  @Deprecated
  @MyAnnotation(foo = "hi", fie = 15)
  public static Annotation annotatedWithParams() {
    return new AnnotatedWithParams();
  }

  public final boolean isAnnotatedWithParams() {
    return (this instanceof AnnotatedWithParams);
  }

  public final AnnotatedWithParams asAnnotatedWithParams() {
    return (AnnotatedWithParams) this;
  }

  public abstract void match(@Nonnull Consumer<AnnotatedWithParams> annotatedWithParams);

  public abstract <R_> R_ map(@Nonnull Function<AnnotatedWithParams, R_> annotatedWithParams);

  public static final class AnnotatedWithParams extends Annotation {
    AnnotatedWithParams() {
    }

    @Override
    public boolean equals(Object other) {
      return other instanceof AnnotatedWithParams;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    @Override
    public String toString() {
      return "AnnotatedWithParams{}";
    }

    @Override
    public final void match(@Nonnull Consumer<AnnotatedWithParams> annotatedWithParams) {
      annotatedWithParams.accept(this);
    }

    @Override
    public final <R_> R_ map(@Nonnull Function<AnnotatedWithParams, R_> annotatedWithParams) {
      return annotatedWithParams.apply(this);
    }
  }
}
