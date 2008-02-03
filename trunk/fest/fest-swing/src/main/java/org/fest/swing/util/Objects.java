/*
 * Created on Jan 14, 2008
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

import static org.fest.swing.util.Strings.isDefaultToString;
import static org.fest.util.Arrays.format;

/**
 * Understands utility methods related to <code>Object</code>s.
 *
 * @author Yvonne Wang 
 * @author Alex Ruiz
 */
public final class Objects {

  /** Represents a "null" <code>String</code>. */
  public static final String NULL = "null";

  /**
   * Indicates that a <code>String</code> is the default <code>toString</code> implementation of an
   * <code>Object</code>.
   */
  public static final String DEFAULT_TO_STRING = "<default-tostring>";

  /**
   * Convert an <code>Object</code> into its <code>String</code> representation. Handles <code>null</code> values
   * and one-dimensional arrays. 
   * @param o the given <code>Object</code>. 
   * @return <code>String</code> representation of a given <code>Object</code>.
   */
  public static String toStringOf(Object o) {
    if (o == null) return NULL;
    if (o.getClass().isArray()) return format(o);
    String s = o.toString();
    if (isDefaultToString(s)) return DEFAULT_TO_STRING;
    return s;
  }
  
  private Objects() {}
}
