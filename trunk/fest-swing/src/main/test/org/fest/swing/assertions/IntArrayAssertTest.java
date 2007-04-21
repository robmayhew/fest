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
package org.fest.swing.assertions;

import org.fest.swing.assertions.IntArrayAssert;

import org.testng.annotations.Test;

/**
 * Unit tests for <code>{IntArrayAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class IntArrayAssertTest {

  @Test public void shouldPassIfArrayIsNull() {
    new IntArrayAssert((int[])null).isNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNotNull() {
    new IntArrayAssert(new int[0]).isNull();
  }

  @Test public void shouldPassIfArrayIsNotNull() {
    new IntArrayAssert(new int[0]).isNotNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNotNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNull() {
    new IntArrayAssert((int[])null).isNotNull();
  }

  @Test public void shouldPassIfArrayIsEmpty() {
    new IntArrayAssert(new int[0]).isEmpty();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsEmpty" , expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNotEmpty() {
    new IntArrayAssert(459, 23).isEmpty();
  }

  @Test public void shouldPassIfArrayIsNotEmpty() {
    new IntArrayAssert(459, 23).isNotEmpty();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNotEmpty", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsEmpty() {
    new IntArrayAssert(new int[0]).isNotEmpty();
  }

  @Test public void shouldPassIfEqualArrays() {
    new IntArrayAssert(459, 23).isEqualTo(459, 23);
  }
  
  @Test(dependsOnMethods = "shouldPassIfEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotEqualArrays() {
    new IntArrayAssert(459, 23).isEqualTo(90, 82);
  }

  @Test public void shouldPassIfNotEqualArrays() {
    new IntArrayAssert(459, 23).isNotEqualTo(90, 82);
  }
  
  @Test(dependsOnMethods = "shouldPassIfNotEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfEqualArrays() {
    new IntArrayAssert(459, 23).isNotEqualTo(459, 23);
  }
}
