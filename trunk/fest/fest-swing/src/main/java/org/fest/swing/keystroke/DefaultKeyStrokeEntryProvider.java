/*
 * Created on Mar 26, 2008
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.swing.KeyStroke;

import static java.awt.event.InputEvent.*;
import static java.awt.event.KeyEvent.*;
import static java.util.Locale.*;

/**
 * Understands a default mapping of characters and <code>{@link KeyStroke}</code>s. If the language of the current
 * <code>{@link Locale locale}</code> is English, this provider will also include <code>{@link KeyStroke}</code>s
 * for English characters.
 * 
 * @author Alex Ruiz
 */
public class DefaultKeyStrokeEntryProvider implements KeyStrokeEntryProvider {

  /**
   * Returns the default mapping of characters and <code>{@link KeyStroke}</code>s. If the country of the current
   * <code>{@link Locale locale}</code> is US or UK, this provider will also include <code>{@link KeyStroke}</code>s
   * for English characters. Otherwise, this provider will only return the mappings for following keys:
   * <ul>
   * <li>Escape</li>
   * <li>Backspace</li>
   * <li>Delete</li>
   * <li>Enter</li>
   * </ul>
   * @return the default mapping of characters and <code>KeyStroke</code>s
   */
  public Collection<KeyStrokeEntry> keyStrokeEntries() {
    List<KeyStrokeEntry> entries = new ArrayList<KeyStrokeEntry>(200);
    entries.addAll(universalKeyStrokes());
    if (!isEnglish()) return entries;
    entries.addAll(basicSymbolKeyStrokes());
    entries.addAll(digitKeyStrokes());
    entries.addAll(lowerCaseKeyStrokes());
    entries.addAll(upperCaseKeyStrokes());
    return entries;
  }

  private static boolean isEnglish() {
    Locale locale = Locale.getDefault();
    return US.equals(locale) || UK.equals(locale);    
  }
  
  public static Collection<KeyStrokeEntry> universalKeyStrokes() {
    List<KeyStrokeEntry> entries = new ArrayList<KeyStrokeEntry>();
    entries.add(new KeyStrokeEntry('', VK_ESCAPE, NO_MASK));
    entries.add(new KeyStrokeEntry('\b', VK_BACK_SPACE, NO_MASK));
    entries.add(new KeyStrokeEntry('', VK_DELETE, NO_MASK));
    entries.add(new KeyStrokeEntry('\n', VK_ENTER, NO_MASK));
    entries.add(new KeyStrokeEntry('\r', VK_ENTER, NO_MASK));
    return entries;
  }

  private static Collection<KeyStrokeEntry> basicSymbolKeyStrokes() {
    List<KeyStrokeEntry> entries = new ArrayList<KeyStrokeEntry>(40);
    entries.add(new KeyStrokeEntry(' ', VK_SPACE, NO_MASK));
    entries.add(new KeyStrokeEntry('\t', VK_TAB, NO_MASK));
    entries.add(new KeyStrokeEntry('~', VK_BACK_QUOTE, SHIFT_MASK));
    entries.add(new KeyStrokeEntry('`', VK_BACK_QUOTE, NO_MASK));
    entries.add(new KeyStrokeEntry('!', VK_1, SHIFT_MASK));
    entries.add(new KeyStrokeEntry('@', VK_2, SHIFT_MASK));
    entries.add(new KeyStrokeEntry('#', VK_3, SHIFT_MASK));
    entries.add(new KeyStrokeEntry('$', VK_4, SHIFT_MASK));
    entries.add(new KeyStrokeEntry('%', VK_5, SHIFT_MASK));
    entries.add(new KeyStrokeEntry('^', VK_6, SHIFT_MASK));
    entries.add(new KeyStrokeEntry('&', VK_7, SHIFT_MASK));
    entries.add(new KeyStrokeEntry('*', VK_8, SHIFT_MASK));
    entries.add(new KeyStrokeEntry('(', VK_9, SHIFT_MASK));
    entries.add(new KeyStrokeEntry(')', VK_0, SHIFT_MASK));
    entries.add(new KeyStrokeEntry('-', VK_MINUS, NO_MASK));
    entries.add(new KeyStrokeEntry('_', VK_MINUS, SHIFT_MASK));
    entries.add(new KeyStrokeEntry('=', VK_EQUALS, NO_MASK));
    entries.add(new KeyStrokeEntry('+', VK_EQUALS, SHIFT_MASK));
    entries.add(new KeyStrokeEntry('[', VK_OPEN_BRACKET, NO_MASK));
    entries.add(new KeyStrokeEntry('{', VK_OPEN_BRACKET, SHIFT_MASK));
    entries.add(new KeyStrokeEntry(']', VK_CLOSE_BRACKET, NO_MASK));
    entries.add(new KeyStrokeEntry('}', VK_CLOSE_BRACKET, SHIFT_MASK));
    entries.add(new KeyStrokeEntry('|', VK_BACK_SLASH, SHIFT_MASK));
    entries.add(new KeyStrokeEntry(';', VK_SEMICOLON, NO_MASK));
    entries.add(new KeyStrokeEntry(':', VK_SEMICOLON, SHIFT_MASK));
    entries.add(new KeyStrokeEntry(',', VK_COMMA, NO_MASK));
    entries.add(new KeyStrokeEntry('<', VK_COMMA, SHIFT_MASK));
    entries.add(new KeyStrokeEntry('.', VK_PERIOD, NO_MASK));
    entries.add(new KeyStrokeEntry('>', VK_PERIOD, SHIFT_MASK));
    entries.add(new KeyStrokeEntry('/', VK_SLASH, NO_MASK));
    entries.add(new KeyStrokeEntry('?', VK_SLASH, SHIFT_MASK));
    entries.add(new KeyStrokeEntry('\\', VK_BACK_SLASH, NO_MASK));
    entries.add(new KeyStrokeEntry('|', VK_BACK_SLASH, SHIFT_MASK));
    entries.add(new KeyStrokeEntry('\'', VK_QUOTE, NO_MASK));
    entries.add(new KeyStrokeEntry('"', VK_QUOTE, SHIFT_MASK));    
    return entries;
  }
  
  private static Collection<KeyStrokeEntry> digitKeyStrokes() {
    List<KeyStrokeEntry> entries = new ArrayList<KeyStrokeEntry>(10);
    for (int i = '0'; i <= '9'; i++)
      entries.add(new KeyStrokeEntry((char)i, VK_0 + i - '0', NO_MASK));
    return entries;
  }
  
  private static Collection<KeyStrokeEntry> lowerCaseKeyStrokes() {
    List<KeyStrokeEntry> entries = new ArrayList<KeyStrokeEntry>(80);
    for (int i = 'a'; i <= 'z'; i++) {
      int keyCode = VK_A + i - 'a';
      entries.add(new KeyStrokeEntry((char)i, keyCode, NO_MASK));
      char controlCharacter = (char)(i - 'a' + 1);
      entries.add(new KeyStrokeEntry(controlCharacter, keyCode, CTRL_MASK));
    }
    return entries;
  }
  
  private static Collection<KeyStrokeEntry> upperCaseKeyStrokes() {
    List<KeyStrokeEntry> entries = new ArrayList<KeyStrokeEntry>(40);
    for (int i = 'A'; i <= 'Z'; i++)
      entries.add(new KeyStrokeEntry((char)i, VK_A + i - 'A', SHIFT_MASK));
    return entries;
  }
}
