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
import static org.fest.test.ExpectedFailure.expectAssertionError;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.fest.test.CodeToTest;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ImageAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ImageAssertTest {

  @Test public void shouldPassIfImagesAreEqual() {
    BufferedImage a = image(5, 5, BLUE);
    BufferedImage e = image(5, 5, BLUE);
    new ImageAssert(a).isEqualTo(e);
  }

  @Test public void shouldFailIfImageWidthsAreNotEqualAndExpectingEqual() {
    expectAssertionError("image size, expected:<(3, 5)> but was:<(5, 5)>").on(new CodeToTest() {
      public void run() {
        BufferedImage a = image(5, 5, BLUE);
        BufferedImage e = image(3, 5, BLUE);
        new ImageAssert(a).isEqualTo(e);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfImageWidthsAreNotEqualAndExpectingEqual() {
    expectAssertionError("[A Test] image size, expected:<(3, 5)> but was:<(5, 5)>").on(new CodeToTest() {
      public void run() {
        BufferedImage a = image(5, 5, BLUE);
        BufferedImage e = image(3, 5, BLUE);
        new ImageAssert(a).as("A Test").isEqualTo(e);
      }
    });
  }

  @Test public void shouldFailIfImageHeightsAreNotEqualAndExpectingEqual() {
    expectAssertionError("image size, expected:<(5, 2)> but was:<(5, 5)>").on(new CodeToTest() {
      public void run() {
        BufferedImage a = image(5, 5, BLUE);
        BufferedImage e = image(5, 2, BLUE);
        new ImageAssert(a).isEqualTo(e);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfImageHeightsAreNotEqualAndExpectingEqual() {
    expectAssertionError("[A Test] image size, expected:<(5, 2)> but was:<(5, 5)>").on(new CodeToTest() {
      public void run() {
        BufferedImage a = image(5, 5, BLUE);
        BufferedImage e = image(5, 2, BLUE);
        new ImageAssert(a).as("A Test").isEqualTo(e);
      }
    });
  }

  @Test public void shouldFailIfImageColorsAreNotEqualAndExpectingEqual() {
    expectAssertionError("images do not have the same color(s)").on(new CodeToTest() {
      public void run() {
        BufferedImage a = image(5, 5, BLUE);
        BufferedImage e = image(5, 5, YELLOW);
        new ImageAssert(a).isEqualTo(e);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfImageColorsAreNotEqualAndExpectingEqual() {
    expectAssertionError("[A Test] images do not have the same color(s)").on(new CodeToTest() {
      public void run() {
        BufferedImage a = image(5, 5, BLUE);
        BufferedImage e = image(5, 5, YELLOW);
        new ImageAssert(a).as("A Test").isEqualTo(e);
      }
    });
  }

  @Test public void shouldPassIfImageWidthsAreNotEqual() {
    BufferedImage a = image(3, 5, BLUE);
    BufferedImage e = image(5, 5, BLUE);
    new ImageAssert(a).isNotEqualTo(e);
  }

  @Test public void shouldPassIfImageHeightsAreNotEqual() {
    BufferedImage a = image(5, 3, BLUE);
    BufferedImage e = image(5, 5, BLUE);
    new ImageAssert(a).isNotEqualTo(e);
  }

  @Test public void shouldPassIfImageColorsAreNotEqual() {
    BufferedImage a = image(5, 5, BLUE);
    BufferedImage e = image(5, 5, YELLOW);
    new ImageAssert(a).isNotEqualTo(e);
  }

  @Test public void shouldFailIfImagesAreEqualAndExpectingNotEqual() {
    expectAssertionError("images are equal").on(new CodeToTest() {
      public void run() {
        BufferedImage a = image(5, 5, BLUE);
        BufferedImage e = image(5, 5, BLUE);
        new ImageAssert(a).isNotEqualTo(e);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfImagesAreEqualAndExpectingNotEqual() {
    expectAssertionError("[A Test] images are equal").on(new CodeToTest() {
      public void run() {
        BufferedImage a = image(5, 5, BLUE);
        BufferedImage e = image(5, 5, BLUE);
        new ImageAssert(a).as("A Test").isNotEqualTo(e);
      }
    });
  }

  private BufferedImage image(int width, int height, Color color) {
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics graphics = image.createGraphics();
    graphics.setColor(color);
    graphics.fillRect(0, 0, width, height);
    return image;
  }
}
