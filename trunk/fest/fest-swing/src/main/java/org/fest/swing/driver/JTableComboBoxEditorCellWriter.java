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

import javax.swing.JComboBox;
import javax.swing.JTable;

import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.core.Robot;

/**
 * Understands an implementation of <code>{@link JTableCellWriter}</code> that knows how to use
 * <code>{@link JComboBox}</code>es as cell editors.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableComboBoxEditorCellWriter extends AbstractJTableCellWriter {

  private final JComboBoxDriver driver;

  public JTableComboBoxEditorCellWriter(Robot robot) {
    super(robot);
    driver = new JComboBoxDriver(robot);
  }

  /** ${@inheritDoc} */
  public void enterValue(JTable table, int row, int column, String value) {
    JComboBox editor = editor(table, row, column);
    startEditing(table, row, column);
    driver.selectItem(editor, value);
  }

  /** ${@inheritDoc} */
  public void startCellEditing(JTable table, int row, int column) {
    editor(table, row, column);
    startEditing(table, row, column);
  }

  private void startEditing(JTable table, int row, int column) {
    clickCell(table, row, column);
  }

  /** ${@inheritDoc} */
  public void stopCellEditing(JTable table, int row, int column) {
    editor(table, row, column);
  }

  /** ${@inheritDoc} */
  public void cancelCellEditing(JTable table, int row, int column) {
    editor(table, row, column);
    robot.invokeAndWait(new CancelTableCellEditingTask(table, row, column));
  }

  private JComboBox editor(JTable table, int row, int column) {
    Component editor = editorForCell(table, row, column);
    if (editor instanceof JComboBox) return (JComboBox)editor;
    throw cannotHandleEditor(editor);
  }
}
