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
package org.fest.util;

import java.util.Arrays;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link Objects}.
 * 
 * @author Yvonne Wang
 */
public class ObjectTest {

  @Test public void shouldReturnIsEmptyIfArrayIsEmpty() {
    assertTrue(Objects.isEmpty(new String[0]));
  }

  @Test public void shouldReturnIsEmptyIfArrayIsNull() {
    assertTrue(Objects.isEmpty(null));
  }

  @Test public void shouldReturnIsNotEmptyIfArrayHasElements() {
    assertFalse(Objects.isEmpty(new String[] { "Tuzi" }));
  }
  
  @Test public void shouldReturnNullIfArrayToConvertToListIsNull() {
    assertNull(Objects.asList((Object[])null));
  }
  
  @Test public void shouldReturnEmptyListIfArrayIsEmpty() {
    assertTrue(Objects.asList(new Object[0]).isEmpty());
  }
  
  @Test public void shouldReturnAreEqualIfBothObjectsAreNull() {
    assertTrue(Objects.areEqual(null, null));
  }
  
  @Test public void shouldReturnAreEqualIfObjectsAreEqual() {
    assertTrue(Objects.areEqual("Yoda", "Yoda"));
  }
  
  @Test public void shouldReturnAreNotEqualIfFirstObjectIsNullAndSecondIsNot() {
    assertFalse(Objects.areEqual(null, "Yoda"));
  }
  
  @Test public void shouldReturnAreNotEqualIfSecondObjectIsNullAndFirstIsNot() {
    assertFalse(Objects.areEqual("Yoda", null));
  }
  
  @Test public void shouldReturnAreNotEqualIfObjectsAreNotEqual() {
    assertFalse(Objects.areEqual("Yoda", new Integer(2)));
  }
  
  @Test public void shouldReturnEmptyStringArrayIfClassArrayIsNull() {
    assertEquals(Objects.namesOf((Class<?>[])null).length, 0);
  }

  @Test public void shouldReturnEmptyStringArrayIfClassArrayIsEmpty() {
    assertEquals(Objects.namesOf(new Class<?>[0]).length, 0);
  }
  
  @Test public void shouldReturnClassNames() {
    String[] expected = { String.class.getName(), Integer.class.getName() };
    String[] actual = Objects.namesOf(String.class, Integer.class);
    assertTrue(Arrays.equals(expected, actual), "expected:<" + Arrays.toString(expected) + "> actual:<"
        + Arrays.toString(actual) + ">");
  }
  
  @Test public void shouldReturnSameArrayAsTheOnePassed() {
    String[] array = Objects.array("Yoda", "Luke");
    assertEquals(array.length, 2);
    assertEquals(array[0], "Yoda");
    assertEquals(array[1], "Luke");
  }
}
