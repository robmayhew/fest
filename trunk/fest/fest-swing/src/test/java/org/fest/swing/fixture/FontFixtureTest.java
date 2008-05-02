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
package org.fest.swing.fixture;

import java.awt.Font;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.fixture.FontFixture;
import org.fest.test.CodeToTest;

import static java.awt.Font.*;

import static org.fest.test.ExpectedFailure.expectAssertionError;

/**
 * Tests for <code>{@link FontFixture}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FontFixtureTest {

  private Font font;
  private FontFixture fixture;

  @BeforeMethod public void setUp() {
    font = new Font("SansSerif", PLAIN, 8);
    fixture = new FontFixture(font);
  }

  @Test public void shouldPassIfFamilyIsEqualToGivenOne() {
    fixture.requireFamily("SansSerif");
  }

  @Test public void shouldFailIfFamilyIsNotEqualToGivenOne() {
    expectAssertionError("[family] expected:<'Monospace'> but was:<'SansSerif'>").on(new CodeToTest() {
      public void run() {
        fixture.requireFamily("Monospace");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfFamilyIsNotEqualToGivenOne() {
    fixture = new FontFixture(font, "test");
    expectAssertionError("[test - family] expected:<'Monospace'> but was:<'SansSerif'>").on(new CodeToTest() {
      public void run() {
        fixture.requireFamily("Monospace");
      }
    });
  }

  @Test public void shouldPassIfNameIsEqualToGivenOne() {
    fixture.requireName("SansSerif");
  }

  @Test public void shouldFailIfNameIsNotEqualToGivenOne() {
    expectAssertionError("[name] expected:<'Monospace'> but was:<'SansSerif'>").on(new CodeToTest() {
      public void run() {
        fixture.requireName("Monospace");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfNameIsNotEqualToGivenOne() {
    fixture = new FontFixture(font, "test");
    expectAssertionError("[test - name] expected:<'Monospace'> but was:<'SansSerif'>").on(new CodeToTest() {
      public void run() {
        fixture.requireName("Monospace");
      }
    });
  }

  @Test public void shouldPassIfSizeIsEqualToGivenOne() {
    fixture.requireSize(8);
  }

  @Test public void shouldFailIfSizeIsNotEqualToGivenOne() {
    expectAssertionError("[size] expected:<6> but was:<8>").on(new CodeToTest() {
      public void run() {
        fixture.requireSize(6);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfSizeIsNotEqualToGivenOne() {
    fixture = new FontFixture(font, "test");
    expectAssertionError("[test - size] expected:<6> but was:<8>").on(new CodeToTest() {
      public void run() {
        fixture.requireSize(6);
      }
    });
  }

  @Test public void shouldPassIfFontIsBoldAsAnticipated() {
    font = font.deriveFont(BOLD);
    fixture = new FontFixture(font);
    fixture.requireBold();
  }

  @Test public void shouldFailIfFontIsNotBoldAndExpectingToBeBold() {
    expectAssertionError("[bold] expected:<true> but was:<false>").on(new CodeToTest() {
      public void run() {
        fixture.requireBold();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfFontIsNotBoldAndExpectingToBeBold() {
    fixture = new FontFixture(font, "test");
    expectAssertionError("[test - bold] expected:<true> but was:<false>").on(new CodeToTest() {
      public void run() {
        fixture.requireBold();
      }
    });
  }

  @Test public void shouldPassIfFontIsNotBoldAsAnticipated() {
    fixture.requireNotBold();
  }

  @Test public void shouldFailIfFontIsBoldAndExpectingNotToBeBold() {
    font = font.deriveFont(BOLD);
    fixture = new FontFixture(font);
    expectAssertionError("[bold] expected:<false> but was:<true>").on(new CodeToTest() {
      public void run() {
        fixture.requireNotBold();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfFontIsBoldAndExpectingNotToBeBold() {
    font = font.deriveFont(BOLD);
    fixture = new FontFixture(font, "test");
    expectAssertionError("[test - bold] expected:<false> but was:<true>").on(new CodeToTest() {
      public void run() {
        fixture.requireNotBold();
      }
    });
  }
}
