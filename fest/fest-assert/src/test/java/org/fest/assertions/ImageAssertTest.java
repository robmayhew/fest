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
import static org.fest.assertions.CommonFailures.expectIllegalArgumentExceptionIfConditionIsNull;
import static org.fest.test.ExpectedFailure.expectAssertionError;
import static org.testng.Assert.*;
import static org.fest.util.Strings.*;

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

  @Test public void shouldSetDescription() {
    ImageAssert assertion = new ImageAssert(fivePixelBlueImage());
    assertNull(assertion.description());
    assertion.as("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescriptionSafelyForGroovy() {
    ImageAssert assertion = new ImageAssert(fivePixelBlueImage());
    assertNull(assertion.description());
    assertion.describedAs("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  private static class NotNullImageCondition extends Condition<BufferedImage> {
    @Override public boolean matches(BufferedImage image) {
      return image != null;
    }
  }

  @Test public void shouldPassIfConditionSatisfied() {
    new ImageAssert(fivePixelBlueImage()).satisfies(new NotNullImageCondition());
  }

  @Test public void shouldThrowErrorIfConditionIsNull() {
    expectIllegalArgumentExceptionIfConditionIsNull().on(new CodeToTest() {
      public void run() {
        new ImageAssert(fivePixelBlueImage()).satisfies(null);
      }
    });
  }

  @Test public void shouldFailIfConditionNotSatisfied() {
    expectAssertionError("condition failed with:<null>").on(new CodeToTest() {
      public void run() {
        new ImageAssert(null).satisfies(new NotNullImageCondition());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfied() {
    expectAssertionError("[A Test] condition failed with:<null>").on(new CodeToTest() {
      public void run() {
        new ImageAssert(null).as("A Test").satisfies(new NotNullImageCondition());
      }
    });
  }

  @Test public void shouldFailIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("expected:<non-null image> but was:<null>").on(new CodeToTest() {
      public void run() {
        new ImageAssert(null).satisfies(new NotNullImageCondition().as("non-null image"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("[A Test] expected:<non-null image> but was:<null>").on(new CodeToTest() {
      public void run() {
        new ImageAssert(null).as("A Test").satisfies(new NotNullImageCondition().as("non-null image"));
      }
    });
  }

  @Test public void shouldPassIfImagesAreEqual() {
    new ImageAssert(fivePixelBlueImage()).isEqualTo(fivePixelBlueImage());
  }

  @Test public void shouldPassIfImagesNullAndCheckingEqual() {
    new ImageAssert(null).isEqualTo(null);
  }

  @Test public void shouldFailIfExpectedIsNullWhenCheckingEqual() {
    final BufferedImage a = fivePixelBlueImage();
    expectAssertionError(concat("expected:<null> but was:<", a, ">")).on(new CodeToTest() {
      public void run() {
        new ImageAssert(a).isEqualTo(null);
      }
    });
  }
  
  @Test public void shouldFailShowingDescriptionIfExpectedIsNullWhenCheckingEqual() {
    final BufferedImage a = fivePixelBlueImage();
    expectAssertionError(concat("[A Test] expected:<null> but was:<", a, ">")).on(new CodeToTest() {
      public void run() {
        new ImageAssert(a).as("A Test").isEqualTo(null);
      }
    });
  }

  @Test public void shouldFailIfImageWidthsAreNotEqualAndExpectingEqual() {
    expectAssertionError("image size, expected:<(3, 5)> but was:<(5, 5)>").on(new CodeToTest() {
      public void run() {
        BufferedImage a = fivePixelBlueImage();
        BufferedImage e = image(3, 5, BLUE);
        new ImageAssert(a).isEqualTo(e);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfImageWidthsAreNotEqualAndExpectingEqual() {
    expectAssertionError("[A Test] image size, expected:<(3, 5)> but was:<(5, 5)>").on(new CodeToTest() {
      public void run() {
        BufferedImage a = fivePixelBlueImage();
        BufferedImage e = image(3, 5, BLUE);
        new ImageAssert(a).as("A Test").isEqualTo(e);
      }
    });
  }

  @Test public void shouldFailIfImageHeightsAreNotEqualAndExpectingEqual() {
    expectAssertionError("image size, expected:<(5, 2)> but was:<(5, 5)>").on(new CodeToTest() {
      public void run() {
        BufferedImage a = fivePixelBlueImage();
        BufferedImage e = image(5, 2, BLUE);
        new ImageAssert(a).isEqualTo(e);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfImageHeightsAreNotEqualAndExpectingEqual() {
    expectAssertionError("[A Test] image size, expected:<(5, 2)> but was:<(5, 5)>").on(new CodeToTest() {
      public void run() {
        BufferedImage a = fivePixelBlueImage();
        BufferedImage e = image(5, 2, BLUE);
        new ImageAssert(a).as("A Test").isEqualTo(e);
      }
    });
  }

  @Test public void shouldFailIfImageColorsAreNotEqualAndExpectingEqual() {
    expectAssertionError("images do not have the same color(s)").on(new CodeToTest() {
      public void run() {
        BufferedImage a = fivePixelBlueImage();
        BufferedImage e = fivePixelYellowImage();
        new ImageAssert(a).isEqualTo(e);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfImageColorsAreNotEqualAndExpectingEqual() {
    expectAssertionError("[A Test] images do not have the same color(s)").on(new CodeToTest() {
      public void run() {
        BufferedImage a = fivePixelBlueImage();
        BufferedImage e = image(5, 5, YELLOW);
        new ImageAssert(a).as("A Test").isEqualTo(e);
      }
    });
  }

  @Test public void shouldPassIfImageWidthsAreNotEqual() {
    BufferedImage a = image(3, 5, BLUE);
    BufferedImage e = fivePixelBlueImage();
    new ImageAssert(a).isNotEqualTo(e);
  }

  @Test public void shouldPassIfActualIsNotNullAndExpectedIsNullWhenCheckingNotEqual() {
    BufferedImage a = fivePixelBlueImage();
    new ImageAssert(a).isNotEqualTo(null);
  }

  @Test public void shouldPassIfImageHeightsAreNotEqual() {
    BufferedImage a = image(5, 3, BLUE);
    BufferedImage e = fivePixelBlueImage();
    new ImageAssert(a).isNotEqualTo(e);
  }

  @Test public void shouldPassIfImageColorsAreNotEqual() {
    BufferedImage a = fivePixelBlueImage();
    BufferedImage e = image(5, 5, YELLOW);
    new ImageAssert(a).isNotEqualTo(e);
  }

  @Test public void shouldFailIfImagesAreNullAndExpectingNotEqual() {
    expectAssertionError("actual value:<null> should not be equal to:<null>").on(new CodeToTest() {
      public void run() {
        new ImageAssert(null).isNotEqualTo(null);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfImagesAreNullAndExpectingNotEqual() {
    expectAssertionError("[A Test] actual value:<null> should not be equal to:<null>").on(new CodeToTest() {
      public void run() {
        new ImageAssert(null).as("A Test").isNotEqualTo(null);
      }
    });
  }

  @Test public void shouldFailIfImagesAreEqualAndExpectingNotEqual() {
    expectAssertionError("images are equal").on(new CodeToTest() {
      public void run() {
        new ImageAssert(fivePixelBlueImage()).isNotEqualTo(fivePixelBlueImage());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfImagesAreEqualAndExpectingNotEqual() {
    expectAssertionError("[A Test] images are equal").on(new CodeToTest() {
      public void run() {
        new ImageAssert(fivePixelBlueImage()).as("A Test").isNotEqualTo(fivePixelBlueImage());
      }
    });
  }

  @Test public void shouldPassIfObjectsAreSame() {
    BufferedImage image = fivePixelBlueImage();
    new ImageAssert(image).isSameAs(image);
  }

  @Test public void shouldFailIfObjectsAreNotSameAndExpectingSame() {
    final BufferedImage a = fivePixelBlueImage();
    final BufferedImage e = fivePixelBlueImage();
    expectAssertionError(concat("expected same instance but found:<", a, "> and:<", e, ">")).on(new CodeToTest() {
      public void run() {
        new ImageAssert(a).isSameAs(e);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfObjectsAreNotSameAndExpectingSame() {
    final BufferedImage a = fivePixelBlueImage();
    final BufferedImage e = fivePixelBlueImage();
    expectAssertionError(concat("[A Test] expected same instance but found:<", a, "> and:<", e, ">")).on(
        new CodeToTest() {
          public void run() {
            new ImageAssert(a).as("A Test").isSameAs(e);
          }
        });
  }

  @Test public void shouldPassIfObjectsAreNotSame() {
    new ImageAssert(fivePixelBlueImage()).isNotSameAs(fivePixelBlueImage());
  }

  @Test public void shouldFailIfObjectsAreSameAndExpectingNotSame() {
    final BufferedImage image = fivePixelBlueImage();
    expectAssertionError(concat("given objects are same:<", image, ">")).on(new CodeToTest() {
      public void run() {
        new ImageAssert(image).isNotSameAs(image);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfObjectsAreSameAndExpectingNotSame() {
    final BufferedImage image = fivePixelBlueImage();
    expectAssertionError(concat("[A Test] given objects are same:<", image, ">")).on(new CodeToTest() {
      public void run() {
        new ImageAssert(image).as("A Test").isNotSameAs(image);
      }
    });
  }


  private BufferedImage fivePixelBlueImage() {
    return image(5, 5, BLUE);
  }

  private BufferedImage fivePixelYellowImage() {
    return image(5, 5, YELLOW);
  }

  private BufferedImage image(int width, int height, Color color) {
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics graphics = image.createGraphics();
    graphics.setColor(color);
    graphics.fillRect(0, 0, width, height);
    return image;
  }
}
