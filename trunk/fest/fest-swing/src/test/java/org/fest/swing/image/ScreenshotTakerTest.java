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
package org.fest.swing.image;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.UUID;

import javax.swing.JButton;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.testing.TestFrame;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.ImageAssert.read;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.testing.TestFrame.showInTest;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Files.temporaryFolderPath;
import static org.fest.util.Strings.concat;

/**
 * Tests for <code>{@link ScreenshotTaker}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ScreenshotTakerTest {

  private static final BufferedImage NO_IMAGE = null;
  private ScreenshotTaker taker;

  @BeforeMethod public void setUp() {
    taker = new ScreenshotTaker();
  }

  @Test(expectedExceptions = ImageException.class)
  public void shouldThrowErrorIfFilePathIsNull() {
    taker.saveImage(NO_IMAGE, null);
  }
  
  @Test(expectedExceptions = ImageException.class)
  public void shouldThrowErrorIfFilePathIsEmpty() {
    taker.saveImage(NO_IMAGE, "");
  }
  
  @Test(expectedExceptions = ImageException.class)
  public void shouldThrowErrorIfFilePathNotEndingWithPng() {
    taker.saveImage(NO_IMAGE, "somePathWithoutPng");
  }

  @Test public void shouldTakeDesktopScreenshotAndSaveItInGivenPath() throws Exception {
    String imagePath = concat(temporaryFolderPath(), imageFileName());
    taker.saveDesktopAsPng(imagePath);
    assertThat(read(imagePath)).hasSize(Toolkit.getDefaultToolkit().getScreenSize());
  }

  @Test public void shouldTakeScreenshotOfWindowAndSaveItInGivenPath() throws Exception {
    TestFrame frame = showInTest(getClass());
    pause(500);
    String imagePath = concat(temporaryFolderPath(), imageFileName());
    taker.saveComponentAsPng(frame, imagePath);
    assertThat(read(imagePath)).hasSize(frame.getSize());
    frame.destroy();
  }

  @Test(groups = GUI) public void shouldTakeScreenshotOfButtonAndSaveItInGivenPath() throws Exception {
    class CustomFrame extends TestFrame {
      private static final long serialVersionUID = 1L;
      
      final JButton button = new JButton("Hello");
      
      CustomFrame(Class<?> testClass) {
        super(testClass);
        add(button);
      }
    }
    CustomFrame frame = new CustomFrame(getClass());
    frame.display();
    pause(500);
    String imagePath = concat(temporaryFolderPath(), imageFileName());
    taker.saveComponentAsPng(frame.button, imagePath);
    assertThat(read(imagePath)).hasSize(frame.button.getSize());
    frame.destroy();
  }

  private String imageFileName() {
    UUID uuid = UUID.randomUUID();
    return concat(uuid.toString(), ".png");
  }
}
