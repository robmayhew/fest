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

import static org.testng.Assert.*;

/**
 * Tests for <code>{@link ByteAssert}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 */
public class ByteAssertTest {

  @Test public void shouldPassIfValuesAreEqualAsExpected() {
    new ByteAssert(asByte(6)).isEqualTo(asByte(6));
  }

  @Test public void shouldFailIfValuesAreNotEqualAndExpectedEqual() {
    try {
      new ByteAssert(asByte(6)).isEqualTo(asByte(8));
      fail();
    } catch (AssertionError e) {
      assertEquals(e.getMessage(), "expected:<8> but was:<6>");
    }
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreNotEqualAndExpectedEqual() {
    try {
      new ByteAssert(asByte(6)).as("A Test").isEqualTo(asByte(8));
      fail();
    } catch (AssertionError e) {
      assertEquals(e.getMessage(), "[A Test] expected:<8> but was:<6>");
    }
  }

  @Test public void shouldPassIfValuesAreNotEqualAsExpected() {
    new ByteAssert(asByte(6)).isNotEqualTo(asByte(8));
  }

  @Test public void shouldFailIfValuesAreEqualAndExpectingNotEqual() {
    try {
      new ByteAssert(asByte(6)).isNotEqualTo(asByte(6));
      fail();
    } catch (AssertionError e) {
      assertEquals(e.getMessage(), "actual value:<6> should not be equal to:<6>");
    }
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreEqualAndExpectingNotEqual() {
    try {
      new ByteAssert(asByte(6)).as("A Test").isNotEqualTo(asByte(6));
      fail();
    } catch (AssertionError e) {
      assertEquals(e.getMessage(), "[A Test] actual value:<6> should not be equal to:<6>");
    }
  }

  @Test public void shouldPassIfActualIsZeroAsExpected() {
    new ByteAssert(asByte(0)).isZero();
  }

  @Test public void shouldFailIfActualIsNotZeroAndExpectingZero() {
    try {
      new ByteAssert(asByte(6)).isZero();
      fail();
    } catch (AssertionError e) {
      assertEquals(e.getMessage(), "expected:<0> but was:<6>");
    }
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotZeroAndExpectingZero() {
    try {
      new ByteAssert(asByte(6)).as("A Test").isZero();
      fail();
    } catch (AssertionError e) {
      assertEquals(e.getMessage(), "[A Test] expected:<0> but was:<6>");
    }
  }

  @Test public void shouldPassIfActualIsGreaterThanExpectedAsExpected() {
    new ByteAssert(asByte(6)).isGreaterThan(asByte(2));
  }

  @Test public void shouldFailIfActualIsLessThanExpectedAndExpectingToBeGreater() {
    try {
      new ByteAssert(asByte(6)).isGreaterThan(asByte(10));
      fail();
    } catch (AssertionError e) {
      assertEquals(e.getMessage(), "actual value:<6> should be greater than <10>");
    }
  }

  @Test public void shouldFailShowingDescriptionIfActualIsLessThanExpectedAndExpectingToBeGreater() {
    try {
      new ByteAssert(asByte(6)).as("A Test").isGreaterThan(asByte(10));
      fail();
    } catch (AssertionError e) {
      assertEquals(e.getMessage(), "[A Test] actual value:<6> should be greater than <10>");
    }
  }

  @Test public void shouldPassIfActualLessThanExpectedAsExpected() {
    new ByteAssert(asByte(2)).isLessThan(asByte(6));
  }

  @Test public void shouldFailIfActualIsNotLessThanExpectedAndExpectingToBeLess() {
    try {
      new ByteAssert(asByte(10)).isLessThan(asByte(6));
      fail();
    } catch (AssertionError e) {
      assertEquals(e.getMessage(), "actual value:<10> should be less than <6>");
    }
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotLessThanExpectedAndExpectingToBeLess() {
    try {
      new ByteAssert(asByte(10)).as("A Test").isLessThan(asByte(6));
      fail();
    } catch (AssertionError e) {
      assertEquals(e.getMessage(), "[A Test] actual value:<10> should be less than <6>");
    }
  }

  @Test public void shouldPassIfActualIsPositiveAsExpected() {
    new ByteAssert(asByte(6)).isPositive();
  }

  @Test public void shouldFailIfActualIsNotPositiveAndExpectingPositive() {
    try {
      new ByteAssert(asByte(-2)).isPositive();
      fail();
    } catch (AssertionError e) {
      assertEquals(e.getMessage(), "actual value:<-2> should be greater than <0>");
    }
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotPositiveAndExpectingPositive() {
    try {
      new ByteAssert(asByte(-2)).as("A Test").isPositive();
      fail();
    } catch (AssertionError e) {
      assertEquals(e.getMessage(), "[A Test] actual value:<-2> should be greater than <0>");
    }
  }

  @Test public void shouldPassIfActualIsNegativeAsExpected() {
    new ByteAssert(asByte(-2)).isNegative();
  }

  @Test public void shoudlFailIfActualIsNotNegativeAndExpectingNegative() {
    try {
      new ByteAssert(asByte(6)).isNegative();
      fail();
    } catch (AssertionError e) {
      assertEquals(e.getMessage(), "actual value:<6> should be less than <0>");
    }
  }

  @Test public void shoudlFailShowingDescriptionIfActualIsNotNegativeAndExpectingNegative() {
    try {
      new ByteAssert(asByte(6)).as("A Test").isNegative();
      fail();
    } catch (AssertionError e) {
      assertEquals(e.getMessage(), "[A Test] actual value:<6> should be less than <0>");
    }
  }

  @Test public void shouldPassIfGreaterOrEqualToAndExpectedGreaterThan() {
    new ByteAssert(asByte(8)).isGreaterOrEqualTo(asByte(8));
    new ByteAssert(asByte(8)).isGreaterOrEqualTo(asByte(6));
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfLessOrEqualToAndExpectedGreaterThan() {
    new ByteAssert(asByte(6)).isGreaterOrEqualTo(asByte(8));
  }

  @Test public void shouldPassIfLessOrEqualToAndExpectedLessThan() {
    new ByteAssert(asByte(8)).isLessOrEqualTo(asByte(8));
    new ByteAssert(asByte(6)).isLessOrEqualTo(asByte(8));
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfGreaterOrEqualToAndExpectedLessThan() {
    new ByteAssert(asByte(8)).isLessOrEqualTo(asByte(6));
  }

  private static byte asByte(int i) {
    return (byte)i;
  }
}
