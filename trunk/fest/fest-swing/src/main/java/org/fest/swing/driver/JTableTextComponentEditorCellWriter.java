/*
 * Created on Jun 10, 2008
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

import javax.swing.JTable;
import javax.swing.text.JTextComponent;

import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.core.Robot;

import static java.awt.event.KeyEvent.VK_ENTER;

import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.driver.JTableCancelCellEditingTask.cancelCellEditingOf;

/**
 * Understands an implementation of <code>{@link JTableCellWriter}</code> that knows how to use
 * <code>{@link JTextComponent}</code>s as cell editors.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableTextComponentEditorCellWriter extends AbstractJTableCellWriter {

  private final JTextComponentDriver driver;

  public JTableTextComponentEditorCellWriter(Robot robot) {
    super(robot);
    driver = new JTextComponentDriver(robot);
  }

  /** {@inheritDoc} */
  public void enterValue(JTable table, int row, int column, String value) {
    JTextComponent editor = editor(table, row, column);
    startCellEditing(table, row, column);
    driver.replaceText(editor, value);
    stopEditing(editor);
  }

  /** {@inheritDoc} */
  public void startCellEditing(JTable table, int row, int column) {
    JTextComponent editor = editor(table, row, column);
    clickCell(table, row, column, 2);
    pause(new WaitForComponentToShowCondition(editor));
  }

  /** {@inheritDoc} */
  public void stopCellEditing(JTable table, int row, int column) {
    stopEditing(editor(table, row, column));
  }

  private void stopEditing(JTextComponent editor) {
    driver.pressAndReleaseKeys(editor, VK_ENTER);
  }

  /** {@inheritDoc} */
  public void cancelCellEditing(JTable table, int row, int column) {
    editor(table, row, column);
    robot.invokeAndWait(cancelCellEditingOf(table, row, column));
  }

  private JTextComponent editor(JTable table, int row, int column) {
    Component editor = editorForCell(table, row, column);
    if (editor instanceof JTextComponent) return (JTextComponent)editor;
    throw cannotHandleEditor(editor);
  }
}
