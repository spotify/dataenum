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
import java.lang.Boolean;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.util.Arrays;
import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.spotify.dataenum.processor.DataEnumProcessor")
public abstract class Redacted {
  Redacted() {
  }

  public static Redacted value(@Nonnull int[] param1, boolean param2) {
    return new Value(param1, param2);
  }

  public final boolean isValue() {
    return (this instanceof Value);
  }

  public final Value asValue() {
    return (Value) this;
  }

  public abstract void match(@Nonnull Consumer<Value> value);

  public abstract <R_> R_ map(@Nonnull Function<Value, R_> value);

  public static final class Value extends Redacted {
    private final int[] param1;

    private final boolean param2;

    Value(int[] param1, boolean param2) {
      this.param1 = checkNotNull(param1);
      this.param2 = param2;
    }

    @Nonnull
    public final int[] param1() {
      return param1;
    }

    public final boolean param2() {
      return param2;
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) return true;
      if (!(other instanceof Value)) return false;
      Value o = (Value) other;
      return o.param2 == param2
          && Arrays.equals(o.param1, param1);
    }

    @Override
    public int hashCode() {
      int result = 0;
      result = result * 31 + Arrays.hashCode(this.param1);
      return result * 31 + Boolean.valueOf(this.param2).hashCode();
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Value{param1=").append("***");
      builder.append(", param2=").append("***");
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
