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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link WindowFinder}</code>.
 *
 * @author Alex Ruiz
 */
public class WindowFinderTest {

  private FrameFixture login;
  private LoginWindow loginWindow;
  
  @BeforeMethod public void setUp() {
    loginWindow = new LoginWindow();
    login = new FrameFixture(loginWindow);
    login.show();
  }
  
  @AfterMethod public void tearDown() {
    login.cleanUp();
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
    WindowFinder.findFrame("main").withTimeout(-20);
  }
  
  @Test(expectedExceptions = ComponentLookupException.class) 
  public void shouldTimeOutIfMainFrameNotFound() {
    loginWindow.loginTime(5000);
    login.button("login").click();
    WindowFinder.findFrame("main").withTimeout(10).using(login.robot);
  }
  
  @Test(dependsOnMethods = "shouldTimeOutIfMainFrameNotFound")
  public void shouldFindMainFrameByNameAfterLogin() {
    loginWindow.loginTime(500);
    login.button("login").click();
    FrameFixture main = WindowFinder.findFrame("main").using(login.robot);
    assertThat(main.target).isInstanceOf(MainWindow.class);
  }
  
  @Test(dependsOnMethods = "shouldTimeOutIfMainFrameNotFound")
  public void shouldFindMainFrameByNameAfterLoginUsingTimeUnit() {
    loginWindow.loginTime(500);
    login.button("login").click();
    FrameFixture main = WindowFinder.findFrame("main").withTimeout(2, SECONDS).using(login.robot);
    assertThat(main.target).isInstanceOf(MainWindow.class);
  }
  
  @Test(dependsOnMethods = "shouldTimeOutIfMainFrameNotFound")
  public void shouldFindMainFrameByTypeAfterLogin() {
    loginWindow.loginTime(500);
    login.button("login").click();
    FrameFixture main = WindowFinder.findFrame(MainWindow.class).using(login.robot);
    assertThat(main.target).isInstanceOf(MainWindow.class);
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
    WindowFinder.findDialog("settings").withTimeout(-20);
  }
  
  @Test(expectedExceptions = ComponentLookupException.class) 
  public void shouldTimeOutIfSettingsDialogNotFound() {
    loginWindow.showSettingsTime(5000);
    login.button("showSettings").click();
    WindowFinder.findDialog("settings").withTimeout(10).using(login.robot);
  }
  
  @Test(dependsOnMethods = "shouldTimeOutIfSettingsDialogNotFound")
  public void shouldFindSettingsDialogByNameAfterLoadingSettings() {
    loginWindow.showSettingsTime(500);
    login.button("showSettings").click();
    DialogFixture settings = WindowFinder.findDialog("settings").using(login.robot);
    assertThat(settings.target).isInstanceOf(SettingsDialog.class);
  }
  
  @Test(dependsOnMethods = "shouldTimeOutIfSettingsDialogNotFound")
  public void shouldFindSettingsDialogByTypeAfterLoadingSettings() {
    loginWindow.showSettingsTime(500);
    login.button("showSettings").click();
    DialogFixture settings = WindowFinder.findDialog(SettingsDialog.class).using(login.robot);
    assertThat(settings.target).isInstanceOf(SettingsDialog.class);
  }

}
