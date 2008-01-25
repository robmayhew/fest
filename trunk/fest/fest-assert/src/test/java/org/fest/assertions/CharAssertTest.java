package org.fest.assertions;

import org.testng.annotations.Test;

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

/**
 * Tests for <code>{@link CharAssert}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 */
public class CharAssertTest {

  @Test public void shouldPassIfEqualAndExpectedEqual() {
    new CharAssert('a').isEqualTo('a');
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfNotEqualAndExpectedEqual() {
    new CharAssert('a').isEqualTo('b');
  }

  @Test public void shouldPassIfNotEqualAndExpectedNotEqual() {
    new CharAssert('a').isNotEqualTo('b');
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfEqualAndExpectedNotEqual() {
    new CharAssert('a').isNotEqualTo('a');
  }

  @Test public void shouldPassIfGreaterThanAndExpectedGreaterThan() {
    new CharAssert('a').isGreaterThan('A');
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfNotGreaterThanAndExpectedGreaterThan() {
    new CharAssert('A').isGreaterThan('a');
  }

  @Test public void shouldPassIfLessThanAndExpectedLessThan() {
    new CharAssert('A').isLessThan('a');
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfNotLessThanAndExpectedLessThan() {
    new CharAssert('a').isLessThan('A');
  }

  @Test public void shouldPassIfWildCharAndExpectedWildChar() {
    new CharAssert('A').isLessThan('a');
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfNotWildCharAndExpectedWildChar() {
    new CharAssert('a').isLessThan('A');
  }

  @Test public void shouldPassIfGreaterOrEqualToAndExpectedGreaterThan() {
    new CharAssert('a').isGreaterOrEqualTo('a');
    new CharAssert('a').isGreaterOrEqualTo('A');
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfLessOrEqualToAndExpectedGreaterThan() {
    new CharAssert('A').isGreaterOrEqualTo('a');
  }

  @Test public void shouldPassIfLessOrEqualToAndExpectedLessThan() {
    new CharAssert('a').isLessOrEqualTo('a');
    new CharAssert('A').isLessOrEqualTo('a');
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfGreaterOrEqualToAndExpectedLessThan() {
    new CharAssert('a').isLessOrEqualTo('A');
  }
}
