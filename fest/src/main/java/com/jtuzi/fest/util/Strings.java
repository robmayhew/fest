/*
 * Created on Jun 2, 2006
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

/**
 * Understands String-related utility methods.
 * 
 * @author Alex Ruiz
 */
public final class Strings {

  public static boolean isEmpty(String s) {
    return s == null || s.length() == 0;
  }

  public static String quote(String s) {
    return s != null ? "'" + s + "'" : null;
  }

  public static Object quote(Object o) {
    return o instanceof String ? quote(o.toString()) : o;
  }

  public static String join(String delimeter, String...strings) {
    if (delimeter == null) throw new IllegalArgumentException("Delimiter should not be null");
    if (Objects.isEmpty(strings)) return "";
    StringBuilder b = new StringBuilder();
    int stringCount = strings.length;
    for (int i = 0; i < stringCount; i++) {
      String s = strings[i];
      b.append(s != null ? s : "");
      if (i < stringCount - 1) b.append(delimeter);
    }
    return b.toString();
  }
  
  private Strings() {}
}
