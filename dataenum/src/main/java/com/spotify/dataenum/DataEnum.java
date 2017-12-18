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
 * Annotation used for specifying and generating tagged union style enums.
 *
 * <p>To be used together with the dataenum-processor annotation processor.
 *
 * <p>This annotation must be used on a package private interface, and the interface name must end
 * with '_dataenum'. For each value of the enum, declare a method with {@link dataenum_value} as the
 * return type. The name of the method will be used as the name of the value type, and the arguments
 * of the method will be the data associated with the value.
 *
 * <p>The value names are taken verbatim from the interface, so you will want to use CamelCase for
 * the value names. Non-primitive types will be non-nullable by default, but this can be overriden
 * using a @Nullable annotation.
 *
 * <p>A new class will be generated without the `_dataenum` suffix, and that will be your data
 * enumeration class. It will act as a factory and a base class for your enumeration values. The
 * interface that has the &#64;DataEnum annotation is only used as an input for code generation,
 * therefore all classes with the `_dataenum` suffix can be excluded from artifacts.
 *
 * <pre>
 * &#64;DataEnum
 * interface MyData_dataenum {
 *   dataenum_value Foo(int justANumber, String stringThatCannotBeNull);
 *   dataenum_value Bar(&#64;Nullable String thisStringCanBeNull);
 *   dataenum_value Baz(); // no data associated with this value
 * }
 * </pre>
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface DataEnum {}
