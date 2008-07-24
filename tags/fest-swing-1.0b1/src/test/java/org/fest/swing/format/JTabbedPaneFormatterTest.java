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

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link JTabbedPaneFormatter}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JTabbedPaneFormatterTest {

  private JTabbedPane tabbedPane;
  private JTabbedPaneFormatter formatter;
  
  @BeforeMethod public void setUp() {
    tabbedPane = new JTabbedPane();
    tabbedPane.setName("tabbedPane");
    formatter = new JTabbedPaneFormatter();
  }
  
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfComponentIsNotJTabbedPane() {
    formatter.format(new JTextField());
  }

  @Test public void shouldFormatJTabbedPaneWithTabsAndSelection() {
    tabbedPane.addTab("First", new JPanel());
    tabbedPane.addTab("Second", new JPanel());
    tabbedPane.setSelectedIndex(1);    
    String formatted = formatter.format(tabbedPane);
    assertThat(formatted).contains(tabbedPane.getClass().getName())
                         .contains("name='tabbedPane'")
                         .contains("selectedTabIndex=1")
                         .contains("selectedTabTitle='Second'")
                         .contains("tabCount=2")
                         .contains("tabTitles=['First', 'Second']")
                         .contains("enabled=true")
                         .contains("visible=true")
                         .contains("showing=false");
  }

  @Test public void shouldFormatJTabbedPaneWithTabsAndNoSelection() {
    tabbedPane.addTab("First", new JPanel());
    tabbedPane.addTab("Second", new JPanel());
    tabbedPane.setSelectedIndex(-1);    
    String formatted = formatter.format(tabbedPane);
    assertThat(formatted).contains(tabbedPane.getClass().getName())
                         .contains("name='tabbedPane'")
                         .contains("selectedTabIndex=-1")
                         .contains("selectedTabTitle=<No selection>")
                         .contains("tabCount=2")
                         .contains("tabTitles=['First', 'Second']")
                         .contains("enabled=true")
                         .contains("visible=true")
                         .contains("showing=false");
  }

  @Test public void shouldFormatJTabbedPaneWithNoTabs() {
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
