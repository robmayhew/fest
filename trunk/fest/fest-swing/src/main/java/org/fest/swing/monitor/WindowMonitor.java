/*
 * Created on Oct 8, 2007
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.monitor;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Window;
import java.util.Collection;

import static org.fest.swing.monitor.ContextMonitor.attachContextMonitor;
import static org.fest.swing.monitor.WindowAvailabilityMonitor.attachWindowAvailabilityMonitor;
import static org.fest.swing.monitor.WindowVisibilityMonitor.attachWindowVisibilityMonitor;

/**
 * Understands a monitor that keeps track of all known root windows (showing, hidden, closed.)
 * <p>
 * Adapted from <code>abbot.tester.WindowTracker</code> from <a href="http://abbot.sourceforge.net"
 * target="_blank">Abbot</a>.
 * </p>
 * 
 * @author Alex Ruiz
 */
public final class WindowMonitor {

  private final Windows windows = new Windows();
  private final Context context = new Context();
  private final WindowStatus windowStatus = new WindowStatus(windows);

  /**
   * Create an instance of WindowTracker which will track all windows coming and going on the current and subsequent app
   * contexts. WARNING: if an applet loads this class, it will only ever see stuff in its own app context.
   */
  private WindowMonitor() {
    attachContextMonitor(windows, context);
    attachWindowAvailabilityMonitor(windows);
    populateExistingWindows();
  }

  private void populateExistingWindows() {
    for (Frame f : Frame.getFrames()) examine(f);
  }

  private void examine(Window w) {
    attachWindowVisibilityMonitor(w, windows);
    for (Window owned : w.getOwnedWindows()) examine(owned);
    windows.markExisting(w);
    context.addContextFor(w);
  }

  /**
   * Returns whether the window is ready to receive OS-level event input. A window's "isShowing" flag may be set true
   * before the WINDOW_OPENED event is generated, and even after the WINDOW_OPENED is sent the window peer is not
   * guaranteed to be ready.
   * @param w the given window.
   * @return whether the window is ready to receive OS-level event input.
   */
  public boolean isWindowReady(Window w) {
    if (windows.isReady(w)) return true;
    windowStatus.checkIfReady(w);
    return false;
  }

  /**
   * Returns the event queue corresponding to the given component. In most cases, this is the same as
   * Component.getToolkit().getSystemEventQueue(), but in the case of applets will bypass the AppContext and provide the
   * real event queue.
   * @param c the given component.
   * @return the event queue corresponding to the given component.
   */
  public EventQueue queueFor(Component c) {
    return context.eventQueueFor(c);
  }

  /**
   * Returns all known event queues.
   * @return all known event queues.
   */
  public Collection<EventQueue> allEventQueues() {
    return context.allEventQueues();
  }

  /**
   * Return all available root windows. A root window is one that has a null parent. Nominally this means a list similar
   * to that returned by <code>{@link Frame#getFrames() Frame.getFrames()}</code>, but in the case of an
   * <code>{@link java.applet.Applet}</code> may return a few dialogs as well.
   * @return all available root windows.
   */
  public Collection<Component> rootWindows() { 
    return context.rootWindows();
  }

  /**
   * Returns the singleton instance of this class.
   * @return the singleton instance of this class.
   */
  public static WindowMonitor instance() {
    return SingletonHolder.INSTANCE;
  }

  private static class SingletonHolder {
    static final WindowMonitor INSTANCE = new WindowMonitor();
  }
}
