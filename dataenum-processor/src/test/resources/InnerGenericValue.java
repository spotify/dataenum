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
import com.google.auto.value.AutoValue;
import com.spotify.dataenum.function.Consumer;
import com.spotify.dataenum.function.Function;
import java.lang.Object;
import java.lang.Override;
import java.util.List;
import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.spotify.dataenum.processor.DataEnumProcessor")
public abstract class InnerGenericValue<T> {
  private InnerGenericValue() {
  }

  public static <T> InnerGenericValue<T> many(@Nonnull List<T> values) {
    return Many.create(values).asInnerGenericValue();
  }

  public static <T> InnerGenericValue<T> one(@Nonnull T value) {
    return One.create(value).asInnerGenericValue();
  }

  public static <T> InnerGenericValue<T> none() {
    return None.create().asInnerGenericValue();
  }

  public final boolean isMany() {
    return (this instanceof Many);
  }

  public final boolean isOne() {
    return (this instanceof One);
  }

  public final boolean isNone() {
    return (this instanceof None);
  }

  public final Many<T> asMany() {
    return (Many<T>) this;
  }

  public final One<T> asOne() {
    return (One<T>) this;
  }

  public final None asNone() {
    return (None) this;
  }

  public abstract void match(@Nonnull Consumer<Many<T>> many, @Nonnull Consumer<One<T>> one,
                             @Nonnull Consumer<None> none);

  public abstract <R_> R_ map(@Nonnull Function<Many<T>, R_> many,
                              @Nonnull Function<One<T>, R_> one, @Nonnull Function<None, R_> none);

  @AutoValue
  public abstract static class Many<T> extends InnerGenericValue<T> {
    Many() {
    }

    private static <T> Many<T> create(List<T> values) {
      return new AutoValue_InnerGenericValue_Many(values);
    }

    @Nonnull
    public abstract List<T> values();

    @Override
    public final void match(@Nonnull Consumer<Many<T>> many, @Nonnull Consumer<One<T>> one,
                            @Nonnull Consumer<None> none) {
      many.accept(this);
    }

    @Override
    public final <R_> R_ map(@Nonnull Function<Many<T>, R_> many, @Nonnull Function<One<T>, R_> one,
                             @Nonnull Function<None, R_> none) {
      return many.apply(this);
    }

    public final InnerGenericValue<T> asInnerGenericValue() {
      return (InnerGenericValue<T>) this;
    }
  }

  @AutoValue
  public abstract static class One<T> extends InnerGenericValue<T> {
    One() {
    }

    private static <T> One<T> create(T value) {
      return new AutoValue_InnerGenericValue_One(value);
    }

    @Nonnull
    public abstract T value();

    @Override
    public final void match(@Nonnull Consumer<Many<T>> many, @Nonnull Consumer<One<T>> one,
                            @Nonnull Consumer<None> none) {
      one.accept(this);
    }

    @Override
    public final <R_> R_ map(@Nonnull Function<Many<T>, R_> many, @Nonnull Function<One<T>, R_> one,
                             @Nonnull Function<None, R_> none) {
      return one.apply(this);
    }

    public final InnerGenericValue<T> asInnerGenericValue() {
      return (InnerGenericValue<T>) this;
    }
  }

  @AutoValue
  public abstract static class None extends InnerGenericValue<Object> {
    None() {
    }

    private static None create() {
      return new AutoValue_InnerGenericValue_None();
    }

    @Override
    public final void match(@Nonnull Consumer<Many<Object>> many,
                            @Nonnull Consumer<One<Object>> one, @Nonnull Consumer<None> none) {
      none.accept(this);
    }

    @Override
    public final <R_> R_ map(@Nonnull Function<Many<Object>, R_> many,
                             @Nonnull Function<One<Object>, R_> one, @Nonnull Function<None, R_> none) {
      return none.apply(this);
    }

    public final <T> InnerGenericValue<T> asInnerGenericValue() {
      return (InnerGenericValue<T>) this;
    }
  }
}
