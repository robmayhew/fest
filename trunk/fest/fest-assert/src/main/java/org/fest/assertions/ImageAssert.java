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
 * @author Alex Ruiz
 */
public class ImageAssert extends Assert<BufferedImage> {

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
  
  public ImageAssert(BufferedImage actual) {
    super(actual);
  }
  
  @Override public final ImageAssert isEqualTo(BufferedImage expected) {
    if (!areEqual(actual, expected)) fail("Images are not equal");
    return this;
  }

  @Override public final ImageAssert isNotEqualTo(BufferedImage image) {
    if (areEqual(actual, image)) fail("Images not equal");
    return this;
  }

  private static boolean areEqual(BufferedImage first, BufferedImage second) {
    if (first == null) return second == null;
    int width = first.getWidth();
    int height = first.getHeight();
    if (width != second.getWidth()) return false;
    if (height != second.getHeight()) return false;
    for (int x = 0; x < width; x++)
      for (int y = 0; y < height; y++)
        if (first.getRGB(x, y) != second.getRGB(x, y)) return false;
    return true;
  }
  
  @Override public final ImageAssert isNotNull() {
    return (ImageAssert)super.isNotNull();
  }

  @Override public final ImageAssert isNotSameAs(BufferedImage expected) {
    return (ImageAssert)super.isNotSameAs(expected);
  }

  @Override public final ImageAssert isSameAs(BufferedImage expected) {
    return (ImageAssert)super.isSameAs(expected);
  }

  public final void hasSize(Dimension expected) {
    Dimension actual = new Dimension(this.actual.getWidth(), this.actual.getHeight());
    failIfNotEqual(actual, expected);
  }
}
