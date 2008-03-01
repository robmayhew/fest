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

import org.fest.swing.core.Robot;
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
  public JInternalFrameDriver(Robot robot) {
    super(robot);
  }

  /**
   * Brings the given <code>{@link JInternalFrame}</code> to the front.
   * @param frame the target <code>JInternalFrame</code>.
   */
  public void moveToFront(JInternalFrame frame) {
    robot.invokeAndWait(new MoveToFrontTask(frame));
  }

  private static class MoveToFrontTask implements Runnable {
    private final JInternalFrame f;

    MoveToFrontTask(JInternalFrame f) {
      this.f = f;
    }
    
    public void run() {
      f.toFront();
    }
  }
  
  /**
   * Brings the given <code>{@link JInternalFrame}</code> to the back.
   * @param frame the target <code>JInternalFrame</code>.
   */
  public void moveToBack(JInternalFrame frame) {
    robot.invokeAndWait(new MoveToBackTask(frame));
  }

  private static class MoveToBackTask implements Runnable {
    private final JInternalFrame f;

    MoveToBackTask(JInternalFrame f) { 
      this.f = f;
    }
    
    public void run() {
      f.toBack();
    }
  }

  /**
   * Maximizes the given <code>{@link JInternalFrame}</code>, deconifying it first if it is iconified.
   * @param frame the target <code>JInternalFrame</code>.
   * @throws ActionFailedException if the given <code>JInternalFrame</code> is not maximizable.
   * @throws ActionFailedException if the <code>JInternalFrame</code> vetoes the action.
   */
  public void maximize(JInternalFrame frame) {
    if (!frame.isMaximizable())
      throw actionFailure(concat("The JInternalFrame <", format(frame), "> is not maximizable"));
    maximizeOrNormalize(frame, MAXIMIZE);
  }

  /**
   * Normalizes the given <code>{@link JInternalFrame}</code>, deconifying it first if it is iconified.
   * @param frame the target <code>JInternalFrame</code>.
   * @throws ActionFailedException if the <code>JInternalFrame</code> vetoes the action.
   */
  public void normalize(JInternalFrame frame) {
    maximizeOrNormalize(frame, NORMALIZE);
  }

  private void maximizeOrNormalize(JInternalFrame frame, Action action) {
    Container clickTarget = frame;
    if (frame.isIcon()) clickTarget = frame.getDesktopIcon();
    Point p = maximizeLocation(clickTarget);
    robot.mouseMove(clickTarget, p.x, p.y);
    if (frame.isIcon()) deiconify(frame);
    setProperty(new SetMaximumTask(frame, action));
  }

  private static class SetMaximumTask extends SetPropertyTask {
    SetMaximumTask(JInternalFrame target, Action action) {
      super(target, action);
    }

    public void run() {
      try {
        target.setMaximum(action.value);
      } catch (PropertyVetoException e) {
        veto.cause = e;
      }
    }
  }

  /** 
   * Iconifies the given <code>{@link JInternalFrame}</code>. 
   * @param frame the target <code>JInternalFrame</code>.
   * @throws ActionFailedException if the given <code>JInternalFrame</code> is not iconifiable.
   * @throws ActionFailedException if the <code>JInternalFrame</code> vetoes the action.
   */
  public void iconify(JInternalFrame frame) {
    if (frame.isIcon()) return;
    if (!frame.isIconifiable()) 
      throw actionFailure(concat("The JInternalFrame <", format(frame), "> is not iconifiable"));
    Point p = iconifyLocation(frame);
    robot.mouseMove(frame, p.x, p.y);
    setProperty(new SetIconTask(frame, ICONIFY));
  }

  /** 
   * Deiconifies the given <code>{@link JInternalFrame}</code>. 
   * @param frame the target <code>JInternalFrame</code>. 
   * @throws ActionFailedException if the <code>JInternalFrame</code> vetoes the action.
   */
  public void deiconify(JInternalFrame frame) {
    if (!frame.isIcon()) return;
    Container c = frame.getDesktopIcon();
    Point p = iconifyLocation(c);
    robot.mouseMove(c, p.x, p.y);
    setProperty(new SetIconTask(frame, DEICONIFY));
  }

  private static class SetIconTask extends SetPropertyTask {
    SetIconTask(JInternalFrame target, Action action) {
      super(target, action);
    }

    public void run() {
      try {
        target.setIcon(action.value);
      } catch (PropertyVetoException e) {
        veto.cause = e;
      }
    }
  }  
  private void setProperty(SetPropertyTask task) {
    robot.invokeAndWait(task);
    PropertyVetoException vetoError = task.veto.cause;
    if (vetoError == null) return;
    throw actionFailure(concat(task.action.name, " of ", format(task.target), " was vetoed: ", vetoError.getMessage()));    
  }
  
  private static abstract class SetPropertyTask implements Runnable {
    final JInternalFrame target;
    final Action action;
    final PropertyVeto veto = new PropertyVeto();

    SetPropertyTask(JInternalFrame target, Action action) {
      this.target = target;
      this.action = action;
    }
  }
  
  /** 
   * Closes the given <code>{@link JInternalFrame}</code>. 
   * @param frame the target <code>JInternalFrame</code>. 
   * @throws ActionFailedException if the <code>JInternalFrame</code> is not closable.
   */
  public void close(final JInternalFrame frame) {
    if (!frame.isClosable()) 
      throw actionFailure(concat("The given JInternalFrame <", format(frame), "> is not closable"));
    // This is LAF-specific, so it must be done programmatically.
    Point p = closeLocation(frame);
    robot.mouseMove(frame, p.x, p.y);
    robot.invokeAndWait(new CloseFrameTask(frame));
  }
  
  private static class CloseFrameTask implements Runnable {
    private final JInternalFrame target;

    CloseFrameTask(JInternalFrame target) {
      this.target = target;
    }

    public void run() {
      target.doDefaultCloseAction();
    }
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
  
  private static class PropertyVeto {
    PropertyVetoException cause = null;
  }
}
