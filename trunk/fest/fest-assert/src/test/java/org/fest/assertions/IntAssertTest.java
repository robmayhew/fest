/*
 * Created on Jun 14, 2007
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
 * Tests for <code>{@link IntAssert}</code>.
 *
 * @author Yvonne Wang
 */
public class IntAssertTest {
  
  @Test public void shouldPassIfEqualAndExpectedEqual(){
    new IntAssert(8).isEqualTo(8);
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotEqualAndExpectedEqual() {
    new IntAssert(6).isEqualTo(8);
  }
  
  @Test public void shouldPassIfNotEqualAndExpectedNotEqual() {
    new IntAssert(6).isNotEqualTo(8);
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfEqualAndExpectedNotEqual() {
    new IntAssert(8).isNotEqualTo(8);
  }
  
  @Test public void shouldPassIfGreaterThanAndExpectedGreaterThan() {
    new IntAssert(8).isGreaterThan(6);
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfEqualAndExpectedGreaterThan() {
    new IntAssert(8).isGreaterThan(8);
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfLessThanAndExpectedGreaterThan() {
    new IntAssert(6).isGreaterThan(8);
  }
 
  @Test public void shouldPassIfLessThanAndExpectedLessThan() {
    new IntAssert(6).isLessThan(8);
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfEqualAndExpectedLessThan() {
    new IntAssert(8).isLessThan(8);
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfGreaterThanAndExpectedLessThan() {
    new IntAssert(8).isLessThan(6);
  }
  
  @Test public void shouldPassIfGreaterThanZeroAndExpectedPositive() {
    new IntAssert(6).isPositive();
  }
  
  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfEqualToZeroAndExpectedPositive() {
    new IntAssert(0).isPositive();
  }
  
  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfLessToZeroAndExpectedPositive() {
    new IntAssert(-8).isPositive();
  }
  
  @Test public void shouldPassIfLessThanZeroAndExpectedNegative() {
    new IntAssert(-6).isNegative();
  }
  
  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfEqualToZeroAndExpectedNegative() {
    new IntAssert(0).isNegative();
  }
  
  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfGreaterToZeroAndExpectedNegative() {
    new IntAssert(8).isNegative();
  }
}
