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
 * Tests for <code>{@link LongAssert}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 */
public class LongAssertTest {

  @Test public void shouldPassIfValuesAreEqualAsAnticipated(){
    new LongAssert(8).isEqualTo(8);
  }

  @Test public void shouldFailIfValuesAreNotEqualAndExpectedEqual() {
    expect(AssertionError.class).withMessage("expected:<8> but was:<6>").on(new CodeToTest() {
      public void run() {
        new LongAssert(6).isEqualTo(8);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreNotEqualAndExpectedEqual() {
    expect(AssertionError.class).withMessage("[A Test] expected:<8> but was:<6>").on(new CodeToTest() {
      public void run() {
        new LongAssert(6).as("A Test").isEqualTo(8);
      }
    });
  }

  @Test public void shouldPassIfValuesAreNotEqualAsAnticipated() {
    new LongAssert(6).isNotEqualTo(8);
  }

  @Test public void shouldFailIfValuesAreEqualAndExpectingNotEqual() {
    expect(AssertionError.class).withMessage("actual value:<8> should not be equal to:<8>").on(new CodeToTest() {
      public void run() {
        new LongAssert(8).isNotEqualTo(8);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreEqualAndExpectingNotEqual() {
    expect(AssertionError.class)
    .withMessage("[A Test] actual value:<8> should not be equal to:<8>").on(new CodeToTest() {
      public void run() {
        new LongAssert(8).as("A Test").isNotEqualTo(8);
      }
    });
  }

  @Test public void shouldPassIfActualIsGreaterThanExpectedAsAnticipated() {
    new LongAssert(8).isGreaterThan(6);
  }

  @Test public void shouldFailIfActualIsEqualToExpectedAndExpectingGreaterThan() {
    expect(AssertionError.class).withMessage("actual value:<8> should be greater than:<8>").on(new CodeToTest() {
      public void run() {
        new LongAssert(8).isGreaterThan(8);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsEqualToExpectedAndExpectingGreaterThan() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<8> should be greater than:<8>").on(new CodeToTest() {
        public void run() {
          new LongAssert(8).as("A Test").isGreaterThan(8);
        }
      });
  }

  @Test public void shouldFailIfActualIsLessThanExpectedAndExpectingGreaterThan() {
    expect(AssertionError.class).withMessage("actual value:<6> should be greater than:<8>").on(new CodeToTest() {
      public void run() {
        new LongAssert(6).isGreaterThan(8);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsLessThanExpectedAndExpectingGreaterThan() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<6> should be greater than:<8>").on(new CodeToTest() {
        public void run() {
          new LongAssert(6).as("A Test").isGreaterThan(8);
        }
      });
  }

  @Test public void shouldPassIfActualIsLessThanExpectedAsAnticipated() {
    new LongAssert(6).isLessThan(8);
  }

  @Test public void shouldFailIfActualIsEqualToExpectedAndExpectingLessThan() {
    expect(AssertionError.class).withMessage("actual value:<8> should be less than:<8>").on(new CodeToTest() {
      public void run() {
        new LongAssert(8).isLessThan(8);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsEqualToExpectedAndExpectingLessThan() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<8> should be less than:<8>").on(new CodeToTest() {
        public void run() {
          new LongAssert(8).as("A Test").isLessThan(8);
        }
      });
  }

  @Test public void shouldFailIfGreaterThanAndExpectedLessThan() {
    expect(AssertionError.class).withMessage("actual value:<8> should be less than:<6>").on(new CodeToTest() {
      public void run() {
        new LongAssert(8).isLessThan(6);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfGreaterThanAndExpectedLessThan() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<8> should be less than:<6>").on(new CodeToTest() {
        public void run() {
          new LongAssert(8).as("A Test").isLessThan(6);
        }
      });
  }

  @Test public void shouldPassIfActualIsPositiveAsAnticipated() {
    new LongAssert(6).isPositive();
  }

  @Test public void shouldFailIfActualIsZeroAndExpectingPositive() {
    expect(AssertionError.class).withMessage("actual value:<0> should be greater than:<0>").on(new CodeToTest() {
      public void run() {
        new LongAssert(0).isPositive();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsZeroAndExpectingPositive() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<0> should be greater than:<0>").on(new CodeToTest() {
        public void run() {
          new LongAssert(0).as("A Test").isPositive();
        }
      });
  }

  @Test public void shouldFailIfActualLessToZeroAndExpectingPositive() {
    expect(AssertionError.class).withMessage("actual value:<-8> should be greater than:<0>").on(new CodeToTest() {
      public void run() {
        new LongAssert(-8).isPositive();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualLessToZeroAndExpectingPositive() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<-8> should be greater than:<0>").on(new CodeToTest() {
        public void run() {
          new LongAssert(-8).as("A Test").isPositive();
        }
      });
  }

  @Test public void shouldPassIfActualIsNegativeAsAnticipated() {
    new LongAssert(-6).isNegative();
  }

  @Test public void shouldFailIfActualIsZeroAndExpectingNegative() {
    expect(AssertionError.class).withMessage("actual value:<0> should be less than:<0>").on(new CodeToTest() {
      public void run() {
        new LongAssert(0).isNegative();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsZeroAndExpectingNegative() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<0> should be less than:<0>").on(new CodeToTest() {
        public void run() {
          new LongAssert(0).as("A Test").isNegative();
        }
      });
  }

  @Test public void shouldFailIfActualIsGreaterThanZeroAndExpectedNegative() {
    expect(AssertionError.class).withMessage("actual value:<8> should be less than:<0>").on(new CodeToTest() {
      public void run() {
        new LongAssert(8).isNegative();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsGreaterThanZeroAndExpectedNegative() {
    expect(AssertionError.class).withMessage("[A Test] actual value:<8> should be less than:<0>").on(new CodeToTest() {
      public void run() {
        new LongAssert(8).as("A Test").isNegative();
      }
    });
  }

  @Test public void shouldPassIfActualIsZeroAsAnticipated() {
    new LongAssert(0).isZero();
  }

  @Test public void shouldFailIfNotZeroAndExpectedZero() {
    expect(AssertionError.class).withMessage("expected:<0> but was:<9>").on(new CodeToTest() {
      public void run() {
        new LongAssert(9).isZero();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfNotZeroAndExpectedZero() {
    expect(AssertionError.class).withMessage("[A Test] expected:<0> but was:<9>").on(new CodeToTest() {
      public void run() {
        new LongAssert(9).as("A Test").isZero();
      }
    });
  }

  @Test public void shouldPassIfActualGreaterThanOrEqualToExpectedAsAnticipated() {
    new LongAssert(8).isGreaterOrEqualTo(8).isGreaterOrEqualTo(6);
  }

  @Test public void shouldFailIfActualIsLessThanExpectedAndExpectingGreaterThanOrEqualTo() {
    expect(AssertionError.class)
      .withMessage("actual value:<6> should be greater than or equal to:<8>").on(new CodeToTest() {
        public void run() {
          new LongAssert(6).isGreaterOrEqualTo(8);
        }
      });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsLessThanExpectedAndExpectingGreaterThanOrEqualTo() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<6> should be greater than or equal to:<8>").on(new CodeToTest() {
        public void run() {
          new LongAssert(6).as("A Test").isGreaterOrEqualTo(8);
        }
      });
  }

  @Test public void shouldPassIfActualIsLessThanOrEqualToExpectedAsAnticipated() {
    new LongAssert(6).isLessOrEqualTo(6).isLessOrEqualTo(8);
  }

  @Test public void shouldFailIfGreaterOrEqualToAndExpectedLessThan() {
    expect(AssertionError.class)
      .withMessage("actual value:<8> should be less than or equal to:<6>").on(new CodeToTest() {
        public void run() {
          new LongAssert(8).isLessOrEqualTo(6);
        }
      });
  }

  @Test public void shouldFailShowingDescriptionIfGreaterOrEqualToAndExpectedLessThan() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<8> should be less than or equal to:<6>").on(new CodeToTest() {
        public void run() {
          new LongAssert(8).as("A Test").isLessOrEqualTo(6);
        }
      });
  }
}
