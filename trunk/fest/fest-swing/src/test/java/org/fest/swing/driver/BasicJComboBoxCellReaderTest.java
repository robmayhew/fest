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


import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GuiTask;
import org.fest.swing.testing.CustomCellRenderer;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link BasicJComboBoxCellReader}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class BasicJComboBoxCellReaderTest {

  private JComboBox comboBox;
  private BasicJComboBoxCellReader reader;

  @BeforeMethod public void setUp() {
    comboBox = new GuiTask<JComboBox>() {
      protected JComboBox executeInEDT() {
        return new JComboBox(new Object[] { "First" });
      }
    }.run();
    reader = new BasicJComboBoxCellReader();
  }

  public void shouldReturnModelValueToString() {
    new GuiTask<Void>() {
      protected Void executeInEDT() {
        comboBox.setModel(new DefaultComboBoxModel(array(new Jedi("Yoda"))));
        return null;
      }
    }.run();
    Object value = reader.valueAt(comboBox, 0);
    assertThat(value).isEqualTo("Yoda");
  }

  public void shouldReturnNullIfRendererNotRecognizedAndModelValueIsNull() {
    new GuiTask<Void>() {
      protected Void executeInEDT() {
        comboBox.setModel(new DefaultComboBoxModel(new Object[] { null }));
        comboBox.setRenderer(new CustomCellRenderer(new JToolBar()));
        return null;
      }
    }.run();
    Object value = reader.valueAt(comboBox, 0);
    assertThat(value).isNull();
  }

  public void shouldReturnTextFromCellRendererIfRendererIsJLabelAndToStringFromModelReturnedNull() {
    new GuiTask<Void>() {
      protected Void executeInEDT() {
        comboBox.setModel(new DefaultComboBoxModel(array(new Jedi(null))));
        comboBox.setRenderer(new CustomCellRenderer(new JLabel("First")));
        return null;
      }
    }.run();
    Object value = reader.valueAt(comboBox, 0);
    assertThat(value).isEqualTo("First");
  }
}
