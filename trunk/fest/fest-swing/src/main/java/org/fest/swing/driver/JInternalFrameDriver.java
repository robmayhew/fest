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

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.UnexpectedException;

import static org.fest.swing.driver.ComponentStateValidator.validateIsShowing;
import static org.fest.swing.driver.JInternalFrameAction.*;
import static org.fest.swing.driver.JInternalFrameClosableQuery.isClosable;
import static org.fest.swing.driver.JInternalFrameDesktopIconQuery.desktopIconOf;
import static org.fest.swing.driver.JInternalFrameIconQuery.isIconified;
import static org.fest.swing.driver.JInternalFrameIconifiableQuery.isIconifiable;
import static org.fest.swing.driver.JInternalFrameSetIconTask.setIcon;
import static org.fest.swing.driver.JInternalFrameSetMaximumTask.setMaximum;
import static org.fest.swing.driver.WindowLikeContainerLocations.*;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.util.Strings.concat;

/**
 * Understands simulation of user input on a <code>{@link JInternalFrame}</code>. Unlike
 * <code>JInternalFrameFixture</code>, this driver only focuses on behavior present only in
 * <code>{@link JInternalFrame}</code>s. This class is intended for internal use only.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JInternalFrameDriver extends ContainerDriver {

  /**
   * Creates a new </code>{@link JInternalFrameDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public JInternalFrameDriver(Robot robot) {
    super(robot);
  }

  /**
   * Brings the given <code>{@link JInternalFrame}</code> to the front.
   * @param internalFrame the target <code>JInternalFrame</code>.
   */
  @RunsInEDT
  public void moveToFront(JInternalFrame internalFrame) {
    toFront(internalFrame);
  }

  @RunsInEDT
  private static void toFront(final JInternalFrame internalFrame) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        // it seems that moving to front always works, regardless if the internal frame is invisible and/or disabled.
        internalFrame.toFront();
      }
    });
  }
  
  /**
   * Brings the given <code>{@link JInternalFrame}</code> to the back.
   * @param internalFrame the target <code>JInternalFrame</code>.
   */
  @RunsInEDT
  public void moveToBack(JInternalFrame internalFrame) {
    toBack(internalFrame);
  }
  
  @RunsInEDT
  private static void toBack(final JInternalFrame internalFrame) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        // it seems that moving to back always works, regardless if the internal frame is invisible and/or disabled.
        internalFrame.moveToBack();
      }
    });
  }

  /**
   * Maximizes the given <code>{@link JInternalFrame}</code>, deconifying it first if it is iconified.
   * @param internalFrame the target <code>JInternalFrame</code>.
   * @throws IllegalStateException if the <code>JInternalFrame</code> is not maximizable.
   * @throws IllegalStateException if the <code>JInternalFrame</code> is not showing on the screen.
   * @throws ActionFailedException if the <code>JInternalFrame</code> vetoes the action.
   */
  @RunsInEDT
  public void maximize(JInternalFrame internalFrame) {
    Point maximizeLocation = validateAndFindMaximizeLocation(internalFrame);
    maximizeOrNormalize(internalFrame, MAXIMIZE, maximizeLocation);
  }
  
  @RunsInEDT
  private static Point validateAndFindMaximizeLocation(final JInternalFrame internalFrame) {
    return execute(new GuiQuery<Point>() {
      protected Point executeInEDT() {
        validateCanMaximize(internalFrame);
        return findMaximizeLocation(internalFrame);
      }
    });
  }
  
  @RunsInCurrentThread
  private static void validateCanMaximize(JInternalFrame internalFrame) {
    validateIsShowingOrIconified(internalFrame);
    if (!internalFrame.isMaximizable()) 
      throw new IllegalStateException(concat("The JInternalFrame <", format(internalFrame), "> is not maximizable"));
  }
  
  /**
   * Normalizes the given <code>{@link JInternalFrame}</code>, deconifying it first if it is iconified.
   * @param internalFrame the target <code>JInternalFrame</code>.
   * @throws IllegalStateException if the <code>JInternalFrame</code> is not showing on the screen.
   * @throws ActionFailedException if the <code>JInternalFrame</code> vetoes the action.
   */
  @RunsInEDT
  public void normalize(JInternalFrame internalFrame) {
    Point normalizeLocation = validateAndFindNormalizeLocation(internalFrame);
    maximizeOrNormalize(internalFrame, NORMALIZE, normalizeLocation);
  }

  @RunsInEDT
  private static Point validateAndFindNormalizeLocation(final JInternalFrame internalFrame) {
    return execute(new GuiQuery<Point>() {
      protected Point executeInEDT() {
        validateIsShowingOrIconified(internalFrame);
        return findMaximizeLocation(internalFrame);
      }
    });
  }
  
  @RunsInCurrentThread
  private static void validateIsShowingOrIconified(JInternalFrame internalFrame) {
    if (!internalFrame.isIcon()) validateIsShowing(internalFrame);
  }
  
  @RunsInCurrentThread
  private static Point findMaximizeLocation(JInternalFrame internalFrame) {
    Container clickTarget = internalFrame.isIcon() ? internalFrame.getDesktopIcon() : internalFrame;
    return maximizeLocationOf(clickTarget);
  }

  @RunsInEDT
  private void maximizeOrNormalize(JInternalFrame internalFrame, JInternalFrameAction action, Point toMoveMouseTo) {
    moveMouseIgnoringAnyError(internalFrame, toMoveMouseTo);
    setMaximumProperty(internalFrame, action);
  }

  @RunsInEDT
  private void setMaximumProperty(JInternalFrame internalFrame, JInternalFrameAction action) {
    try {
      setIconProperty(internalFrame, DEICONIFY);
      setMaximum(internalFrame, action);
      robot.waitForIdle();
    } catch (UnexpectedException unexpected) {
      failIfVetoed(internalFrame, action, unexpected);
    }
  }

  /**
   * Iconifies the given <code>{@link JInternalFrame}</code>.
   * @param internalFrame the target <code>JInternalFrame</code>.
   * @throws ActionFailedException if the given <code>JInternalFrame</code> is not iconifiable.
   * @throws ActionFailedException if the <code>JInternalFrame</code> vetoes the action.
   */
  public void iconify(JInternalFrame internalFrame) {
    if (isIconified(internalFrame)) return;
    if (!isIconifiable(internalFrame))
      throw actionFailure(concat("The JInternalFrame <", format(internalFrame), "> is not iconifiable"));
    Point p = iconifyLocationOf(internalFrame);
    robot.moveMouse(internalFrame, p.x, p.y);
    setIconProperty(internalFrame, ICONIFY);
  }

  /**
   * De-iconifies the given <code>{@link JInternalFrame}</code>.
   * @param internalFrame the target <code>JInternalFrame</code>.
   * @throws ActionFailedException if the <code>JInternalFrame</code> vetoes the action.
   */
  public void deiconify(JInternalFrame internalFrame) {
    if (!isIconified(internalFrame)) return;
    Container c = desktopIconOf(internalFrame);
    Point p = iconifyLocationOf(c);
    robot.moveMouse(c, p.x, p.y);
    setIconProperty(internalFrame, DEICONIFY);
  }

  private void setIconProperty(JInternalFrame internalFrame, JInternalFrameAction action) {
    try {
      setIcon(internalFrame, action);
      robot.waitForIdle();
    } catch (UnexpectedException unexpected) {
      failIfVetoed(internalFrame, action, unexpected);
    }
  }

  // made package-protected for testing purposes only
  void failIfVetoed(JInternalFrame internalFrame, JInternalFrameAction action, UnexpectedException unexpected) {
    PropertyVetoException vetoError = vetoFrom(unexpected);
    if (vetoError == null) return;
    throw actionFailure(concat(action.name, " of ", format(internalFrame), " was vetoed: <", vetoError.getMessage(), ">"));
  }

  private PropertyVetoException vetoFrom(UnexpectedException unexpected) {
    Throwable cause = unexpected.getCause();
    if (!(cause instanceof PropertyVetoException)) return null;
    return (PropertyVetoException)cause;
  }

  /**
   * Resizes the <code>{@link JInternalFrame}</code> horizontally.
   * @param internalFrame the target <code>JInternalFrame</code>.
   * @param width the width that the <code>JInternalFrame</code> should have after being resized.
   */
  public void resizeWidthTo(JInternalFrame internalFrame, int width) {
    Dimension size = sizeOf(internalFrame);
    resizeTo(internalFrame, new Dimension(width, size.height));
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
   * @param internalFrame the target <code>JInternalFrame</code>.
   * @param size the size to resize the <code>JInternalFrame</code> to.
   */
  public void resizeTo(JInternalFrame internalFrame, Dimension size) {
    resize(internalFrame, size.width, size.height);
  }

  /**
   * Moves the <code>{@link JInternalFrame}</code> to the given location.
   * @param internalFrame the target <code>JInternalFrame</code>.
   * @param where the location to move the <code>JInternalFrame</code> to.
   * @throws ActionFailedException if the given <code>JInternalFrame</code> is not showing on the screen.
   */
  public void moveTo(JInternalFrame internalFrame, Point where) {
    move(internalFrame, where.x, where.y);
  }

  /**
   * Closes the given <code>{@link JInternalFrame}</code>.
   * @param internalFrame the target <code>JInternalFrame</code>.
   * @throws ActionFailedException if the <code>JInternalFrame</code> is not closable.
   */
  public void close(JInternalFrame internalFrame) {
    if (!isClosable(internalFrame))
      throw actionFailure(concat("The JInternalFrame <", format(internalFrame), "> is not closable"));
    // This is LAF-specific, so it must be done programmatically.
    robot.moveMouse(internalFrame, closeLocationOf(internalFrame));
    JInternalFrameCloseTask.close(internalFrame);
    robot.waitForIdle();
  }
}
