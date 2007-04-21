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
package org.fest.swing.assertions;

import org.fest.swing.assertions.StringAssert;

import org.testng.annotations.Test;

/**
 * Unit tests for <code>{@link StringAssert}</code>.
 *
 * @author Yvonne Wang
 */
public class StringAssertTest {

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfStringIsNotEmpty() {
    new StringAssert("Luke").isEmpty();
  }

  @Test public void shouldSucceedIfStringIsEmpty() {
    new StringAssert("").isEmpty();
  }

  @Test public void shouldSucceedIfStringIsNull() {
    new StringAssert(null).isEmpty();
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfStringsAreNotEqual() {
    new StringAssert("Luke").isEqualTo("Yoda");
  }

  @Test public void shouldSucceedIfStringsAreEqual() {
    new StringAssert("Anakin").isEqualTo("Anakin");
  } 

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfStringDoesNotContainGivenText() {
    new StringAssert("Luke").containsText("Yoda");
  }

  @Test public void shouldSucceedIfStringContainsGivenText() {
    new StringAssert("Anakin").containsText("akin");
  } 
}
