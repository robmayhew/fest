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

import javax.swing.JSplitPane;

import org.fest.swing.edt.GuiTask;

/**
 * Understands a task that sets the location of the divider of a <code>{@link JSplitPane}</code>. This task should be
 * executed in the event dispatch thread.
 * 
 * @author Alex Ruiz
 */
class JSplitPaneMoveDividerTask extends GuiTask {
  
  private final JSplitPane splitPane;
  private final int location;

  static JSplitPaneMoveDividerTask moveDividerTask(JSplitPane splitPane, int location) {
    return new JSplitPaneMoveDividerTask(splitPane, location);
  }
  
  private JSplitPaneMoveDividerTask(JSplitPane splitPane, int location) {
    this.splitPane = splitPane;
    this.location = location;
  }

  protected void executeInEDT() {
    splitPane.setDividerLocation(location);
  }
}