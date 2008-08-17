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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GuiQuery;
import org.fest.swing.core.GuiTask;
import org.fest.swing.testing.CustomCellRenderer;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
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
    comboBox = newComboBox();
    reader = new BasicJComboBoxCellReader();
  }

  private static JComboBox newComboBox() {
    return execute(new GuiQuery<JComboBox>() {
      protected JComboBox executeInEDT() {
        return new JComboBox(new Object[] { "First" });
      }
    });
  }

  public void shouldReturnModelValueToString() {
    setModelValues(comboBox, array(new Jedi("Yoda")));
    Object value = reader.valueAt(comboBox, 0);
    assertThat(value).isEqualTo("Yoda");
  }

  public void shouldReturnNullIfRendererNotRecognizedAndModelValueIsNull() {
    setModelValues(comboBox, new Object[] { null });
    setRendererComponent(comboBox, new JToolBar());
    Object value = reader.valueAt(comboBox, 0);
    assertThat(value).isNull();
  }

  public void shouldReturnTextFromCellRendererIfRendererIsJLabelAndToStringFromModelReturnedNull() {
    setModelValues(comboBox, array(new Jedi(null)));
    setRendererComponent(comboBox, new JLabel("First"));
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
      protected void executeInEDT() throws Throwable {
        comboBox.setRenderer(new CustomCellRenderer(renderer));
      }
    });
  }
}
