/*
 * Created on Mar 27, 2008
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

import static java.awt.event.InputEvent.SHIFT_MASK;
import static java.awt.event.KeyEvent.*;

/**
 * Understands a template for <code>{@link KeyStrokeMappingProvider}</code>s that handle English as the language in
 * the current locale.
 *
 * @author Alex Ruiz
 */
public abstract class EnglishStrokeMappingProviderTemplate implements KeyStrokeMappingProvider {

  /**
   * Returns the universal key stroke mappings:
   * <ul>
   * <li>Escape</li>
   * <li>Backspace</li>
   * <li>Delete</li>
   * <li>Enter</li>
   * </ul>
   * @return the universal key stroke mappings. 
   */
  protected final Collection<KeyStrokeMapping> universalKeyStrokes() {
    List<KeyStrokeMapping> mappings = new ArrayList<KeyStrokeMapping>();
    mappings.add(new KeyStrokeMapping('\b', VK_BACK_SPACE, NO_MASK));
    mappings.add(new KeyStrokeMapping('', VK_DELETE, NO_MASK));
    mappings.add(new KeyStrokeMapping('', VK_ESCAPE, NO_MASK));
    mappings.add(new KeyStrokeMapping('\n', VK_ENTER, NO_MASK));
    mappings.add(new KeyStrokeMapping('\r', VK_ENTER, NO_MASK));
    return mappings;
  }

  /**
   * Returns the English-specific key stroke mappings.
   * @return the English-specific key stroke mappings.
   */
  protected final Collection<KeyStrokeMapping> englishKeyStrokes() {
    List<KeyStrokeMapping> mappings = new ArrayList<KeyStrokeMapping>(100);
    mappings.add(new KeyStrokeMapping('0', VK_0, NO_MASK));
    mappings.add(new KeyStrokeMapping(')', VK_0, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('1', VK_1, NO_MASK));
    mappings.add(new KeyStrokeMapping('!', VK_1, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('2', VK_2, NO_MASK));
    mappings.add(new KeyStrokeMapping('@', VK_2, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('3', VK_3, NO_MASK));
    mappings.add(new KeyStrokeMapping('#', VK_3, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('4', VK_4, NO_MASK));
    mappings.add(new KeyStrokeMapping('$', VK_4, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('5', VK_5, NO_MASK));
    mappings.add(new KeyStrokeMapping('%', VK_5, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('6', VK_6, NO_MASK));
    mappings.add(new KeyStrokeMapping('^', VK_6, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('7', VK_7, NO_MASK));
    mappings.add(new KeyStrokeMapping('&', VK_7, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('8', VK_8, NO_MASK));
    mappings.add(new KeyStrokeMapping('*', VK_8, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('9', VK_9, NO_MASK));
    mappings.add(new KeyStrokeMapping('(', VK_9, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('a', VK_A, NO_MASK));
    mappings.add(new KeyStrokeMapping('A', VK_A, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('b', VK_B, NO_MASK));
    mappings.add(new KeyStrokeMapping('B', VK_B, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('`', VK_BACK_QUOTE, NO_MASK));
    mappings.add(new KeyStrokeMapping('~', VK_BACK_QUOTE, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('\\', VK_BACK_SLASH, NO_MASK));
    mappings.add(new KeyStrokeMapping('|', VK_BACK_SLASH, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('c', VK_C, NO_MASK));
    mappings.add(new KeyStrokeMapping('C', VK_C, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping(']', VK_CLOSE_BRACKET, NO_MASK));
    mappings.add(new KeyStrokeMapping('}', VK_CLOSE_BRACKET, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping(',', VK_COMMA, NO_MASK));
    mappings.add(new KeyStrokeMapping('<', VK_COMMA, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('d', VK_D, NO_MASK));
    mappings.add(new KeyStrokeMapping('D', VK_D, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('e', VK_E, NO_MASK));
    mappings.add(new KeyStrokeMapping('E', VK_E, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('=', VK_EQUALS, NO_MASK));
    mappings.add(new KeyStrokeMapping('+', VK_EQUALS, NO_MASK));
    mappings.add(new KeyStrokeMapping('f', VK_F, NO_MASK));
    mappings.add(new KeyStrokeMapping('F', VK_F, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('g', VK_G, NO_MASK));
    mappings.add(new KeyStrokeMapping('G', VK_G, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('h', VK_H, NO_MASK));
    mappings.add(new KeyStrokeMapping('H', VK_H, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('i', VK_I, NO_MASK));
    mappings.add(new KeyStrokeMapping('I', VK_I, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('j', VK_J, NO_MASK));
    mappings.add(new KeyStrokeMapping('J', VK_J, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('k', VK_K, NO_MASK));
    mappings.add(new KeyStrokeMapping('K', VK_K, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('l', VK_L, NO_MASK));
    mappings.add(new KeyStrokeMapping('L', VK_L, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('m', VK_M, NO_MASK));
    mappings.add(new KeyStrokeMapping('M', VK_M, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('-', VK_MINUS, NO_MASK));
    mappings.add(new KeyStrokeMapping('_', VK_MINUS, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('n', VK_N, NO_MASK));
    mappings.add(new KeyStrokeMapping('N', VK_N, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('o', VK_O, NO_MASK));
    mappings.add(new KeyStrokeMapping('O', VK_O, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('[', VK_OPEN_BRACKET, NO_MASK));
    mappings.add(new KeyStrokeMapping('{', VK_OPEN_BRACKET, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('p', VK_P, NO_MASK));
    mappings.add(new KeyStrokeMapping('P', VK_P, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('.', VK_PERIOD, NO_MASK));
    mappings.add(new KeyStrokeMapping('>', VK_PERIOD, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('q', VK_Q, NO_MASK));
    mappings.add(new KeyStrokeMapping('Q', VK_Q, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('\'', VK_QUOTE, NO_MASK));
    mappings.add(new KeyStrokeMapping('"', VK_QUOTE, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('r', VK_R, NO_MASK));
    mappings.add(new KeyStrokeMapping('R', VK_R, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('s', VK_S, NO_MASK));
    mappings.add(new KeyStrokeMapping('S', VK_S, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping(';', VK_SEMICOLON, NO_MASK));
    mappings.add(new KeyStrokeMapping(':', VK_SEMICOLON, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('/', VK_SLASH, NO_MASK));
    mappings.add(new KeyStrokeMapping('?', VK_SLASH, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping(' ', VK_SPACE, NO_MASK));
    mappings.add(new KeyStrokeMapping('t', VK_T, NO_MASK));
    mappings.add(new KeyStrokeMapping('T', VK_T, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('u', VK_U, NO_MASK));
    mappings.add(new KeyStrokeMapping('U', VK_U, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('v', VK_V, NO_MASK));
    mappings.add(new KeyStrokeMapping('V', VK_V, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('w', VK_W, NO_MASK));
    mappings.add(new KeyStrokeMapping('W', VK_W, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('x', VK_X, NO_MASK));
    mappings.add(new KeyStrokeMapping('X', VK_X, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('y', VK_Y, NO_MASK));
    mappings.add(new KeyStrokeMapping('Y', VK_Y, SHIFT_MASK));
    mappings.add(new KeyStrokeMapping('z', VK_Z, NO_MASK));
    mappings.add(new KeyStrokeMapping('Z', VK_Z, SHIFT_MASK));
    return mappings;
  }
}
