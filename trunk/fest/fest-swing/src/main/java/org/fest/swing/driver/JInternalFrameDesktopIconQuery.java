/*
 * Created on Aug 8, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.driver;

import javax.swing.JInternalFrame;
import javax.swing.JInternalFrame.JDesktopIcon;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the <code>{@link JDesktopIcon}</code> used
 * when a given <code>{@link JInternalFrame}</code> is iconified.
 * @see JInternalFrame#getDesktopIcon()
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
final class JInternalFrameDesktopIconQuery {

  static JDesktopIcon desktopIconOf(final JInternalFrame internalFrame) {
    return execute(new GuiQuery<JDesktopIcon>() {
      protected JDesktopIcon executeInEDT() throws Throwable {
        return internalFrame.getDesktopIcon();
      }
    });
  }

  private JInternalFrameDesktopIconQuery() {}
}