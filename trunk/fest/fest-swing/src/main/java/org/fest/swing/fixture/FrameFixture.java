/*
 * Created on Feb 8, 2007
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
import java.awt.Frame;
import java.awt.Point;

import abbot.tester.FrameTester;
import abbot.util.Bugs;
import static java.awt.Frame.*;

import static org.fest.swing.core.Pause.pause;

import org.fest.swing.core.Condition;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.core.Timeout;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;

/**
 * Understands simulation of user events on a <code>{@link Frame}</code> and verification of the state of such
 * <code>{@link Frame}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FrameFixture extends WindowFixture<Frame> implements FrameLikeFixture {

  /**
   * Creates a new <code>{@link FrameFixture}</code>. This constructor creates a new <code>{@link RobotFixture}</code>
   * containing the current AWT hierarchy.
   * @param name the name of the <code>Frame</code> to find.
   * @throws ComponentLookupException if a <code>Frame</code> having a matching name could not be found.
   * @throws ComponentLookupException if more than one <code>Frame</code> having a matching name is found.
   */
  public FrameFixture(String name) {
    super(name, Frame.class);
  }

  /**
   * Creates a new <code>{@link FrameFixture}</code>.
   * @param robot performs user events on the given window and verifies expected output.
   * @param name the name of the <code>Frame</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a <code>Frame</code> having a matching name could not be found.
   * @throws ComponentLookupException if more than one <code>Frame</code> having a matching name is found.
   */
  public FrameFixture(RobotFixture robot, String name) {
    super(robot, name, Frame.class);
  }

  /**
   * Creates a new <code>{@link FrameFixture}</code>. This constructor creates a new <code>{@link RobotFixture}</code>
   * containing the current AWT hierarchy.
   * @param target the <code>Frame</code> to be managed by this fixture.
   * @see RobotFixture#robotWithCurrentAwtHierarchy()
   */
  public FrameFixture(Frame target) {
    super(target);
  }

  /**
   * Creates a new <code>{@link FrameFixture}</code>.
   * @param robot performs user events on the given window and verifies expected output.
   * @param target the <code>Frame</code> to be managed by this fixture.
   */
  public FrameFixture(RobotFixture robot, Frame target) {
    super(robot, target);
  }

  /**
   * Shows this fixture's <code>{@link Frame}</code>.
   * @return this fixture.
   */
  public final FrameFixture show() {
    return (FrameFixture)doShow();
  }

  /**
   * Shows this fixture's <code>{@link Frame}</code>, resized to the given size.
   * @param size the size to resize this fixture's <code>Frame</code> to.
   * @return this fixture.
   */
  public final FrameFixture show(Dimension size) {
    return (FrameFixture)doShow(size);
  }

  /**
   * Simulates a user moving this fixture's <code>{@link Frame}</code> to the given point.
   * @param p the point to move this fixture's <code>Frame</code> to.
   * @return this fixture.
   */
  public final FrameFixture moveTo(Point p) {
    windowTester().actionMove(target, p.x, p.y);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link Frame}</code>.
   * @return this fixture.
   */
  public final FrameFixture click() {
    return (FrameFixture)doClick();
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link Frame}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public final FrameFixture click(MouseButton button) {
    return (FrameFixture)doClick(button);
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link Frame}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final FrameFixture click(MouseClickInfo mouseClickInfo) {
    return (FrameFixture)doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link Frame}</code>.
   * @return this fixture.
   */
  public final FrameFixture rightClick() {
    return (FrameFixture)doRightClick();
  }

  /**
   * Simulates a user doble-clicking this fixture's <code>{@link Frame}</code>.
   * @return this fixture.
   */
  public final FrameFixture doubleClick() {
    return (FrameFixture)doDoubleClick();
  }

  /**
   * Gives input focus to this fixture's <code>{@link Frame}</code>.
   * @return this fixture.
   */
  public FrameFixture focus() {
    return (FrameFixture)doFocus();
  }

  /**
   * Simulates a user iconifying this fixture's <code>{@link Frame}</code>.
   * @return this fixture.
   */
  public final FrameFixture iconify() {
    frameTester().iconify(target);
    pause(new Condition("frame being iconified") {
      public boolean test() {
        return target.getExtendedState() == ICONIFIED;
      }
    });
    return this;
  }

  /**
   * Simulates a user deiconifying this fixture's <code>{@link Frame}</code>.
   * @return this fixture.
   */
  public final FrameFixture deiconify() {
    frameTester().deiconify(target);
    pause(new Condition("frame being deiconified") {
      public boolean test() {
        return target.getExtendedState() != ICONIFIED;
      }
    });
    return this;
  }

  /**
   * Simulates a user maximizing this fixture's <code>{@link Frame}</code>.
   * @return this fixture.
   */
  public final FrameFixture maximize() {
    frameTester().maximize(target);
    pause(new Condition("frame being maximized") {
      public boolean test() {
        return (target.getExtendedState() & MAXIMIZED_BOTH) == MAXIMIZED_BOTH;
      }
    });
    return this;
  }

  /**
   * Simulates a user normalizing this fixture's <code>{@link Frame}</code>.
   * @return this fixture.
   */
  public final FrameFixture normalize() {
    robot.invokeLater(target, new Runnable() {
      public void run() {
        target.setExtendedState(NORMAL);
        if (Bugs.hasFrameDeiconifyBug()) target.setVisible(true);
      }
    });
    pause(new Condition("frame being normalized") {
      public boolean test() {
        return target.getExtendedState() == NORMAL;
      }
    });
    return this;
  }

  private FrameTester frameTester() {
    return (FrameTester)tester();
  }

  /**
   * Simulates a user resizing horizontally this fixture's <code>{@link Frame}</code>.
   * @param width the width that this fixture's <code>Frame</code> should have after being resized.
   * @return this fixture.
   */
  public final FrameFixture resizeWidthTo(int width) {
    return (FrameFixture)doResizeWidthTo(width);
  }

  /**
   * Simulates a user resizing vertically this fixture's <code>{@link Frame}</code>.
   * @param height the height that this fixture's <code>Frame</code> should have after being resized.
   * @return this fixture.
   */
  public final FrameFixture resizeHeightTo(int height) {
    return (FrameFixture)doResizeHeightTo(height);
  }

  /**
   * Simulates a user resizing this fixture's <code>{@link Frame}</code>.
   * @param size the size that the target window should have after being resized.
   * @return this fixture.
   */
  public final FrameFixture resizeTo(Dimension size) {
    return (FrameFixture)doResizeTo(size);
  }

  /**
   * Asserts that the size of this fixture's <code>{@link Frame}</code> is equal to given one.
   * @param size the given size to match.
   * @return this fixture.
   * @throws AssertionError if the size of this fixture's <code>Frame</code> is not equal to the given size.
   */
  public final FrameFixture requireSize(Dimension size) {
    return (FrameFixture)assertEqualSize(size);
  }

  /**
   * Simulates a user pressing and releasing the given keys on this fixture's <code>{@link Frame}</code>.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final FrameFixture pressAndReleaseKeys(int... keyCodes) {
    return (FrameFixture)doPressAndReleaseKeys(keyCodes);
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link Frame}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final FrameFixture pressKey(int keyCode) {
    return (FrameFixture)doPressKey(keyCode);
  }

  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link Frame}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final FrameFixture releaseKey(int keyCode) {
    return (FrameFixture)doReleaseKey(keyCode);
  }

  /**
   * Asserts that this fixture's <code>{@link Frame}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>Frame</code> is not visible.
   */
  public final FrameFixture requireVisible() {
    return (FrameFixture)assertVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link Frame}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>Frame</code> is visible.
   */
  public final FrameFixture requireNotVisible() {
    return (FrameFixture)assertNotVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link Frame}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>Frame</code> is disabled.
   */
  public final FrameFixture requireEnabled() {
    return (FrameFixture)assertEnabled();
  }


  /**
   * Asserts that this fixture's <code>{@link Frame}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws WaitTimedOutError if this fixture's <code>Frame</code> is never enabled.
   */
  public final FrameFixture requireEnabled(Timeout timeout) {
    return (FrameFixture)assertEnabled(timeout);
  }

  /**
   * Asserts that this fixture's <code>{@link Frame}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>Frame</code> is enabled.
   */
  public final FrameFixture requireDisabled() {
    return (FrameFixture)assertDisabled();
  }
}
