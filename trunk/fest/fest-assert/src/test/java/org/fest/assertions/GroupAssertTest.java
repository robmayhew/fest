/*
 * Created on Jan 15, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.assertions;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link GroupAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class GroupAssertTest {

  @Test public void shouldReturnIntAssertForSize() {
    GroupAssert<Object> a = new GroupAssert<Object>(new Object()) {

      int actualGroupSize() {
        return 3;
      }

      GroupAssert<Object> hasSize(int expected) { return null; }
      void isEmpty() {}
      void isNullOrEmpty() {}
      GroupAssert<Object> isNotEmpty() { return null; }
      GenericAssert<Object> as(String description) { return null; }
      GenericAssert<Object> describedAs(String description) { return null; }
      GenericAssert<Object> isEqualTo(Object expected) { return null; }
      GenericAssert<Object> isNotEqualTo(Object other) { return null; }
      GenericAssert<Object> isNotNull() { return null; }
      GenericAssert<Object> isNotSameAs(Object other) { return null; }
      GenericAssert<Object> isSameAs(Object expected) { return null; }
      GenericAssert<Object> satisfies(Condition<Object> condition) { return null; }
    };
    a.size().isEqualTo(3);
  }
}
