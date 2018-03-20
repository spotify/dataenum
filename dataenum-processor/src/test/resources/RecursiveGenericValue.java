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
import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.spotify.dataenum.processor.DataEnumProcessor")
public abstract class RecursiveGenericValue<L, R> {
  RecursiveGenericValue() {
  }

  public static <L, R> RecursiveGenericValue<L, R> branch(@Nonnull RecursiveGenericValue<L, R> left,
      @Nonnull RecursiveGenericValue<L, R> right) {
    return new Branch<L, R>(left, right).asRecursiveGenericValue();
  }

  public static <L, R> RecursiveGenericValue<L, R> left(@Nonnull L value) {
    return new Left<L>(value).asRecursiveGenericValue();
  }

  public static <L, R> RecursiveGenericValue<L, R> right(@Nonnull R value) {
    return new Right<R>(value).asRecursiveGenericValue();
  }

  public final boolean isBranch() {
    return (this instanceof Branch);
  }

  public final boolean isLeft() {
    return (this instanceof Left);
  }

  public final boolean isRight() {
    return (this instanceof Right);
  }

  public final Branch<L, R> asBranch() {
    return (Branch<L, R>) this;
  }

  @SuppressWarnings("unchecked")
  public final Left<L> asLeft() {
    return (Left<L>) this;
  }

  @SuppressWarnings("unchecked")
  public final Right<R> asRight() {
    return (Right<R>) this;
  }

  public abstract void match(@Nonnull Consumer<Branch<L, R>> branch,
      @Nonnull Consumer<Left<L>> left, @Nonnull Consumer<Right<R>> right);

  public abstract <R_> R_ map(@Nonnull Function<Branch<L, R>, R_> branch,
      @Nonnull Function<Left<L>, R_> left, @Nonnull Function<Right<R>, R_> right);

  public static final class Branch<L, R> extends RecursiveGenericValue<L, R> {
    private final RecursiveGenericValue<L, R> left;

    private final RecursiveGenericValue<L, R> right;

    Branch(RecursiveGenericValue<L, R> left, RecursiveGenericValue<L, R> right) {
      this.left = checkNotNull(left);
      this.right = checkNotNull(right);
    }

    @Nonnull
    public final RecursiveGenericValue<L, R> left() {
      return left;
    }

    @Nonnull
    public final RecursiveGenericValue<L, R> right() {
      return right;
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) return true;
      if (!(other instanceof Branch)) return false;
      Branch<?, ?> o = (Branch<?, ?>) other;
      return o.left.equals(this.left)
          && o.right.equals(this.right);
    }

    @Override
    public int hashCode() {
      int result = 0;
      result = result * 31 + left.hashCode();
      result = result * 31 + right.hashCode();
      return result;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Branch{left=").append(left);
      builder.append(", right=").append(right);
      return builder.append('}').toString();
    }

    @Override
    public final void match(@Nonnull Consumer<Branch<L, R>> branch,
        @Nonnull Consumer<Left<L>> left, @Nonnull Consumer<Right<R>> right) {
      branch.accept(this);
    }

    @Override
    public final <R_> R_ map(@Nonnull Function<Branch<L, R>, R_> branch,
        @Nonnull Function<Left<L>, R_> left, @Nonnull Function<Right<R>, R_> right) {
      return branch.apply(this);
    }

    public final RecursiveGenericValue<L, R> asRecursiveGenericValue() {
      return (RecursiveGenericValue<L, R>) this;
    }
  }

  public static final class Left<L> extends RecursiveGenericValue<L, Object> {
    private final L value;

    Left(L value) {
      this.value = checkNotNull(value);
    }

    @Nonnull
    public final L value() {
      return value;
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) return true;
      if (!(other instanceof Left)) return false;
      Left<?> o = (Left<?>) other;
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
      builder.append("Left{value=").append(value);
      return builder.append('}').toString();
    }

    @Override
    public final void match(@Nonnull Consumer<Branch<L, Object>> branch,
        @Nonnull Consumer<Left<L>> left, @Nonnull Consumer<Right<Object>> right) {
      left.accept(this);
    }

    @Override
    public final <R_> R_ map(@Nonnull Function<Branch<L, Object>, R_> branch,
        @Nonnull Function<Left<L>, R_> left, @Nonnull Function<Right<Object>, R_> right) {
      return left.apply(this);
    }

    @SuppressWarnings("unchecked")
    public final <R> RecursiveGenericValue<L, R> asRecursiveGenericValue() {
      return (RecursiveGenericValue<L, R>) this;
    }
  }

  public static final class Right<R> extends RecursiveGenericValue<Object, R> {
    private final R value;

    Right(R value) {
      this.value = checkNotNull(value);
    }

    @Nonnull
    public final R value() {
      return value;
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) return true;
      if (!(other instanceof Right)) return false;
      Right<?> o = (Right<?>) other;
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
      builder.append("Right{value=").append(value);
      return builder.append('}').toString();
    }

    @Override
    public final void match(@Nonnull Consumer<Branch<Object, R>> branch,
        @Nonnull Consumer<Left<Object>> left, @Nonnull Consumer<Right<R>> right) {
      right.accept(this);
    }

    @Override
    public final <R_> R_ map(@Nonnull Function<Branch<Object, R>, R_> branch,
        @Nonnull Function<Left<Object>, R_> left, @Nonnull Function<Right<R>, R_> right) {
      return right.apply(this);
    }

    @SuppressWarnings("unchecked")
    public final <L> RecursiveGenericValue<L, R> asRecursiveGenericValue() {
      return (RecursiveGenericValue<L, R>) this;
    }
  }
}
