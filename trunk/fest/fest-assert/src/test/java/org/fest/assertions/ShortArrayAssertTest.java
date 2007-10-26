/*
 * Created on Oct 26, 2007
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

import org.testng.annotations.Test;

/**
 * Tests for <code>{ShortArrayAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ShortArrayAssertTest {

  @Test public void shouldPassIfGivenValuesAreInArray() {
    new ShortArrayAssert((short)43, (short)68).contains((short)43, (short)68);
  }
  
  @Test(dependsOnMethods = "shouldPassIfGivenValuesAreInArray", expectedExceptions = AssertionError.class) 
  public void shouldFailIfGivenValueIsNotInArray() {
    new ShortArrayAssert(emptyArray()).contains((short)43, (short)68);
  }

  @Test public void shouldPassIfGivenValuesAreNotInArray() {
    new ShortArrayAssert((short)43, (short)68).excludes((short)98);
  }
  
  @Test(dependsOnMethods = "shouldPassIfGivenValuesAreNotInArray", expectedExceptions = AssertionError.class) 
  public void shouldFailIfGivenValueIsInArray() {
    new ShortArrayAssert((short)43, (short)68).excludes((short)43, (short)68);
  }

  @Test public void shouldPassIfArrayIsNull() {
    new ShortArrayAssert(nullArray()).isNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNotNull() {
    new ShortArrayAssert(emptyArray()).isNull();
  }

  @Test public void shouldPassIfArrayIsNotNull() {
    new ShortArrayAssert(emptyArray()).isNotNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNotNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNull() {
    new ShortArrayAssert(nullArray()).isNotNull();
  }

  @Test public void shouldPassIfArrayIsEmpty() {
    new ShortArrayAssert(emptyArray()).isEmpty();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsEmpty" , expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNotEmpty() {
    new ShortArrayAssert((short)43, (short)68).isEmpty();
  }

  @Test public void shouldPassIfArrayIsNotEmpty() {
    new ShortArrayAssert((short)43, (short)68).isNotEmpty();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNotEmpty", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsEmpty() {
    new ShortArrayAssert(emptyArray()).isNotEmpty();
  }

  @Test public void shouldPassIfEqualArrays() {
    new ShortArrayAssert((short)43, (short)68).isEqualTo(array((short)43, (short)68));
  }
  
  @Test(dependsOnMethods = "shouldPassIfEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotEqualArrays() {
    new ShortArrayAssert((short)43, (short)68).isEqualTo(array((short)98));
  }

  @Test public void shouldPassIfNotEqualArrays() {
    new ShortArrayAssert((short)43, (short)68).isNotEqualTo(array((short)98));
  }
  
  @Test(dependsOnMethods = "shouldPassIfNotEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfEqualArrays() {
    new ShortArrayAssert((short)43, (short)68).isNotEqualTo(array((short)43, (short)68));
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfArrayIsEmptyWhenLookingForSpecificElements() {
    new ShortArrayAssert(emptyArray()).containsOnly((short)43, (short)68);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfArrayHasExtraElements() {
    new ShortArrayAssert((short)43, (short)68).containsOnly(array((short)43, (short)68, (short)98));
  }
  
  @Test public void shouldPassIfArrayHasOnlySpecifiedElements() {
    new ShortArrayAssert((short)43, (short)68).containsOnly(array((short)43, (short)68));    
  }
  
  private short[] nullArray() { return null; }

  private short[] emptyArray() { return new short[0]; }
  
  private short[] array(short... args) { return args; }
}
