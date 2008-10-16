/*
 * Created on Aug 11, 2008
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

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.text.JTextComponent;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JSpinnerValueQuery.valueOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.testing.TestGroups.*;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JSpinnerCommitEditTask}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class JSpinnerCommitEditTaskTest {

  private Robot robot;
  private JSpinner spinner;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    spinner = window.spinner;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldCommitEditJSpinner() {
    final JTextComponent editor = spinnerEditor();
    final String text = "Three";
    setTextInEditor(editor, text);
    assertThat(valueOf(spinner)).isNotEqualTo(text);
    JSpinnerCommitEditTask.commitEdit(spinner);
    robot.waitForIdle();
    assertThat(valueOf(spinner)).isEqualTo(text);
  }

  private void setTextInEditor(final JTextComponent editor, final String text) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        editor.setText(text);
      }
    });
    robot.waitForIdle();
  }

  private JTextComponent spinnerEditor() {
    JComponent editor = spinner.getEditor();
    assertThat(editor).isInstanceOf(DefaultEditor.class);
    return ((DefaultEditor)editor).getTextField();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JSpinner spinner = new JSpinner(new SpinnerListModel(array("One", "Two", "Three")));

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(JSpinnerCommitEditTaskTest.class);
      add(spinner);
      setPreferredSize(new Dimension(160, 80));
    }
  }
}
