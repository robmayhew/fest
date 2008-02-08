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

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Tests for <code>{@link BooleanAssert}</code>.
 *
 * @author Alex Ruiz
 * @author David DIDIER
 */
public class BooleanAssertTest {

  @Test public void shouldPassIfActualValueIsTrueAsExpected() {
    new BooleanAssert(true).isTrue();
  }

  @Test public void shouldFailIfActualValueIsTrueAndExpectingFalse() {
    try {
      new BooleanAssert(true).isFalse();
      fail();
    } catch (AssertionError e) {
      assertEquals(e.getMessage(), "expected:<false> but was:<true>");
    }
  }

  @Test public void shouldFailShowingDescriptionIfActualValueIsTrueAndExceptingFalse() {
    try {
      new BooleanAssert(true).as("A Test").isFalse();
      fail();
    } catch (AssertionError e) {
      assertEquals(e.getMessage(), "[A Test] expected:<false> but was:<true>");
    }
  }

  @Test public void shouldPassIfActualValueIsFalseAsExpected() {
    new BooleanAssert(false).isFalse();
  }

  @Test public void shouldFailIfActualValueIsFalseAndExpectingTrue() {
    try {
      new BooleanAssert(false).isTrue();
      fail();
    } catch (AssertionError e) {
      assertEquals(e.getMessage(), "expected:<true> but was:<false>");
    }
  }

  @Test public void shouldFailShowingDescriptionIfActualValueIsFalseAndExpectingTrue() {
    try {
      new BooleanAssert(false).as("A Test").isTrue();
      fail();
    } catch (AssertionError e) {
      assertEquals(e.getMessage(), "[A Test] expected:<true> but was:<false>");
    }
  }

  @Test public void shouldFailIfValuesAreNotEqualAndExpectingEqual() {
    try {
      new BooleanAssert(false).isEqualTo(true);
      fail();
    } catch (AssertionError e) {
      assertEquals(e.getMessage(), "expected:<true> but was:<false>");
    }
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreNotEqualAndExpectingEqual() {
    try {
      new BooleanAssert(false).as("A Test").isEqualTo(true);
      fail();
    } catch (AssertionError e) {
      assertEquals(e.getMessage(), "[A Test] expected:<true> but was:<false>");
    }
  }

  @Test public void shouldFailIfValuesAreEqualAndExpectedToBeNotEqual() {
    try {
      new BooleanAssert(false).isNotEqualTo(false);
      fail();
    } catch (AssertionError e) {
      assertEquals(e.getMessage(), "actual value:<false> should not be equal to:<false>");
    }
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreEqualAndExpectedToBeNotEqual() {
    try {
      new BooleanAssert(false).as("A Test").isNotEqualTo(false);
      fail();
    } catch (AssertionError e) {
      assertEquals(e.getMessage(), "[A Test] actual value:<false> should not be equal to:<false>");
    }
  }

  @Test public void shouldPassIfValuesAreNotEqualAsExpected() {
    new BooleanAssert(false).isNotEqualTo(true);
  }

  @Test public void shouldPassIfValuesAreEqualAsExpected() {
    new BooleanAssert(false).isEqualTo(false);
  }
}
