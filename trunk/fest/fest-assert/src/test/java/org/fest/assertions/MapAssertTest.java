/*
 * Created on Jan 24, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.assertions.MapAssert.entry;
import static org.fest.test.ExpectedFailure.expect;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.fest.assertions.MapAssert.Entry;
import org.fest.test.CodeToTest;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link MapAssert}</code>.
 *
 * @author David DIDIER
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class MapAssertTest {

  @Test public void shouldPassIfGivenKeysAreInMap() {
    Map<Object, Object> map = map(entry("key1", 1), entry("key2", 2));
    new MapAssert(map).keySetIncludes("key1", "key2");
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfContainsKeys() {
    expect(AssertionError.class).withMessage("expecting a non-null map, but it was null").on(new CodeToTest() {
      public void run() {
        new MapAssert(null).keySetIncludes("key1");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfContainsKeys() {
    expect(AssertionError.class)
      .withMessage("[A Test] expecting a non-null map, but it was null").on(new CodeToTest() {
        public void run() {
          new MapAssert(null).as("A Test").keySetIncludes("key1");
        }
      });
  }

  @Test public void shouldFailIfArrayOfKeysIsNullWhenCheckingIfContainsKeys() {
    expect(AssertionError.class).withMessage("the given array of keys should not be null").on(new CodeToTest() {
      public void run() {
        Map<Object, Object> map = map(entry("key1", 1), entry("key2", 2));
        Object[] keys = null;
        new MapAssert(map).keySetIncludes(keys);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArrayOfKeysIsNullWhenCheckingIfContainsKeys() {
    expect(AssertionError.class)
      .withMessage("[A Test] the given array of keys should not be null").on(new CodeToTest() {
        public void run() {
          Map<Object, Object> map = map(entry("key1", 1), entry("key2", 2));
          Object[] keys = null;
          new MapAssert(map).as("A Test").keySetIncludes(keys);
        }
      });
  }

  @Test public void shouldFailIfGivenKeysAreNotInMap() {
    expect(AssertionError.class)
      .withMessage("the map:<{'key1'=1, 'key2'=2}> does not contain the key(s):<['key4', 'key5']>")
      .on(new CodeToTest() {
        public void run() {
          Map<Object, Object> map = map(entry("key1", 1), entry("key2", 2));
          new MapAssert(map).keySetIncludes("key4", "key5");
        }
      });
  }

  @Test public void shouldFailShowingDescriptionIfGivenKeysAreNotInMap() {
    expect(AssertionError.class)
      .withMessage("[A Test] the map:<{'key1'=1, 'key2'=2}> does not contain the key(s):<['key4', 'key5']>")
      .on(new CodeToTest() {
        public void run() {
          Map<Object, Object> map = map(entry("key1", 1), entry("key2", 2));
          new MapAssert(map).as("A Test").keySetIncludes("key4", "key5");
        }
      });
  }

  @Test public void shouldPassIfGivenValuesAreInMap() {
    Map<Object, Object> map = map(entry("key1", 1), entry("key2", 2));
    new MapAssert(map).valuesInclude(1, 2);
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfContainsValues() {
    expect(AssertionError.class).withMessage("expecting a non-null map, but it was null").on(new CodeToTest() {
      public void run() {
        new MapAssert(null).valuesInclude(8);
      }
    });
  }

  @Test public void shouldShowingDescriptionFailIfActualIsNullWhenCheckingIfContainsValues() {
    expect(AssertionError.class)
      .withMessage("[A Test] expecting a non-null map, but it was null").on(new CodeToTest() {
        public void run() {
          new MapAssert(null).as("A Test").valuesInclude(8);
        }
      });
  }

  @Test public void shouldFailIfArrayOfKeysIsNullWhenCheckingIfContainsValues() {
    expect(AssertionError.class).withMessage("the given array of values should not be null").on(new CodeToTest() {
      public void run() {
        Map<Object, Object> map = map(entry("key1", 1), entry("key2", 2));
        Object[] values = null;
        new MapAssert(map).valuesInclude(values);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfArrayOfKeysIsNullWhenCheckingIfContainsValues() {
    expect(AssertionError.class)
      .withMessage("[A Test] the given array of values should not be null").on(new CodeToTest() {
        public void run() {
          Map<Object, Object> map = map(entry("key1", 1), entry("key2", 2));
          Object[] values = null;
          new MapAssert(map).as("A Test").valuesInclude(values);
        }
      });
  }

  @Test public void shouldFailIfGivenValuesAreNotInMap() {
    expect(AssertionError.class)
      .withMessage("the map:<{'key1'=1, 'key2'=2}> does not contain the value(s):<[4, 5]>").on(new CodeToTest() {
        public void run() {
          Map<Object, Object> map = map(entry("key1", 1), entry("key2", 2));
          new MapAssert(map).valuesInclude(4, 5);
        }
      });
  }

  @Test public void shouldFailShowingDescriptionIfGivenValuesAreNotInMap() {
    expect(AssertionError.class)
      .withMessage("[A Test] the map:<{'key1'=1, 'key2'=2}> does not contain the value(s):<[4, 5]>")
      .on(new CodeToTest() {
        public void run() {
          Map<Object, Object> map = map(entry("key1", 1), entry("key2", 2));
          new MapAssert(map).as("A Test").valuesInclude(4, 5);
        }
      });
  }

  @Test public void shouldPassIfGivenEntryIsInMap() {
    Map<Object, Object> map = map(entry("key1", 1), entry("key2", 2));
    new MapAssert(map).contains(entry("key1", 1));
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfContainsEntry() {
    expect(AssertionError.class).withMessage("expecting a non-null map, but it was null").on(new CodeToTest() {
      public void run() {
        new MapAssert(null).contains(entry("key6", 6));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfContainsEntry() {
    expect(AssertionError.class)
      .withMessage("[A Test] expecting a non-null map, but it was null").on(new CodeToTest() {
        public void run() {
          new MapAssert(null).as("A Test").contains(entry("key6", 6));
        }
      });
  }

  @Test public void shouldFailIfEntryArrayIsNullWhenCheckingIfContainsEntry() {
    expect(AssertionError.class).withMessage("the given array of entries should not be null").on(new CodeToTest() {
      public void run() {
        Map<Object, Object> map = map(entry("key1", 1), entry("key2", 2));
        Entry[] entry = null;
        new MapAssert(map).contains(entry);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfEntryArrayIsNullWhenCheckingIfContainsEntry() {
    expect(AssertionError.class)
      .withMessage("[A Test] the given array of entries should not be null").on(new CodeToTest() {
        public void run() {
          Map<Object, Object> map = map(entry("key1", 1), entry("key2", 2));
          Entry[] entries = null;
          new MapAssert(map).as("A Test").contains(entries);
        }
      });
  }

  @Test public void shouldFailIfEntryIsNullWhenCheckingIfContainsEntry() {
    expect(AssertionError.class).withMessage("the entry to check should not be null").on(new CodeToTest() {
      public void run() {
        Map<Object, Object> map = map(entry("key1", 1), entry("key2", 2));
        Entry entry = null;
        new MapAssert(map).contains(entry);
      }
    });
  }

  @Test public void shouldFailIfGivenEntryIsNotInMap() {
    expect(AssertionError.class)
      .withMessage("the map:<{'key1'=1, 'key2'=2}> does not contain the entry:<['key6'=6]>").on(new CodeToTest() {
        public void run() {
          Map<Object, Object> map = map(entry("key1", 1), entry("key2", 2));
          new MapAssert(map).contains(entry("key6", 6));
        }
      });
  }

  @Test public void shouldFailShowingDescriptionIfGivenEntryIsNotInMap() {
    expect(AssertionError.class)
      .withMessage("[A Test] the map:<{'key1'=1, 'key2'=2}> does not contain the entry:<['key6'=6]>")
      .on(new CodeToTest() {
        public void run() {
          Map<Object, Object> map = map(entry("key1", 1), entry("key2", 2));
          new MapAssert(map).as("A Test").contains(entry("key6", 6));
        }
      });
  }

  @Test public void shouldFailIfGivenEntriesIsNotInMap() {
    expect(AssertionError.class)
      .withMessage("the map:<{'key1'=1, 'key2'=2}> does not contain the entries:<['key6'=6, 'key8'=8]>")
      .on(new CodeToTest() {
        public void run() {
          Map<Object, Object> map = map(entry("key1", 1), entry("key2", 2));
          new MapAssert(map).contains(entry("key6", 6), entry("key8", 8));
        }
      });
  }

  @Test public void shouldFailShowingDescriptionIfGivenEntriesIsNotInMap() {
    expect(AssertionError.class)
      .withMessage("[A Test] the map:<{'key1'=1, 'key2'=2}> does not contain the entries:<['key6'=6, 'key8'=8]>")
      .on(new CodeToTest() {
        public void run() {
          Map<Object, Object> map = map(entry("key1", 1), entry("key2", 2));
          new MapAssert(map).as("A Test").contains(entry("key6", 6), entry("key8", 8));
        }
      });
  }

  @Test public void shouldFailIfGivenValueNotAssociatedWithExistingKey() {
    expect(AssertionError.class)
      .withMessage("the map:<{'key1'=1, 'key2'=2}> does not contain the entry:<['key1'=6]>").on(new CodeToTest() {
        public void run() {
          Map<Object, Object> map = map(entry("key1", 1), entry("key2", 2));
          new MapAssert(map).contains(entry("key1", 6));
        }
      });
  }

  @Test public void shouldFailIfMapsAreEqualAndExpectingNotEqual() {
    expect(AssertionError.class)
      .withMessage("actual value:<{'key1'=1, 'key2'=2}> should not be equal to:<{'key1'=1, 'key2'=2}>")
      .on(new CodeToTest() {
        public void run() {
          Map<Object, Object> map1 = map(entry("key1", 1), entry("key2", 2));
          Map<Object, Object> map2 = map(entry("key1", 1), entry("key2", 2));
          new MapAssert(map1).isNotEqualTo(map2);
        }
      });
  }

  @Test public void shouldFailShowingDescriptionIfMapsAreEqualAndExpectingNotEqual() {
    expect(AssertionError.class)
      .withMessage("[A Test] actual value:<{'key1'=1, 'key2'=2}> should not be equal to:<{'key1'=1, 'key2'=2}>")
      .on(new CodeToTest() {
        public void run() {
          Map<Object, Object> map1 = map(entry("key1", 1), entry("key2", 2));
          Map<Object, Object> map2 = map(entry("key1", 1), entry("key2", 2));
          new MapAssert(map1).as("A Test").isNotEqualTo(map2);
        }
      });
  }

  @Test public void shouldFailIfActualIsNotEqualToExpectedAndExpectingEqual() {
    expect(AssertionError.class)
      .withMessage("expected:<{'key6'=6, 'key8'=8}> but was:<{'key1'=1, 'key2'=2}>").on(new CodeToTest() {
        public void run() {
          Map<Object, Object> map1 = map(entry("key1", 1), entry("key2", 2));
          Map<Object, Object> map2 = map(entry("key6", 6), entry("key8", 8));
          new MapAssert(map1).isEqualTo(map2);
        }
      });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotEqualToExpectedAndExpectingEqual() {
    expect(AssertionError.class)
      .withMessage("[A Test] expected:<{'key6'=6, 'key8'=8}> but was:<{'key1'=1, 'key2'=2}>").on(new CodeToTest() {
        public void run() {
          Map<Object, Object> map1 = map(entry("key1", 1), entry("key2", 2));
          Map<Object, Object> map2 = map(entry("key6", 6), entry("key8", 8));
          new MapAssert(map1).as("A Test").isEqualTo(map2);
        }
      });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingSize() {
    expect(AssertionError.class).withMessage("expecting a non-null map, but it was null").on(new CodeToTest() {
      public void run() {
        new MapAssert(null).hasSize(2);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingSize() {
    expect(AssertionError.class)
      .withMessage("[A Test] expecting a non-null map, but it was null").on(new CodeToTest() {
        public void run() {
          new MapAssert(null).as("A Test").hasSize(2);
        }
      });
  }

  @Test public void shouldFailIfMapDoesNotHaveExpectedSize() {
    expect(AssertionError.class)
      .withMessage("expected size:<2> but was:<1> for map:<{'key1'=1}>").on(new CodeToTest() {
        public void run() {
          Map<Object, Object> map = map(entry("key1", 1));
          new MapAssert(map).hasSize(2);
        }
      });
  }

  @Test public void shouldFailShowingDescriptionIfMapDoesNotHaveExpectedSize() {
    expect(AssertionError.class)
      .withMessage("[A Test] expected size:<2> but was:<1> for map:<{'key1'=1}>").on(new CodeToTest() {
        public void run() {
          Map<Object, Object> map = map(entry("key1", 1));
          new MapAssert(map).as("A Test").hasSize(2);
        }
      });
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfEmpty() {
    expect(AssertionError.class).withMessage("expecting a non-null map, but it was null").on(new CodeToTest() {
      public void run() {
        new MapAssert(null).isEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfEmpty() {
    expect(AssertionError.class)
      .withMessage("[A Test] expecting a non-null map, but it was null").on(new CodeToTest() {
        public void run() {
          new MapAssert(null).as("A Test").isEmpty();
        }
      });
  }

  @Test public void shouldFailIfMapIsNotEmptyAndExpectingEmpty() {
    expect(AssertionError.class).withMessage("expecting empty map, but was:<{'key1'=1}>").on(new CodeToTest() {
      public void run() {
        Map<Object, Object> map = map(entry("key1", 1));
        new MapAssert(map).isEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfMapIsNotEmptyAndExpectingEmpty() {
    expect(AssertionError.class)
      .withMessage("[A Test] expecting empty map, but was:<{'key1'=1}>").on(new CodeToTest() {
        public void run() {
          Map<Object, Object> map = map(entry("key1", 1));
          new MapAssert(map).as("A Test").isEmpty();
        }
      });
  }

  @Test public void shouldFailIfMapIsEmptyAndExpectingNotEmpty() {
    expect(AssertionError.class).withMessage("expecting non-empty map, but it was empty").on(new CodeToTest() {
      public void run() {
        new MapAssert(map()).isNotEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfMapIsEmptyAndExpectingNotEmpty() {
    expect(AssertionError.class)
      .withMessage("[A Test] expecting non-empty map, but it was empty").on(new CodeToTest() {
        public void run() {
          new MapAssert(map()).as("A Test").isNotEmpty();
        }
      });
  }

  @Test public void shouldFailIfMapIsNotNullAndExpectingNull() {
    expect(AssertionError.class).withMessage("<{}> should be null").on(new CodeToTest() {
      public void run() {
        new MapAssert(map()).isNull();
      }
    });
  }

  @Test public void shouldPassIfGivenMapIsEqualTo() {
    Map<Object, Object> map1 = map(entry("key1", 1), entry("key2", 2));
    Map<Object, Object> map2 = map(entry("key1", 1), entry("key2", 2));
    new MapAssert(map1).isEqualTo(map2);
  }

  @Test public void shouldPassIfGivenMapIsNotEqualTo() {
    Map<Object, Object> map1 = map(entry("key1", 1), entry("key2", 2));
    Map<Object, Object> map2 = map(entry("key1", 1), entry("key3", 3));
    new MapAssert(map1).isNotEqualTo(map2);
  }

  @Test public void shouldPassIfMapHasExpectedSize() {
    Map<Object, Object> map = map(entry("key1", 1), entry("key2", 2));
    new MapAssert(map).hasSize(2);
  }

  @Test public void shouldPassIfMapIsEmptyAndExpectingEmpty() {
    new MapAssert(new HashMap<Object, Object>()).isEmpty();
  }

  @Test public void shouldPassIfMapIsNotEmptyAndExpectingNotEmpty() {
    Map<Object, Object> map = map(entry("key1", 1));
    new MapAssert(map).isNotEmpty();
  }

  @Test public void shouldPassIfMapIsNullAndExpectingNull() {
    new MapAssert(null).isNull();
  }

  private Map<Object, Object> map(Entry...entries) {
    Map<Object, Object> map = new LinkedHashMap<Object, Object>();
    for (Entry entry : entries) map.put(entry.key, entry.value);
    return map;
  }
}
