/*
 * Created on Sep 5, 2007
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
package org.fest.swing.fixture;

import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

import abbot.tester.ComponentLocation;
import abbot.tester.ComponentTester;

/**
 * Understands lookup of <code>{@link JPopupMenu}</code>.
 *
 * @author Yvonne Wang
 */
public final class JPopupMenuFixture {
  
  private static final ComponentTester TESTER = new ComponentTester();
  
  protected static JPopupMenu popupMenu(JComponent invoker) {
    return popupMenu(invoker, new ComponentLocation().getPoint(invoker));
  }
  
  protected static JPopupMenu popupMenu(JComponent invoker, Point location) {
    JPopupMenu popUpMenu = invoker.getComponentPopupMenu();
    if (popUpMenu == null) return null;
    TESTER.showPopupMenu(invoker, location.x, location.y);
    if (popUpMenu.isVisible()) return popUpMenu;
    return null;
  }
  
  private JPopupMenuFixture() {}
}
