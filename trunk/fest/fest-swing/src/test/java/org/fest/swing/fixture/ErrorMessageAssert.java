/*
 * Created on Dec 4, 2007
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
package org.fest.swing.fixture;

import java.awt.Component;
import java.util.logging.Logger;

import org.fest.assertions.AssertExtension;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.isEmpty;
import static org.fest.util.Strings.quote;

/**
 * Understands assertion methods for error messages.
 *
 * @author Alex Ruiz 
 */
class ErrorMessageAssert implements AssertExtension {

  private static final Logger LOGGER = Logger.getAnonymousLogger();

  private final AssertionError error;
  private final Component target;

  ErrorMessageAssert(AssertionError error, Component target) {
    this.error = error;
    this.target = target;
  }

  ErrorMessageAssert contains(PropertyName property, Expected expected, Actual actual) {
    return contains(property, equalsFailedMessage(expected, actual));
  }

  private static String equalsFailedMessage(Expected expected, Actual actual) {
    return concat("expected:<", expected.value, "> but was:<", actual.value, ">");
  }
  
  static Expected expected(Object value) { return new Expected(value); }
  static Actual actual(Object value) { return new Actual(value); }
  
  ErrorMessageAssert contains(PropertyName property, Message message) {
    return contains(property, message.text);
  }

  private ErrorMessageAssert contains(PropertyName property, String message) {
    LOGGER.info(concat("Error message: ", error.getMessage()));
    String completeMessage = concat(formattedProperty(property.name), " ", message);
    assertThat(error.getMessage()).isEqualTo(completeMessage);
    return this;
  }

  static PropertyName property(String propertyName) { return new PropertyName(propertyName); }
  static Message message(String message) { return new Message(message); }

  private String formattedProperty(String name) {
    return concat("[", formattedTarget(), " - property:", quote(name), "]");
  }
  
  private String formattedTarget() {
    String targetName = targetName();
    if (isEmpty(targetName)) return targetType();
    return concat(targetType(), "<", quote(targetName), ">");
  }

  private String targetType() { return target.getClass().getName(); }
  private String targetName() { return target.getName(); }

  private static class PropertyName {
    final String name;
    PropertyName(String name) { this.name = name; }
  }

  private static class Message {
    final String text;
    Message(String text) { this.text = text; }
  }

  private static class Expected {
    final Object value;
    Expected(Object value) { this.value = value; }
  }
  
  private static class Actual {
    final Object value;
    Actual(Object value) { this.value = value; }
  }
}
