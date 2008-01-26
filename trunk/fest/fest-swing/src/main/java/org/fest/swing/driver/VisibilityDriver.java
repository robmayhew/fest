/*
 * Created on Jan 21, 2008
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

import abbot.Log;

import org.fest.swing.core.RobotFixture;

/**
 * Understands visibility of a <code>{@link JComponent}</code>.
 * 
 * @author Alex Ruiz
 */
public final class VisibilityDriver {

  private final RobotFixture robot;

  /**
   * Creates a new </code>{@link VisibilityDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public VisibilityDriver(RobotFixture robot) {
    this.robot = robot;
  }

  /**
   * Invoke <code>{@link JComponent#scrollRectToVisible(Rectangle)}</code> on the given
   * <code>{@link JComponent}</code> on the event dispatch thread.
   * @param c the given <code>JComponent</code>.
   * @param r the visible <code>Rectangle</code>.
   */
  public void scrollToVisible(final JComponent c, final Rectangle r) {
    Log.debug("Scroll to visible: " + r);
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
  public boolean isVisible(JComponent c, Rectangle r) {
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
  public boolean isVisible(JComponent c, Point p) {
    return visibleRectangleOf(c).contains(p);
  }

  private Rectangle visibleRectangleOf(JComponent c) {
    return c.getVisibleRect();
  }

}
