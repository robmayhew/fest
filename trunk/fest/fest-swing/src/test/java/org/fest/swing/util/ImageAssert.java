/*
 * Created on May 19, 2007
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

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Understands assertion methods related to images.
 *
 * @author Yvonne Wang 
 */
public final class ImageAssert {

  /**
   * Verifies that a screenshot of the desktop was stored in the given path.
   * @param path the path of the screenshot to verify.
   * @throws IOException any I/O error thrown when reading the image file.
   */
  public static void assertScreenshotOfDesktopTaken(String path) throws IOException {
    File imageFile = new File(path);
    assertThat(imageFile.isFile()).isTrue();
    // assertThat(imageFile.getTotalSpace() > 0).isTrue();
    BufferedImage image = ImageIO.read(imageFile);
    Dimension expectedImageSize = Toolkit.getDefaultToolkit().getScreenSize();
    assertThat(image.getWidth()).isEqualTo(expectedImageSize.width);
    assertThat(image.getHeight()).isEqualTo(expectedImageSize.height);
  }
  
  private ImageAssert() {}
}
