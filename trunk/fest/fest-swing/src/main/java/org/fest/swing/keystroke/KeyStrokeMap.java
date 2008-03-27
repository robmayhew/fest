/*
 * Created on Mar 26, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.keystroke;

import static java.awt.event.InputEvent.SHIFT_MASK;

import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.KeyStroke;

/**
 * Understands a collection of <code>{@link KeyStrokeEntry}</code>.
 *
 * @author Yvonne Wang
 */
public class KeyStrokeMap {

  private static final Map<Character, KeyStroke> CHAR_TO_KEY_STROKE = new HashMap<Character, KeyStroke>();
  private static final Map<KeyStroke, Character> KEY_STROKE_TO_CHAR = new HashMap<KeyStroke, Character>();

  public static synchronized void add(Collection<KeyStrokeEntry> keyStrokes) {
    for (KeyStrokeEntry entry : keyStrokes) {
      Character character = entry.character();
      KeyStroke keyStroke = entry.keyStroke();
      CHAR_TO_KEY_STROKE.put(character, keyStroke);
      KEY_STROKE_TO_CHAR.put(keyStroke, character);
    }
  }

  /**
   * Returns the key code-based <code>{@link KeyStroke}</code> corresponding to the given character, as best we can
   * guess it, or {@link KeyStrokeMap} if we don't know how to generate it.
   * @param character the given character.
   * @return the key code-based <code>KeyStroke</code> corresponding to the given character, or <code>null</code> if
   * we cannot generate it.
   */
  public static KeyStroke getKeyStroke(char character) {
    return CHAR_TO_KEY_STROKE.get(character);
  }

  /**
   * Given a key code-based <code>{@link KeyStroke}</code>, returns the equivalent character. Key strokes are defined
   * properly for US keyboards only. To contribute your own, please add them using the method
   * <code>{@link #add(Collection)}</code>.
   * @param keyStroke the given <code>KeyStroke</code>.
   * @return KeyEvent.VK_UNDEFINED if the result is unknown.
   */
  public static char getChar(KeyStroke keyStroke) {
    Character character = KEY_STROKE_TO_CHAR.get(keyStroke);
    if (character == null) {
      // Try again, but strip all modifiers but shift
      int mask = keyStroke.getModifiers() & ~SHIFT_MASK;
      character = KEY_STROKE_TO_CHAR.get(KeyStroke.getKeyStroke(keyStroke.getKeyCode(), mask));
      if (character == null) return KeyEvent.CHAR_UNDEFINED;
    }
    return character.charValue();
  }

  private KeyStrokeMap() {}
}
