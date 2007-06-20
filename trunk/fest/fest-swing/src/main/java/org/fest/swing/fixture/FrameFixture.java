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

import abbot.tester.FrameTester;
import abbot.util.Bugs;
import static java.awt.Frame.ICONIFIED;
import static java.awt.Frame.MAXIMIZED_BOTH;
import static java.awt.Frame.NORMAL;

import org.fest.swing.Condition;
import org.fest.swing.RobotFixture;

/**
 * Understands simulation of user events on a <code>{@link Frame}</code> and verification of the state of such
 * <code>{@link Frame}</code>.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FrameFixture extends WindowFixture<Frame> {

  /**
   * Creates a new </code>{@link FrameFixture}</code>. This constructor creates a new <code>{@link RobotFixture}</code>
   * containing the current AWT hierarchy.
   * @param target the <code>Frame</code> to be managed by this fixture.
   * @see RobotFixture#robotWithCurrentAwtHierarchy()
   */
  public FrameFixture(Frame target) {
    super(target);
  }

  /**
   * Creates a new </code>{@link FrameFixture}</code>.
   * @param robot performs user events on the given window and verifies expected output.
   * @param target the <code>Frame</code> to be managed by this fixture.
   */
  public FrameFixture(RobotFixture robot, Frame target) {
    super(robot, target);
  }
  
  /**
   * Shows the <code>{@link Frame}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final FrameFixture show() {
    return (FrameFixture)super.show();
  }
  
  /**
   * Shows the <code>{@link Frame}</code> managed by this fixture, resized to the given size.
   * @param size the size to resize the managed <code>Frame</code> to.
   * @return this fixture.
   */
  @Override public final FrameFixture show(Dimension size) {
    return (FrameFixture)super.show(size);
  }

  /**
   * Simulates a user clicking the <code>{@link Frame}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public FrameFixture click() {
    return (FrameFixture)super.click();
  }

  /**
   * Gives input focus to the <code>{@link Frame}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public FrameFixture focus() {
    return (FrameFixture)super.focus();
  }

  /**
   * Simulates a user iconifying the <code>{@link Frame}</code> managed by this fixture.
   * @return this fixture.
   */
  public final FrameFixture iconify() {
    frameTester().iconify(target);
    robot.wait(new Condition("frame being iconified") {
      public boolean test() {
        return target.getExtendedState() == ICONIFIED;
      }
    });
    return this;
  }

  /**
   * Simulates a user deiconifying the <code>{@link Frame}</code> managed by this fixture.
   * @return this fixture.
   */
  public final FrameFixture deiconify() {
    frameTester().deiconify(target);
    robot.wait(new Condition("frame being deiconified") {
      public boolean test() {
        return target.getExtendedState() != ICONIFIED;
      }
    });
    return this;
  }

  /**
   * Simulates a user maximizing the <code>{@link Frame}</code> managed by this fixture.
   * @return this fixture.
   */
  public final FrameFixture maximize() {
    frameTester().maximize(target);
    robot.wait(new Condition("frame being maximized") {
      public boolean test() {
        return (target.getExtendedState() & MAXIMIZED_BOTH) == MAXIMIZED_BOTH;
      }
    });
    return this;
  }

  /**
   * Simulates a user normalizing the <code>{@link Frame}</code> managed by this fixture.
   * @return this fixture.
   */
  public final FrameFixture normalize() {
    robot.invokeLater(target, new Runnable() {
      public void run() {
        target.setExtendedState(NORMAL);
        if (Bugs.hasFrameDeiconifyBug()) target.setVisible(true);
      }
    });
    robot.wait(new Condition("frame being normalized") {
      public boolean test() {
        return target.getExtendedState() == NORMAL;
      }
    });
    return this;
  }

  private FrameTester frameTester() {
    return testerCastedTo(FrameTester.class);
  }

  /**
   * Simulates a user resizing horizontally the <code>{@link Frame}</code> managed by this fixture.
   * @param width the width that the managed <code>Frame</code> should have after being resized.
   * @return this fixture.
   */
  @Override public final FrameFixture resizeWidthTo(int width) {
    return (FrameFixture)super.resizeWidthTo(width);
  }

  /**
   * Simulates a user resizing vertically the <code>{@link Frame}</code> managed by this fixture.
   * @param height the height that the managed <code>Frame</code> should have after being resized.
   * @return this fixture.
   */
  @Override public final FrameFixture resizeHeightTo(int height) {
    return (FrameFixture)super.resizeHeightTo(height);
  }

  /**
   * Simulates a user resizing the <code>{@link Frame}</code> managed by this fixture.
   * @param size the size that the target window should have after being resized.
   * @return this fixture.
   */
  @Override public final FrameFixture resizeTo(Dimension size) {
    return (FrameFixture)super.resizeTo(size);
  }

  /**
   * Asserts that the size of the <code>{@link Frame}</code> managed by this fixture is equal to given one. 
   * @param size the given size to match.
   * @return this fixture.
   * @throws AssertionError if the size of the managed <code>Frame</code> is not equal to the given size. 
   */
  @Override public final FrameFixture requireSize(Dimension size) {
    return (FrameFixture)super.requireSize(size);
  }

  /**
   * Asserts that the <code>{@link Frame}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>Frame</code> is not visible.
   */
  @Override public final FrameFixture requireVisible() {
    return (FrameFixture)super.requireVisible();
  }
  
  /**
   * Asserts that the <code>{@link Frame}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>Frame</code> is visible.
   */
  @Override public final FrameFixture requireNotVisible() {
    return (FrameFixture)super.requireNotVisible();
  }

  /**
   * Asserts that the <code>{@link Frame}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>Frame</code> is disabled.
   */
  @Override public final FrameFixture requireEnabled() {
    return (FrameFixture)super.requireEnabled();
  }
  
  /**
   * Asserts that the <code>{@link Frame}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>Frame</code> is enabled.
   */
  @Override public final FrameFixture requireDisabled() {
    return (FrameFixture)super.requireDisabled();
  }
}
