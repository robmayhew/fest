/*
 * Created on Oct 24, 2008
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
package org.fest.swing.testing;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jvnet.substance.skin.SubstanceBusinessLookAndFeel;

/**
 * Understands setting the look and feel of the windows used in the test suite in this project.
 *
 * @author Alex Ruiz
 */
public class LookAndFeel {

 public static void applySubstanceBusinessLookAndFeel() {
    try {
      UIManager.setLookAndFeel(new SubstanceBusinessLookAndFeel());
    } catch (UnsupportedLookAndFeelException e) {
      e.printStackTrace();
    }
  }

 public static void applySystemLookAndFeel() {
   try {
     UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
   } catch (Exception ignored) {
     ignored.printStackTrace();
   }
 }
}
