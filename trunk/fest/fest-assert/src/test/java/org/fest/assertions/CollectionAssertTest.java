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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.testng.annotations.Test;

import static java.util.logging.Level.INFO;

import static org.fest.assertions.Fail.fail;
import static org.fest.util.Collections.list;

/**
 * Tests for <code>{@link CollectionAssert}</code>.
 *
 * @author Yvonne Wang
 */
public class CollectionAssertTest {

  private static Logger logger = Logger.getAnonymousLogger();
  
  @Test() 
  public void shouldFailIfCollectionHasDuplicates() {
    List<String> list = list("Luke", "Yoda", "Luke");
    try {
      new CollectionAssert<String>(list).doesNotHaveDuplicates();
      fail("Should have failed");
    } catch (AssertionError e) {
      String message = e.getMessage();
      logger.log(INFO, message);
      new StringAssert(message).contains("Luke");
    }
  }
  
  @Test public void shouldSucceedIfCollectionDoesNotHaveDuplicates() {
    new CollectionAssert<String>(list("Luke", "Yoda")).doesNotHaveDuplicates();
  }

  @Test public void shouldSucceedIfCollectionIsEmpty() {
    new CollectionAssert<String>(new ArrayList<String>()).doesNotHaveDuplicates();
  }

  @Test public void shouldSucceedIfCollectionIsNull() {
    new CollectionAssert<String>(null).doesNotHaveDuplicates();
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfCollectionIsNotEmptyAndExpectingEmpty() {
    new CollectionAssert<String>(list("Yoda")).isEmpty();
  }
  
  @Test public void shouldPassIfCollectionIsEmptyAndExpectingEmpty() {
    new CollectionAssert<String>(new ArrayList<String>()).isEmpty();
  }
  
  @Test public void shouldPassIfCollectionIsNullAndExpectingEmpty() {
    List<String> nullList = null;
    new CollectionAssert<String>(nullList).isEmpty();
  }
  
  @Test public void shouldPassIfCollectionHasExpectedSize() {
    List<String> names = list("Gandalf", "Frodo", "Sam");
    new CollectionAssert<String>(names).hasSize(3);
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfCollectionDoesNotHaveExpectedSize() {
    List<String> names = list("Frodo");
    new CollectionAssert<String>(names).hasSize(2);
  }
  
  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfCollectionIsNullAndExpectingSomeSize() {
    new CollectionAssert<String>(null).hasSize(0);
  }
  
  @Test public void shouldPassIfCollectionIsNotEmptyAndExpectingNotEmpty() {
    List<String> names = list("Frodo", "Sam");
    new CollectionAssert<String>(names).isNotEmpty();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfCollectionIsNotEmptyAndExpectingNotEmpty() {
    List<String> names = new ArrayList<String>();
    new CollectionAssert<String>(names).isNotEmpty();
  }
  
  @Test public void shouldPassIfCollectionIsNullAndExpectingNull() {
    new CollectionAssert<String>(null).isNull();
  }
  
  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfCollectionIsNotNullAndExpectingNull() {
    new CollectionAssert<String>(new ArrayList<String>()).isNull();
  }
}
