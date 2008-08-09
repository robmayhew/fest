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


import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JToolBar;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GuiQuery;
import org.fest.swing.testing.CustomCellRenderer;

import static org.fest.assertions.Assertions.assertThat;
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
    list = new GuiQuery<JList>() {
      protected JList executeInEDT() {
        return new JList();
      }
    }.run();
    reader = new BasicJListCellReader();
  }

  public void shouldReturnModelValueToString() {
    new GuiQuery<Void>() {
      protected Void executeInEDT() {
        DefaultListModel model = new DefaultListModel();
        model.addElement(new Jedi("Yoda"));
        list.setModel(model);
        return null;
      }
    }.run();
    Object value = reader.valueAt(list, 0);
    assertThat(value).isEqualTo("Yoda");
  }

  public void shouldReturnNullIfRendererNotRecognizedAndModelValueIsNull() {
    new GuiQuery<Void>() {
      protected Void executeInEDT() {
        DefaultListModel model = new DefaultListModel();
        model.addElement(null);
        list.setModel(model);
        list.setCellRenderer(new CustomCellRenderer(new JToolBar()));
        return null;
      }
    }.run();
    Object value = reader.valueAt(list, 0);
    assertThat(value).isNull();
  }

  public void shouldReturnTextFromCellRendererIfRendererIsJLabelAndToStringFromModelReturnedNull() {
    new GuiQuery<Void>() {
      protected Void executeInEDT() {
        DefaultListModel model = new DefaultListModel();
        model.addElement(new Jedi(null));
        list.setModel(model);
        list.setCellRenderer(new CustomCellRenderer(new JLabel("First")));
        return null;
      }
    }.run();
    Object value = reader.valueAt(list, 0);
    assertThat(value).isEqualTo("First");
  }
}
