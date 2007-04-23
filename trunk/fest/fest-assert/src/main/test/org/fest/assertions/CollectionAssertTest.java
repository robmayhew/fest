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

/**
 * Unit tests for <code>{@link CollectionAssert}</code>.
 *
 * @author Yvonne Wang
 */
public class CollectionAssertTest {

  private static Logger logger = Logger.getAnonymousLogger();
  
  @Test() 
  public void shouldFailIfCollectionHasDuplicates() {
    List<String> withDuplicates = new ArrayList<String>();
    withDuplicates.add("Luke");
    withDuplicates.add("Yoda");
    withDuplicates.add("Luke");
    try {
      new CollectionAssert<String>(withDuplicates).doesNotHaveDuplicates();
    } catch (AssertionError e) {
      logger.log(INFO, e.getMessage());
      new StringAssert(e.getMessage()).containsText("Luke");
    }
  }
  
  @Test public void shouldSucceedIfCollectionDoesNotHaveDuplicates() {
    List<String> withoutDuplicates = new ArrayList<String>();
    withoutDuplicates.add("Luke");
    withoutDuplicates.add("Yoda");
    new CollectionAssert<String>(withoutDuplicates).doesNotHaveDuplicates();
  }

  @Test public void shouldSucceedIfCollectionIsEmpty() {
    new CollectionAssert<String>(new ArrayList<String>()).doesNotHaveDuplicates();
  }

  @Test public void shouldSucceedIfCollectionIsNull() {
    new CollectionAssert<String>(null).doesNotHaveDuplicates();
  }
}
