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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.assertions.AssertExtension;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.EventMode;
import org.fest.swing.core.EventModeProvider;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.CheckThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.task.ComponentSetVisibleTask;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.AbstractButtonSelectedQuery.isSelected;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.task.AbstractButtonSetSelectedTask.setSelected;
import static org.fest.swing.task.ComponentSetEnabledTask.disable;
import static org.fest.swing.testing.CommonAssertions.*;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link AbstractButtonDriver}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class AbstractButtonDriverTest {

  private Robot robot;
  private AbstractButtonDriver driver;
  private MyWindow window;
  private JCheckBox checkBox;

  @BeforeClass public void setUpOnce() {
    CheckThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new AbstractButtonDriver(robot);
    window = MyWindow.createNew();
    checkBox = window.checkBox;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldClickButton(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    ActionPerformedRecorder recorder = ActionPerformedRecorder.attachTo(checkBox);
    driver.click(checkBox);
    assertThat(recorder).wasPerformed();
  }

  public void shouldThrowErrorWhenClickingDisabledAbstractButton() {
    disableCheckBox();
    ActionPerformedRecorder action = ActionPerformedRecorder.attachTo(checkBox);
    try {
      driver.click(checkBox);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
    assertThat(action).wasNotPerformed();
  }

  public void shouldThrowErrorWhenClickingNotShowingAbstractButton() {
    hideWindow();
    ActionPerformedRecorder action = ActionPerformedRecorder.attachTo(checkBox);
    try {
      driver.click(checkBox);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
    assertThat(action).wasNotPerformed();
  }

  public void shouldReturnButtonText() {
    assertThat(driver.textOf(checkBox)).isEqualTo("Hello");
  }

  public void shouldPassIfTextIsEqualToExpectedOne() {
    driver.requireText(checkBox, "Hello");
  }

  public void shouldFailIfTextIsNotEqualToExpectedOne() {
    try {
      driver.requireText(checkBox, "Bye");
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'text'")
                             .contains("expected:<'Bye'> but was:<'Hello'>");
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectIfButtonAlreadySelected(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    selectCheckBox();
    driver.select(checkBox);
    assertThatCheckBoxIsSelected();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSelectButton(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    unselectCheckBox();
    driver.select(checkBox);
    assertThatCheckBoxIsSelected();
  }

  private void assertThatCheckBoxIsSelected() {
    assertThat(isSelected(checkBox)).isTrue();
  }

  public void shouldThrowErrorWhenSelectingDisabledAbstractButton() {
    disableCheckBox();
    try {
      driver.select(checkBox);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenSelectingNotShowingAbstractButton() {
    hideWindow();
    try {
      driver.select(checkBox);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotUnselectIfButtonAlreadySelected(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    unselectCheckBox();
    driver.unselect(checkBox);
    assertThatCheckBoxIsNotSelected();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldUnselectButton(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    selectCheckBox();
    driver.unselect(checkBox);
    assertThatCheckBoxIsNotSelected();
  }

  public void shouldThrowErrorWhenUnselectingDisabledAbstractButton() {
    disableCheckBox();
    try {
      driver.unselect(checkBox);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  public void shouldThrowErrorWhenUnselectingNotShowingAbstractButton() {
    hideWindow();
    try {
      driver.unselect(checkBox);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }

  @RunsInEDT
  private void disableCheckBox() {
    disable(checkBox);
    robot.waitForIdle();
  }

  @RunsInEDT
  private void hideWindow() {
    ComponentSetVisibleTask.hide(window);
    robot.waitForIdle();
  }
  
  @RunsInEDT
  private void assertThatCheckBoxIsNotSelected() {
    assertThat(isSelected(checkBox)).isFalse();
  }

  public void shouldPassIfButtonIsSelectedAsAnticipated() {
    selectCheckBox();
    driver.requireSelected(checkBox);
  }

  public void shouldFailIfButtonIsNotSelectedAndExpectingSelected() {
    unselectCheckBox();
    try {
      driver.requireSelected(checkBox);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selected'")
                             .contains("expected:<true> but was:<false>");
    }
  }

  public void shouldPassIfButtonIsUnselectedAsAnticipated() {
    unselectCheckBox();
    driver.requireNotSelected(checkBox);
  }

  @RunsInEDT
  private void unselectCheckBox() {
    setSelected(checkBox, false);
    robot.waitForIdle();
  }

  public void shouldFailIfButtonIsSelectedAndExpectingNotSelected() {
    selectCheckBox();
    try {
      driver.requireNotSelected(checkBox);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selected'")
                             .contains("expected:<false> but was:<true>");
    }
  }

  @RunsInEDT
  private void selectCheckBox() {
    setSelected(checkBox, true);
    robot.waitForIdle();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    final JCheckBox checkBox = new JCheckBox("Hello", true);

    private MyWindow() {
      super(AbstractButtonDriverTest.class);
      add(checkBox);
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

    ActionPerformedRecorder wasPerformed() {
      assertThat(actionPerformed).isTrue();
      return this;
    }

    ActionPerformedRecorder wasNotPerformed() {
      assertThat(actionPerformed).isFalse();
      return this;
    }
  }
}
