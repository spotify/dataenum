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
import java.util.Arrays;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Generated("com.spotify.dataenum.processor.DataEnumProcessor")
public abstract class ArrayFields {
  ArrayFields() {
  }

  public static ArrayFields value(@Nullable byte[] builder, @Nonnull char[] result, @Nonnull Object[] param3) {
    return new Value(builder, result, param3);
  }

  public final boolean isValue() {
    return (this instanceof Value);
  }

  public final Value asValue() {
    return (Value) this;
  }

  public abstract void match(@Nonnull Consumer<Value> value);

  public abstract <R_> R_ map(@Nonnull Function<Value, R_> value);

  public static final class Value extends ArrayFields {
    private final byte[] builder;

    private final char[] result;

    private final Object[] param3;

    Value(byte[] builder, char[] result, Object[] param3) {
      this.builder = builder;
      this.result = checkNotNull(result);
      this.param3 = checkNotNull(param3);
    }

    @Nullable
    public final byte[] builder() {
      return builder;
    }

    @Nonnull
    public final char[] result() {
      return result;
    }

    @Nonnull
    public final Object[] param3() {
      return param3;
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) return true;
      if (!(other instanceof Value)) return false;
      Value o = (Value) other;
      return Arrays.equals(o.builder, builder)
          && Arrays.equals(o.result, result)
          && Arrays.equals(o.param3, param3);
    }

    @Override
    public int hashCode() {
      int result = 0;
      result = result * 31 + Arrays.hashCode(this.builder);
      result = result * 31 + Arrays.hashCode(this.result);
      return result * 31 + Arrays.hashCode(this.param3);
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Value{builder=").append(Arrays.toString(this.builder));
      builder.append(", result=").append(Arrays.toString(this.result));
      builder.append(", param3=").append(Arrays.toString(this.param3));
      return builder.append('}').toString();
    }

    @Override
    public final void match(@Nonnull Consumer<Value> value) {
      value.accept(this);
    }

    @Override
    public final <R_> R_ map(@Nonnull Function<Value, R_> value) {
      return value.apply(this);
    }
  }
}
