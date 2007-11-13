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

import org.fest.swing.listener.WeakEventListener;
import org.fest.util.CollectionFilter;
import static org.fest.util.Collections.list;
import static org.fest.util.Objects.areEqual;

import java.awt.Toolkit;
import static java.awt.Toolkit.getDefaultToolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.AWTEventListenerProxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;
import java.util.List;

/**
 * Understands utility methods related to <code>{@link Toolkit}</code>.
 *
 * @author Alex Ruiz
 */
public final class ToolkitUtils {

  public static void removeFromToolkit(Class<? extends AWTEventListener> listenerType) {
    List<? extends AWTEventListener> toRemove = eventListenersInToolkit(listenerType);
    if (toRemove.isEmpty()) return;
    removeFromToolkit(toRemove);
  }

  public static void removeFromToolkit(Collection<? extends AWTEventListener> listeners) {
    for (AWTEventListener listener : listeners)
      getDefaultToolkit().removeAWTEventListener(listener);
  }

  public static boolean isListenerInToolkit(AWTEventListener target, long eventMask) {
    for (AWTEventListener l : getDefaultToolkit().getAWTEventListeners(eventMask)) 
      if (areEqualListeners(target, l)) return true;
    return false;
  }

  private static boolean areEqualListeners(AWTEventListener target, AWTEventListener listenerToCheck) {
    if (target == listenerToCheck) return true;
    return areEqual(target, proxiedListener(listenerToCheck));
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
    return new EventListenerFilter<T>(listenerType).filter(list(eventListeners));
  }

  private static class EventListenerFilter<T extends AWTEventListener> implements CollectionFilter<T> {
    private Class<T> targetType;

    EventListenerFilter(Class<T> targetType) {
      this.targetType = targetType;
    }

    public List<T> filter(Collection<?> objects) {
      List<T> filtered = new ArrayList<T>();
      for (Object o : objects) {
        T listener = findListenerIn(o);
        if (listener != null) filtered.add(listener);
      }
      return filtered; 
    }

    private T findListenerIn(Object o) {
      EventListener proxied = proxiedListener(o);
      if (!(proxied instanceof WeakEventListener)) return null;
      AWTEventListener real = ((WeakEventListener) proxied).realListener();
      if (real != null && targetType.isAssignableFrom(real.getClass())) return targetType.cast(real);
      return null;
    }
  }

  private static EventListener proxiedListener(Object o) {
    if (!(o instanceof AWTEventListenerProxy)) return null;
    return ((AWTEventListenerProxy)o).getListener();
  }

  private ToolkitUtils() {}
}
