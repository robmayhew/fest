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
 * Tests for <code>{@link CharArrayAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class CharArrayAssertTest {

  private static final char[] NULL_ARRAY = null;
  private static final char[] EMPTY_ARRAY = new char[0];

  @Test public void shouldSetDescription() {
    CharArrayAssert assertion = new CharArrayAssert('a', 'b');
    assertNull(assertion.description());
    assertion.as("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescriptionSafelyForGroovy() {
    CharArrayAssert assertion = new CharArrayAssert('a', 'b');
    assertNull(assertion.description());
    assertion.describedAs("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  private static class EmptyOrNullArrayCondition extends Condition<char[]> {
    @Override public boolean matches(char[] array) {
      return array == null || array.length == 0;
    }
  }

  @Test public void shouldPassIfConditionSatisfied() {
    new CharArrayAssert(EMPTY_ARRAY).satisfies(new EmptyOrNullArrayCondition());
  }

  @Test public void shouldFailIfConditionNotSatisfied() {
    expectAssertionError("condition failed with:<[a, b]>").on(new CodeToTest() {
      public void run() {
        new CharArrayAssert('a', 'b').satisfies(new EmptyOrNullArrayCondition());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfied() {
    expectAssertionError("[A Test] condition failed with:<[a, b]>").on(new CodeToTest() {
      public void run() {
        new CharArrayAssert('a', 'b').as("A Test").satisfies(new EmptyOrNullArrayCondition());
      }
    });
  }

  @Test public void shouldFailIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("expected:<Empty or null array> but was:<[a, b]>").on(new CodeToTest() {
      public void run() {
        new CharArrayAssert('a', 'b').satisfies(new EmptyOrNullArrayCondition().as("Empty or null array"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("[A Test] expected:<Empty or null array> but was:<[a, b]>").on(new CodeToTest() {
      public void run() {
        new CharArrayAssert('a', 'b').as("A Test").satisfies(new EmptyOrNullArrayCondition().as("Empty or null array"));
      }
    });
  }

  @Test public void shouldPassIfGivenObjectsIsInArray() {
    new CharArrayAssert('a', 'b').contains('a', 'b');
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfContainsValues() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new CharArrayAssert(NULL_ARRAY).contains('a', 'b');
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfContainsValues() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new CharArrayAssert(NULL_ARRAY).as("A Test").contains('a', 'b');
      }
    });
  }

  @Test public void shouldFailIfGivenObjectIsNotInArray() {
    expectAssertionError("array:<[]> does not contain element(s):<[a, b]>").on(new CodeToTest() {
      public void run() {
        new CharArrayAssert(EMPTY_ARRAY).contains('a', 'b');
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfGivenObjectIsNotInArray() {
    expectAssertionError("[A Test] array:<[]> does not contain element(s):<[a, b]>").on(new CodeToTest() {
      public void run() {
        new CharArrayAssert(EMPTY_ARRAY).as("A Test").contains('a', 'b');
      }
    });
  }

  @Test public void shouldPassIfGivenObjectsAreNotInArray() {
    new CharArrayAssert('a', 'b').excludes('c', 'd');
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfIncludesValues() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new CharArrayAssert(NULL_ARRAY).excludes('a', 'b');
      }
    });
  }

  @Test public void shouldFailShowindDescriptionIfActualIsNullWhenCheckingIfIncludesValues() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new CharArrayAssert(NULL_ARRAY).as("A Test").excludes('a', 'b');
      }
    });
  }

  @Test public void shouldFailIfActualIncludesValueAndExpectingExclude() {
    expectAssertionError("array:<[a, b]> does not exclude element(s):<[a]>").on(new CodeToTest() {
      public void run() {
        new CharArrayAssert('a', 'b').excludes('a');
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIncludesValueAndExpectingExclude() {
    expectAssertionError("[A Test] array:<[a, b]> does not exclude element(s):<[a]>").on(new CodeToTest() {
      public void run() {
        new CharArrayAssert('a', 'b').as("A Test").excludes('a');
      }
    });
  }

  @Test public void shouldPassIfActualIsNull() {
    new CharArrayAssert(NULL_ARRAY).isNull();
  }

  @Test public void shouldFailIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("<[]> should be null").on(new CodeToTest() {
      public void run() {
        new CharArrayAssert(EMPTY_ARRAY).isNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("[A Test] <[]> should be null").on(new CodeToTest() {
      public void run() {
        new CharArrayAssert(EMPTY_ARRAY).as("A Test").isNull();
      }
    });
  }

  @Test public void shouldPassIfActualIsNotNull() {
    new CharArrayAssert(EMPTY_ARRAY).isNotNull();
  }

  @Test public void shouldFailIfActualIsNullAndExpectingNotNull() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new CharArrayAssert(NULL_ARRAY).isNotNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingNotNull() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new CharArrayAssert(NULL_ARRAY).as("A Test").isNotNull();
      }
    });
  }

  @Test public void shouldPassIfActualIsEmpty() {
    new CharArrayAssert(EMPTY_ARRAY).isEmpty();
  }

  @Test public void shouldFailIfActualIsNullAndExpectingEmpty() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new CharArrayAssert(NULL_ARRAY).isEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingEmpty() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new CharArrayAssert(NULL_ARRAY).as("A Test").isEmpty();
      }
    });
  }

  @Test public void shouldFailIfActualIsNotEmptyAndExpectingEmpty() {
    expectAssertionError("expecting empty array, but was:<[a, b]>").on(new CodeToTest() {
      public void run() {
        new CharArrayAssert('a', 'b').isEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotEmptyAndExpectingEmpty() {
    expectAssertionError("[A Test] expecting empty array, but was:<[a, b]>").on(new CodeToTest() {
      public void run() {
        new CharArrayAssert('a', 'b').as("A Test").isEmpty();
      }
    });
  }

  @Test public void shouldPassIfActualIsNotEmpty() {
    new CharArrayAssert('a', 'b').isNotEmpty();
  }

  @Test public void shouldFailIfArrayIsEmpty() {
    expectAssertionError("expecting a non-empty array, but it was empty").on(new CodeToTest() {
      public void run() {
        new CharArrayAssert(EMPTY_ARRAY).isNotEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArrayIsEmpty() {
    expectAssertionError("[A Test] expecting a non-empty array, but it was empty").on(new CodeToTest() {
      public void run() {
        new CharArrayAssert(EMPTY_ARRAY).as("A Test").isNotEmpty();
      }
    });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingForNotEmpty() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new CharArrayAssert(NULL_ARRAY).isNotEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingForNotEmpty() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new CharArrayAssert(NULL_ARRAY).as("A Test").isNotEmpty();
      }
    });
  }

  @Test public void shouldPassIfArraysAreEqual() {
    new CharArrayAssert('a', 'b').isEqualTo(array('a', 'b'));
  }

  @Test public void shouldFailIfArraysAreNotEqualAndExpectingEqual() {
    expectAssertionError("expected:<[c, d]> but was:<[a, b]>").on(new CodeToTest() {
      public void run() {
        new CharArrayAssert('a', 'b').isEqualTo(array('c', 'd'));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArraysAreNotEqualAndExpectingEqual() {
    expectAssertionError("[A Test] expected:<[c, d]> but was:<[a, b]>").on(new CodeToTest() {
      public void run() {
        new CharArrayAssert('a', 'b').as("A Test").isEqualTo(array('c', 'd'));
      }
    });
  }

  @Test public void shouldPassIfArraysAreNotEqual() {
    new CharArrayAssert('a', 'b').isNotEqualTo(array('a'));
  }

  @Test public void shouldFailIfArraysAreEqualAndExpectingNotEqual() {
    expectAssertionError("actual value:<[a, b]> should not be equal to:<[a, b]>").on(
        new CodeToTest() {
          public void run() {
            new CharArrayAssert('a', 'b').isNotEqualTo(array('a', 'b'));
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfArraysAreEqualAndExpectingNotEqual() {
    expectAssertionError("[A Test] actual value:<[a, b]> should not be equal to:<[a, b]>").on(
        new CodeToTest() {
          public void run() {
            new CharArrayAssert('a', 'b').as("A Test").isNotEqualTo(array('a', 'b'));
          }
        });
  }

  @Test public void shouldPassIfActualContainsOnlyExpectedElements() {
    new CharArrayAssert('a').containsOnly('a');
  }

  @Test public void shouldFailIfArrayIsEmptyWhenLookingForSpecificElements() {
    expectAssertionError("array:<[]> does not contain element(s):<[c, d]>").on(new CodeToTest() {
      public void run() {
        new CharArrayAssert(EMPTY_ARRAY).containsOnly(array('c', 'd'));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArrayIsEmptyWhenLookingForSpecificElements() {
    expectAssertionError("[A Test] array:<[]> does not contain element(s):<[c, d]>").on(new CodeToTest() {
      public void run() {
        new CharArrayAssert(EMPTY_ARRAY).as("A Test").containsOnly(array('c', 'd'));
      }
    });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfContainsOnly() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new CharArrayAssert(NULL_ARRAY).containsOnly(array('c', 'd'));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfContainsOnly() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new CharArrayAssert(NULL_ARRAY).as("A Test").containsOnly(array('c', 'd'));
      }
    });
  }

  @Test public void shouldFailIfActualHasExtraElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("unexpected element(s):<[b]> in array:<[a, b]>").on(
        new CodeToTest() {
          public void run() {
            new CharArrayAssert('a', 'b').containsOnly(array('a'));
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfActualHasExtraElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("[A Test] unexpected element(s):<[b]> in array:<[a, b]>").on(
        new CodeToTest() {
          public void run() {
            new CharArrayAssert('a', 'b').as("A Test").containsOnly(array('a'));
          }
        });
  }

  @Test public void shouldFailIfActualIsMissingElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("array:<[a, b]> does not contain element(s):<[c]>").on(new CodeToTest() {
      public void run() {
        new CharArrayAssert('a', 'b').containsOnly(array('c'));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsMissingElementsWhenCheckingIfContainsOnly() {
    expectAssertionError("[A Test] array:<[a, b]> does not contain element(s):<[c]>").on(
        new CodeToTest() {
          public void run() {
            new CharArrayAssert('a', 'b').as("A Test").containsOnly(array('c'));
          }
        });
  }

  @Test public void shouldPassIfActualHasExpectedSize() {
    char[] array = array('a', 'b', 'c');
    new CharArrayAssert(array).hasSize(3);
  }

  @Test public void shouldFailIfActualDoesNotHaveExpectedSize() {
    expectAssertionError("expected size:<2> but was:<1> for array:<[a]>").on(new CodeToTest() {
      public void run() {
        char[] array = array('a');
        new CharArrayAssert(array).hasSize(2);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualDoesNotHaveExpectedSize() {
    expectAssertionError("[A Test] expected size:<2> but was:<1> for array:<[a]>").on(new CodeToTest() {
      public void run() {
        char[] array = array('a');
        new CharArrayAssert(array).as("A Test").hasSize(2);
      }
    });
  }

  @Test public void shouldPassIfArraysAreSame() {
    char[] array = array('a', 'b');
    new CharArrayAssert(array).isSameAs(array);
  }

  @Test public void shouldFailIfActualIsNotSameAsExpectedAndExpectingSame() {
    expectAssertionError("expected same instance but found:<[a, b]> and:<[]>").on(new CodeToTest() {
      public void run() {
        new CharArrayAssert('a', 'b').isSameAs(EMPTY_ARRAY);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotSameAsExpectedAndExpectingSame() {
    expectAssertionError("[A Test] expected same instance but found:<[a, b]> and:<[]>").on(new CodeToTest() {
      public void run() {
        new CharArrayAssert('a', 'b').as("A Test").isSameAs(EMPTY_ARRAY);
      }
    });
  }

  @Test public void shouldFailIfActualIsSameAsExpectedAndExpectingNotSame() {
    expectAssertionError("given objects are same:<[a, b]>").on(new CodeToTest() {
      public void run() {
        char[] array = array('a', 'b');
        new CharArrayAssert(array).isNotSameAs(array);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsSameAsExpectedAndExpectingNotSame() {
    expectAssertionError("[A Test] given objects are same:<[a, b]>").on(new CodeToTest() {
      public void run() {
        char[] array = array('a', 'b');
        new CharArrayAssert(array).as("A Test").isNotSameAs(array);
      }
    });
  }

  @Test public void shouldPassIfArraysAreNotSame() {
    new CharArrayAssert('a').isNotSameAs(EMPTY_ARRAY);
  }

  private void shouldFailIfActualIsNull(CodeToTest codeToTest) {
    expectAssertionError("expecting a non-null array, but it was null").on(codeToTest);
  }

  private void shouldFailShowingDescriptionIfActualIsNull(CodeToTest codeToTest) {
    expectAssertionError("[A Test] expecting a non-null array, but it was null").on(codeToTest);
  }

  private char[] array(char... args) { return args; }
}
