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

import com.spotify.dataenum.DataEnum;
import com.spotify.dataenum.Static;
import com.spotify.dataenum.dataenum_case;

import java.util.Set;

@DataEnum
interface MethodsAndValues_dataenum {
    dataenum_case Val1(String x);

    dataenum_case Val2(String x);

    default String classMethodNoExtraArgs(MethodsAndValues x) {
        return x.map(
                v1 -> v1.x(),
                v2 -> v2.x()
        );
    }

    default String classMethodSomeExtraArgs(MethodsAndValues x, String suffix) {
        return x.map(
                v1 -> v1.x() + suffix,
                v2 -> v2.x() + suffix
        );
    }

    default <T> String classMethodTypeParams(MethodsAndValues x, T suffix) {
        return x.map(
                v1 -> v1.x() + suffix.toString(),
                v2 -> v2.x() + suffix.toString()
        );
    }

    @Static
    default String staticMethodNoExtraArgs(MethodsAndValues x) {
        return x.map(
                v1 -> v1.x(),
                v2 -> v2.x()
        );
    }

    @Static
    default String staticMethodSomeExtraArgs(MethodsAndValues x, String suffix) {
        return x.map(
                v1 -> v1.x() + suffix,
                v2 -> v2.x() + suffix
        );
    }

    @Static
    default <T> String staticMethodTypeParams(MethodsAndValues x, T suffix) {
        return x.map(
                v1 -> v1.x() + suffix.toString(),
                v2 -> v2.x() + suffix.toString()
        );
    }

}
