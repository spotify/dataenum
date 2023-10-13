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
package com.spotify.dataenum.it;

import com.spotify.dataenum.DataEnum;
import com.spotify.dataenum.Redacted;
import com.spotify.dataenum.dataenum_case;

/** For use in integration-type tests. */
@DataEnum
interface Testing_dataenum {
  dataenum_case One(int i);

  dataenum_case Two(String s);

  dataenum_case Three(String s);

  dataenum_case RedactedValue(String shouldShow, @Redacted Integer redacted);
}
