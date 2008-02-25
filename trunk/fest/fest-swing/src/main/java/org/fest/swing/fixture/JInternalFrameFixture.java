/*
 * Created on Dec 8, 2007
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

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JInternalFrame;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.core.Timeout;
import org.fest.swing.driver.JInternalFrameDriver;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;

/**
 * Understands simulation of user events on a <code>{@link JInternalFrame}</code> and verification of the state of such
 * <code>{@link JInternalFrame}</code>.
 *
 * @author Alex Ruiz
 */
public class JInternalFrameFixture extends ContainerFixture<JInternalFrame> implements FrameLikeFixture {

  private final JInternalFrameDriver driver;
  
  /**
   * Creates a new <code>{@link JInternalFrameFixture}</code>.
   * @param robot performs simulation of user events on a <code>JInternalFrame</code>.
   * @param internalFrameName the name of the <code>JInternalFrame</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JInternalFrame</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JInternalFrame</code> is found.
   */
  public JInternalFrameFixture(RobotFixture robot, String internalFrameName) {
    super(robot, internalFrameName, JInternalFrame.class);
    driver = newInternalFrameDriver();
  }

  /**
   * Creates a new <code>{@link JInternalFrameFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JInternalFrame</code>.
   * @param target the <code>JInternalFrame</code> to be managed by this fixture.
   */
  public JInternalFrameFixture(RobotFixture robot, JInternalFrame target) {
    super(robot, target);
    driver = newInternalFrameDriver();
  }

  private JInternalFrameDriver newInternalFrameDriver() {
    return new JInternalFrameDriver(robot);
  }
  
  /**
   * Brings this fixture's <code>{@link JInternalFrame}</code> to the front.
   * @return this fixture.
   */
  public final JInternalFrameFixture moveToFront() {
    driver.moveToFront(target);
    return this;
  }

  /**
   * Brings this fixture's <code>{@link JInternalFrame}</code> to the back.
   * @return this fixture.
   */
  public final JInternalFrameFixture moveToBack() {
    driver.moveToBack(target);
    return this;
  }

  /**
   * Simulates a user deiconifying this fixture's <code>{@link JInternalFrame}</code>.
   * @return this fixture.
   * @throws ActionFailedException if the <code>JInternalFrame</code> vetoes the action.
   */
  public final JInternalFrameFixture deiconify() {
    driver.deiconify(target);
    return this;
  }

  /**
   * Simulates a user iconifying this fixture's <code>{@link JInternalFrame}</code>.
   * @return this fixture.
   * @throws ActionFailedException if the given <code>JInternalFrame</code> is not iconifiable.
   * @throws ActionFailedException if the <code>JInternalFrame</code> vetoes the action.
   */
  public final JInternalFrameFixture iconify() {
    driver.iconify(target);
    return this;
  }

  /**
   * Simulates a user maximizing this fixture's <code>{@link JInternalFrame}</code>, deconifying it first if it is 
   * iconified.
   * @return this fixture.
   * @throws ActionFailedException if the given <code>JInternalFrame</code> is not maximizable.
   * @throws ActionFailedException if the <code>JInternalFrame</code> vetoes the action.
   */
  public final JInternalFrameFixture maximize() {
    driver.maximize(target);
    return this;
  }

  /**
   * Simulates a user normalizing this fixture's <code>{@link JInternalFrame}</code>, deconifying it first if it is 
   * iconified.
   * @return this fixture.
   * @throws ActionFailedException if the <code>JInternalFrame</code> vetoes the action.
   */
  public final JInternalFrameFixture normalize() {
    driver.normalize(target);
    return this;
  }

  /**
   * Simulates a user closing this fixture's <code>{@link JInternalFrame}</code>.
   * @throws ActionFailedException if the <code>JInternalFrame</code> is not closable.
   */
  public final void close() {
    driver.close(target);
  }

  /**
   * Asserts that the size of this fixture's <code>{@link JInternalFrame}</code> is equal to given one.
   * @param size the given size to match.
   * @return this fixture.
   * @throws AssertionError if the size of this fixture's <code>JInternalFrame</code> is not equal to the given size.
   */
  public final JInternalFrameFixture requireSize(Dimension size) {
    return (JInternalFrameFixture)assertEqualSize(size);
  }

  /**
   * Simulates a user resizing horizontally this fixture's <code>{@link JInternalFrame}</code>.
   * @param width the width that this fixture's <code>JInternalFrame</code> should have after being resized.
   * @return this fixture.
   */
  public final JInternalFrameFixture resizeWidthTo(int width) {
    return resizeTo(new Dimension(width, target.getHeight()));
  }

  /**
   * Simulates a user resizing vertically this fixture's <code>{@link JInternalFrame}</code>.
   * @param height the height that this fixture's <code>JInternalFrame</code> should have after being resized.
   * @return this fixture.
   */
  public final JInternalFrameFixture resizeHeightTo(int height) {
    return resizeTo(new Dimension(target.getWidth(), height));
  }

  /**
   * Simulates a user resizing this fixture's <code>{@link JInternalFrame}</code>.
   * @param size the size that the target <code>JInternalFrame</code> should have after being resized.
   * @return this fixture.
   */
  public final JInternalFrameFixture resizeTo(Dimension size) {
    driver.resize(target, size.width, size.height);
    return this;
  }

  /**
   * Simulates a user moving this fixture's <code>{@link JInternalFrame}</code> to the given point.
   * @param p the point to move this fixture's <code>JInternalFrame</code> to.
   * @return this fixture.
   */
  public final JInternalFrameFixture moveTo(Point p) {
    Point locationOnScreen = target.getLocationOnScreen();
    Point destination = new Point(p.x - locationOnScreen.x, p.y - locationOnScreen.y);
    driver.move(target, destination.x, destination.y);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JInternalFrame}</code>.
   * @return this fixture.
   */
  public final JInternalFrameFixture click() {
    return (JInternalFrameFixture)doClick();
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JInternalFrame}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public final JInternalFrameFixture click(MouseButton button) {
    return (JInternalFrameFixture)doClick(button);
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JInternalFrame}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JInternalFrameFixture click(MouseClickInfo mouseClickInfo) {
    return (JInternalFrameFixture)doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JInternalFrame}</code>.
   * @return this fixture.
   */
  public final JInternalFrameFixture rightClick() {
    return (JInternalFrameFixture)doRightClick();
  }

  /**
   * Simulates a user double-clicking this fixture's <code>{@link JInternalFrame}</code>.
   * @return this fixture.
   */
  public final JInternalFrameFixture doubleClick() {
    return (JInternalFrameFixture)doDoubleClick();
  }

  /**
   * Gives input focus to this fixture's <code>{@link JInternalFrame}</code>.
   * @return this fixture.
   */
  public final JInternalFrameFixture focus() {
    return (JInternalFrameFixture)doFocus();
  }

  /**
   * Simulates a user pressing and releasing the given keys on this fixture's <code>{@link JInternalFrame}</code> .
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JInternalFrameFixture pressAndReleaseKeys(int... keyCodes) {
    return (JInternalFrameFixture)doPressAndReleaseKeys(keyCodes);
  }

  /**
   * Simulates a user pressing given key on this fixture's <code>{@link JInternalFrame}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JInternalFrameFixture pressKey(int keyCode) {
    return (JInternalFrameFixture)doPressKey(keyCode);
  }

  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JInternalFrame}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JInternalFrameFixture releaseKey(int keyCode) {
    return (JInternalFrameFixture)doReleaseKey(keyCode);
  }

  /**
   * Asserts that this fixture's <code>{@link JInternalFrame}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JInternalFrame</code> is not visible.
   */
  public final JInternalFrameFixture requireVisible() {
    return (JInternalFrameFixture)assertVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JInternalFrame}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JInternalFrame</code> is visible.
   */
  public final JInternalFrameFixture requireNotVisible() {
    return (JInternalFrameFixture)assertNotVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JInternalFrame}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JInternalFrame</code> is disabled.
   */
  public final JInternalFrameFixture requireEnabled() {
    return (JInternalFrameFixture)assertEnabled();
  }

  /**
   * Asserts that this fixture's <code>{@link JInternalFrame}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws WaitTimedOutError if the managed <code>JInternalFrame</code> is never enabled.
   */
  public final JInternalFrameFixture requireEnabled(Timeout timeout) {
    return (JInternalFrameFixture)assertEnabled(timeout);
  }

  /**
   * Asserts that this fixture's <code>{@link JInternalFrame}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JInternalFrame</code> is enabled.
   */
  public final JInternalFrameFixture requireDisabled() {
    return (JInternalFrameFixture)assertDisabled();
  }
}
