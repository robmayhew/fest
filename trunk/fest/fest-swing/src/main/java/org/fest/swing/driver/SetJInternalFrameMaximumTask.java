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
 * Understands a task that maximizes or restores a <code>{@link JInternalFrame}</code>.
 *
 * @author Yvonne Wang
 */
class SetJInternalFrameMaximumTask extends JInternalFrameSetPropertyTask {

  SetJInternalFrameMaximumTask(JInternalFrame target, JInternalFrameAction action) {
    super(target, action);
  }

  public void execute() throws PropertyVetoException {
    target.setMaximum(action.value);
  }
}