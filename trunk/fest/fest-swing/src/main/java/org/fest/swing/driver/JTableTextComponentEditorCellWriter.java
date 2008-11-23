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

import java.awt.Point;

import javax.swing.JTable;
import javax.swing.text.JTextComponent;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.util.Pair;

import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.driver.ComponentShownWaiter.waitTillShown;
import static org.fest.swing.edt.GuiActionRunner.execute;

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
  @RunsInEDT
  public void enterValue(JTable table, int row, int column, String value) {
    JTextComponent editor = doStartCellEditing(table, row, column);
    driver.replaceText(editor, value);
    stopCellEditing(table, row, column);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  public void startCellEditing(JTable table, int row, int column) {
    doStartCellEditing(table, row, column);
  }

  @RunsInEDT
  private JTextComponent doStartCellEditing(JTable table, int row, int column) {
    Pair<Point, JTextComponent> info = startEditingCellInfo(table, row, column, location);
    robot.click(table, info.i, LEFT_BUTTON, 2); // activate JTextComponent editor
    JTextComponent editor = info.ii;
    waitTillShown(editor);
    return editor;
  }

  @RunsInEDT
  private static Pair<Point, JTextComponent> startEditingCellInfo(final JTable table, final int row, final int column, 
      final JTableLocation location) {
    return execute(new GuiQuery<Pair<Point, JTextComponent>>() {
      protected Pair<Point, JTextComponent> executeInEDT() {
        JTextComponent editor = editor(table, row, column, JTextComponent.class);
        scrollToCell(table, row, column, location);
        return new Pair<Point, JTextComponent>(location.pointAt(table, row, column), editor);
      }
    });
  }
}
