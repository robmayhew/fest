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
package org.fest.swing.util;

import static java.awt.event.KeyEvent.*;
import static org.fest.util.Strings.concat;

import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

/**
 * Understands platform-specific functionality.
 *
 * <p>
 * Adapted from <code>abbot.Platform</code> from <a href="http://abbot.sourceforge.net" target="_blank">Abbot</a>.
 * </p>
 *
 * @author Alex Ruiz
 */
public final class Platform {

  /** Name of the operating system. */
  public static final String OS_NAME;

  /** Indicates whether the operating system is Windows. */
  public static final boolean IS_WINDOWS;

  /** Indicates whether the operating system is Windows 9x (95, 98 or ME.) */
  public static final boolean IS_WINDOWS_9X;

  /** Indicates whether the operating system is Windows XP. */
  public static final boolean IS_WINDOWS_XP;

  /** Indicates whether the operating system is a Macintosh OS. */
  public static final boolean IS_MACINTOSH;

  /** Indicates whether the operating system is Mac OS X. */
  public static final boolean IS_OS_X;

  /** Indicates whether the operating system is using the X11 Windowing system. */
  public static final boolean IS_X11;

  /** Indicates whether the operating system is Solaris. */
  public static final boolean IS_SOLARIS;

  /** Indicates whether the operating system is HP-UX. */
  public static final boolean IS_HP_UX;

  /** Indicates whether the operating system is Linux. */
  public static final boolean IS_LINUX;

  static {
    OS_NAME = property("os.name");
    IS_WINDOWS = OS_NAME.startsWith("Windows");
    IS_WINDOWS_9X = IS_WINDOWS && containsAny(OS_NAME, "95", "98", "ME");
    IS_WINDOWS_XP = IS_WINDOWS && OS_NAME.contains("XP");
    IS_MACINTOSH = property("mrj.version") != null;
    IS_OS_X = IS_MACINTOSH && OS_NAME.contains("OS X");
    IS_X11 = !IS_OS_X && !IS_WINDOWS;
    IS_SOLARIS = OS_NAME.startsWith("SunOS") || OS_NAME.startsWith("Solaris");
    IS_HP_UX = OS_NAME.equals("HP-UX");
    IS_LINUX = OS_NAME.equals("Linux");
  }

  private static boolean containsAny(String s, String...subs) {
    for (String sub : subs) if (s.contains(sub)) return true;
    return false;
  }

  private static String property(String name) {
    return java.lang.System.getProperty(name);
  }

  /**
   * Returns <code>{@link KeyEvent#VK_CONTROL}</code> or <code>{@link KeyEvent#VK_META}</code> (if MacOS).
   * @return <code>VK_CONTROL</code> or <code>VK_META</code> (if MacOS).
   * @throws IllegalStateException if unable to find the appropriate key.
   */
  public static int controlOrCommandKey() {
    int menuShortcutKeyMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
    if (menuShortcutKeyMask == Event.CTRL_MASK) return VK_CONTROL;
    if (menuShortcutKeyMask == Event.META_MASK) return VK_META;
    throw new IllegalStateException(concat("Unable to map event mask '", String.valueOf(menuShortcutKeyMask), "' to a key"));
  }

  /**
   * Indicates whether it is possible to resize windows that are not an instance of <code>{@link java.awt.Frame}</code>
   * or <code>{@link java.awt.Dialog}</code>. Most X11 window managers will allow this, but stock Macintosh and Windows
   * do not.
   * @return <code>true</code> if it is possible to resize windows other than <code>Frame</code>s or
   *         <code>Dialog</code>s, <code>false</code> otherwise.
   */
  public static boolean canResizeWindows() {
    return !IS_WINDOWS && !IS_MACINTOSH;
  }

  /**
   * Indicates whether it is possible to move windows that are not an instance of <code>{@link java.awt.Frame}</code>
   * or <code>{@link java.awt.Dialog}</code>. Most X11 window managers will allow this, but stock Macintosh and Windows
   * do not.
   * @return <code>true</code> if it is possible to move windows other than <code>Frame</code>s or
   *         <code>Dialog</code>s, <code>false</code> otherwise.
   */
  public static boolean canMoveWindows() {
    return !IS_WINDOWS && !IS_MACINTOSH;
  }

  private Platform() {}
}
