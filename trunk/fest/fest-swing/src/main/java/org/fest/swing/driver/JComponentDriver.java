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

import static java.awt.event.KeyEvent.VK_UNDEFINED;
import static org.fest.swing.driver.Actions.findActionKey;
import static org.fest.swing.driver.KeyStrokes.findKeyStrokesForAction;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.util.Strings.*;

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.ActionFailedException;

/**
 * Understands simulation of user input on a <code>{@link JComponent}</code>. This class is intended for internal use
 * only.
 *
 * <p>
 * Adapted from <code>abbot.tester.JComponentTester</code> from <a href="http://abbot.sourceforge.net"
 * target="_blank">Abbot</a>.
 * </p>
 *
 * @author Alex Ruiz
 */
public abstract class JComponentDriver extends ComponentDriver {

  /**
   * Creates a new </code>{@link JComponentDriver}</code>.
   * @param robot the robot the robot to use to simulate user input.
   */
  public JComponentDriver(RobotFixture robot) {
    super(robot);
  }

  /**
   * Invoke <code>{@link JComponent#scrollRectToVisible(Rectangle)}</code> on the given
   * <code>{@link JComponent}</code> on the event dispatch thread.
   * @param c the given <code>JComponent</code>.
   * @param r the visible <code>Rectangle</code>.
   */
  protected final void scrollToVisible(final JComponent c, final Rectangle r) {
    // From Abbot:
    // Ideally, we'd use scrollBar commands to effect the scrolling, but that gets really complicated for no real gain
    // in function. Fortunately, Swing's Scrollable makes for a simple solution.
    // NOTE: absolutely MUST wait for idle in order for the scroll to finish, and the UI to update so that the next
    // action goes to the proper location within the scrolled component.
    robot.invokeAndWait(new Runnable() {
      public void run() {
        c.scrollRectToVisible(r);
      }
    });
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
    return visibleRectangleOf(c).contains(r);
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
    return visibleRectangleOf(c).contains(p);
  }

  private Rectangle visibleRectangleOf(JComponent c) {
    return c.getVisibleRect();
  }

  /**
   * Invoke an <code>{@link javax.swing.Action}</code> from the <code>{@link JComponent}</code>'s
   * <code>{@link javax.swing.ActionMap}</code>.
   * @param c the given <code>JComponent</code>.
   * @param name the name of the <code>Action</code> to invoke.
   * @throws ActionFailedException if an <code>Action</code> cannot be found under the given name.
   * @throws ActionFailedException if a <code>KeyStroke</code> cannot be found for the <code>Action</code> under the
   *          given name.
   * @throws ActionFailedException if it is not possible to type any of the found <code>KeyStroke</code>s.
   */
  public void invokeAction(JComponent c, String name) {
    robot.focusAndWaitForFocusGain(c);
    // From Abbot: On OSX/1.3.1, some action map keys are actions instead of strings.
    // On XP/1.4.1, all action map keys are strings.
    // If we can't look it up with the string key we saved, check all the actions for a corresponding name.
    Object key = findActionKey(name, c.getActionMap());
    KeyStroke[] keyStrokes = findKeyStrokesForAction(name, key, c.getInputMap());
    for (KeyStroke keyStroke : keyStrokes) {
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
