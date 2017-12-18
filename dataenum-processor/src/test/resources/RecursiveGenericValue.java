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
import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.spotify.dataenum.processor.DataEnumProcessor")
public abstract class RecursiveGenericValue<L, R> {
  private RecursiveGenericValue() {
  }

  public static <L, R> RecursiveGenericValue<L, R> branch(@Nonnull RecursiveGenericValue<L, R> left,
                                                          @Nonnull RecursiveGenericValue<L, R> right) {
    return Branch.create(left, right).asRecursiveGenericValue();
  }

  public static <L, R> RecursiveGenericValue<L, R> left(@Nonnull L value) {
    return Left.create(value).asRecursiveGenericValue();
  }

  public static <L, R> RecursiveGenericValue<L, R> right(@Nonnull R value) {
    return Right.create(value).asRecursiveGenericValue();
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

  public final Left<L> asLeft() {
    return (Left<L>) this;
  }

  public final Right<R> asRight() {
    return (Right<R>) this;
  }

  public abstract void match(@Nonnull Consumer<Branch<L, R>> branch,
                             @Nonnull Consumer<Left<L>> left, @Nonnull Consumer<Right<R>> right);

  public abstract <R_> R_ map(@Nonnull Function<Branch<L, R>, R_> branch,
                              @Nonnull Function<Left<L>, R_> left, @Nonnull Function<Right<R>, R_> right);

  @AutoValue
  public abstract static class Branch<L, R> extends RecursiveGenericValue<L, R> {
    Branch() {
    }

    private static <L, R> Branch<L, R> create(RecursiveGenericValue<L, R> left,
                                              RecursiveGenericValue<L, R> right) {
      return new AutoValue_RecursiveGenericValue_Branch(left, right);
    }

    @Nonnull
    public abstract RecursiveGenericValue<L, R> left();

    @Nonnull
    public abstract RecursiveGenericValue<L, R> right();

    @Override
    public final void match(@Nonnull Consumer<Branch<L, R>> branch, @Nonnull Consumer<Left<L>> left,
                            @Nonnull Consumer<Right<R>> right) {
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

  @AutoValue
  public abstract static class Left<L> extends RecursiveGenericValue<L, Object> {
    Left() {
    }

    private static <L> Left<L> create(L value) {
      return new AutoValue_RecursiveGenericValue_Left(value);
    }

    @Nonnull
    public abstract L value();

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

    public final <R> RecursiveGenericValue<L, R> asRecursiveGenericValue() {
      return (RecursiveGenericValue<L, R>) this;
    }
  }

  @AutoValue
  public abstract static class Right<R> extends RecursiveGenericValue<Object, R> {
    Right() {
    }

    private static <R> Right<R> create(R value) {
      return new AutoValue_RecursiveGenericValue_Right(value);
    }

    @Nonnull
    public abstract R value();

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

    public final <L> RecursiveGenericValue<L, R> asRecursiveGenericValue() {
      return (RecursiveGenericValue<L, R>) this;
    }
  }
}
