/*
 * Created on Apr 16, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.assertion;

import static java.awt.Font.*;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.test.ExpectedFailure.expectAssertionError;
import static org.fest.util.Strings.concat;

import java.awt.Font;

import org.fest.test.CodeToTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link FontAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FontAssertTest {

  private Font font;
  private FontAssert fontAssert;

  @BeforeMethod public void setUp() {
    font = new Font("SansSerif", PLAIN, 8);
    fontAssert = new FontAssert(font);
  }

  @Test public void shouldPassIfFontIsEqualToGivenOne() {
    fontAssert.isEqualTo(new Font("SansSerif", PLAIN, 8));
  }

  @Test public void shouldFailIfFontIsNotEqualToGivenOneAndExpectingToBeEqual() {
    Font expected = new Font("SansSerif", BOLD, 6);
    try {
      fontAssert.isEqualTo(expected);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains(concat("expected:<", expected, ">"))
                             .contains(concat("was:<", font, ">"));
    }
  }

  @Test public void shouldFailShowingDescriptionIfFontIsNotEqualToGivenOneAndExpectingToBeEqual() {
    Font expected = new Font("SansSerif", BOLD, 6);
    try {
      fontAssert.as("test").isEqualTo(expected);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("[test]")
                             .contains(concat("expected:<", expected, ">"))
                             .contains(concat("was:<", font, ">"));
    }
  }

  @Test public void shouldPassIfFontIsNotEqualToGivenOne() {
    fontAssert.isNotEqualTo(new Font("SansSerif", BOLD, 6));
  }

  @Test public void shouldFailIfFontIsEqualToGivenOneAndExpectingToBeNotEqual() {
    Font equal = new Font("SansSerif", PLAIN, 8);
    try {
      fontAssert.isNotEqualTo(equal);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains(concat("actual value:<", font, ">"))
                             .contains(concat("should not be equal to:<", equal, ">"));
    }
  }

  @Test public void shouldFailShowingDescriptionIfFontIsEqualToGivenOneAndExpectingToBeNotEqual() {
    Font equal = new Font("SansSerif", PLAIN, 8);
    try {
      fontAssert.as("test").isNotEqualTo(equal);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("[test]")
                             .contains(concat("actual value:<", font, ">"))
                             .contains(concat("should not be equal to:<", equal, ">"));
    }
  }

  @Test public void shouldPassIfFamilyIsEqualToGivenOne() {
    fontAssert.hasFamily("SansSerif");
  }

  @Test public void shouldFailIfFamilyIsNotEqualToGivenOne() {
    expectAssertionError("[family] expected:<'Monospace'> but was:<'SansSerif'>").on(new CodeToTest() {
      public void run() {
        fontAssert.hasFamily("Monospace");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfFamilyIsNotEqualToGivenOne() {
    expectAssertionError("[test - family] expected:<'Monospace'> but was:<'SansSerif'>").on(new CodeToTest() {
      public void run() {
        fontAssert.as("test").hasFamily("Monospace");
      }
    });
  }

  @Test public void shouldPassIfNameIsEqualToGivenOne() {
    fontAssert.hasName("SansSerif");
  }

  @Test public void shouldFailIfNameIsNotEqualToGivenOne() {
    expectAssertionError("[name] expected:<'Monospace'> but was:<'SansSerif'>").on(new CodeToTest() {
      public void run() {
        fontAssert.hasName("Monospace");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfNameIsNotEqualToGivenOne() {
    expectAssertionError("[test - name] expected:<'Monospace'> but was:<'SansSerif'>").on(new CodeToTest() {
      public void run() {
        fontAssert.as("test").hasName("Monospace");
      }
    });
  }

  @Test public void shouldPassIfSizeIsEqualToGivenOne() {
    fontAssert.hasSize(8);
  }

  @Test public void shouldFailIfSizeIsNotEqualToGivenOne() {
    expectAssertionError("[size] expected:<6> but was:<8>").on(new CodeToTest() {
      public void run() {
        fontAssert.hasSize(6);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfSizeIsNotEqualToGivenOne() {
    expectAssertionError("[test - size] expected:<6> but was:<8>").on(new CodeToTest() {
      public void run() {
        fontAssert.as("test").hasSize(6);
      }
    });
  }

  @Test public void shouldPassIfFontIsBoldAsAnticipated() {
    font = font.deriveFont(BOLD);
    fontAssert = new FontAssert(font);
    fontAssert.isBold();
  }

  @Test public void shouldFailIfSizeIsNotBoldAndExpectingToBeBold() {
    expectAssertionError("[bold] expected:<true> but was:<false>").on(new CodeToTest() {
      public void run() {
        fontAssert.isBold();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfSizeIsNotBoldAndExpectingToBeBold() {
    expectAssertionError("[test - bold] expected:<true> but was:<false>").on(new CodeToTest() {
      public void run() {
        fontAssert.as("test").isBold();
      }
    });
  }
}
