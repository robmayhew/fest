/*
 * Created on Jan 31, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.driver;

import java.awt.Component;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;

import org.fest.swing.core.RobotFixture;

/**
 * Understands simulation of user input on a <code>{@link JPopupMenu}</code>. Unlike <code>JPopupMenuFixture</code>,
 * this driver only focuses on behavior present only in <code>{@link JPopupMenu}</code>s. This class is intended for
 * internal use only.
 *
 * @author Yvonne Wang
 */
public class JPopupMenuDriver extends JComponentDriver {

  /**
   * Creates a new </code>{@link JPopupMenuDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public JPopupMenuDriver(RobotFixture robot) {
    super(robot);
  }

  /**
   * Returns the contents of the pop-up menu as a <code>String</code> array.
   * @param popupMenu the target <code>JPopupMenu</code>.
   * @return the contents of the pop-up menu as a <code>String</code> array.
   */
  public String[] menuLabelsOf(JPopupMenu popupMenu) {
    MenuElement[] subElements = popupMenu.getSubElements();
    String[] result = new String[subElements.length];
    for (int i = 0; i < subElements.length; i++) result[i] = format(subElements[i]);
    return result;
  }

  private String format(MenuElement e) {
    Component c = e.getComponent();
    if (c instanceof JMenuItem) return ((JMenuItem) c).getText();
    return "-";
  }
}
