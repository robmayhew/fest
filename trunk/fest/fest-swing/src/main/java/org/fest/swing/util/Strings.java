/*
 * Created on Jan 13, 2008
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

import static org.fest.util.Strings.isEmpty;

/**
 * Understands utility methods related to <code>String</code>s.
 *
 * @author Alex Ruiz
 */
public final class Strings {

  public static boolean isDefaultToString(String s) {
    if (isEmpty(s)) return false;
    int at = s.indexOf("@");
    if (at == -1) return false;
    String hash = s.substring(at + 1, s.length());
    try {
      Integer.parseInt(hash, 16);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
  
  private Strings() {}
}
