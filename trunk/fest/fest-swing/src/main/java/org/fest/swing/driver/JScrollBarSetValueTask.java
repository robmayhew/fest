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

import org.fest.swing.core.Condition;
import org.fest.swing.core.GuiTask;

import static java.lang.String.valueOf;

import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.util.Strings.concat;

/**
 * Understands a task that sets the value of a <code>{@link JScrollBar}</code>.This task is executed in the event
 * dispatch thread.
 * 
 * @author Alex Ruiz
 */
final class JScrollBarSetValueTask {
  
  static void setValue(final JScrollBar scrollBar, final int value) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        scrollBar.setValue(value);
      }
    }, new Condition(concat("JScrollBar value is ", valueOf(value))) {
      public boolean test() {
        return !scrollBar.getValueIsAdjusting() && scrollBar.getValue() == value;
      }
    });
  }
  
  private JScrollBarSetValueTask() {}
}