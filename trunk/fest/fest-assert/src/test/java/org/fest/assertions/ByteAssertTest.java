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

import static org.fest.test.ExpectedFailure.expect;

import org.fest.test.CodeToTest;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ByteAssert}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 */
public class ByteAssertTest {

  @Test public void shouldPassIfValuesAreEqualAsAnticipated() {
    new ByteAssert(asByte(6)).isEqualTo(asByte(6));
  }

  @Test public void shouldFailIfValuesAreNotEqualAndExpectedEqual() {
    expect(AssertionError.class).withMessage("expected:<8> but was:<6>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(6)).isEqualTo(asByte(8));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreNotEqualAndExpectedEqual() {
    expect(AssertionError.class).withMessage("[A Test] expected:<8> but was:<6>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(6)).as("A Test").isEqualTo(asByte(8));
      }
    });
  }

  @Test public void shouldPassIfValuesAreNotEqualAsAnticipated() {
    new ByteAssert(asByte(6)).isNotEqualTo(asByte(8));
  }

  @Test public void shouldFailIfValuesAreEqualAndExpectingNotEqual() {
    expect(AssertionError.class).withMessage("actual value:<6> should not be equal to:<6>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(6)).isNotEqualTo(asByte(6));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreEqualAndExpectingNotEqual() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<6> should not be equal to:<6>").on(new CodeToTest() {
        public void run() {
          new ByteAssert(asByte(6)).as("A Test").isNotEqualTo(asByte(6));
        }
      });
  }

  @Test public void shouldPassIfActualIsZeroAsAnticipated() {
    new ByteAssert(asByte(0)).isZero();
  }

  @Test public void shouldFailIfActualIsNotZeroAndExpectingZero() {
    expect(AssertionError.class).withMessage("expected:<0> but was:<6>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(6)).isZero();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotZeroAndExpectingZero() {
    expect(AssertionError.class).withMessage("[A Test] expected:<0> but was:<6>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(6)).as("A Test").isZero();
      }
    });
  }

  @Test public void shouldPassIfActualIsGreaterThanExpectedAsAnticipated() {
    new ByteAssert(asByte(6)).isGreaterThan(asByte(2));
  }

  @Test public void shouldFailIfActualIsLessThanExpectedAndExpectingToBeGreater() {
    expect(AssertionError.class).withMessage("actual value:<6> should be greater than <10>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(6)).isGreaterThan(asByte(10));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsLessThanExpectedAndExpectingToBeGreater() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<6> should be greater than <10>").on(new CodeToTest() {
        public void run() {
          new ByteAssert(asByte(6)).as("A Test").isGreaterThan(asByte(10));
        }
      });
  }

  @Test public void shouldPassIfActualIsLessThanExpectedAsAnticipated() {
    new ByteAssert(asByte(2)).isLessThan(asByte(6));
  }

  @Test public void shouldFailIfActualIsNotLessThanExpectedAndExpectingToBeLess() {
    expect(AssertionError.class).withMessage("actual value:<10> should be less than <6>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(10)).isLessThan(asByte(6));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotLessThanExpectedAndExpectingToBeLess() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<10> should be less than <6>").on(new CodeToTest() {
        public void run() {
          new ByteAssert(asByte(10)).as("A Test").isLessThan(asByte(6));
        }
      });
  }

  @Test public void shouldPassIfActualIsPositiveAsAnticipated() {
    new ByteAssert(asByte(6)).isPositive();
  }

  @Test public void shouldFailIfActualIsNotPositiveAndExpectingPositive() {
    expect(AssertionError.class).withMessage("actual value:<-2> should be greater than <0>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(-2)).isPositive();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotPositiveAndExpectingPositive() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<-2> should be greater than <0>").on(new CodeToTest() {
        public void run() {
          new ByteAssert(asByte(-2)).as("A Test").isPositive();
        }
      });
  }

  @Test public void shouldPassIfActualIsNegativeAsAnticipated() {
    new ByteAssert(asByte(-2)).isNegative();
  }

  @Test public void shoudlFailIfActualIsNotNegativeAndExpectingNegative() {
    expect(AssertionError.class).withMessage("actual value:<6> should be less than <0>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(6)).isNegative();
      }
    });
  }

  @Test public void shoudlFailShowingDescriptionIfActualIsNotNegativeAndExpectingNegative() {
    expect(AssertionError.class).withMessage("[A Test] actual value:<6> should be less than <0>").on(new CodeToTest() {
      public void run() {
        new ByteAssert(asByte(6)).as("A Test").isNegative();
      }
    });
  }

  @Test public void shouldPassIfActualIsGreaterThanOrEqualToExpectedAsAnticipated() {
    new ByteAssert(asByte(8)).isGreaterOrEqualTo(asByte(8)).isGreaterOrEqualTo(asByte(6));
  }

  @Test public void shouldFailIfActualValueIsLessThanExpectedAndExpectingGreaterThanOrEqualTo() {
    expect(AssertionError.class)
      .withMessage("actual value:<6> should be greater than or equal to:<8>").on(new CodeToTest() {
        public void run() {
          new ByteAssert(asByte(6)).isGreaterOrEqualTo(asByte(8));
        }
      });
  }

  @Test public void shouldFailShowingDescriptionIfActualValueIsLessThanExpectedAndExpectingGreaterThanOrEqualTo() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<6> should be greater than or equal to:<8>").on(new CodeToTest() {
        public void run() {
          new ByteAssert(asByte(6)).as("A Test").isGreaterOrEqualTo(asByte(8));
        }
      });
  }

  @Test public void shouldPassIfActualValueIsLessThanOrEqualToExpectedAsAnticipated() {
    new ByteAssert(asByte(6)).isLessOrEqualTo(asByte(8)).isLessOrEqualTo(asByte(6));
  }

  @Test public void shouldFailIfActualValueIsGreaterThanExpectedAndExpectingLessThanOrEqualTo() {
    expect(AssertionError.class)
      .withMessage("actual value:<8> should be less than or equal to:<6>").on(new CodeToTest() {
        public void run() {
          new ByteAssert(asByte(8)).isLessOrEqualTo(asByte(6));
        }
      });
  }

  @Test public void shouldFailShowingDescriptionIfActualValueIsGreaterThanExpectedAndExpectingLessThanOrEqualTo() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<8> should be less than or equal to:<6>").on(new CodeToTest() {
        public void run() {
          new ByteAssert(asByte(8)).as("A Test").isLessOrEqualTo(asByte(6));
        }
      });
  }

  private static byte asByte(int i) {
    return (byte)i;
  }
}
