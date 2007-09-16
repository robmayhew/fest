/*
 * Created on Sep 16, 2007
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
 * Tests for <code>{LongArrayAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class LongArrayAssertTest {

  @Test public void shouldPassIfArrayIsNull() {
    new LongArrayAssert(nullArray()).isNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNotNull() {
    new LongArrayAssert(emptyArray()).isNull();
  }

  @Test public void shouldPassIfArrayIsNotNull() {
    new LongArrayAssert(emptyArray()).isNotNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNotNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNull() {
    new LongArrayAssert(nullArray()).isNotNull();
  }

  @Test public void shouldPassIfArrayIsEmpty() {
    new LongArrayAssert(emptyArray()).isEmpty();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsEmpty" , expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNotEmpty() {
    new LongArrayAssert(43l, 53l).isEmpty();
  }

  @Test public void shouldPassIfArrayIsNotEmpty() {
    new LongArrayAssert(43l, 53l).isNotEmpty();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNotEmpty", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsEmpty() {
    new LongArrayAssert(emptyArray()).isNotEmpty();
  }

  @Test public void shouldPassIfEqualArrays() {
    new LongArrayAssert(43l, 53l).isEqualTo(array(43l, 53l));
  }
  
  @Test(dependsOnMethods = "shouldPassIfEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotEqualArrays() {
    new LongArrayAssert(43l, 53l).isEqualTo(array(434l));
  }

  @Test public void shouldPassIfNotEqualArrays() {
    new LongArrayAssert(43l, 53l).isNotEqualTo(array(434l));
  }
  
  @Test(dependsOnMethods = "shouldPassIfNotEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfEqualArrays() {
    new LongArrayAssert(43l, 53l).isNotEqualTo(array(43l, 53l));
  }
  
  private long[] nullArray() { return null; }

  private long[] emptyArray() { return new long[0]; }
  
  private long[] array(long... args) { return args; }
}
