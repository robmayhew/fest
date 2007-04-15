/*
 * Created on Apr 3, 2007
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
 * Copyright @2007 the original author or authors.
 */
package com.jtuzi.fest.fixture;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.jtuzi.fest.RobotFixture;
import com.jtuzi.fest.fixture.JTabbedPaneFixture;

import static com.jtuzi.fest.assertions.Assertions.assertThat;


import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Unit tests for <code>{@link JTabbedPaneFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTabbedPaneFixtureTest {

  private static class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;
   
    final JTabbedPane tabbedPane = new JTabbedPane();
    
    public MainWindow() {
      setLayout(new FlowLayout());
      setUpComponents();
      addComponents();
    }

    private void setUpComponents() {
      tabbedPane.setName("tabbedPane");
      tabbedPane.addTab("First", new JPanel());
      tabbedPane.addTab("Second", new JPanel());
    }
    
    private void addComponents() {
      add(tabbedPane);
    }
  }
  
  private MainWindow window;
  private RobotFixture robot;
  private JTabbedPaneFixture fixture;
  
  @BeforeClass public void setUp() {
    robot = new RobotFixture();
    window = new MainWindow();
    robot.showWindow(window);
    fixture = new JTabbedPaneFixture(robot, "tabbedPane");
  }
  
  @Test public void shouldHaveFoundTabbedPane() {
    assertThat(fixture.target).isSameAs(window.tabbedPane);
  }

  @Test(dependsOnMethods = "shouldHaveFoundTabbedPane", dataProvider = "tabIndexProvider") 
  public void shouldSelectTabWithGivenIndex(int index) {
    fixture.selectTab(index);
    assertThat(fixture.target.getSelectedIndex()).isEqualTo(index);
  }
  
  @DataProvider(name = "tabIndexProvider")
  public Object[][] tabIndexProvider() {
   return new Object[][] { { 0 }, { 1 } };
  }
  
  @Test(dependsOnMethods = "shouldHaveFoundTabbedPane", dataProvider = "tabTextProvider") 
  public void shouldSelectTabWithGivenText(String tabName, int expectedIndex) {
    fixture.selectTab(tabName);
    assertThat(fixture.target.getSelectedIndex()).isEqualTo(expectedIndex);
  }
  
  @DataProvider(name = "tabTextProvider")
  public Object[][] tabTextProvider() {
   return new Object[][] { { "First", 0 }, { "Second", 1 } };
  }

  @Test(dependsOnMethods = "shouldHaveFoundTabbedPane")
  public void shouldReturnAllTabs() {
    assertThat(fixture.tabTitles()).isEqualTo("First", "Second");
  }
  
  @AfterClass public void tearDown() {
    robot.cleanUp();
  }
}
