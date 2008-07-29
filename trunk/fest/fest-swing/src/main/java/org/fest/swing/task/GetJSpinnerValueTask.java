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
package org.fest.swing.task;

import javax.swing.JSpinner;

import org.fest.swing.core.GuiTask;

/**
 * Understands a task that returns the value of a <code>{@link JSpinner}</code>.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class GetJSpinnerValueTask extends GuiTask<Object> {

  private final JSpinner spinner;

  /**
   * Returns the value of the given <code>{@link JSpinner}</code>. This action is executed in the event dispatch thread.
   * @param spinner the given <code>JSpinner</code>.
   * @return the value of the given <code>JSpinner</code>.
   */
  public static Object valueOf(JSpinner spinner) {
    return new GetJSpinnerValueTask(spinner).run();
  }

  private GetJSpinnerValueTask(JSpinner spinner) {
    this.spinner = spinner;
  }

  /**
   * Returns the value in this task's <code>{@link JSpinner}</code>. This action is executed in the event dispatch
   * thread.
   * @return the value in this task's <code>JSpinner</code>.
   */
  protected Object executeInEDT() {
    return spinner.getValue();
  }
}