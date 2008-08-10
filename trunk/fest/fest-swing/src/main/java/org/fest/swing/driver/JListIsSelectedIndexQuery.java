/*
 * Created on Aug 8, 2008
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

import javax.swing.JList;

import org.fest.swing.core.GuiQuery;

/**
 * Understands an action, executed in the event dispatch thread, that indicates whether a cell in a
 * <code>{@link JList}</code> is selected or not.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class JListIsSelectedIndexQuery extends GuiQuery<Boolean> {

  private final JList list;
  private final int index;

  static boolean isSelectedIndex(JList list, int index) {
    return new JListIsSelectedIndexQuery(list, index).run();
  }

  private JListIsSelectedIndexQuery(JList list, int index) {
    this.list = list;
    this.index = index;
  }

  protected Boolean executeInEDT() throws Throwable {
    return list.isSelectedIndex(index);
  }
}