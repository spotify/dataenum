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
import just.some.pkg.InPackage;

@Generated("com.spotify.dataenum.processor.DataEnumProcessor")
public abstract class ReferencesOther {
  ReferencesOther() {
  }

  public static ReferencesOther another(@Nonnull InPackage other) {
    return new Another(other);
  }

  public static ReferencesOther typeParamAnother(@Nonnull Set<InPackage> others) {
    return new TypeParamAnother(others);
  }

  public static ReferencesOther typeParamParamAnother(@Nonnull Set<Set<InPackage>> manyOthers) {
    return new TypeParamParamAnother(manyOthers);
  }

  public final boolean isAnother() {
    return (this instanceof Another);
  }

  public final boolean isTypeParamAnother() {
    return (this instanceof TypeParamAnother);
  }

  public final boolean isTypeParamParamAnother() {
    return (this instanceof TypeParamParamAnother);
  }

  public final Another asAnother() {
    return (Another) this;
  }

  public final TypeParamAnother asTypeParamAnother() {
    return (TypeParamAnother) this;
  }

  public final TypeParamParamAnother asTypeParamParamAnother() {
    return (TypeParamParamAnother) this;
  }

  public abstract void match(@Nonnull Consumer<Another> another,
                             @Nonnull Consumer<TypeParamAnother> typeParamAnother,
                             @Nonnull Consumer<TypeParamParamAnother> typeParamParamAnother);

  public abstract <R_> R_ map(@Nonnull Function<Another, R_> another,
                              @Nonnull Function<TypeParamAnother, R_> typeParamAnother,
                              @Nonnull Function<TypeParamParamAnother, R_> typeParamParamAnother);

  public static final class Another extends ReferencesOther {
    private final InPackage other;

    Another(InPackage other) {
      this.other = checkNotNull(other);
    }

    @Nonnull
    public final InPackage other() {
      return other;
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) return true;
      if (!(other instanceof Another)) return false;
      Another o = (Another) other;
      return o.other.equals(this.other);
    }

    @Override
    public int hashCode() {
      int result = 0;
      return result * 31 + this.other.hashCode();
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Another{other=").append(this.other);
      return builder.append('}').toString();
    }

    @Override
    public final void match(@Nonnull Consumer<Another> another,
                            @Nonnull Consumer<TypeParamAnother> typeParamAnother,
                            @Nonnull Consumer<TypeParamParamAnother> typeParamParamAnother) {
      another.accept(this);
    }

    @Override
    public final <R_> R_ map(@Nonnull Function<Another, R_> another,
                             @Nonnull Function<TypeParamAnother, R_> typeParamAnother,
                             @Nonnull Function<TypeParamParamAnother, R_> typeParamParamAnother) {
      return another.apply(this);
    }
  }

  public static final class TypeParamAnother extends ReferencesOther {
    private final Set<InPackage> others;

    TypeParamAnother(Set<InPackage> others) {
      this.others = checkNotNull(others);
    }

    @Nonnull
    public final Set<InPackage> others() {
      return others;
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) return true;
      if (!(other instanceof TypeParamAnother)) return false;
      TypeParamAnother o = (TypeParamAnother) other;
      return o.others.equals(this.others);
    }

    @Override
    public int hashCode() {
      int result = 0;
      return result * 31 + this.others.hashCode();
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("TypeParamAnother{others=").append(this.others);
      return builder.append('}').toString();
    }

    @Override
    public final void match(@Nonnull Consumer<Another> another,
                            @Nonnull Consumer<TypeParamAnother> typeParamAnother,
                            @Nonnull Consumer<TypeParamParamAnother> typeParamParamAnother) {
      typeParamAnother.accept(this);
    }

    @Override
    public final <R_> R_ map(@Nonnull Function<Another, R_> another,
                             @Nonnull Function<TypeParamAnother, R_> typeParamAnother,
                             @Nonnull Function<TypeParamParamAnother, R_> typeParamParamAnother) {
      return typeParamAnother.apply(this);
    }
  }

  public static final class TypeParamParamAnother extends ReferencesOther {
    private final Set<Set<InPackage>> manyOthers;

    TypeParamParamAnother(Set<Set<InPackage>> manyOthers) {
      this.manyOthers = checkNotNull(manyOthers);
    }

    @Nonnull
    public final Set<Set<InPackage>> manyOthers() {
      return manyOthers;
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) return true;
      if (!(other instanceof TypeParamParamAnother)) return false;
      TypeParamParamAnother o = (TypeParamParamAnother) other;
      return o.manyOthers.equals(this.manyOthers);
    }

    @Override
    public int hashCode() {
      int result = 0;
      return result * 31 + this.manyOthers.hashCode();
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("TypeParamParamAnother{manyOthers=").append(this.manyOthers);
      return builder.append('}').toString();
    }

    @Override
    public final void match(@Nonnull Consumer<Another> another,
                            @Nonnull Consumer<TypeParamAnother> typeParamAnother,
                            @Nonnull Consumer<TypeParamParamAnother> typeParamParamAnother) {
      typeParamParamAnother.accept(this);
    }

    @Override
    public final <R_> R_ map(@Nonnull Function<Another, R_> another,
                             @Nonnull Function<TypeParamAnother, R_> typeParamAnother,
                             @Nonnull Function<TypeParamParamAnother, R_> typeParamParamAnother) {
      return typeParamParamAnother.apply(this);
    }
  }
}
