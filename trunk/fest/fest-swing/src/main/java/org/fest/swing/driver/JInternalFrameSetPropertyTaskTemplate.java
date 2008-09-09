/*
 * Created on Jul 22, 2008
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

import org.fest.swing.edt.GuiTask;

/**
 * Understands a template for tasks that set a property in a <code>{@link JInternalFrame}</code>. This task should be 
 * executed in the event dispatch thread.
 *
 * @author Alex Ruiz 
 */
abstract class JInternalFrameSetPropertyTaskTemplate extends GuiTask {
  
  final JInternalFrame target;
  final JInternalFrameAction action;
  
  private PropertyVeto veto;

  JInternalFrameSetPropertyTaskTemplate(JInternalFrame target, JInternalFrameAction action) {
    this.target = target;
    this.action = action;
  }

  protected final void executeInEDT() {
    try {
      execute();
    } catch (PropertyVetoException e) {
      veto = new PropertyVeto(e);
    }
  }

  abstract void execute() throws PropertyVetoException;
  
  PropertyVeto veto() { return veto; }
  
  static class PropertyVeto {
    private final PropertyVetoException cause;
    
    PropertyVeto(PropertyVetoException cause) {
      this.cause = cause;
    }
    
    PropertyVetoException cause() { return cause; }
  }
}