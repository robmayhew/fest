/*
 * Created on Jan 10, 2007
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

import static org.fest.test.ExpectedFailure.expectAssertionError;
import static org.fest.util.Collections.list;

import java.util.ArrayList;
import java.util.List;

import org.fest.test.CodeToTest;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link CollectionAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class CollectionAssertTest {

  private static final ArrayList<String> EMPTY_COLLECTION = new ArrayList<String>();

  @Test public void shouldPassIfActualContainsValueAsAnticipated() {
    new CollectionAssert(list("Luke", "Leia")).contains("Luke");
  }

  @Test public void shouldPassIfActualContainsValuesAsAnticipated() {
    new CollectionAssert(list("Luke", "Leia", "Anakin")).contains("Luke", "Leia");
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfActualContainsObjects() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).contains("Luke");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfActualContainsObjects() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).as("A Test").contains("Luke");
      }
    });
  }

  @Test public void shouldFailIfArrayOfObjectsToContainIsNull() {
    expectAssertionError("the given array of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new CollectionAssert(EMPTY_COLLECTION).contains(objects);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArrayOfObjectsToContainIsNull() {
    expectAssertionError("[A Test] the given array of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new CollectionAssert(EMPTY_COLLECTION).as("A Test").contains(objects);
      }
    });
  }

  @Test public void shouldFailIfActualExcludesValueAndExpectingToContain() {
    expectAssertionError("collection:<[]> does not contain element(s):<['Luke']>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(EMPTY_COLLECTION).contains("Luke");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualExcludesValueAndExpectingToContain() {
    expectAssertionError("[A Test] collection:<[]> does not contain element(s):<['Luke']>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(EMPTY_COLLECTION).as("A Test").contains("Luke");
      }
    });
  }

  @Test public void shouldPassIfActualExcludesValueAsAnticipated() {
    new CollectionAssert(list("Luke", "Leia")).excludes("Anakin");
  }

  @Test public void shouldPassIfActualExcludesGivenValuesAsAnticipated() {
    new CollectionAssert(list("Luke", "Leia", "Anakin")).excludes("Han", "Yoda");
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfActualExcludesObjects() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).excludes("Luke");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfActualExcludesObjects() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).as("A Test").excludes("Luke");
      }
    });
  }

  @Test public void shouldFailIfArrayOfObjectsToExcludeIsNull() {
    expectAssertionError("the given array of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new CollectionAssert(EMPTY_COLLECTION).excludes(objects);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArrayOfObjectsToExcludeIsNull() {
    expectAssertionError("[A Test] the given array of objects should not be null").on(new CodeToTest() {
      public void run() {
        Object[] objects = null;
        new CollectionAssert(EMPTY_COLLECTION).as("A Test").excludes(objects);
      }
    });
  }

  @Test public void shouldFailIfActualContainsValueAndExpectingToExclude() {
    expectAssertionError("collection:<['Luke', 'Leia']> does not exclude element(s):<['Luke']>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(list("Luke", "Leia")).excludes("Luke");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualContainsValueAndExpectingToExclude() {
    expectAssertionError("[A Test] collection:<['Luke', 'Leia']> does not exclude element(s):<['Luke']>").on(
        new CodeToTest() {
          public void run() {
            new CollectionAssert(list("Luke", "Leia")).as("A Test").excludes("Luke");
          }
        });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingItDoesNotHaveDuplicates() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).doesNotHaveDuplicates();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingItDoesNotHaveDuplicates() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).as("A Test").doesNotHaveDuplicates();
      }
    });
  }

  @Test public void shouldFailIfActualHasDuplicatesAndExpectingNoDuplicates() {
    expectAssertionError("collection:<['Luke', 'Yoda', 'Luke']> contains duplicate(s):<['Luke']>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(list("Luke", "Yoda", "Luke")).doesNotHaveDuplicates();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualHasDuplicatesAndExpectingNoDuplicates() {
    expectAssertionError("[A Test] collection:<['Luke', 'Yoda', 'Luke']> contains duplicate(s):<['Luke']>").on(
        new CodeToTest() {
          public void run() {
            new CollectionAssert(list("Luke", "Yoda", "Luke")).as("A Test").doesNotHaveDuplicates();
          }
        });
  }

  @Test public void shouldPassIfActualContainsNoDuplicatesAsAnticipated() {
    new CollectionAssert(list("Luke", "Yoda")).doesNotHaveDuplicates();
  }

  @Test public void shouldPassIfEmptyActualContainsNoDuplicatesAsAnticipated() {
    new CollectionAssert(EMPTY_COLLECTION).doesNotHaveDuplicates();
  }

  @Test public void shouldFailIfActualIsNotEmptyAndExpectingEmpty() {
    expectAssertionError("expecting empty collection, but was:<['Yoda']>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(list("Yoda")).isEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotEmptyAndExpectingEmpty() {
    expectAssertionError("[A Test] expecting empty collection, but was:<['Yoda']>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(list("Yoda")).as("A Test").isEmpty();
      }
    });
  }

  @Test public void shouldPassIfActualIsEmptyAsAnticipated() {
    new CollectionAssert(EMPTY_COLLECTION).isEmpty();
  }

  @Test public void shouldFailIfActualIsNullAndExpectingEmpty() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).isEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingEmpty() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        List<String> nullList = null;
        new CollectionAssert(nullList).as("A Test").isEmpty();
      }
    });
  }

  @Test public void shouldPassIfActualHasExpectedSize() {
    new CollectionAssert(list("Gandalf", "Frodo", "Sam")).hasSize(3);
  }

  @Test public void shouldFailIfActualDoesNotHaveExpectedSize() {
    expectAssertionError("expected size:<2> but was:<1> for collection:<['Frodo']>").on(new CodeToTest() {
      public void run() {
        List<String> names = list("Frodo");
        new CollectionAssert(names).hasSize(2);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualDoesNotHaveExpectedSize() {
    expectAssertionError("[A Test] expected size:<2> but was:<1> for collection:<['Frodo']>").on(new CodeToTest() {
      public void run() {
        List<String> names = list("Frodo");
        new CollectionAssert(names).as("A Test").hasSize(2);
      }
    });
  }

  @Test public void shouldFailIfActualIsNullAndExpectingSomeSize() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).hasSize(0);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingSomeSize() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).as("A Test").hasSize(0);
      }
    });
  }

  @Test public void shouldPassIfActualIsNotEmptyAsAnticipated() {
    List<String> names = list("Frodo", "Sam");
    new CollectionAssert(names).isNotEmpty();
  }

  @Test public void shouldFailIfActualIsNullAndExpectingNotEmpty() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).isNotEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingNotEmpty() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).as("A Test").isNotEmpty();
      }
    });
  }

  @Test public void shouldFailIfActualIsEmptyAndExpectingNotEmpty() {
    expectAssertionError("expecting a non-empty collection, but it was empty").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(EMPTY_COLLECTION).isNotEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsEmptyAndExpectingNotEmpty() {
    expectAssertionError("[A Test] expecting a non-empty collection, but it was empty").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(EMPTY_COLLECTION).as("A Test").isNotEmpty();
      }
    });
  }

  @Test public void shouldPassIfActualIsNullAsAnticipated() {
    new CollectionAssert(null).isNull();
  }

  @Test public void shouldFailIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("<[]> should be null").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(EMPTY_COLLECTION).isNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("[A Test] <[]> should be null").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(EMPTY_COLLECTION).as("A Test").isNull();
      }
    });
  }

  @Test public void shouldFailIfActualIsEmptyAndExpectingToContainValue() {
    expectAssertionError("collection:<[]> does not contain element(s):<['Sam']>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(EMPTY_COLLECTION).containsOnly("Sam");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsEmptyAndExpectingToContainValue() {
    expectAssertionError("[A Test] collection:<[]> does not contain element(s):<['Sam']>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(EMPTY_COLLECTION).as("A Test").containsOnly("Sam");
      }
    });
  }

  @Test public void shouldFailIfActualHasNotExpectedValues() {
    expectAssertionError("unexpected element(s):<['Sam']> in collection:<['Gandalf', 'Frodo', 'Sam']>").on(new CodeToTest() {
      public void run() {
        List<String> names = list("Gandalf", "Frodo", "Sam");
        new CollectionAssert(names).containsOnly("Gandalf", "Frodo");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualHasNotExpectedValues() {
    expectAssertionError("[A Test] unexpected element(s):<['Sam']> in collection:<['Gandalf', 'Frodo', 'Sam']>").on(
        new CodeToTest() {
          public void run() {
            List<String> names = list("Gandalf", "Frodo", "Sam");
            new CollectionAssert(names).as("A Test").containsOnly("Gandalf", "Frodo");
          }
        });
  }

  @Test public void shouldFailIfActualDoesNotContainExpectedElement() {
    expectAssertionError("collection:<['Gandalf', 'Frodo']> does not contain element(s):<['Sam']>").on(
        new CodeToTest() {
          public void run() {
            List<String> names = list("Gandalf", "Frodo");
            new CollectionAssert(names).containsOnly("Gandalf", "Frodo", "Sam");
          }
        });
  }

  @Test public void shouldFailShowingDescriptionIfActualDoesNotContainExpectedElement() {
    expectAssertionError("[A Test] collection:<['Gandalf', 'Frodo']> does not contain element(s):<['Sam']>").on(
        new CodeToTest() {
          public void run() {
            List<String> names = list("Gandalf", "Frodo");
            new CollectionAssert(names).as("A Test").containsOnly("Gandalf", "Frodo", "Sam");
          }
        });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingThatItContainsExpectedElement() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).containsOnly("Gandalf", "Frodo", "Sam");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingThatItContainsExpectedElement() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).as("A Test").containsOnly("Gandalf", "Frodo", "Sam");
      }
    });
  }

  private void shouldFailIfActualIsNull(CodeToTest codeToTest) {
    expectAssertionError("expecting a non-null collection, but it was null").on(codeToTest);
  }

  private void shouldFailShowingDescriptionIfActualIsNull(CodeToTest codeToTest) {
    expectAssertionError("[A Test] expecting a non-null collection, but it was null").on(codeToTest);
  }

  @Test public void shouldPassIfActualHasExpectedElementsOnly() {
    List<String> names = list("Gandalf", "Frodo");
    new CollectionAssert(names).containsOnly("Gandalf", "Frodo");
  }
}
