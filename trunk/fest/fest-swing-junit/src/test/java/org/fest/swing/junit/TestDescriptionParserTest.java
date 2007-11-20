/*
 * Created on Nov 19, 2007
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
package org.fest.swing.junit;

import org.junit.runner.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.junit.TestDescriptionParser.ParseResult;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link TestDescriptionParser}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class TestDescriptionParserTest {

  private TestDescriptionParser parser;
  
  @BeforeMethod public void setUp() {
    parser = new TestDescriptionParser();
  }
  
  @Test public void shouldParseGUITest() {
    Class<FakeGUITest> testClass = FakeGUITest.class;
    String testMethodName = "successfulGUITest";
    Description description = Description.createTestDescription(testClass, testMethodName);
    ParseResult result = parser.parse(description);
    assertThat(result.testClass()).isSameAs(testClass);
    assertThat(result.testMethod().getName()).isEqualTo(testMethodName);
    assertThat(result.isGUITest()).isTrue();
  }

  @Test public void shouldParseNonGUITest() {
    Class<FakeGUITest> testClass = FakeGUITest.class;
    String testMethodName = "successfulNonGUITest";
    Description description = Description.createTestDescription(testClass, testMethodName);
    ParseResult result = parser.parse(description);
    assertThat(result.testClass()).isSameAs(testClass);
    assertThat(result.testMethod().getName()).isEqualTo(testMethodName);
    assertThat(result.isGUITest()).isFalse();
  }
}
