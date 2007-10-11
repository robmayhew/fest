/*
 * Created on Oct 10, 2007
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

import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.AWTEventListenerProxy;

import static java.awt.Toolkit.getDefaultToolkit;
import static org.fest.util.Objects.areEqual;

/**
 * Understands utility methods related to <code>{@link Toolkit}</code>.
 *
 * @author Alex Ruiz
 */
public final class ToolkitUtils {

  public static boolean toolkitHasListenerUnderEventMask(AWTEventListener target, long eventMask) {
    for (AWTEventListener l : getDefaultToolkit().getAWTEventListeners(eventMask)) 
      if (isListenerUnderTest(target, l)) return true;
    return false;
  }
  
  private static boolean isListenerUnderTest(AWTEventListener target, AWTEventListener listenerToCheck) {
    if (target == listenerToCheck) return true;
    if (!(listenerToCheck instanceof AWTEventListenerProxy)) return false;
    AWTEventListenerProxy proxy = (AWTEventListenerProxy)listenerToCheck;
    return areEqual(target, proxy.getListener());
  }

  private ToolkitUtils() {}
}
