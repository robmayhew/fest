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

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.easymock.IArgumentMatcher;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Robot;
import org.fest.swing.core.Settings;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.testing.StopWatch;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.core.Timeout.timeout;
import static org.fest.swing.driver.ComponentDriverTest.PerformDefaultAccessibleActionTaskMatcher.eqTask;
import static org.fest.swing.testing.StopWatch.startNewStopWatch;
import static org.fest.swing.util.Platform.*;

/**
 * Tests for <code>{@link ComponentDriver}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentDriverTest {

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

  @Test public void shouldClickComponent() {
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

  @Test public void shouldClickComponentUsingGivenMouseButton() {
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

  @Test public void shouldClickComponentUsingGivenMouseButtonAndTimes() {
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

  @Test public void shouldClickComponentAtGivenPoint() {
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

  @Test public void shouldDoubleClickComponent() {
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

  @Test public void shouldRightClickComponent() {
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

  @Test public void shouldGiveFocusAndWaitForFocusGain() {
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

  @Test public void shouldReturnSettingsFromRobot() {
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

  @Test public void shouldPassIfSizeIsEqualToExpected() {
    Dimension size = new Dimension(10, 10);
    c.setSize(size);
    driver.requireSize(c, size);
  }

  @Test public void shouldFailIfSizeIsNotEqualToExpected() {
    c.setSize(new Dimension(10, 10));
    try {
      driver.requireSize(c, new Dimension(20, 20));
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'size'")
                             .contains("expected:<(20, 20)> but was:<(10, 10)>");
    }
  }

  @Test public void shouldPassIfVisibleAsAnticipated() {
    c.setVisible(true);
    driver.requireVisible(c);
  }

  @Test public void shouldFailIfNotVisibleAndExpectingVisible() {
    c.setVisible(false);
    try {
      driver.requireVisible(c);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'visible'")
                             .contains("expected:<true> but was:<false>");
    }
  }

  @Test public void shouldPassIfNotVisibleAsAnticipated() {
    c.setVisible(false);
    driver.requireNotVisible(c);
  }

  @Test public void shouldFailIfVisibleAndExpectingNotVisible() {
    c.setVisible(true);
    try {
      driver.requireNotVisible(c);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'visible'")
                             .contains("expected:<false> but was:<true>");
    }
  }

  @Test public void shouldPassIfEnabledAsAnticipated() {
    c.setEnabled(true);
    driver.requireEnabled(c);
  }

  @Test public void shouldFailIfNotEnabledAndExpectingEnabled() {
    c.setEnabled(false);
    try {
      driver.requireEnabled(c);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'enabled'")
                             .contains("expected:<true> but was:<false>");
    }
  }

  @Test public void shouldPassIfComponentIsEnabledBeforeTimeout() {
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

  @Test public void shouldPassIfComponentDisabledAsAnticipated() {
    c.setEnabled(false);
    driver.requireDisabled(c);
  }

  @Test public void shouldFailIfEnabledAndExpectingDisabled() {
    c.setEnabled(true);
    try {
      driver.requireDisabled(c);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'enabled'")
                             .contains("expected:<false> but was:<true>");
    }
  }

  @Test public void shouldPressAndReleaseKeys() {
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

  @Test public void shouldPressAndReleaseKey() {
    final int key = 6;
    final int modifier = 8;
    new EasyMockTemplate(robot) {
      protected void expectations() {
        robot.focus(c);
        expectLastCall().once();
        robot.pressAndReleaseKey(key, modifier);
        expectLastCall().once();
      }

      protected void codeToTest() {
        driver.pressAndReleaseKey(c, key, modifier);
      }
    }.run();
  }

  @Test public void shouldPressKey() {
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

  @Test public void shouldReleaseKey() {
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

  @Test public void shouldGiveFocus() {
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

  @Test public void shouldDrag() {
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

  @Test public void shouldDragOver() {
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

  @Test public void shouldDrop() {
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

  @Test public void shouldReturnIsResizableIfComponentIsDialog() {
    Component c = new JDialog();
    assertThat(driver.isUserResizable(c)).isTrue();
  }

  @Test public void shouldReturnIsResizableIfComponentIsFrame() {
    Component c = new JFrame();
    assertThat(driver.isUserResizable(c)).isTrue();
  }

  @Test public void shouldReturnIsResizableIfComponentIsNotDialogOrFrameAndPlatformIsNotWindowsOrMac() {
    if (isWindowsOrMac()) return;
    Component c = new JTextField();
    assertThat(driver.isUserResizable(c)).isTrue();
  }

  @Test public void shouldReturnIsNotResizableIfComponentIsNotDialogOrFrameAndPlatformIsWindowsOrMac() {
    if (!isWindowsOrMac()) return;
    Component c = new JTextField();
    assertThat(driver.isUserResizable(c)).isFalse();
    return;
  }

  @Test public void shouldReturnIsMovableIfComponentIsDialog() {
    Component c = new JDialog();
    assertThat(driver.isUserMovable(c)).isTrue();
  }

  @Test public void shouldReturnIsMovableIfComponentIsFrame() {
    Component c = new JFrame();
    assertThat(driver.isUserMovable(c)).isTrue();
  }

  @Test public void shouldReturnIsMovableIfComponentIsNotDialogOrFrameAndPlatformIsNotWindowsOrMac() {
    if (isWindowsOrMac()) return;
    Component c = new JTextField();
    assertThat(driver.isUserMovable(c)).isTrue();
  }

  @Test public void shouldReturnIsNotMovableIfComponentIsNotDialogOrFrameAndPlatformIsWindowsOrMac() {
    if (!isWindowsOrMac()) return;
    Component c = new JTextField();
    assertThat(driver.isUserMovable(c)).isFalse();
    return;
  }

  private boolean isWindowsOrMac() {
    return isWindows() || isMacintosh();
  }
  
  @Test public void shouldPerformAccessibleAction() {
    new EasyMockTemplate(robot) {
      protected void expectations() {
        robot.invokeLater(same(c), eqTask(new PerformDefaultAccessibleActionTask(c)));
        expectLastCall().once();
      }

      protected void codeToTest() {
        driver.performAccessibleActionOf(c);
      }
    }.run();
  }

  static class PerformDefaultAccessibleActionTaskMatcher implements IArgumentMatcher {
    static PerformDefaultAccessibleActionTask eqTask(PerformDefaultAccessibleActionTask expected) {
      reportMatcher(new PerformDefaultAccessibleActionTaskMatcher(expected));
      return expected;
    }
    
    private final PerformDefaultAccessibleActionTask expected;
    
    PerformDefaultAccessibleActionTaskMatcher(PerformDefaultAccessibleActionTask expected) {
      this.expected = expected;
    }
    
    public boolean matches(Object o) {
      if (!(o instanceof PerformDefaultAccessibleActionTask)) return false;
      PerformDefaultAccessibleActionTask actual = (PerformDefaultAccessibleActionTask)o;
      return expected.action == actual.action; 
    }
    
    public void appendTo(StringBuffer buffer) {}
  }
  
  @Test public void shouldNotWaitIfComponentIsReady() {
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
  
  @Test public void shouldWaitUntilComponentIsReady() {
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

  @Test public void shouldReturnFalseComponentIsReady() {
    new EasyMockTemplate(robot) {
      protected void expectations() {
        expect(robot.isReadyForInput(c)).andReturn(false).atLeastOnce();
      }

      protected void codeToTest() {
        assertThat(driver.waitForShowing(c, 100)).isFalse();
      }
    }.run();
  }
}
