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

import static org.fest.assertions.Fail.fail;
import static org.fest.swing.util.System.LINE_SEPARATOR;
import static org.fest.util.Objects.areEqual;
import static org.fest.util.Strings.*;

/**
 * Understands utility methods for arrays.
 *
 * @author Alex Ruiz
 */
public final class Arrays {

  private static final String NO_COLUMNS = "[[]]";
  private static final String NO_ROWS = "[]";
  private static final String NULL = "null";

  /**
   * Asserts that the given arrays are equal.
   * @param actual the actual array.
   * @param expected the expected array.
   * @param message the message to show if the arrays are not equal.
   * @throws AssertionError if the given arrays are not equal.
   */
  public static void assertEquals(String[][] actual, String[][] expected, String message) {
    if (actual == null && expected == null) return;
    if (actual == null || expected == null) failNotEqual(actual, expected, message);
    if (actual.length != expected.length) failNotEqual(actual, expected, message);
    if (actual.length == 0) return;
    if (actual[0].length != expected[0].length) failNotEqual(actual, expected, message);
    for (int i = 0; i < actual.length; i++)
      for (int j = 0; j < actual[i].length; j++)
        if (!areEqual(actual[i][j], expected[i][j])) failNotEqual(actual, expected, message);
  }
  
  private static void failNotEqual(String[][] actual, String[][] expected, String description) {
    String message = description == null ? "" : concat("[", description, "]");
    fail(concat(message, " expected:<", format(expected), "> but was:<", format(actual), ">"));
  }
  
  /**
   * Formats a two-dimensional <code>String</code> array. For example, the array:
   * <pre>
   * String[][] array = { 
   *      { &quot;0-0&quot;, &quot;0-1&quot;, &quot;0-2&quot; }, 
   *      { &quot;1-0&quot;, &quot;1-1&quot;, &quot;1-2&quot; }, 
   *      { &quot;2-0&quot;, &quot;2-1&quot;, &quot;2-2&quot; },
   *      { &quot;3-0&quot;, &quot;3-1&quot;, &quot;3-2&quot; }, };
   * </pre>
   * will be formatted as:
   * <pre>
   * [['0-0', '0-1', '0-2'],
   *  ['1-0', '1-1', '1-2'],
   *  ['2-0', '2-1', '2-2'],
   *  ['3-0', '3-1', '3-2']]
   * </pre>
   * 
   * @param array the array to format.
   * @return the data of the given array formatted to make it easier to read.
   */
  public static String format(String[][] array) {
    if (array == null) return NULL;
    int size = array.length;
    if (size == 0) return NO_ROWS; 
    if (array[0].length == 0) return NO_COLUMNS;
    StringBuilder b = new StringBuilder();
    b.append("[");
    for (int i = 0; i < size; i++) {
      if (i != 0) b.append(LINE_SEPARATOR).append(" ");
      addLine(array[i], b);
      if (i != size - 1) b.append(",");
    }
    b.append("]");
    return b.toString();
  }

  private static void addLine(String[] line, StringBuilder b) {
    int lineSize = line.length;
    b.append("[");
    for (int i = 0; i < lineSize; i++) {
      b.append(quote(line[i]));
      if (i != lineSize - 1) b.append(", ");
    }
    b.append("]");
  }

  private Arrays() {}
}
