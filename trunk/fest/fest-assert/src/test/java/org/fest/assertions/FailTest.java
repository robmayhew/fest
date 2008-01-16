/*
 * Created on Sep 16, 2007
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

import static org.fest.util.Strings.*;
import static org.testng.Assert.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link Fail}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FailTest {

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldThrowExceptionWhenFailing() {
    Fail.fail();
  }
  
  @Test public void shouldThrowExceptionWithGivenMessageAndCause() {
    Throwable t = new Throwable();
    String message = "Some Throwable";
    try {
      Fail.fail(message, t);
      shouldHaveFailed();
    } catch (AssertionError e) {
      assertSame(e.getCause(), t);
      assertEquals(e.getMessage(), message);
    }
  }

  @Test(dataProvider = "messageProvider") 
  public void failIfEqualShouldFailIfEqual(String message) {
    try {
      Fail.failIfEqual(message, "Yoda", "Yoda");
      shouldHaveFailed();
    } catch (AssertionError e) {
      String expectedMessage = concat(messageFrom(message), "<'Yoda'> should not be equal to <'Yoda'>");
      assertEquals(e.getMessage(), expectedMessage);
    }
  }
  
  @Test public void failIfEqualShouldNotFailIfNotEqual() {
    try {
      Fail.failIfEqual("", "Yoda", "Ben");
    } catch (AssertionError e) {
      unexpected(e);
    }
  }
  
  @Test(dataProvider = "messageProvider") 
  public void failIfNotEqualShouldFailIfNotEqual(String message) {
    try {
      Fail.failIfNotEqual(message, "Yoda", "Luke");
      shouldHaveFailed();
    } catch (AssertionError e) {
      String expectedMessage = concat(messageFrom(message), "expected:<'Luke'> but was:<'Yoda'>");
      assertEquals(e.getMessage(), expectedMessage);
    }
  }
  
  @Test void failIfNotEqualShouldNotFailIfEqual() {
    try {
      Fail.failIfNotEqual("", "Yoda", "Yoda");
    } catch (AssertionError e) {
      unexpected(e);
    }
  }

  @Test(dataProvider = "messageProvider") 
  public void failIfNullShouldFailIfNull(String message) {
    try {
      Fail.failIfNull(message, null);
      shouldHaveFailed();
    } catch (AssertionError e) {
      String expectedMessage = concat(messageFrom(message), "expecting a non-null object");
      assertEquals(e.getMessage(), expectedMessage);
    }
  }
  
  @Test void failIfNullShouldNotFailIfNotNull() {
    try {
      Fail.failIfNull("", "Luke");
    } catch (AssertionError e) {
      unexpected(e);
    }
  }

  @Test(dataProvider = "messageProvider") 
  public void failIfNotNullShouldFailIfNotNull(String message) {
    try {
      Fail.failIfNotNull(message, "Leia");
      shouldHaveFailed();
    } catch (AssertionError e) {
      String expectedMessage = concat(messageFrom(message), "<'Leia'> should be null");
      assertEquals(e.getMessage(), expectedMessage);
    }
  }
  
  @Test void failIfNotNullShouldNotFailIfNull() {
    try {
      Fail.failIfNotNull("", null);
    } catch (AssertionError e) {
      unexpected(e);
    }
  }

  @Test(dataProvider = "messageProvider") 
  public void failIfSameShouldFailIfSame(String message) {
    Object o = new Object();
    try {
      Fail.failIfSame(message, o, o);
      shouldHaveFailed();
    } catch (AssertionError e) {
      String expectedMessage = concat(messageFrom(message), "given objects are same:<", o, ">");
      assertEquals(e.getMessage(), expectedMessage);
    }
  }
  
  @Test void failIfSameShouldNotFailIfNotSame() {
    try {
      Fail.failIfSame("", "Luke", "Anakin");
    } catch (AssertionError e) {
      unexpected(e);
    }
  }

  @Test(dataProvider = "messageProvider") 
  public void failIfNotSameShouldFailIfNotSame(String message) {
    try {
      Fail.failIfNotSame(message, "Ben", "Han");
      shouldHaveFailed();
    } catch (AssertionError e) {
      String expectedMessage = concat(messageFrom(message), "expected same instance but found <'Ben'> and <'Han'>");
      assertEquals(e.getMessage(), expectedMessage);
    }
  }
  
  @Test void failIfNotSameShouldNotFailIfSame() {
    Object o = new Object();
    try {
      Fail.failIfNotSame("", o, o);
    } catch (AssertionError e) {
      unexpected(e);
    }
  }

  @Test
  public void shouldFailWithGivenMessage() {
    String message = "Failed :(";
    try {
      Fail.fail(message);
      shouldHaveFailed();
    } catch (AssertionError e) {
      assertEquals(e.getMessage(), message);
    }
  }
  
  @DataProvider(name = "messageProvider")
  public Object[][] messageProvider() {
    return new Object[][] {
        { "a message" },
        { "" },
        { null }
    };
  }

  private void shouldHaveFailed() {
    fail(concat(AssertionError.class.getSimpleName(), " should have been thrown"));
  }

  private String messageFrom(String s) {
    if (isEmpty(s)) return "";
    return concat("[", s, "] ");
  }

  private void unexpected(AssertionError e) {
    fail(concat("Unexpected exception: ", quote(e.getMessage())), e);
  }
}
