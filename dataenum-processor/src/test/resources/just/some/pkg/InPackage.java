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

import com.google.auto.value.AutoValue;
import com.spotify.dataenum.function.Consumer;
import com.spotify.dataenum.function.Function;
import java.lang.Override;
import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.spotify.dataenum.processor.DataEnumProcessor")
public abstract class InPackage {
  private InPackage() {
  }

  public static InPackage value1(int param1, boolean param2) {
    return Value1.create(param1, param2);
  }

  public final boolean isValue1() {
    return (this instanceof Value1);
  }

  public final Value1 asValue1() {
    return (Value1) this;
  }

  public abstract void match(@Nonnull Consumer<Value1> value1);

  public abstract <R_> R_ map(@Nonnull Function<Value1, R_> value1);

  @AutoValue
  public abstract static class Value1 extends InPackage {
    Value1() {
    }

    private static Value1 create(int param1, boolean param2) {
      return new AutoValue_InPackage_Value1(param1, param2);
    }

    public abstract int param1();

    public abstract boolean param2();

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
