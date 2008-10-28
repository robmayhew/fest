/*
 * Created on Feb 24, 2008
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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.MouseClickInfo;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.testing.ClickRecorder;
import org.fest.swing.testing.StopWatch;
import org.fest.swing.testing.TestWindow;

import static java.awt.Event.SHIFT_MASK;
import static java.awt.event.KeyEvent.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.awt.AWT.centerOf;
import static org.fest.swing.core.KeyPressInfo.keyCode;
import static org.fest.swing.core.MouseButton.*;
import static org.fest.swing.core.MouseClickInfo.*;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.swing.query.JTextComponentTextQuery.textOf;
import static org.fest.swing.task.ComponentSetEnabledTask.disable;
import static org.fest.swing.task.ComponentSetVisibleTask.setVisible;
import static org.fest.swing.testing.StopWatch.startNewStopWatch;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.swing.timing.Pause.pause;
import static org.fest.swing.timing.Timeout.timeout;

/**
 * Tests for <code>{@link ComponentDriver}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class ComponentDriverTest {

  private static final int PAUSE_TIME = 2000;

  private Robot robot;
  private ComponentDriver driver;
  private MyWindow window;
  private MyButton button;
  private JTextField textField;


  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = MyWindow.createNew();
    button = window.button;
    textField = window.textField;
    driver = new ComponentDriver(robot);
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldClickComponent() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    driver.click(button);
    assertThat(clickRecorder).wasClicked()
                             .clickedAt(centerOf(button))
                             .timesClicked(1);
  }

  public void shouldThrowErrorWhenClickingDisabledComponent() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    disableButton();
    try {
      driver.click(button);
      fail("Expecting exception");
    } catch (ActionFailedException e) {}
    assertThat(clickRecorder).wasNotClicked();
  }

  @Test(dataProvider = "mouseButtons", groups = GUI)
  public void shouldClickComponentWithGivenMouseButtonOnce(MouseButton mouseButton) {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    driver.click(button, mouseButton);
    assertThat(clickRecorder).wasClickedWith(mouseButton)
                             .clickedAt(centerOf(button))
                             .timesClicked(1);
  }

  @DataProvider(name = "mouseButtons") public Object[][] mouseButtons() {
    return new Object[][] { { LEFT_BUTTON }, { MIDDLE_BUTTON }, { RIGHT_BUTTON } };
  }

  @Test(expectedExceptions = NullPointerException.class, groups = GUI)
  public void shouldThrowErrorIfMouseButtonIsNull() {
    driver.click(button, (MouseButton)null);
  }

  public void shouldThrowErrorWhenClickingDisabledComponentWithGivenMouseButton() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    disableButton();
    try {
      driver.click(button, RIGHT_BUTTON);
      fail("Expecting exception");
    } catch (ActionFailedException e) {}
    assertThat(clickRecorder).wasNotClicked();
  }

  @Test(expectedExceptions = NullPointerException.class, groups = GUI)
  public void shouldThrowErrorIfMouseClickInfoIsNull() {
    driver.click(button, (MouseClickInfo)null);
  }

  @Test(dataProvider = "mouseClickInfos", groups = GUI)
  public void shouldClickComponentWithGivenMouseClickInfo(MouseClickInfo mouseClickInfo) {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    driver.click(button, mouseClickInfo);
    assertThat(clickRecorder).wasClickedWith(mouseClickInfo.button())
                             .clickedAt(centerOf(button))
                             .timesClicked(mouseClickInfo.times());
  }

  @DataProvider(name = "mouseClickInfos") public Object[][] mouseClickInfos() {
    return new Object[][] { { leftButton().times(3) }, { middleButton() }};
  }

  public void shouldThrowErrorWhenClickingDisabledComponentWithGivenMouseClickInfo() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    disableButton();
    try {
      driver.click(button, leftButton());
      fail("Expecting exception");
    } catch (ActionFailedException e) {}
    assertThat(clickRecorder).wasNotClicked();
  }

  public void shouldDoubleClickComponent() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    driver.doubleClick(button);
    assertThat(clickRecorder).wasDoubleClicked()
                             .clickedAt(centerOf(button));
  }

  public void shouldThrowErrorWhenDoubleClickingDisabledComponent() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    disableButton();
    try {
      driver.doubleClick(button);
      fail("Expecting exception");
    } catch (ActionFailedException e) {}
    assertThat(clickRecorder).wasNotClicked();
  }

  public void shouldRightClickComponent() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    driver.rightClick(button);
    assertThat(clickRecorder).wasRightClicked()
                             .clickedAt(centerOf(button))
                             .timesClicked(1);
  }

  public void shouldThrowErrorWhenRightClickingDisabledComponent() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    disableButton();
    try {
      driver.rightClick(button);
      fail("Expecting exception");
    } catch (ActionFailedException e) {}
    assertThat(clickRecorder).wasNotClicked();
  }

  public void shouldClickComponentOnGivenPoint() {
    Point center = centerOf(button);
    Point where = new Point(center.x + 1, center.y + 1);
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    driver.click(button, where);
    assertThat(clickRecorder).wasClicked()
                             .clickedAt(where)
                             .timesClicked(1);

  }

  public void shouldThrowErrorWhenClickingDisabledComponentAtGivenPoint() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    disableButton();
    try {
      driver.click(button, new Point(10, 10));
      fail("Expecting exception");
    } catch (ActionFailedException e) {}
    assertThat(clickRecorder).wasNotClicked();
  }

  public void shouldGiveFocusToComponentAndWaitTillComponentIsFocused() {
    assertThat(hasFocus(button)).isFalse();
    button.waitToRequestFocus();
    final CountDownLatch done = new CountDownLatch(1);
    StopWatch stopWatch = startNewStopWatch();
    new Thread() {
      @Override public void run() {
        driver.focusAndWaitForFocusGain(button);
        done.countDown();
      }
    }.start();
    try {
      done.await();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    stopWatch.stop();
    assertThat(hasFocus(button)).isTrue();
    assertThatWaited(stopWatch, PAUSE_TIME);
  }

  private static boolean hasFocus(final Component c) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() throws Throwable {
        return c.hasFocus();
      }
    });
  }

  public void shouldThrowErrorWhenGivingFocusToDisabledComponentAndWaiting() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    disableButton();
    try {
      driver.focusAndWaitForFocusGain(button);
      fail("Expecting exception");
    } catch (ActionFailedException e) {}
    assertThat(clickRecorder).wasNotClicked();
  }

  public void shouldReturnSettingsFromRobot() {
    assertThat(driver.settings()).isSameAs(robot.settings());
  }

  public void shouldPassIfActualSizeIsEqualToExpectedOne() {
    Dimension expected = sizeOf(button);
    driver.requireSize(button, expected);
  }

  public void shouldFailIfActualSizeIsNotEqualToExpectedOne() {
    try {
      driver.requireSize(button, new Dimension(0, 0));
      fail("Expecting exception");
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'size'")
                             .contains("expected:<(0, 0)>")
                             .contains("but was:<");
    }
  }

  public void shouldPassIfComponentIsVisibleAsExpected() {
    driver.requireVisible(button);
  }

  public void shouldFailIfComponentIsNotVisibleAndExpectedToBeVisible() {
    hideWindow();
    try {
      driver.requireVisible(window);
      fail("Expecting exception");
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'visible'")
                             .contains("expected:<true> but was:<false>");
    }
  }

  public void shouldPassIfComponentIsNotVisibleAsExpected() {
    hideWindow();
    driver.requireNotVisible(window);
  }
  
  private void hideWindow() {
    setVisible(window, false);
    robot.waitForIdle();
  }
  
  public void shouldFailIfComponentIsVisibleAndExpectedToBeNotVisible() {
    try {
      driver.requireNotVisible(button);
      fail("Expecting exception");
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'visible'")
                             .contains("expected:<false> but was:<true>");
    }
  }
  
  public void shouldPassIfComponentIsEnabledAsExpected() {
    driver.requireEnabled(button);
  }
  
  public void shouldFailIfComponentIsNotEnabledAndExpectedToBeEnabled() {
    disableButton();
    try {
      driver.requireEnabled(button);
      fail("Expecting exception");
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'enabled'")
                             .contains("expected:<true> but was:<false>");
    }
  }

  public void shouldPassIfComponentIsEnabledAsExpectedBeforeTimeout() {
    driver.requireEnabled(button, timeout(100));
  }
  
  public void shouldFailIfComponentIsNotEnabledAsExpectedBeforeTimeout() {
    disableButton();
    int timeout = 1000;
    StopWatch stopWatch = startNewStopWatch();
    try {
      driver.requireEnabled(button, timeout(timeout));
      fail("Expecting exception");
    } catch (WaitTimedOutError e) {
      assertThat(e).message().contains("Timed out waiting for")
                             .contains(button.getClass().getName())
                             .contains("to be enabled");
    }
    stopWatch.stop();
    assertThatWaited(stopWatch, timeout);
  }

  private void assertThatWaited(StopWatch stopWatch, long minimumWaitedTime) {
    long ellapsedTimeInMs = stopWatch.ellapsedTime() / 1000;
    assertThat(ellapsedTimeInMs).isGreaterThanOrEqualTo(minimumWaitedTime);
  }
  
  public void shouldPassIfComponentIsNotEnabledAsExpected() {
    disableButton();
    driver.requireDisabled(button);
  }
  
  public void shouldFailIfComponentIsEnabledAndExpectedToBeNotEnabled() {
    try {
      driver.requireDisabled(button);
      fail("Expecting exception");
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'enabled'")
                             .contains("expected:<false> but was:<true>");
    }
  }

  private void disableButton() {
    disable(button);
    robot.waitForIdle();
  }
  
  @Test(expectedExceptions = NullPointerException.class, groups = GUI)
  public void shouldThrowErrorIfArrayOfKeysToPressAndReleaseIsNull() {
    int[] keyCodes = null;
    driver.pressAndReleaseKeys(button, keyCodes);
  }
  
  public void shouldPressAndReleaseKeys() {
    int[] keyCodes = { VK_A, VK_C, VK_E };
    driver.pressAndReleaseKeys(textField, keyCodes);
    assertThat(textOf(textField)).isEqualTo("ace");
  }

  public void shouldThrowErrorWhenPressingAndReleasingKeysInDisabledComponent() {
    disable(textField);
    try {
      driver.pressAndReleaseKeys(textField, VK_A);
      fail("Expecting exception");
    } catch (ActionFailedException e) {}
    assertThat(textOf(textField)).isEmpty();
  }
  
  @Test(expectedExceptions = NullPointerException.class, groups = GUI)
  public void shouldThrowErrorIfKeyPressInfoIsNull() {
    driver.pressAndReleaseKey(button, null);
  }
  
  public void shouldPressAndReleaseKeyInSpecifiedKeyPressInfo() {
    driver.pressAndReleaseKey(textField, keyCode(VK_A).modifiers(SHIFT_MASK));
    assertThat(textOf(textField)).isEqualTo("A");
  }
  
  public void shouldThrowErrorWhenPressingAndReleasingKeyInDisabledComponent() {
    disable(textField);
    try {
      driver.pressAndReleaseKey(textField, keyCode(VK_A).modifiers(SHIFT_MASK));
      fail("Expecting exception");
    } catch (ActionFailedException e) {}
    assertThat(textOf(textField)).isEmpty();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    final JTextField textField = new JTextField(20);
    final MyButton button = new MyButton("Click Me");

    private MyWindow() {
      super(ComponentDriverTest.class);
      addComponents(textField, button);
    }
  }

  private static class MyButton extends JButton {
    private static final long serialVersionUID = 1L;

    private boolean waitToRequestFocus;

    MyButton(String text) {
      super(text);
    }

    void waitToRequestFocus() { waitToRequestFocus = true; }

    @Override public boolean requestFocusInWindow() {
      if (waitToRequestFocus) pause(PAUSE_TIME);
      return super.requestFocusInWindow();
    }
  }
}
