/*
 * Created on Oct 13, 2007
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
import static org.fest.swing.monitor.WindowVisibilityMonitor.attachWindowVisibilityMonitor;

import java.applet.Applet;
import java.awt.*;
import static java.awt.AWTEvent.COMPONENT_EVENT_MASK;
import static java.awt.AWTEvent.WINDOW_EVENT_MASK;
import java.awt.event.AWTEventListener;
import java.awt.event.ComponentEvent;
import static java.awt.event.ComponentEvent.COMPONENT_SHOWN;
import static java.awt.event.WindowEvent.*;

/**
 * Understands a monitor for components and event queues.
 * <p>
 * Adapted from <code>abbot.tester.WindowTracker</code> from <a href="http://abbot.sourceforge.net"
 * target="_blank">Abbot</a>.
 * </p>
 * 
 * @author Alex Ruiz
 */
final class ContextMonitor implements AWTEventListener {

  private static final long EVENT_MASK = WINDOW_EVENT_MASK | COMPONENT_EVENT_MASK;

  private final Context context;
  private final Windows windows;

  static ContextMonitor attachContextMonitor(Windows windows, Context context) {
    ContextMonitor monitor = new ContextMonitor(windows, context);
    attachAsWeakEventListener(monitor, EVENT_MASK);
    return monitor;
  }
  
  ContextMonitor(Windows windows, Context context) {
    this.windows = windows;
    this.context = context;
  }

  /** ${@inheritDoc} */
  public void eventDispatched(AWTEvent e) {
    ComponentEvent event = (ComponentEvent) e;
    Component component = event.getComponent();
    // This is our sole means of accessing other app contexts (if running within an applet). We look for window events
    // beyond OPENED in order to catch windows that have already opened by the time we start listening but which are not
    // in the Frame.getFrames list (i.e. they are on a different context). Specifically watch for COMPONENT_SHOWN on 
    // applets, since we may not get frame events for them.
    if (!(component instanceof Applet) && !(component instanceof Window)) return;
    processEvent(event);
    // The context for root-level windows may change between WINDOW_OPENED and subsequent events.
    if (!component.getToolkit().getSystemEventQueue().equals(context.lookupEventQueueFor(component)))
      context.addContextFor(component);
  }

  private void processEvent(ComponentEvent event) {
    Component component = event.getComponent();
    int id = event.getID();
    if (id == WINDOW_OPENED) {
      recognizeAsOpenWindow(component);
      return;
    }
    if (id == WINDOW_CLOSED) {
      recognizeAsClosedWindow(component);
      return;
    }
    if (id == WINDOW_CLOSING) return;
    if ((id >= WINDOW_FIRST && id <= WINDOW_LAST) || id == COMPONENT_SHOWN)
      if ((!context.rootWindows().contains(component)) || windows.isClosed(component)) recognizeAsOpenWindow(component);    
  }
  
  private void recognizeAsOpenWindow(Component component) {
    context.addContextFor(component);
    // Attempt to ensure the window is ready for input before recognizing it as "open". 
    // There is no Java API for this, so we institute an empirically tested delay.
    if (!(component instanceof Window)) return; 
    attachWindowVisibilityMonitor((Window)component, windows);
    windows.markAsShowing((Window) component);
    // Native components don't receive events anyway...
    if (component instanceof FileDialog) windows.markAsReady((Window) component);
  }

  private void recognizeAsClosedWindow(Component component) {
    if (component.getParent() == null) context.removeContextFor(component);
    if (component instanceof Window) windows.markAsClosed((Window) component);
  }
}
