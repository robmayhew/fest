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

import static org.fest.assertions.Fail.fail;
import static org.fest.assertions.MapAssert.entry;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.fest.assertions.MapAssert.Entry;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link MapAssert}</code>.
 *
 * @author David DIDIER
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class MapAssertTest {

  private static Logger logger = Logger.getAnonymousLogger();

  @Test public void shouldPassIfGivenKeysAreInMap() {
    Map<Object, Object> map = map(entry("key1", "value1"), entry("key2", "value2"));
    new MapAssert(map).keySetIncludes("key1", "key2");
  }

  @Test public void shouldFailIfGivenKeysAreNotInMap() {
    Map<Object, Object> map = map(entry("key1", "value1"), entry("key2", "value2"));
    try {
      new MapAssert(map).keySetIncludes("key4", "key5");
      fail();
    } catch (AssertionError e) {
      log(e);
    }
  }

  @Test public void shouldPassIfGivenValuesAreInMap() {
    Map<Object, Object> map = map(entry("key1", "value1"), entry("key2", "value2"));
    new MapAssert(map).valuesInclude("value1", "value2");
  }

  @Test public void shouldFailIfGivenValuesAreNotInMap() {
    Map<Object, Object> map = map(entry("key1", "value1"), entry("key2", "value2"));
    try {
      new MapAssert(map).valuesInclude("value4", "value5");
      fail();
    } catch (AssertionError e) {
      log(e);
    }
  }

  @Test public void shouldPassIfGivenEntryIsInMap() {
    Map<Object, Object> map = map(entry("key1", "value1"), entry("key2", "value2"));
    new MapAssert(map).contains(entry("key1", "value1"));
  }

  @Test public void shouldFailIfGivenEntryIsNotInMap() {
    Map<Object, Object> map = map(entry("key1", "value1"), entry("key2", "value2"));
    try {
      new MapAssert(map).contains(entry("key6", "value6"));
      fail();
    } catch (AssertionError e) {
      log(e);
    }
  }

  @Test public void shouldFailIfGivenValueNotAssociatedWithExistingKey() {
    Map<Object, Object> map = map(entry("key1", "value1"), entry("key2", "value2"));
    try {
      new MapAssert(map).contains(entry("key1", "value6"));
      fail();
    } catch (AssertionError e) {
      log(e);
    }
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfGivenMapsAreEqual() {
    Map<Object, Object> map1 = map(entry("key1", "value1"), entry("key2", "value2"));
    Map<Object, Object> map2 = map(entry("key1", "value1"), entry("key2", "value2"));
    new MapAssert(map1).isNotEqualTo(map2);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfGivenMapIsNotEqualTo() {
    Map<Object, Object> map1 = map(entry("key1", "value1"), entry("key2", "value2"));
    Map<Object, Object> map2 = map(entry("key1", "value1"), entry("key3", "value3"));
    new MapAssert(map1).isEqualTo(map2);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfMapDoesNotHaveExpectedSize() {
    Map<Object, Object> map = map(entry("key1", "value1"));
    new MapAssert(map).hasSize(2);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfMapIsNotEmptyAndExpectingEmpty() {
    Map<Object, Object> map = map(entry("key1", "value1"));
    new MapAssert(map).isEmpty();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfMapIsNotEmptyAndExpectingNotEmpty() {
    new MapAssert(new HashMap<Object, Object>()).isNotEmpty();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfMapIsNotNullAndExpectingNull() {
    new MapAssert(new HashMap<Object, Object>()).isNull();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfMapIsNullAndExpectingSomeSize() {
    new MapAssert(null).hasSize(0);
  }

  @Test public void shouldPassIfGivenMapIsEqualTo() {
    Map<Object, Object> map1 = map(entry("key1", "value1"), entry("key2", "value2"));
    Map<Object, Object> map2 = map(entry("key1", "value1"), entry("key2", "value2"));
    new MapAssert(map1).isEqualTo(map2);
  }

  @Test public void shouldPassIfGivenMapIsNotEqualTo() {
    Map<Object, Object> map1 = map(entry("key1", "value1"), entry("key2", "value2"));
    Map<Object, Object> map2 = map(entry("key1", "value1"), entry("key3", "value3"));
    new MapAssert(map1).isNotEqualTo(map2);
  }

  @Test public void shouldPassIfMapHasExpectedSize() {
    Map<Object, Object> map = map(entry("key1", "value1"), entry("key2", "value2"));
    new MapAssert(map).hasSize(2);
  }

  @Test public void shouldPassIfMapIsEmptyAndExpectingEmpty() {
    new MapAssert(new HashMap<Object, Object>()).isEmpty();
  }

  @Test public void shouldPassIfMapIsNotEmptyAndExpectingNotEmpty() {
    Map<Object, Object> map = map(entry("key1", "value1"));
    new MapAssert(map).isNotEmpty();
  }

  @Test public void shouldPassIfMapIsNullAndExpectingEmpty() {
    Map<Object, Object> nullMap = null;
    new MapAssert(nullMap).isEmpty();
  }

  @Test public void shouldPassIfMapIsNullAndExpectingNull() {
    new MapAssert(null).isNull();
  }

  private void log(AssertionError e) {
    logger.info(e.getMessage());
  }

  private Map<Object, Object> map(Entry...entries) {
    Map<Object, Object> map = new LinkedHashMap<Object, Object>();
    for (Entry entry : entries) map.put(entry.key, entry.value);
    return map;
  }
}
