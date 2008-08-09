/*
 * Created on Jul 28, 2008
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
package org.fest.swing.query;

import javax.swing.JToolBar;

import org.fest.swing.core.GuiQuery;

import static javax.swing.SwingConstants.*;

/**
 * Understands a task that returns whether a <code>{@link JToolBar}</code> is horizontal or vertical.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class GetJToolBarOrientationTask extends GuiQuery<Integer> {
  private final JToolBar toolBar;

  /**
   * Returns the whether the given <code>{@link JToolBar}</code> is horizontal. This action is executed in the event
   * dispatch thread.
   * @param toolBar the given <code>JToolBar</code>.
   * @return <code>true</code> if the given <code>JToolBar</code> is horizontal, <code>false</code> otherwise.
   */
  public static boolean isHorizontal(JToolBar toolBar) {
    return orientationOf(toolBar) == HORIZONTAL;
  }

  /**
   * Returns the whether the given <code>{@link JToolBar}</code> is vertical. This action is executed in the event
   * dispatch thread.
   * @param toolBar the given <code>JToolBar</code>.
   * @return <code>true</code> if the given <code>JToolBar</code> is vertical, <code>false</code> otherwise.
   */
  public static boolean isVertical(JToolBar toolBar) {
    return orientationOf(toolBar) == VERTICAL;
  }

  /**
   * Returns the orientation of the given <code>{@link JToolBar}</code>. This action is executed in the event dispatch
   * thread.
   * @param toolBar the given <code>JToolBar</code>.
   * @return the orientation of the given <code>JToolBar</code>.
   */
  public static int orientationOf(JToolBar toolBar) {
    return new GetJToolBarOrientationTask(toolBar).run();
  }

  private GetJToolBarOrientationTask(JToolBar toolBar) {
    this.toolBar = toolBar;
  }

  /**
   * Returns the orientation in this task's <code>{@link JToolBar}</code>. This action is executed in the event dispatch
   * thread.
   * @return the orientation in this task's <code>JToolBar</code>.
   */
  protected Integer executeInEDT() {
    return toolBar.getOrientation();
  }
}