/*
 * Created on Feb 1, 2008
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

import java.awt.Container;
import java.awt.Point;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;

import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.ActionFailedException;

import static org.fest.swing.driver.JInternalFrameDriver.Action.*;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.format.Formatting.format;
import static org.fest.util.Strings.concat;

/**
 * Understands simulation of user input on a <code>{@link JInternalFrame}</code>. Unlike
 * <code>JInternalFrameFixture</code>, this driver only focuses on behavior present only in
 * <code>{@link JInternalFrame}</code>s. This class is intended for internal use only.
 * 
 * @author Alex Ruiz
 */
public class JInternalFrameDriver extends WindowLikeContainerDriver {

  /**
   * Creates a new </code>{@link JInternalFrameDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public JInternalFrameDriver(RobotFixture robot) {
    super(robot);
  }

  /**
   * Maximizes the given <code>{@link JInternalFrame}</code>, deconifying it first if it is iconified.
   * @param frame the target <code>JInternalFrame</code>.
   * @throws ActionFailedException if the given <code>JInternalFrame</code> is not maximizable.
   * @throws ActionFailedException if the <code>JInternalFrame</code> vetoes the action.
   */
  public final void maximize(JInternalFrame frame) {
    if (!frame.isMaximizable())
      throw actionFailure(concat("The JInternalFrame <", format(frame), "> is not maximizable"));
    maximizeOrNormalize(frame, MAXIMIZE);
  }

  /**
   * Normalizes the given <code>{@link JInternalFrame}</code>, deconifying it first if it is iconified.
   * @param frame the target <code>JInternalFrame</code>.
   * @throws ActionFailedException if the <code>JInternalFrame</code> vetoes the action.
   */
  public final void normalize(JInternalFrame frame) {
    maximizeOrNormalize(frame, NORMALIZE);
  }

  private void maximizeOrNormalize(final JInternalFrame frame, final Action action) {
    Container clickTarget = frame;
    if (frame.isIcon()) clickTarget = frame.getDesktopIcon();
    Point p = maximizeLocation(clickTarget);
    robot.mouseMove(clickTarget, p.x, p.y);
    if (frame.isIcon()) deiconify(frame);
    final VetoExceptionHolder veto = new VetoExceptionHolder();
    robot.invokeAndWait(new Runnable() {
      public void run() {
        try {
          frame.setMaximum(action.value);
        } catch (PropertyVetoException e) {
          veto.e = e;
        }
      }
    });
    checkIfActionWasVetoed(frame, action, veto);
  }

  /** 
   * Iconifies the given <code>{@link JInternalFrame}</code>. 
   * @param frame the target <code>JInternalFrame</code>.
   * @throws ActionFailedException if the given <code>JInternalFrame</code> is not iconifiable.
   * @throws ActionFailedException if the <code>JInternalFrame</code> vetoes the action.
   */
  public final void iconify(JInternalFrame frame) {
    if (frame.isIcon()) return;
    if (!frame.isIconifiable()) 
      throw actionFailure(concat("The JInternalFrame <", format(frame), "> is not iconifiable"));
    Point p = iconifyLocation(frame);
    robot.mouseMove(frame, p.x, p.y);
    setIconProperty(frame, ICONIFY);
  }

  /** 
   * Deiconifies the given <code>{@link JInternalFrame}</code>. 
   * @param frame the target <code>JInternalFrame</code>. 
   * @throws ActionFailedException if the <code>JInternalFrame</code> vetoes the action.
   */
  public final void deiconify(JInternalFrame frame) {
    if (!frame.isIcon()) return;
    Container c = frame.getDesktopIcon();
    Point p = iconifyLocation(c);
    robot.mouseMove(c, p.x, p.y);
    setIconProperty(frame, DEICONIFY);
  }

  private void setIconProperty(final JInternalFrame frame, final Action action) {
    final VetoExceptionHolder veto = new VetoExceptionHolder();
    robot.invokeAndWait(new Runnable() {
      public void run() {
        try {
          frame.setIcon(action.value);
        } catch (PropertyVetoException e) {
          veto.e = e;
        }
      }
    });
    checkIfActionWasVetoed(frame, action, veto);
  }

  private void checkIfActionWasVetoed(JInternalFrame frame, Action action, VetoExceptionHolder veto) {
    if (veto.e == null) return;
    throw actionFailure(concat(action.name, " of ", format(frame), " was vetoed: ", veto.e.getMessage()));    
  }

  /** 
   * Closes the given <code>{@link JInternalFrame}</code>. 
   * @param frame the target <code>JInternalFrame</code>. 
   * @throws ActionFailedException if the <code>JInternalFrame</code> is not closable.
   */
  public final void close(final JInternalFrame frame) {
    if (!frame.isClosable()) 
      throw actionFailure(concat("The given JInternalFrame <", format(frame), "> is not closable"));
    // This is LAF-specific, so it must be done programmatically.
    Point p = closeLocation(frame);
    robot.mouseMove(frame, p.x, p.y);
    robot.invokeAndWait(new Runnable() {
      public void run() {
        frame.doDefaultCloseAction();
      }
    });
  }
  
  static enum Action {
    MAXIMIZE("Maximize", true), NORMALIZE("Normalize", false), ICONIFY("Iconify", true), DEICONIFY("Deiconify", false);
    
    final String name;
    final boolean value;
    
    private Action(String name, boolean value) {
      this.name = name;
      this.value = value;
    }
  }
  
  private static class VetoExceptionHolder {
    PropertyVetoException e = null;
  }
}
