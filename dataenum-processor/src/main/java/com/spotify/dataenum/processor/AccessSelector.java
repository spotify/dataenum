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

import com.spotify.dataenum.Access;
import com.spotify.dataenum.ConstructorAccess;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;

/**
 * Determines the most appropriate access level based on package-level annotations. Uses a
 * simplistic algorithm that should perform well given a small number of annotated packages, which
 * seems like a reasonable assumption to make.
 */
class AccessSelector {

  // sorted so that the longest names are first; this means the first hit is going to be the best
  // one
  private final List<PackageAndAccess> packages;

  AccessSelector(Set<? extends Element> visibilityAnnotatedPackages) {
    packages = parseAnnotatedPackages(visibilityAnnotatedPackages);
  }

  private List<PackageAndAccess> parseAnnotatedPackages(
      Set<? extends Element> visibilityAnnotatedPackages) {
    ArrayList<PackageAndAccess> result = new ArrayList<>(visibilityAnnotatedPackages.size());

    for (Element element : visibilityAnnotatedPackages) {
      if (!(element instanceof PackageElement)) {
        throw new IllegalArgumentException(
            "received a access annotated element that is not a package: " + element);
      }

      PackageElement packageElement = (PackageElement) element;

      result.add(
          new PackageAndAccess(
              packageElement.getQualifiedName().toString(),
              element.getAnnotation(ConstructorAccess.class).value()));
    }

    result.sort((o1, o2) -> o2.packageName.length() - o1.packageName.length());

    return result;
  }

  public Optional<Modifier> accessModifierFor(String packageName) {
    switch (accessFor(packageName)) {
      case PACKAGE_PRIVATE:
        return Optional.empty();
      case PRIVATE:
        return Optional.of(Modifier.PRIVATE);
      case PROTECTED:
        return Optional.of(Modifier.PROTECTED);
      case PUBLIC:
        return Optional.of(Modifier.PUBLIC);
    }

    throw new AssertionError("cannot get here");
  }

  private Access accessFor(String packageName) {
    for (PackageAndAccess packageAndAccess : packages) {
      if (isParentOf(packageAndAccess.packageName, packageName)) {
        return packageAndAccess.access;
      }
    }

    return Access.PACKAGE_PRIVATE;
  }

  private boolean isParentOf(String maybeParentPackage, String packageName) {
    // check the easy case first
    if (!packageName.startsWith(maybeParentPackage)) {
      return false;
    }

    // even if they start with the same string, there might be a mismatch in the last package name
    // in the possible parent. For instance, "com.spot" is not a parent package of "com.spotify".
    String[] parentParts = maybeParentPackage.split("\\.");
    String[] packageParts = packageName.split("\\.");

    String lastParentPackagePart = parentParts[parentParts.length - 1];

    return packageParts[parentParts.length - 1].equals(lastParentPackagePart);
  }

  private static class PackageAndAccess {

    private final String packageName;
    private final Access access;

    private PackageAndAccess(String packageName, Access access) {
      this.packageName = packageName;
      this.access = access;
    }
  }
}
