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
package com.jtuzi.fest;

import java.awt.Component;

import javax.swing.JLabel;

import com.jtuzi.fest.AbbotFixture;

import abbot.tester.ComponentTester;


import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Understands programmatic Abbot unit tests for <code>{@link CelsiusConverter}</code>.
 *
 * @author Alex Ruiz
 */
public class CelsiusConverterProgrammaticTest {

  private AbbotFixture abbotFixture;  
  
  @BeforeMethod public void setUp() {
    abbotFixture = new AbbotFixture();
    abbotFixture.showWindow(new CelsiusConverter());
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
    Component convertButton = abbotFixture.findByName("convertButton");
    tester.actionClick(convertButton);
  }

  private ComponentTester tester() {
    Component celsiusTextInput = abbotFixture.findByName("celsiusTextInput");
    return ComponentTester.getTester(celsiusTextInput);
  }
  
  private void assertMatchingResult(String expected) {
    JLabel fahrenheitLabel = abbotFixture.findByName("fahrenheitLabel", JLabel.class);
    assertEquals(fahrenheitLabel.getText(), expected);
  }

  @AfterMethod public void tearDown() {
    abbotFixture.cleanUp();
  }
}
