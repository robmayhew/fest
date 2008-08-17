/*
 * Created on Feb 2, 2008
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

import java.awt.*;

import javax.swing.JToolBar;
import javax.swing.plaf.ToolBarUI;
import javax.swing.plaf.basic.BasicToolBarUI;

import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;

import static javax.swing.SwingUtilities.getWindowAncestor;

import static org.fest.swing.driver.ComponentLocationQuery.locationOf;
import static org.fest.swing.driver.JToolBarDockingSourceQuery.dockingSourceOf;
import static org.fest.swing.driver.JToolBarFloatableQuery.isFloatable;
import static org.fest.swing.driver.JToolBarIsFloatingQuery.isJToolBarFloating;
import static org.fest.swing.driver.JToolBarUIQuery.uiOf;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.query.ComponentParentTaskQuery.parentOf;
import static org.fest.util.Strings.*;

/**
 * Understands simulation of user input on a <code>{@link JToolBar}</code>. Unlike <code>JToolBarFixture</code>, this
 * driver only focuses on behavior present only in <code>{@link JToolBar}</code>s. This class is intended for internal
 * use only.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JToolBarDriver extends JComponentDriver {

  private final JToolBarLocation location = new JToolBarLocation();

  /**
   * Creates a new </code>{@link JToolBarDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public JToolBarDriver(Robot robot) {
    super(robot);
  }

  /**
   * Indicates whether the given <code>{@link JToolBar}</code> is floating or not.
   * @param toolBar the target <code>JToolBar</code>.
   * @return <code>true</code> if the <code>JToolBar</code> is floating, <code>false</code> otherwise.
   */
  public boolean isFloating(JToolBar toolBar) {
    ToolBarUI ui = uiOf(toolBar);
    if (ui instanceof BasicToolBarUI) return isJToolBarFloating((BasicToolBarUI)ui);
    // Have to guess; probably ought to check for sibling components
    Window w = getWindowAncestor(toolBar);
    return !(w instanceof Frame) && parentOf(toolBar).getComponentCount() == 1;
  }

  /**
   * Makes the given <code>{@link JToolBar}</code> float.
   * @param toolBar the target <code>JToolBar</code>.
   * @throws ActionFailedException if the <code>JToolBar</code> is not floatable.
   * @throws ActionFailedException if the <code>JToolBar</code> cannot be dragged.
   */
  public void makeFloat(JToolBar toolBar) {
    Window w = getWindowAncestor(toolBar);
    Point where = locationOf(w);
    floatTo(toolBar, where.x, where.y);
  }

  /**
   * Drags the <code>{@link JToolBar}</code> to the given location, causing it to float.
   * @param toolBar the target <code>JToolBar</code>.
   * @param x the horizontal coordinate of the location to drag the <code>JToolBar</code> to.
   * @param y the vertical coordinate of the location to drag the <code>JToolBar</code> to.
   * @throws ActionFailedException if the <code>JToolBar</code> is not floatable.
   * @throws ActionFailedException if the <code>JToolBar</code> cannot be dragged.
   */
  public void floatTo(JToolBar toolBar, int x, int y) {
    if (!isFloatable(toolBar)) throw actionFailure(concat("JToolbar <", format(toolBar), "> is not floatable"));
    Window w = getWindowAncestor(toolBar);
    drag(toolBar, location.pointToGrab(toolBar));
    drop(w, new Point(x - w.getX(), y - w.getY()));
    if (!isFloating(toolBar)) throw actionFailure(concat("Unable to float JToolbar <", format(toolBar), ">"));
  }

  /**
   * Drop the {@link JToolBar} to the requested constraint position. The constraint position must be one of the
   * constants <code>{@link BorderLayout#NORTH NORTH}</code>, <code>{@link BorderLayout#EAST EAST}</code>,
   * <code>{@link BorderLayout#SOUTH SOUTH}</code>, or <code>{@link BorderLayout#WEST WEST}</code>.
   * @param toolBar the target <code>JToolBar</code>.
   * @param constraint the constraint position.
   * @throws IllegalArgumentException if the constraint has an invalid value.
   * @throws ActionFailedException if the dock container cannot be found.
   */
  public void unfloat(JToolBar toolBar, String constraint) {
    Container dock = dockFor(toolBar);
    drag(toolBar, location.pointToGrab(toolBar));
    drop(dock, location.dockLocation(toolBar, dock, constraint));
    if (isFloating(toolBar))
      throw actionFailure(concat("Failed to dock <", format(toolBar), "> using constraint ", quote(constraint)));
  }

  private Container dockFor(final JToolBar toolBar) {
    Container dock = dockingSourceOf(toolBar);
    if (dock != null) return dock;
    throw actionFailure("Unabled to determine dock for JToolBar");
  }

  /**
   * Closes a floating <code>{@link JToolBar}</code>, making it go back to its original container in its last known
   * location.
   * @param toolBar the target <code>JToolBar</code>.
   */
  public void unfloat(JToolBar toolBar) {
    Window w = getWindowAncestor(toolBar);
    robot.close(w);
    robot.waitForIdle();
  }
}
