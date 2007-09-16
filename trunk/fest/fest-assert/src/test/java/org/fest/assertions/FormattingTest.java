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

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Tests for <code>{@link Formatting}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FormattingTest {

  @Test public void shouldReturnEmptyStringIfMessageIsNull() {
    assertEquals(Formatting.formatMessage(null), "");
  }
  
  @Test public void shouldReturnEmptyStringIfMessageIsEmpty() {
    assertEquals(Formatting.formatMessage(""), "");
  }
  
  @Test public void shouldReturnFormattedMessageIfMessageIsNotEmpty() {
    assertEquals(Formatting.formatMessage("some message"), "[some message] ");
  }
  
  @Test public void shouldFormatObject() {
    assertEquals(Formatting.formatObject(new Integer(3)), "<3>");
  }
  
  @Test public void shouldFormatString() {
    assertEquals(Formatting.formatObject("Yoda"), "<'Yoda'>");
  }

  @Test public void shouldFormatNullObject() {
    assertEquals(Formatting.formatObject(null), "<null>");
  }
}
