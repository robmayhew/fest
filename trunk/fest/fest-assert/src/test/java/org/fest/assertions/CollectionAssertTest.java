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

import static org.fest.test.ExpectedFailure.expect;
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

  @Test public void shouldPassIfActualContainsValueAsAnticipated() {
    new CollectionAssert(list("Luke", "Leia")).contains("Luke");
  }

  @Test public void shouldPassIfActualContainsValuesAsAnticipated() {
    new CollectionAssert(list("Luke", "Leia", "Anakin")).contains("Luke", "Leia");
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfActualContainsObjects() {
    expect(AssertionError.class).withMessage("expecting a non-null collection, but it was null").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).contains("Luke");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfActualContainsObjects() {
    expect(AssertionError.class)
      .withMessage("[A Test] expecting a non-null collection, but it was null").on(new CodeToTest() {
        public void run() {
          new CollectionAssert(null).as("A Test").contains("Luke");
        }
      });
  }

  @Test public void shouldFailIfArrayOfObjectsToContainIsNull() {
    expect(AssertionError.class)
      .withMessage("the given array of objects should not be null").on(new CodeToTest() {
        public void run() {
          Object[] objects = null;
          new CollectionAssert(new ArrayList<String>()).contains(objects);
        }
      });
  }

  @Test public void shouldFailShowingDescriptionIfArrayOfObjectsToContainIsNull() {
    expect(AssertionError.class)
      .withMessage("[A Test] the given array of objects should not be null").on(new CodeToTest() {
        public void run() {
          Object[] objects = null;
          new CollectionAssert(new ArrayList<String>()).as("A Test").contains(objects);
        }
      });
  }

  @Test public void shouldFailIfActualExcludesValueAndExpectingToContain() {
    expect(AssertionError.class)
      .withMessage("collection:<[]> does not contain element(s):<['Luke']>").on(new CodeToTest() {
        public void run() {
          new CollectionAssert(new ArrayList<String>()).contains("Luke");
        }
      });
  }

  @Test public void shouldFailShowingDescriptionIfActualExcludesValueAndExpectingToContain() {
    expect(AssertionError.class)
      .withMessage("[A Test] collection:<[]> does not contain element(s):<['Luke']>").on(new CodeToTest() {
        public void run() {
          new CollectionAssert(new ArrayList<String>()).as("A Test").contains("Luke");
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
    expect(AssertionError.class).withMessage("expecting a non-null collection, but it was null").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).excludes("Luke");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfActualExcludesObjects() {
    expect(AssertionError.class)
      .withMessage("[A Test] expecting a non-null collection, but it was null").on(new CodeToTest() {
        public void run() {
          new CollectionAssert(null).as("A Test").excludes("Luke");
        }
      });
  }

  @Test public void shouldFailIfArrayOfObjectsToExcludeIsNull() {
    expect(AssertionError.class)
      .withMessage("the given array of objects should not be null").on(new CodeToTest() {
        public void run() {
          Object[] objects = null;
          new CollectionAssert(new ArrayList<String>()).excludes(objects);
        }
      });
  }

  @Test public void shouldFailShowingDescriptionIfArrayOfObjectsToExcludeIsNull() {
    expect(AssertionError.class)
      .withMessage("[A Test] the given array of objects should not be null").on(new CodeToTest() {
        public void run() {
          Object[] objects = null;
          new CollectionAssert(new ArrayList<String>()).as("A Test").excludes(objects);
        }
      });
  }

  @Test public void shouldFailIfActualContainsValueAndExpectingToExclude() {
    expect(AssertionError.class)
      .withMessage("collection:<['Luke', 'Leia']> does not exclude element(s):<['Luke']>").on(new CodeToTest() {
        public void run() {
          new CollectionAssert(list("Luke", "Leia")).excludes("Luke");
        }
      });
  }

  @Test public void shouldFailShowingDescriptionIfActualContainsValueAndExpectingToExclude() {
    expect(AssertionError.class)
      .withMessage("[A Test] collection:<['Luke', 'Leia']> does not exclude element(s):<['Luke']>")
      .on(new CodeToTest() {
        public void run() {
          new CollectionAssert(list("Luke", "Leia")).as("A Test").excludes("Luke");
        }
      });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingItDoesNotHaveDuplicates() {
    expect(AssertionError.class).withMessage("expecting a non-null collection, but it was null").on(new CodeToTest() {
        public void run() {
          new CollectionAssert(null).doesNotHaveDuplicates();
        }
      });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingItDoesNotHaveDuplicates() {
    expect(AssertionError.class)
      .withMessage("[A Test] expecting a non-null collection, but it was null").on(new CodeToTest() {
        public void run() {
          new CollectionAssert(null).as("A Test").doesNotHaveDuplicates();
        }
      });
  }

  @Test public void shouldFailIfActualHasDuplicatesAndExpectingNoDuplicates() {
    expect(AssertionError.class)
      .withMessage("collection:<['Luke', 'Yoda', 'Luke']> contains duplicate(s):<['Luke']>").on(new CodeToTest() {
        public void run() {
          new CollectionAssert(list("Luke", "Yoda", "Luke")).doesNotHaveDuplicates();
        }
      });
  }

  @Test public void shouldFailShowingDescriptionIfActualHasDuplicatesAndExpectingNoDuplicates() {
    expect(AssertionError.class)
      .withMessage("[A Test] collection:<['Luke', 'Yoda', 'Luke']> contains duplicate(s):<['Luke']>")
      .on(new CodeToTest() {
        public void run() {
          new CollectionAssert(list("Luke", "Yoda", "Luke")).as("A Test").doesNotHaveDuplicates();
        }
      });
  }

  @Test public void shouldPassIfActualContainsNoDuplicatesAsAnticipated() {
    new CollectionAssert(list("Luke", "Yoda")).doesNotHaveDuplicates();
  }

  @Test public void shouldPassIfEmptyActualContainsNoDuplicatesAsAnticipated() {
    new CollectionAssert(new ArrayList<String>()).doesNotHaveDuplicates();
  }

  @Test public void shouldFailIfActualIsNotEmptyAndExpectingEmpty() {
    expect(AssertionError.class)
    .withMessage("expecting empty collection, but was:<['Yoda']>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(list("Yoda")).isEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotEmptyAndExpectingEmpty() {
    expect(AssertionError.class)
    .withMessage("[A Test] expecting empty collection, but was:<['Yoda']>").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(list("Yoda")).as("A Test").isEmpty();
      }
    });
  }

  @Test public void shouldPassIfActualIsEmptyAsAnticipated() {
    new CollectionAssert(new ArrayList<String>()).isEmpty();
  }

  @Test public void shouldFailIfActualIsNullAndExpectingEmpty() {
    expect(AssertionError.class).withMessage("expecting a non-null collection, but it was null").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).isEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingEmpty() {
    expect(AssertionError.class)
      .withMessage("[A Test] expecting a non-null collection, but it was null").on(new CodeToTest() {
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
    expect(AssertionError.class)
      .withMessage("expected size:<2> but was:<1> for collection:<['Frodo']>").on(new CodeToTest() {
        public void run() {
          List<String> names = list("Frodo");
          new CollectionAssert(names).hasSize(2);
        }
      });
  }

  @Test public void shouldFailShowingDescriptionIfActualDoesNotHaveExpectedSize() {
    expect(AssertionError.class)
      .withMessage("[A Test] expected size:<2> but was:<1> for collection:<['Frodo']>").on(new CodeToTest() {
        public void run() {
          List<String> names = list("Frodo");
          new CollectionAssert(names).as("A Test").hasSize(2);
        }
      });
  }

  @Test public void shouldFailIfActualIsNullAndExpectingSomeSize() {
    expect(AssertionError.class).withMessage("expecting a non-null collection, but it was null").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).hasSize(0);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingSomeSize() {
    expect(AssertionError.class)
      .withMessage("[A Test] expecting a non-null collection, but it was null").on(new CodeToTest() {
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
    expect(AssertionError.class).withMessage("expecting a non-null collection, but it was null").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).isNotEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingNotEmpty() {
    expect(AssertionError.class)
      .withMessage("[A Test] expecting a non-null collection, but it was null").on(new CodeToTest() {
        public void run() {
          new CollectionAssert(null).as("A Test").isNotEmpty();
        }
      });
  }

  @Test public void shouldFailIfActualIsEmptyAndExpectingNotEmpty() {
    expect(AssertionError.class).withMessage("expecting a non-empty collection, but it was empty").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(new ArrayList<String>()).isNotEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsEmptyAndExpectingNotEmpty() {
    expect(AssertionError.class)
      .withMessage("[A Test] expecting a non-empty collection, but it was empty").on(new CodeToTest() {
        public void run() {
          new CollectionAssert(new ArrayList<String>()).as("A Test").isNotEmpty();
        }
      });
  }

  @Test public void shouldPassIfActualIsNullAsAnticipated() {
    new CollectionAssert(null).isNull();
  }

  @Test public void shouldFailIfActualIsNotNullAndExpectingNull() {
    expect(AssertionError.class).withMessage("<[]> should be null").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(new ArrayList<String>()).isNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotNullAndExpectingNull() {
    expect(AssertionError.class).withMessage("[A Test] <[]> should be null").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(new ArrayList<String>()).as("A Test").isNull();
      }
    });
  }

  @Test public void shouldFailIfActualIsEmptyAndExpectingToContainValue() {
    expect(AssertionError.class)
      .withMessage("collection:<[]> does not contain element(s):<['Sam']>").on(new CodeToTest() {
        public void run() {
          new CollectionAssert(new ArrayList<String>()).containsOnly("Sam");
        }
      });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsEmptyAndExpectingToContainValue() {
    expect(AssertionError.class)
      .withMessage("[A Test] collection:<[]> does not contain element(s):<['Sam']>").on(new CodeToTest() {
        public void run() {
          new CollectionAssert(new ArrayList<String>()).as("A Test").containsOnly("Sam");
        }
      });
  }

  @Test public void shouldFailIfActualHasNotExpectedValues() {
    expect(AssertionError.class)
      .withMessage("unexpected element(s):<['Sam']> in collection:<['Gandalf', 'Frodo', 'Sam']>").on(new CodeToTest() {
        public void run() {
          List<String> names = list("Gandalf", "Frodo", "Sam");
          new CollectionAssert(names).containsOnly("Gandalf", "Frodo");
        }
      });
  }

  @Test public void shouldFailShowingDescriptionIfActualHasNotExpectedValues() {
    expect(AssertionError.class)
      .withMessage("[A Test] unexpected element(s):<['Sam']> in collection:<['Gandalf', 'Frodo', 'Sam']>")
      .on(new CodeToTest() {
        public void run() {
          List<String> names = list("Gandalf", "Frodo", "Sam");
          new CollectionAssert(names).as("A Test").containsOnly("Gandalf", "Frodo");
        }
      });
  }

  @Test public void shouldFailIfActualDoesNotContainExpectedElement() {
    expect(AssertionError.class)
    .withMessage("collection:<['Gandalf', 'Frodo']> does not contain element(s):<['Sam']>").on(new CodeToTest() {
      public void run() {
        List<String> names = list("Gandalf", "Frodo");
        new CollectionAssert(names).containsOnly("Gandalf", "Frodo", "Sam");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualDoesNotContainExpectedElement() {
    expect(AssertionError.class)
      .withMessage("[A Test] collection:<['Gandalf', 'Frodo']> does not contain element(s):<['Sam']>")
      .on(new CodeToTest() {
        public void run() {
          List<String> names = list("Gandalf", "Frodo");
          new CollectionAssert(names).as("A Test").containsOnly("Gandalf", "Frodo", "Sam");
        }
      });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingThatItContainsExpectedElement() {
    expect(AssertionError.class).withMessage("expecting a non-null collection, but it was null").on(new CodeToTest() {
      public void run() {
        new CollectionAssert(null).containsOnly("Gandalf", "Frodo", "Sam");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingThatItContainsExpectedElement() {
    expect(AssertionError.class)
      .withMessage("[A Test] expecting a non-null collection, but it was null").on(new CodeToTest() {
        public void run() {
          new CollectionAssert(null).as("A Test").containsOnly("Gandalf", "Frodo", "Sam");
        }
      });
  }

  @Test public void shouldPassIfActualHasExpectedElementsOnly() {
    List<String> names = list("Gandalf", "Frodo");
    new CollectionAssert(names).containsOnly("Gandalf", "Frodo");
  }
}
