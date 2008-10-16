/*
 * Created on Apr 12, 2008
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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.testing.CustomCellRenderer;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link BasicJComboBoxCellReader}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class BasicJComboBoxCellReaderTest {

  private Robot robot;
  private JComboBox comboBox;
  private BasicJComboBoxCellReader reader;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    reader = new BasicJComboBoxCellReader();
    MyWindow window = MyWindow.createNew();
    comboBox = window.comboBox;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnModelValueToString() {
    setModelValues(comboBox, array(new Jedi("Yoda")));
    robot.waitForIdle();
    Object value = reader.valueAt(comboBox, 0);
    assertThat(value).isEqualTo("Yoda");
  }

  public void shouldReturnNullIfRendererNotRecognizedAndModelValueIsNull() {
    setModelValues(comboBox, new Object[] { null });
    setRendererComponent(comboBox, new JToolBar());
    robot.waitForIdle();
    Object value = reader.valueAt(comboBox, 0);
    assertThat(value).isNull();
  }

  public void shouldReturnTextFromCellRendererIfRendererIsJLabelAndToStringFromModelReturnedNull() {
    setModelValues(comboBox, array(new Jedi(null)));
    setRendererComponent(comboBox, new JLabel("First"));
    robot.waitForIdle();
    Object value = reader.valueAt(comboBox, 0);
    assertThat(value).isEqualTo("First");
  }

  private static void setModelValues(final JComboBox comboBox, final Object[] values) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        comboBox.setModel(new DefaultComboBoxModel(values));
      }
    });
  }

  private static void setRendererComponent(final JComboBox comboBox, final Component renderer) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        comboBox.setRenderer(new CustomCellRenderer(renderer));
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JComboBox comboBox = new JComboBox(array("First"));

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(BasicJComboBoxCellReaderTest.class);
      addComponents(comboBox);
    }
  }
}
