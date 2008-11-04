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

import org.fest.assertions.Description;
import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.*;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiLazyLoadingDescription;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.format.ComponentFormatter;
import org.fest.swing.format.Formatting;
import org.fest.swing.timing.Timeout;
import org.fest.swing.util.TimeoutWatch;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.MouseButton.*;
import static org.fest.swing.driver.ComponentEnabledCondition.untilIsEnabled;
import static org.fest.swing.driver.ComponentPerformDefaultAccessibleActionTask.performDefaultAccessibleAction;
import static org.fest.swing.driver.ComponentStateValidator.inEdtValidateIsEnabled;
import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.query.ComponentEnabledQuery.isEnabled;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.swing.query.ComponentVisibleQuery.isVisible;
import static org.fest.swing.timing.Pause.pause;
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
   * @throws ActionFailedException if the <code>Component</code> is disabled.
   */
  @RunsInEDT
  public void click(Component c) {
    robot.click(c);
  }

  /**
   * Simulates a user clicking once the given <code>{@link Component}</code> using the given mouse button.
   * @param c the <code>Component</code> to click on.
   * @param button the mouse button to use.
   * @throws NullPointerException if the given <code>MouseButton</code> is <code>null</code>.
   * @throws ActionFailedException if the <code>Component</code> is disabled.
   */
  @RunsInEDT
  public void click(Component c, MouseButton button) {
    click(c, button, 1);
  }

  /**
   * Simulates a user clicking the given mouse button, the given times on the given <code>{@link Component}</code>.
   * @param c the <code>Component</code> to click on.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @throws NullPointerException if the given <code>MouseClickInfo</code> is <code>null</code>.
   * @throws ActionFailedException if the <code>Component</code> is disabled.
   */
  @RunsInEDT
  public void click(Component c, MouseClickInfo mouseClickInfo) {
    if (mouseClickInfo == null) throw new NullPointerException("The given MouseClickInfo should not be null");
    click(c, mouseClickInfo.button(), mouseClickInfo.times());
  }

  /**
   * Simulates a user double-clicking the given <code>{@link Component}</code>.
   * @param c the <code>Component</code> to click on.
   * @throws ActionFailedException if the <code>Component</code> is disabled.
   */
  @RunsInEDT
  public void doubleClick(Component c) {
    click(c, LEFT_BUTTON, 2);
  }

  /**
   * Simulates a user right-clicking the given <code>{@link Component}</code>.
   * @param c the <code>Component</code> to click on.
   * @throws ActionFailedException if the <code>Component</code> is disabled.
   */
  @RunsInEDT
  public void rightClick(Component c) {
    click(c, RIGHT_BUTTON);
  }

  /**
   * Simulates a user clicking the given mouse button, the given times on the given <code>{@link Component}</code>.
   * @param c the <code>Component</code> to click on.
   * @param button the mouse button to click.
   * @param times the number of times to click the given mouse button.
   * @throws NullPointerException if the given <code>MouseButton</code> is <code>null</code>.
   * @throws ActionFailedException if the <code>Component</code> is disabled.
   */
  @RunsInEDT
  public void click(Component c, MouseButton button, int times) {
    if (button == null) throw new NullPointerException("The given MouseButton should not be null");
    robot.click(c, button, times);
  }

  /**
   * Simulates a user clicking at the given position on the given <code>{@link Component}</code>.
   * @param c the <code>Component</code> to click on.
   * @param where the position where to click.
   * @throws ActionFailedException if the <code>Component</code> is disabled.
   */
  @RunsInEDT
  public void click(Component c, Point where) {
    inEdtValidateIsEnabled(c);
    robot.click(c, where);
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
  @RunsInEDT
  public void requireSize(Component c, Dimension size) {
    assertThat(sizeOf(c)).as(propertyName(c, SIZE_PROPERTY)).isEqualTo(size);
  }

  /**
   * Asserts that the <code>{@link Component}</code> is visible.
   * @param c the target component.
   * @throws AssertionError if the <code>Component</code> is not visible.
   */
  @RunsInEDT
  public void requireVisible(Component c) {
    assertThat(isVisible(c)).as(visibleProperty(c)).isTrue();
  }

  /**
   * Asserts that the <code>{@link Component}</code> is not visible.
   * @param c the target component.
   * @throws AssertionError if the <code>Component</code> is visible.
   */
  @RunsInEDT
  public void requireNotVisible(Component c) {
    assertThat(isVisible(c)).as(visibleProperty(c)).isFalse();
  }

  @RunsInEDT
  private static Description visibleProperty(Component c) {
    return propertyName(c, VISIBLE_PROPERTY);
  }

  /**
   * Asserts that the <code>{@link Component}</code> is enabled.
   * @param c the target component.
   * @throws AssertionError if the <code>Component</code> is disabled.
   */
  @RunsInEDT
  public void requireEnabled(Component c) {
    assertThat(isEnabled(c)).as(enabledProperty(c)).isTrue();
  }

  /**
   * Asserts that the <code>{@link Component}</code> is enabled.
   * @param c the target component.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @throws WaitTimedOutError if the <code>Component</code> is never enabled.
   */
  @RunsInEDT
  public void requireEnabled(Component c, Timeout timeout) {
    pause(untilIsEnabled(c), timeout);
  }

  /**
   * Asserts that the <code>{@link Component}</code> is disabled.
   * @param c the target component.
   * @throws AssertionError if the <code>Component</code> is enabled.
   */
  @RunsInEDT
  public void requireDisabled(Component c) {
    assertThat(isEnabled(c)).as(enabledProperty(c)).isFalse();
  }

  @RunsInEDT
  private static Description enabledProperty(Component c) {
    return propertyName(c, ENABLED_PROPERTY);
  }

  /**
   * Simulates a user pressing and releasing the given keys on the <code>{@link Component}</code>.
   * @param c the target component.
   * @param keyCodes one or more codes of the keys to press.
   * @throws NullPointerException if the given array of codes is <code>null</code>.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   * @throws ActionFailedException if the <code>Component</code> is disabled.
   * @see java.awt.event.KeyEvent
   */
  @RunsInEDT
  public void pressAndReleaseKeys(Component c, int... keyCodes) {
    if (keyCodes == null) throw new NullPointerException("The array of key codes should not be null");
    focusAndWaitForFocusGain(c);
    robot.pressAndReleaseKeys(keyCodes);
  }

  /**
   * Simulates a user pressing and releasing the given key on the <code>{@link Component}</code>. Modifiers is a
   * mask from the available <code>{@link java.awt.event.InputEvent}</code> masks.
   * @param c the target component.
   * @param keyPressInfo specifies the key and modifiers to press.
   * @throws NullPointerException if the given <code>KeyPressInfo</code> is <code>null</code>.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   * @throws ActionFailedException if the <code>Component</code> is disabled.
   * @see java.awt.event.KeyEvent
   * @see java.awt.event.InputEvent
   */
  @RunsInEDT
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
   * @throws ActionFailedException if the <code>Component</code> is disabled.
   * @see java.awt.event.KeyEvent
   * @see java.awt.event.InputEvent
   */
  @RunsInEDT
  public void pressAndReleaseKey(Component c, int keyCode, int[] modifiers) {
    focusAndWaitForFocusGain(c);
    robot.pressAndReleaseKey(keyCode, modifiers);
  }

  /**
   * Simulates a user pressing given key on the <code>{@link Component}</code>.
   * @param c the target component.
   * @param keyCode the code of the key to press.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   * @throws ActionFailedException if the <code>Component</code> is disabled.
   * @see java.awt.event.KeyEvent
   */
  @RunsInEDT
  public void pressKey(Component c, int keyCode) {
    focusAndWaitForFocusGain(c);
    robot.pressKey(keyCode);
  }

  /**
   * Simulates a user releasing the given key on the <code>{@link Component}</code>.
   * @param c the target component.
   * @param keyCode the code of the key to release.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   * @throws ActionFailedException if the <code>Component</code> is disabled.
   * @see java.awt.event.KeyEvent
   */
  @RunsInEDT
  public void releaseKey(Component c, int keyCode) {
    focusAndWaitForFocusGain(c);
    robot.releaseKey(keyCode);
  }

  /**
   * Gives input focus to the given <code>{@link Component}</code> and waits until the <code>{@link Component}</code>
   * has focus.
   * @param c the component to give focus to.
   * @throws ActionFailedException if the <code>Component</code> is disabled.
   */
  @RunsInEDT
  public void focusAndWaitForFocusGain(Component c) {
    inEdtValidateIsEnabled(c);
    robot.focusAndWaitForFocusGain(c);
  }

  /**
   * Gives input focus to the given <code>{@link Component}</code>. Note that the component may not yet have focus when
   * this method returns.
   * @param c the component to give focus to.
   */
  @RunsInEDT
  public void focus(Component c) {
    inEdtValidateIsEnabled(c);
    robot.focus(c);
  }

  /**
   * Performs a drag action at the given point.
   * @param c the target component.
   * @param where the point where to start the drag action.
   */
  @RunsInEDT
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
  @RunsInEDT
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
   * Indicates whether it is possible for the user to resize the given component. <b>Note:</b> This method is <b>not</b>
   * executed in the event dispatch thread.
   * @param c the target component.
   * @return <code>true</code> if it is possible for the user to resize the given component, <code>false</code>
   * otherwise.
   */
  @RunsInCurrentThread
  protected final boolean isUserResizable(Component c) {
    if (c instanceof  Frame) return  ((Frame)c).isResizable();
    if (c instanceof Dialog) return ((Dialog)c).isResizable();
    return false;
  }

  /**
   * Indicates whether it is possible for the user to move the given component.
   * @param c the target component.
   * @return <code>true</code> if it is possible for the user to move the given component, <code>false</code>
   * otherwise.
   */
  protected final boolean isUserMovable(Component c) {
    return c instanceof Dialog || c instanceof Frame;
  }

  /**
   * Performs the <code>{@link AccessibleAction}</code> in the given <code>{@link Component}</code>'s event queue.
   * @param c the given <code>Component</code>.
   * @throws ActionFailedException if <code>action</code> is <code>null</code> or empty.
   */
  @RunsInCurrentThread
  protected final void performAccessibleActionOf(Component c) {
    performDefaultAccessibleAction(c);
    robot.waitForIdle();
  }

  /**
   * Wait the given number of milliseconds for the <code>{@link Component}</code> to be showing and ready. Returns
   * <code>false</code> if the operation times out.
   * @param c the given <code>Component</code>.
   * @param timeout the time in milliseconds to wait for the <code>Component</code> to be showing and ready.
   * @return <code>true</code> if the <code>Component</code> is showing and ready, <code>false</code> otherwise.
   */
  @RunsInCurrentThread
  protected final boolean waitForShowing(Component c, long timeout) {
    // TODO test
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

  /**
   * Formats the name of a property of the given <code>{@link Component}</code> by concatenating the value obtained
   * from <code>{@link Formatting#format(Component)}</code> with the given property name.
   * @param c the given <code>Component</code>.
   * @param propertyName the name of the property.
   * @return the description of a property belonging to a <code>Component</code>.
   * @see ComponentFormatter
   * @see Formatting#format(Component)
   */
  @RunsInEDT
  public static Description propertyName(final Component c, final String propertyName) {
    return new GuiLazyLoadingDescription() {
      protected String loadDescription() {
        return concat(format(c), " - property:", quote(propertyName));
      }
    };
  }
}
