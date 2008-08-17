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

import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;

/**
 * Understands a task that iconifies or de-iconifies a <code>{@link JInternalFrame}</code>. This task should be executed
 * in the event dispatch thread.
 * 
 * @author Yvonne Wang
 */
class JInternalFrameSetIconTask extends JInternalFrameSetPropertyTaskTemplate {

  static JInternalFrameSetIconTask setIconTask(JInternalFrame internalFrame, JInternalFrameAction action) {
    return new JInternalFrameSetIconTask(internalFrame, action);
  }
  
  private JInternalFrameSetIconTask(JInternalFrame internalFrame, JInternalFrameAction action) {
    super(internalFrame, action);
  }

  public void execute() throws PropertyVetoException {
    target.setIcon(action.value);
  }
}