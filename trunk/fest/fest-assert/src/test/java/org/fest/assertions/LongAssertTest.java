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
 * Tests for <code>{@link LongAssert}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 */
public class LongAssertTest {

  @Test public void shouldPassIfEqualAndExpectedEqual() {
    new LongAssert(6L).isEqualTo(6L);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfNotEqualAndExpectedEqual() {
    new LongAssert(6L).isEqualTo(8L);
  }

  @Test public void shouldPassIfNotEqualAndExpectedNotEqual() {
    new LongAssert(6L).isNotEqualTo(8L);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfEqualAndExpectedNotEqual() {
    new LongAssert(8L).isNotEqualTo(8L);
  }

  @Test public void shouldPassIfGreaterThanAndExpectedGreaterThan() {
    new LongAssert(8L).isGreaterThan(6L);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfLessThanAndExpectedGreaterThan() {
    new LongAssert(6L).isGreaterThan(8L);
  }

  @Test public void shouldPassIfLessThanAndExpectedLessThan() {
    new LongAssert(6L).isLessThan(8L);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfGreaterThanAndExpectedLessThan() {
    new LongAssert(8L).isLessThan(6L);
  }

  @Test public void shouldPassIfPositiveAndExpectedPositive() {
    new LongAssert(6L).isPositive();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfNotPositiveAndExpectedPositive() {
    new LongAssert(0L).isPositive();
  }

  @Test public void shouldPassIfNegativeAndExpectedNegative() {
    new LongAssert(-6L).isNegative();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfNotNegativeAndExpectedNegative() {
    new LongAssert(0L).isNegative();
  }

  @Test public void shouldPassIfZeroAndExpectedZero() {
    new LongAssert(0L).isZero();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfNotZeroAndExpectedZero() {
    new LongAssert(9L).isZero();
  }

  @Test public void shouldPassIfGreaterOrEqualToAndExpectedGreaterThan() {
    new LongAssert(8L).isGreaterOrEqualTo(8L);
    new LongAssert(8L).isGreaterOrEqualTo(6L);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfLessOrEqualToAndExpectedGreaterThan() {
    new LongAssert(6L).isGreaterOrEqualTo(8L);
  }

  @Test public void shouldPassIfLessOrEqualToAndExpectedLessThan() {
    new LongAssert(8L).isLessOrEqualTo(8L);
    new LongAssert(6L).isLessOrEqualTo(8L);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfGreaterOrEqualToAndExpectedLessThan() {
    new LongAssert(8L).isLessOrEqualTo(6L);
  }

}
