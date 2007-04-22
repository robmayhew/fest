/*
 * Created on Mar 19, 2007
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

import org.fest.assertions.BooleanArrayAssert;

import org.testng.annotations.Test;

/**
 * Unit tests for <code>{BooleanArrayAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BooleanArrayAssertTest {

  @Test public void shouldPassIfArrayIsNull() {
    new BooleanArrayAssert((boolean[])null).isNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNotNull() {
    new BooleanArrayAssert(new boolean[0]).isNull();
  }

  @Test public void shouldPassIfArrayIsNotNull() {
    new BooleanArrayAssert(new boolean[0]).isNotNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNotNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNull() {
    new BooleanArrayAssert((boolean[])null).isNotNull();
  }

  @Test public void shouldPassIfArrayIsEmpty() {
    new BooleanArrayAssert(new boolean[0]).isEmpty();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsEmpty" , expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNotEmpty() {
    new BooleanArrayAssert(true, false).isEmpty();
  }

  @Test public void shouldPassIfArrayIsNotEmpty() {
    new BooleanArrayAssert(true, false).isNotEmpty();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNotEmpty", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsEmpty() {
    new BooleanArrayAssert(new boolean[0]).isNotEmpty();
  }

  @Test public void shouldPassIfEqualArrays() {
    new BooleanArrayAssert(true, false).isEqualTo(true, false);
  }
  
  @Test(dependsOnMethods = "shouldPassIfEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotEqualArrays() {
    new BooleanArrayAssert(true, false).isEqualTo(false, true);
  }

  @Test public void shouldPassIfNotEqualArrays() {
    new BooleanArrayAssert(true, false).isNotEqualTo(false, true);
  }
  
  @Test(dependsOnMethods = "shouldPassIfNotEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfEqualArrays() {
    new BooleanArrayAssert(true, false).isNotEqualTo(true, false);
  }
}
