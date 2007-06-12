/*
 * Created on Feb 9, 2007
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
import javax.swing.JLabel;

import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.RobotFixture.robotWithNewAwtHierarchy;

import org.fest.swing.GUITest;
import org.fest.swing.RobotFixture;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JLabelFixture}</code>.
 *
 * @author Yvonne Wang
 */
@GUITest public class JLabelFixtureTest {

  
  private static class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    final JLabel firstLabel = new JLabel("First Label");
    final JLabel secondLabel = new JLabel("Second Label");
    
    final ComponentEvents secondLabelEvents = ComponentEvents.attachTo(secondLabel);
    
    MainWindow() {
      setLayout(new FlowLayout());
      setUpComponents();
      addComponents();
    }
    
    private void setUpComponents() {
      firstLabel.setName("firstLabel");
      secondLabel.setName("secondLabel");
    }
    
    private void addComponents() {
      add(firstLabel);
      add(secondLabel);
    }
  }
  
  private MainWindow window;
  private RobotFixture robot;
  private JLabelFixture secondLabelFixture;
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = new MainWindow();
    robot.showWindow(window);
    secondLabelFixture = new JLabelFixture(robot, "secondLabel");
  }
  
  @Test public void shouldHaveFoundLabel() {
    assertThat(secondLabelFixture.target).isSameAs(window.secondLabel);
  }
  
  @Test(dependsOnMethods = "shouldHaveFoundLabel") 
  public void shouldClickLabel() {
    secondLabelFixture.click();
    assertThat(window.secondLabelEvents.clicked()).isTrue();
  }
  
  @Test(dependsOnMethods = "shouldHaveFoundLabel") 
  public void shouldGiveFocusToLabel() {
    secondLabelFixture.focus();
    assertThat(window.secondLabel.hasFocus()).isTrue();
  }
  
  @Test(dependsOnMethods = "shouldHaveFoundLabel") 
  public void shouldPassIfLabelHasMatchingText() {
    secondLabelFixture.requireText("Second Label");
  }
  
  @Test(dependsOnMethods = {"shouldHaveFoundLabel", "shouldPassIfLabelHasMatchingText"},
        expectedExceptions = AssertionError.class) 
  public void shouldFailIfLabelHasNotMatchingText() {
    secondLabelFixture.requireText("A Label");
  }
  
  @Test(dependsOnMethods = "shouldHaveFoundLabel") 
  public void shouldReturnLabelText() {
    assertThat(secondLabelFixture.text()).isEqualTo("Second Label");
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

}
