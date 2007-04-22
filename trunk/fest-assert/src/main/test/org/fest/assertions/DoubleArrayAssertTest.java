/*
 * Created on Apr 22, 2007
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
 * Unit tests for <code>{DoubleArrayAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class DoubleArrayAssertTest {

  @Test public void shouldPassIfArrayIsNull() {
    new DoubleArrayAssert((double[])null).isNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNotNull() {
    new DoubleArrayAssert(new double[0]).isNull();
  }

  @Test public void shouldPassIfArrayIsNotNull() {
    new DoubleArrayAssert(new double[0]).isNotNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNotNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNull() {
    new DoubleArrayAssert((double[])null).isNotNull();
  }

  @Test public void shouldPassIfArrayIsEmpty() {
    new DoubleArrayAssert(new double[0]).isEmpty();
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
    new DoubleArrayAssert(new double[0]).isNotEmpty();
  }

  @Test public void shouldPassIfEqualArrays() {
    new DoubleArrayAssert(55.03, 4345.91).isEqualTo(55.03, 4345.91);
  }
  
  @Test(dependsOnMethods = "shouldPassIfEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotEqualArrays() {
    new DoubleArrayAssert(55.03, 4345.91).isEqualTo(5323.2);
  }

  @Test public void shouldPassIfNotEqualArrays() {
    new DoubleArrayAssert(55.03, 4345.91).isNotEqualTo(5323.2);
  }
  
  @Test(dependsOnMethods = "shouldPassIfNotEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfEqualArrays() {
    new DoubleArrayAssert(55.03, 4345.91).isNotEqualTo(55.03, 4345.91);
  }
}
