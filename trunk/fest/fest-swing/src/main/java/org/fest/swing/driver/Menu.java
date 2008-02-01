/*
 * Created on Jan 31, 2008
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
package org.fest.swing.driver;

import java.awt.Component;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * Understands a Swing menu item.
 *
 * @author Alex Ruiz
 */
final class Menu {

  private Component parent;
  private JPopupMenu parentPopup;
  
  private final boolean isMenu;
  private final boolean inMenuBar;
  
  Menu(JMenuItem menuItem) {
    parent = menuItem.getParent();
    if (parent instanceof JPopupMenu) {
      parentPopup = (JPopupMenu)parent;
      parent = ((JPopupMenu)parent).getInvoker();
    }
    isMenu = menuItem instanceof JMenu;
    inMenuBar = parent instanceof JMenuBar;
  }
  
  /**
   * Indicates whether the parent of this menu is another menu.
   * @return <code>true</code> if the parent of this menu is another menu, <code>false</code> otherwise.
   */
  boolean parentIsMenu() {
    if (!(parent instanceof JMenuItem)) return false;
    return parentPopup == null || !parentPopup.isShowing();
  }
  
  /**
   * Returns the parent of this menu, or its invoker, if the menu is in a pop-up.
   * @return the parent of this menu, or its invoker, if the menu is in a pop-up.
   */
  Component parent() { return parent; }

  /**
   * Returns the parent pop-up menu, or <code>null</code> if this menu is not in a pop-up.
   * @return the parent pop-up menu, or <code>null</code> if this menu is not in a pop-up.
   */
  JPopupMenu parentPopup() { return parentPopup; }

  /**
   * Indicates whether this menu is an instance of <code>{@link JMenu}</code>.
   * @return <code>true</code> if this menu is an instance of <code>JMenu</code>, <code>false</code> otherwise.
   */
  boolean isMenu() { return isMenu; }

  /**
   * Indicates whether this menu is in a <code>{@link JMenuBar}</code>.
   * @return <code>true</code> if this menu is in a <code>JMenuBar</code>, <code>false</code> otherwise.
   */
  boolean inMenuBar() { return inMenuBar; }
}
