/*
 * Created on Jul 26, 2008
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

import java.awt.Component;

import org.fest.swing.core.GuiTask;

/**
 * Understands a task that returns a <code>{@link Component}</code> is enabled or not.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class IsComponentEnabledTask extends GuiTask<Boolean> {

  private final Component component;

  /**
   * Returns whether the given <code>{@link Component}</code> is enabled or not. This action is executed in the event
   * dispatch thread.
   * @param component the given <code>Component</code>.
   * @return <code>true</code> if the given <code>Component</code> is enabled, <code>false</code> otherwise.
   */
  public static boolean isEnabled(Component component) {
    return new IsComponentEnabledTask(component).run();
  }

  private IsComponentEnabledTask(Component component) {
    this.component = component;
  }

  /**
   * Returns whether this task's <code>{@link Component}</code> is enabled or not. This action is executed in the event
   * dispatch thread.
   * @return <code>true</code> if this task's <code>Component</code> is enabled, <code>false</code> otherwise.
   */
  protected Boolean executeInEDT() {
    return component.isEnabled();
  }
}