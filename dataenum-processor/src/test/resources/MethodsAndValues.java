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
import javax.annotation.Generated;
import javax.annotation.Nonnull;

/**
 * Generated from {@link MethodsAndValues_dataenum}
 */
@Generated("com.spotify.dataenum.processor.DataEnumProcessor")
public abstract class MethodsAndValues {
  MethodsAndValues() {
  }

  /**
   * @return a {@link Val1} (see {@link MethodsAndValues_dataenum#Val1} for source)
   */
  public static MethodsAndValues val1(@Nonnull String x) {
    return new Val1(x);
  }

  /**
   * @return a {@link Val2} (see {@link MethodsAndValues_dataenum#Val2} for source)
   */
  public static MethodsAndValues val2(@Nonnull String x) {
    return new Val2(x);
  }

  public final boolean isVal1() {
    return (this instanceof Val1);
  }

  public final boolean isVal2() {
    return (this instanceof Val2);
  }

  public final Val1 asVal1() {
    return (Val1) this;
  }

  public final Val2 asVal2() {
    return (Val2) this;
  }

  public abstract void match(@Nonnull Consumer<Val1> val1, @Nonnull Consumer<Val2> val2);

  public abstract <R_> R_ map(@Nonnull Function<Val1, R_> val1, @Nonnull Function<Val2, R_> val2);

  public String classMethodNoExtraArgs(MethodsAndValues x) {
    return x.map((v1)->v1.x(), (v2)->v2.x());}

  public String classMethodSomeExtraArgs(MethodsAndValues x, String suffix) {
    return x.map((v1)->v1.x() + suffix, (v2)->v2.x() + suffix);}

  public <T> String classMethodTypeParams(MethodsAndValues x, T suffix) {
    return x.map((v1)->v1.x() + suffix.toString(), (v2)->v2.x() + suffix.toString());}

  public static String staticMethodNoExtraArgs(MethodsAndValues x) {
    return x.map((v1)->v1.x(), (v2)->v2.x());}

  public static String staticMethodSomeExtraArgs(MethodsAndValues x, String suffix) {
    return x.map((v1)->v1.x() + suffix, (v2)->v2.x() + suffix);}

  public static <T> String staticMethodTypeParams(MethodsAndValues x, T suffix) {
    return x.map((v1)->v1.x() + suffix.toString(), (v2)->v2.x() + suffix.toString());}

  public static final class Val1 extends MethodsAndValues {
    private final String x;

    Val1(String x) {
      this.x = checkNotNull(x);
    }

    @Nonnull
    public final String x() {
      return x;
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) return true;
      if (!(other instanceof Val1)) return false;
      Val1 o = (Val1) other;
      return o.x.equals(this.x);
    }

    @Override
    public int hashCode() {
      int result = 0;
      return result * 31 + this.x.hashCode();
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Val1{x=").append(this.x);
      return builder.append('}').toString();
    }

    @Override
    public final void match(@Nonnull Consumer<Val1> val1, @Nonnull Consumer<Val2> val2) {
      val1.accept(this);
    }

    @Override
    public final <R_> R_ map(@Nonnull Function<Val1, R_> val1, @Nonnull Function<Val2, R_> val2) {
      return val1.apply(this);
    }
  }

  public static final class Val2 extends MethodsAndValues {
    private final String x;

    Val2(String x) {
      this.x = checkNotNull(x);
    }

    @Nonnull
    public final String x() {
      return x;
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) return true;
      if (!(other instanceof Val2)) return false;
      Val2 o = (Val2) other;
      return o.x.equals(this.x);
    }

    @Override
    public int hashCode() {
      int result = 0;
      return result * 31 + this.x.hashCode();
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Val2{x=").append(this.x);
      return builder.append('}').toString();
    }

    @Override
    public final void match(@Nonnull Consumer<Val1> val1, @Nonnull Consumer<Val2> val2) {
      val2.accept(this);
    }

    @Override
    public final <R_> R_ map(@Nonnull Function<Val1, R_> val1, @Nonnull Function<Val2, R_> val2) {
      return val2.apply(this);
    }
  }
}
