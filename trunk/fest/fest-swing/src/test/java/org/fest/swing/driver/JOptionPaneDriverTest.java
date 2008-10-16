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
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.query.AbstractButtonTextQuery.textOf;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.swing.timing.Pause.pause;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JOptionPaneDriver}</code>.
 * 
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JOptionPaneDriverTest {

  private Robot robot;
  private JOptionPaneDriver driver;
  private MyWindow window;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JOptionPaneDriver(robot);
    window = MyWindow.createNew();
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldFindButtonWithGivenTextInOptionPane() {
    window.setUpMessageWithOptions("First", "Second");
    JOptionPane optionPane = showJOptionPane();
    JButton button = driver.buttonWithText(optionPane, "Second");
    assertThat(textOf(button)).isEqualTo("Second");
  }

  public void shouldFindOKButton() {
    window.setUpInformationMessage();
    JOptionPane optionPane = showJOptionPane();
    JButton button = driver.okButton(optionPane);
    assertThatButtonHasTextFromUIManager(button, "OptionPane.okButtonText");
  }

  public void shouldFindCancelButton() {
    window.setUpInputMessage();
    JOptionPane optionPane = showJOptionPane();
    JButton button = driver.cancelButton(optionPane);
    assertThatButtonHasTextFromUIManager(button, "OptionPane.cancelButtonText");
  }

  public void shouldFindYesButton() {
    window.setUpConfirmMessage();
    JOptionPane optionPane = showJOptionPane();
    JButton button = driver.yesButton(optionPane);
    assertThatButtonHasTextFromUIManager(button, "OptionPane.yesButtonText");
  }

  public void shouldFindNoButton() {
    window.setUpConfirmMessage();
    JOptionPane optionPane = showJOptionPane();
    JButton button = driver.noButton(optionPane);
    assertThatButtonHasTextFromUIManager(button, "OptionPane.noButtonText");
  }

  private void assertThatButtonHasTextFromUIManager(JButton button, String textKey) {
    String expected = UIManager.getString(textKey);
    assertThat(textOf(button)).isEqualTo(expected);
  }
  
  public void shouldFindTextComponentInOptionPane() {
    window.setUpInputMessage();
    JOptionPane optionPane = showJOptionPane();
    JTextComponent textBox = driver.textBox(optionPane);
    assertThat(textBox).isNotNull();
  }

  @Test(groups = GUI, expectedExceptions = ComponentLookupException.class) 
  public void shouldNotFindTextComponentInOptionPaneIfNotInputMessage() {
    window.setUpErrorMessage();
    JOptionPane optionPane = showJOptionPane();
    driver.textBox(optionPane);
  }

  public void shouldPassIfMatchingTitle() {
    window.setUpMessageWithTitle("Star Wars");
    JOptionPane optionPane = showJOptionPane();
    driver.requireTitle(optionPane, "Star Wars");
  }

  public void shouldPassIfMatchingTitleWhenOptionPaneCreatedManually() {
    window.setUpManuallyCreatedOptionPaneWithTitle("Jedi");
    JOptionPane optionPane = showJOptionPane();
    driver.requireTitle(optionPane, "Jedi");
  }

  public void shouldFailIfNotMatchingTitle() {
    window.setUpMessageWithTitle("Yoda");
    JOptionPane optionPane =showJOptionPane(); 
    try {
      driver.requireTitle(optionPane, "Darth Vader");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'title'").contains("expected:<'Darth Vader'> but was:<'Yoda'>");
    }
  }

  public void shouldPassIfMatchingOptions() {
    window.setUpMessageWithOptions("First", "Second");
    JOptionPane optionPane = showJOptionPane();
    driver.requireOptions(optionPane, array("First", "Second"));
  }

  public void shouldFailIfNotMatchingOptions() {
    window.setUpMessageWithOptions("First", "Second");
    JOptionPane optionPane = showJOptionPane();
    try {
      driver.requireOptions(optionPane, array("Third"));
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'options'")
                             .contains("expected:<['Third']> but was:<['First', 'Second']>");
    }
  }

  public void shouldPassIfMatchingMessage() {
    window.setUpMessageWithText("Leia");
    JOptionPane optionPane = showJOptionPane();
    driver.requireMessage(optionPane, "Leia");
  }

  public void shouldFailIfNotMatchingMessage() {
    window.setUpMessageWithText("Palpatine");
    JOptionPane optionPane = showJOptionPane();
    try {
      driver.requireMessage(optionPane, "Anakin");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'message'").contains("expected:<'Anakin'> but was:<'Palpatine'>");
    }
  }

  public void shouldPassIfExpectedAndActualMessageTypeIsError() {
    window.setUpErrorMessage();
    JOptionPane optionPane = showJOptionPane();
    driver.requireErrorMessage(optionPane);
  }

  public void shouldFailIfExpectedMessageTypeIsErrorAndActualIsNot() {
    window.setUpInformationMessage();
    JOptionPane optionPane = showJOptionPane();
    try {
      driver.requireErrorMessage(optionPane);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'messageType'")
                             .contains("expected:<'Error Message'> but was:<'Information Message'>");
    }
  }

  public void shouldPassIfExpectedAndActualMessageTypeIsInformation() {
    window.setUpInformationMessage();
    JOptionPane optionPane = showJOptionPane();
    driver.requireInformationMessage(optionPane);
  }

  public void shouldFailIfExpectedMessageTypeIsInformationAndActualIsNot() {
    window.setUpErrorMessage();
    JOptionPane optionPane = showJOptionPane();
    try {
      driver.requireInformationMessage(optionPane);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'messageType'")
                             .contains("expected:<'Information Message'> but was:<'Error Message'>");
    }
  }

  public void shouldPassIfExpectedAndActualMessageTypeIsWarning() {
    window.setUpWarningMessage();
    JOptionPane optionPane = showJOptionPane();
    driver.requireWarningMessage(optionPane);
  }

  public void shouldFailIfExpectedMessageTypeIsWarningAndActualIsNot() {
    window.setUpErrorMessage();
    JOptionPane optionPane = showJOptionPane();
    try {
      driver.requireWarningMessage(optionPane);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'messageType'")
                             .contains("expected:<'Warning Message'> but was:<'Error Message'>");
    }
  }

  public void shouldPassIfExpectedAndActualMessageTypeIsQuestion() {
    window.setUpQuestionMessage();
    JOptionPane optionPane = showJOptionPane();
    driver.requireQuestionMessage(optionPane);
  }

  public void shouldFailIfExpectedMessageTypeIsQuestionAndActualIsNot() {
    window.setUpErrorMessage();
    JOptionPane optionPane = showJOptionPane();
    try {
      driver.requireQuestionMessage(optionPane);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'messageType'")
                             .contains("expected:<'Question Message'> but was:<'Error Message'>");
    }
  }

  public void shouldPassIfExpectedAndActualMessageTypeIsPlain() {
    window.setUpPlainMessage();
    JOptionPane optionPane = showJOptionPane();
    driver.requirePlainMessage(optionPane);
  }

  public void shouldFailIfExpectedMessageTypeIsPlainAndActualIsNot() {
    window.setUpErrorMessage();
    JOptionPane optionPane = showJOptionPane();
    try {
      driver.requirePlainMessage(optionPane);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'messageType'")
                             .contains("expected:<'Plain Message'> but was:<'Error Message'>");
    }
  }

  private JOptionPane showJOptionPane() {
    robot.click(window.button);
    pause(500);
    return robot.finder().findByType(JOptionPane.class, true);
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JButton button = new JButton("Click me");

    static MyWindow createNew() {
      return new MyWindow();
    }
    
    private MyWindow() {
      super(JOptionPaneDriverTest.class);
      add(button);
    }

    void setUpMessageWithTitle(String title) {
      setUpOptionPane("Information", title, INFORMATION_MESSAGE);
    }

    void setUpMessageWithText(String message) {
      setUpOptionPane(message, "Title", INFORMATION_MESSAGE);
    }

    void setUpMessageWithOptions(final Object... options) {
      setUpOptionPaneOnMouseClick(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) {
          showOptionDialog(MyWindow.this, "Message", "Title", YES_NO_OPTION, QUESTION_MESSAGE, null, options,
              options[0]);
        }
      });
    }

    void setUpInputMessage() {
      setUpOptionPaneOnMouseClick(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) {
          showInputDialog(MyWindow.this, "Message");
        }
      });
    }

    void setUpConfirmMessage() {
      setUpOptionPaneOnMouseClick(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) {
          JOptionPane.showConfirmDialog(MyWindow.this, "Text");
        }
      });
    }

    void setUpErrorMessage() {
      setUpOptionPaneWithMessageType(ERROR_MESSAGE);
    }

    void setUpInformationMessage() {
      setUpOptionPaneWithMessageType(INFORMATION_MESSAGE);
    }

    void setUpWarningMessage() {
      setUpOptionPaneWithMessageType(WARNING_MESSAGE);
    }

    void setUpQuestionMessage() {
      setUpOptionPaneWithMessageType(QUESTION_MESSAGE);
    }

    void setUpPlainMessage() {
      setUpOptionPaneWithMessageType(PLAIN_MESSAGE);
    }

    private void setUpOptionPaneWithMessageType(int messageType) {
      setUpOptionPane("Text", "Title", messageType);
    }

    private void setUpOptionPane(final String text, final String title, final int messageType) {
      setUpOptionPaneOnMouseClick(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) {
          showMessageDialog(MyWindow.this, text, title, messageType);
        }
      });
    }

    void setUpManuallyCreatedOptionPaneWithTitle(final String title) {
      setUpOptionPaneOnMouseClick(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) {
          JOptionPane optionPane = new JOptionPane("Manually Created");
          JDialog dialog = optionPane.createDialog(MyWindow.this, title);
          dialog.setVisible(true);
        }
      });
    }

    private void setUpOptionPaneOnMouseClick(final MouseListener toAdd) {
      for (MouseListener toRemove : button.getMouseListeners()) 
        button.removeMouseListener(toRemove);
      button.addMouseListener(toAdd);
    }
  }
}
