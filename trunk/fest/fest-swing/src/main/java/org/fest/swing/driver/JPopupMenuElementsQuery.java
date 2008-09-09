/*
 * Created on Aug 21, 2008
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

import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.JPopupMenu;
import javax.swing.MenuElement;

import org.fest.swing.edt.GuiQuery;

/**
 * Understands an action, executed in the event dispatch thread, that returns array of <code>{@link MenuElement}</code>s
 * containing the sub-menu for a <code>{@link JPopupMenu}</code>.
 * 
 * @author Alex Ruiz
 */
class JPopupMenuElementsQuery extends GuiQuery<MenuElement[]> {

  private final JPopupMenu popupMenu;

  static MenuElement[] elementsOf(JPopupMenu popupMenu) {
    return execute(new JPopupMenuElementsQuery(popupMenu));
  }
  
  JPopupMenuElementsQuery(JPopupMenu popupMenu) {
    this.popupMenu = popupMenu;
  }

  protected MenuElement[] executeInEDT() {
    return popupMenu.getSubElements();
  }
}