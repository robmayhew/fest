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

import static org.fest.assertions.Fail.failIfNotEqual;
import static org.fest.util.Strings.*;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Understands assertion methods for images. To create a new instance of this class use the
 * method <code>{@link Assertions#assertThat(BufferedImage)}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class ImageAssert extends GenericAssert<BufferedImage> {

  /**
   * Reads the image in the specified path.
   * @param imageFilePath the path of the image to read.
   * @return the read image.
   * @throws AssertionError wrapping any errors thrown when reading the image.
   */
  public static BufferedImage read(String imageFilePath) {
    File imageFile = new File(imageFilePath);
    if (!imageFile.isFile()) Fail.fail(concat("The path ", quote(imageFilePath), "does not belong to a file"));
    try {
      return ImageIO.read(imageFile);
    } catch (IOException e) {
      Fail.fail(concat("Unable to read image from file ", quote(imageFilePath)), e);
      return null;
    }
  }

  ImageAssert(BufferedImage actual) {
    super(actual);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(picture).<strong>as</strong>(&quot;Vacation Picture&quot;).hasSize(new Dimension(800, 600));
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ImageAssert as(String description) {
    return (ImageAssert) description(description);
  }

  /**
   * Alternative to <code>{@link #as(String)}</code>, since "as" is a keyword in
   * <a href="http://groovy.codehaus.org/" target="_blank">Groovy</a>. This method should be called before any assertion
   * method, otherwise any assertion failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(picture).<strong>describedAs</strong>(&quot;Vacation Picture&quot;).hasSize(new Dimension(800, 600));
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ImageAssert describedAs(String description) {
    return as(description);
  }

  /**
   * Verifies that the actual image satisfies the given condition.
   * @param condition the condition to satisfy.
   * @return this assertion object.
   * @throws AssertionError if the actual image does not satisfy the given condition.
   */
  public ImageAssert satisfies(Condition<BufferedImage> condition) {
    return (ImageAssert)verify(condition);
  }

  /**
   * Verifies that the actual image is equal to the given one. Two images are equal if they have the same size and the
   * pixels at the same coordinates have the same color.
   * @param expected the given image to compare the actual image to.
   * @return this assertion object.
   * @throws AssertionError if the actual image is not equal to the given one.
   */
  public ImageAssert isEqualTo(BufferedImage expected) {
    if (!areEqual(actual, expected)) fail("images are not equal");
    return this;
  }

  /**
   * Verifies that the actual image is not equal to the given one. Two images are equal if they have the same size and
   * the pixels at the same coordinates have the same color.
   * @param image the given image to compare the actual image to.
   * @return this assertion object.
   * @throws AssertionError if the actual image is equal to the given one.
   */
  public ImageAssert isNotEqualTo(BufferedImage image) {
    if (areEqual(actual, image)) fail("images are equal");
    return this;
  }

  private static boolean areEqual(BufferedImage first, BufferedImage second) {
    if (first == null) return second == null;
    int w = first.getWidth();
    int h = first.getHeight();
    if (w != second.getWidth()) return false;
    if (h != second.getHeight()) return false;
    for (int x = 0; x < w; x++)
      for (int y = 0; y < h; y++)
        if (first.getRGB(x, y) != second.getRGB(x, y)) return false;
    return true;
  }

  /**
   * Verifies that the actual image is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual image is <code>null</code>.
   */
  public ImageAssert isNotNull() {
    return (ImageAssert)assertNotNull();
  }

  /**
   * Verifies that the actual image is not the same as the given one.
   * @param expected the given image to compare the actual image to.
   * @return this assertion object.
   * @throws AssertionError if the actual image is the same as the given one.
   */
  public ImageAssert isNotSameAs(BufferedImage expected) {
    return (ImageAssert)assertNotSameAs(expected);
  }

  /**
   * Verifies that the actual image is the same as the given one.
   * @param expected the given image to compare the actual image to.
   * @return this assertion object.
   * @throws AssertionError if the actual image is not the same as the given one.
   */
  public ImageAssert isSameAs(BufferedImage expected) {
    return (ImageAssert)assertSameAs(expected);
  }

  /**
   * Verifies that the size of the actual image is equal to the given one.
   * @param expected the expected size of the actual image.
   * @return this assertion object.
   * @throws AssertionError if the size of the actual image is not equal to the given one.
   */
  public ImageAssert hasSize(Dimension expected) {
    isNotNull();
    Dimension actual = new Dimension(this.actual.getWidth(), this.actual.getHeight());
    failIfNotEqual(description(), actual, expected);
    return this;
  }
}
