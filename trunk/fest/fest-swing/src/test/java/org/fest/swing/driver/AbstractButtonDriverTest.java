/*
 * Created on Apr 5, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.driver;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.assertions.AssertExtension;
import org.fest.swing.core.GuiTask;
import org.fest.swing.core.Robot;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link AbstractButtonDriver}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class AbstractButtonDriverTest {

  private Robot robot;
  private JCheckBox checkBox;
  private AbstractButtonDriver driver;

  @BeforeMethod public void setUp() {
    robot = RobotFixture.robotWithNewAwtHierarchy();
    driver = new AbstractButtonDriver(robot);
    MyFrame frame = new GuiTask<MyFrame>() {
      protected MyFrame executeInEDT() {
        return new MyFrame();
      }
    }.run();
    checkBox = frame.checkBox;
    robot.showWindow(frame);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  public void shouldClickButton() {
    ActionPerformedRecorder recorder = ActionPerformedRecorder.attachTo(checkBox);
    driver.click(checkBox);
    assertThat(recorder).actionWasPerformed();
  }
  
  public void shouldNotClickButtonIfButtonDisabled() {
    new GuiTask<Void>() {
      protected Void executeInEDT() {
        checkBox.setEnabled(false);
        return null;
      }
    }.run();
    ActionPerformedRecorder recorder = ActionPerformedRecorder.attachTo(checkBox);
    driver.click(checkBox);
    assertThat(recorder).actionWasNotPerformed();
  }
  
  public void shouldPassIfTextIsEqualToExpectedOne() {
    driver.requireText(checkBox, "Hello");
  }
  
  public void shouldFailIfTextIsNotEqualToExpectedOne() {
    try {
      driver.requireText(checkBox, "Bye");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'text'")
                             .contains("expected:<'Bye'> but was:<'Hello'>");
    }
  }

  public void shouldNotSelectIfButtonAlreadySelected() {
    selectCheckBox();
    driver.select(checkBox);
    assertThatCheckBoxIsSelected();
  }
  
  public void shouldSelectButton() {
    unselectCheckBox();
    driver.select(checkBox);
    assertThatCheckBoxIsSelected();
  }

  private void assertThatCheckBoxIsSelected() {
    assertThat(isCheckBoxSelected()).isTrue();
  }
  
  public void shouldNotUnselectIfButtonAlreadySelected() {
    unselectCheckBox();
    driver.unselect(checkBox);
    assertThatCheckBoxIsNotSelected();
  }
  
  public void shouldUnselectButton() {
    selectCheckBox();
    driver.unselect(checkBox);
    assertThatCheckBoxIsNotSelected();
  }

  private void assertThatCheckBoxIsNotSelected() {
    assertThat(isCheckBoxSelected()).isFalse();
  }

  private boolean isCheckBoxSelected() {
    return new GuiTask<Boolean>() {
      protected Boolean executeInEDT() {
        return checkBox.isSelected();
      }
    }.run();
  }

  public void shouldPassIfButtonIsSelectedAsAnticipated() {
    selectCheckBox();
    driver.requireSelected(checkBox);
  }
  
  public void shouldFailIfButtonIsNotSelectedAndExpectingSelected() {
    unselectCheckBox();
    try {
      driver.requireSelected(checkBox);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selected'")
                             .contains("expected:<true> but was:<false>");
    }
  }
  
  public void shouldPassIfButtonIsUnselectedAsAnticipated() {
    unselectCheckBox();
    driver.requireNotSelected(checkBox);
  }

  private void unselectCheckBox() {
    setCheckBoxSelected(false);
  }

  public void shouldFailIfButtonIsSelectedAndExpectingNotSelected() {
    selectCheckBox();
    try {
      driver.requireNotSelected(checkBox);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selected'")
                             .contains("expected:<false> but was:<true>");
    }
  }

  private void selectCheckBox() {
    setCheckBoxSelected(true);
  }

  private void setCheckBoxSelected(final boolean selected) {
    new GuiTask<Void>() {
      protected Void executeInEDT() {
        checkBox.setSelected(selected);
        return null;
      }
    }.run();
  }


  private static class MyFrame extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JCheckBox checkBox = new JCheckBox("Hello", true);

    public MyFrame() {
      super(AbstractButtonDriverTest.class);
      add(checkBox);
      setPreferredSize(new Dimension(200, 200));
    }
  }

  private static class ActionPerformedRecorder implements ActionListener, AssertExtension {
    private boolean actionPerformed;

    static ActionPerformedRecorder attachTo(AbstractButton button) {
      ActionPerformedRecorder r = new ActionPerformedRecorder();
      button.addActionListener(r);
      return r;
    }
    
    public void actionPerformed(ActionEvent e) {
      actionPerformed = true;
    }
    
    ActionPerformedRecorder actionWasPerformed() {
      assertThat(actionPerformed).isTrue();
      return this;
    }

    ActionPerformedRecorder actionWasNotPerformed() {
      assertThat(actionPerformed).isFalse();
      return this;
    }
  }
}
