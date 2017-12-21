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
import static com.spotify.dataenum.DataenumUtils.checkNotNull;


import com.spotify.dataenum.function.Consumer;
import com.spotify.dataenum.function.Function;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.List;
import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.spotify.dataenum.processor.DataEnumProcessor")
public abstract class InnerGenericValue<T> {
  private InnerGenericValue() {
  }

  public static <T> InnerGenericValue<T> many(@Nonnull List<T> values) {
    return new Many<T>(values).asInnerGenericValue();
  }

  public static <T> InnerGenericValue<T> one(@Nonnull T value) {
    return new One<T>(value).asInnerGenericValue();
  }

  public static <T> InnerGenericValue<T> none() {
    return new None().asInnerGenericValue();
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

  public static final class Many<T> extends InnerGenericValue<T> {
    private final List<T> values;

    private Many(List<T> values) {
      this.values = checkNotNull(values);
    }

    @Nonnull
    public final List<T> values() {
      return values;
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) return true;
      if (!(other instanceof Many)) return false;
      Many<?> o = (Many<?>) other;
      return o.values.equals(this.values);
    }

    @Override
    public int hashCode() {
      int result = 0;
      result = result * 31 + values.hashCode();
      return result;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Many{values=").append(values);
      return builder.append('}').toString();
    }

    @Override
    public final void match(@Nonnull Consumer<Many<T>> many, @Nonnull Consumer<One<T>> one,
        @Nonnull Consumer<None> none) {
      many.accept(this);
    }

    @Override
    public final <R_> R_ map(@Nonnull Function<Many<T>, R_> many,
        @Nonnull Function<One<T>, R_> one, @Nonnull Function<None, R_> none) {
      return many.apply(this);
    }

    public final InnerGenericValue<T> asInnerGenericValue() {
      return (InnerGenericValue<T>) this;
    }
  }

  public static final class One<T> extends InnerGenericValue<T> {
    private final T value;

    private One(T value) {
      this.value = checkNotNull(value);
    }

    @Nonnull
    public final T value() {
      return value;
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) return true;
      if (!(other instanceof One)) return false;
      One<?> o = (One<?>) other;
      return o.value.equals(this.value);
    }

    @Override
    public int hashCode() {
      int result = 0;
      result = result * 31 + value.hashCode();
      return result;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("One{value=").append(value);
      return builder.append('}').toString();
    }

    @Override
    public final void match(@Nonnull Consumer<Many<T>> many, @Nonnull Consumer<One<T>> one,
        @Nonnull Consumer<None> none) {
      one.accept(this);
    }

    @Override
    public final <R_> R_ map(@Nonnull Function<Many<T>, R_> many,
        @Nonnull Function<One<T>, R_> one, @Nonnull Function<None, R_> none) {
      return one.apply(this);
    }

    public final InnerGenericValue<T> asInnerGenericValue() {
      return (InnerGenericValue<T>) this;
    }
  }

  public static final class None extends InnerGenericValue<Object> {
    private None() {
    }

    @Override
    public boolean equals(Object other) {
      return other instanceof None;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    @Override
    public String toString() {
      return "None{}";
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

    @SuppressWarnings("unchecked")
    public final <T> InnerGenericValue<T> asInnerGenericValue() {
      return (InnerGenericValue<T>) this;
    }
  }
}
