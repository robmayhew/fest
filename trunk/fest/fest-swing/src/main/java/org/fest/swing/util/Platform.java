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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.util;

import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.*;

import static org.fest.util.Strings.concat;

/**
 * Understands platform-specific functionality.
 *
 * @author Alex Ruiz
 */
public final class Platform {

  private static boolean isWindows;
  private static boolean isWindows9x;
  private static boolean isWindowsXP;
  private static boolean isMacintosh;
  private static boolean isOSX;
  private static boolean isX11;
  private static boolean isSolaris;
  private static boolean isHPUX;
  private static boolean isLinux;
  
  static {
    initialize(property("os.name"));
  }

  static void initialize(String osName) {
    isWindows = osName.startsWith("Windows");
    isWindows9x = isWindows && containsAny(osName, "95", "98", "ME");
    isWindowsXP = isWindows && osName.contains("XP");
    isMacintosh = property("mrj.version") != null;
    isOSX = isMacintosh && osName.contains("OS X");
    isX11 = !isOSX && !isWindows;
    isSolaris = osName.startsWith("SunOS") || osName.startsWith("Solaris");
    isHPUX = osName.equals("HP-UX");
    isLinux = osName.equals("Linux");
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
    return !isWindows() && !isMacintosh();
  }

  /**
   * Indicates whether it is possible to move windows that are not an instance of <code>{@link java.awt.Frame}</code>
   * or <code>{@link java.awt.Dialog}</code>. Most X11 window managers will allow this, but stock Macintosh and Windows
   * do not.
   * @return <code>true</code> if it is possible to move windows other than <code>Frame</code>s or
   *         <code>Dialog</code>s, <code>false</code> otherwise.
   */
  public static boolean canMoveWindows() {
    return !isWindows() && !isMacintosh();
  }

  /**
   * Indicates whether the operating system is Windows.
   * @return <code>true</code> if the operation system is Windows, <code>false</code> otherwise.
   */
  public static boolean isWindows() { return isWindows; }
  
  /**
   * Indicates whether the operating system is Windows 9x (95, 98 or ME.)
   * @return <code>true</code> if the operating system is Windows 9x (95, 98 or ME,) <code>false</code> otherwise.
   */
  public static boolean isWindows9x() { return isWindows9x; }
  
  /**
   * Indicates whether the operating system is Windows XP.
   * @return <code>true</code> if the operating system is Windows XP, <code>false</code> otherwise.
   */
  public static boolean isWindowsXP() { return isWindowsXP; }
  
  /**
   * Indicates whether the operating system is a Macintosh OS.
   * @return <code>true</code> is the operating system is a Macintosh OS, <code>false</code> otherwise.
   */
  public static boolean isMacintosh() { return isMacintosh; }

  /**
   * Indicates whether the operating system is Mac OS X.
   * @return <code>true</code> if the operating system is Mac OS X, <code>false</code> otherwise.
   */
  public static boolean isOSX() { return isOSX; }
  
  /**
   * Indicates whether the operating system is using the X11 Windowing system.
   * @return <code>true</code> if the operating system is using the X11 Windowing system, <code>false</code> otherwise.
   */
  public static boolean isX11() { return isX11; }

  /**
   * Indicates whether the operating system is Solaris.
   * @return <code>true</code> if the operating system is Solaris, <code>false</code> otherwise.
   */
  public static boolean isSolaris() { return isSolaris; }

  /**
   * Indicates whether the operating system is HP-UX.
   * @return <code>true</code> if the operating system is HP-UX, <code>false</code> otherwise.
   */
  public static boolean isHPUX() { return isHPUX; }
  
  /**
   * Indicates whether the operating system is Linux.
   * @return <code>true</code> if the operating system is Linux, <code>false</code> otherwise.
   */
  public static boolean isLinux() { return isLinux; }

  private Platform() {}
}
