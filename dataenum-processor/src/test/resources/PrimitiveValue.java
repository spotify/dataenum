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
public abstract class PrimitiveValue {
  private PrimitiveValue() {
  }

  public static PrimitiveValue value(int param1, boolean param2, float param3, double param4) {
    return Value.create(param1, param2, param3, param4);
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
  public abstract static class Value extends PrimitiveValue {
    Value() {
    }

    private static Value create(int param1, boolean param2, float param3, double param4) {
      return new AutoValue_PrimitiveValue_Value(param1, param2, param3, param4);
    }

    public abstract int param1();

    public abstract boolean param2();

    public abstract float param3();

    public abstract double param4();

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
