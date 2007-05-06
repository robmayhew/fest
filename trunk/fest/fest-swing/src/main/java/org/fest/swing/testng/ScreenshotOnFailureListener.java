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

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import static java.io.File.separator;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.isEmpty;
import static org.fest.util.Strings.join;
import static org.fest.util.Strings.quote;

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

  private String output;
  private Robot robot;
  private boolean ready;
  
  public ScreenshotOnFailureListener() {
    try {
      robot = new Robot();
    } catch (AWTException e) {
      logger.log(Level.SEVERE, "Unable to create AWT Robot.", e);
    }
  }
  
  @Override public void onStart(ITestContext context) {
    output = context.getOutputDirectory();
    logger.info(concat("TestNG output directory: ", quote(output)));
    ready = !isEmpty(output) && robot != null;
  }

  @Override public void onTestFailure(ITestResult result) {
    if (!ready) return;
    String screenshotFileName = takeScreenshotAndReturnFileName(result);
    if (isEmpty(screenshotFileName)) return;
    Reporter.setCurrentTestResult(result);
    Reporter.log(concat("<a href=\"", screenshotFileName, "\">Screenshot</a>"));
  }

  private String takeScreenshotAndReturnFileName(ITestResult result) {
    Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    BufferedImage screenshot = robot.createScreenCapture(screen);
    String name = join(".", result.getTestClass().getName(), result.getMethod().getMethodName(), IMAGE_FILE_EXTENSION);
    File imageFile = new File(concat(output, separator, name));
    try {
      ImageIO.write(screenshot, IMAGE_FILE_EXTENSION, imageFile);
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Unable to take screenshot", e);
      return null;
    }
    return name;
  }
}
