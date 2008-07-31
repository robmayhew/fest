/*
 * Created on Jul 30, 2008
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

import javax.swing.JList;

import org.fest.swing.core.GuiTask;

/**
 * Understands a task that returns the number of elements in a <code>{@link JList}</code>.
 *
 * @author Yvonne Wang
 */
public class GetJListSizeTask extends GuiTask<Integer> {
  private final JList list;

  /**
   * Returns the number of elements in the given <code>{@link JList}</code>. This action is executed in the event
   * dispatch thread.
   * @param list the given <code>JList</code>.
   * @return the number of elements in the given <code>JList</code>.
   */
  public static int sizeOf(JList list) {
    return new GetJListSizeTask(list).run();
  }

  private GetJListSizeTask(JList list) {
    this.list = list;
  }

  /**
   * Returns the number of elements in this task's <code>{@link JList}</code>. This action is executed in the event
   * dispatch thread.
   * @return the number of elements in this task's <code>JList</code>.
   */
  protected Integer executeInEDT() throws Throwable {
    return list.getModel().getSize();
  }
}