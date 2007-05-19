/*
 * Created on May 16, 2007
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
package org.fest.swing.testng;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.Reflection.field;

import static org.fest.swing.util.ImageAssert.assertScreenshotTaken;

import static org.fest.util.Files.temporaryFolderPath;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.join;

import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ScreenshotOnFailureListener}</code>.
 *
 * @author Yvonne Wang
 */
public class ScreenshotOnFailureListenerTest {

  private TestNGFixture testNG;
  private ScreenshotOnFailureListener listener;
  
  @BeforeMethod public void setUp() {
    testNG = new TestNGFixture();
    listener = new ScreenshotOnFailureListener();
  }

  @Test public void shouldGetOutputFolderOnStart() {
    String outputFolder = "output";
    testNG.testContext.setOutputDirectory(outputFolder);
    listener.onStart(testNG.testContext);
    String actualOutputFolder = field("output").ofType(String.class).in(listener).get();
    assertThat(actualOutputFolder).isEqualTo(outputFolder);
  }
  
  @Test public void shouldTakeScreenshotOnTestFailure() throws Exception {
    String outputFolder = temporaryFolderPath();
    Date now = new GregorianCalendar().getTime();
    String className = new SimpleDateFormat("yyMMdd").format(now);
    String methodName = new SimpleDateFormat("hhmmss").format(now);
    testNG.testContext.setOutputDirectory(outputFolder);
    testNG.testClass.setName(className);
    testNG.testMethod.setMethodName(methodName);
    listener.onStart(testNG.testContext);
    listener.onTestFailure(testNG.testResult);
    String imageFileName = join(className, methodName, "png").with(".");
    String screenshotPath = concat(outputFolder, imageFileName);
    assertScreenshotTaken(screenshotPath);
    List<String> reporterOutput = Reporter.getOutput();
    assertThat(reporterOutput.size()).isEqualTo(1);
    assertThat(reporterOutput.get(0)).isEqualTo(concat("<a href=\"", imageFileName, "\">Screenshot</a>"));
  }

  private static class TestNGFixture {
    final TestContextStub testContext;
    final ClassStub testClass;
    final TestNGMethodStub testMethod;
    final TestResultStub testResult;
    
    TestNGFixture() {
      testContext = new TestContextStub();
      testClass = new ClassStub();
      testMethod = new TestNGMethodStub();
      testResult = new TestResultStub();
      testResult.setTestClass(testClass);
      testResult.setMethod(testMethod);
    }
  }
}
