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
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import org.fest.swing.listener.WeakEventListener;

import static java.awt.Toolkit.getDefaultToolkit;

import static org.fest.util.Objects.*;

/**
 * Understands utility methods related to <code>{@link Toolkit}</code>.
 *
 * @author Alex Ruiz
 */
public final class ToolkitUtils {

  public static boolean isListenerInToolkit(AWTEventListener target, long eventMask) {
    for (AWTEventListener l : getDefaultToolkit().getAWTEventListeners(eventMask)) 
      if (areEqualListeners(target, l)) return true;
    return false;
  }

  private static boolean areEqualListeners(AWTEventListener target, AWTEventListener listenerToCheck) {
    return target == listenerToCheck || areEqual(target, proxiedListener(listenerToCheck));
  }

  public static <T extends AWTEventListener> List<T> eventListenersInToolkit(Class<T> listenerType) {
    AWTEventListener[] eventListeners = getDefaultToolkit().getAWTEventListeners();
    return filter(eventListeners, listenerType);
  }

  public static <T extends AWTEventListener> List<T> eventListenersInToolkit(long eventMask, Class<T> listenerType) {
    AWTEventListener[] eventListeners = getDefaultToolkit().getAWTEventListeners(eventMask);
    return filter(eventListeners, listenerType);
  }

  private static <T extends AWTEventListener> List<T> filter(AWTEventListener[] eventListeners, Class<T> listenerType) {
    List<T> filtered = new ArrayList<T>();
    for (AWTEventListener l : eventListeners) {
      T listener = listenerFrom(l, listenerType);
      if (listener != null) filtered.add(listener);
    }
    return filtered;
  }

  private static <T extends AWTEventListener> T listenerFrom(AWTEventListener l, Class<T> targetType) {
    T casted = castIfBelongsToType(l, targetType);
    if (casted != null) return casted;
    return castIfBelongsToType(realListener(l), targetType);
  }

  private static AWTEventListener realListener(AWTEventListener l) {
    EventListener proxied = proxiedListener(l);
    if (!(proxied instanceof WeakEventListener)) return null;
    return ((WeakEventListener)proxied).realListener();
  }

  private static EventListener proxiedListener(AWTEventListener l) {
    if (!(l instanceof AWTEventListenerProxy)) return null;
    return ((AWTEventListenerProxy)l).getListener();
  }

  private ToolkitUtils() {}
}
