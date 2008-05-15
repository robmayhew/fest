/*
 * Created on Jul 30, 2007
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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.finder;

import java.awt.Dialog;
import java.awt.Frame;
import java.util.concurrent.TimeUnit;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.finder.LauncherWindow.DialogToLaunch;
import org.fest.swing.finder.LauncherWindow.FrameToLaunch;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.FrameFixture;

import static java.util.concurrent.TimeUnit.SECONDS;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link WindowFinder}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class WindowFinderTest {

  private FrameFixture launcher;
  private Robot robot;
  private LauncherWindow launcherWindow;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    launcherWindow = new LauncherWindow(WindowFinderTest.class);
    launcher = new FrameFixture(robot, launcherWindow);
    launcher.show();
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowExceptionIfFrameNameIsNull() {
    WindowFinder.findFrame((String)null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowExceptionIfFrameNameIsEmpty() {
    WindowFinder.findFrame("");
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowExceptionIfFrameTypeIsNull() {
    WindowFinder.findFrame((Class<Frame>)null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowExceptionIfFrameMatcherIsNull() {
    GenericTypeMatcher<JFrame> matcher = null;
    WindowFinder.findFrame(matcher);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowExceptionIfFrameSearchTimeoutIsNegative() {
    WindowFinder.findFrame("frame").withTimeout(-20);
  }

  @Test(expectedExceptions = WaitTimedOutError.class)
  public void shouldTimeOutIfFrameNotFound() {
    launchFrame(5000);
    WindowFinder.findFrame("frame").withTimeout(10).using(launcher.robot);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfTimeUnitToFindFrameIsNull() {
    WindowFinder.findFrame("frame").withTimeout(10, null).using(launcher.robot);
  }

  @Test(expectedExceptions = WaitTimedOutError.class)
  public void shouldTimeOutWhenUsingTimeUnitsIfFrameNotFound() {
    launchFrame(5000);
    WindowFinder.findDialog("frame").withTimeout(10, TimeUnit.MILLISECONDS).using(launcher.robot);
  }

  @Test public void shouldFindFrameByNameAfterLogin() {
    launchFrame();
    FrameFixture frame = WindowFinder.findFrame("frame").using(launcher.robot);
    assertThat(frame.target).isInstanceOf(FrameToLaunch.class);
  }

  @Test public void shouldFindFrameByNameAfterLoginUsingTimeUnit() {
    launchFrame();
    FrameFixture frame = WindowFinder.findFrame("frame").withTimeout(2, SECONDS).using(launcher.robot);
    assertThat(frame.target).isInstanceOf(FrameToLaunch.class);
  }

  @Test public void shouldFindFrameUsingMatcherAfterLogin() {
    launchFrame();
    GenericTypeMatcher<JFrame> matcher = new GenericTypeMatcher<JFrame>() {
      protected boolean isMatching(JFrame frame) {
        return "frame".equals(frame.getName()) && frame.isShowing();
      }
    };
    FrameFixture frame = WindowFinder.findFrame(matcher).using(launcher.robot);
    assertThat(frame.target).isInstanceOf(FrameToLaunch.class);
  }

  @Test public void shouldFindFrameByTypeAfterLogin() {
    launchFrame();
    FrameFixture frame = WindowFinder.findFrame(FrameToLaunch.class).using(launcher.robot);
    assertThat(frame.target).isInstanceOf(FrameToLaunch.class);
  }

  private void launchFrame() {
    launchFrame(500);
  }

  private void launchFrame(int delay) {
    launcherWindow.frameLaunchDelay(delay);
    launcher.button("launchFrame").click();
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowExceptionIfDialogNameIsNull() {
    WindowFinder.findDialog((String)null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowExceptionIfDialogNameIsEmpty() {
    WindowFinder.findDialog("");
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowExceptionIfDialogTypeIsNull() {
    WindowFinder.findDialog((Class<Dialog>)null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowExceptionIfDialogMatcherIsNull() {
    GenericTypeMatcher<JDialog> matcher = null;
    WindowFinder.findDialog(matcher);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowExceptionIfDialogSearchTimeoutIsNegative() {
    WindowFinder.findDialog("dialog").withTimeout(-20);
  }

  @Test(expectedExceptions = WaitTimedOutError.class)
  public void shouldTimeOutIfDialogNotFound() {
    launchDialog(5000);
    WindowFinder.findDialog("dialog").withTimeout(5).using(launcher.robot);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfTimeUnitToFindDialogIsNull() {
    WindowFinder.findDialog("dialog").withTimeout(10, null).using(launcher.robot);
  }

  @Test(expectedExceptions = WaitTimedOutError.class)
  public void shouldTimeOutWhenUsingTimeUnitsIfDialogNotFound() {
    launchDialog(5000);
    WindowFinder.findDialog("dialog").withTimeout(10, TimeUnit.MILLISECONDS).using(launcher.robot);
  }

  @Test public void shouldFindDialogByNameAfterLoadingSettings() {
    launchDialog();
    DialogFixture dialog = WindowFinder.findDialog("dialog").using(launcher.robot);
    assertThat(dialog.target).isInstanceOf(DialogToLaunch.class);
  }

  @Test public void shouldFindDialogByTypeAfterLoadingSettings() {
    launchDialog();
    DialogFixture dialog = WindowFinder.findDialog(DialogToLaunch.class).using(launcher.robot);
    assertThat(dialog.target).isInstanceOf(DialogToLaunch.class);
  }

  @Test public void shouldFindDialogUsingMatcherAfterLoadingSettings() {
    launchDialog();
    GenericTypeMatcher<JDialog> matcher = new GenericTypeMatcher<JDialog>() {
      protected boolean isMatching(JDialog dialog) {
        return "dialog".equals(dialog.getName()) && dialog.isShowing();
      }
    };
    DialogFixture dialog = WindowFinder.findDialog(matcher).using(launcher.robot);
    assertThat(dialog.target).isInstanceOf(DialogToLaunch.class);
  }

  private void launchDialog() {
    launchDialog(500);
  }

  private void launchDialog(int delay) {
    launcherWindow.dialogLaunchDelay(delay);
    launcher.button("launchDialog").click();
  }
}
