/*
 * Created on Jul 29, 2008
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

import javax.swing.JInternalFrame;

import org.fest.swing.core.GuiQuery;

/**
 * Understands a task that indicates if a <code>{@link JInternalFrame}</code> is iconified or not.
 *
 * @author Yvonne Wang
 */
public class IsJInternalFrameIconifiedTask extends GuiQuery<Boolean> {
  private final JInternalFrame frame;

  /**
   * Indicates whether the given <code>{@link JInternalFrame}</code> is iconified or not. This action is executed in the
   * event dispatch thread.
   * @param frame the given <code>JInternalFrame</code>.
   * @return <code>true</code> if the given <code>JInternalFrame</code> is iconified, <code>false</code> otherwise.
   */
  public static boolean isIconified(JInternalFrame frame) {
    return new IsJInternalFrameIconifiedTask(frame).run();
  }

  private IsJInternalFrameIconifiedTask(JInternalFrame frame) {
    this.frame = frame;
  }

  /**
   * Indicates whether this task's <code>{@link JInternalFrame}</code> is iconified or not. This action is executed in
   * the event dispatch thread.
   * @return <code>true</code> if this task's <code>JInternalFrame</code> is iconified, <code>false</code> otherwise.
   */
  protected Boolean executeInEDT() {
    return frame.isIcon();
  }
}