/*
 * Created on Mar 24, 2008
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
package org.fest.swing.format;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GuiQuery;
import org.fest.swing.core.GuiTask;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Tests for <code>{@link JTabbedPaneFormatter}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test public class JTabbedPaneFormatterTest {

  private JTabbedPane tabbedPane;
  private JTabbedPaneFormatter formatter;
  
  @BeforeMethod public void setUp() {
    tabbedPane = newTabbedPane();
    formatter = new JTabbedPaneFormatter();
  }
  
  private static JTabbedPane newTabbedPane() {
    return execute(new GuiQuery<JTabbedPane>() {
      protected JTabbedPane executeInEDT() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setName("tabbedPane");
        return tabbedPane;
      }
    });
  }
  
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfComponentIsNotJTabbedPane() {
    formatter.format(new JTextField());
  }

  public void shouldFormatJTabbedPaneWithTabsAndSelection() {
    addTwoTabsTo(tabbedPane);
    setSelectedIndex(tabbedPane, 1);    
    String formatted = formatter.format(tabbedPane);
    assertThat(formatted).contains(tabbedPane.getClass().getName())
                         .contains("name='tabbedPane'")
                         .contains("selectedTabIndex=1")
                         .contains("selectedTabTitle='Two'")
                         .contains("tabCount=2")
                         .contains("tabTitles=['One', 'Two']")
                         .contains("enabled=true")
                         .contains("visible=true")
                         .contains("showing=false");
  }

  public void shouldFormatJTabbedPaneWithTabsAndNoSelection() {
    addTwoTabsTo(tabbedPane);
    setSelectedIndex(tabbedPane, -1);    
    String formatted = formatter.format(tabbedPane);
    assertThat(formatted).contains(tabbedPane.getClass().getName())
                         .contains("name='tabbedPane'")
                         .contains("selectedTabIndex=-1")
                         .contains("selectedTabTitle=<No selection>")
                         .contains("tabCount=2")
                         .contains("tabTitles=['One', 'Two']")
                         .contains("enabled=true")
                         .contains("visible=true")
                         .contains("showing=false");
  }

  private static void addTwoTabsTo(final JTabbedPane tabbedPane) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        tabbedPane.addTab("One", new JPanel());
        tabbedPane.addTab("Two", new JPanel());
      }
    });
  }
  
  private static void setSelectedIndex(final JTabbedPane tabbedPane, final int index) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        tabbedPane.setSelectedIndex(index);
      }
    });
  }

  public void shouldFormatJTabbedPaneWithNoTabs() {
    String formatted = formatter.format(tabbedPane);
    assertThat(formatted).contains(tabbedPane.getClass().getName())
                         .contains("name='tabbedPane'")
                         .contains("selectedTabIndex=-1")
                         .contains("selectedTabTitle=<No selection>")
                         .contains("tabCount=0")
                         .contains("tabTitles=[]")
                         .contains("enabled=true")
                         .contains("visible=true")
                         .contains("showing=false");
  }
}
