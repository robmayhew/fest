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

import org.fest.swing.core.GuiTask;
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
 * Tests for <code>{@link JOptionPaneDriver}</code>.
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
    frame = new GuiTask<MyFrame>() {
      protected MyFrame executeInEDT() {
        return new MyFrame();
      }
    }.run();
    robot.showWindow(frame);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldFindButtonWithGivenTextInOptionPane() {
    setUpMessageWithOptions("First", "Second");
    JOptionPane optionPane = showJOptionPane();
    JButton button = driver.buttonWithText(optionPane, "Second");
    assertThat(button.getText()).isEqualTo("Second");
  }

  public void shouldFindOKButton() {
    setUpInformationMessage();
    JOptionPane optionPane = showJOptionPane();
    JButton button = driver.okButton(optionPane);
    assertThatButtonHasTextFromUIManager(button, "OptionPane.okButtonText");
  }

  public void shouldFindCancelButton() {
    setUpInputMessage();
    JOptionPane optionPane = showJOptionPane();
    JButton button = driver.cancelButton(optionPane);
    assertThatButtonHasTextFromUIManager(button, "OptionPane.cancelButtonText");
  }

  public void shouldFindYesButton() {
    setUpConfirmMessage();
    JOptionPane optionPane = showJOptionPane();
    JButton button = driver.yesButton(optionPane);
    assertThatButtonHasTextFromUIManager(button, "OptionPane.yesButtonText");
  }

  public void shouldFindNoButton() {
    setUpConfirmMessage();
    JOptionPane optionPane = showJOptionPane();
    JButton button = driver.noButton(optionPane);
    assertThatButtonHasTextFromUIManager(button, "OptionPane.noButtonText");
  }

  private void setUpConfirmMessage() {
    new GuiTask<Void>() {
      protected Void executeInEDT() {
        frame.setUpConfirmMessage();
        return null;
      }
    }.run();
  }

  private void assertThatButtonHasTextFromUIManager(JButton button, String textKey) {
    String expected = UIManager.getString(textKey);
    assertThat(button.getText()).isEqualTo(expected);
  }
  
  public void shouldFindTextComponentInOptionPane() {
    setUpInputMessage();
    JOptionPane optionPane = showJOptionPane();
    JTextComponent textBox = driver.textBox(optionPane);
    assertThat(textBox).isNotNull();
  }

  private void setUpInputMessage() {
    new GuiTask<Void>() {
      protected Void executeInEDT() {
        frame.setUpInputMessage();
        return null;
      }
    }.run();
  }

  @Test(groups = GUI, expectedExceptions = ComponentLookupException.class) 
  public void shouldNotFindTextComponentInOptionPaneIfNotInputMessage() {
    setUpErrorMessage();
    JOptionPane optionPane = showJOptionPane();
    driver.textBox(optionPane);
  }

  public void shouldPassIfMatchingTitle() {
    final String title = "Star Wars";
    JOptionPane optionPane = showMessageWithTitle(title);
    driver.requireTitle(optionPane, title);
  }

  public void shouldPassIfMatchingTitleWhenOptionPaneCreatedManually() {
    new GuiTask<Void>() {
      protected Void executeInEDT() {
        frame.setUpManuallyCreatedOptionPane("Jedi");
        return null;
      }
    }.run();
    JOptionPane optionPane = showJOptionPane();
    driver.requireTitle(optionPane, "Jedi");
  }

  public void shouldFailIfNotMatchingTitle() {
    final String title = "Yoda";
    JOptionPane optionPane = showMessageWithTitle(title);
    try {
      driver.requireTitle(optionPane, "Darth Vader");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'title'").contains("expected:<'Darth Vader'> but was:<'Yoda'>");
    }
  }

  private JOptionPane showMessageWithTitle(final String title) {
    new GuiTask<Void>() {
      protected Void executeInEDT() {
        frame.setUpMessageWithTitle(title);
        return null;
      }
    }.run();
    return showJOptionPane();
  }

  public void shouldPassIfMatchingOptions() {
    setUpMessageWithOptions("First", "Second");
    JOptionPane optionPane = showJOptionPane();
    driver.requireOptions(optionPane, array("First", "Second"));
  }

  public void shouldFailIfNotMatchingOptions() {
    setUpMessageWithOptions("First", "Second");
    JOptionPane optionPane = showJOptionPane();
    try {
      driver.requireOptions(optionPane, array("Third"));
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'options'")
                             .contains("expected:<['Third']> but was:<['First', 'Second']>");
    }
  }

  private void setUpMessageWithOptions(final String... options) {
    new GuiTask<Void>() {
      protected Void executeInEDT() {
        frame.setUpMessageWithOptions(options);
        return null;
      }
    }.run();
  }

  public void shouldPassIfMatchingMessage() {
    setUpMessageWithText("Leia");
    JOptionPane optionPane = showJOptionPane();
    driver.requireMessage(optionPane, "Leia");
  }

  public void shouldFailIfNotMatchingMessage() {
    setUpMessageWithText("Palpatine");
    JOptionPane optionPane = showJOptionPane();
    try {
      driver.requireMessage(optionPane, "Anakin");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'message'").contains("expected:<'Anakin'> but was:<'Palpatine'>");
    }
  }

  private void setUpMessageWithText(final String text) {
    new GuiTask<Void>() {
      protected Void executeInEDT() {
        frame.setUpMessageWithText(text);
        return null;
      }
    }.run();
  }

  public void shouldPassIfExpectedAndActualMessageTypeIsError() {
    setUpErrorMessage();
    JOptionPane optionPane = showJOptionPane();
    driver.requireErrorMessage(optionPane);
  }

  public void shouldFailIfExpectedMessageTypeIsErrorAndActualIsNot() {
    setUpInformationMessage();
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
    setUpInformationMessage();
    JOptionPane optionPane = showJOptionPane();
    driver.requireInformationMessage(optionPane);
  }

  private void setUpInformationMessage() {
    new GuiTask<Void>() {
      protected Void executeInEDT() {
        frame.setUpInformationMessage();
        return null;
      }
    }.run();
  }

  public void shouldFailIfExpectedMessageTypeIsInformationAndActualIsNot() {
    setUpErrorMessage();
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
    new GuiTask<Void>() {
      protected Void executeInEDT() {
        frame.setUpWarningMessage();
        return null;
      }
    }.run();
    JOptionPane optionPane = showJOptionPane();
    driver.requireWarningMessage(optionPane);
  }

  public void shouldFailIfExpectedMessageTypeIsWarningAndActualIsNot() {
    setUpErrorMessage();
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
    new GuiTask<Void>() {
      @Override protected Void executeInEDT() {
        frame.setUpQuestionMessage();
        return null;
      }
    }.run();
    JOptionPane optionPane = showJOptionPane();
    driver.requireQuestionMessage(optionPane);
  }

  public void shouldFailIfExpectedMessageTypeIsQuestionAndActualIsNot() {
    setUpErrorMessage();
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
    new GuiTask<Void>() {
      @Override protected Void executeInEDT() {
        frame.setUpPlainMessage();
        return null;
      }
    }.run();
    JOptionPane optionPane = showJOptionPane();
    driver.requirePlainMessage(optionPane);
  }

  public void shouldFailIfExpectedMessageTypeIsPlainAndActualIsNot() {
    setUpErrorMessage();
    JOptionPane optionPane = showJOptionPane();
    try {
      driver.requirePlainMessage(optionPane);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'messageType'")
                             .contains("expected:<'Plain Message'> but was:<'Error Message'>");
    }
  }
  
  private void setUpErrorMessage() {
    new GuiTask<Void>() {
      protected Void executeInEDT() {
        frame.setUpErrorMessage();
        return null;
      }
    }.run();
  }

  private JOptionPane showJOptionPane() {
    robot.click(frame.button);
    pause(500);
    return robot.finder().findByType(JOptionPane.class, true);
  }

  public static class MyFrame extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JButton button = new JButton("Click me");

    MyFrame() {
      super(JOptionPaneDriverTest.class);
      add(button);
    }

    void setUpMessageWithTitle(String title) {
      setUpOptionPane("Information", title, INFORMATION_MESSAGE);
    }

    void setUpMessageWithText(String message) {
      setUpOptionPane(message, "Title", INFORMATION_MESSAGE);
    }

    void setUpMessageWithOptions(final Object[] options) {
      setUpOptionPaneOnMouseClick(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) {
          showOptionDialog(MyFrame.this, "Message", "Title", YES_NO_OPTION, QUESTION_MESSAGE, null, options,
              options[0]);
        }
      });
    }

    void setUpInputMessage() {
      setUpOptionPaneOnMouseClick(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) {
          showInputDialog(MyFrame.this, "Message");
        }
      });
    }

    void setUpConfirmMessage() {
      setUpOptionPaneOnMouseClick(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) {
          JOptionPane.showConfirmDialog(MyFrame.this, "Text");
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
          showMessageDialog(MyFrame.this, text, title, messageType);
        }
      });
    }

    void setUpManuallyCreatedOptionPane(final String title) {
      setUpOptionPaneOnMouseClick(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) {
          JOptionPane optionPane = new JOptionPane("Manually Created");
          JDialog dialog = optionPane.createDialog(MyFrame.this, title);
          dialog.setVisible(true);
        }
      });
    }

    private void setUpOptionPaneOnMouseClick(MouseListener l) {
      removeAllMouseListeners();
      button.addMouseListener(l);
    }

    private void removeAllMouseListeners() {
      for (MouseListener l : button.getMouseListeners()) button.removeMouseListener(l);
    }
  }
}
