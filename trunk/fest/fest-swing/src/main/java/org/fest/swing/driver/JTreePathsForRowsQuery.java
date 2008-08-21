/*
 * Created on Aug 20, 2008
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

import java.awt.Rectangle;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the <code>{@link Rectangle}</code> that
 * the node at the specified row (in a <code>{@link JTree}</code>) is drawn.
 *
 * @author Yvonne Wang
 */
class JTreePathsForRowsQuery extends GuiQuery<TreePath[]> {

  private final JTree tree;
  private final int[] rows;

  static TreePath[] pathsForRows(JTree tree, int[] rows) {
    return execute(new JTreePathsForRowsQuery(tree, rows));
  }

  static TreePath pathForRow(JTree tree, int row) {
    TreePath[] paths = execute(new JTreePathsForRowsQuery(tree, row));
    return paths[0];
  }

  private JTreePathsForRowsQuery(JTree tree, int... rows) {
    this.tree = tree;
    this.rows = rows;
  }

  protected TreePath[] executeInEDT() throws Throwable {
    int rowCount = rows.length;
    TreePath[] paths = new TreePath[rowCount];
    for (int i = 0; i < rowCount; i++)
      paths[i] = tree.getPathForRow(rows[i]);
    return paths;
  }

}
