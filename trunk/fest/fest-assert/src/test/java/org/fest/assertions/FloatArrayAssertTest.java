/*
 * Created on Jan 25, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.assertions;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link FloatArrayAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FloatArrayAssertTest {

  @Test public void shouldPassIfGivenValuesAreInArray() {
    new FloatArrayAssert(34.90f).contains(34.90f);
  }
  
  @Test(dependsOnMethods = "shouldPassIfGivenValuesAreInArray", expectedExceptions = AssertionError.class) 
  public void shouldFailIfGivenValueIsNotInArray() {
    new FloatArrayAssert(emptyArray()).contains(34.90f);
  }

  @Test public void shouldPassIfGivenValuesAreNotInArray() {
    new FloatArrayAssert(34.90f).excludes(88.43f);
  }
  
  @Test(dependsOnMethods = "shouldPassIfGivenValuesAreNotInArray", expectedExceptions = AssertionError.class) 
  public void shouldFailIfGivenValueIsInArray() {
    new FloatArrayAssert(34.90f).excludes(34.90f);
  }

  @Test public void shouldPassIfArrayIsNull() {
    new FloatArrayAssert(nullArray()).isNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNotNull() {
    new FloatArrayAssert(emptyArray()).isNull();
  }

  @Test public void shouldPassIfArrayIsNotNull() {
    new FloatArrayAssert(emptyArray()).isNotNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNotNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNull() {
    new FloatArrayAssert(nullArray()).isNotNull();
  }

  @Test public void shouldPassIfArrayIsEmpty() {
    new FloatArrayAssert(emptyArray()).isEmpty();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsEmpty" , expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNotEmpty() {
    new FloatArrayAssert(34.90f).isEmpty();
  }

  @Test public void shouldPassIfArrayIsNotEmpty() {
    new FloatArrayAssert(34.90f).isNotEmpty();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNotEmpty", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsEmpty() {
    new FloatArrayAssert(emptyArray()).isNotEmpty();
  }

  @Test public void shouldPassIfEqualArrays() {
    new FloatArrayAssert(34.90f).isEqualTo(array(34.90f));
  }
  
  @Test(dependsOnMethods = "shouldPassIfEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotEqualArrays() {
    new FloatArrayAssert(34.90f).isEqualTo(array(88.43f));
  }

  @Test public void shouldPassIfNotEqualArrays() {
    new FloatArrayAssert(34.90f).isNotEqualTo(array(88.43f));
  }
  
  @Test(dependsOnMethods = "shouldPassIfNotEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfEqualArrays() {
    new FloatArrayAssert(34.90f).isNotEqualTo(array(34.90f));
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfArrayIsEmptyWhenLookingForSpecificElements() {
    new FloatArrayAssert(emptyArray()).containsOnly(34.90f);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfArrayHasExtraElements() {
    new FloatArrayAssert(34.90f, 88.6f).containsOnly(array(34.90f));
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfArrayIsMissingElements() {
    new FloatArrayAssert(34.90f).containsOnly(array(88.43f));
  }

  @Test public void shouldPassIfArrayHasOnlySpecifiedElements() {
    new FloatArrayAssert(34.90f).containsOnly(array(34.90f));    
  }
  
  private float[] nullArray() { return null; }

  private float[] emptyArray() { return new float[0]; }
  
  private float[] array(float... args) { return args; }
}
