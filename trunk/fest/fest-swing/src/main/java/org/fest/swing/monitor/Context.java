/*
 * Created on Oct 14, 2007
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
package org.fest.swing.monitor;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import static org.fest.util.Collections.list;

/**
 * Understands a monitor that maps event queues to GUI components and vice versa.
 * <p>
 * Adapted from <code>abbot.tester.WindowTracker</code> from <a href="http://abbot.sourceforge.net"
 * target="_blank">Abbot</a>.
 * </p>
 * 
 * @author Alex Ruiz
 */
class Context {

  /** Maps unique event queues to the set of root windows found on each queue. */
  private final Map<EventQueue, Map<Window, Boolean>> contexts = new WeakHashMap<EventQueue, Map<Window, Boolean>>();
  
  /** Maps components to their corresponding event queues. */
  private final Map<Component, WeakReference<EventQueue>> queues = new WeakHashMap<Component, WeakReference<EventQueue>>();

  private final Object lock = new Object();
  
  public Context() {
    contexts.put(Toolkit.getDefaultToolkit().getSystemEventQueue(), new WeakHashMap<Window, Boolean>());
  }
  
  /**
   * Return all available root windows. A root window is one that has a null parent. Nominally this means a list similar
   * to that returned by <code>{@link Frame#getFrames() Frame.getFrames()}</code>, but in the case of an
   * <code>{@link java.applet.Applet}</code> may return a few dialogs as well.
   * @return all available root windows.
   */
  Collection<Window> rootWindows() {
    Set<Window> rootWindows = new HashSet<Window>();
    synchronized (lock) {
      for (EventQueue queue : contexts.keySet())
        rootWindows.addAll(contexts.get(queue).keySet());
    }
    rootWindows.addAll(list(Frame.getFrames()));
    return rootWindows;
  }
  
  EventQueue lookupEventQueueFor(Component c) {
    synchronized (lock) {
      WeakReference<EventQueue> reference = queues.get(c);
      if (reference != null) return reference.get();
      return null;
    }
  }

  void removeContextFor(Component component) {
    EventQueue queue = component.getToolkit().getSystemEventQueue();
    synchronized (lock) {
      Map<Window, Boolean> context = contexts.get(queue);
      if (context != null) {
        context.remove(component);
      }
      for (EventQueue q : contexts.keySet()) 
        contexts.get(q).remove(component);
    }
  }

  void addContextFor(Component component) {
    EventQueue queue = component.getToolkit().getSystemEventQueue();
    synchronized (lock) {
      Map<Window, Boolean> context = contexts.get(queue);
      if (context == null) context = createContext(queue);
      if (component instanceof Window && component.getParent() == null) context.put((Window)component, true);
      queues.put(component, new WeakReference<EventQueue>(queue));
    }
  }

  private Map<Window, Boolean> createContext(EventQueue queue) {
    Map<Window, Boolean> context = new WeakHashMap<Window, Boolean>();
    contexts.put(queue, context);
    return context;
  }  
  
  /**
   * Return the event queue corresponding to the given component. In most cases, this is the same as
   * <code>{@link java.awt.Toolkit#getSystemEventQueue()}</code>, but in the case of applets will bypass the AppContext 
   * and provide the real event queue.
   * @param c the given component.
   * @return the event queue corresponding to the given component
   */
  EventQueue eventQueueFor(Component c) {
    Component component = c;
    // Components above the applet in the hierarchy may or may not share the same context with the applet itself.
    while (!(component instanceof java.applet.Applet) && component.getParent() != null)
      component = component.getParent();
    synchronized (lock) {
      WeakReference<EventQueue> reference = queues.get(component);
      EventQueue queue = reference != null ? reference.get() : null;
      if (queue == null) queue = component.getToolkit().getSystemEventQueue();
      return queue;
    }
  }

  /**
   * Returns all known event queues.
   * @return all known event queues.
   */
  Collection<EventQueue> allEventQueues() {
    HashSet<EventQueue> eventQueues = new HashSet<EventQueue>();
    synchronized (lock) {
      eventQueues.addAll(contexts.keySet());
      for (WeakReference<EventQueue> reference : queues.values()) {
        EventQueue queue = reference.get();
        if (queue != null) eventQueues.add(queue);
      }
    }
    return eventQueues;
  }
}
