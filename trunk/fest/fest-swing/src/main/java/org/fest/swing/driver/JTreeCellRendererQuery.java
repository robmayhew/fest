/*
 * Created on Aug 7, 2008
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

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the <code>{@link Component}</code> used as
 * list renderer for a particular cell in a <code>{@link JTree}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class JTreeCellRendererQuery extends GuiQuery<Component> {

  private final Object modelValue;
  private final JTree tree;

  static Component cellRendererIn(JTree tree, Object modelValue) {
    return execute(new JTreeCellRendererQuery(tree, modelValue));
  }

  private JTreeCellRendererQuery(JTree tree, Object modelValue) {
    this.modelValue = modelValue;
    this.tree = tree;
  }

  protected Component executeInEDT() {
    TreeCellRenderer r = tree.getCellRenderer();
    return r.getTreeCellRendererComponent(tree, modelValue, false, false, false, 0, false);
  }
}