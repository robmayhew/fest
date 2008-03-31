/*
 * Created on Sep 5, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.driver;

import java.awt.Component;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.fest.swing.core.ComponentMatcher;

import static org.fest.util.Objects.areEqual;
import static org.fest.util.Strings.*;

/**
 * Matches a <code>{@link JMenuItem}</code> given a simple label or a menu path of the format "menu|submenu|menuitem",
 * for example "File|Open|Can of worms". Adapted from Abbot's own <code>JMenuItemMatcher</code>.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JMenuItemMatcher implements ComponentMatcher {

  private static final String SEPARATOR = "|";
  
  private final String label;

  /**
   * Creates a new </code>{@link JMenuItemMatcher}</code>.
   * @param path the path of the menu to match.
   */
  public JMenuItemMatcher(String... path) {
    this.label = join(path).with(SEPARATOR);
  }

  public boolean matches(Component c) {
    if (!(c instanceof JMenuItem)) return false;
    JMenuItem menuItem = (JMenuItem) c;
    String text = menuItem.getText();
    return areEqual(label, text) || areEqual(label, pathOf(menuItem));
  }

  private String pathOf(JMenuItem menuItem) {
    Component parent = parentOf(menuItem);
    if (parent instanceof JMenuItem) 
      return concat(pathOf((JMenuItem)parent), SEPARATOR, menuItem.getText()); 
    return menuItem.getText();
  }
  
  private Component parentOf(JMenuItem menuItem) {
    Component parent = menuItem.getParent();
    if (parent instanceof JPopupMenu) 
      parent = ((JPopupMenu) parent).getInvoker();
    return parent;
  }

  @Override public String toString() {
    return concat(getClass().getName(), "[", "label=", quote(label), "]");
  }
}