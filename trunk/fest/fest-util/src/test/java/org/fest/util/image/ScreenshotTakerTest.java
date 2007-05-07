/*
 * Created on May 6, 2007
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
package org.fest.swing.util;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ScreenshotTaker}</code>.
 *
 * @author Alex Ruiz
 */
public class ScreenshotTakerTest {

  private static final String JAVA_AWT_HEADLESS = "java.awt.headless";

  @Test(expectedExceptions = ImageException.class) 
  public void shouldThrowErrorIfRobotCannotBeCreated() {
    boolean headless = Boolean.valueOf(System.getProperty(JAVA_AWT_HEADLESS));
    updateHeadlessProperty(true);
    new ScreenshotTaker();
    updateHeadlessProperty(headless);
  }

  private void updateHeadlessProperty(boolean value) {
    System.setProperty(JAVA_AWT_HEADLESS, String.valueOf(value));
  }
  
  @Test(expectedExceptions = ImageException.class, dependsOnMethods = "shouldThrowErrorIfRobotCannotBeCreated")
  public void shouldThrowErrorIfFilePathIsNull() {
    ScreenshotTaker taker = new ScreenshotTaker();
    taker.saveDesktopAsPng(null);
  }
  
  @Test(expectedExceptions = ImageException.class, dependsOnMethods = "shouldThrowErrorIfRobotCannotBeCreated")
  public void shouldThrowErrorIfFilePathIsEmpty() {
    ScreenshotTaker taker = new ScreenshotTaker();
    taker.saveDesktopAsPng("");
  }
  
  @Test(expectedExceptions = ImageException.class, dependsOnMethods = "shouldThrowErrorIfRobotCannotBeCreated")
  public void shouldThrowErrorIfFilePathNotEndingWithPng() {
    ScreenshotTaker taker = new ScreenshotTaker();
    taker.saveDesktopAsPng("somePathWithoutPng");
  }
}
