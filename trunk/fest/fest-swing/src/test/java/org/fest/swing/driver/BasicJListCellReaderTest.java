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

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Condition;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.testing.CustomCellRenderer;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.factory.JLabels.label;
import static org.fest.swing.factory.JLists.list;
import static org.fest.swing.factory.JToolBars.toolBar;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link BasicJListCellReader}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class BasicJListCellReaderTest {

  private JList list;
  private BasicJListCellReader reader;

  @BeforeMethod public void setUp() {
    list = list().createNew();
    reader = new BasicJListCellReader();
  }

  public void shouldReturnModelValueToString() {
    setModelValue(list, new Jedi("Yoda"));
    Object value = reader.valueAt(list, 0);
    assertThat(value).isEqualTo("Yoda");
  }

  public void shouldReturnNullIfRendererNotRecognizedAndModelValueIsNull() {
    setModelValue(list, null);
    setRendererComponent(list, toolBar().createNew());
    Object value = reader.valueAt(list, 0);
    assertThat(value).isNull();
  }

  public void shouldReturnTextFromCellRendererIfRendererIsJLabelAndToStringFromModelReturnedNull() {
    setModelValue(list, new Jedi(null));
    JLabel renderer = label().withText("First").createNew();
    setRendererComponent(this.list, renderer);
    Object value = reader.valueAt(this.list, 0);
    assertThat(value).isEqualTo("First");
  }

  private static void setModelValue(final JList list, final Object value) {
    final MyListModel model = new MyListModel(value);
    GuiActionRunner.execute(new GuiTask() {
      protected void executeInEDT() {
        list.setModel(model);
      }
    }, new Condition("JList's model is set") {
      public boolean test() {
        return list.getModel() == model;
      }
    });
  }
  
  private static void setRendererComponent(final JList list, final Component renderer) {
    final CustomCellRenderer cellRenderer = new CustomCellRenderer(renderer);
    execute(new GuiTask() {
      protected void executeInEDT() {
        list.setCellRenderer(cellRenderer);
      }
    }, new Condition("JList's cell renderer is set") {
      public boolean test() {
        return list.getCellRenderer() == cellRenderer;
      }
    });
  }
  
  private static class MyListModel extends DefaultListModel {
    private static final long serialVersionUID = 1L;

    MyListModel(Object... values) {
      for (Object value : values) addElement(value);
    }
  }
}
