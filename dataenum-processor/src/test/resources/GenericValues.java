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
import java.lang.Throwable;
import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.spotify.dataenum.processor.DataEnumProcessor")
public abstract class GenericValues<L, R extends Throwable> {

  private GenericValues() {
  }

  public static <L, R extends Throwable> GenericValues<L, R> left(@Nonnull L other) {
    return new Left<L>(other).asGenericValues();
  }

  public static <L, R extends Throwable> GenericValues<L, R> right(@Nonnull R error) {
    return new Right<R>(error).asGenericValues();
  }

  public static <L, R extends Throwable> GenericValues<L, R> neither(@Nonnull String s) {
    return new Neither(s).asGenericValues();
  }

  public static <L, R extends Throwable> GenericValues<L, R> both(@Nonnull L one, @Nonnull R two) {
    return new Both<L, R>(one, two).asGenericValues();
  }

  public final boolean isLeft() {
    return (this instanceof Left);
  }

  public final boolean isRight() {
    return (this instanceof Right);
  }

  public final boolean isNeither() {
    return (this instanceof Neither);
  }

  public final boolean isBoth() {
    return (this instanceof Both);
  }

  @SuppressWarnings("unchecked")
  public final Left<L> asLeft() {
    return (Left<L>) this;
  }

  @SuppressWarnings("unchecked")
  public final Right<R> asRight() {
    return (Right<R>) this;
  }

  public final Neither asNeither() {
    return (Neither) this;
  }

  public final Both<L, R> asBoth() {
    return (Both<L, R>) this;
  }

  public abstract void match(
      @Nonnull Consumer<Left<L>> left,
      @Nonnull Consumer<Right<R>> right,
      @Nonnull Consumer<Neither> neither,
      @Nonnull Consumer<Both<L, R>> both);

  public abstract <R_> R_ map(
      @Nonnull Function<Left<L>, R_> left,
      @Nonnull Function<Right<R>, R_> right,
      @Nonnull Function<Neither, R_> neither,
      @Nonnull Function<Both<L, R>, R_> both);

  public static final class Left<L> extends GenericValues<L, Throwable> {

    private final L other;

    private Left(L other) {
      this.other = checkNotNull(other);
    }

    @Nonnull
    public final L other() {
      return other;
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) return true;
      if (!(other instanceof Left)) return false;
      Left<?> o = (Left<?>) other;
      return o.other.equals(this.other);
    }

    @Override
    public int hashCode() {
      int result = 0;
      result = result * 31 + other.hashCode();
      return result;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Left{other=").append(other);
      return builder.append('}').toString();
    }

    @Override
    public final void match(
        @Nonnull Consumer<Left<L>> left,
        @Nonnull Consumer<Right<Throwable>> right,
        @Nonnull Consumer<Neither> neither,
        @Nonnull Consumer<Both<L, Throwable>> both) {
      left.accept(this);
    }

    @Override
    public final <R_> R_ map(
        @Nonnull Function<Left<L>, R_> left,
        @Nonnull Function<Right<Throwable>, R_> right,
        @Nonnull Function<Neither, R_> neither,
        @Nonnull Function<Both<L, Throwable>, R_> both) {
      return left.apply(this);
    }

    @SuppressWarnings("unchecked")
    public final <R extends Throwable> GenericValues<L, R> asGenericValues() {
      return (GenericValues<L, R>) this;
    }
  }

  public static final class Right<R extends Throwable> extends GenericValues<Object, R> {

    private final R error;

    private Right(R error) {
      this.error = checkNotNull(error);
    }

    @Nonnull
    public final R error() {
      return error;
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) return true;
      if (!(other instanceof Right)) return false;
      Right<?> o = (Right<?>) other;
      return o.error.equals(this.error);
    }

    @Override
    public int hashCode() {
      int result = 0;
      result = result * 31 + error.hashCode();
      return result;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Right{error=").append(error);
      return builder.append('}').toString();
    }

    @Override
    public final void match(
        @Nonnull Consumer<Left<Object>> left,
        @Nonnull Consumer<Right<R>> right,
        @Nonnull Consumer<Neither> neither,
        @Nonnull Consumer<Both<Object, R>> both) {
      right.accept(this);
    }

    @Override
    public final <R_> R_ map(
        @Nonnull Function<Left<Object>, R_> left,
        @Nonnull Function<Right<R>, R_> right,
        @Nonnull Function<Neither, R_> neither,
        @Nonnull Function<Both<Object, R>, R_> both) {
      return right.apply(this);
    }

    @SuppressWarnings("unchecked")
    public final <L> GenericValues<L, R> asGenericValues() {
      return (GenericValues<L, R>) this;
    }
  }

  public static final class Neither extends GenericValues<Object, Throwable> {

    private final String s;

    private Neither(String s) {
      this.s = checkNotNull(s);
    }

    @Nonnull
    public final String s() {
      return s;
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) return true;
      if (!(other instanceof Neither)) return false;
      Neither o = (Neither) other;
      return o.s.equals(this.s);
    }

    @Override
    public int hashCode() {
      int result = 0;
      result = result * 31 + s.hashCode();
      return result;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Neither{s=").append(s);
      return builder.append('}').toString();
    }

    @Override
    public final void match(
        @Nonnull Consumer<Left<Object>> left,
        @Nonnull Consumer<Right<Throwable>> right,
        @Nonnull Consumer<Neither> neither,
        @Nonnull Consumer<Both<Object, Throwable>> both) {
      neither.accept(this);
    }

    @Override
    public final <R_> R_ map(
        @Nonnull Function<Left<Object>, R_> left,
        @Nonnull Function<Right<Throwable>, R_> right,
        @Nonnull Function<Neither, R_> neither,
        @Nonnull Function<Both<Object, Throwable>, R_> both) {
      return neither.apply(this);
    }

    @SuppressWarnings("unchecked")
    public final <L, R extends Throwable> GenericValues<L, R> asGenericValues() {
      return (GenericValues<L, R>) this;
    }
  }

  public static final class Both<L, R extends Throwable> extends GenericValues<L, R> {

    private final L one;

    private final R two;

    private Both(L one, R two) {
      this.one = checkNotNull(one);
      this.two = checkNotNull(two);
    }

    @Nonnull
    public final L one() {
      return one;
    }

    @Nonnull
    public final R two() {
      return two;
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) return true;
      if (!(other instanceof Both)) return false;
      Both<?, ?> o = (Both<?, ?>) other;
      return o.one.equals(this.one)
          && o.two.equals(this.two);
    }

    @Override
    public int hashCode() {
      int result = 0;
      result = result * 31 + one.hashCode();
      result = result * 31 + two.hashCode();
      return result;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Both{one=").append(one);
      builder.append(", two=").append(two);
      return builder.append('}').toString();
    }

    @Override
    public final void match(@Nonnull Consumer<Left<L>> left, @Nonnull Consumer<Right<R>> right,
        @Nonnull Consumer<Neither> neither, @Nonnull Consumer<Both<L, R>> both) {
      both.accept(this);
    }

    @Override
    public final <R_> R_ map(@Nonnull Function<Left<L>, R_> left,
        @Nonnull Function<Right<R>, R_> right, @Nonnull Function<Neither, R_> neither,
        @Nonnull Function<Both<L, R>, R_> both) {
      return both.apply(this);
    }

    public final GenericValues<L, R> asGenericValues() {
      return (GenericValues<L, R>) this;
    }
  }
}
