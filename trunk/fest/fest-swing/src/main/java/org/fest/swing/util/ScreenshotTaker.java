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

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import static org.fest.util.Files.newFile;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.isEmpty;
import static org.fest.util.Strings.quote;

/**
 * Understands taking screenshots of the desktop.
 *
 * @author Alex Ruiz
 */
public final class ScreenshotTaker {

  public static final String PNG_EXTENSION = "png";
  
  private final Robot robot;
  
  /**
   * Creates a new </code>{@link ScreenshotTaker}</code>.
   * @throws ImageException if a AWT Robot (the responsible for taking screenshots) cannot be instantiated.
   */
  public ScreenshotTaker() {
    try {
      robot = new Robot();
    } catch (AWTException e) {
      throw new ImageException("Unable to create AWT Robot", e);
    }
  }

  /**
   * Takes a screenshot of the desktop and saves it as a PNG file.
   * @param imageFilePath the path of the file to save the screentshot to.
   * @throws ImageException if the given file path is <code>null</code> or empty.
   * @throws ImageException if the given file path does not end with ".png".
   * @throws ImageException if the given file path belongs to a non-empty directory.
   * @throws ImageException if an I/O error prevents the image from being saved as a file.
   */
  public void saveDesktopAsPng(String imageFilePath) {
    validate(imageFilePath);
    BufferedImage screenshot = takeDesktopScreenshot();
    try {
      ImageIO.write(screenshot, PNG_EXTENSION, newFile(imageFilePath));
    } catch (IOException e) {
      throw new ImageException(concat("Unable to save screenshot as ", quote(imageFilePath)), e);
    }
  }

  /** @return the screenshot of the destop. */
  public BufferedImage takeDesktopScreenshot() {
    Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    return robot.createScreenCapture(screen);
  }
  
  private void validate(String imageFilePath) {
    if (isEmpty(imageFilePath)) throw new ImageException("The image path cannot be empty");
    if (!imageFilePath.endsWith(PNG_EXTENSION)) 
      throw new ImageException(concat("The image file should be a ", PNG_EXTENSION.toUpperCase()));     
  }
}
