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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.image;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.UUID;

import javax.swing.JButton;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.ScreenLock;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.ImageAssert.read;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.swing.testing.TestWindow.createAndShowNewWindow;
import static org.fest.util.Files.temporaryFolderPath;
import static org.fest.util.Strings.concat;

/**
 * Tests for <code>{@link ScreenshotTaker}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test public class ScreenshotTakerTest {

  private static final BufferedImage NO_IMAGE = null;
  private ScreenshotTaker taker;

  @BeforeMethod public void setUp() {
    taker = new ScreenshotTaker();
  }

  @AfterMethod public void tearDown() {
    ScreenLock screenLock = ScreenLock.instance();
    if (screenLock.acquiredBy(this)) screenLock.release(this);
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

  public void shouldTakeDesktopScreenshotAndSaveItInGivenPath() throws Exception {
    String imagePath = concat(temporaryFolderPath(), imageFileName());
    taker.saveDesktopAsPng(imagePath);
    assertThat(read(imagePath)).hasSize(Toolkit.getDefaultToolkit().getScreenSize());
  }

  public void shouldTakeScreenshotOfWindowAndSaveItInGivenPath() throws Exception {
    TestWindow frame = createAndShowNewWindow(getClass());
    String imagePath = concat(temporaryFolderPath(), imageFileName());
    taker.saveComponentAsPng(frame, imagePath);
    assertThat(read(imagePath)).hasSize(sizeOf(frame));
    frame.destroy();
  }

  @Test(groups = GUI)
  public void shouldTakeScreenshotOfButtonAndSaveItInGivenPath() throws Exception {
    ScreenLock.instance().acquire(this);
    MyWindow frame = MyWindow.createNew();
    try {
      frame.display();
      String imagePath = concat(temporaryFolderPath(), imageFileName());
      taker.saveComponentAsPng(frame.button, imagePath);
      assertThat(read(imagePath)).hasSize(sizeOf(frame.button));
    } finally {
      frame.destroy();
    }
  }

  private String imageFileName() {
    UUID uuid = UUID.randomUUID();
    return concat(uuid.toString(), ".png");
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }

    final JButton button = new JButton("Hello");

    private MyWindow() {
      super(ScreenshotTakerTest.class);
      add(button);
    }
  }
}
