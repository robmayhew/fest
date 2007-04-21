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

import org.fest.swing.Condition;
import org.fest.swing.RobotFixture;
import org.fest.swing.fixture.JButtonFixture;



import static org.fest.swing.assertions.Assertions.assertThat;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Unit tests for <code>{@link JButtonFixture}</code>.
 *
 * @author Yvonne Wang
 */
public class JButtonFixtureTest {

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
  private JButtonFixture fixtureForSecondButton;
  
  @BeforeClass public void setUp() {
    robot = new RobotFixture();
    window = new MainWindow();
    robot.showWindow(window);
    fixtureForSecondButton = new JButtonFixture(robot, "secondButton");
  }
  
  @Test public void shouldHaveFoundButton() {
    assertThat(fixtureForSecondButton.target).isSameAs(window.secondButton);
  }
  
  @Test(dependsOnMethods = "shouldHaveFoundButton") 
  public void shouldClickButton() {
    fixtureForSecondButton.click();
    assertTrue(window.secondButton.wasClicked());
  }
  
  @Test(dependsOnMethods = "shouldHaveFoundButton") 
  public void shouldGiveFocusToButton() {
    window.firstButton.requestFocusInWindow();
    robot.wait(new Condition("button has focus") {
      public boolean test() {
        return window.firstButton.hasFocus();
      }
    });
    fixtureForSecondButton.focus();
    assertTrue(window.secondButton.hasFocus());
  }
  
  @Test(dependsOnMethods = "shouldHaveFoundButton") 
  public void shouldPassIfButtonHasMatchingText() {
    fixtureForSecondButton.shouldHaveThisText("Second Button");
  }
  
  @Test(dependsOnMethods = {"shouldHaveFoundButton", "shouldPassIfButtonHasMatchingText"},
        expectedExceptions = AssertionError.class) 
  public void shouldFailIfButtonHasNotMatchingText() {
    fixtureForSecondButton.shouldHaveThisText("A Button");
  }
  
  @Test(dependsOnMethods = "shouldHaveFoundButton") 
  public void shouldReturnButtonText() {
    assertThat(fixtureForSecondButton.text()).isEqualTo("Second Button");
  }
  
  @AfterClass public void tearDown() {
    robot.cleanUp();
  }
}
