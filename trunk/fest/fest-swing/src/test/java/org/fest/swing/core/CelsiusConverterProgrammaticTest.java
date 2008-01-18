/*
 * Created on Sep 29, 2006
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
 * Copyright @2006 the original author or authors.
 */
package org.fest.swing.core;

import java.awt.Component;

import javax.swing.JLabel;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import abbot.tester.ComponentTester;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;

/**
 * Understands programmatic GUI tests for <code>{@link CelsiusConverter}</code>.
 *
 * @author Alex Ruiz
 */
public class CelsiusConverterProgrammaticTest {

  private RobotFixture robot;
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    robot.showWindow(new CelsiusConverter());
  }
 
  @Test public void shouldBeAbleToConvertNegativeNumber() {
    convertToFarenheit("-45");
    assertMatchingResult("-49 Fahrenheit");
  }

  @Test public void shouldBeUnableToConvertNaNInput() {
    convertToFarenheit("abc");
    assertMatchingResult("Unable to convert text to Fahrenheit");    
  }
  
  private void convertToFarenheit(String celsius) {
    ComponentTester tester = tester();
    tester.actionKeyString(celsius);
    Component convertButton = robot.finder().findByName("convertButton");
    tester.actionClick(convertButton);
  }

  private ComponentTester tester() {
    Component celsiusTextInput = robot.finder().findByName("celsiusTextInput");
    return ComponentTester.getTester(celsiusTextInput);
  }
  
  private void assertMatchingResult(String expected) {
    JLabel fahrenheitLabel = robot.finder().findByName("fahrenheitLabel", JLabel.class);
    assertThat(fahrenheitLabel.getText()).isEqualTo(expected);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
}
