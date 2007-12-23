/*
 * Created on Dec 22, 2007
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
 * Tests for <code>{@link BooleanArrayAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BooleanArrayAssertTest {

  @Test public void shouldPassIfGivenValuesAreInArray() {
    new BooleanArrayAssert(true).contains(true);
  }
  
  @Test(dependsOnMethods = "shouldPassIfGivenValuesAreInArray", expectedExceptions = AssertionError.class) 
  public void shouldFailIfGivenValueIsNotInArray() {
    new BooleanArrayAssert(emptyArray()).contains(true);
  }

  @Test public void shouldPassIfGivenValuesAreNotInArray() {
    new BooleanArrayAssert(true).excludes(false);
  }
  
  @Test(dependsOnMethods = "shouldPassIfGivenValuesAreNotInArray", expectedExceptions = AssertionError.class) 
  public void shouldFailIfGivenValueIsInArray() {
    new BooleanArrayAssert(true).excludes(true);
  }

  @Test public void shouldPassIfArrayIsNull() {
    new BooleanArrayAssert(nullArray()).isNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNotNull() {
    new BooleanArrayAssert(emptyArray()).isNull();
  }

  @Test public void shouldPassIfArrayIsNotNull() {
    new BooleanArrayAssert(emptyArray()).isNotNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNotNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNull() {
    new BooleanArrayAssert(nullArray()).isNotNull();
  }

  @Test public void shouldPassIfArrayIsEmpty() {
    new BooleanArrayAssert(emptyArray()).isEmpty();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsEmpty" , expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNotEmpty() {
    new BooleanArrayAssert(true).isEmpty();
  }

  @Test public void shouldPassIfArrayIsNotEmpty() {
    new BooleanArrayAssert(true).isNotEmpty();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNotEmpty", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsEmpty() {
    new BooleanArrayAssert(emptyArray()).isNotEmpty();
  }

  @Test public void shouldPassIfEqualArrays() {
    new BooleanArrayAssert(true).isEqualTo(array(true));
  }
  
  @Test(dependsOnMethods = "shouldPassIfEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotEqualArrays() {
    new BooleanArrayAssert(true).isEqualTo(array(false));
  }

  @Test public void shouldPassIfNotEqualArrays() {
    new BooleanArrayAssert(true).isNotEqualTo(array(false));
  }
  
  @Test(dependsOnMethods = "shouldPassIfNotEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfEqualArrays() {
    new BooleanArrayAssert(true).isNotEqualTo(array(true));
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfArrayIsEmptyWhenLookingForSpecificElements() {
    new BooleanArrayAssert(emptyArray()).containsOnly(true);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfArrayHasExtraElements() {
    new BooleanArrayAssert(true, false).containsOnly(array(true));
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfArrayIsMissingElements() {
    new BooleanArrayAssert(true).containsOnly(array(false));
  }

  @Test public void shouldPassIfArrayHasOnlySpecifiedElements() {
    new BooleanArrayAssert(true).containsOnly(array(true));    
  }
  
  private boolean[] nullArray() { return null; }

  private boolean[] emptyArray() { return new boolean[0]; }
  
  private boolean[] array(boolean... args) { return args; }
}
