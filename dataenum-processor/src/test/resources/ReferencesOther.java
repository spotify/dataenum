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
import just.some.pkg.InPackage;

@Generated("com.spotify.dataenum.processor.DataEnumProcessor")
public abstract class ReferencesOther {
  ReferencesOther() {
  }

  public static ReferencesOther another(@Nonnull InPackage other) {
    return new Another(other);
  }

  public final boolean isAnother() {
    return (this instanceof Another);
  }

  public final Another asAnother() {
    return (Another) this;
  }

  public abstract void match(@Nonnull Consumer<Another> another);

  public abstract <R_> R_ map(@Nonnull Function<Another, R_> another);

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
      result = result * 31 + other.hashCode();
      return result;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Another{other=").append(other);
      return builder.append('}').toString();
    }

    @Override
    public final void match(@Nonnull Consumer<Another> another) {
      another.accept(this);
    }

    @Override
    public final <R_> R_ map(@Nonnull Function<Another, R_> another) {
      return another.apply(this);
    }
  }
}
