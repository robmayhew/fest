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

import static java.util.logging.Level.INFO;
import static org.fest.assertions.Fail.fail;
import static org.fest.util.Collections.list;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link CollectionAssert}</code>.
 *
 * @author Yvonne Wang
 */
public class CollectionAssertTest {

  private static Logger logger = Logger.getAnonymousLogger();

  @Test public void shouldPassIfGivenObjectIsInCollection() {
    new CollectionAssert(list("Luke", "Leia")).contains("Luke");
  }

  @Test(dependsOnMethods = "shouldPassIfGivenObjectIsInCollection")
  public void shouldPassIfGivenObjectsAreInCollection() {
    new CollectionAssert(list("Luke", "Leia", "Anakin")).contains("Luke", "Leia");
  }

  @Test(dependsOnMethods = "shouldPassIfGivenObjectIsInCollection", expectedExceptions = AssertionError.class)
  public void shouldFailIfGivenObjectIsNotInCollection() {
    new CollectionAssert(new ArrayList<String>()).contains("Luke");
  }


  @Test public void shouldPassIfGivenObjectIsNotInCollection() {
    new CollectionAssert(list("Luke", "Leia")).excludes("Anakin");
  }

  @Test(dependsOnMethods = "shouldPassIfGivenObjectIsNotInCollection")
  public void shouldPassIfGivenObjectsAreNotInCollection() {
    new CollectionAssert(list("Luke", "Leia", "Anakin")).excludes("Han", "Yoda");
  }

  @Test(dependsOnMethods = "shouldPassIfGivenObjectIsInCollection", expectedExceptions = AssertionError.class)
  public void shouldFailIfGivenObjectIsInCollection() {
    new CollectionAssert(list("Luke", "Leia")).excludes("Luke");
  }

  @Test public void shouldFailIfCollectionHasDuplicates() {
    List<String> list = list("Luke", "Yoda", "Luke");
    try {
      new CollectionAssert(list).doesNotHaveDuplicates();
      fail("Should have failed");
    } catch (AssertionError e) {
      String message = e.getMessage();
      logger.log(INFO, message);
      new StringAssert(message).contains("Luke");
    }
  }

  @Test public void shouldSucceedIfCollectionDoesNotHaveDuplicates() {
    new CollectionAssert(list("Luke", "Yoda")).doesNotHaveDuplicates();
  }

  @Test public void shouldSucceedIfCollectionIsEmpty() {
    new CollectionAssert(new ArrayList<String>()).doesNotHaveDuplicates();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfCollectionIsNotEmptyAndExpectingEmpty() {
    new CollectionAssert(list("Yoda")).isEmpty();
  }

  @Test public void shouldPassIfCollectionIsEmptyAndExpectingEmpty() {
    new CollectionAssert(new ArrayList<String>()).isEmpty();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfCollectionIsNullAndExpectingEmpty() {
    List<String> nullList = null;
    new CollectionAssert(nullList).isEmpty();
  }

  @Test public void shouldPassIfCollectionHasExpectedSize() {
    List<String> names = list("Gandalf", "Frodo", "Sam");
    new CollectionAssert(names).hasSize(3);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfCollectionDoesNotHaveExpectedSize() {
    List<String> names = list("Frodo");
    new CollectionAssert(names).hasSize(2);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfCollectionIsNullAndExpectingSomeSize() {
    new CollectionAssert(null).hasSize(0);
  }

  @Test public void shouldPassIfCollectionIsNotEmptyAndExpectingNotEmpty() {
    List<String> names = list("Frodo", "Sam");
    new CollectionAssert(names).isNotEmpty();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfCollectionIsNotEmptyAndExpectingNotEmpty() {
    new CollectionAssert(new ArrayList<String>()).isNotEmpty();
  }

  @Test public void shouldPassIfCollectionIsNullAndExpectingNull() {
    new CollectionAssert(null).isNull();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfCollectionIsNotNullAndExpectingNull() {
    new CollectionAssert(new ArrayList<String>()).isNull();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfCollectionIsEmpty() {
    new CollectionAssert(new ArrayList<String>()).containsOnly("Sam");
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfCollectionHasExtraElements() {
    List<String> names = list("Gandalf", "Frodo", "Sam");
    new CollectionAssert(names).containsOnly("Gandalf", "Frodo");
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfCollectionDoesNotContainElement() {
    List<String> names = list("Gandalf", "Frodo");
    new CollectionAssert(names).containsOnly("Gandalf", "Frodo", "Sam");
  }

  @Test public void shouldPassIfCollectionHasOnlySpecifiedElements() {
    List<String> names = list("Gandalf", "Frodo");
    new CollectionAssert(names).containsOnly("Gandalf", "Frodo");
  }
}
