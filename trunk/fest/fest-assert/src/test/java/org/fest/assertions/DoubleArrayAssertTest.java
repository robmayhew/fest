/*
 * Created on May 27, 2007
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

import javax.annotation.Generated;

import org.testng.annotations.Test;

/**
 * Tests for <code>{DoubleArrayAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Generated(value = "org.fest.assertions.PrimitiveArrayAssertGenerator", 
           date = "2007-05-27T04:44:16", 
           comments = "Generated using Velocity template 'org/fest/assertions/ArrayAssertTestTemplate.vm'")
public class DoubleArrayAssertTest {

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
  
  private double[] nullArray() { return null; }

  private double[] emptyArray() { return new double[0]; }
  
  private double[] array(double... args) { return args; }
}
