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

import org.fest.swing.Condition;
import org.fest.swing.RobotFixture;


import abbot.tester.FrameTester;
import abbot.util.Bugs;
import static java.awt.Frame.ICONIFIED;
import static java.awt.Frame.MAXIMIZED_BOTH;
import static java.awt.Frame.NORMAL;


/**
 * Understands simulation of user events and state verification of a <code>{@link Frame}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FrameFixture extends AbstractWindowFixture<Frame> {

  /**
   * Creates a new </code>{@link FrameFixture}</code>. This constructor creates a new <code>{@link RobotFixture}</code>
   * containing the current AWT hierarchy.
   * @param target the window to test.
   */
  public FrameFixture(Frame target) {
    super(target);
  }

  /**
   * Creates a new </code>{@link FrameFixture}</code>.
   * @param robot performs user events on the given window and verifies expected output.
   * @param target the window to test.
   */
  public FrameFixture(RobotFixture robot, Frame target) {
    super(robot, target);
  }
  
  /** {@inheritDoc} */
  public final FrameFixture show() {
    doShow();
    return this;
  }
  
  /** {@inheritDoc} */
  public final FrameFixture show(Dimension size) {
    doShow(size);
    return this;
  }

  /** {@inheritDoc} */
  public FrameFixture click() {
    doClick();
    return this;
  }

  /** {@inheritDoc} */
  public FrameFixture focus() {
    doFocus();
    return this;
  }

  /**
   * Simulates a user iconifying the target frame.
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
   * Simulates a user deiconifying the target frame.
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
   * Simulates a user maximizing the target frame.
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
   * Simulates a user normalizing the target frame.
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

  /** {@inheritDoc} */
  public final FrameFixture resizeWidthTo(int width) {
    doResizeWidthTo(width);
    return this;
  }

  /** {@inheritDoc} */
  public final FrameFixture resizeHeightTo(int height) {
    doResizeHeightTo(height);
    return this;
  }

  /** {@inheritDoc} */
  public final FrameFixture resizeTo(Dimension size) {
    doResizeTo(size);
    return this;
  }

  /** {@inheritDoc} */
  public final FrameFixture shouldHaveThisSize(Dimension size) {
    assertEqualSize(size);
    return this;
  }

  /** {@inheritDoc} */
  public FrameFixture shouldBeVisible() {
    assertIsVisible();
    return this;
  }

  /** {@inheritDoc} */
  public FrameFixture shouldNotBeVisible() {
    assertIsNotVisible();
    return this;
  }
}
