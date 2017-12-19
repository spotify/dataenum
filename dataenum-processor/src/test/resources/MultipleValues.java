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
import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.spotify.dataenum.processor.DataEnumProcessor")
public abstract class MultipleValues {
  private MultipleValues() {
  }

  public static MultipleValues value1(int param1, boolean param2) {
    return Value1.create(param1, param2);
  }

  public static MultipleValues value2(int param1, boolean param2) {
    return Value2.create(param1, param2);
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

  @AutoValue
  public abstract static class Value1 extends MultipleValues {
    Value1() {
    }

    private static Value1 create(int param1, boolean param2) {
      return new AutoValue_MultipleValues_Value1(param1, param2);
    }

    public abstract int param1();

    public abstract boolean param2();

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

  @AutoValue
  public abstract static class Value2 extends MultipleValues {
    Value2() {
    }

    private static Value2 create(int param1, boolean param2) {
      return new AutoValue_MultipleValues_Value2(param1, param2);
    }

    public abstract int param1();

    public abstract boolean param2();

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
