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
import java.lang.Object;
import java.lang.Override;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Generated("com.spotify.dataenum.processor.DataEnumProcessor")
public abstract class NullableValue {
  private NullableValue() {
  }

  public static NullableValue value(@Nonnull Object param1, @Nullable Object param2,
                                    @Nullable Object param3, @Nonnull Object param4, @Nonnull Object param5) {
    return Value.create(param1, param2, param3, param4, param5);
  }

  public final boolean isValue() {
    return (this instanceof Value);
  }

  public final Value asValue() {
    return (Value) this;
  }

  public abstract void match(@Nonnull Consumer<Value> value);

  public abstract <R_> R_ map(@Nonnull Function<Value, R_> value);

  @AutoValue
  public abstract static class Value extends NullableValue {
    Value() {
    }

    private static Value create(Object param1, Object param2, Object param3, Object param4,
                                Object param5) {
      return new AutoValue_NullableValue_Value(param1, param2, param3, param4, param5);
    }

    @Nonnull
    public abstract Object param1();

    @Nullable
    public abstract Object param2();

    @Nullable
    public abstract Object param3();

    @Nonnull
    public abstract Object param4();

    @Nonnull
    public abstract Object param5();

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
