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
 * Tests for <code>{@link CharAssert}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 * @author Alex Ruiz
 */
public class CharAssertTest {

  @Test public void shouldPassIfValuesAreEqualAsAnticipated() {
    new CharAssert('a').isEqualTo('a');
  }

  @Test public void shouldFailIfValuesAreNotEqualAndExpectingEqual() {
    expect(AssertionError.class).withMessage("expected:<b> but was:<a>").on(new CodeToTest() {
      public void run() {
        new CharAssert('a').isEqualTo('b');
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreNotEqualAndExpectingEqual() {
    expect(AssertionError.class).withMessage("[A Test] expected:<b> but was:<a>").on(new CodeToTest() {
      public void run() {
        new CharAssert('a').as("A Test").isEqualTo('b');
      }
    });
  }

  @Test public void shouldPassIfValueAreNotEqualAsAnticipated() {
    new CharAssert('a').isNotEqualTo('b');
  }

  @Test public void shouldFailIfValuesAreEqualAndExpectingNotEqual() {
    expect(AssertionError.class).withMessage("actual value:<a> should not be equal to:<a>").on(new CodeToTest() {
      public void run() {
        new CharAssert('a').isNotEqualTo('a');
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfValuesAreEqualAndExpectingNotEqual() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<a> should not be equal to:<a>").on(new CodeToTest() {
        public void run() {
          new CharAssert('a').as("A Test").isNotEqualTo('a');
        }
      });
  }

  @Test public void shouldPassIfActualIsGreaterThanExpectedAsAnticipated() {
    new CharAssert('a').isGreaterThan('A');
  }

  @Test public void shouldFailIfActualIsEqualtoExpectedAndExpectingGreaterThan() {
    expect(AssertionError.class).withMessage("actual value:<a> should be greater than:<a>").on(new CodeToTest() {
      public void run() {
        new CharAssert('a').isGreaterThan('a');
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsEqualtoExpectedAndExpectingGreaterThan() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<a> should be greater than:<a>").on(new CodeToTest() {
        public void run() {
          new CharAssert('a').as("A Test").isGreaterThan('a');
        }
      });
  }

  @Test public void shouldFailIfActualIsLessThanExpectedAndExpectingGreaterThan() {
    expect(AssertionError.class).withMessage("actual value:<A> should be greater than:<a>").on(new CodeToTest() {
      public void run() {
        new CharAssert('A').isGreaterThan('a');
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsLessThanExpectedAndExpectingGreaterThan() {
    expect(AssertionError.class)
    .withMessage("[A Test] actual value:<A> should be greater than:<a>").on(new CodeToTest() {
      public void run() {
        new CharAssert('A').as("A Test").isGreaterThan('a');
      }
    });
  }

  @Test public void shouldPassIfActualIsLessThanExpectedAsAnticipated() {
    new CharAssert('A').isLessThan('a');
  }

  @Test public void shouldFailIfActualIsEqualToExpectedAndExpectingLessThan() {
    expect(AssertionError.class).withMessage("actual value:<a> should be less than:<a>").on(new CodeToTest() {
      public void run() {
        new CharAssert('a').isLessThan('a');
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsEqualToExpectedAndExpectingLessThan() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<a> should be less than:<a>").on(new CodeToTest() {
        public void run() {
          new CharAssert('a').as("A Test").isLessThan('a');
        }
      });
  }

  @Test public void shouldFailIfActualIsGreaterThanExpectedAndExpectingLessThan() {
    expect(AssertionError.class).withMessage("actual value:<a> should be less than:<A>").on(new CodeToTest() {
      public void run() {
        new CharAssert('a').isLessThan('A');
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsGreaterThanExpectedAndExpectingLessThan() {
    expect(AssertionError.class).withMessage("[A Test] actual value:<a> should be less than:<A>").on(new CodeToTest() {
      public void run() {
        new CharAssert('a').as("A Test").isLessThan('A');
      }
    });
  }

  @Test public void shouldPassIfActualIsGreaterThanOrEqualToExpectedAsAnticipated() {
    new CharAssert('a').isGreaterOrEqualTo('a').isGreaterOrEqualTo('A');
  }

  @Test public void shouldFailIfActualIsLessThanExpectedAndExpectingGreaterThanOrEqualTo() {
    expect(AssertionError.class)
      .withMessage("actual value:<A> should be greater than or equal to:<a>").on(new CodeToTest() {
        public void run() {
          new CharAssert('A').isGreaterOrEqualTo('a');
        }
      });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsLessThanExpectedAndExpectingGreaterThanOrEqualTo() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<A> should be greater than or equal to:<a>").on(new CodeToTest() {
        public void run() {
          new CharAssert('A').as("A Test").isGreaterOrEqualTo('a');
        }
      });
  }

  @Test public void shouldPassIfActualIsLessOrEqualToExpectedAsAnticipated() {
    new CharAssert('A').isLessOrEqualTo('a').isLessOrEqualTo('A');
  }

  @Test public void shouldFailIfActualIsGreaterThanExpectedAndExpectingLessThanOrEqualTo() {
    expect(AssertionError.class)
      .withMessage("actual value:<a> should be less than or equal to:<A>").on(new CodeToTest() {
        public void run() {
          new CharAssert('a').isLessOrEqualTo('A');
        }
      });
  }

  @Test public void shouldFailShowingMessageIfActualIsGreaterThanExpectedAndExpectingLessThanOrEqualTo() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<a> should be less than or equal to:<A>").on(new CodeToTest() {
        public void run() {
          new CharAssert('a').as("A Test").isLessOrEqualTo('A');
        }
      });
  }
}
