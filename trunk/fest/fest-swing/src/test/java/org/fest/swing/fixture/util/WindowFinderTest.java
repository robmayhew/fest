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

import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.ComponentLookupException;
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
  public void shouldThrowExceptionIfFrameTimeoutIsNegative() {
    WindowFinder.findFrame("main").timeout(-20);
  }
  
  @Test(expectedExceptions = ComponentLookupException.class) 
  public void shouldTimeOut() {
    loginWindow.loginTime(5000);
    login.button("login").click();
    WindowFinder.findFrame("main").timeout(10).with(login.robot);
  }
  
  @Test(dependsOnMethods = "shouldTimeOut")
  public void shouldFindMainFrameByNameAfterLogin() {
    loginWindow.loginTime(500);
    login.button("login").click();
    FrameFixture main = WindowFinder.findFrame("main").with(login.robot);
    assertThat(main.target).isInstanceOf(MainWindow.class);
  }
  
  @Test(dependsOnMethods = "shouldTimeOut")
  public void shouldFindMainFrameByTypeAfterLogin() {
    loginWindow.loginTime(500);
    login.button("login").click();
    FrameFixture main = WindowFinder.findFrame(MainWindow.class).with(login.robot);
    assertThat(main.target).isInstanceOf(MainWindow.class);
  }
}
