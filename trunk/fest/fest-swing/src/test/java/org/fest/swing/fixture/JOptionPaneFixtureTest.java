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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
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
import static org.fest.assertions.Fail.fail;

import static org.fest.swing.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.fixture.ErrorMessages.equalsFailedMessage;

import static org.fest.util.Arrays.array;

import org.fest.swing.ComponentLookupException;
import org.fest.swing.GUITest;
import org.fest.swing.RobotFixture;
import org.fest.swing.TestFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JOptionPaneFixture}</code>.
 *
 * @author Alex Ruiz
 */
@GUITest public class JOptionPaneFixtureTest {

  public static class CustomWindow extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JButton button = new JButton("Click me");
    
    private final ComponentTester tester = new ComponentTester();
    
    CustomWindow(Class testClass) {
      super(testClass);
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
      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
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
  private JOptionPaneFixture fixture;
  private ErrorMessages errorMessages;
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = new CustomWindow(getClass());
    robot.showWindow(window);
  }
  
  @Test public void shouldFindButtonWithGivenTextInOptionPane() {
    window.showMessageWithOptions(array("First", "Second"));
    createFixture();
    JButtonFixture button = fixture.buttonWithText("Second");
    assertThat(button).isNotNull();
    assertThat(button.target.getText()).isEqualTo("Second");
  }

  @Test public void shouldFindTextComponentInOptionPane() {
    window.showInputMessage();
    createFixture();
    JTextComponentFixture textComponentFixture = fixture.textBox();
    assertThat(textComponentFixture).isNotNull();
  }

  @Test(dependsOnMethods = "shouldFindTextComponentInOptionPane", expectedExceptions = ComponentLookupException.class)
  public void shouldNotFindTextComponentInOptionPaneIfNotInputMessage() {
    window.showErrorMessage();
    createFixture();
    fixture.textBox();
  }

  @Test public void shouldPassIfMatchingTitle() {
    window.showMessageWithTitle("Star Wars");
    createFixture();
    fixture.requireTitle("Star Wars");
  }
  
  @Test public void shouldPassIfMatchingTitleWhenOptionPaneCreatedManually() {
    window.showManuallyCreatedOptionPaneWithTitle("Jedi");
    createFixture();
    fixture.requireTitle("Jedi");
  }
  
  @Test(dependsOnMethods = "shouldPassIfMatchingTitle")
  public void shouldFailIfNotMatchingTitle() {
    window.showMessageWithTitle("Yoda");
    createFixture();
    try {
      fixture.requireTitle("Darth Vader");
      fail();
    } catch (AssertionError e) {
      errorMessages.assertIsCorrect(e, "title", equalsFailedMessage("'Darth Vader'", "'Yoda'"));
    }
  }

  @Test public void shouldPassIfMatchingOptions() {
    window.showMessageWithOptions(array("First", "Second"));
    createFixture();
    fixture.requireOptions(array("First", "Second"));
  }

  @Test(dependsOnMethods = "shouldPassIfMatchingOptions")
  public void shouldFailIfNotMatchingOptions() {
    window.showMessageWithOptions(array("First", "Second"));
    createFixture();
    try {
      fixture.requireOptions(array("Third"));
      fail();
    } catch (AssertionError e) {
      errorMessages.assertIsCorrect(e, "options", equalsFailedMessage("['First', 'Second']", "['Third']"));
    }
  }

  @Test public void shouldPassIfMatchingMessage() {
    window.showMessageWithText("Leia");
    createFixture();
    fixture.requireMessage("Leia");
  }
  
  @Test(dependsOnMethods = "shouldPassIfMatchingMessage", expectedExceptions = AssertionError.class)
  public void shouldFailIfNotMatchingMessage() {
    window.showMessageWithText("Palpatine");
    createFixture();
    fixture.requireMessage("Anakin");
  }
  
  @Test public void shouldPassIfExpectedAndActualMessageTypeIsError() {
    window.showErrorMessage();
    createFixture();
    fixture.requireErrorMessage();
  }

  @Test(dependsOnMethods = "shouldPassIfExpectedAndActualMessageTypeIsError", expectedExceptions = AssertionError.class)
  public void shouldFailIfExpectedMessageTypeIsErrorAndActualIsNot() {
    window.showInformationMessage();
    createFixture();
    fixture.requireErrorMessage();
  }

  @Test public void shouldPassIfExpectedAndActualMessageTypeIsInformation() {
    window.showInformationMessage();
    createFixture();
    fixture.requireInformationMessage();
  }

  @Test(dependsOnMethods = "shouldPassIfExpectedAndActualMessageTypeIsInformation", 
        expectedExceptions = AssertionError.class)
  public void shouldFailIfExpectedMessageTypeIsInformationAndActualIsNot() {
    window.showErrorMessage();
    createFixture();
    fixture.requireInformationMessage();
  }

  @Test public void shouldPassIfExpectedAndActualMessageTypeIsWarning() {
    window.showWarningMessage();
    createFixture();
    fixture.requireWarningMessage();
  }
  
  @Test(dependsOnMethods = "shouldPassIfExpectedAndActualMessageTypeIsWarning", 
        expectedExceptions = AssertionError.class)
  public void shouldFailIfExpectedMessageTypeIsWarningAndActualIsNot() {
    window.showErrorMessage();
    createFixture();
    fixture.requireWarningMessage();
  }

  @Test public void shouldPassIfExpectedAndActualMessageTypeIsQuestion() {
    window.showQuestionMessage();
    createFixture();
    fixture.requireQuestionMessage();
  }
  
  @Test(dependsOnMethods = "shouldPassIfExpectedAndActualMessageTypeIsWarning",
        expectedExceptions = AssertionError.class)
  public void shouldFailIfExpectedMessageTypeIsQuestionAndActualIsNot() {
    window.showErrorMessage();
    createFixture();
    fixture.requireQuestionMessage();
  }

  @Test public void shouldPassIfExpectedAndActualMessageTypeIsPlain() {
    window.showPlainMessage();
    createFixture();
    fixture.requirePlainMessage();
  }
  
  @Test(dependsOnMethods = "shouldPassIfExpectedAndActualMessageTypeIsWarning",
        expectedExceptions = AssertionError.class)
  public void shouldFailIfExpectedMessageTypeIsPlainAndActualIsNot() {
    window.showErrorMessage();
    createFixture();
    fixture.requirePlainMessage();
  }
  
  private void createFixture() {
    fixture = new JOptionPaneFixture(robot);
    assertThat(fixture.target).isNotNull();
    errorMessages = new ErrorMessages(fixture.target);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
}
