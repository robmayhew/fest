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

import static org.fest.test.ExpectedFailure.expect;

import org.fest.test.CodeToTest;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link BooleanAssert}</code>.
 *
 * @author Alex Ruiz
 * @author David DIDIER
 */
public class BooleanAssertTest {

  @Test public void shouldPassIfActualValueIsTrueAsAnticipated() {
    new BooleanAssert(true).isTrue();
  }

  @Test public void shouldFailIfActualValueIsTrueAndExpectingFalse() {
    expect(AssertionError.class).withMessage("expected:<false> but was:<true>").on(new CodeToTest() {
      public void run() {
        new BooleanAssert(true).isFalse();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualValueIsTrueAndExpectingFalse() {
    expect(AssertionError.class).withMessage("[A Test] expected:<false> but was:<true>").on(new CodeToTest() {
      public void run() {
        new BooleanAssert(true).as("A Test").isFalse();
      }
    });
  }

  @Test public void shouldPassIfActualValueIsFalseAsAnticipated() {
    new BooleanAssert(false).isFalse();
  }

  @Test public void shouldFailIfActualValueIsFalseAndExpectingTrue() {
    expect(AssertionError.class).withMessage("expected:<true> but was:<false>").on(new CodeToTest() {
      public void run() {
        new BooleanAssert(false).isTrue();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualValueIsFalseAndExpectingTrue() {
    expect(AssertionError.class).withMessage("[A Test] expected:<true> but was:<false>").on(new CodeToTest() {
      public void run() {
        new BooleanAssert(false).as("A Test").isTrue();
      }
    });
  }

  @Test public void shouldFailIfValuesAreNotEqualAndExpectingEqual() {
    expect(AssertionError.class).withMessage("expected:<true> but was:<false>").on(new CodeToTest() {
      public void run() {
        new BooleanAssert(false).isEqualTo(true);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreNotEqualAndExpectingEqual() {
    expect(AssertionError.class).withMessage("[A Test] expected:<true> but was:<false>").on(new CodeToTest() {
      public void run() {
        new BooleanAssert(false).as("A Test").isEqualTo(true);
      }
    });
  }

  @Test public void shouldFailIfValuesAreEqualAndExpectedToBeNotEqual() {
    expect(AssertionError.class)
      .withMessage("actual value:<false> should not be equal to:<false>").on(new CodeToTest() {
        public void run() {
          new BooleanAssert(false).isNotEqualTo(false);
        }
      });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreEqualAndExpectedToBeNotEqual() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<false> should not be equal to:<false>").on(new CodeToTest() {
        public void run() {
          new BooleanAssert(false).as("A Test").isNotEqualTo(false);
        }
      });
  }

  @Test public void shouldPassIfValuesAreNotEqualAsAnticipated() {
    new BooleanAssert(false).isNotEqualTo(true);
  }

  @Test public void shouldPassIfValuesAreEqualAsAnticipated() {
    new BooleanAssert(false).isEqualTo(false);
  }
}
