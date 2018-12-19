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
public abstract class MultipleValues {
  MultipleValues() {
  }

  public static MultipleValues value1(int param1, boolean param2) {
    return new Value1(param1, param2);
  }

  public static MultipleValues value2(int param1, boolean param2) {
    return new Value2(param1, param2);
  }

  public final boolean isValue1() {
    return (this instanceof Value1);
  }

  public final boolean isValue2() {
    return (this instanceof Value2);
  }

  public final Value1 asValue1() {
    return (Value1) this;
  }

  public final Value2 asValue2() {
    return (Value2) this;
  }

  public abstract void match(@Nonnull Consumer<Value1> value1, @Nonnull Consumer<Value2> value2);

  public abstract <R_> R_ map(@Nonnull Function<Value1, R_> value1,
      @Nonnull Function<Value2, R_> value2);

  public static final class Value1 extends MultipleValues {
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
      int result = 0;
      result = result * 31 + Integer.valueOf(this.param1).hashCode();
      return result * 31 + Boolean.valueOf(this.param2).hashCode();
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Value1{param1=").append(this.param1);
      builder.append(", param2=").append(this.param2);
      return builder.append('}').toString();
    }

    @Override
    public final void match(@Nonnull Consumer<Value1> value1, @Nonnull Consumer<Value2> value2) {
      value1.accept(this);
    }

    @Override
    public final <R_> R_ map(@Nonnull Function<Value1, R_> value1,
        @Nonnull Function<Value2, R_> value2) {
      return value1.apply(this);
    }
  }

  public static final class Value2 extends MultipleValues {
    private final int param1;

    private final boolean param2;

    Value2(int param1, boolean param2) {
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
      if (!(other instanceof Value2)) return false;
      Value2 o = (Value2) other;
      return o.param1 == param1
          && o.param2 == param2;
    }

    @Override
    public int hashCode() {
      int result = 0;
      result = result * 31 + Integer.valueOf(this.param1).hashCode();
      return result * 31 + Boolean.valueOf(this.param2).hashCode();
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Value2{param1=").append(this.param1);
      builder.append(", param2=").append(this.param2);
      return builder.append('}').toString();
    }

    @Override
    public final void match(@Nonnull Consumer<Value1> value1, @Nonnull Consumer<Value2> value2) {
      value2.accept(this);
    }

    @Override
    public final <R_> R_ map(@Nonnull Function<Value1, R_> value1,
        @Nonnull Function<Value2, R_> value2) {
      return value2.apply(this);
    }
  }
}
