/*
 * Created on Aug 30, 2007
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

import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import static java.awt.Event.CTRL_MASK;
import static java.awt.Event.META_MASK;
import static java.awt.event.KeyEvent.VK_CONTROL;
import static java.awt.event.KeyEvent.VK_META;
import static org.fest.util.Strings.concat;

/**
 * Understands platform-specific functionality.
 *
 * @author Alex Ruiz
 */
public final class Platform {

  /**
   * Returns <code>{@link KeyEvent#VK_CONTROL}</code> or <code>{@link KeyEvent#VK_META}</code> (if MacOS).
   * @return <code>VK_CONTROL</code> or <code>VK_META</code> (if MacOS).
   * @throws IllegalStateException if unable to find the appropriate key.
   */
  public static int controlOrCommandKey() {
    int menuShortcutKeyMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
    if (menuShortcutKeyMask == CTRL_MASK) return VK_CONTROL;
    if (menuShortcutKeyMask == META_MASK) return VK_META;
    throw new IllegalStateException(concat("Unable to map even mask '", String.valueOf(menuShortcutKeyMask), "' to a key"));
  }

  private Platform() {}
}
