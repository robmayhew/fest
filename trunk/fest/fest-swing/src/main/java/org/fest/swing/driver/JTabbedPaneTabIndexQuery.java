/*
 * Created on Aug 22, 2008
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

import javax.swing.JTabbedPane;

import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.util.Strings.match;

/**
 * Understands an action, executed in the event dispatch thread, that returns the index of a tab (in a
 * <code>{@link JTabbedPane}</code>) whose title matches the given text. This action returns -1 if a matching tab could 
 * not be found.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JTabbedPaneTabIndexQuery extends GuiQuery<Integer> {

  private final JTabbedPane tabbedPane;
  private final String title;

  static int indexOfTab(JTabbedPane tabbedPane, String title) {
    return execute(new JTabbedPaneTabIndexQuery(tabbedPane, title));
  }
  
  JTabbedPaneTabIndexQuery(JTabbedPane tabbedPane, String title) {
    this.title = title;
    this.tabbedPane = tabbedPane;
  }

  protected Integer executeInEDT() {
    int tabCount = tabbedPane.getTabCount();
    for (int i = 0; i < tabCount; i++)
      if (match(title, tabbedPane.getTitleAt(i))) return i;
    return -1;
  }
}