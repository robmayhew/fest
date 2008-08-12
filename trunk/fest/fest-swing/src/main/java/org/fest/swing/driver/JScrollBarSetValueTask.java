/*
 * Created on Aug 11, 2008
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

import javax.swing.JScrollBar;

import org.fest.swing.core.GuiTask;

/**
 * Understands a task that sets the value of a <code>{@link JScrollBar}</code>.This task should be executed in the event 
 * dispatch thread.
 *
 * @author Alex Ruiz 
 */
class JScrollBarSetValueTask extends GuiTask {
  
  private final JScrollBar scrollBar;
  private final int value;

  static JScrollBarSetValueTask setValue(JScrollBar scrollBar, int value) {
    return new JScrollBarSetValueTask(scrollBar, value);
  }
  
  private JScrollBarSetValueTask(JScrollBar scrollBar, int value) {
    this.scrollBar = scrollBar;
    this.value = value;
  }

  protected void executeInEDT() {
    scrollBar.setValue(value);
  }
}