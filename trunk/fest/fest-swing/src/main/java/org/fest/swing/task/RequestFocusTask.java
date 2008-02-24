/*
 * Created on Feb 23, 2008
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

import java.awt.Component;

/**
 * Understands a task that request focus for a given <code>{@link Component}</code>.
 *
 * @author Alex Ruiz 
 */
public class RequestFocusTask implements Runnable {
  private final Component c;

  /**
   * Creates a new </code>{@link RequestFocusTask}</code>.
   * @param c the <code>Component</code> to request focus for.
   */
  public RequestFocusTask(Component c) {
    this.c = c;
  }

  /**
   * Invokes <code>{@link Component#requestFocusInWindow() requestFocusInWindow}</code> for this task's 
   * <code>{@link Component}</code>.
   */
  public void run() {
    c.requestFocusInWindow();
  }
}
