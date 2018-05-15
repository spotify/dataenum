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
import static com.spotify.dataenum.DataenumUtils.equal;

import com.spotify.dataenum.function.Consumer;
import com.spotify.dataenum.function.Function;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Generated("com.spotify.dataenum.processor.DataEnumProcessor")
public abstract class NullableValue {
  NullableValue() {
  }

  public static NullableValue value(@Nonnull Object param1, @Nullable Object param2,
      @Nullable Object param3, @Nonnull Object param4, @Nonnull Object param5) {
    return new Value(param1, param2, param3, param4, param5);
  }

  public final boolean isValue() {
    return (this instanceof Value);
  }

  public final Value asValue() {
    return (Value) this;
  }

  public abstract void match(@Nonnull Consumer<Value> value);

  public abstract <R_> R_ map(@Nonnull Function<Value, R_> value);

  public static final class Value extends NullableValue {
    private final Object param1;

    private final Object param2;

    private final Object param3;

    private final Object param4;

    private final Object param5;

    Value(Object param1, Object param2, Object param3, Object param4, Object param5) {
      this.param1 = checkNotNull(param1);
      this.param2 = param2;
      this.param3 = param3;
      this.param4 = checkNotNull(param4);
      this.param5 = checkNotNull(param5);
    }

    @Nonnull
    public final Object param1() {
      return param1;
    }

    @Nullable
    public final Object param2() {
      return param2;
    }

    @Nullable
    public final Object param3() {
      return param3;
    }

    @Nonnull
    public final Object param4() {
      return param4;
    }

    @Nonnull
    public final Object param5() {
      return param5;
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) return true;
      if (!(other instanceof Value)) return false;
      Value o = (Value) other;
      return o.param1.equals(this.param1)
          && equal(o.param2, this.param2)
          && equal(o.param3, this.param3)
          && o.param4.equals(this.param4)
          && o.param5.equals(this.param5);
    }

    @Override
    public int hashCode() {
      int result = 0;
      result = result * 31 + param1.hashCode();
      result = result * 31 + (param2 != null ? param2.hashCode() : 0);
      result = result * 31 + (param3 != null ? param3.hashCode() : 0);
      result = result * 31 + param4.hashCode();
      return result * 31 + param5.hashCode();
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Value{param1=").append(param1);
      builder.append(", param2=").append(param2);
      builder.append(", param3=").append(param3);
      builder.append(", param4=").append(param4);
      builder.append(", param5=").append(param5);
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
