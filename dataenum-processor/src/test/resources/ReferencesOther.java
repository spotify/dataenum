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
import just.some.pkg.InPackage;

@Generated("com.spotify.dataenum.processor.DataEnumProcessor")
public abstract class ReferencesOther {
  private ReferencesOther() {
  }

  public static ReferencesOther another(@Nonnull InPackage other) {
    return Another.create(other);
  }

  public final boolean isAnother() {
    return (this instanceof Another);
  }

  public final Another asAnother() {
    return (Another) this;
  }

  public abstract void match(@Nonnull Consumer<Another> another);

  public abstract <R_> R_ map(@Nonnull Function<Another, R_> another);

  @AutoValue
  public abstract static class Another extends ReferencesOther {
    Another() {
    }

    private static Another create(InPackage other) {
      return new AutoValue_ReferencesOther_Another(other);
    }

    @Nonnull
    public abstract InPackage other();

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
