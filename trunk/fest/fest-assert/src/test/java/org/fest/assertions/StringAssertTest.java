/*
 * Created on Jan 10, 2007
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
 * Tests for <code>{@link StringAssert}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 */
public class StringAssertTest {

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfStringIsNotEmpty() {
    new StringAssert("Luke").isEmpty();
  }

  @Test public void shouldSucceedIfStringIsEmpty() {
    new StringAssert("").isEmpty();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfStringIsNullAndExpectingEmpty() {
    new StringAssert(null).isEmpty();
  }

  @Test public void shouldFailIfStringsAreNotEqual() {
    try {
      new StringAssert("Luke").as("Jedi").isEqualTo("Yoda");
      fail();
    } catch (AssertionError expected) {
      assertEquals(expected.getMessage(), "[Jedi] expected:<'Yoda'> but was:<'Luke'>");
    }
  }

  @Test public void shouldSucceedIfStringsAreEqual() {
    new StringAssert("Anakin").isEqualTo("Anakin");
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfStringDoesNotContainGivenString() {
    new StringAssert("Luke").contains("Yoda");
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfStringDoesNotStartWithGivenString() {
    new StringAssert("Luke").startsWith("uke");
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfStringDoesNotEndWithGivenString() {
    new StringAssert("Luke").endsWith("Luk");
  }

  @Test public void shouldSucceedIfStringContainsGivenString() {
    new StringAssert("Anakin").contains("akin");
  }

  @Test public void shouldSucceedIfStringDoesNotContainGivenString() {
    new StringAssert("Luke").excludes("Yoda");
  }

  @Test public void shouldSucceedIfStringStartsWithGivenString() {
    new StringAssert("Luke").startsWith("Luk");
  }

  @Test public void shouldSucceedIfStringEndsWithGivenString() {
    new StringAssert("Luke").endsWith("uke");
  }

  @Test(expectedExceptions = AssertionError.class)
   public void shouldFailIfStringContainsGivenString() {
    new StringAssert("Anakin").excludes("akin");
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfStringDoesNotMatchGivenRegExp() {
    new StringAssert("Luke 001").matches("^\\d+.*$");
  }

  @Test public void shouldSucceedIfStringMatchesGivenRegExp() {
    new StringAssert("Luke 001").matches("^.*\\d+$");
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfStringMatchesGivenRegExp() {
      new StringAssert("Luke 001").doesNotMatch("^.*\\d+$");
  }

  @Test public void shouldSucceedIfStringDoesNotMatchGivenRegExp() {
    new StringAssert("Luke 001").doesNotMatch("^\\d+.*$");
  }
}
