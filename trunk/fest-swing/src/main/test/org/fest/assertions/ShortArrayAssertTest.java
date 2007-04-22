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

import org.fest.assertions.ShortArrayAssert;

import org.testng.annotations.Test;

/**
 * Unit tests for <code>{ShortArrayAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ShortArrayAssertTest {

  @Test public void shouldPassIfArrayIsNull() {
    new ShortArrayAssert((short[])null).isNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNotNull() {
    new ShortArrayAssert(new short[0]).isNull();
  }

  @Test public void shouldPassIfArrayIsNotNull() {
    new ShortArrayAssert(new short[0]).isNotNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNotNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNull() {
    new ShortArrayAssert((short[])null).isNotNull();
  }

  @Test public void shouldPassIfArrayIsEmpty() {
    new ShortArrayAssert(new short[0]).isEmpty();
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
    new ShortArrayAssert(new short[0]).isNotEmpty();
  }

  @Test public void shouldPassIfEqualArrays() {
    new ShortArrayAssert((short)43, (short)68).isEqualTo((short)43, (short)68);
  }
  
  @Test(dependsOnMethods = "shouldPassIfEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotEqualArrays() {
    new ShortArrayAssert((short)43, (short)68).isEqualTo((short)98);
  }

  @Test public void shouldPassIfNotEqualArrays() {
    new ShortArrayAssert((short)43, (short)68).isNotEqualTo((short)98);
  }
  
  @Test(dependsOnMethods = "shouldPassIfNotEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfEqualArrays() {
    new ShortArrayAssert((short)43, (short)68).isNotEqualTo((short)43, (short)68);
  }
}
