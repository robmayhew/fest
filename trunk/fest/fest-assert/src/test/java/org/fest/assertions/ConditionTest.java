/*
 * Created on Sep 17, 2007
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

import static org.fest.test.ExpectedFailure.expect;
import static org.fest.util.Strings.isEmpty;

import org.fest.test.CodeToTest;
import org.testng.annotations.Test;

/**
 * Tests interaction between an <code>{@link GenericAssert}</code> object and a <code>{@link Condition}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ConditionTest {

  private static class UpperCaseCondition extends Condition<String> {
    @Override public boolean matches(String value) {
      if(isEmpty(value)) return false;
      return value.equals(value.toUpperCase());
    }
  }
    
  @Test public void shouldFailIfConditionNotSatisfied() {
    expect(AssertionError.class).withMessage("[Test] condition failed with: <'hello'>").on(new CodeToTest() {
      public void run() {
        new StringAssert("hello").as("Test").satisfies(new UpperCaseCondition());
      }
    });
  }
  
  @Test public void shouldFailIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expect(AssertionError.class).withMessage("[Test] expected:Uppercase but was:<'hello'>").on(new CodeToTest() {
      public void run() {
        new StringAssert("hello").as("Test").satisfies(new UpperCaseCondition().as("Uppercase"));
      }
    });
  }
}
