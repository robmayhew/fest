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
import java.awt.Point;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;

import org.fest.swing.core.ComponentMatcher;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.core.TypeMatcher;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.util.TimeoutWatch;

import static java.lang.System.currentTimeMillis;
import static javax.swing.SwingUtilities.*;

import static org.fest.swing.core.MouseButton.RIGHT_BUTTON;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.util.Swing.centerOf;
import static org.fest.swing.util.TimeoutWatch.startWatchWithTimeoutOf;
import static org.fest.util.Strings.concat;

/**
 * Understands simulation of user input on a <code>{@link JPopupMenu}</code>. Unlike <code>JPopupMenuFixture</code>,
 * this driver only focuses on behavior present only in <code>{@link JPopupMenu}</code>s. This class is intended for
 * internal use only.
 *
 * @author Yvonne Wang
 */
public final class JPopupMenuDriver extends JComponentDriver {

  private static final int POPUP_DELAY = 10000;

  private static int POPUP_TIMEOUT = 5000;

  private static final ComponentMatcher POPUP_MATCHER = new TypeMatcher(JPopupMenu.class, true);

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
    for (int i = 0; i < subElements.length; i++) result[i] = asString(subElements[i]);
    return result;
  }
  
  private String asString(MenuElement e) {
    Component c = e.getComponent();
    if (c instanceof JMenuItem) return ((JMenuItem) c).getText();
    return "-";
  }

  /**
   * Shows a pop-up menu.
   * @param invoker the component to invoke the pop-up menu from.
   * @return the displayed pop-up menu.
   * @throws org.fest.swing.exception.ComponentLookupException if a pop-up menu cannot be found.
   */
  public JPopupMenu showPopupMenu(Component invoker) {
    return showPopupMenu(invoker, centerOf(invoker));
  }

  /**
   * Shows a pop-up menu at the given coordinates.
   * @param invoker the component to invoke the pop-up menu from.
   * @param location the given coordinates for the pop-up menu.
   * @return the displayed pop-up menu.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  public JPopupMenu showPopupMenu(Component invoker, Point location) {
    robot.click(invoker, location, RIGHT_BUTTON, 1);
    JPopupMenu popup = findActivePopupMenu();
    if (popup == null) 
      throw new ComponentLookupException(concat("Unable to show popup at ", location, " on ", format(invoker)));
    long start = currentTimeMillis();
    while (!robot.isReadyForInput(getWindowAncestor(popup)) && currentTimeMillis() - start > POPUP_DELAY) pause();
    return popup;
  }

  /**
   * Returns the currently active pop-up menu, if any. If no pop-up is currently showing, returns <code>null</code>.
   * @return the currently active pop-up menu or <code>null</code>, if no pop-up is currently showing.
   */
  public JPopupMenu findActivePopupMenu() {
    JPopupMenu popup = activePopupMenu();
    if (popup != null || isEventDispatchThread()) return popup;
    TimeoutWatch watch = startWatchWithTimeoutOf(POPUP_TIMEOUT);
    while ((popup = activePopupMenu()) == null) {
      if (watch.isTimeOut()) break;
      pause(100);
    }
    return popup;
  }

  private JPopupMenu activePopupMenu() {
    try {
      return (JPopupMenu)robot.finder().find(POPUP_MATCHER);
    } catch (ComponentLookupException e) {
      return null;
    }
  }
}
