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
package org.fest.swing.query;

import javax.swing.JTabbedPane;

import org.fest.swing.core.GuiQuery;

/**
 * Understands a task that returns the number of tabs in a <code>{@link JTabbedPane}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class GetJTabbedPaneTabCountTask extends GuiQuery<Integer> {
  private final JTabbedPane tabbedPane;

  /**
   * Returns the number of tabs in the given <code>{@link JTabbedPane}</code>. This action is executed in the event 
   * dispatch thread.
   * @param tabbedPane the given <code>JTabbedPane</code>.
   * @return the number of tabs in the given <code>JTabbedPane</code>.
   */
  public static int tabCountOf(JTabbedPane tabbedPane) {
    return new GetJTabbedPaneTabCountTask(tabbedPane).run();
  }

  private GetJTabbedPaneTabCountTask(JTabbedPane tabbedPane) {
    this.tabbedPane = tabbedPane;
  }

  /**
   * Returns the number of tabs in this task's <code>{@link JTabbedPane}</code>. This action is executed in the event
   * dispatch thread.
   * @return the number of tabs in this task's <code>JTabbedPane</code>.
   */
  protected Integer executeInEDT() {
    return tabbedPane.getTabCount();
  }
}