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
import static java.lang.Math.*;

import static org.fest.swing.core.ComponentLookupScope.DEFAULT;

/**
 * Understands project-wide configuration settings.
 *
 * @author Alex Ruiz
 */
public final class Settings {

  private static ComponentLookupScope componentLookupScope;
  private static int componentDelay;
  
  static {
    componentDelay(30000);
    componentLookupScope(DEFAULT);
  }
  
  /**
   * Returns a value representing the millisecond count in between generated events.
   * @return a value representing the millisecond count in between generated events.
   */
  public static int robotAutoDelay() { 
    return Robot.getAutoDelay(); 
  }
  
  /**
   * Updates the value representing the millisecond count in between generated events. Usually just set to 100-200 if
   * you want to slow down the playback to simulate actual user input. The default is zero delay.
   * @param ms the millisecond count in between generated events.
   */
  public static void robotAutoDelay(int ms) { 
    Robot.setAutoDelay(ms); 
  }

  /**
   * Returns the delay before failing to find a component that should be visible.
   * @return the delay before failing to find a component that should be visible.
   */
  public static int componentDelay() {
    return componentDelay;
  }
  
  /**
   * Updates the delay before failing to find a component that should be visible. The default value is 30000 seconds.
   * @param ms the delay in milliseconds. It should be between 0 and 60000.
   */
  public static void componentDelay(int ms) {
    componentDelay = valueToUpdate(ms, 0, 60000);
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
  
  public static int valueToUpdate(int value, int min, int max) {
    return max(min, min(max, value));
  }
  
  private Settings() {}
}
