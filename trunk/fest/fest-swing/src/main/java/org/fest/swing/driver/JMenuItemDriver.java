/*
 * Created on Jan 30, 2008
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

import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.core.WindowAncestorFinder.ancestorOf;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.util.Platform.isOSX;
import static org.fest.util.Strings.concat;

import java.awt.Window;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import static java.lang.Boolean.*;

/**
 * Understands simulation of user input on a <code>{@link JMenuItem}</code>. Unlike <code>JMenuItemFixture</code>, this
 * driver only focuses on behavior present only in <code>{@link JMenuItem}</code>s. This class is intended for internal
 * use only.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JMenuItemDriver extends JComponentDriver {

  /**
   * With decreased robot auto delay, OSX pop-up menus don't activate properly. Indicate the minimum delay for proper
   * operation (determined experimentally.)
   */
  private static final int SUBMENU_DELAY = isOSX() ? 100 : 0;

  /**
   * Creates a new </code>{@link JMenuItemDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public JMenuItemDriver(Robot robot) {
    super(robot);
  }

  /**
   * Find and select the given <code>{@link JMenuItem}</code>.
   * @param menuItem the <code>JMenuItem</code> to select.
   * @throws ActionFailedException if the menu to select is disabled.
   * @throws ActionFailedException if the menu has a pop-up and it fails to show up.
   */
  public void selectMenuItem(JMenuItem menuItem) {
    JMenuItemLocation location = new JMenuItemLocation(menuItem);
    activateParentIfIsAMenu(location);
    moveParentWindowToFront(location);
    if (menuItem instanceof JMenu && !location.inMenuBar()) waitForSubMenuToShow();
    click(menuItem);
    ensurePopupIsShowing(menuItem);
  }

  private void ensurePopupIsShowing(JMenuItem menuItem) {
    if (!(menuItem instanceof JMenu)) return;
    JPopupMenu popup = ((JMenu)menuItem).getPopupMenu();
    if (!waitForShowing(popup, robot.settings().timeoutToFindPopup()))
      throw actionFailure(concat("Clicking on menu item <", format(menuItem), "> never showed a pop-up menu"));
    waitForSubMenuToShow();
  }

  private void activateParentIfIsAMenu(JMenuItemLocation location) {
    if (!location.isParentAMenu()) return;
    selectMenuItem((JMenuItem)location.parentOrInvoker());
  }

  private void moveParentWindowToFront(JMenuItemLocation location) {
    if (!location.inMenuBar()) return;
    moveToFront(ancestorOf(location.parentOrInvoker()));
  }

  private void moveToFront(Window w) {
    if (w == null) return;
    // Make sure the window is in front, or its menus may be obscured by another window.
    robot.invokeAndWait(w, new MoveToFrontTask(w));
    robot.moveMouse(w);
  }

  private static class MoveToFrontTask implements Runnable {
    private final Window target;

    MoveToFrontTask(Window target) {
      this.target = target;
    }

    public void run() {
      target.toFront();
    }
  }

  private void waitForSubMenuToShow() {
    int delayBetweenEvents = robot.settings().delayBetweenEvents();
    if (SUBMENU_DELAY > delayBetweenEvents) pause(SUBMENU_DELAY - delayBetweenEvents);
  }

  private void click(JMenuItem menuItem) {
    if (!menuItem.isEnabled()) actionFailure(concat("Menu item <", format(menuItem), "> is disabled"));
    if (isMacOSMenuBar()) {
      clickMenuInMacOSMenuBar(menuItem);
      return;
    }
    super.click(menuItem);
    robot.waitForIdle();
  }

  private boolean isMacOSMenuBar() {
    return isOSX() && (getBoolean("apple.laf.useScreenMenuBar") || getBoolean("com.apple.macos.useScreenMenuBar"));
  }

  private void clickMenuInMacOSMenuBar(JMenuItem menuItem) {
    robot.invokeAndWait(new ClickMenuItemTask(menuItem));
  }

  private static class ClickMenuItemTask implements Runnable {
    private final JMenuItem item;

    ClickMenuItemTask(JMenuItem item) {
      this.item = item;
    }

    public void run() {
      item.doClick();
    }
  }
}
