/*
 * Created on Jun 11, 2008
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
package org.fest.swing.keystroke;

import java.util.Collection;
import java.util.Locale;

import javax.swing.KeyStroke;

import org.testng.annotations.Test;

import static java.awt.event.KeyEvent.*;

import static org.fest.assertions.Fail.fail;

/**
 * Tests for <code>{@link KeyStrokeMappingProvider_en}</code>.
 *
 * @author Alex Ruiz
 */
public class DefaultKeyStrokeMappingProviderTest extends KeyStrokeMappingProviderTestCase {

  @Test(dataProvider = "keyStrokeMappings")
  public void shouldProvideKeyStrokesForEnglishKeyboard(char character, KeyStroke keyStroke) {
    focusTextArea();
    if (character == BACKSPACE) {
      assertKeyWasPressedWithoutModifiers(keyStroke, VK_BACK_SPACE);
      return;
    }
    if (character == CR) {
      assertKeyWasPressedWithoutModifiers(keyStroke, VK_ENTER);
      return;
    }
    if (character == DELETE) {
      assertKeyWasPressedWithoutModifiers(keyStroke, VK_DELETE);
      return;
    }
    if (character == ESCAPE) {
      assertKeyWasPressedWithoutModifiers(keyStroke, VK_ESCAPE);
      return;
    }
    if (character == LF) {
      assertKeyWasPressedWithoutModifiers(keyStroke, VK_ENTER);
      return;
    }
  }
  
  void verifyTestCanRun() {
    if (!Locale.getDefault().getLanguage().equals("en")) fail("To run this test, your language should be English");
  }

  Collection<KeyStrokeMapping> keyStrokeMappingsToTest() {
    return new DefaultKeyStrokeMappingProvider().keyStrokeMappings();
  }
}
