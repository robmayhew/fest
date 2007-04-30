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

/**
 * Unit tests for <code>{@link CollectionAssert}</code>.
 *
 * @author Yvonne Wang
 */
public class CollectionAssertTest {

  private static Logger logger = Logger.getAnonymousLogger();
  
  @Test() 
  public void shouldFailIfCollectionHasDuplicates() {
    List<String> withDuplicates = collectionWithoutDuplicates();
    withDuplicates.add("Luke");
    try {
      new CollectionAssert<String>(withDuplicates).doesNotHaveDuplicates();
      fail("Should have failed");
    } catch (AssertionError e) {
      String message = e.getMessage();
      logger.log(INFO, message);
      new StringAssert(message).containsText("Luke");
    }
  }
  
  @Test public void shouldSucceedIfCollectionDoesNotHaveDuplicates() {
    new CollectionAssert<String>(collectionWithoutDuplicates()).doesNotHaveDuplicates();
  }

  @Test public void shouldSucceedIfCollectionIsEmpty() {
    new CollectionAssert<String>(new ArrayList<String>()).doesNotHaveDuplicates();
  }

  @Test public void shouldSucceedIfCollectionIsNull() {
    new CollectionAssert<String>(null).doesNotHaveDuplicates();
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfCollectionIsNotEmpty() {
    new CollectionAssert<String>(collectionWithoutDuplicates()).isEmpty();
  }
  
  @Test public void shouldPassIfCollectionIsEmpty() {
    new CollectionAssert<String>(new ArrayList<String>()).isEmpty();
  }
  
  @Test public void shouldPassIfCollectionIsNull() {
    List<String> nullList = null;
    new CollectionAssert<String>(nullList).isEmpty();
  }

  private List<String> collectionWithoutDuplicates() {
    List<String> withoutDuplicates = new ArrayList<String>();
    withoutDuplicates.add("Luke");
    withoutDuplicates.add("Yoda");
    return withoutDuplicates;
  }
}
