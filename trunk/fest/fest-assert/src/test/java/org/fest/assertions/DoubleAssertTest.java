package org.fest.assertions;

import static org.fest.assertions.DoubleAssert.delta;
import static org.fest.test.ExpectedFailure.expect;

import org.fest.test.CodeToTest;
import org.testng.annotations.Test;

/**
 * Test for <code>{@link DoubleAssert}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 * @author Alex Ruiz
 */
public class DoubleAssertTest {

  @Test public void shouldPassIfValuesAreEqualAsAnticipated() {
    new DoubleAssert(8.68).isEqualTo(8.680);
  }

  @Test public void shouldFailIfNotEqualAndExpectingEqual() {
    expect(AssertionError.class).withMessage("expected:<-0.0> but was:<0.0>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(0.0).isEqualTo(-0.0);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfNotEqualAndExpectingEqual() {
    expect(AssertionError.class).withMessage("[A Test] expected:<-0.0> but was:<0.0>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(0.0).as("A Test").isEqualTo(-0.0);
      }
    });
  }

  @Test public void shouldPassIfValuesAreNotEqualAsAnticipated() {
    new DoubleAssert(8.88).isNotEqualTo(8.68);
  }

  @Test public void shouldFailIfValuesAreEqualAndExpectingNotEqual() {
    expect(AssertionError.class).withMessage("actual value:<8.88> should not be equal to:<8.88>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(8.88).isNotEqualTo(8.88);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreEqualAndExpectingNotEqual() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<8.88> should not be equal to:<8.88>").on(new CodeToTest() {
        public void run() {
          new DoubleAssert(8.88).as("A Test").isNotEqualTo(8.88);
        }
      });
  }

  @Test public void shouldPassIfActualIsGreaterThanExpectedAsAnticipated() {
    new DoubleAssert(0.00).isGreaterThan(-0.00);
  }

  @Test public void shouldFailIfActualIsLessThanExpectedAndExpectingGreaterThan() {
    expect(AssertionError.class).withMessage("actual value:<8.68> should be greater than:<8.88>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(8.68).isGreaterThan(8.88);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsLessThanExpectedAndExpectingGreaterThan() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<8.68> should be greater than:<8.88>").on(new CodeToTest() {
        public void run() {
          new DoubleAssert(8.68).as("A Test").isGreaterThan(8.88);
        }
      });
  }

  @Test public void shouldFailIfActualIsEqualToExpectedAndExpectingGreaterThan() {
    expect(AssertionError.class).withMessage("actual value:<8.68> should be greater than:<8.68>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(8.68).isGreaterThan(8.68);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsEqualToExpectedAndExpectingGreaterThan() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<8.68> should be greater than:<8.68>").on(new CodeToTest() {
        public void run() {
          new DoubleAssert(8.68).as("A Test").isGreaterThan(8.68);
        }
      });
  }

  @Test public void shouldPassIfActualIsLessThanExpectedAsAnticipated() {
    new DoubleAssert(-0.0).isLessThan(0.0);
  }

  @Test public void shouldFailIfActualIsEqualToExpectedAndExpectingLessThan() {
    expect(AssertionError.class).withMessage("actual value:<6.68> should be less than:<6.68>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(6.68).isLessThan(6.68);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsEqualToExpectedAndExpectingLessThan() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<6.68> should be less than:<6.68>").on(new CodeToTest() {
        public void run() {
          new DoubleAssert(6.68).as("A Test").isLessThan(6.68);
        }
      });
  }

  @Test public void shouldFailIfActualIsGreaterThanExpectedAndExpectingLessThan() {
    expect(AssertionError.class).withMessage("actual value:<6.88> should be less than:<6.68>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(6.88).isLessThan(6.68);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsGreaterThanExpectedAndExpectingLessThan() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<6.88> should be less than:<6.68>").on(new CodeToTest() {
        public void run() {
          new DoubleAssert(6.88).as("A Test").isLessThan(6.68);
        }
      });
  }

  @Test public void shouldPassIfActualIsPositiveAsAnticipated() {
    new DoubleAssert(6.68).isPositive();
  }

  @Test public void shouldFailIfNotPositiveAndExpectingPositive() {
    expect(AssertionError.class).withMessage("actual value:<-6.68> should be greater than:<0.0>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(-6.68).isPositive();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfNotPositiveAndExpectingPositive() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<-6.68> should be greater than:<0.0>").on(new CodeToTest() {
        public void run() {
          new DoubleAssert(-6.68).as("A Test").isPositive();
        }
      });
  }

  @Test public void shouldPassIfActualIsNegativeAsAnticipated() {
    new DoubleAssert(-6.68).isNegative();
  }

  @Test public void shouldFailIfNotNegativeAndExpectingNegative() {
    expect(AssertionError.class).withMessage("actual value:<6.68> should be less than:<0.0>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(6.68).isNegative();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfNotNegativeAndExpectingNegative() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<6.68> should be less than:<0.0>").on(new CodeToTest() {
        public void run() {
          new DoubleAssert(6.68).as("A Test").isNegative();
        }
      });
  }

  @Test public void shouldPassIfActualIsNaNAsAnticipated() {
    new DoubleAssert(Double.NaN).isNaN();
  }

  @Test public void shouldFailIfActualIsNotNaNAndExpectingNaN() {
    expect(AssertionError.class).withMessage("expected:<NaN> but was:<6.68>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(6.68).isNaN();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotNaNAndExpectingNaN() {
    expect(AssertionError.class).withMessage("[A Test] expected:<NaN> but was:<6.68>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(6.68).as("A Test").isNaN();
      }
    });
  }

  @Test public void shouldPassIfActualIsEqualToExpectedUsingDeltaAsAnticipated() {
    new DoubleAssert(8.688).isEqualTo(8.68, delta(0.009));
  }

  @Test public void shouldFailIfValuesNotEqualUsingDeltaAndExpectingEqual() {
    expect(AssertionError.class)
      .withMessage("expected:<8.888> but was:<8.688> using delta:<0.0090>").on(new CodeToTest() {
        public void run() {
          new DoubleAssert(8.688).isEqualTo(8.888, delta(0.009));
        }
      });
  }

  @Test public void shouldFailShowingDescriptionIfValuesNotEqualUsingDeltaAndExpectingEqual() {
    expect(AssertionError.class)
      .withMessage("[A Test] expected:<8.888> but was:<8.688> using delta:<0.0090>").on(new CodeToTest() {
        public void run() {
          new DoubleAssert(8.688).as("A Test").isEqualTo(8.888, delta(0.009));
        }
      });
  }

  @Test public void shouldPassIfActualIsZeroAsAnticipated() {
    new DoubleAssert(0).isZero();
  }

  @Test public void shouldFailIfActualIsNotZeroAndExpectingZero() {
    expect(AssertionError.class).withMessage("expected:<0.0> but was:<9.0>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(9).isZero();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotZeroAndExpectingZero() {
    expect(AssertionError.class).withMessage("[A Test] expected:<0.0> but was:<9.0>").on(new CodeToTest() {
      public void run() {
        new DoubleAssert(9).as("A Test").isZero();
      }
    });
  }

  @Test public void shouldPassIfActualIsGreaterThanOrEqualToExpectedAsAnticipated() {
    new DoubleAssert(8.8).isGreaterOrEqualTo(8.8).isGreaterOrEqualTo(6.6);
  }

  @Test public void shouldFailIfActualIsLessThanExpectedAndExpectingGreaterThanOrEqual() {
    expect(AssertionError.class)
      .withMessage("actual value:<6.6> should be greater than or equal to:<8.8>").on(new CodeToTest() {
        public void run() {
          new DoubleAssert(6.6).isGreaterOrEqualTo(8.8);
        }
      });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsLessThanExpectedAndExpectingGreaterThanOrEqual() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<6.6> should be greater than or equal to:<8.8>").on(new CodeToTest() {
        public void run() {
          new DoubleAssert(6.6).as("A Test").isGreaterOrEqualTo(8.8);
        }
      });
  }

  @Test public void shouldPassIfActualIsLessThanOrEqualToExpectedAsAnticipated() {
    new DoubleAssert(6.6).isLessOrEqualTo(6.6).isLessOrEqualTo(8.8);
  }

  @Test public void shouldFailIfActualIsGreaterThanExpectedAndExpectingLessThanOrEqualTo() {
    expect(AssertionError.class)
      .withMessage("actual value:<8.8> should be less than or equal to:<6.6>").on(new CodeToTest() {
        public void run() {
          new DoubleAssert(8.8).isLessOrEqualTo(6.6);
        }
      });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsGreaterThanExpectedAndExpectingLessThanOrEqualTo() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<8.8> should be less than or equal to:<6.6>").on(new CodeToTest() {
        public void run() {
          new DoubleAssert(8.8).as("A Test").isLessOrEqualTo(6.6);
        }
      });
  }
}
