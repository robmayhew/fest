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
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.format.ComponentFormatter;
import org.fest.swing.format.Formatting;
import org.fest.swing.util.TimeoutWatch;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.task.GetComponentSizeTask.sizeOf;
import static org.fest.swing.task.GetJPopupMenuInvokerTask.invokerOf;
import static org.fest.swing.task.IsComponentEnabledTask.isEnabled;
import static org.fest.swing.task.IsComponentVisibleTask.isVisible;
import static org.fest.swing.util.Platform.*;
import static org.fest.swing.util.TimeoutWatch.startWatchWithTimeoutOf;
import static org.fest.util.Strings.*;

/**
 * Understands simulation of user input on a <code>{@link Component}</code>. This class is intended for internal use
 * only.
 *
 * @author Alex Ruiz
 */
public class ComponentDriver {

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
    this(robot, new DragAndDrop(robot));
  }

  ComponentDriver(Robot robot, DragAndDrop dragAndDrop) {
    this.robot = robot;
    this.dragAndDrop = dragAndDrop;
  }

  /**
   * Simulates a user clicking once the given <code>{@link Component}</code> using the left mouse button.
   * @param c the <code>Component</code> to click on.
   */
  public void click(Component c) {
    robot.click(c);
  }

  /**
   * Simulates a user clicking once the given <code>{@link Component}</code> using the given mouse button.
   * @param c the <code>Component</code> to click on.
   * @param button the mouse button to use.
   */
  public void click(Component c, MouseButton button) {
    robot.click(c, button);
  }

  /**
   * Simulates a user clicking the given mouse button, the given times on the given <code>{@link Component}</code>.
   * @param c the <code>Component</code> to click on.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @throws NullPointerException if the given <code>MouseClickInfo</code> is <code>null</code>.
   */
  public void click(Component c, MouseClickInfo mouseClickInfo) {
    if (mouseClickInfo == null) throw new NullPointerException("The given MouseClickInfo should not be null");
    click(c, mouseClickInfo.button(), mouseClickInfo.times());
  }

  /**
   * Simulates a user double-clicking the given <code>{@link Component}</code>.
   * @param c the <code>Component</code> to click on.
   */
  public void doubleClick(Component c) {
    robot.doubleClick(c);
  }

  /**
   * Simulates a user right-clicking the given <code>{@link Component}</code>.
   * @param c the <code>Component</code> to click on.
   */
  public void rightClick(Component c) {
    robot.rightClick(c);
  }

  /**
   * Simulates a user clicking the given mouse button, the given times on the given <code>{@link Component}</code>.
   * @param c the <code>Component</code> to click on.
   * @param button the mouse button to click.
   * @param times the number of times to click the given mouse button.
   */
  public void click(Component c, MouseButton button, int times) {
    robot.click(c, button, times);
  }

  /**
   * Simulates a user clicking at the given position on the given <code>{@link Component}</code>.
   * @param target the <code>Component</code> to click on.
   * @param where the position where to click.
   */
  public void click(Component target, Point where) {
    robot.click(target, where);
  }

  /**
   * Gives input focus to the given <code>{@link Component}</code> and waits until the <code>{@link Component}</code>
   * has focus.
   * @param c the component to give focus to.
   */
  public void focusAndWaitForFocusGain(Component c) {
    robot.focusAndWaitForFocusGain(c);
  }

  protected Settings settings() {
    return robot.settings();
  }

  /**
   * Asserts that the size of the <code>{@link Component}</code> is equal to given one.
   * @param c the target component.
   * @param size the given size to match.
   * @throws AssertionError if the size of the <code>Window</code> is not equal to the given size.
   */
  public void requireSize(Component c, Dimension size) {
    assertThat(sizeOf(c)).as(propertyName(c, SIZE_PROPERTY)).isEqualTo(size);
  }

  /**
   * Asserts that the <code>{@link Component}</code> is visible.
   * @param c the target component.
   * @throws AssertionError if the <code>Component</code> is not visible.
   */
  public void requireVisible(Component c) {
    assertThat(isVisible(c)).as(visibleProperty(c)).isTrue();
  }

  /**
   * Asserts that the <code>{@link Component}</code> is not visible.
   * @param c the target component.
   * @throws AssertionError if the <code>Component</code> is visible.
   */
  public void requireNotVisible(Component c) {
    assertThat(isVisible(c)).as(visibleProperty(c)).isFalse();
  }

  private static String visibleProperty(Component c) {
    return propertyName(c, VISIBLE_PROPERTY);
  }

  /**
   * Asserts that the <code>{@link Component}</code> is enabled.
   * @param c the target component.
   * @throws AssertionError if the <code>Component</code> is disabled.
   */
  public void requireEnabled(Component c) {
    assertThat(isEnabled(c)).as(enabledProperty(c)).isTrue();
  }

  /**
   * Asserts that the <code>{@link Component}</code> is enabled.
   * @param c the target component.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @throws WaitTimedOutError if the <code>Component</code> is never enabled.
   */
  public void requireEnabled(Component c, Timeout timeout) {
    pause(new ComponentEnabledCondition(c), timeout);
  }

  /**
   * Asserts that the <code>{@link Component}</code> is disabled.
   * @param c the target component.
   * @throws AssertionError if the <code>Component</code> is enabled.
   */
  public void requireDisabled(Component c) {
    assertThat(isEnabled(c)).as(enabledProperty(c)).isFalse();
  }

  private static String enabledProperty(Component c) {
    return propertyName(c, ENABLED_PROPERTY);
  }

  /**
   * Simulates a user pressing and releasing the given keys on the <code>{@link Component}</code>.
   * @param c the target component.
   * @param keyCodes one or more codes of the keys to press.
   * @throws NullPointerException if the given array of codes is <code>null</code>.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   * @see java.awt.event.KeyEvent
   */
  public void pressAndReleaseKeys(Component c, int... keyCodes) {
    if (keyCodes == null) throw new NullPointerException("The array of key codes should not be null");
    focus(c);
    robot.pressAndReleaseKeys(keyCodes);
  }

  /**
   * Simulates a user pressing and releasing the given key on the <code>{@link Component}</code>. Modifiers is a
   * mask from the available <code>{@link java.awt.event.InputEvent}</code> masks.
   * @param c the target component.
   * @param keyPressInfo specifies the key and modifiers to press.
   * @throws NullPointerException if the given <code>KeyPressInfo</code> is <code>null</code>.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   * @see java.awt.event.KeyEvent
   * @see java.awt.event.InputEvent
   */
  public void pressAndReleaseKey(Component c, KeyPressInfo keyPressInfo) {
    if (keyPressInfo == null) throw new NullPointerException("The given KeyPressInfo should not be null");
    pressAndReleaseKey(c, keyPressInfo.keyCode(), keyPressInfo.modifiers());
  }

  /**
   * Simulates a user pressing and releasing the given key on the <code>{@link Component}</code>. Modifiers is a
   * mask from the available <code>{@link java.awt.event.InputEvent}</code> masks.
   * @param c the target component.
   * @param keyCode the code of the key to press.
   * @param modifiers the given modifiers.
   * @throws IllegalArgumentException if the given code is not a valid key code. *
   * @see java.awt.event.KeyEvent
   * @see java.awt.event.InputEvent
   */
  public void pressAndReleaseKey(Component c, int keyCode, int[] modifiers) {
    focus(c);
    robot.pressAndReleaseKey(keyCode, modifiers);
  }

  /**
   * Simulates a user pressing given key on the <code>{@link Component}</code>.
   * @param c the target component.
   * @param keyCode the code of the key to press.
   * @throws IllegalArgumentException if the given code is not a valid key code. *
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
   * @throws IllegalArgumentException if the given code is not a valid key code. *
   * @see java.awt.event.KeyEvent
   */
  public void releaseKey(Component c, int keyCode) {
    focus(c);
    robot.releaseKey(keyCode);
  }

  /**
   * Gives input focus to the given <code>{@link Component}</code>. Note that the component may not yet have focus when
   * this method returns.
   * @param c the component to give focus to.
   */
  public void focus(Component c) {
    robot.focus(c);
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
   * <code>{@link RobotFixture#moveMouse(Component, int, int)}</code> and
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
        Component invoker = invokerOf((JPopupMenu)c);
        if (invoker instanceof JMenu) robot.jitter(invoker);
      }
      if (watch.isTimeOut()) return false;
      pause();
    }
    return true;
  }

  /**
   * Formats the name of a property of the given <code>{@link Component}</code> by concatenating the value obtained
   * from <code>{@link Formatting#format(Component)}</code> with the given property name.
   * @param c the given <code>Component</code>.
   * @param propertyName the name of the property.
   * @return the description of a property belonging to a <code>Component</code>.
   * @see ComponentFormatter
   * @see Formatting#format(Component)
   */
  public static String propertyName(Component c, String propertyName) {
    return concat(format(c), " - property:", quote(propertyName));
  }
}
