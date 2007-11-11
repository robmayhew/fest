/*
 * Created on Oct 9, 2007
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

import static org.fest.swing.listener.WeakEventListener.attachAsWeakEventListener;

import static javax.swing.SwingUtilities.getWindowAncestor;
import java.awt.*;
import static java.awt.AWTEvent.*;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;

/**
 * Understands an event listener that monitors when a window is ready to receive OS-level event input.
 * <p>
 * Adapted from <code>abbot.tester.WindowTracker</code> from <a href="http://abbot.sourceforge.net"
 * target="_blank">Abbot</a>.
 * </p>
 * 
 * @author Alex Ruiz
 */
final class WindowAvailabilityMonitor implements AWTEventListener {

  private static final long EVENT_MASK = MOUSE_MOTION_EVENT_MASK | MOUSE_EVENT_MASK | PAINT_EVENT_MASK;
  
  private final Windows windows;

  static WindowAvailabilityMonitor attachWindowAvailabilityMonitor(Windows windows) {
    WindowAvailabilityMonitor monitor = new WindowAvailabilityMonitor(windows);
    attachAsWeakEventListener(monitor, EVENT_MASK);
    return monitor;
  }
  
  WindowAvailabilityMonitor(Windows windows) {
    this.windows = windows;
  }
  
  public void eventDispatched(AWTEvent e) {
    if (!(e instanceof MouseEvent)) return; 
    Component c = (Component) e.getSource();
    Window w = c instanceof Window ? (Window)c : getWindowAncestor(c);
    windows.markAsReady(w);
  }

}
