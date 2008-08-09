/*
 * Created on Jul 29, 2008
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
package org.fest.swing.query;

import java.awt.Component;

import org.fest.swing.core.GuiQuery;

/**
 * Understands a task that indicates whether a <code>{@link Component}</code> is visible or not.
 *
 * @author Yvonne Wang
 */
public final class IsComponentVisibleTask extends GuiQuery<Boolean> {

  private final Component component;

  /**
   * Indicates whether the given <code>{@link Component}</code> is visible or not. This action is executed in the event
   * dispatch thread.
   * @param component the given <code>Component</code>.
   * @return <code>true</code> if the given <code>Component</code> is visible, <code>false</code> otherwise.
   */
  public static boolean isVisible(Component component) {
    return new IsComponentVisibleTask(component).run();
  }

  private IsComponentVisibleTask(Component component) {
    this.component = component;
  }

  /**
   * Indicates whether this task's <code>{@link Component}</code> is visible or not. This action is executed in the
   * event dispatch thread.
   * @return <code>true</code> if this task's <code>Component</code> is visible, <code>false</code> otherwise.
   */
  protected Boolean executeInEDT() {
    return component.isVisible();
  }
}