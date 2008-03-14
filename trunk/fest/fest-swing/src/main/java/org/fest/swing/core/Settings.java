/*
 * Created on Dec 19, 2007
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
package org.fest.swing.core;

import abbot.tester.Robot;

import org.fest.swing.exception.ComponentLookupException;

import static java.lang.Math.*;

import static org.fest.swing.core.ComponentLookupScope.DEFAULT;
import static org.fest.swing.util.Platform.*;

/**
 * Understands project-wide configuration settings.
 *
 * @author Alex Ruiz
 */
public final class Settings {

  private static final int DEFAULT_DELAY = 30000;
  
  private static ComponentLookupScope componentLookupScope;
  private static int timeoutToBeVisible;
  private static int timeoutToFindPopup;
  private static int dragDelay;
  private static int dropDelay;
  private static int eventPostingDelay;
  private static boolean includeHierarchyInComponentLookupException;
  
  static {
    timeoutToBeVisible(DEFAULT_DELAY);
    timeoutToFindPopup(DEFAULT_DELAY);
    dragDelay(0);
    dropDelay(0);
    eventPostingDelay(100);
    componentLookupScope(DEFAULT);
    includeHierarchyInComponentLookupException(true);
  }
  
  /**
   * Returns a value representing the millisecond count in between generated events.
   * @return a value representing the millisecond count in between generated events.
   */
  public static int delayBetweenEvents() { 
    return Robot.getAutoDelay(); 
  }
  
  /**
   * Updates the value representing the millisecond count in between generated events. Usually just set to 100-200 if
   * you want to slow down the playback to simulate actual user input. The default is zero delay.
   * @param ms the millisecond count in between generated events.
   */
  public static void delayBetweenEvents(int ms) { 
    Robot.setAutoDelay(ms); 
  }

  /**
   * Returns the number of milliseconds to wait for a component to be visible.
   * @return the number of milliseconds to wait for a component to be visible.
   */
  public static int timeoutToBeVisible() {
    return timeoutToBeVisible;
  }
  
  /**
   * Updates the number of milliseconds to wait for a component to be visible. The default value is 30000 milliseconds.
   * @param ms the time in milliseconds. It should be between 0 and 60000.
   */
  public static void timeoutToBeVisible(int ms) {
    timeoutToBeVisible = valueToUpdate(ms, 0, 60000);
  }
  
  /**
   * Returns the number of milliseconds to wait before failing to find a pop-up menu that should appear.
   * @return the number of milliseconds to wait before failing to find a pop-up menu that should appear.
   */
  public static int timeoutToFindPopup() {
    return timeoutToFindPopup;
  }

  /**
   * Updates the number of milliseconds to wait before failing to find a pop-up menu that should appear. The default
   * value is 30000 milliseconds.
   * @param ms the time in milliseconds. It should be between 0 and 60000.
   */
  public static void timeoutToFindPopup(int ms) {
    timeoutToFindPopup = valueToUpdate(ms, 0, 60000);
  }

  /**
   * Returns the number of milliseconds to wait between a pressing a mouse button and moving the mouse.
   * @return the number of milliseconds to wait between a pressing a mouse button and moving the mouse.
   */
  public static int dragDelay() {
    return dragDelay;
  }
  
  /**
   * Updates the number of milliseconds to wait between a pressing a mouse button and moving the mouse. The default 
   * value for Mac OS X or the X11 Windowing system is 100 milliseconds. For other platforms, the default value is 0.
   * @param ms the time in milliseconds. For Mac OS X or the X11 Windowing system, the minimum value is 100. For other
   * platforms the minimum value is 0. The maximum value for all platforms is 60000.
   */
  public static void dragDelay(int ms) {
    int min = IS_X11 || IS_OS_X ? 100 : 0;
    dragDelay = valueToUpdate(ms, min, 60000);
  }

  /**
   * Returns the number of milliseconds before checking for idle.
   * @return the number of milliseconds before checking for idle.
   */
  public static int eventPostingDelay() {
    return eventPostingDelay;
  }
  
  /**
   * Updates the number of milliseconds before checking for idle. This allows the system a little time to put a native
   * event onto the AWT event queue. The default value is 100 milliseconds.
   * @param ms the time in milliseconds. It should be between 0 and 1000.
   */
  public static void eventPostingDelay(int ms) {
    eventPostingDelay = valueToUpdate(ms, 0, 1000);
  }
  
  /**
   * Returns the number of milliseconds between the final mouse movement and mouse release to ensure drop ends.
   * @return the number of milliseconds between the final mouse movement and mouse release to ensure drop ends.
   */
  public static int dropDelay() {
    return dropDelay;
  }
  
  /**
   * Updates the number of milliseconds between the final mouse movement and mouse release to ensure drop ends. The 
   * default value for Windows is 200. For other platforms, the default value is 0.
   * @param ms the time in milliseconds. For Windows, the minimum value is 200. For other platforms, the minimum value
   * is 0. The maximum value for all platforms is 60000.
   */
  public static void dropDelay(int ms) {
    int min = IS_WINDOWS ? 200 : 0;
    dropDelay = valueToUpdate(ms, min, 60000);
  }
  
  /**
   * Returns the scope of component lookups. This setting only affects the component fixtures in the package
   * <code>org.fest.swing.fixture</code>.
   * @return the scope of component lookups.
   */
  public static ComponentLookupScope componentLookupScope() {
    return componentLookupScope;
  }
  
  /**
   * Updates the scope of component lookups.  This setting only affects the component fixtures in the package
   * <code>org.fest.swing.fixture</code>. The default value is <code>{@link ComponentLookupScope#DEFAULT}</code>.
   * @param scope the new value for the scope.
   */
  public static void componentLookupScope(ComponentLookupScope scope) {
    componentLookupScope = scope;
  }
  
  /**
   * Returns whether the message in a thrown <code>{@link ComponentLookupException}</code> should include the current 
   * component hierarchy.
   * @return <code>true</code> if the component hierarchy is included as part of the 
   *         <code>ComponentLookupException</code> message, <code>false</code> otherwise.
   */
  public static final boolean shouldIncludeHierarchyInComponentLookupException() {
    return includeHierarchyInComponentLookupException;
  }

  /**
   * Updates whether the message in a thrown <code>{@link ComponentLookupException}</code> should include the current 
   * component hierarchy. The default value is <code>true</code>.
   * @param newValue the new value to set.
   */
  public static final void includeHierarchyInComponentLookupException(boolean newValue) {
    includeHierarchyInComponentLookupException = newValue;
  }

  public static int valueToUpdate(int value, int min, int max) {
    return max(min, min(max, value));
  }
  
  private Settings() {}
}
