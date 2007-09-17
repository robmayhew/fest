/*
 * Created on Sep 14, 2007
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

import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.isEmpty;
import static org.fest.util.Strings.quote;

import java.util.Arrays;

/**
 * Understands utility methods related to formatting.
 *
 * @author Yvonne Wang
 */
final class Formatting {

  private static final String EMPTY_MESSAGE = "";

  static String format(String message) {
    if (isEmpty(message)) return EMPTY_MESSAGE;
    return concat("[", message, "] ");
  }

  static String bracketAround(Object o) {
    return doBracketAround(quote(o));
  }
  
  static String bracketAround(Object[] array) {
    return doBracketAround(toString(array));
  }  
  
  static String bracketAround(boolean val) {
    return doBracketAround(String.valueOf(val));
  }

  static String bracketAround(boolean[] array) {
    return doBracketAround(Arrays.toString(array));
  }  
  
  static String bracketAround(byte val) {
    return doBracketAround(String.valueOf(val));
  }

  static String bracketAround(byte[] array) {
    return doBracketAround(Arrays.toString(array));
  }  
  
  static String bracketAround(char val) {
    return doBracketAround(quote(String.valueOf(val)));
  }

  static String bracketAround(char[] array) {
    return doBracketAround(Arrays.toString(array));
  }  
  
  static String bracketAround(double val) {
    return doBracketAround(String.valueOf(val));
  }

  static String bracketAround(double[] array) {
    return doBracketAround(Arrays.toString(array));
  }  
  
  static String bracketAround(float val) {
    return doBracketAround(String.valueOf(val));
  }

  static String bracketAround(float[] array) {
    return doBracketAround(Arrays.toString(array));
  }  
  
  static String bracketAround(int val) {
    return doBracketAround(String.valueOf(val));
  }

  static String bracketAround(int[] array) {
    return doBracketAround(Arrays.toString(array));
  }  
  
  static String bracketAround(long val) {
    return doBracketAround(String.valueOf(val));
  }

  static String bracketAround(long[] array) {
    return doBracketAround(Arrays.toString(array));
  }  
  
  static String bracketAround(short val) {
    return doBracketAround(String.valueOf(val));
  }

  static String bracketAround(short[] array) {
    return doBracketAround(Arrays.toString(array));
  }  
  
  static String doBracketAround(Object o) {
    return concat("<", o, ">");    
  }

  private static String toString(Object[] array) {
    if (array == null) return null;
    int max = array.length - 1;
    if (max == -1) return "[]";
    StringBuilder b = new StringBuilder();
    b.append('[');
    for (int i = 0;; i++) {
      b.append(quote(array[i]));
      if (i == max) return b.append(']').toString();
      b.append(", ");
    }
  }

  private Formatting() {}

}
