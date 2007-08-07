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
package org.fest.swing.fixture.util;

import java.awt.Dialog;
import java.awt.Frame;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.ComponentLookupException;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.util.LauncherWindow.DialogToLaunch;
import org.fest.swing.fixture.util.LauncherWindow.FrameToLaunch;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link WindowFinder}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class WindowFinderTest {

  private FrameFixture launcher;
  private LauncherWindow launcherWindow;
  
  @BeforeMethod public void setUp() {
    launcherWindow = new LauncherWindow();
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
  
  @Test(expectedExceptions = ComponentLookupException.class) 
  public void shouldTimeOutIfMainFrameNotFound() {
    launcherWindow.frameLaunchDelay(5000);
    launcher.button("launchFrame").click();
    WindowFinder.findFrame("frame").withTimeout(10).using(launcher.robot);
  }
  
  @Test(dependsOnMethods = "shouldTimeOutIfMainFrameNotFound")
  public void shouldFindMainFrameByNameAfterLogin() {
    launcherWindow.frameLaunchDelay(500);
    launcher.button("launchFrame").click();
    FrameFixture frame = WindowFinder.findFrame("frame").using(launcher.robot);
    assertThat(frame.target).isInstanceOf(FrameToLaunch.class);
  }
  
  @Test(dependsOnMethods = "shouldTimeOutIfMainFrameNotFound")
  public void shouldFindMainFrameByNameAfterLoginUsingTimeUnit() {
    launcherWindow.frameLaunchDelay(500);
    launcher.button("launchFrame").click();
    FrameFixture frame = WindowFinder.findFrame("frame").withTimeout(2, SECONDS).using(launcher.robot);
    assertThat(frame.target).isInstanceOf(FrameToLaunch.class);
  }
  
  @Test(dependsOnMethods = "shouldTimeOutIfMainFrameNotFound")
  public void shouldFindMainFrameByTypeAfterLogin() {
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
  
  @Test(expectedExceptions = ComponentLookupException.class) 
  public void shouldTimeOutIfSettingsDialogNotFound() {
    launcherWindow.dialogLaunchDelay(5000);
    launcher.button("launchDialog").click();
    WindowFinder.findDialog("dialog").withTimeout(10).using(launcher.robot);
  }
  
  @Test(dependsOnMethods = "shouldTimeOutIfSettingsDialogNotFound")
  public void shouldFindSettingsDialogByNameAfterLoadingSettings() {
    launcherWindow.dialogLaunchDelay(500);
    launcher.button("launchDialog").click();
    DialogFixture dialog = WindowFinder.findDialog("dialog").using(launcher.robot);
    assertThat(dialog.target).isInstanceOf(DialogToLaunch.class);
  }
  
  @Test(dependsOnMethods = "shouldTimeOutIfSettingsDialogNotFound")
  public void shouldFindSettingsDialogByTypeAfterLoadingSettings() {
    launcherWindow.dialogLaunchDelay(500);
    launcher.button("launchDialog").click();
    DialogFixture dialog = WindowFinder.findDialog(DialogToLaunch.class).using(launcher.robot);
    assertThat(dialog.target).isInstanceOf(DialogToLaunch.class);
  }
}
