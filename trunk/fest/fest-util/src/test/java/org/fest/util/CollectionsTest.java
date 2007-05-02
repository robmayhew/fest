/*
 * Created on Apr 29, 2007
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
package org.fest.util;

import java.util.ArrayList;
import java.util.Collection;

import org.testng.annotations.Test;

import static org.fest.util.Objects.asList;

import static org.testng.Assert.*;

/**
 * Unit tests for <code>{@link Collections}</code>.
 *
 * @author Yvonne Wang
 */
public class CollectionsTest {

  @Test public void shouldReturnTrueIfCollectionIsEmpty() {
    assertTrue(Collections.isEmpty(new ArrayList<String>()));
  }
  
  @Test public void shouldReturnTrueIfCollectionIsNull() {
    assertTrue(Collections.isEmpty(null));
  }
  
  @Test public void shouldReturnFalseIfCollectionHasElements() {
    assertFalse(Collections.isEmpty(asList("Frodo")));
  }
  
  @Test public void shouldReturnDuplicatesIfTheyExist() {
    Collection<String> duplicates = Collections.duplicatesFrom(asList("Frodo", "Sam", "Frodo"));
    assertEquals(duplicates.size(), 1);
    assertTrue(duplicates.contains("Frodo"));
  }
  
  @Test public void shouldNotReturnDuplicatesIfTheyDoNotExist() {
    Collection<String> duplicates = Collections.duplicatesFrom(asList("Frodo", "Sam", "Gandalf"));
    assertTrue(duplicates.isEmpty());
  }
  
  @Test public void shouldNotReturnDuplicatesIfCollectionIsEmpty() {
    Collection<String> duplicates = Collections.duplicatesFrom(new ArrayList<String>());
    assertTrue(duplicates.isEmpty());
  }
  
  @Test public void shouldNotReturnDuplicatesIfCollectionIsNull() {
    Collection<String> duplicates = Collections.duplicatesFrom(null);
    assertTrue(duplicates.isEmpty());
  }
}
