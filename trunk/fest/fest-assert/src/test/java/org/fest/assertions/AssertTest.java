/*
 * Created on Oct 10, 2007
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

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Tests for {@link GenericAssert}.
 *
 * @author Yvonne Wang
 */
public class AssertTest {

  private GenericAssert<Object> assertion;
  
  @BeforeTest public void setUp() {
    assertion = new GenericAssert<Object>(new Object()) {
      protected GenericAssert<Object> as(String description) { return null; }
      protected GenericAssert<Object> describedAs(String description) { return null; }
      protected GenericAssert<Object> isEqualTo(Object expected) { return null; }
      protected GenericAssert<Object> isNotEqualTo(Object other) { return null; }
      protected GenericAssert<Object> isNotNull() { return null; }
      protected GenericAssert<Object> isNotSameAs(Object other) { return null; }
      protected GenericAssert<Object> isSameAs(Object expected) { return null; }
      protected GenericAssert<Object> satisfies(Condition<Object> condition) { return null; }
    };
  }
  
  @Test(expectedExceptions = UnsupportedOperationException.class)
  public void shouldThrowErrorIfEqualsCalled() {
    assertion.equals(null);
  }
}
