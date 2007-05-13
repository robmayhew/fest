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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JTextField;

import static java.awt.GridBagConstraints.HORIZONTAL;
import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.Condition;
import org.fest.swing.RobotFixture;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTextComponentFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTextComponentFixtureTest {

  private static class CustomTextField extends JTextField {
    private static final long serialVersionUID = 1L;

    private boolean wasClicked;
    
    CustomTextField() {
      addMouseListener(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) { wasClicked = true; }
      });      
    }
    
    boolean wasClicked() { return wasClicked; }
  }
  
  private static class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    final JTextField firstTextField = new JTextField();
    final CustomTextField secondTextField = new CustomTextField();
    
    MainWindow() {
      setLayout(new GridBagLayout());
      setUpComponents();
      addComponents();
    }
    
    private void setUpComponents() {
      firstTextField.setName("firstTextField");
      firstTextField.setColumns(20);
      secondTextField.setName("secondTextField");
      secondTextField.setColumns(20);
    }

    private void addComponents() {
      GridBagConstraints c = new GridBagConstraints();
      c.gridx = c.gridy = 0;
      c.fill = HORIZONTAL;
      add(firstTextField, c);
      c.gridy++;
      add(secondTextField, c);
    }
  }

  private MainWindow window;
  private RobotFixture robot;
  private JTextComponentFixture fixtureForSecondTextField;
  
  @BeforeClass public void setUp() throws Exception {
    robot = new RobotFixture();
    window = new MainWindow();
    robot.showWindow(window);
    fixtureForSecondTextField = new JTextComponentFixture(robot, "secondTextField");
  }
  
  @Test public void shouldHaveFoundTextField() {
    assertThat(fixtureForSecondTextField.target).isSameAs(window.secondTextField);
  }

  @Test(dependsOnMethods = "shouldHaveFoundTextField") 
  public void shouldClickSecondTextField() {
    fixtureForSecondTextField.click();
    assertThat(window.secondTextField.wasClicked()).isTrue();
  }
  
  @Test(dependsOnMethods = "shouldHaveFoundTextField") 
  public void shouldGiveFocusToTextField() {
    window.firstTextField.requestFocusInWindow();
    robot.wait(new Condition("text field has focus") {
      public boolean test() {
        return window.firstTextField.hasFocus();
      }
    });
    fixtureForSecondTextField.focus();
    assertThat(window.secondTextField.hasFocus()).isTrue();
  }
  
  @Test(dependsOnMethods = "shouldHaveFoundTextField") 
  public void shouldPassIfTextFieldHasMatchingText() {
    window.secondTextField.setText("Second Text Field");
    fixtureForSecondTextField.requireText("Second Text Field");
  }
  
  @Test(dependsOnMethods = {"shouldHaveFoundTextField", "shouldPassIfTextFieldHasMatchingText"}, 
        expectedExceptions = AssertionError.class)  
  public void shouldFailIfTextFieldHasNotMatchingText() {
    fixtureForSecondTextField.requireText("A Text Field");
  }
  
  @Test(dependsOnMethods = "shouldHaveFoundTextField") 
  public void shouldReturnTextFieldText() {
    window.secondTextField.setText("Second Text Field");
    assertThat("Second Text Field").isEqualTo(fixtureForSecondTextField.text());
  }

  @Test(dependsOnMethods = "shouldHaveFoundTextField") 
  public void shouldPassIfTextFieldIsEmpty() {
    window.secondTextField.setText("");
    fixtureForSecondTextField.requireEmpty();
  }

  @Test(dependsOnMethods = { "shouldHaveFoundTextField", "shouldPassIfTextFieldIsEmpty" }, 
        expectedExceptions = AssertionError.class) 
  public void shouldFailIfTextFieldIsNotEmpty() {
    window.secondTextField.setText("Some text");
    fixtureForSecondTextField.requireEmpty();
  }
  
  @Test(dependsOnMethods = "shouldHaveFoundTextField") 
  public void shouldEnterTextInTextField() {
    window.secondTextField.setText("");
    fixtureForSecondTextField.enterText("Text entered by Abbot");
    assertThat(window.secondTextField.getText()).isEqualTo("Text entered by Abbot");
  }
  
  @Test(dependsOnMethods = "shouldHaveFoundTextField") 
  public void shouldDeleteTextInTextField() {
    window.secondTextField.setText("Some text to delete");
    fixtureForSecondTextField.deleteText();
    assertThat(window.secondTextField.getText()).isEmpty();
  }
  
  @Test(dependsOnMethods = "shouldHaveFoundTextField") 
  public void shouldSelectAllTextInTextField() {
    window.secondTextField.setText("Some text to select");
    fixtureForSecondTextField.selectAll();
    assertThat(window.secondTextField.getSelectedText()).isEqualTo("Some text to select");
  }
  
  @Test(dependsOnMethods = { "shouldHaveFoundTextField", "shouldSelectAllTextInTextField" }) 
  public void shouldNotSelectAllTextIfTextFieldIsEmpty() {
    window.secondTextField.setText("");
    fixtureForSecondTextField.selectAll();
    assertThat(window.secondTextField.getSelectedText()).isEmpty();
  }
  
  @AfterClass public void tearDown() {
    robot.cleanUp();
  }
}
