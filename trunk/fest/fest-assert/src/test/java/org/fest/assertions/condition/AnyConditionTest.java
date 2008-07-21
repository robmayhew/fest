/*
 * Created on Jul 20, 2008
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
package org.fest.assertions.condition;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.assertions.Condition;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.assertions.condition.AnyCondition.any;
import static org.fest.assertions.condition.LowerCaseCondition.isLowerCase;
import static org.fest.assertions.condition.UpperCaseCondition.isUpperCase;

/**
 * Tests for <code>{@link AnyCondition}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test public class AnyConditionTest {

  private String target;
  
  @BeforeMethod public void setUp() {
    target = "hello";
  }
  
  public void shouldPassIfAnyConditionPasses() {
    assertThat(target).satisfies(any(isUpperCase(), isLowerCase()));
  }
  
  public void shouldFailIfNoConditionPasses() {
    try {
      assertThat(target).satisfies(any(isUpperCase(), new Condition<String>("Never matches") {
        public boolean matches(String value) { return false; }}));
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("<Any of:[Uppercase, Never matches]>");
    }
  }
  
  public void shouldFailIfOnlyOnePresentConditionFails() {
    try {
      assertThat(target).satisfies(any(isUpperCase()));
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("<Any of:[Uppercase]>");
    }
  }

  public void shouldThrowErrorIfConditionArrayIsNull() {
    Condition<String>[] conditions = null;
    try {
      assertThat(target).satisfies(any(conditions));
      fail();
    } catch (NullPointerException e) {
      assertThat(e).message().contains("The array of conditions should not be null");
    }
  }
}
