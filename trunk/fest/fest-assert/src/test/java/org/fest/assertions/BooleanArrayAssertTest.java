/*
 * Created on May 30, 2007
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
 * Tests for <code>{BooleanArrayAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Generated(value = "org.fest.assertions.PrimitiveArrayAssertGenerator", 
           date = "2007-05-30T23:28:11", 
           comments = "Generated using Velocity template 'org/fest/assertions/ArrayAssertTestTemplate.vm'")
public class BooleanArrayAssertTest {

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
    new BooleanArrayAssert(true, false).isEmpty();
  }

  @Test public void shouldPassIfArrayIsNotEmpty() {
    new BooleanArrayAssert(true, false).isNotEmpty();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNotEmpty", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsEmpty() {
    new BooleanArrayAssert(emptyArray()).isNotEmpty();
  }

  @Test public void shouldPassIfEqualArrays() {
    new BooleanArrayAssert(true, false).isEqualTo(array(true, false));
  }
  
  @Test(dependsOnMethods = "shouldPassIfEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotEqualArrays() {
    new BooleanArrayAssert(true, false).isEqualTo(array(false, true));
  }

  @Test public void shouldPassIfNotEqualArrays() {
    new BooleanArrayAssert(true, false).isNotEqualTo(array(false, true));
  }
  
  @Test(dependsOnMethods = "shouldPassIfNotEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfEqualArrays() {
    new BooleanArrayAssert(true, false).isNotEqualTo(array(true, false));
  }
  
  private boolean[] nullArray() { return null; }

  private boolean[] emptyArray() { return new boolean[0]; }
  
  private boolean[] array(boolean... args) { return args; }
}
