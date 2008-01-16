/*
 * Created on Jun 9, 2007
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
package org.fest.assertions;

import static java.awt.Color.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ImageAssert}</code>.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ImageAssertTest {

  @Test public void isEqualToShouldPassIfImagesAreEqual() {
    BufferedImage first = image(5, 5, BLUE);
    BufferedImage second = image(5, 5, BLUE);
    new ImageAssert(first).isEqualTo(second);
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public void isEqualToShouldFailIfImagesHaveDifferentWidth() {
    BufferedImage first = image(5, 5, BLUE);
    BufferedImage second = image(3, 5, BLUE);
    new ImageAssert(first).isEqualTo(second);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void isEqualToShouldFailIfImagesHaveDifferentHeight() {
    BufferedImage first = image(5, 5, BLUE);
    BufferedImage second = image(5, 2, BLUE);
    new ImageAssert(first).isEqualTo(second);
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public void isEqualToShouldFailIfImagesHaveDifferentColor() {
    BufferedImage first = image(5, 5, BLUE);
    BufferedImage second = image(5, 5, YELLOW);
    new ImageAssert(first).isEqualTo(second);
  }

  private BufferedImage image(int width, int height, Color color) {
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics graphics = image.createGraphics();
    graphics.setColor(color);
    graphics.fillRect(0, 0, width, height);
    return image;
  }
}
