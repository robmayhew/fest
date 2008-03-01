/*
 * Created on Jan 26, 2008
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

import java.awt.*;

import javax.accessibility.AccessibleAction;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import org.fest.swing.core.*;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.task.ActivateWindowTask;
import org.fest.swing.task.PerformDefaultAccessibleActionTask;
import org.fest.swing.task.RequestFocusTask;
import org.fest.swing.util.TimeoutWatch;

import static java.lang.System.currentTimeMillis;
import static javax.swing.SwingUtilities.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.MouseButton.*;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.core.Settings.timeoutToBeVisible;
import static org.fest.swing.driver.FocusMonitor.addFocusMonitorTo;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.util.AWT.*;
import static org.fest.swing.util.Platform.*;
import static org.fest.swing.util.Swing.centerOf;
import static org.fest.swing.util.TimeoutWatch.startWatchWithTimeoutOf;
import static org.fest.util.Strings.*;

/**
 * Understands simulation of user input on a <code>{@link Component}</code>. This class is intended for internal use
 * only.
 *
 * @author Alex Ruiz
 */
public class ComponentDriver {

  private static final int POPUP_TIMEOUT = 5000;
  private static final int POPUP_DELAY = 10000;

  private static final ComponentMatcher POPUP_MATCHER = new TypeMatcher(JPopupMenu.class, true);
  
  private static final String ENABLED_PROPERTY = "enabled";
  private static final String SIZE_PROPERTY = "size";
  private static final String VISIBLE_PROPERTY = "visible";

  protected final Robot robot;

  private final DragAndDrop dragAndDrop;

  /**
   * Creates a new </code>{@link ComponentDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public ComponentDriver(Robot robot) {
    this.robot = robot;
    dragAndDrop = new DragAndDrop(robot);
  }

  /**
   * Simulates a user clicking once the given <code>{@link Component}</code> using the left mouse button.
   * @param c the <code>Component</code> to click on.
   */
  public void click(Component c) {
    click(c, LEFT_BUTTON);
  }
  
  /**
   * Simulates a user right-clicking the given <code>{@link Component}</code>.
   * @param c the <code>Component</code> to click on.
   */
  public void rightClick(Component c) {
    click(c, RIGHT_BUTTON);
  }

  /**
   * Simulates a user clicking once the given <code>{@link Component}</code> using the given mouse button.
   * @param c the <code>Component</code> to click on.
   * @param button the mouse button to use.
   */
  public void click(Component c, MouseButton button) {
    click(c, button, 1);
  }

  /**
   * Simulates a user double-clicking the given <code>{@link Component}</code>.
   * @param c the <code>Component</code> to click on.
   */
  public void doubleClick(Component c) {
    click(c, LEFT_BUTTON, 2);
  }
  
  /**
   * Simulates a user clicking the given mouse button, the given times on the given <code>{@link Component}</code>.
   * @param c the <code>Component</code> to click on.
   * @param button the mouse button to click.
   * @param times the number of times to click the given mouse button.
   */
  public void click(Component c, MouseButton button, int times) {
    robot.click(c, centerOf(c), button, times);
  }

  /**
   * Simulates a user clicking at the given position on the given <code>{@link Component}</code>.
   * @param target the <code>Component</code> to click on.
   * @param where the position where to click.
   */
  public void click(Component target, Point where) {
    robot.click(target, where, LEFT_BUTTON, 1);
  }

  /**
   * Gives input focus to the given <code>{@link Component}</code>. Note that the component may not yet have focus when
   * this method returns.
   * @param c the component to give focus to.
   */
  public void focus(Component c) {
    focus(c, false);
  }

  /**
   * Gives input focus to the given <code>{@link Component}</code> and waits until the <code>{@link Component}</code>
   * has focus.
   * @param c the component to give focus to.
   */
  public void focusAndWaitForFocusGain(Component c) {
    focus(c, true);
  }

  private void focus(final Component c, boolean wait) {
    Component currentOwner = focusOwner();
    if (currentOwner == c) return;
    FocusMonitor focusMonitor = addFocusMonitorTo(c);
    // for pointer focus
    robot.mouseMove(c);
    robot.waitForIdle();
    // Make sure the correct window is in front
    Window currentOwnerAncestor = currentOwner != null ? ancestorOf(currentOwner) : null;
    Window componentAncestor = ancestorOf(c);
    if (currentOwnerAncestor != componentAncestor) {
      activate(componentAncestor);
      robot.waitForIdle();
    }
    robot.invokeAndWait(c, new RequestFocusTask(c));
    try {
      if (wait) {
        TimeoutWatch watch = startWatchWithTimeoutOf(timeoutToBeVisible());
        while (!focusMonitor.hasFocus()) {
          if (watch.isTimeOut()) throw actionFailure(concat("Focus change to ", format(c), " failed"));
          pause();
        }
      }
    } finally {
      c.removeFocusListener(focusMonitor);
    }
  }

  /**
   * Activates the given <code>{@link Window}</code>. "Activate" means that the given window gets the keyboard focus.
   * @param w the window to activate. 
   */
  protected void activate(Window w) {
    robot.invokeAndWait(w, new ActivateWindowTask(w));
    robot.mouseMove(w); // For pointer-focus systems
  }

  /**
   * Asserts that the size of the <code>{@link Component}</code> is equal to given one.
   * @param c the target component.
   * @param size the given size to match.
   * @throws AssertionError if the size of the <code>Window</code> is not equal to the given size.
   */
  public void requireSize(Component c, Dimension size) {
    assertThat(c.getSize()).as(propertyName(c, SIZE_PROPERTY)).isEqualTo(size);
  }

  /**
   * Asserts that the <code>{@link Component}</code> is visible.
   * @param c the target component.
   * @throws AssertionError if the <code>Component</code> is not visible.
   */
  public void requireVisible(Component c) {
    assertThat(c.isVisible()).as(visibleProperty(c)).isTrue();
  }

  /**
   * Asserts that the <code>{@link Component}</code> is not visible.
   * @param c the target component.
   * @throws AssertionError if the <code>Component</code> is visible.
   */
  public void requireNotVisible(Component c) {
    assertThat(c.isVisible()).as(visibleProperty(c)).isFalse();
  }

  private String visibleProperty(Component c) { 
    return propertyName(c, VISIBLE_PROPERTY); 
  }

  /**
   * Asserts that the <code>{@link Component}</code> is enabled.
   * @param c the target component.
   * @throws AssertionError if the <code>Component</code> is disabled.
   */
  public void requireEnabled(Component c) {
    assertThat(c.isEnabled()).as(enabledProperty(c)).isTrue();
  }

  /**
   * Asserts that the <code>{@link Component}</code> is enabled.
   * @param c the target component.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @throws WaitTimedOutError if the <code>Component</code> is never enabled.
   */
  public void requireEnabled(final Component c, Timeout timeout) {
    Condition targetEnabledCondition = new Condition(enabledProperty(c)) {
      @Override public boolean test() {
        return c.isEnabled();
      }
    };
    pause(targetEnabledCondition, timeout);
  }

  /**
   * Asserts that the <code>{@link Component}</code> is disabled.
   * @param c the target component.
   * @throws AssertionError if the <code>Component</code> is enabled.
   */
  public void requireDisabled(Component c) {
    assertThat(c.isEnabled()).as(enabledProperty(c)).isFalse();
  }

  private String enabledProperty(Component c) { 
    return propertyName(c, ENABLED_PROPERTY); 
  }

  /**
   * Simulates a user pressing and releasing the given keys on the <code>{@link Component}</code>.
   * @param c the target component.
   * @param keyCodes one or more codes of the keys to press.
   * @see java.awt.event.KeyEvent
   */
  public void pressAndReleaseKeys(Component c, int... keyCodes) {
    focus(c);
    robot.pressAndReleaseKeys(keyCodes);
  }

  /**
   * Simulates a user pressing given key on the <code>{@link Component}</code>.
   * @param c the target component.
   * @param keyCode the code of the key to press.
   * @see java.awt.event.KeyEvent
   */
  public void pressKey(Component c, int keyCode) {
    focus(c);
    robot.pressKey(keyCode);
  }

  /**
   * Simulates a user releasing the given key on the <code>{@link Component}</code>.
   * @param c the target component.
   * @param keyCode the code of the key to release.
   * @see java.awt.event.KeyEvent
   */
  public void releaseKey(Component c, int keyCode) {
    focus(c);
    robot.releaseKey(keyCode);
  }

  /**
   * Performs a drag action at the given point.
   * @param c the target component.
   * @param where the point where to start the drag action.
   */
  protected final void drag(Component c, Point where) {
    dragAndDrop.drag(c, where);
  }

  /**
   * Ends a drag operation, releasing the mouse button over the given target location.
   * <p>
   * This method is tuned for native drag/drop operations, so if you get odd behavior, you might try using a simple
   * <code>{@link RobotFixture#mouseMove(Component, int, int)}</code> and
   * <code>{@link RobotFixture#releaseMouseButtons()}</code>.
   * @param c the target component.
   * @param where the point where the drag operation ends.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  protected final void drop(Component c, Point where) {
    dragAndDrop.drop(c, where);
  }

  /**
   * Move the mouse appropriately to get from the source to the destination. Enter/exit events will be generated where
   * appropriate.
   * @param c the target component.
   * @param where the point to drag over.
   */
  protected final void dragOver(Component c, Point where) {
    dragAndDrop.dragOver(c, where);
  }

  /**
   * Indicates whether it is possible for the user to resize the given component.
   * @param c the target component.
   * @return <code>true</code> if it is possible for the user to resize the given component, <code>false</code>
   * otherwise.
   */
  protected final boolean isUserResizable(Component c) {
    if (c instanceof Dialog) return ((Dialog)c).isResizable();
    if (c instanceof Frame) return ((Frame)c).isResizable();
    return canResizeWindows(); // most X11 window managers allow arbitrary resizing
  }

  /**
   * Indicates whether it is possible for the user to move the given component.
   * @param c the target component.
   * @return <code>true</code> if it is possible for the user to move the given component, <code>false</code>
   * otherwise.
   */
  protected final boolean isUserMovable(Component c) {
    return c instanceof Dialog || c instanceof Frame || canMoveWindows();
  }

  /**
   * Performs the <code>{@link AccessibleAction}</code> in the given <code>{@link Component}</code>'s event queue.
   * @param c the given <code>Component</code>.
   * @throws ActionFailedException if <code>action</code> is <code>null</code> or empty. 
   */
  protected final void performAccessibleActionOf(Component c) {
    robot.invokeLater(c, new PerformDefaultAccessibleActionTask(c));
  }

  /**
   * Wait the given number of milliseconds for the <code>{@link Component}</code> to be showing and ready. Returns
   * <code>false</code> if the operation times out.
   * @param c the given <code>Component</code>.
   * @param timeout the time in milliseconds to wait for the <code>Component</code> to be showing and ready.
   * @return <code>true</code> if the <code>Component</code> is showing and ready, <code>false</code> otherwise.
   */
  protected final boolean waitForShowing(Component c, long timeout) {
    if (robot.isReadyForInput(c)) return true;
    TimeoutWatch watch = startWatchWithTimeoutOf(timeout);
    while (!robot.isReadyForInput(c)) {
      if (c instanceof JPopupMenu) {
        // move the mouse over the parent menu item to ensure the sub-menu shows
        Component invoker = ((JPopupMenu)c).getInvoker();
        if (invoker instanceof JMenu) robot.jitter(invoker);
      }
      if (watch.isTimeOut()) return false;
      pause();
    }
    return true;
  }

  protected final String propertyName(Component c, String propertyName) {
    return concat(format(c), " - property:", quote(propertyName));
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
