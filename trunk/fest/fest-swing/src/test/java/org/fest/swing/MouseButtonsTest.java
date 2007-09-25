/*
 * Created on Sep 21, 2007
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
package org.fest.swing;

import static java.awt.event.InputEvent.BUTTON1_MASK;
import static java.awt.event.InputEvent.BUTTON2_MASK;
import static java.awt.event.InputEvent.BUTTON3_MASK;
import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link MouseButtons}</code>.
 *
 * @author Alex Ruiz
 */
public class MouseButtonsTest {

  @Test(dataProvider = "masks") 
  public void shouldContainCorrectMouseButtonMask(MouseButtons target, int expectedMask) {
    assertThat(target.mask).isEqualTo(expectedMask);
  }
  
  @Test(dataProvider = "masks") 
  public void shouldLookupButtonGivenMask(MouseButtons button, int mask) {
    assertThat(MouseButtons.lookup(mask)).isEqualTo(button);
  }

  @DataProvider(name = "masks") public Object[][] masks() {
    return new Object[][] {
        { MouseButtons.BUTTON1, BUTTON1_MASK },
        { MouseButtons.BUTTON2, BUTTON2_MASK },
        { MouseButtons.BUTTON3, BUTTON3_MASK },
    };
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorInLookupIfMaskIsInvalid() {
    MouseButtons.lookup(Integer.MIN_VALUE);
  }
}
