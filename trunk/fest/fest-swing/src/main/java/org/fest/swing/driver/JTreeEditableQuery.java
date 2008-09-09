package org.fest.swing.driver;

import javax.swing.JTree;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that indicates whether a <code>{@link JTree}</code> 
 * is editable or not.
 *
 * @author Alex Ruiz 
 */
class JTreeEditableQuery extends GuiQuery<Boolean> {
  
  private final JTree tree;

  static boolean isEditable(final JTree tree) {
    return execute(new JTreeEditableQuery(tree));
  }
  
  JTreeEditableQuery(JTree tree) {
    this.tree = tree;
  }

  protected Boolean executeInEDT() {
    return tree.isEditable();
  }
}