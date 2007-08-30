/*
 * Created on Jun 20, 2007
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
package org.fest.swing.fixture;

import abbot.tester.ComponentTester;

import org.fest.swing.fixture.JOptionPaneFixtureTest.CustomWindow;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Fix for <a href="http://code.google.com/p/fest/issues/detail?id=13&can=2&q=" target="_blank">issue 13</a>.
 *
 * @author Alex Ruiz
 */
public class FixForWrongOptionPaneFixtureFoundTest {

  private FrameFixture frameFixture;
  private CustomWindow window;
  
  @BeforeMethod public void setUp() {
    frameFixture = new FrameFixture(new CustomWindow());
    window = frameFixture.targetCastedTo(CustomWindow.class);
    frameFixture.show();
  }
  
  @Test(dataProvider = "messages") 
  public void shouldFindVisibleJOptionPanesOnly(String message) {
    showMessageWithText(message);
    frameFixture.optionPane().requireMessage(message);
  }
  
  @DataProvider(name="messages")
  Object[][] messages() {
    return new Object[][]{
        {"first"},
        {"second"},
        {"third"},
        {"fourth"},
        {"fifth"},
    };
  }

  private void showMessageWithText(String text) {
    window.showMessageWithText(text);
    clickWindowButton();
  }

  private void clickWindowButton() {
    ComponentTester.getTester(window.button).actionClick(window.button);
  }
  
  @AfterMethod public void tearDown() {
    frameFixture.cleanUp();
  }
}
