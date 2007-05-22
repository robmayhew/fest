/*
 * Created on Feb 8, 2007
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.RobotFixture.robotWithNewAwtHierarchy;

import org.fest.swing.Condition;
import org.fest.swing.GUITest;
import org.fest.swing.RobotFixture;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JButtonFixture}</code>.
 *
 * @author Yvonne Wang
 */
@GUITest public class JButtonFixtureTest {

  private static class CustomButton extends JButton {
    private static final long serialVersionUID = 1L;

    private boolean wasClicked;
    
    CustomButton(String text) {
      super(text);
      addMouseListener(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) { wasClicked = true; }
      });      
    }
    
    boolean wasClicked() { return wasClicked; }
  }
  
  private static class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    final JButton firstButton = new JButton("First Button");
    final CustomButton secondButton = new CustomButton("Second Button");
    
    MainWindow() {
      setLayout(new FlowLayout());
      setUpComponents();
      addComponents();
    }
    
    private void setUpComponents() {
      firstButton.setName("firstButton");
      secondButton.setName("secondButton");
    }
    
    private void addComponents() {
      add(firstButton);
      add(secondButton);
    }
  }
  
  private MainWindow window;
  private RobotFixture robot;
  private JButtonFixture fixture;
  
  @BeforeClass public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = new MainWindow();
    robot.showWindow(window);
    fixture = new JButtonFixture(robot, "secondButton");
  }
  
  @Test public void shouldHaveFoundButton() {
    assertThat(fixture.target).isSameAs(window.secondButton);
  }
  
  @Test(dependsOnMethods = "shouldHaveFoundButton") 
  public void shouldClickButton() {
    fixture.click();
    assertThat(window.secondButton.wasClicked()).isTrue();
  }
  
  @Test(dependsOnMethods = "shouldHaveFoundButton") 
  public void shouldGiveFocusToButton() {
    window.firstButton.requestFocusInWindow();
    robot.wait(new Condition("button has focus") {
      public boolean test() {
        return window.firstButton.hasFocus();
      }
    });
    fixture.focus();
    assertThat(window.secondButton.hasFocus()).isTrue();
  }
  
  @Test(dependsOnMethods = "shouldHaveFoundButton") 
  public void shouldPassIfButtonHasMatchingText() {
    fixture.requireText("Second Button");
  }
  
  @Test(dependsOnMethods = {"shouldHaveFoundButton", "shouldPassIfButtonHasMatchingText"},
        expectedExceptions = AssertionError.class) 
  public void shouldFailIfButtonHasNotMatchingText() {
    fixture.requireText("A Button");
  }
  
  @Test(dependsOnMethods = "shouldHaveFoundButton") 
  public void shouldReturnButtonText() {
    assertThat(fixture.text()).isEqualTo("Second Button");
  }
  
  @AfterClass public void tearDown() {
    robot.cleanUp();
  }
}
