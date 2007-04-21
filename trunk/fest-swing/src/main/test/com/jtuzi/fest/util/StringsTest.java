/*
 * Created on Sep 22, 2006
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2006 the original author or authors.
 */
package com.jtuzi.fest.util;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import com.jtuzi.fest.util.Strings;

import org.testng.annotations.Test;

/**
 * Unit tests for <code>{@link Strings}</code>.
 * 
 * @author Alex Ruiz
 */
public class StringsTest {

  @Test public void shouldReturnIsEmptyWithEmptyString() {
    assertTrue(Strings.isEmpty(""));
  }

  @Test public void shouldReturnIsEmptyWithNullString() {
    assertTrue(Strings.isEmpty(null));
  }

  @Test public void shouldReturnIsNotEmptyWithNotEmptyString() {
    assertFalse(Strings.isEmpty("foo"));
  }

  @Test public void shouldQuoteNotEmptyString() {
    assertEquals(Strings.quote("foo"), "'foo'");
  }

  @Test public void shouldQuoteEmptyString() {
    assertEquals(Strings.quote(""), "''");
  }

  @Test public void shouldNotQuoteNullString() {
    assertNull(Strings.quote(null));
  }

  @Test public void shouldNotQuoteIfNotString() {
    assertEquals(Strings.quote(9), 9);
  }
  
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfDelimeterIsNull() {
    Strings.join(null, "Uno", "Dos");
  }
  
  @Test public void shouldReturnEmptyStringIfArrayToJoinIsNull() {
    assertEquals(Strings.join("|", (String[])null), "");
  }
  
  @Test public void shouldJoinArrayUsingDelimeter() {
    assertEquals(Strings.join("|", "Luke", "Leia", "Han"), "Luke|Leia|Han");
  }
}
