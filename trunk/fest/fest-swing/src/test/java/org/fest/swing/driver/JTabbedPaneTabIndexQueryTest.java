/*
 * Created on Aug 22, 2008
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

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.core.Condition;
import org.fest.swing.testing.TestWindow;

import static javax.swing.SwingUtilities.invokeLater;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JTabbedPaneTabIndexQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, EDT_ACTION })
public class JTabbedPaneTabIndexQueryTest {

  private MyWindow window;
  
  @BeforeMethod public void setUp() {
    window = MyWindow.createAndShow();
  }
  
  @AfterMethod public void tearDown() {
    window.destroy();
  }
  
  @Test(dataProvider = "tabTitlesAndIndices", groups = { GUI, EDT_ACTION })
  public void shouldReturnIndexForTab(String tabTitle, int expectedIndex) {
    int index = JTabbedPaneTabIndexQuery.indexOfTab(window.tabbedPane, tabTitle);
    assertThat(index).isEqualTo(expectedIndex);
  }
  
  @DataProvider(name = "tabTitlesAndIndices") public Object[][] tabTitlesAndIndices() {
    return new Object[][] {
        { "First" , 0 },  
        { "Second", 1 },  
        { "Third" , 2 },  
    };
  }
  
  public void shouldNotFindIndexIfTitleIsNotMatching() {
    int index = JTabbedPaneTabIndexQuery.indexOfTab(window.tabbedPane, "Hello");
    assertThat(index).isEqualTo(-1);
  }

  public void shouldNotFindIndexIfTabbedPaneHasNoTabs() {
    removeAllTabsIn(window.tabbedPane);
    int index = JTabbedPaneTabIndexQuery.indexOfTab(window.tabbedPane, "First");
    assertThat(index).isEqualTo(-1);
  }

  private static void removeAllTabsIn(final JTabbedPane tabbedPane) {
    invokeLater(new Runnable() {
      public void run() {
        tabbedPane.removeAll();
      }
    });
    pause(new Condition("JTabbedPane has not tabs") {
      public boolean test() {
        return tabbedPane.getTabCount() == 0;
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JTabbedPane tabbedPane = new JTabbedPane();
    
    static MyWindow createAndShow() {
      MyWindow window = new MyWindow();
      window.display();
      return window;
    }
    
    private MyWindow() {
      super(JTabbedPaneTabIndexQueryTest.class);
      tabbedPane.addTab("First", new JPanel());
      tabbedPane.addTab("Second", new JPanel());
      tabbedPane.addTab("Third", new JPanel());
      addComponents(tabbedPane);
    }
  }
}
