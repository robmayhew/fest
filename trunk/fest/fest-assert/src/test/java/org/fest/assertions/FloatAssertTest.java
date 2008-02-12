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

import static org.fest.assertions.FloatAssert.delta;
import static org.fest.test.ExpectedFailure.expectAssertionError;

import org.fest.test.CodeToTest;
import org.testng.annotations.Test;

/**
 * Test for <code>{@link FloatAssert}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 */
public class FloatAssertTest {

  @Test public void shouldPassIfValuesAreEqualUsingDeltaAsAnticipated() {
    new FloatAssert(6.6f).isEqualTo(6.6f, delta(0.0f));
  }

  @Test public void shouldFailIfValuesAreNotEqualUsingDeltaAndExpectingEqual() {
    expectAssertionError("expected:<6.8> but was:<6.6> using delta:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(6.6f).isEqualTo(6.8f, delta(0.0f));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreNotEqualUsingDeltaAndExpectingEqual() {
    expectAssertionError("[A Test] expected:<6.8> but was:<6.6> using delta:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(6.6f).as("A Test").isEqualTo(6.8f, delta(0.0f));
      }
    });
  }

  @Test public void shouldPassIfValuesAreNotEqualAsAnticipated() {
    new FloatAssert(0.0f).isNotEqualTo(-0.0f);
  }

  @Test public void shouldFailIfActualIsEqualToExpectedAndExpectingNotEqual() {
    expectAssertionError("actual value:<0.0> should not be equal to:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(0.0f).isNotEqualTo(0.0f);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsEqualToExpectedAndExpectingNotEqual() {
    expectAssertionError("[A Test] actual value:<0.0> should not be equal to:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(0.0f).as("A Test").isNotEqualTo(0.0f);
      }
    });
  }

  @Test public void shouldPassIfActualIsGreaterThanExpectedAsAnticipated() {
    new FloatAssert(0.0f).isGreaterThan(-0.0f);
  }

  @Test public void shouldFailIfActualIsLessThanExpectedAndExpectingGreaterThan() {
    expectAssertionError("actual value:<-0.0> should be greater than:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(-0.0f).isGreaterThan(0.0f);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsLessThanExpectedAndExpectingGreaterThan() {
    expectAssertionError("[A Test] actual value:<-0.0> should be greater than:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(-0.0f).as("A Test").isGreaterThan(0.0f);
      }
    });
  }

  @Test public void shouldFailIfActualIsEqualExpectedAndExpectingGreaterThan() {
    expectAssertionError("actual value:<8.0> should be greater than:<8.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(8f).isGreaterThan(8f);
      }
    });
  }

  @Test public void shouldPassIfActualIsLessThanExpectedAsAnticipated() {
    new FloatAssert(6.6f).isLessThan(6.8f);
  }

  @Test public void shouldFailIfActualIsGreaterThanExpectedAndExpectingLessThan() {
    expectAssertionError("actual value:<0.0> should be less than:<-0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(0.0f).isLessThan(-0.0f);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsGreaterThanExpectedAndExpectingLessThan() {
    expectAssertionError("[A Test] actual value:<0.0> should be less than:<-0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(0.0f).as("A Test").isLessThan(-0.0f);
      }
    });
  }

  @Test public void shouldFailIfActualIsEqualToExpectedAndExpectingLessThan() {
    expectAssertionError("actual value:<0.0> should be less than:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(0.0f).isLessThan(0.0f);
      }
    });
  }

  @Test public void shouldPassIfActualIsZeroAsAnticipated() {
    new FloatAssert(0.0f).isZero();
  }

  @Test public void shouldFailIfActualIsNotZeroAndExpectingZero() {
    expectAssertionError("expected:<0.0> but was:<-0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(-0.0f).isZero();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotZeroAndExpectingZero() {
    expectAssertionError("[A Test] expected:<0.0> but was:<-0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(-0.0f).as("A Test").isZero();
      }
    });
  }

  @Test public void shouldPassIfActualIsNaNAsAnticipated() {
    new FloatAssert(Float.NaN).isNaN();
  }

  @Test public void shouldFailIfNotNaNAndExpectedNaN() {
    expectAssertionError("expected:<NaN> but was:<-0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(-0.0f).isNaN();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfNotNaNAndExpectedNaN() {
    expectAssertionError("[A Test] expected:<NaN> but was:<-0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(-0.0f).as("A Test").isNaN();
      }
    });
  }

  @Test public void shouldPassIfActualIsPositiveAsAnticipated() {
    new FloatAssert(6.6f).isPositive();
  }

  @Test public void shouldFailIfActualIsZeroAndExpectedPositive() {
    expectAssertionError("actual value:<0.0> should be greater than:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(0.0f).isPositive();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsZeroAndExpectedPositive() {
    expectAssertionError("[A Test] actual value:<0.0> should be greater than:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(0.0f).as("A Test").isPositive();
      }
    });
  }

  @Test public void shouldFailIfActualIsLessThanZeroAndExpectedPositive() {
    expectAssertionError("actual value:<-6.6> should be greater than:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(-6.6f).isPositive();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsLessThanZeroAndExpectedPositive() {
    expectAssertionError("[A Test] actual value:<-6.6> should be greater than:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(-6.6f).as("A Test").isPositive();
      }
    });
  }

  @Test public void shouldPassIfActualIsNegativeAsAnticipated() {
    new FloatAssert(-6.6f).isNegative();
  }

  @Test public void shouldFailIfActualIsZeroAndExpectingNegative() {
    expectAssertionError("actual value:<0.0> should be less than:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(0.0f).isNegative();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsZeroAndExpectingNegative() {
    expectAssertionError("[A Test] actual value:<0.0> should be less than:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(0.0f).as("A Test").isNegative();
      }
    });
  }

  @Test public void shouldFailIfActualIsGreaterThanZeroAndExpectingNegative() {
    expectAssertionError("actual value:<6.6> should be less than:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(6.6f).isNegative();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsGreaterThanZeroAndExpectingNegative() {
    expectAssertionError("[A Test] actual value:<6.6> should be less than:<0.0>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(6.6f).as("A Test").isNegative();
      }
    });
  }

  @Test public void shouldPassIfActualIsGreaterThanOrEqualToExpectedAsAnticipated() {
    new FloatAssert(8.8f).isGreaterOrEqualTo(8.8f).isGreaterOrEqualTo(6.6f);
  }

  @Test public void shouldFailIfActualIsLessThanExpectedAndExpectingGreaterThanOrEqualTo() {
    expectAssertionError("actual value:<6.6> should be greater than or equal to:<8.8>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(6.6f).isGreaterOrEqualTo(8.8f);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsLessThanExpectedAndExpectingGreaterThanOrEqualTo() {
    expectAssertionError("[A Test] actual value:<6.6> should be greater than or equal to:<8.8>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(6.6f).as("A Test").isGreaterOrEqualTo(8.8f);
      }
    });
  }

  @Test public void shouldPassIfActualIsLessThanOrEqualToExpectedAsAnticipated() {
    new FloatAssert(6.6f).isLessOrEqualTo(6.6f).isLessOrEqualTo(8.8f);
  }

  @Test public void shouldFailIfActualIsGreaterThanExpectedAndExpectingLessThanOrEqualTo() {
    expectAssertionError("actual value:<8.8> should be less than or equal to:<6.6>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(8.8f).isLessOrEqualTo(6.6f);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsGreaterThanExpectedAndExpectingLessThanOrEqualTo() {
    expectAssertionError("[A Test] actual value:<8.8> should be less than or equal to:<6.6>").on(new CodeToTest() {
      public void run() {
        new FloatAssert(8.8f).as("A Test").isLessOrEqualTo(6.6f);
      }
    });
  }
}
