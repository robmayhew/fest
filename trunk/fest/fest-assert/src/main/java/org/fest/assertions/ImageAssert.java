/*
 * Created on Jun 7, 2007
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
package org.fest.assertions;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import static org.fest.assertions.Fail.fail;
import static org.fest.assertions.Fail.failIfNotEqual;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

/**
 * Understands assertion methods for images.
 *
 * @author Yvonne Wang
 */
public final class ImageAssert {

  public static BufferedImage read(String imageFilePath) {
    File imageFile = new File(imageFilePath);
    if (!imageFile.isFile()) fail(concat("The path ", quote(imageFilePath), "does not belong to a file"));
    try {
      return ImageIO.read(imageFile);
    } catch (IOException e) {
      fail(concat("Unable to read image from file ", quote(imageFilePath)), e);
      return null;
    }
  }
  
  private final BufferedImage actual;

  public ImageAssert(BufferedImage actual) {
    this.actual = actual;
  }

  public void hasSize(Dimension expected) {
    if (expected == null) throw new IllegalArgumentException("The expected dimension should not be null");
    failIfNotEqual(actual.getWidth(), expected.width);
    failIfNotEqual(actual.getHeight(), expected.height);
  }
}
