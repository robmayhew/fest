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
import java.awt.Dimension;
import java.awt.Point;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.JInternalFrame.JDesktopIcon;

import org.fest.swing.core.GuiTask;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.JInternalFrameSetPropertyTask.PropertyVeto;
import org.fest.swing.exception.ActionFailedException;

import static org.fest.swing.driver.JInternalFrameAction.*;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.task.IsJInternalFrameIconifiedTask.isIconified;
import static org.fest.util.Strings.concat;

/**
 * Understands simulation of user input on a <code>{@link JInternalFrame}</code>. Unlike
 * <code>JInternalFrameFixture</code>, this driver only focuses on behavior present only in
 * <code>{@link JInternalFrame}</code>s. This class is intended for internal use only.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
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
    if (!isMaximizable(frame))
      throw actionFailure(concat("The JInternalFrame <", format(frame), "> is not maximizable"));
    maximizeOrNormalize(frame, MAXIMIZE);
  }

  private boolean isMaximizable(JInternalFrame frame) {
    return new IsInternalFrameMaximizableTask(frame).run();
  }

  private static class IsInternalFrameMaximizableTask extends GuiTask<Boolean> {
    private final JInternalFrame frame;

    IsInternalFrameMaximizableTask(JInternalFrame frame) {
      this.frame = frame;
    }

    protected Boolean executeInEDT() {
      return frame.isMaximizable();
    }
  }

  /**
   * Normalizes the given <code>{@link JInternalFrame}</code>, deconifying it first if it is iconified.
   * @param frame the target <code>JInternalFrame</code>.
   * @throws ActionFailedException if the <code>JInternalFrame</code> vetoes the action.
   */
  public void normalize(JInternalFrame frame) {
    maximizeOrNormalize(frame, NORMALIZE);
  }

  private void maximizeOrNormalize(JInternalFrame frame, JInternalFrameAction action) {
    Container clickTarget = frame;
    if (isIconified(frame)) clickTarget = desktopIconOf(frame);
    Point p = maximizeLocation(clickTarget);
    robot.moveMouse(clickTarget, p.x, p.y);
    if (isIconified(frame)) deiconify(frame);
    setProperty(new SetMaximumTask(frame, action));
  }

  private static class SetMaximumTask extends JInternalFrameSetPropertyTask {
    SetMaximumTask(JInternalFrame target, JInternalFrameAction action) {
      super(target, action);
    }

    public void execute() throws PropertyVetoException {
      target.setMaximum(action.value);
    }
  }

  /**
   * Iconifies the given <code>{@link JInternalFrame}</code>.
   * @param frame the target <code>JInternalFrame</code>.
   * @throws ActionFailedException if the given <code>JInternalFrame</code> is not iconifiable.
   * @throws ActionFailedException if the <code>JInternalFrame</code> vetoes the action.
   */
  public void iconify(final JInternalFrame frame) {
    if (isIconified(frame)) return;
    if (!isIconifiable(frame))
      throw actionFailure(concat("The JInternalFrame <", format(frame), "> is not iconifiable"));
    Point p = iconifyLocation(frame);
    robot.moveMouse(frame, p.x, p.y);
    setProperty(new SetIconTask(frame, ICONIFY));
  }

  private boolean isIconifiable(final JInternalFrame frame) {
    return new IsIconifiableTask(frame).run();
  }

  private static class IsIconifiableTask extends GuiTask<Boolean> {
    private final JInternalFrame frame;

    IsIconifiableTask(JInternalFrame frame) {
      this.frame = frame;
    }

    protected Boolean executeInEDT() {
      return frame.isIconifiable();
    }
  }

  /**
   * De-iconifies the given <code>{@link JInternalFrame}</code>.
   * @param frame the target <code>JInternalFrame</code>.
   * @throws ActionFailedException if the <code>JInternalFrame</code> vetoes the action.
   */
  public void deiconify(JInternalFrame frame) {
    if (!isIconified(frame)) return;
    Container c = desktopIconOf(frame);
    Point p = iconifyLocation(c);
    robot.moveMouse(c, p.x, p.y);
    setProperty(new SetIconTask(frame, DEICONIFY));
  }

  private JDesktopIcon desktopIconOf(final JInternalFrame frame) {
    return new GetJDesktopIconTask(frame).run();
  }

  private static class GetJDesktopIconTask extends GuiTask<JDesktopIcon> {
    private final JInternalFrame frame;

    GetJDesktopIconTask(JInternalFrame frame) {
      this.frame = frame;
    }

    protected JDesktopIcon executeInEDT() {
      return frame.getDesktopIcon();
    }
  }

  private static class SetIconTask extends JInternalFrameSetPropertyTask {
    SetIconTask(JInternalFrame target, JInternalFrameAction action) {
      super(target, action);
    }

    public void execute() throws PropertyVetoException {
      target.setIcon(action.value);
    }
  }

  void setProperty(JInternalFrameSetPropertyTask task) {
    robot.invokeAndWait(task);
    PropertyVeto veto = task.veto();
    PropertyVetoException vetoError = veto != null ? veto.cause() : null;
    if (vetoError == null) return;
    throw actionFailure(concat(task.action.name, " of ", format(task.target), " was vetoed: <", vetoError.getMessage(), ">"));
  }

  /**
   * Resizes the <code>{@link JInternalFrame}</code> horizontally.
   * @param w the target <code>JInternalFrame</code>.
   * @param width the width that the <code>JInternalFrame</code> should have after being resized.
   */
  public void resizeWidthTo(JInternalFrame w, int width) {
    resizeTo(w, new Dimension(width, w.getHeight()));
  }

  /**
   * Resizes the <code>{@link JInternalFrame}</code> vertically.
   * @param w the target <code>JInternalFrame</code>.
   * @param height the height that the <code>JInternalFrame</code> should have after being resized.
   */
  public void resizeHeightTo(JInternalFrame w, int height) {
    resizeTo(w, new Dimension(w.getWidth(), height));
  }

  /**
   * Resizes the <code>{@link JInternalFrame}</code> to the given size.
   * @param w the target <code>JInternalFrame</code>.
   * @param size the size to resize the <code>JInternalFrame</code> to.
   */
  public void resizeTo(JInternalFrame w, Dimension size) {
    resize(w, size.width, size.height);
  }

  /**
   * Moves the <code>{@link JInternalFrame}</code> to the given location.
   * @param w the target <code>JInternalFrame</code>.
   * @param where the location to move the <code>JInternalFrame</code> to.
   * @throws ActionFailedException if the given <code>JInternalFrame</code> is not showing on the screen.
   */
  public void moveTo(JInternalFrame w, Point where) {
    move(w, where.x, where.y);
  }

  /**
   * Closes the given <code>{@link JInternalFrame}</code>.
   * @param frame the target <code>JInternalFrame</code>.
   * @throws ActionFailedException if the <code>JInternalFrame</code> is not closable.
   */
  public void close(JInternalFrame frame) {
    if (!isClosable(frame))
      throw actionFailure(concat("The JInternalFrame <", format(frame), "> is not closable"));
    // This is LAF-specific, so it must be done programmatically.
    Point p = closeLocation(frame);
    robot.moveMouse(frame, p.x, p.y);
    robot.invokeAndWait(new CloseFrameTask(frame));
  }

  private boolean isClosable(final JInternalFrame frame) {
    return new IsClosableTask(frame).run();
  }

  private static class IsClosableTask extends GuiTask<Boolean> {
    private final JInternalFrame frame;

    IsClosableTask(JInternalFrame frame) {
      this.frame = frame;
    }

    protected Boolean executeInEDT() {
      return frame.isClosable();
    }
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
}
