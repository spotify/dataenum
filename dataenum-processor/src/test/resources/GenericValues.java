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
import java.lang.Throwable;
import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.spotify.dataenum.processor.DataEnumProcessor")
public abstract class GenericValues<L, R extends Throwable> {
  private GenericValues() {
  }

  public static <L, R extends Throwable> GenericValues<L, R> left(@Nonnull L other) {
    return Left.create(other).asGenericValues();
  }

  public static <L, R extends Throwable> GenericValues<L, R> right(@Nonnull R error) {
    return Right.create(error).asGenericValues();
  }

  public final boolean isLeft() {
    return (this instanceof Left);
  }

  public final boolean isRight() {
    return (this instanceof Right);
  }

  public final Left<L> asLeft() {
    return (Left<L>) this;
  }

  public final Right<R> asRight() {
    return (Right<R>) this;
  }

  public abstract void match(@Nonnull Consumer<Left<L>> left, @Nonnull Consumer<Right<R>> right);

  public abstract <R_> R_ map(@Nonnull Function<Left<L>, R_> left,
                              @Nonnull Function<Right<R>, R_> right);

  @AutoValue
  public abstract static class Left<L> extends GenericValues<L, Throwable> {
    Left() {
    }

    private static <L> Left<L> create(L other) {
      return new AutoValue_GenericValues_Left(other);
    }

    @Nonnull
    public abstract L other();

    @Override
    public final void match(@Nonnull Consumer<Left<L>> left,
                            @Nonnull Consumer<Right<Throwable>> right) {
      left.accept(this);
    }

    @Override
    public final <R_> R_ map(@Nonnull Function<Left<L>, R_> left,
                             @Nonnull Function<Right<Throwable>, R_> right) {
      return left.apply(this);
    }

    public final <R extends Throwable> GenericValues<L, R> asGenericValues() {
      return (GenericValues<L, R>) this;
    }
  }

  @AutoValue
  public abstract static class Right<R extends Throwable> extends GenericValues<Object, R> {
    Right() {
    }

    private static <R extends Throwable> Right<R> create(R error) {
      return new AutoValue_GenericValues_Right(error);
    }

    @Nonnull
    public abstract R error();

    @Override
    public final void match(@Nonnull Consumer<Left<Object>> left,
                            @Nonnull Consumer<Right<R>> right) {
      right.accept(this);
    }

    @Override
    public final <R_> R_ map(@Nonnull Function<Left<Object>, R_> left,
                             @Nonnull Function<Right<R>, R_> right) {
      return right.apply(this);
    }

    public final <L> GenericValues<L, R> asGenericValues() {
      return (GenericValues<L, R>) this;
    }
  }
}
