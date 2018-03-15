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
package com.spotify.dataenum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows configuration of which access level should be used for generated constructors. When
 * generating code in package X, the dataenum-processor will use the value from the nearest
 * package-info.java file with this annotation to determine which value to use. If the traversal
 * reaches the root (the default package) without finding such a package-info.java file, {@link
 * Access#PACKAGE_PRIVATE} will be used.
 *
 * <p>There are two useful choices to make: PRIVATE or PACKAGE_PRIVATE. PRIVATE is best in the sense
 * of providing the clearest API: the only way to instantiate DataEnum cases is through the static
 * factory method in the top-level type. However, using private constructors leads to the compiler
 * generating synthetic constructor methods, and for Android, you want to keep the method count
 * down. As that is an important use case for DataEnum, we chose PACKAGE_PRIVATE as the default.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PACKAGE)
public @interface ConstructorAccess {
  Access value();
}
