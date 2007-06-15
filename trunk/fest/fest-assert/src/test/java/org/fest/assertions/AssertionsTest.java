/*
 * Created on Jan 10, 2007
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Copyright @2007 the original author or authors.
 */
package org.fest.assertions;

import java.util.ArrayList;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Tests for <code>{@link Assertions}</code>.
 *
 * @author Yvonne Wang
 */
public class AssertionsTest {

  @Test public void shouldReturnObjectAssertIfArgumentIsObject() {
    assertIsInstanceOf(Assertions.assertThat(new Object()), ObjectAssert.class);
  }
  
  @Test public void shouldReturnStringAssertIfArgumentIsString() {
    assertIsInstanceOf(Assertions.assertThat(""), StringAssert.class);
  }

  @Test public void shouldReturnObjectArrayAssertIfArgumentIsObjectArray() {
    assertIsInstanceOf(Assertions.assertThat(new String[] { "One" }), ObjectArrayAssert.class);
  }

  @Test public void shouldReturnCollectionAssertIfArgumentIsCollection() {
    assertIsInstanceOf(Assertions.assertThat(new ArrayList<Object>()), CollectionAssert.class);
  }
  
  @Test public void shouldReturnIntAssertIfArgumentIsInt() {
    assertIsInstanceOf(Assertions.assertThat(8), IntAssert.class);
  }
  
  private void assertIsInstanceOf(Object target, Class<?> expectedType) {
    assertEquals(target.getClass(), expectedType);
  }
}
