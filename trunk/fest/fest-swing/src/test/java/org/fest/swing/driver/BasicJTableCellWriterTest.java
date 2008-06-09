/*
 * Created on Jun 8, 2008
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
import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.testing.TableDialogEditDemo;
import org.fest.swing.testing.TestFrame;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link BasicJTableCellWriter}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class BasicJTableCellWriterTest {

  private Robot robot;
  private MyFrame frame;
  private BasicJTableCellWriter writer;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    writer = new BasicJTableCellWriter(robot);
    frame = new MyFrame();
    robot.showWindow(frame, new Dimension(500, 100));
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(dataProvider = "cellEditors")
  public void shouldReturnEditorForCell(int row, int column, Class<Component> editorType) {
    Component editor = writer.editorForCell(frame.table, row, column);
    assertThat(editor).isNotNull().isInstanceOf(editorType);
  }

  @DataProvider(name = "cellEditors")
  public Object[][] cellEditors() {
    return new Object[][] {
        { 0, 2, JComboBox.class },
        { 0, 3, JTextField.class },
        { 0, 4, JCheckBox.class }
    };
  }

  @Test public void shouldThrowErrorIfEditorComponentCannotBeHandled() {
    try {
      writer.enterValue(frame.table, 0, 1, "hello");
      fail();
    } catch (ActionFailedException e) {
      assertThat(e).message().contains("Unable to handle editor component of type javax.swing.JButton");
    }
  }
  
  @Test public void shouldSelectItemInComboBoxEditor() {
    writer.enterValue(frame.table, 0, 2, "Pool");
    assertThat(valueAt(0, 2)).isEqualTo("Pool");
  }

  @Test public void shouldSelectItemInCheckBoxEditor() {
    writer.enterValue(frame.table, 0, 4, "false");
    assertThat(valueAt(0,4)).isEqualTo(false);
    writer.enterValue(frame.table, 0, 4, "true");
    assertThat(valueAt(0,4)).isEqualTo(true);
    writer.enterValue(frame.table, 0, 4, "false");
    assertThat(valueAt(0,4)).isEqualTo(false);
  }

  @Test public void shouldEnterTextInTextComponentEditor() {
    writer.enterValue(frame.table, 4, 3, "8");
    assertThat(valueAt(4, 3)).isEqualTo(8);
  }

  private Object valueAt(int row, int column) {
    return frame.table.getValueAt(row, column);
  }

  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JTable table;

    public MyFrame() {
      super(BasicJTableCellWriterTest.class);
      TableDialogEditDemo newContentPane = new TableDialogEditDemo();
      table = newContentPane.table;
      newContentPane.setOpaque(true); // content panes must be opaque
      setContentPane(newContentPane);
    }
  }

}
