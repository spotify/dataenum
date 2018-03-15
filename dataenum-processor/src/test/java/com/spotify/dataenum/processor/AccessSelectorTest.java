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
package com.spotify.dataenum.processor;

import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PROTECTED;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.collect.Sets;
import com.spotify.dataenum.Access;
import com.spotify.dataenum.ConstructorAccess;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.type.TypeMirror;
import org.junit.Before;
import org.junit.Test;

public class AccessSelectorTest {

  private AccessSelector selector;

  @Before
  public void setUp() throws Exception {
    selector =
        new AccessSelector(
            Sets.newHashSet(
                new Package(
                    new FakeName("com.spotify.foo"),
                    new ConfiguredConstructorAccess(Access.PRIVATE)),
                new Package(
                    new FakeName("com.spotify.bar"),
                    new ConfiguredConstructorAccess(Access.PROTECTED)),
                new Package(
                    new FakeName("com.spotify.bar.flu"),
                    new ConfiguredConstructorAccess(Access.PUBLIC)),
                new Package(
                    new FakeName("com.spotify.pkgpriv"),
                    new ConfiguredConstructorAccess(Access.PACKAGE_PRIVATE)),
                new Package(
                    new FakeName("com.spotify"), new ConfiguredConstructorAccess(Access.PUBLIC))));
  }

  @Test
  public void shouldReturnEmptyForNoHit() {
    assertThat(selector.accessModifierFor("org.apache"), is(Optional.empty()));
  }

  @Test
  public void shouldReturnEmptyForExplicitPackagePrivate() {
    assertThat(selector.accessModifierFor("com.spotify.pkgpriv"), is(Optional.empty()));
  }

  @Test
  public void shouldReturnConfiguredForDirectHit() {
    assertThat(selector.accessModifierFor("com.spotify.foo"), is(Optional.of(PRIVATE)));
  }

  @Test
  public void shouldReturnConfiguredForRecursiveHit() {
    assertThat(selector.accessModifierFor("com.spotify.bar.spork"), is(Optional.of(PROTECTED)));
  }

  @Test
  public void shouldOnlyMatchFullPackageNames() {
    // 'com.spotify.bar.flu' will yield PUBLIC if it matches, which it shouldn't
    assertThat(selector.accessModifierFor("com.spotify.bar.flurb"), is(Optional.of(PROTECTED)));
  }

  private static class Package implements PackageElement {

    private final FakeName packageName;
    private final ConstructorAccess constructorAccess;

    private Package(FakeName packageName, ConstructorAccess constructorAccess) {
      this.packageName = packageName;
      this.constructorAccess = constructorAccess;
    }

    @Override
    public Name getQualifiedName() {
      return packageName;
    }

    @Override
    public TypeMirror asType() {
      return null;
    }

    @Override
    public ElementKind getKind() {
      return ElementKind.PACKAGE;
    }

    @Override
    public Set<Modifier> getModifiers() {
      return null;
    }

    @Override
    public Name getSimpleName() {
      return null;
    }

    @Override
    public List<? extends Element> getEnclosedElements() {
      return Collections.emptyList();
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
      return Collections.emptyList();
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
      return (A) constructorAccess;
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(Class<A> annotationType) {
      return null;
    }

    @Override
    public <R, P> R accept(ElementVisitor<R, P> v, P p) {
      return null;
    }

    @Override
    public boolean isUnnamed() {
      return false;
    }

    @Override
    public Element getEnclosingElement() {
      return null;
    }
  }

  private static class FakeName implements Name {

    private final String name;

    private FakeName(String name) {
      this.name = name;
    }

    @Override
    public boolean contentEquals(CharSequence cs) {
      return name.contentEquals(cs);
    }

    @Override
    public int length() {
      return name.length();
    }

    @Override
    public char charAt(int index) {
      return name.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
      return name.subSequence(start, end);
    }

    @Override
    public String toString() {
      return name;
    }
  }

  private static class ConfiguredConstructorAccess implements ConstructorAccess {

    private final Access access;

    private ConfiguredConstructorAccess(Access access) {
      this.access = access;
    }

    @Override
    public Access value() {
      return access;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
      return ConstructorAccess.class;
    }
  }

  private static class FakeElement implements Element {

    @Override
    public TypeMirror asType() {
      return null;
    }

    @Override
    public ElementKind getKind() {
      return null;
    }

    @Override
    public Set<Modifier> getModifiers() {
      return null;
    }

    @Override
    public Name getSimpleName() {
      return null;
    }

    @Override
    public Element getEnclosingElement() {
      return null;
    }

    @Override
    public List<? extends Element> getEnclosedElements() {
      return null;
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
      return null;
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
      return null;
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(Class<A> annotationType) {
      return null;
    }

    @Override
    public <R, P> R accept(ElementVisitor<R, P> v, P p) {
      return null;
    }
  }
}
