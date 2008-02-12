/*
 * Created on Mar 1, 2007
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
import static org.fest.util.Arrays.array;
import static org.testng.Assert.*;

import org.fest.test.CodeToTest;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ObjectArrayAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ObjectArrayAssertTest {

  @Test public void shouldPassIfGivenObjectIsInArrayAsAnticipated() {
    new ObjectArrayAssert("Luke", "Leia").contains("Luke");
  }

  @Test public void shouldPassIfGivenObjectsAreInArrayAsAnticipated() {
    new ObjectArrayAssert("Luke", "Leia", "Anakin").contains("Luke", "Leia");
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfContainsValues() {
    expect(AssertionError.class).withMessage("expecting a non-null array, but it was null").on(new CodeToTest() {
      public void run() {
        Object[] array = null;
        new ObjectArrayAssert(array).contains("Luke");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfContainsValues() {
    expect(AssertionError.class)
      .withMessage("[A Test] expecting a non-null array, but it was null").on(new CodeToTest() {
        public void run() {
          Object[] array = null;
          new ObjectArrayAssert(array).as("A Test").contains("Luke");
        }
      });
  }

  @Test public void shouldFailIfGivenObjectIsNotInArray() {
    expect(AssertionError.class).withMessage("array:<[]> does not contain element(s):<['Luke']>").on(new CodeToTest() {
      public void run() {
        new ObjectArrayAssert(new Object[0]).contains("Luke");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfGivenObjectIsNotInArray() {
    expect(AssertionError.class)
      .withMessage("[A Test] array:<[]> does not contain element(s):<['Luke']>").on(new CodeToTest() {
        public void run() {
          new ObjectArrayAssert(new Object[0]).as("A Test").contains("Luke");
        }
      });
  }

  @Test public void shouldPassIfGivenObjectIsNotInArrayAsAnticipated() {
    new ObjectArrayAssert("Luke", "Leia").excludes("Anakin");
  }

  @Test public void shouldPassIfGivenObjectsAreNotInArrayAsAnticipated() {
    new ObjectArrayAssert("Luke", "Leia", "Anakin").excludes("Han", "Yoda");
  }

  @Test public void shouldFailIfActualIncludesValueAndExpectingExclude() {
    expect(AssertionError.class)
      .withMessage("array:<['Luke']> does not exclude element(s):<['Luke']>").on(new CodeToTest() {
        public void run() {
          new ObjectArrayAssert("Luke").excludes("Luke");
        }
      });
  }

  @Test public void shouldFailShowingDescriptionIfActualIncludesValueAndExpectingExclude() {
    expect(AssertionError.class)
      .withMessage("[A Test] array:<['Luke']> does not exclude element(s):<['Luke']>").on(new CodeToTest() {
        public void run() {
          new ObjectArrayAssert("Luke").as("A Test").excludes("Luke");
        }
      });
  }

  @Test public void shouldPassIfArrayIsNullAsAnticipated() {
    new ObjectArrayAssert((Object[])null).isNull();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfArrayIsNotNull() {
    new ObjectArrayAssert(new Object[0]).isNull();
  }

  @Test public void shouldPassIfArrayIsNotNull() {
    new ObjectArrayAssert(new Object[0]).isNotNull();
  }

  @Test(dependsOnMethods = "shouldPassIfArrayIsNotNull", expectedExceptions = AssertionError.class)
  public void shouldFailIfArrayIsNull() {
    new ObjectArrayAssert((Object[])null).isNotNull();
  }

  @Test public void shouldPassIfArrayIsEmpty() {
    new ObjectArrayAssert(new Object[0]).isEmpty();
  }

  @Test(dependsOnMethods = "shouldPassIfArrayIsEmpty" , expectedExceptions = AssertionError.class)
  public void shouldFailIfArrayIsNotEmpty() {
    new ObjectArrayAssert("Luke", "Leia").isEmpty();
  }

  @Test public void shouldPassIfArrayIsNotEmpty() {
    new ObjectArrayAssert("Luke", "Leia").isNotEmpty();
  }

  @Test(dependsOnMethods = "shouldPassIfArrayIsNotEmpty", expectedExceptions = AssertionError.class)
  public void shouldFailIfArrayIsEmpty() {
    new ObjectArrayAssert(new Object[0]).isNotEmpty();
  }

  @Test public void shouldPassIfEqualArrays() {
    new ObjectArrayAssert("Luke", "Leia").isEqualTo(array("Luke", "Leia"));
  }

  @Test(dependsOnMethods = "shouldPassIfEqualArrays")
  public void shouldFailIfNotEqualArrays() {
    try {
      new ObjectArrayAssert("Luke", "Leia").as("Skywalker").isEqualTo(array("Anakin"));
      fail();
    } catch (AssertionError expected) {
      assertEquals(expected.getMessage(), "[Skywalker] expected:<['Anakin']> but was:<['Luke', 'Leia']>");
    }
  }

  @Test public void shouldPassIfNotEqualArrays() {
    new ObjectArrayAssert("Luke", "Leia").isNotEqualTo(array("Yoda"));
  }

  @Test(dependsOnMethods = "shouldPassIfNotEqualArrays")
  public void shouldFailIfEqualArrays() {
    try {
      new ObjectArrayAssert("Luke", "Leia").as("Skywalker").isNotEqualTo(array("Luke", "Leia"));
      fail();
    } catch (AssertionError expected) {
    }
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfArrayIsEmptyWhenLookingForSpecificElements() {
    new ObjectArrayAssert(new Object[0]).containsOnly("Yoda");
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfArrayHasExtraElements() {
    new ObjectArrayAssert("Luke", "Leia", "Anakin").containsOnly("Luke", "Leia");
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfArrayIsMissingElements() {
    new ObjectArrayAssert("Luke", "Leia").containsOnly("Luke", "Leia", "Anakin");
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

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfOneOrMoreElementsDoNotBelongToGivenType() {
    Number[] numbers = { 2d, 4, 5 };
    Assertions.assertThat(numbers).hasAllElementsOfType(Double.class);
  }

  @Test public void shouldPassIfAllElementsBelongToGivenType() {
    Number[] numbers = { 2, 4, 5 };
    Assertions.assertThat(numbers).hasAtLeastOneElementOfType(Integer.class);
  }

  @Test public void shouldPassIfOneElementBelongToSubtypeOfGivenType() {
    Number[] numbers = { 2, 4.0f, 5 };
    Assertions.assertThat(numbers).hasAtLeastOneElementOfType(Integer.class);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfElementsDoNotBelongToGivenType() {
    Number[] numbers = { 2, 4, 5 };
    Assertions.assertThat(numbers).hasAtLeastOneElementOfType(Double.class);
  }
}
