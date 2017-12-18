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
import java.lang.SafeVarargs;
import java.lang.String;
import java.lang.StringBuilder;
import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.spotify.dataenum.processor.DataEnumProcessor")
public abstract class GenericVarargValue<T> {
  private GenericVarargValue() {
  }

  @SafeVarargs
  public static <T> GenericVarargValue<T> value(@Nonnull T... values) {
    return new Value<T>(values).asGenericVarargValue();
  }

  public final boolean isValue() {
    return (this instanceof Value);
  }

  public final Value<T> asValue() {
    return (Value<T>) this;
  }

  public abstract void match(@Nonnull Consumer<Value<T>> value);

  public abstract <R_> R_ map(@Nonnull Function<Value<T>, R_> value);

  public static final class Value<T> extends GenericVarargValue<T> {
    private final T[] values;

    private Value(T[] values) {
      this.values = checkNotNull(values);
    }

    @Nonnull
    public final T[] values() {
      return values;
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) return true;
      if (!(other instanceof Value)) return false;
      Value o = (Value) other;
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
      builder.append("Value{values=").append(values);
      return builder.append('}').toString();
    }

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
