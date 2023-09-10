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
import java.io.Serializable;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import javax.annotation.Generated;
import javax.annotation.Nonnull;

/**
 * Generated from {@link SuperInterfaces_dataenum}
 */
@Generated("com.spotify.dataenum.processor.DataEnumProcessor")
public abstract class SuperInterfaces implements Serializable {
  SuperInterfaces() {
  }

  /**
   * @return a {@link Value1} (see {@link SuperInterfaces_dataenum#Value1} for source)
   */
  public static SuperInterfaces value1(@Nonnull String msg) {
    return new Value1(msg);
  }

  /**
   * @return a {@link Value2} (see {@link SuperInterfaces_dataenum#Value2} for source)
   */
  public static SuperInterfaces value2(@Nonnull String msg, int code) {
    return new Value2(msg, code);
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

  public static final class Value1 extends SuperInterfaces {
    private final String msg;

    Value1(String msg) {
      this.msg = checkNotNull(msg);
    }

    @Nonnull
    public final String msg() {
      return msg;
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) return true;
      if (!(other instanceof Value1)) return false;
      Value1 o = (Value1) other;
      return o.msg.equals(this.msg);
    }

    @Override
    public int hashCode() {
      int result = 0;
      return result * 31 + this.msg.hashCode();
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Value1{msg=").append(this.msg);
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

  public static final class Value2 extends SuperInterfaces {
    private final String msg;

    private final int code;

    Value2(String msg, int code) {
      this.msg = checkNotNull(msg);
      this.code = code;
    }

    @Nonnull
    public final String msg() {
      return msg;
    }

    public final int code() {
      return code;
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) return true;
      if (!(other instanceof Value2)) return false;
      Value2 o = (Value2) other;
      return o.code == code
              && o.msg.equals(this.msg);
    }

    @Override
    public int hashCode() {
      int result = 0;
      result = result * 31 + this.msg.hashCode();
      return result * 31 + Integer.valueOf(this.code).hashCode();
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Value2{msg=").append(this.msg);
      builder.append(", code=").append(this.code);
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
