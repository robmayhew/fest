/*
 * Created on Feb 14, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.test.ExpectedFailure.expectAssertionError;
import static org.testng.Assert.*;

import org.fest.test.CodeToTest;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link BooleanArrayAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BooleanArrayAssertTest {

  private static final boolean[] NULL_ARRAY = null;
  private static final boolean[] EMPTY_ARRAY = new boolean[0];

  @Test public void shouldSetDescription() {
    BooleanArrayAssert assertion = new BooleanArrayAssert(true);
    assertNull(assertion.description());
    assertion.as("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescriptionSafelyForGroovy() {
    BooleanArrayAssert assertion = new BooleanArrayAssert(true);
    assertNull(assertion.description());
    assertion.describedAs("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  private static class EmptyOrNullArrayCondition extends Condition<boolean[]> {
    @Override public boolean matches(boolean[] array) {
      return array == null || array.length == 0;
    }
  }

  @Test public void shouldPassIfConditionSatisfied() {
    new BooleanArrayAssert(EMPTY_ARRAY).satisfies(new EmptyOrNullArrayCondition());
  }

  @Test public void shouldFailIfConditionNotSatisfied() {
    expectAssertionError("condition failed with:<[true]>").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(true).satisfies(new EmptyOrNullArrayCondition());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfied() {
    expectAssertionError("[A Test] condition failed with:<[true]>").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(true).as("A Test").satisfies(new EmptyOrNullArrayCondition());
      }
    });
  }

  @Test public void shouldFailIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("expected:<Empty or null array> but was:<[true]>").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(true).satisfies(new EmptyOrNullArrayCondition().as("Empty or null array"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("[A Test] expected:<Empty or null array> but was:<[true]>").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(true).as("A Test").satisfies(new EmptyOrNullArrayCondition().as("Empty or null array"));
      }
    });
  }

  @Test public void shouldPassIfGivenObjectsIsInArray() {
    new BooleanArrayAssert(true).contains(true);
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfContainsValues() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(NULL_ARRAY).contains(true);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfContainsValues() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(NULL_ARRAY).as("A Test").contains(true);
      }
    });
  }

  @Test public void shouldFailIfGivenObjectIsNotInArray() {
    expectAssertionError("array:<[]> does not contain element(s):<[true]>").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(EMPTY_ARRAY).contains(true);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfGivenObjectIsNotInArray() {
    expectAssertionError("[A Test] array:<[]> does not contain element(s):<[true]>").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(EMPTY_ARRAY).as("A Test").contains(true);
      }
    });
  }

  @Test public void shouldPassIfGivenObjectsAreNotInArray() {
    new BooleanArrayAssert(true).excludes(false);
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfIncludesValues() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new BooleanArrayAssert(NULL_ARRAY).excludes(true);
      }
    });
  }

  @Test public void shouldFailShowindDescriptionIfActualIsNullWhenCheckingIfIncludesValues() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new BooleanArrayAssert(NULL_ARRAY).as("A Test").excludes(true);
      }
    });
  }

  @Test public void shouldFailIfActualIncludesValueAndExpectingExclude() {
    expectAssertionError("array:<[true]> does not exclude element(s):<[true]>").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(true).excludes(true);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIncludesValueAndExpectingExclude() {
    expectAssertionError("[A Test] array:<[true]> does not exclude element(s):<[true]>").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(true).as("A Test").excludes(true);
      }
    });
  }

  @Test public void shouldPassIfActualIsNull() {
    new BooleanArrayAssert(NULL_ARRAY).isNull();
  }

  @Test public void shouldFailIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("<[]> should be null").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(EMPTY_ARRAY).isNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("[A Test] <[]> should be null").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(EMPTY_ARRAY).as("A Test").isNull();
      }
    });
  }

  @Test public void shouldPassIfActualIsNotNull() {
    new BooleanArrayAssert(EMPTY_ARRAY).isNotNull();
  }

  @Test public void shouldFailIfActualIsNullAndExpectingNotNull() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(NULL_ARRAY).isNotNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingNotNull() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(NULL_ARRAY).as("A Test").isNotNull();
      }
    });
  }

  @Test public void shouldPassIfActualIsEmpty() {
    new BooleanArrayAssert(EMPTY_ARRAY).isEmpty();
  }

  @Test public void shouldFailIfActualIsNullAndExpectingEmpty() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(NULL_ARRAY).isEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingEmpty() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(NULL_ARRAY).as("A Test").isEmpty();
      }
    });
  }

  @Test public void shouldFailIfActualIsNotEmptyAndExpectingEmpty() {
    expectAssertionError("expecting empty array, but was:<[true]>").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(true).isEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotEmptyAndExpectingEmpty() {
    expectAssertionError("[A Test] expecting empty array, but was:<[true]>").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(true).as("A Test").isEmpty();
      }
    });
  }

  @Test public void shouldPassIfActualIsNotEmpty() {
    new BooleanArrayAssert(true).isNotEmpty();
  }

  @Test public void shouldFailIfArrayIsEmpty() {
    expectAssertionError("expecting a non-empty array, but it was empty").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(EMPTY_ARRAY).isNotEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArrayIsEmpty() {
    expectAssertionError("[A Test] expecting a non-empty array, but it was empty").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(EMPTY_ARRAY).as("A Test").isNotEmpty();
      }
    });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingForNotEmpty() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new BooleanArrayAssert(NULL_ARRAY).isNotEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingForNotEmpty() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new BooleanArrayAssert(NULL_ARRAY).as("A Test").isNotEmpty();
      }
    });
  }

  @Test public void shouldPassIfArraysAreEqual() {
    new BooleanArrayAssert(true).isEqualTo(array(true));
  }

  @Test public void shouldFailIfArraysAreNotEqualAndExpectingEqual() {
    expectAssertionError("expected:<[false]> but was:<[true]>").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(true).isEqualTo(array(false));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArraysAreNotEqualAndExpectingEqual() {
    expectAssertionError("[A Test] expected:<[false]> but was:<[true]>").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(true).as("A Test").isEqualTo(array(false));
      }
    });
  }

  @Test public void shouldPassIfArraysAreNotEqual() {
    new BooleanArrayAssert(true).isNotEqualTo(array(false));
  }

  @Test public void shouldFailIfArraysAreEqualAndExpectingNotEqual() {
    expectAssertionError("actual value:<[true]> should not be equal to:<[true]>").on(
        new CodeToTest() {
          public void run() {
            new BooleanArrayAssert(true).isNotEqualTo(array(true));
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfArraysAreEqualAndExpectingNotEqual() {
    expectAssertionError("[A Test] actual value:<[true]> should not be equal to:<[true]>").on(
        new CodeToTest() {
          public void run() {
            new BooleanArrayAssert(true).as("A Test").isNotEqualTo(array(true));
          }
        });
  }

  @Test public void shouldPassIfActualContainsOnlyExpectedElements() {
    new BooleanArrayAssert(true).containsOnly(true);
  }

  @Test public void shouldFailIfArrayIsEmptyWhenLookingForSpecificElements() {
    expectAssertionError("array:<[]> does not contain element(s):<[false]>").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(EMPTY_ARRAY).containsOnly(array(false));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArrayIsEmptyWhenLookingForSpecificElements() {
    expectAssertionError("[A Test] array:<[]> does not contain element(s):<[false]>").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(EMPTY_ARRAY).as("A Test").containsOnly(array(false));
      }
    });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfContainsOnly() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new BooleanArrayAssert(NULL_ARRAY).containsOnly(array(false));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfContainsOnly() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new BooleanArrayAssert(NULL_ARRAY).as("A Test").containsOnly(array(false));
      }
    });
  }

  @Test public void shouldFailIfActualHasExtraElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("unexpected element(s):<[false]> in array:<[true, false]>").on(
        new CodeToTest() {
          public void run() {
            new BooleanArrayAssert(true, false).containsOnly(array(true));
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfActualHasExtraElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("[A Test] unexpected element(s):<[false]> in array:<[true, false]>").on(
        new CodeToTest() {
          public void run() {
            new BooleanArrayAssert(true, false).as("A Test").containsOnly(array(true));
          }
        });
  }

  @Test public void shouldFailIfActualIsMissingElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("array:<[true]> does not contain element(s):<[false]>").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(true).containsOnly(array(true, false));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsMissingElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("[A Test] array:<[true]> does not contain element(s):<[false]>").on(
        new CodeToTest() {
          public void run() {
            new BooleanArrayAssert(true).as("A Test").containsOnly(array(false));
          }
        });
  }

  @Test public void shouldPassIfActualHasExpectedSize() {
    boolean[] array = array(true, false, true);
    new BooleanArrayAssert(array).hasSize(3);
  }

  @Test public void shouldFailIfActualDoesNotHaveExpectedSize() {
    expectAssertionError("expected size:<2> but was:<1> for array:<[true]>").on(new CodeToTest() {
      public void run() {
        boolean[] array = array(true);
        new BooleanArrayAssert(array).hasSize(2);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualDoesNotHaveExpectedSize() {
    expectAssertionError("[A Test] expected size:<2> but was:<1> for array:<[true]>").on(new CodeToTest() {
      public void run() {
        boolean[] array = array(true);
        new BooleanArrayAssert(array).as("A Test").hasSize(2);
      }
    });
  }

  @Test public void shouldPassIfArraysAreSame() {
    boolean[] array = array(true);
    new BooleanArrayAssert(array).isSameAs(array);
  }

  @Test public void shouldFailIfActualIsNotSameAsExpectedAndExpectingSame() {
    expectAssertionError("expected same instance but found:<[true]> and:<[]>").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(true).isSameAs(EMPTY_ARRAY);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotSameAsExpectedAndExpectingSame() {
    expectAssertionError("[A Test] expected same instance but found:<[true]> and:<[]>").on(new CodeToTest() {
      public void run() {
        new BooleanArrayAssert(true).as("A Test").isSameAs(EMPTY_ARRAY);
      }
    });
  }

  @Test public void shouldFailIfActualIsSameAsExpectedAndExpectingNotSame() {
    expectAssertionError("given objects are same:<[true]>").on(new CodeToTest() {
      public void run() {
        boolean[] array = array(true);
        new BooleanArrayAssert(array).isNotSameAs(array);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsSameAsExpectedAndExpectingNotSame() {
    expectAssertionError("[A Test] given objects are same:<[true]>").on(new CodeToTest() {
      public void run() {
        boolean[] array = array(true);
        new BooleanArrayAssert(array).as("A Test").isNotSameAs(array);
      }
    });
  }

  @Test public void shouldPassIfArraysAreNotSame() {
    new BooleanArrayAssert(true).isNotSameAs(EMPTY_ARRAY);
  }

  private void shouldFailIfActualIsNull(CodeToTest codeToTest) {
    expectAssertionError("expecting a non-null array, but it was null").on(codeToTest);
  }

  private void shouldFailShowingDescriptionIfActualIsNull(CodeToTest codeToTest) {
    expectAssertionError("[A Test] expecting a non-null array, but it was null").on(codeToTest);
  }

  private boolean[] array(boolean... args) { return args; }
}
