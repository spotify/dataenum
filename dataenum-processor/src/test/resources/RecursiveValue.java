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
import java.util.Set;
import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("com.spotify.dataenum.processor.DataEnumProcessor")
public abstract class RecursiveValue {
  RecursiveValue() {
  }

  public static RecursiveValue value(@Nonnull RecursiveValue child) {
    return new Value(child);
  }

  public static RecursiveValue typeParamValue(@Nonnull Set<RecursiveValue> children) {
    return new TypeParamValue(children);
  }

  public final boolean isValue() {
    return (this instanceof Value);
  }

  public final boolean isTypeParamValue() {
    return (this instanceof TypeParamValue);
  }

  public final Value asValue() {
    return (Value) this;
  }

  public final TypeParamValue asTypeParamValue() {
    return (TypeParamValue) this;
  }

  public abstract void match(@Nonnull Consumer<Value> value,
                             @Nonnull Consumer<TypeParamValue> typeParamValue);

  public abstract <R_> R_ map(@Nonnull Function<Value, R_> value,
                              @Nonnull Function<TypeParamValue, R_> typeParamValue);

  public static final class Value extends RecursiveValue {
    private final RecursiveValue child;

    Value(RecursiveValue child) {
      this.child = checkNotNull(child);
    }

    @Nonnull
    public final RecursiveValue child() {
      return child;
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) return true;
      if (!(other instanceof Value)) return false;
      Value o = (Value) other;
      return o.child.equals(this.child);
    }

    @Override
    public int hashCode() {
      int _hash_result = 0;
      return _hash_result * 31 + child.hashCode();
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Value{child=").append(child);
      return builder.append('}').toString();
    }

    @Override
    public final void match(@Nonnull Consumer<Value> value,
                            @Nonnull Consumer<TypeParamValue> typeParamValue) {
      value.accept(this);
    }

    @Override
    public final <R_> R_ map(@Nonnull Function<Value, R_> value,
                             @Nonnull Function<TypeParamValue, R_> typeParamValue) {
      return value.apply(this);
    }
  }

  public static final class TypeParamValue extends RecursiveValue {
    private final Set<RecursiveValue> children;

    TypeParamValue(Set<RecursiveValue> children) {
      this.children = checkNotNull(children);
    }

    @Nonnull
    public final Set<RecursiveValue> children() {
      return children;
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) return true;
      if (!(other instanceof TypeParamValue)) return false;
      TypeParamValue o = (TypeParamValue) other;
      return o.children.equals(this.children);
    }

    @Override
    public int hashCode() {
      int _hash_result = 0;
      return _hash_result * 31 + children.hashCode();
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("TypeParamValue{children=").append(children);
      return builder.append('}').toString();
    }

    @Override
    public final void match(@Nonnull Consumer<Value> value,
                            @Nonnull Consumer<TypeParamValue> typeParamValue) {
      typeParamValue.accept(this);
    }

    @Override
    public final <R_> R_ map(@Nonnull Function<Value, R_> value,
                             @Nonnull Function<TypeParamValue, R_> typeParamValue) {
      return typeParamValue.apply(this);
    }
  }
}
