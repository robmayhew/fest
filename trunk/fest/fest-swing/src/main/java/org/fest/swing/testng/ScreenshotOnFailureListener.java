/*
 * Created on May 6, 2007
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.testng;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.io.File.separator;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.isEmpty;
import static org.fest.util.Strings.join;
import static org.fest.util.Strings.quote;

import org.fest.swing.util.ImageException;
import org.fest.swing.util.ScreenshotTaker;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;

/**
 * Understands a TestNG listener that takes a screenshot when a test fails.
 * 
 * @author Alex Ruiz
 */
public class ScreenshotOnFailureListener extends AbstractTestListener {

  private static final String IMAGE_FILE_EXTENSION = "png";
  
  private static Logger logger = Logger.getAnonymousLogger();

  private ScreenshotTaker screenshotTaker;
  private String output;
  private boolean ready;
  
  public ScreenshotOnFailureListener() {
    try {
      screenshotTaker = new ScreenshotTaker();
    } catch (ImageException e) {
      logger.log(Level.SEVERE, "Unable to create ScreenshotTaker", e);
    }
  }
  
  /** {@inheritDoc} */
  @Override public void onStart(ITestContext context) {
    output = context.getOutputDirectory();
    logger.info(concat("TestNG output directory: ", quote(output)));
    ready = !isEmpty(output) && screenshotTaker != null;
  }

  /** {@inheritDoc} */
  @Override public void onTestFailure(ITestResult result) {
    if (!ready) return;
    String screenshotFileName = takeScreenshotAndReturnFileName(result);
    if (isEmpty(screenshotFileName)) return;
    Reporter.setCurrentTestResult(result);
    Reporter.log(concat("<a href=\"", screenshotFileName, "\">Screenshot</a>"));
  }

  private String takeScreenshotAndReturnFileName(ITestResult result) {
    String imageName = join(classNameFrom(result), methodNameFrom(result), IMAGE_FILE_EXTENSION).with(".");
    String imagePath = concat(output, separator, imageName);
    try {
      screenshotTaker.saveDesktopAsPng(imagePath);
    } catch (ImageException e) {
      return null;
    }
    return imageName;
  }

  private String methodNameFrom(ITestResult result) {
    return result.getMethod().getMethodName();
  }

  private String classNameFrom(ITestResult result) {
    return result.getTestClass().getName();
  }
}
