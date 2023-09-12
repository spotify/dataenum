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

  default String x(MethodsAndValues z) {
    return z.map(
      v1 -> v1.x(),
      v2 -> v2.x()
    );
  }

  @Static default int y(MethodsAndValues z) {
    return z.map(
      v1 -> 0,
      v2 -> 1
    );
  }
}
