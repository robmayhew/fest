/*
 * Created on Jun 22, 2008
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
package org.fest.swing.input;

import java.awt.Toolkit;

import org.fest.swing.core.GuiTask;

/**
 * Understands a task that replaces the system event queue with a <code>{@link DragAwareEventQueue}</code>. This task
 * should be executed in the event dispatch thread.
 *
 * @author Alex Ruiz
 */
class ToolkitPushDragAwereEventQueueTask extends GuiTask {
  
  private final Toolkit toolkit;
  private final DragAwareEventQueue queue;

  static ToolkitPushDragAwereEventQueueTask pushDragAwareEventQueueTask(Toolkit toolkit, DragAwareEventQueue queue) {
    return new ToolkitPushDragAwereEventQueueTask(toolkit, queue);
  }
  
  ToolkitPushDragAwereEventQueueTask(Toolkit toolkit, DragAwareEventQueue queue) {
    this.toolkit = toolkit;
    this.queue = queue;
  }

  protected void executeInEDT() {
    toolkit.getSystemEventQueue().push(queue);
  }
}