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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.finder;

import java.awt.Dialog;
import java.awt.Frame;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.finder.LauncherWindow.DialogToLaunch;
import org.fest.swing.finder.LauncherWindow.FrameToLaunch;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.FrameFixture;

import static java.util.concurrent.TimeUnit.SECONDS;

import static org.fest.assertions.Assertions.assertThat;
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
  private LauncherWindow launcherWindow;
  
  @BeforeMethod public void setUp() {
    launcherWindow = new LauncherWindow(WindowFinderTest.class);
    launcher = new FrameFixture(launcherWindow);
    launcher.show();
  }
  
  @AfterMethod public void tearDown() {
    launcher.cleanUp();
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
  public void shouldThrowExceptionIfFrameSearchTimeoutIsNegative() {
    WindowFinder.findFrame("frame").withTimeout(-20);
  }
  
  @Test(expectedExceptions = WaitTimedOutError.class)
  public void shouldTimeOutIfFrameNotFound() {
    launcherWindow.frameLaunchDelay(5000);
    launcher.button("launchFrame").click();
    WindowFinder.findFrame("frame").withTimeout(10).using(launcher.robot);
  }
  
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfTimeUnitToFindFrameIsNull() {
    WindowFinder.findFrame("frame").withTimeout(10, null).using(launcher.robot);
  }

  @Test(expectedExceptions = WaitTimedOutError.class) 
  public void shouldTimeOutWhenUsingTimeUnitsIfFrameNotFound() {
    launcherWindow.frameLaunchDelay(5000);
    launcher.button("launchFrame").click();
    WindowFinder.findDialog("frame").withTimeout(10, TimeUnit.MILLISECONDS).using(launcher.robot);
  }

  @Test(dependsOnMethods = "shouldTimeOutIfFrameNotFound")
  public void shouldFindFrameByNameAfterLogin() {
    launcherWindow.frameLaunchDelay(500);
    launcher.button("launchFrame").click();
    FrameFixture frame = WindowFinder.findFrame("frame").using(launcher.robot);
    assertThat(frame.target).isInstanceOf(FrameToLaunch.class);
  }
  
  @Test(dependsOnMethods = "shouldTimeOutIfFrameNotFound")
  public void shouldFindFrameByNameAfterLoginUsingTimeUnit() {
    launcherWindow.frameLaunchDelay(500);
    launcher.button("launchFrame").click();
    FrameFixture frame = WindowFinder.findFrame("frame").withTimeout(2, SECONDS).using(launcher.robot);
    assertThat(frame.target).isInstanceOf(FrameToLaunch.class);
  }
  
  @Test(dependsOnMethods = "shouldTimeOutIfFrameNotFound")
  public void shouldFindFrameByTypeAfterLogin() {
    launcherWindow.frameLaunchDelay(500);
    launcher.button("launchFrame").click();
    FrameFixture frame = WindowFinder.findFrame(FrameToLaunch.class).using(launcher.robot);
    assertThat(frame.target).isInstanceOf(FrameToLaunch.class);
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
  public void shouldThrowExceptionIfDialogSearchTimeoutIsNegative() {
    WindowFinder.findDialog("dialog").withTimeout(-20);
  }
  
  @Test(expectedExceptions = WaitTimedOutError.class) 
  public void shouldTimeOutIfDialogNotFound() {
    launcherWindow.dialogLaunchDelay(5000);
    launcher.button("launchDialog").click();
    WindowFinder.findDialog("dialog").withTimeout(10).using(launcher.robot);
  }
  
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfTimeUnitToFindDialogIsNull() {
    WindowFinder.findDialog("dialog").withTimeout(10, null).using(launcher.robot);
  }
  
  @Test(expectedExceptions = WaitTimedOutError.class) 
  public void shouldTimeOutWhenUsingTimeUnitsIfDialogNotFound() {
    launcherWindow.dialogLaunchDelay(5000);
    launcher.button("launchDialog").click();
    WindowFinder.findDialog("dialog").withTimeout(10, TimeUnit.MILLISECONDS).using(launcher.robot);
  }

  @Test(dependsOnMethods = "shouldTimeOutIfDialogNotFound")
  public void shouldFindDialogByNameAfterLoadingSettings() {
    launcherWindow.dialogLaunchDelay(500);
    launcher.button("launchDialog").click();
    DialogFixture dialog = WindowFinder.findDialog("dialog").using(launcher.robot);
    assertThat(dialog.target).isInstanceOf(DialogToLaunch.class);
  }
  
  @Test(dependsOnMethods = "shouldTimeOutIfDialogNotFound")
  public void shouldFindDialogByTypeAfterLoadingSettings() {
    launcherWindow.dialogLaunchDelay(500);
    launcher.button("launchDialog").click();
    DialogFixture dialog = WindowFinder.findDialog(DialogToLaunch.class).using(launcher.robot);
    assertThat(dialog.target).isInstanceOf(DialogToLaunch.class);
  }
}
