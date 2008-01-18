/*
 * Created on Dec 19, 2007
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
package org.fest.swing.core;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import abbot.tester.Robot;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link Settings}</code>.
 *
 * @author Alex Ruiz
 */
public class SettingsTest {

  private int originalAutoDelay;

  @BeforeMethod public void setUp() {
    originalAutoDelay = Robot.getAutoDelay();
  }
  
  @AfterMethod public void tearDown() {
    Robot.setAutoDelay(originalAutoDelay);
  }
  
  @Test(dataProvider = "autoDelayProvider") 
  public void shouldUpdateAndReturnRobotAutoDelay(int delay) {
    Settings.robotAutoDelay(delay);
    assertThat(Settings.robotAutoDelay()).isEqualTo(Robot.getAutoDelay()).isEqualTo(delay);
  }

  @DataProvider(name="autoDelayProvider")
  public Object[][] autoDelayProvider() {
    return new Object[][] {
        { 100 },
        { 200 },
        { 68 }
    };
  }
}
