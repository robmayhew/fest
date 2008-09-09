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

import org.fest.swing.edt.GuiTask;

/**
 * Understands a task that closes a <code>{@link JInternalFrame}</code>. This task should be executed in the event 
 * dispatch thread.
 *
 * @author Yvonne Wang
 */
class JInternalFrameCloseTask extends GuiTask {

  private final JInternalFrame internalFrame;

  static JInternalFrameCloseTask closeTask(JInternalFrame internalFrame) {
    return new JInternalFrameCloseTask(internalFrame);
  }
  
  private JInternalFrameCloseTask(JInternalFrame internalFrame) {
    this.internalFrame = internalFrame;
  }

  protected void executeInEDT() {
    internalFrame.doDefaultCloseAction();
  }
}