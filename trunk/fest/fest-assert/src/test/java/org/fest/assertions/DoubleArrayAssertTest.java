/*
 * Created on Feb 14, 2008
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
 * Tests for <code>{@link DoubleArrayAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class DoubleArrayAssertTest {

  @Test public void shouldPassIfGivenValuesAreInArray() {
    new DoubleArrayAssert(55.03, 4345.91).contains(55.03, 4345.91);
  }
  
  @Test(dependsOnMethods = "shouldPassIfGivenValuesAreInArray", expectedExceptions = AssertionError.class) 
  public void shouldFailIfGivenValueIsNotInArray() {
    new DoubleArrayAssert(emptyArray()).contains(55.03, 4345.91);
  }

  @Test public void shouldPassIfGivenValuesAreNotInArray() {
    new DoubleArrayAssert(55.03, 4345.91).excludes(5323.2);
  }
  
  @Test(dependsOnMethods = "shouldPassIfGivenValuesAreNotInArray", expectedExceptions = AssertionError.class) 
  public void shouldFailIfGivenValueIsInArray() {
    new DoubleArrayAssert(55.03, 4345.91).excludes(55.03, 4345.91);
  }

  @Test public void shouldPassIfArrayIsNull() {
    new DoubleArrayAssert(nullArray()).isNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNotNull() {
    new DoubleArrayAssert(emptyArray()).isNull();
  }

  @Test public void shouldPassIfArrayIsNotNull() {
    new DoubleArrayAssert(emptyArray()).isNotNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNotNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNull() {
    new DoubleArrayAssert(nullArray()).isNotNull();
  }

  @Test public void shouldPassIfArrayIsEmpty() {
    new DoubleArrayAssert(emptyArray()).isEmpty();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsEmpty" , expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNotEmpty() {
    new DoubleArrayAssert(55.03, 4345.91).isEmpty();
  }

  @Test public void shouldPassIfArrayIsNotEmpty() {
    new DoubleArrayAssert(55.03, 4345.91).isNotEmpty();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNotEmpty", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsEmpty() {
    new DoubleArrayAssert(emptyArray()).isNotEmpty();
  }

  @Test public void shouldPassIfEqualArrays() {
    new DoubleArrayAssert(55.03, 4345.91).isEqualTo(array(55.03, 4345.91));
  }
  
  @Test(dependsOnMethods = "shouldPassIfEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotEqualArrays() {
    new DoubleArrayAssert(55.03, 4345.91).isEqualTo(array(5323.2));
  }

  @Test public void shouldPassIfNotEqualArrays() {
    new DoubleArrayAssert(55.03, 4345.91).isNotEqualTo(array(5323.2));
  }
  
  @Test(dependsOnMethods = "shouldPassIfNotEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfEqualArrays() {
    new DoubleArrayAssert(55.03, 4345.91).isNotEqualTo(array(55.03, 4345.91));
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfArrayIsEmptyWhenLookingForSpecificElements() {
    new DoubleArrayAssert(emptyArray()).containsOnly(55.03, 4345.91);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfArrayHasExtraElements() {
    new DoubleArrayAssert(55.03, 4345.91, 88.6).containsOnly(array(55.03, 4345.91));
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfArrayIsMissingElements() {
    new DoubleArrayAssert(55.03, 4345.91).containsOnly(array(5323.2));
  }

  @Test public void shouldPassIfArrayHasOnlySpecifiedElements() {
    new DoubleArrayAssert(55.03, 4345.91).containsOnly(array(55.03, 4345.91));    
  }
  
  private double[] nullArray() { return null; }

  private double[] emptyArray() { return new double[0]; }
  
  private double[] array(double... args) { return args; }
}
