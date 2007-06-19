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
 * Test for <code>{@link FloatAssert}</code>.
 * 
 * @author Yvonne Wang
 */
public class FloatAssertTest {

  @Test public void shouldPassIfEqualAndExpectedEqual() {
    new FloatAssert(6.6f).isEqualTo(6.6f);
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotEqualAndExpectedEqual() {
    new FloatAssert(6.6f).isEqualTo(6.8f);
  }

  @Test public void shouldPassIfNotEqualAndExpectedNotEqual() {
    new FloatAssert(0.0f).isNotEqualTo(-0.0f);
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfEqualAndExpectedNotEqual() {
    new FloatAssert(0.0f).isNotEqualTo(0.0f);
  }

  @Test public void shouldPassIfGreaterThanAndExpectedGreaterThan() {
    new FloatAssert(0.0f).isGreaterThan(-0.0f);
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfLessThanAndExpectedGreaterThan() {
    new FloatAssert(-0.0f).isGreaterThan(0.0f);
  }

  @Test public void shouldPassIfLessThanAndExpectedLessThan() {
    new FloatAssert(6.6f).isLessThan(6.8f);
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfGreaterThanAndExpectedLessThan() {
    new FloatAssert(0.0f).isLessThan(-0.0f);
  }

  @Test public void shouldPassIfZeroAndExpectedZero() {
    new FloatAssert(0.0f).isZero();
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotZeroAndExpectedZero() {
    new FloatAssert(-0.0f).isZero();
  }

  @Test public void shouldPassIfNaNAndExpectedNaN() {
    new FloatAssert(Float.NaN).isNaN();
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotNaNAndExpectedNaN() {
    new FloatAssert(-0.0f).isNaN();
  }

  @Test public void shouldPassIfPositiveAndExpectedPositive() {
    new FloatAssert(6.6f).isPositive();
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotPositiveAndExpectedPositive() {
    new FloatAssert(-6.6f).isPositive();
  }

  @Test public void shouldPassIfNegativeAndExpectedNegative() {
    new FloatAssert(-6.6f).isNegative();
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotNegativeAndExpectedNegative() {
    new FloatAssert(6.6f).isNegative();
  }

}
