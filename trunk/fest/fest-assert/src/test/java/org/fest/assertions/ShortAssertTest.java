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
 * Tests for <code>{@link ShortAssert}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 */
public class ShortAssertTest {

  @Test public void shouldPassIfEqualAndExpectedEqual() {
    new ShortAssert(asShort(6)).isEqualTo(asShort(6));
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfNotEqualAndExpectedEqual() {
    new ShortAssert(asShort(6)).isEqualTo(asShort(8));
  }

  @Test public void shouldPassIfNotEqualAndExpectedNotEqual() {
    new ShortAssert(asShort(6)).isNotEqualTo(asShort(8));
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfEqualAndExpectedNotEqual() {
    new ShortAssert(asShort(6)).isNotEqualTo(asShort(6));
  }

  @Test public void shouldPassIfZeroAndExpectedZero() {
    new ShortAssert(asShort(0)).isZero();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfNotZeroAndExpectedZero() {
    new ShortAssert(asShort(6)).isZero();
  }

  @Test public void shouldPassIfGreaterThanAndExpectedGreaterThan() {
    new ShortAssert(asShort(6)).isGreaterThan(asShort(2));
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfLessThanAndExpectedGreaterThan() {
    new ShortAssert(asShort(6)).isGreaterThan(asShort(10));
  }

  @Test public void shouldPassIfLessThanAndExpectedLessThan() {
    new ShortAssert(asShort(6)).isLessThan(asShort(10));
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfNotLessThanAndExpectedLessThan() {
    new ShortAssert(asShort(6)).isLessThan(asShort(2));
  }

  @Test public void shouldPassIfPositiveAndExpectedPositive() {
    new ShortAssert(asShort(6)).isPositive();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfNotPositiveAndExpectedPositive() {
    new ShortAssert(asShort(-2)).isPositive();
  }

  @Test public void shouldPassIfNegativeAndExpectedNegative() {
    new ShortAssert(asShort(-2)).isNegative();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfNotNegativeAndExpectedNegative() {
    new ShortAssert(asShort(6)).isNegative();
  }

  @Test public void shouldPassIfGreaterOrEqualToAndExpectedGreaterThan() {
    new ShortAssert(asShort(8)).isGreaterOrEqualTo(asShort(8));
    new ShortAssert(asShort(8)).isGreaterOrEqualTo(asShort(6));
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfLessOrEqualToAndExpectedGreaterThan() {
    new ShortAssert(asShort(6)).isGreaterOrEqualTo(asShort(8));
  }

  @Test public void shouldPassIfLessOrEqualToAndExpectedLessThan() {
    new ShortAssert(asShort(8)).isLessOrEqualTo(asShort(8));
    new ShortAssert(asShort(6)).isLessOrEqualTo(asShort(8));
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfGreaterOrEqualToAndExpectedLessThan() {
    new ShortAssert(asShort(8)).isLessOrEqualTo(asShort(6));
  }

  private static short asShort(int i) {
    return (short)i;
  }
}
