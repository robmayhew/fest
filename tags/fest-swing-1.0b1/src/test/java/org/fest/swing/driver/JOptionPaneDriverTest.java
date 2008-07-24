/*
 * Created on Mar 11, 2008
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.driver;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.text.JTextComponent;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.testing.TestWindow;

import static javax.swing.JOptionPane.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JOptionPane}</code>.
 * 
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JOptionPaneDriverTest {

  private Robot robot;
  private JOptionPaneDriver driver;
  private MyFrame frame;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JOptionPaneDriver(robot);
    frame = new MyFrame(robot);
    robot.showWindow(frame);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldFindButtonWithGivenTextInOptionPane() {
    JOptionPane optionPane = frame.showMessageWithOptions(array("First", "Second"));
    JButton button = driver.buttonWithText(optionPane, "Second");
    assertThat(button.getText()).isEqualTo("Second");
  }

  public void shouldFindOKButton() {
    JOptionPane optionPane = frame.showInformationMessage();
    JButton button = driver.okButton(optionPane);
    assertThatButtonHasTextFromUIManager(button, "OptionPane.okButtonText");
  }

  public void shouldFindCancelButton() {
    JOptionPane optionPane = frame.showInputMessage();
    JButton button = driver.cancelButton(optionPane);
    assertThatButtonHasTextFromUIManager(button, "OptionPane.cancelButtonText");
  }

  public void shouldFindYesButton() {
    JOptionPane optionPane = frame.showConfirmMessage();
    JButton button = driver.yesButton(optionPane);
    assertThatButtonHasTextFromUIManager(button, "OptionPane.yesButtonText");
  }

  public void shouldFindNoButton() {
    JOptionPane optionPane = frame.showConfirmMessage();
    JButton button = driver.noButton(optionPane);
    assertThatButtonHasTextFromUIManager(button, "OptionPane.noButtonText");
  }

  private void assertThatButtonHasTextFromUIManager(JButton button, String textKey) {
    String expected = UIManager.getString(textKey);
    assertThat(button.getText()).isEqualTo(expected);
  }
  
  public void shouldFindTextComponentInOptionPane() {
    JOptionPane optionPane = frame.showInputMessage();
    JTextComponent textBox = driver.textBox(optionPane);
    assertThat(textBox).isNotNull();
  }

  @Test(groups = GUI, expectedExceptions = ComponentLookupException.class) 
  public void shouldNotFindTextComponentInOptionPaneIfNotInputMessage() {
    JOptionPane optionPane = frame.showErrorMessage();
    driver.textBox(optionPane);
  }

  public void shouldPassIfMatchingTitle() {
    JOptionPane optionPane = frame.showMessageWithTitle("Star Wars");
    driver.requireTitle(optionPane, "Star Wars");
  }

  public void shouldPassIfMatchingTitleWhenOptionPaneCreatedManually() {
    JOptionPane optionPane = frame.showManuallyCreatedOptionPaneWithTitle("Jedi");
    driver.requireTitle(optionPane, "Jedi");
  }

  public void shouldFailIfNotMatchingTitle() {
    JOptionPane optionPane = frame.showMessageWithTitle("Yoda");
    try {
      driver.requireTitle(optionPane, "Darth Vader");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'title'").contains("expected:<'Darth Vader'> but was:<'Yoda'>");
    }
  }

  public void shouldPassIfMatchingOptions() {
    JOptionPane optionPane = frame.showMessageWithOptions(array("First", "Second"));
    driver.requireOptions(optionPane, array("First", "Second"));
  }

  public void shouldFailIfNotMatchingOptions() {
    JOptionPane optionPane = frame.showMessageWithOptions(array("First", "Second"));
    try {
      driver.requireOptions(optionPane, array("Third"));
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'options'")
                             .contains("expected:<['Third']> but was:<['First', 'Second']>");
    }
  }

  public void shouldPassIfMatchingMessage() {
    JOptionPane optionPane = frame.showMessageWithText("Leia");
    driver.requireMessage(optionPane, "Leia");
  }

  public void shouldFailIfNotMatchingMessage() {
    JOptionPane optionPane = frame.showMessageWithText("Palpatine");
    try {
      driver.requireMessage(optionPane, "Anakin");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'message'").contains("expected:<'Anakin'> but was:<'Palpatine'>");
    }
  }

  public void shouldPassIfExpectedAndActualMessageTypeIsError() {
    JOptionPane optionPane = frame.showErrorMessage();
    driver.requireErrorMessage(optionPane);
  }

  public void shouldFailIfExpectedMessageTypeIsErrorAndActualIsNot() {
    JOptionPane optionPane = frame.showInformationMessage();
    try {
      driver.requireErrorMessage(optionPane);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'messageType'")
                             .contains("expected:<'Error Message'> but was:<'Information Message'>");
    }
  }

  public void shouldPassIfExpectedAndActualMessageTypeIsInformation() {
    JOptionPane optionPane = frame.showInformationMessage();
    driver.requireInformationMessage(optionPane);
  }

  public void shouldFailIfExpectedMessageTypeIsInformationAndActualIsNot() {
    JOptionPane optionPane = frame.showErrorMessage();
    try {
      driver.requireInformationMessage(optionPane);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'messageType'")
                             .contains("expected:<'Information Message'> but was:<'Error Message'>");
    }
  }

  public void shouldPassIfExpectedAndActualMessageTypeIsWarning() {
    JOptionPane optionPane = frame.showWarningMessage();
    driver.requireWarningMessage(optionPane);
  }

  public void shouldFailIfExpectedMessageTypeIsWarningAndActualIsNot() {
    JOptionPane optionPane = frame.showErrorMessage();
    try {
      driver.requireWarningMessage(optionPane);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'messageType'")
                             .contains("expected:<'Warning Message'> but was:<'Error Message'>");
    }
  }

  public void shouldPassIfExpectedAndActualMessageTypeIsQuestion() {
    JOptionPane optionPane = frame.showQuestionMessage();
    driver.requireQuestionMessage(optionPane);
  }

  public void shouldFailIfExpectedMessageTypeIsQuestionAndActualIsNot() {
    JOptionPane optionPane = frame.showErrorMessage();
    try {
      driver.requireQuestionMessage(optionPane);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'messageType'")
                             .contains("expected:<'Question Message'> but was:<'Error Message'>");
    }
  }

  public void shouldPassIfExpectedAndActualMessageTypeIsPlain() {
    JOptionPane optionPane = frame.showPlainMessage();
    driver.requirePlainMessage(optionPane);
  }

  public void shouldFailIfExpectedMessageTypeIsPlainAndActualIsNot() {
    JOptionPane optionPane = frame.showErrorMessage();
    try {
      driver.requirePlainMessage(optionPane);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'messageType'")
                             .contains("expected:<'Plain Message'> but was:<'Error Message'>");
    }
  }
  
  public static class MyFrame extends TestWindow {
    private static final long serialVersionUID = 1L;

    private final Robot robot;

    final JButton button = new JButton("Click me");

    MyFrame(Robot robot) {
      super(JOptionPaneDriverTest.class);
      this.robot = robot;
      add(button);
    }

    JOptionPane showMessageWithTitle(String title) {
      return showMessage("Information", title, INFORMATION_MESSAGE);
    }

    JOptionPane showMessageWithText(String message) {
      return showMessage(message, "Title", INFORMATION_MESSAGE);
    }

    JOptionPane showMessageWithOptions(final Object[] options) {
      return setUpAndFindOptionPane(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) {
          showOptionDialog(MyFrame.this, "Message", "Title", YES_NO_OPTION, QUESTION_MESSAGE, null, options,
              options[0]);
        }
      });
    }

    JOptionPane showInputMessage() {
      return setUpAndFindOptionPane(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) {
          showInputDialog(MyFrame.this, "Message");
        }
      });
    }

    JOptionPane showConfirmMessage() {
      return setUpAndFindOptionPane(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) {
          JOptionPane.showConfirmDialog(MyFrame.this, "Text");
        }
      });
    }

    JOptionPane showErrorMessage() {
      return showMessage(ERROR_MESSAGE);
    }

    JOptionPane showInformationMessage() {
      return showMessage(INFORMATION_MESSAGE);
    }

    JOptionPane showWarningMessage() {
      return showMessage(WARNING_MESSAGE);
    }

    JOptionPane showQuestionMessage() {
      return showMessage(QUESTION_MESSAGE);
    }

    JOptionPane showPlainMessage() {
      return showMessage(PLAIN_MESSAGE);
    }

    private JOptionPane showMessage(final int messageType) {
      return showMessage("Text", "Title", messageType);
    }

    private JOptionPane showMessage(final String text, final String title, final int messageType) {
      return setUpAndFindOptionPane(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) {
          showMessageDialog(MyFrame.this, text, title, messageType);
        }
      });
    }

    JOptionPane showManuallyCreatedOptionPaneWithTitle(final String title) {
      return setUpAndFindOptionPane(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) {
          JOptionPane optionPane = new JOptionPane("Manually Created");
          JDialog dialog = optionPane.createDialog(MyFrame.this, title);
          dialog.setVisible(true);
        }
      });
    }

    private JOptionPane setUpAndFindOptionPane(MouseListener l) {
      removeAllMouseListeners();
      button.addMouseListener(l);
      clickButton();
      pause(500);
      JOptionPane optionPane = robot.finder().findByType(JOptionPane.class, true);
      pause(500);
      return optionPane;
    }

    private void removeAllMouseListeners() {
      for (MouseListener l : button.getMouseListeners()) button.removeMouseListener(l);
    }

    void clickButton() {
      robot.click(button);
    }
  }
}
