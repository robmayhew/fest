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
package org.fest.swing.fixture;

import java.awt.Component;
import java.util.logging.Logger;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.isEmpty;
import static org.fest.util.Strings.quote;

/**
 * Understands utility methods related to testing.
 *
 * @author Alex Ruiz
 */
final class ErrorMessages {

  private static final Logger LOGGER = Logger.getAnonymousLogger();
  
  static final String EXPECTED_TRUE_BUT_WAS_FALSE = equalsFailedMessage("true", "false");
  static final String EXPECTED_FALSE_BUT_WAS_TRUE = equalsFailedMessage("false", "true");

  static String equalsFailedMessage(Object expected, Object actual) {
    return concat("expected:<", expected, "> but was:<", actual, ">");
  }
  
  private final Component target;

  ErrorMessages(Component target) {
    this.target = target;
  }

  void assertIsCorrect(AssertionError error, String propertyName, String expectedMessage) {
    logError(error);
    String completeMessage = concat(property(propertyName), " ", expectedMessage);
    assertThat(error.getMessage()).isEqualTo(completeMessage);
  }
  
  private void logError(Throwable error) {
    LOGGER.info(concat("Error message: ", error.getMessage()));
  }

  private String property(String name) {
    return concat("[", formattedTarget(), " - property:", quote(name), "]");
  }
  
  private String formattedTarget() {
    String targetName = targetName();
    if (isEmpty(targetName)) return targetType();
    return concat(targetType(), "<", quote(targetName), ">");
  }

  private String targetType() { return target.getClass().getName(); }
  private String targetName() { return target.getName(); }
}
