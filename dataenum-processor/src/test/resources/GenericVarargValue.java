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
import java.lang.Override;
import java.lang.SafeVarargs;
import java.lang.SuppressWarnings;
import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.spotify.dataenum.processor.DataEnumProcessor")
public abstract class GenericVarargValue<T> {
  private GenericVarargValue() {
  }

  @SafeVarargs
  public static <T> GenericVarargValue<T> value(@Nonnull T... values) {
    return Value.create(values).asGenericVarargValue();
  }

  public final boolean isValue() {
    return (this instanceof Value);
  }

  public final Value<T> asValue() {
    return (Value<T>) this;
  }

  public abstract void match(@Nonnull Consumer<Value<T>> value);

  public abstract <R_> R_ map(@Nonnull Function<Value<T>, R_> value);

  @AutoValue
  public abstract static class Value<T> extends GenericVarargValue<T> {
    Value() {
    }

    private static <T> Value<T> create(T[] values) {
      return new AutoValue_GenericVarargValue_Value(values);
    }

    @SuppressWarnings("mutable")
    @Nonnull
    public abstract T[] values();

    @Override
    public final void match(@Nonnull Consumer<Value<T>> value) {
      value.accept(this);
    }

    @Override
    public final <R_> R_ map(@Nonnull Function<Value<T>, R_> value) {
      return value.apply(this);
    }

    public final GenericVarargValue<T> asGenericVarargValue() {
      return (GenericVarargValue<T>) this;
    }
  }
}
