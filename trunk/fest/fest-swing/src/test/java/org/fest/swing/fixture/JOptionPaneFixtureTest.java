/*
 * Created on Feb 13, 2007
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
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import abbot.tester.ComponentTester;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showOptionDialog;
import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.RobotFixture.robotWithNewAwtHierarchy;

import static org.fest.util.Arrays.array;

import org.fest.swing.ComponentLookupException;
import org.fest.swing.GUITest;
import org.fest.swing.RobotFixture;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JOptionPaneFixture}</code>.
 *
 * @author Alex Ruiz
 */
@GUITest public class JOptionPaneFixtureTest {

  public static class CustomWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    final JButton button = new JButton("Click me");
    
    private final ComponentTester tester = new ComponentTester();
    
    CustomWindow() {
      setLayout(new FlowLayout());
      add(button);
    }

    void showMessageWithTitle(String title) {
      showMessage("Information", title, INFORMATION_MESSAGE);
    }
    
    void showMessageWithText(String message) {
      showMessage(message, "Title", INFORMATION_MESSAGE);
    }
    
    void showMessageWithOptions(final Object[] options) {
      setActionAndClickButton(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) {
          showOptionDialog(CustomWindow.this, "Message", "Title", YES_NO_OPTION, QUESTION_MESSAGE, null, options,
              options[0]); 
        }
      });
    }

    void showInputMessage() {
      setActionAndClickButton(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) {
          showInputDialog(CustomWindow.this, "Message"); 
        }
      });
    }
    
    void showErrorMessage() { showMessage(ERROR_MESSAGE); }
    void showInformationMessage() { showMessage(INFORMATION_MESSAGE); }
    void showWarningMessage() { showMessage(WARNING_MESSAGE); }
    void showQuestionMessage() { showMessage(QUESTION_MESSAGE); }
    void showPlainMessage() { showMessage(PLAIN_MESSAGE); }

    private void showMessage(final int messageType) {
      showMessage("Text", "Title", messageType);
    }
    
    private void showMessage(final String text, final String title, final int messageType) {
      setActionAndClickButton(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) {
          showMessageDialog(CustomWindow.this, text, title, messageType);
        }
      });
    }
    
    void showManuallyCreatedOptionPaneWithTitle(final String title) {
      setActionAndClickButton(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) {
          JOptionPane optionPane = new JOptionPane("Manually Created");
          JDialog dialog = optionPane.createDialog(CustomWindow.this, title);
          dialog.setVisible(true);      
        }
      });
    }

    private void setActionAndClickButton(MouseListener l) {
      removeAllMouseListeners();
      button.addMouseListener(l);
      clickButton();
    }
    
    private void removeAllMouseListeners() {
      for (MouseListener l : button.getMouseListeners()) button.removeMouseListener(l);
    }
    
    void clickButton() {
      tester.click(button);      
    }    
  }

  private CustomWindow window;
  private RobotFixture robot;
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = new CustomWindow();
    robot.showWindow(window);
  }
  
  @Test public void shouldFindOptionPane() throws Exception {
    window.showErrorMessage();
    JOptionPaneFixture fixture = fixture();
    assertThat(fixture.target).isNotNull();
    fixture.button().click();
  }
  
  @Test(dependsOnMethods = "shouldFindOptionPane")
  public void shouldFindButtonWithGivenTextInOptionPane() {
    window.showMessageWithOptions(array("First", "Second"));
    JOptionPaneFixture fixture = fixture();
    JButtonFixture button = fixture.buttonWithText("Second");
    assertThat(button).isNotNull();
    assertThat(button.target.getText()).isEqualTo("Second");
    fixture.button().click();
  }

  @Test(dependsOnMethods = "shouldFindOptionPane")
  public void shouldFindTextComponentInOptionPane() {
    window.showInputMessage();
    JOptionPaneFixture fixture = fixture();
    JTextComponentFixture textComponentFixture = fixture.textBox();
    assertThat(textComponentFixture).isNotNull();
    fixture.button().click();
  }

  @Test(dependsOnMethods = { "shouldFindOptionPane", "shouldFindTextComponentInOptionPane" },
        expectedExceptions = ComponentLookupException.class)
  public void shouldNotFindTextComponentInOptionPaneIfNotInputMessage() {
    window.showErrorMessage();
    JOptionPaneFixture fixture = fixture();
    fixture.textBox();
    fixture.button().click();
  }

  @Test(dependsOnMethods = "shouldFindOptionPane")
  public void shouldPassIfMatchingTitle() {
    window.showMessageWithTitle("Star Wars");
    JOptionPaneFixture fixture = fixture();
    fixture.requireTitle("Star Wars");
    fixture.button().click();
  }
  
  @Test(dependsOnMethods = "shouldFindOptionPane")
  public void shouldPassIfMatchingTitleWhenOptionPaneCreatedManually() {
    window.showManuallyCreatedOptionPaneWithTitle("Jedi");
    JOptionPaneFixture fixture = fixture();
    fixture.requireTitle("Jedi");
    fixture.button().click();
  }
  
  @Test(dependsOnMethods = { "shouldFindOptionPane", "shouldPassIfMatchingTitle" },
        expectedExceptions = AssertionError.class)
  public void shouldFailIfNotMatchingTitle() {
    window.showMessageWithTitle("Yoda");
    JOptionPaneFixture fixture = fixture();
    fixture.requireTitle("Darth Vader");
    fixture.button().click();
  }

  @Test(dependsOnMethods = "shouldFindOptionPane")
  public void shouldPassIfMatchingOptions() {
    window.showMessageWithOptions(array("First", "Second"));
    JOptionPaneFixture fixture = fixture();
    fixture.requireOptions(array("First", "Second"));
    fixture.button().click();
  }

  @Test(dependsOnMethods = { "shouldFindOptionPane", "shouldPassIfMatchingOptions" },
        expectedExceptions = AssertionError.class)
  public void shouldFailIfNotMatchingOptions() {
    window.showMessageWithOptions(array("First", "Second"));
    JOptionPaneFixture fixture = fixture();
    fixture.requireOptions(array("Third"));
    fixture.button().click();
  }

  @Test(dependsOnMethods = "shouldFindOptionPane")
  public void shouldPassIfMatchingMessage() {
    window.showMessageWithText("Leia");
    JOptionPaneFixture fixture = fixture();
    fixture.requireMessage("Leia");
    fixture.button().click();
  }
  
  @Test(dependsOnMethods = { "shouldFindOptionPane", "shouldPassIfMatchingMessage" },
        expectedExceptions = AssertionError.class)
  public void shouldFailIfNotMatchingMessage() {
    window.showMessageWithText("Palpatine");
    JOptionPaneFixture fixture = fixture();
    fixture.requireMessage("Anakin");
    fixture.button().click();
  }
  
  @Test(dependsOnMethods = "shouldFindOptionPane")
  public void shouldPassIfExpectedAndActualMessageTypeIsError() {
    window.showErrorMessage();
    JOptionPaneFixture fixture = fixture();
    fixture.requireErrorMessage();
    fixture.button().click();
  }

  @Test(dependsOnMethods = { "shouldFindOptionPane", "shouldPassIfExpectedAndActualMessageTypeIsError" },
        expectedExceptions = AssertionError.class)
  public void shouldFailIfExpectedMessageTypeIsErrorAndActualIsNot() {
    window.showInformationMessage();
    JOptionPaneFixture fixture = fixture();
    fixture.requireErrorMessage();
    fixture.button().click();
  }

  @Test(dependsOnMethods = "shouldFindOptionPane")
  public void shouldPassIfExpectedAndActualMessageTypeIsInformation() {
    window.showInformationMessage();
    JOptionPaneFixture fixture = fixture();
    fixture.requireInformationMessage();
    fixture.button().click();
  }

  @Test(dependsOnMethods = { "shouldFindOptionPane", "shouldPassIfExpectedAndActualMessageTypeIsInformation" },
        expectedExceptions = AssertionError.class)
  public void shouldFailIfExpectedMessageTypeIsInformationAndActualIsNot() {
    window.showErrorMessage();
    JOptionPaneFixture fixture = fixture();
    fixture.requireInformationMessage();
    fixture.button().click();
  }

  @Test(dependsOnMethods = "shouldFindOptionPane")
  public void shouldPassIfExpectedAndActualMessageTypeIsWarning() {
    window.showWarningMessage();
    JOptionPaneFixture fixture = fixture();
    fixture.requireWarningMessage();
    fixture.button().click();
  }
  
  @Test(dependsOnMethods = { "shouldFindOptionPane", "shouldPassIfExpectedAndActualMessageTypeIsWarning" },
        expectedExceptions = AssertionError.class)
  public void shouldFailIfExpectedMessageTypeIsWarningAndActualIsNot() {
    window.showErrorMessage();
    JOptionPaneFixture fixture = fixture();
    fixture.requireWarningMessage();
  }

  @Test(dependsOnMethods = "shouldFindOptionPane")
  public void shouldPassIfExpectedAndActualMessageTypeIsQuestion() {
    window.showQuestionMessage();
    JOptionPaneFixture fixture = fixture();
    fixture.requireQuestionMessage();
    fixture.button().click();
  }
  
  @Test(dependsOnMethods = { "shouldFindOptionPane", "shouldPassIfExpectedAndActualMessageTypeIsWarning" },
        expectedExceptions = AssertionError.class)
  public void shouldFailIfExpectedMessageTypeIsQuestionAndActualIsNot() {
    window.showErrorMessage();
    JOptionPaneFixture fixture = fixture();
    fixture.requireQuestionMessage();
    fixture.button().click();
  }

  @Test(dependsOnMethods = "shouldFindOptionPane")
  public void shouldPassIfExpectedAndActualMessageTypeIsPlain() {
    window.showPlainMessage();
    JOptionPaneFixture fixture = fixture();
    fixture.requirePlainMessage();
    fixture.button().click();
  }
  
  @Test(dependsOnMethods = { "shouldFindOptionPane", "shouldPassIfExpectedAndActualMessageTypeIsWarning" },
        expectedExceptions = AssertionError.class)
  public void shouldFailIfExpectedMessageTypeIsPlainAndActualIsNot() {
    window.showErrorMessage();
    JOptionPaneFixture fixture = fixture();
    fixture.requirePlainMessage();
    fixture.button().click();
  }
  
  private JOptionPaneFixture fixture() {
    return new JOptionPaneFixture(robot);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
}
