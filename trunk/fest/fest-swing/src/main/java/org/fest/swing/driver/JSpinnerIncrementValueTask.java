/*
 * Created on Aug 12, 2008
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

import javax.swing.JSpinner;

/**
 * Understands a task that increments the value of a <code>{@link JSpinner}</code> .This task should be executed in the
 * event dispatch thread.
 * 
 * @author Alex Ruiz
 */
class JSpinnerIncrementValueTask extends JSpinnerSetValueTaskTemplate {
  
  static JSpinnerIncrementValueTask incrementValueOf(JSpinner spinner) {
    return new JSpinnerIncrementValueTask(spinner);
  }
  
  private JSpinnerIncrementValueTask(JSpinner spinner) {
    super(spinner);
  }

  protected void executeInEDT() {
    setValue(spinner.getNextValue());
  }
}