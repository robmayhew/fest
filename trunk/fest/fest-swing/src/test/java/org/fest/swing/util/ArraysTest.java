/*
 * Created on May 12, 2008
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
package org.fest.swing.util;

import org.testng.annotations.Test;

import org.fest.test.CodeToTest;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.util.System.LINE_SEPARATOR;
import static org.fest.test.ExpectedFailure.expectAssertionError;
import static org.fest.util.Strings.concat;

/**
 * Tests for <code>{@link Arrays}</code>.
 *
 * @author Alex Ruiz 
 */
public class ArraysTest {

  @Test public void shouldPassAsEqualIfBothArraysAreNull() {
    Arrays.assertEquals(null, null, "");
  }
  
  @Test public void shouldFailIfActualArrayIsNullAndExpectedArrayIsNotNull() {
    expectAssertionError("[test] expected:<[]> but was:<null>").on(new CodeToTest() {
      public void run() {
        Arrays.assertEquals(null, new String[0][], "test");
      }
    });
  }
  
  @Test public void shouldFailIfActualArrayIsNotNullAndExpectedArrayIsNull() {
    expectAssertionError("[test] expected:<null> but was:<[]>").on(new CodeToTest() {
      public void run() {
        Arrays.assertEquals(new String[0][], null, "test");
      }
    });
  }
  
  @Test public void shouldFailIfActualAndExpectedHaveDifferentDimensions() {
    expectAssertionError("[test] expected:<[[]]> but was:<[]>").on(new CodeToTest() {
      public void run() {
        Arrays.assertEquals(new String[0][], new String[1][0], "test");
      }
    });    
  }
  
  @Test public void shouldFailIfActualAndExpectedHaveSameDimensionButDifferentData() {
    final String[][] actual = { { "Hello" } };
    final String[][] expected = { { "Bye" } };
    expectAssertionError("[test] expected:<[['Bye']]> but was:<[['Hello']]>").on(new CodeToTest() {
      public void run() {
        Arrays.assertEquals(actual, expected, "test");
      }
    });    
  }

  @Test public void shouldPassIfBothArraysAreEqual() {
    String[][] actual = { { "Hello" } };
    String[][] expected = { { "Hello" } };
    Arrays.assertEquals(actual, expected, "test");
  }

  @Test public void shouldReturnNullWordIfArrayIsNull() {
    assertThat(Arrays.format(null)).isEqualTo("null");
  }
  
  @Test public void shouldReturnEmptyBracesIfFirstDimensionIsZero() {
    String[][] array = new String[0][];
    assertThat(Arrays.format(array)).isEqualTo("[]");
  }
  
  @Test public void shouldReturnEmptyBracesIfSecondDimensionIsZero() {
    String[][] array = new String[1][0];
    assertThat(Arrays.format(array)).isEqualTo("[[]]");
  }
  
  @Test public void shouldFormatArray() {
    String[][] array = {
        { "0-0", "0-1", "0-2"},
        { "1-0", "1-1", "1-2"},
        { "2-0", "2-1", "2-2"},
        { "3-0", "3-1", "3-2"},
    };
    String formatted = concat(
        "[['0-0', '0-1', '0-2'],", LINE_SEPARATOR,
        " ['1-0', '1-1', '1-2'],", LINE_SEPARATOR,
        " ['2-0', '2-1', '2-2'],", LINE_SEPARATOR,
        " ['3-0', '3-1', '3-2']]"
    );
    assertThat(Arrays.format(array)).isEqualTo(formatted);
  }
}
