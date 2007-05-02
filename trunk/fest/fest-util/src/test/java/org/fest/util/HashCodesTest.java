/*
 * Created on May 1, 2007
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
package org.fest.util;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Unit tests for <code>{@link HashCodes}</code>.
 *
 * @author Alex Ruiz
 */
public class HashCodesTest {

  @Test public void shouldCreateHashCodeForBoolean() {
    assertEquals(HashCodes.hashCodeForBoolean(true), Boolean.TRUE.hashCode());
    assertEquals(HashCodes.hashCodeForBoolean(false), Boolean.FALSE.hashCode());
  }
  
  @Test public void shouldCreateHashCodeForChar() {
    assertEquals(HashCodes.hashCodeForChar('a'), new Character('a').hashCode());
  }
  
  @Test public void shouldCreateHashCodeForByte() {
    assertEquals(HashCodes.hashCodeForByte((byte)8), new Byte((byte)8).hashCode());
  }

  @Test public void shouldCreateHashCodeForShort() {
    assertEquals(HashCodes.hashCodeForShort((short)16), new Short((short)16).hashCode());
  }

  @Test public void shouldCreateHashCodeForInt() {
    assertEquals(HashCodes.hashCodeForInt(28), new Integer(28).hashCode());
  }

  @Test public void shouldCreateHashCodeForLong() {
    assertEquals(HashCodes.hashCodeForLong(58690l), new Long(58690l).hashCode());
  }
  
  @Test public void shouldCreateHashCodeForFloat() {
    assertEquals(HashCodes.hashCodeForFloat(28.9f), new Float(28.9f).hashCode());
  }
  
  @Test public void shouldCreateHashCodeForDouble() {
    assertEquals(HashCodes.hashCodeForDouble(943.5), new Double(943.5).hashCode());
  }
  
  @Test public void shouldReturnHashCodeFromObjectIfObjectIsNotNull() {
    String s = "Yoda";
    assertEquals(HashCodes.hashCodeFrom(s), s.hashCode());
  }
  
  @Test public void shouldReturnZeroIfObjectIsNull() {
    assertEquals(HashCodes.hashCodeFrom(null), 0);
  }
}
