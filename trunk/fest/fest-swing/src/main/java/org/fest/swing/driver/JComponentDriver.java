/*
 * Created on Jan 26, 2008
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

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.UnexpectedException;
import org.fest.swing.query.JComponentVisibleRectQuery;

import static java.awt.event.KeyEvent.VK_UNDEFINED;

import static org.fest.swing.awt.AWT.centerOfVisibleRect;
import static org.fest.swing.driver.ComponentStateValidator.validateIsEnabled;
import static org.fest.swing.driver.JComponentKeyStrokesForActionQuery.keyStrokesForAction;
import static org.fest.swing.edt.GuiActionExecutionType.RUN_IN_CURRENT_THREAD;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.util.Strings.*;

/**
 * Understands simulation of user input on a <code>{@link JComponent}</code>. This class is intended for internal use
 * only.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JComponentDriver extends ContainerDriver {

  /**
   * Creates a new </code>{@link JComponentDriver}</code>.
   * @param robot the robot the robot to use to simulate user input.
   */
  public JComponentDriver(Robot robot) {
    super(robot);
  }

  /**
   * Simulates a user clicking once the given <code>{@link JComponent}</code> using the left mouse button.
   * @param c the <code>JComponent</code> to click on.
   * @throws ActionFailedException if the <code>JComponent</code> is disabled.
   */
  public void click(final JComponent c) {
    Point where = null;
    try {
      where = execute(new GuiQuery<Point>() {
        protected Point executeInEDT() {
          validateIsEnabled(c);
          return centerOfVisibleRect(c, RUN_IN_CURRENT_THREAD);
        }
      });
    } catch (UnexpectedException unexpected) {
      throw unexpected.bomb();
    }
    super.click(c, where);
  }

  /**
   * Invoke <code>{@link JComponent#scrollRectToVisible(Rectangle)}</code> on the given <code>{@link JComponent}</code>.
   * <b>Note:</b> this method is <b>not</b> executed in the event dispatch thread. Callers are responsible for calling
   * this method in the event dispatch thread.
   * @param c the given <code>JComponent</code>.
   * @param r the visible <code>Rectangle</code>.
   */
  protected final void scrollToVisible(JComponent c, Rectangle r) {
    // From Abbot:
    // Ideally, we'd use scrollBar commands to effect the scrolling, but that gets really complicated for no real gain
    // in function. Fortunately, Swing's Scrollable makes for a simple solution.
    // NOTE: absolutely MUST wait for idle in order for the scroll to finish, and the UI to update so that the next
    // action goes to the proper location within the scrolled component.
    c.scrollRectToVisible(r);
  }

  /**
   * Indicates whether the given <code>{@link JComponent}</code>'s visible <code>{@link Rectangle}</code> contains
   * the given one.
   * @param c the given <code>JComponent</code>.
   * @param r the <code>Rectangle</code> to verify.
   * @return <code>true</code> if the given <code>Rectangle</code> is contained in the given <code>JComponent</code>'s
   *         visible <code>Rectangle</code>.
   */
  protected final boolean isVisible(JComponent c, Rectangle r) {
    return JComponentVisibleRectQuery.visibleRectOf(c).contains(r);
  }

  /**
   * Indicates whether the given <code>{@link JComponent}</code>'s visible <code>{@link Rectangle}</code> contains
   * the given <code>{@link Point}</code>.
   * @param c the given <code>JComponent</code>.
   * @param p the <code>Point</code> to verify.
   * @return <code>true</code> if the given <code>Point</code> is contained in the given <code>JComponent</code>'s
   *         visible <code>Rectangle</code>.
   */
  protected final boolean isVisible(JComponent c, Point p) {
    return JComponentVisibleRectQuery.visibleRectOf(c).contains(p);
  }

  /**
   * Invoke an <code>{@link javax.swing.Action}</code> from the <code>{@link JComponent}</code>'s
   * <code>{@link javax.swing.ActionMap}</code>.
   * @param c the given <code>JComponent</code>.
   * @param name the name of the <code>Action</code> to invoke.
   * @throws ActionFailedException if an <code>Action</code> cannot be found under the given name.
   * @throws ActionFailedException if a <code>KeyStroke</code> cannot be found for the <code>Action</code> under the
   *         given name.
   * @throws ActionFailedException if it is not possible to type any of the found <code>KeyStroke</code>s.
   */
  public final void invokeAction(JComponent c, String name) {
    focusAndWaitForFocusGain(c);
    for (KeyStroke keyStroke : keyStrokesForAction(c, name)) {
      try {
        type(keyStroke);
        robot.waitForIdle();
        return;
      } catch (IllegalArgumentException e) { /* try the next one, if any */ }
    }
    throw actionFailure(concat("Unable to type any key for the action with key ", quote(name)));
  }

  private void type(KeyStroke keyStroke) {
    if (keyStroke.getKeyCode() == VK_UNDEFINED) {
      robot.type(keyStroke.getKeyChar());
      return;
    }
    robot.pressAndReleaseKey(keyStroke.getKeyCode(), keyStroke.getModifiers());
  }
}
