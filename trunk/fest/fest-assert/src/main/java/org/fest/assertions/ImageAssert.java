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
public final class ImageAssert extends Assert<BufferedImage> {

  /**
   * Reads the image in the specified path.
   * @param imageFilePath the path of the image to read.
   * @return the read image.
   * @throws AssertionError wrapping any errors thrown when reading the image.
   */
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
  
  ImageAssert(BufferedImage actual) {
    super(actual);
  }
  
  /**
   * Verifies that the actual image is equal to the given one. Two images are equal if they have the same size and the
   * pixels at the same coordinates have the same color.
   * @param expected the given image to compare the actual image to.
   * @return this assertion object.
   * @throws AssertionError if the actual image is not equal to the given one.
   */
  @Override public ImageAssert isEqualTo(BufferedImage expected) {
    if (!areEqual(actual, expected)) fail("Images are not equal");
    return this;
  }

  /**
   * Verifies that the actual image is not equal to the given one. Two images are equal if they have the same size and 
   * the pixels at the same coordinates have the same color.
   * @param image the given image to compare the actual image to.
   * @return this assertion object.
   * @throws AssertionError if the actual image is equal to the given one.
   */
  @Override public ImageAssert isNotEqualTo(BufferedImage image) {
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
  
  /**
   * Verifies that the actual image is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual image is <code>null</code>.
   */
  @Override public ImageAssert isNotNull() {
    return (ImageAssert)super.isNotNull();
  }

  /**
   * Verifies that the actual image is not the same as the given one.
   * @param expected the given image to compare the actual image to.
   * @return this assertion object.
   * @throws AssertionError if the actual image is the same as the given one.
   */
  @Override public ImageAssert isNotSameAs(BufferedImage expected) {
    return (ImageAssert)super.isNotSameAs(expected);
  }

  /**
   * Verifies that the actual image is the same as the given one.
   * @param expected the given image to compare the actual image to.
   * @return this assertion object.
   * @throws AssertionError if the actual image is not the same as the given one.
   */
  @Override public ImageAssert isSameAs(BufferedImage expected) {
    return (ImageAssert)super.isSameAs(expected);
  }

  /**
   * Verifies that the size of the actual image is equal to the given one.
   * @param expected the expected size of the actual image.
   * @return this assertion object.
   * @throws AssertionError if the size of the actual image is not equal to the given one.
   */
  public ImageAssert hasSize(Dimension expected) {
    Dimension actual = new Dimension(this.actual.getWidth(), this.actual.getHeight());
    failIfNotEqual(actual, expected);
    return this;
  }
}
