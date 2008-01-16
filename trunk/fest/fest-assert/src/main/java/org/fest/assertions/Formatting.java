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

import static org.fest.util.Strings.*;

import org.fest.util.Arrays;

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
    if (o != null && o.getClass().isArray() && !o.getClass().getComponentType().isArray()) 
      return doBracketAround(Arrays.format(o));
    return doBracketAround(quote(o));
  }
  
  private static String doBracketAround(Object o) {
    return concat("<", o, ">");    
  }

  private Formatting() {}

}
