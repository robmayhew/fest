/*
 * Created on Mar 1, 2007
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

import static org.fest.test.ExpectedFailure.expectAssertionError;
import static org.fest.util.Arrays.array;

import org.fest.test.CodeToTest;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ObjectArrayAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ObjectArrayAssertTest {

  private static final Object[] NULL_ARRAY = null;
  private static final Object[] EMPTY_ARRAY = new Object[0];

  @Test public void shouldPassIfGivenObjectIsInArrayAsAnticipated() {
    new ObjectArrayAssert("Luke", "Leia").contains("Luke");
  }

  @Test public void shouldPassIfGivenObjectsAreInArrayAsAnticipated() {
    new ObjectArrayAssert("Luke", "Leia", "Anakin").contains("Luke", "Leia");
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfContainsValues() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(NULL_ARRAY).contains("Luke");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfContainsValues() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        Object[] array = null;
        new ObjectArrayAssert(array).as("A Test").contains("Luke");
      }
    });
  }

  @Test public void shouldFailIfGivenObjectIsNotInArray() {
    expectAssertionError("array:<[]> does not contain element(s):<['Luke']>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(EMPTY_ARRAY).contains("Luke");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfGivenObjectIsNotInArray() {
    expectAssertionError("[A Test] array:<[]> does not contain element(s):<['Luke']>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(EMPTY_ARRAY).as("A Test").contains("Luke");
      }
    });
  }

  @Test public void shouldPassIfGivenObjectIsNotInArrayAsAnticipated() {
    new ObjectArrayAssert("Luke", "Leia").excludes("Anakin");
  }

  @Test public void shouldPassIfGivenObjectsAreNotInArrayAsAnticipated() {
    new ObjectArrayAssert("Luke", "Leia", "Anakin").excludes("Han", "Yoda");
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfIncludesValues() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new ObjectArrayAssert(NULL_ARRAY).excludes("Han");
      }
    });
  }

  @Test public void shouldFailShowindDescriptionIfActualIsNullWhenCheckingIfIncludesValues() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new ObjectArrayAssert(NULL_ARRAY).as("A Test").excludes("Han");
      }
    });
  }

  @Test public void shouldFailIfActualIncludesValueAndExpectingExclude() {
    expectAssertionError("array:<['Luke']> does not exclude element(s):<['Luke']>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert("Luke").excludes("Luke");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIncludesValueAndExpectingExclude() {
    expectAssertionError("[A Test] array:<['Luke']> does not exclude element(s):<['Luke']>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert("Luke").as("A Test").excludes("Luke");
      }
    });
  }

  @Test public void shouldPassIfActualIsNullAsAnticipated() {
    new ObjectArrayAssert(NULL_ARRAY).isNull();
  }

  @Test public void shouldFailIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("<[]> should be null").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(EMPTY_ARRAY).isNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("[A Test] <[]> should be null").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(EMPTY_ARRAY).as("A Test").isNull();
      }
    });
  }

  @Test public void shouldPassIfActualIsNotNullAsAnticipated() {
    new ObjectArrayAssert(EMPTY_ARRAY).isNotNull();
  }

  @Test public void shouldFailIfActualIsNullAndExpectingNotNull() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(NULL_ARRAY).isNotNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingNotNull() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(NULL_ARRAY).as("A Test").isNotNull();
      }
    });
  }

  @Test public void shouldPassIfActualIsEmptyAsAnticipated() {
    new ObjectArrayAssert(EMPTY_ARRAY).isEmpty();
  }

  @Test public void shouldFailIfActualIsNullAndExpectingEmpty() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(NULL_ARRAY).isEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingEmpty() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(NULL_ARRAY).as("A Test").isEmpty();
      }
    });
  }

  @Test public void shouldFailIfActualIsNotEmptyAndExpectingEmpty() {
    expectAssertionError("expecting empty array, but was:<['Luke', 'Leia']>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert("Luke", "Leia").isEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotEmptyAndExpectingEmpty() {
    expectAssertionError("[A Test] expecting empty array, but was:<['Luke', 'Leia']>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert("Luke", "Leia").as("A Test").isEmpty();
      }
    });
  }

  @Test public void shouldPassIfActualIsNotEmptyAsAnticipated() {
    new ObjectArrayAssert("Luke", "Leia").isNotEmpty();
  }

  @Test public void shouldFailIfArrayIsEmpty() {
    expectAssertionError("expecting a non-empty array, but it was empty").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(EMPTY_ARRAY).isNotEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArrayIsEmpty() {
    expectAssertionError("[A Test] expecting a non-empty array, but it was empty").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(EMPTY_ARRAY).as("A Test").isNotEmpty();
      }
    });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingForNotEmpty() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new ObjectArrayAssert(NULL_ARRAY).isNotEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingForNotEmpty() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new ObjectArrayAssert(NULL_ARRAY).as("A Test").isNotEmpty();
      }
    });
  }

  @Test public void shouldPassIfArraysAreEqualAsAnticipated() {
    new ObjectArrayAssert("Luke", "Leia").isEqualTo(array("Luke", "Leia"));
  }

  @Test public void shouldFailIfArraysAreNotEqualAndExpectingEqual() {
    expectAssertionError("expected:<['Anakin']> but was:<['Luke', 'Leia']>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert("Luke", "Leia").isEqualTo(array("Anakin"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArraysAreNotEqualAndExpectingEqual() {
    expectAssertionError("[A Test] expected:<['Anakin']> but was:<['Luke', 'Leia']>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert("Luke", "Leia").as("A Test").isEqualTo(array("Anakin"));
      }
    });
  }

  @Test public void shouldPassIfArraysAreNotEqualAsAnticipated() {
    new ObjectArrayAssert("Luke", "Leia").isNotEqualTo(array("Yoda"));
  }

  @Test public void shouldFailIfArraysAreEqualAndExpectingNotEqual() {
    expectAssertionError("actual value:<['Luke', 'Leia']> should not be equal to:<['Luke', 'Leia']>").on(
        new CodeToTest() {
          public void run() {
            new ObjectArrayAssert("Luke", "Leia").isNotEqualTo(array("Luke", "Leia"));
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfArraysAreEqualAndExpectingNotEqual() {
    expectAssertionError("[A Test] actual value:<['Luke', 'Leia']> should not be equal to:<['Luke', 'Leia']>").on(
        new CodeToTest() {
          public void run() {
            new ObjectArrayAssert("Luke", "Leia").as("A Test").isNotEqualTo(array("Luke", "Leia"));
          }
        });
  }

  @Test public void shouldFailIfArrayIsEmptyWhenLookingForSpecificElements() {
    expectAssertionError("array:<[]> does not contain element(s):<['Yoda']>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(EMPTY_ARRAY).containsOnly("Yoda");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArrayIsEmptyWhenLookingForSpecificElements() {
    expectAssertionError("[A Test] array:<[]> does not contain element(s):<['Yoda']>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(EMPTY_ARRAY).as("A Test").containsOnly("Yoda");
      }
    });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfContainsOnly() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new ObjectArrayAssert(NULL_ARRAY).containsOnly("Yoda");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfContainsOnly() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new ObjectArrayAssert(NULL_ARRAY).as("A Test").containsOnly("Yoda");
      }
    });
  }

  @Test public void shouldFailIfActualHasExtraElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("unexpected element(s):<['Anakin']> in array:<['Luke', 'Leia', 'Anakin']>").on(
        new CodeToTest() {
          public void run() {
            new ObjectArrayAssert("Luke", "Leia", "Anakin").containsOnly("Luke", "Leia");
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfActualHasExtraElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("[A Test] unexpected element(s):<['Anakin']> in array:<['Luke', 'Leia', 'Anakin']>").on(
        new CodeToTest() {
          public void run() {
            new ObjectArrayAssert("Luke", "Leia", "Anakin").as("A Test").containsOnly("Luke", "Leia");
          }
        });
  }

  @Test public void shouldFailIfActualIsMissingElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("array:<['Luke', 'Leia']> does not contain element(s):<['Anakin']>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert("Luke", "Leia").containsOnly("Luke", "Leia", "Anakin");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsMissingElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("[A Test] array:<['Luke', 'Leia']> does not contain element(s):<['Anakin']>").on(
        new CodeToTest() {
          public void run() {
            new ObjectArrayAssert("Luke", "Leia").as("A Test").containsOnly("Luke", "Leia", "Anakin");
          }
        });
  }

  @Test public void shouldPassIfArrayHasOnlySpecifiedElements() {
    new ObjectArrayAssert("Luke", "Leia").containsOnly("Luke", "Leia");
  }

  @Test public void shouldPassIfElementsBelongToGivenType() {
    Number[] numbers = { 2, 4, 5 };
    Assertions.assertThat(numbers).hasAllElementsOfType(Number.class);
  }

  @Test public void shouldPassIfElementsBelongToSubtypeOfGivenType() {
    Number[] numbers = { 2, 4, 5 };
    Assertions.assertThat(numbers).hasAllElementsOfType(Integer.class);
  }

  @Test public void shouldFailIfOneOrMoreElementsDoNotBelongToGivenType() {
    expectAssertionError("not all elements in array:<[2.0, 4, 5]> belong to the type:<java.lang.Double>").on(
        new CodeToTest() {
          public void run() {
            Number[] numbers = { 2d, 4, 5 };
            Assertions.assertThat(numbers).hasAllElementsOfType(Double.class);
          }
        });
  }

  @Test public void shouldPassIfAllElementsBelongToGivenType() {
    Number[] numbers = { 2, 4, 5 };
    Assertions.assertThat(numbers).hasAtLeastOneElementOfType(Integer.class);
  }

  @Test public void shouldPassIfOneElementBelongToSubtypeOfGivenType() {
    Number[] numbers = { 2, 4.0f, 5 };
    Assertions.assertThat(numbers).hasAtLeastOneElementOfType(Integer.class);
  }

  @Test(expectedExceptions = AssertionError.class) public void shouldFailIfElementsDoNotBelongToGivenType() {
    Number[] numbers = { 2, 4, 5 };
    Assertions.assertThat(numbers).hasAtLeastOneElementOfType(Double.class);
  }

  private void shouldFailIfActualIsNull(CodeToTest codeToTest) {
    expectAssertionError("expecting a non-null array, but it was null").on(codeToTest);
  }

  private void shouldFailShowingDescriptionIfActualIsNull(CodeToTest codeToTest) {
    expectAssertionError("[A Test] expecting a non-null array, but it was null").on(codeToTest);
  }
}
