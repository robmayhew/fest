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

import javax.swing.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.*;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.testing.StopWatch;

import static java.awt.event.InputEvent.SHIFT_MASK;
import static java.awt.event.KeyEvent.VK_R;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.core.MouseClickInfo.button;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.core.Timeout.timeout;
import static org.fest.swing.testing.StopWatch.startNewStopWatch;
import static org.fest.swing.util.Platform.*;

/**
 * Tests for <code>{@link ComponentDriver}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test public class ComponentDriverTest {

  private Robot robot;
  private DragAndDrop dragAndDrop;
  private Component c;
  private ComponentDriver driver;

  @BeforeMethod public void setUp() {
    robot = createMock(Robot.class);
    dragAndDrop = createMock(DragAndDrop.class);
    c = new JTextField();
    driver = new ComponentDriver(robot, dragAndDrop);
  }

  public void shouldClickComponent() {
    new EasyMockTemplate(robot) {
      protected void expectations() {
        robot.click(c);
        expectLastCall().once();
      }

      protected void codeToTest() {
        driver.click(c);
      }
    }.run();
  }

  public void shouldClickComponentUsingGivenMouseButton() {
    final MouseButton button = LEFT_BUTTON;
    new EasyMockTemplate(robot) {
      protected void expectations() {
        robot.click(c, button);
        expectLastCall().once();
      }

      protected void codeToTest() {
        driver.click(c, button);
      }
    }.run();
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfMouseClickInfoIsNull() {
    MouseClickInfo info = null;
    driver.click(c, info);
  }
  
  public void shouldClickComponentUsingMouseClickInfo() {
    final MouseButton button = LEFT_BUTTON;
    final int times = 2;
    final MouseClickInfo info = button(button).times(times);
    new EasyMockTemplate(robot) {
      protected void expectations() {
        robot.click(c, button, times);
        expectLastCall().once();
      }

      protected void codeToTest() {
        driver.click(c, info);
      }
    }.run();
    
  }
  
  public void shouldClickComponentUsingGivenMouseButtonAndTimes() {
    final MouseButton button = LEFT_BUTTON;
    final int times = 2;
    new EasyMockTemplate(robot) {
      protected void expectations() {
        robot.click(c, button, times);
        expectLastCall().once();
      }

      protected void codeToTest() {
        driver.click(c, button, times);
      }
    }.run();
  }

  public void shouldClickComponentAtGivenPoint() {
    final Point point = new Point(0, 0);
    new EasyMockTemplate(robot) {
      protected void expectations() {
        robot.click(c, point);
        expectLastCall().once();
      }

      protected void codeToTest() {
        driver.click(c, point);
      }
    }.run();
  }

  public void shouldDoubleClickComponent() {
    new EasyMockTemplate(robot) {
      protected void expectations() {
        robot.doubleClick(c);
        expectLastCall().once();
      }

      protected void codeToTest() {
        driver.doubleClick(c);
      }
    }.run();
  }

  public void shouldRightClickComponent() {
    new EasyMockTemplate(robot) {
      protected void expectations() {
        robot.rightClick(c);
        expectLastCall().once();
      }

      protected void codeToTest() {
        driver.rightClick(c);
      }
    }.run();
  }

  public void shouldGiveFocusAndWaitForFocusGain() {
    new EasyMockTemplate(robot) {
      protected void expectations() {
        robot.focusAndWaitForFocusGain(c);
        expectLastCall().once();
      }

      protected void codeToTest() {
        driver.focusAndWaitForFocusGain(c);
      }
    }.run();
  }

  public void shouldReturnSettingsFromRobot() {
    final Settings settings = new Settings();
    new EasyMockTemplate(robot) {
      protected void expectations() {
        expect(robot.settings()).andReturn(settings);
      }

      protected void codeToTest() {
        assertThat(driver.settings()).isSameAs(settings);
      }
    }.run();
  }

  public void shouldPassIfSizeIsEqualToExpected() {
    Dimension size = new Dimension(10, 10);
    c.setSize(size);
    driver.requireSize(c, size);
  }

  public void shouldFailIfSizeIsNotEqualToExpected() {
    c.setSize(new Dimension(10, 10));
    try {
      driver.requireSize(c, new Dimension(20, 20));
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'size'")
                             .contains("expected:<(20, 20)> but was:<(10, 10)>");
    }
  }

  public void shouldPassIfVisibleAsAnticipated() {
    c.setVisible(true);
    driver.requireVisible(c);
  }

  public void shouldFailIfNotVisibleAndExpectingVisible() {
    c.setVisible(false);
    try {
      driver.requireVisible(c);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'visible'")
                             .contains("expected:<true> but was:<false>");
    }
  }

  public void shouldPassIfNotVisibleAsAnticipated() {
    c.setVisible(false);
    driver.requireNotVisible(c);
  }

  public void shouldFailIfVisibleAndExpectingNotVisible() {
    c.setVisible(true);
    try {
      driver.requireNotVisible(c);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'visible'")
                             .contains("expected:<false> but was:<true>");
    }
  }

  public void shouldPassIfEnabledAsAnticipated() {
    c.setEnabled(true);
    driver.requireEnabled(c);
  }

  public void shouldFailIfNotEnabledAndExpectingEnabled() {
    c.setEnabled(false);
    try {
      driver.requireEnabled(c);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'enabled'")
                             .contains("expected:<true> but was:<false>");
    }
  }

  public void shouldPassIfComponentIsEnabledBeforeTimeout() {
    c.setEnabled(false);
    Runnable task = new Runnable() {
      public void run() {
        pause(1000);
        c.setEnabled(true);
      }
    };
    new Thread(task).start();
    driver.requireEnabled(c, timeout(2, SECONDS));
  }

  @Test(expectedExceptions = WaitTimedOutError.class)
  public void shouldFailIfComponentNotEnabledBeforeTimeout() {
    c.setEnabled(false);
    Runnable task = new Runnable() {
      public void run() {
        pause(500);
        c.setEnabled(true);
      }
    };
    new Thread(task).start();
    driver.requireEnabled(c, timeout(100));
  }

  public void shouldPassIfComponentDisabledAsAnticipated() {
    c.setEnabled(false);
    driver.requireDisabled(c);
  }

  public void shouldFailIfEnabledAndExpectingDisabled() {
    c.setEnabled(true);
    try {
      driver.requireDisabled(c);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'enabled'")
                             .contains("expected:<false> but was:<true>");
    }
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfKeyPressInfoIsNull() {
    driver.pressAndReleaseKey(c, null);
  }
  
  public void shouldPressAndReleaseKeysUsingKeyPressInfo() {
    final KeyPressInfo keyPressInfo = KeyPressInfo.keyCode(VK_R).modifiers(SHIFT_MASK);
    new EasyMockTemplate(robot) {
      protected void expectations() {
        robot.focus(c);
        expectLastCall().once();
        robot.pressAndReleaseKey(keyPressInfo.keyCode(), keyPressInfo.modifiers());
        expectLastCall().once();
      }

      protected void codeToTest() {
        driver.pressAndReleaseKey(c, keyPressInfo);
      }
    }.run();
  }
  
  public void shouldPressAndReleaseKeysWithModifiers() {
    final int keyCode = VK_R;
    final int[] modifiers = { SHIFT_MASK };
    new EasyMockTemplate(robot) {
      protected void expectations() {
        robot.focus(c);
        expectLastCall().once();
        robot.pressAndReleaseKey(keyCode, modifiers);
        expectLastCall().once();
      }

      protected void codeToTest() {
        driver.pressAndReleaseKey(c, keyCode, modifiers);
      }
    }.run();
  }

  public void shouldPressAndReleaseKeys() {
    final int[] keys = { 6, 8 };
    new EasyMockTemplate(robot) {
      protected void expectations() {
        robot.focus(c);
        expectLastCall().once();
        robot.pressAndReleaseKeys(keys);
        expectLastCall().once();
      }

      protected void codeToTest() {
        driver.pressAndReleaseKeys(c, keys);
      }
    }.run();
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfKeyArrayIsNull() {
    driver.pressAndReleaseKeys(c, (int[])null);
  }
  
  public void shouldPressKey() {
    final int key = 6;
    new EasyMockTemplate(robot) {
      protected void expectations() {
        robot.focus(c);
        expectLastCall().once();
        robot.pressKey(key);
        expectLastCall().once();
      }

      protected void codeToTest() {
        driver.pressKey(c, key);
      }
    }.run();
  }

  public void shouldReleaseKey() {
    final int key = 6;
    new EasyMockTemplate(robot) {
      protected void expectations() {
        robot.focus(c);
        expectLastCall().once();
        robot.releaseKey(key);
        expectLastCall().once();
      }

      protected void codeToTest() {
        driver.releaseKey(c, key);
      }
    }.run();
  }

  public void shouldGiveFocus() {
    new EasyMockTemplate(robot) {
      protected void expectations() {
        robot.focus(c);
        expectLastCall().once();
      }

      protected void codeToTest() {
        driver.focus(c);
      }
    }.run();
  }

  public void shouldDrag() {
    final Point point = new Point(0, 0);
    new EasyMockTemplate(robot) {
      protected void expectations() {
        dragAndDrop.drag(c, point);
        expectLastCall().once();
      }

      protected void codeToTest() {
        driver.drag(c, point);
      }
    }.run();
  }

  public void shouldDragOver() {
    final Point point = new Point(0, 0);
    new EasyMockTemplate(robot) {
      protected void expectations() {
        dragAndDrop.dragOver(c, point);
        expectLastCall().once();
      }

      protected void codeToTest() {
        driver.dragOver(c, point);
      }
    }.run();
  }

  public void shouldDrop() {
    final Point point = new Point(0, 0);
    new EasyMockTemplate(robot) {
      protected void expectations() {
        dragAndDrop.drop(c, point);
        expectLastCall().once();
      }

      protected void codeToTest() {
        driver.drop(c, point);
      }
    }.run();
  }

  public void shouldReturnIsResizableIfComponentIsDialog() {
    Component c = new JDialog();
    assertThat(driver.isUserResizable(c)).isTrue();
  }

  public void shouldReturnIsResizableIfComponentIsFrame() {
    Component c = new JFrame();
    assertThat(driver.isUserResizable(c)).isTrue();
  }

  public void shouldReturnIsResizableIfComponentIsNotDialogOrFrameAndPlatformIsNotWindowsOrMac() {
    if (isWindowsOrMac()) return;
    Component c = new JTextField();
    assertThat(driver.isUserResizable(c)).isTrue();
  }

  public void shouldReturnIsNotResizableIfComponentIsNotDialogOrFrameAndPlatformIsWindowsOrMac() {
    if (!isWindowsOrMac()) return;
    Component c = new JTextField();
    assertThat(driver.isUserResizable(c)).isFalse();
    return;
  }

  public void shouldReturnIsMovableIfComponentIsDialog() {
    Component c = new JDialog();
    assertThat(driver.isUserMovable(c)).isTrue();
  }

  public void shouldReturnIsMovableIfComponentIsFrame() {
    Component c = new JFrame();
    assertThat(driver.isUserMovable(c)).isTrue();
  }

  public void shouldReturnIsMovableIfComponentIsNotDialogOrFrameAndPlatformIsNotWindowsOrMac() {
    if (isWindowsOrMac()) return;
    Component c = new JTextField();
    assertThat(driver.isUserMovable(c)).isTrue();
  }

  public void shouldReturnIsNotMovableIfComponentIsNotDialogOrFrameAndPlatformIsWindowsOrMac() {
    if (!isWindowsOrMac()) return;
    Component c = new JTextField();
    assertThat(driver.isUserMovable(c)).isFalse();
    return;
  }

  private boolean isWindowsOrMac() {
    return isWindows() || isMacintosh();
  }
  
  public void shouldNotWaitIfComponentIsReady() {
    new EasyMockTemplate(robot) {
      protected void expectations() {
        expect(robot.isReadyForInput(c)).andReturn(true);
      }

      protected void codeToTest() {
        long timeout = 500;
        StopWatch stopWatch = startNewStopWatch();
        assertThat(driver.waitForShowing(c, timeout)).isTrue();
        stopWatch.stop();
        assertThat(stopWatch.ellapsedTime()).isLessThan(timeout);
      }
    }.run();
  }
  
  public void shouldWaitUntilComponentIsReady() {
    new EasyMockTemplate(robot) {
      protected void expectations() {
        expect(robot.isReadyForInput(c)).andReturn(false);
        expect(robot.isReadyForInput(c)).andReturn(true);
      }

      protected void codeToTest() {
        assertThat(driver.waitForShowing(c, 2000)).isTrue();
      }
    }.run();
  }

  public void shouldReturnFalseIfComponentIsNeverReady() {
    new EasyMockTemplate(robot) {
      protected void expectations() {
        expect(robot.isReadyForInput(c)).andReturn(false).atLeastOnce();
      }

      protected void codeToTest() {
        assertThat(driver.waitForShowing(c, 100)).isFalse();
      }
    }.run();
  }
  
  public void shouldReturnFalseIfJPopupMenuIsNeverVisible() {
    final JPopupMenu popupMenu = createMock(JPopupMenu.class);
    final JMenu invoker = createMock(JMenu.class);
    new EasyMockTemplate(robot, popupMenu, invoker) {
      protected void expectations() {
        expect(robot.isReadyForInput(popupMenu)).andReturn(false).atLeastOnce();
        expect(popupMenu.getInvoker()).andReturn(invoker).atLeastOnce();
        robot.jitter(invoker);
        expectLastCall().atLeastOnce();
      }

      protected void codeToTest() {
        assertThat(driver.waitForShowing(popupMenu, 100)).isFalse();
      }
    }.run();
  }
  
}
