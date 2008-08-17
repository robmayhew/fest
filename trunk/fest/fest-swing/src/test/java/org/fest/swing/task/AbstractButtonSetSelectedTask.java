/*
 * Created on Aug 14, 2008
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
package org.fest.swing.task;

import javax.swing.AbstractButton;

import org.fest.swing.core.GuiTask;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands a task that selects/diselects a <code>{@link AbstractButton}</code>.
 *
 * @author Alex Ruiz 
 */
public class AbstractButtonSetSelectedTask extends GuiTask {
  
  private final AbstractButton button;
  private final boolean selected;

  public static void setSelected(AbstractButton button, boolean selected) {
    execute(new AbstractButtonSetSelectedTask(button, selected));
  }
  
  private AbstractButtonSetSelectedTask(AbstractButton button, boolean selected) {
    this.button = button;
    this.selected = selected;
  }

  protected void executeInEDT() {
    button.setSelected(selected);
  }
}