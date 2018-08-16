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
package just.some.pkg;

import com.spotify.dataenum.function.Consumer;
import com.spotify.dataenum.function.Function;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.spotify.dataenum.processor.DataEnumProcessor")
public abstract class InPackage {
  InPackage() {
  }

  public static InPackage value1(int param1, boolean param2) {
    return new Value1(param1, param2);
  }

  public final boolean isValue1() {
    return (this instanceof Value1);
  }

  public final Value1 asValue1() {
    return (Value1) this;
  }

  public abstract void match(@Nonnull Consumer<Value1> value1);

  public abstract <R_> R_ map(@Nonnull Function<Value1, R_> value1);

  public static final class Value1 extends InPackage {
    private final int param1;

    private final boolean param2;

    Value1(int param1, boolean param2) {
      this.param1 = param1;
      this.param2 = param2;
    }

    public final int param1() {
      return param1;
    }

    public final boolean param2() {
      return param2;
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) return true;
      if (!(other instanceof Value1)) return false;
      Value1 o = (Value1) other;
      return o.param1 == param1
          && o.param2 == param2;
    }

    @Override
    public int hashCode() {
      int _hash_result = 0;
      _hash_result = _hash_result * 31 + Integer.valueOf(param1).hashCode();
      return _hash_result * 31 + Boolean.valueOf(param2).hashCode();
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Value1{param1=").append(param1);
      builder.append(", param2=").append(param2);
      return builder.append('}').toString();
    }

    @Override
    public final void match(@Nonnull Consumer<Value1> value1) {
      value1.accept(this);
    }

    @Override
    public final <R_> R_ map(@Nonnull Function<Value1, R_> value1) {
      return value1.apply(this);
    }
  }
}
