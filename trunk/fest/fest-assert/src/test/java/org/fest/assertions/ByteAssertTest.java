/*
 * Created on Jun 18, 2007
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2007 the original author or authors.
 */
package org.fest.assertions;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ByteAssert}</code>.
 * 
 * @author Yvonne Wang
 */
public class ByteAssertTest {

  @Test public void shouldPassIfEqualAndExpectedEqual() {
    new ByteAssert(asByte(6)).isEqualTo(asByte(6));
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotEqualAndExpectedEqual() {
    new ByteAssert(asByte(6)).isEqualTo(asByte(8));
  }

  @Test public void shouldPassIfNotEqualAndExpectedNotEqual() {
    new ByteAssert(asByte(6)).isNotEqualTo(asByte(8));
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfEqualAndExpectedNotEqual() {
    new ByteAssert(asByte(6)).isNotEqualTo(asByte(6));
  }

  @Test public void shouldPassIfZeroAndExpectedZero() {
    new ByteAssert(asByte(0)).isZero();
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotZeroAndExpectedZero() {
    new ByteAssert(asByte(6)).isZero();
  }

  @Test public void shouldPassIfGreaterThanAndExpectedGreaterThan() {
    new ByteAssert(asByte(6)).isGreaterThan(asByte(2));
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfLessThanAndExpectedGreaterThan() {
    new ByteAssert(asByte(6)).isGreaterThan(asByte(10));
  }

  @Test public void shouldPassIfLessThanAndExpectedLessThan() {
    new ByteAssert(asByte(2)).isLessThan(asByte(6));
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfGreaterThanAndExpectedLessThan() {
    new ByteAssert(asByte(10)).isLessThan(asByte(6));
  }

  @Test public void shouldPassIfPositiveAndExpectedPositive() {
    new ByteAssert(asByte(6)).isPositive();
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotPositiveAndExpectedPositive() {
    new ByteAssert(asByte(-2)).isPositive();
  }

  @Test public void shouldPassIfNegativeAndExpectedNegative() {
    new ByteAssert(asByte(-2)).isNegative();
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shoudlFailIfNotNegativeAndExpectedNegative() {
    new ByteAssert(asByte(6)).isNegative();
  }
  
  private static byte asByte(int i) {
    return (byte)i;
  }
}
