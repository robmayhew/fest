/*
 * Created on Jun 12, 2007
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
package org.fest.swing.fixture;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JList;

import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.RobotFixture.robotWithNewAwtHierarchy;

import static org.fest.util.Arrays.array;

import org.fest.swing.RobotFixture;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JListFixture}</code>.
 *
 * @author Alex Ruiz 
 */
public class JListFixtureTest {

  private static class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    final JList list = new JList(array("one", "two", "three"));
    
    MainWindow() {
      setLayout(new FlowLayout());
      setUpComponents();
      addComponents();
    }
    
    private void setUpComponents() {
      list.setName("list");
    }
    
    private void addComponents() {
      add(list);
    }
  }
  
  private MainWindow window;
  private RobotFixture robot;
  private JListFixture fixture;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = new MainWindow();
    robot.showWindow(window);
    fixture = new JListFixture(robot, "list");
  }

  @Test public void shouldHaveFoundList() {
    assertThat(fixture.target).isSameAs(window.list);
  }

  @Test(dependsOnMethods = "shouldHaveFoundList")
  public void shouldReturnListContents() {
    assertThat(fixture.contents()).isEqualTo(array("one", "two", "three"));
  }
  
  @Test(dependsOnMethods = "shouldHaveFoundList")
  public void shouldSelectItemAtGivenIndex() {
    fixture.selectItemAt(2);
    assertThat(window.list.getSelectedValue()).equals("three");
  }

  @Test(dependsOnMethods = "shouldHaveFoundList")
  public void shouldSelectItemWithGivenText() {
    fixture.selectItemWithText("two");
    assertThat(window.list.getSelectedValue()).equals("two");
  }

  @Test(dependsOnMethods = "shouldHaveFoundList")
  public void shouldReturnValueAtGivenIndex() {
    assertThat(fixture.valueAt(2)).isEqualTo("three");
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
}
