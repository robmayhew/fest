/*
 * Created on Apr 15, 2008
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

import java.awt.Color;

import static org.fest.util.Strings.*;

/**
 * Understands utility methods related to colors.
 *
 * @author Alex Ruiz
 */
public final class Colors {

  /**
   * Returns a <code>{@link Color}</code> from the given <code>String</code> containing the hexadecimal coding of a 
   * color. 
   * @param hexString contains the hexadecimal coding of a color.
   * @return a <code>Color</code> from the given <code>String</code> containing the hexadecimal coding of a color. 
   * @throws IllegalArgumentException if the value represented by the given hexadecimal coding is not valid.
   */
  public static Color colorFromHexString(String hexString) {
    try {
      return new Color(Integer.parseInt(hexString, 16));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(concat("The hexadecimal code ", quote(hexString), " is not a valid color"));
    }
  }
  
  private Colors() {}
}
