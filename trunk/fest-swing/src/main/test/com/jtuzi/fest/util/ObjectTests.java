/*
 * Created on Sep 23, 2006
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2006 the original author or authors.
 */
package com.jtuzi.fest.util;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import com.jtuzi.fest.util.Objects;

import org.testng.annotations.Test;

/**
 * Unit tests for {@link Objects}.
 * 
 * @author Yvonne Wang
 */
public class ObjectTests {

  @Test public void shouldReturnIsEmptyIfArrayIsEmpty() {
    assertTrue(Objects.isEmpty(new String[0]));
  }

  @Test public void shouldReturnIsEmptyIfArrayIsNull() {
    assertTrue(Objects.isEmpty(null));
  }

  @Test public void shouldReturnIsNotEmptyIfArrayHasElements() {
    assertFalse(Objects.isEmpty(new String[] { "Tuzi" }));
  }
}
