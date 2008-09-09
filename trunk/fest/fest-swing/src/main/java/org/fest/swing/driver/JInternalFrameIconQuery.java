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
package org.fest.swing.driver;

import javax.swing.JInternalFrame;

import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that indicates if a <code>{@link JInternalFrame}</code> 
 * is iconified or not.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class JInternalFrameIconQuery extends GuiQuery<Boolean> {
  private final JInternalFrame frame;

  static boolean isIconified(JInternalFrame frame) {
    return execute(new JInternalFrameIconQuery(frame));
  }

  JInternalFrameIconQuery(JInternalFrame frame) {
    this.frame = frame;
  }

  protected Boolean executeInEDT() {
    return frame.isIcon();
  }
}