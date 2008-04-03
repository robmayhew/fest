/*
 * Created on Mar 29, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.input;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.WindowEvent;
import java.util.EmptyStackException;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Logger;

import org.fest.swing.listener.WeakEventListener;

import static java.awt.event.WindowEvent.*;
import static java.util.logging.Level.WARNING;
import static javax.swing.SwingUtilities.invokeAndWait;

import static org.fest.swing.listener.WeakEventListener.attachAsWeakEventListener;

/**
 * Understands an <code>{@link AWTEventListener}</code> which normalizes the event stream:
 * <ul>
 * <li>sends a single <code>WINDOW_CLOSED</code>, instead of one every time dispose is called
 * <li>catches <code>sun.awt.dnd.SunDropTargetEvents</code> during native drags
 * </ul>
 */
class EventNormalizer implements AWTEventListener {

  private static Logger logger = Logger.getLogger(EventNormalizer.class.getName());

  private final Map<Window, Boolean> disposedWindows = new WeakHashMap<Window, Boolean>();

  private final boolean trackDrag;

  private WeakEventListener weakEventListener;
  private AWTEventListener listener;
  private DragAwareEventQueue dragAwareEventQueue;

  EventNormalizer() {
    this(false);
  }

  EventNormalizer(boolean trackDrag) {
    this.trackDrag = trackDrag;
  }

  void startListening(final Toolkit toolkit, AWTEventListener listener, long mask) {
    this.listener = listener;
    weakEventListener = attachAsWeakEventListener(toolkit, this, mask);
    if (!trackDrag) return;
    dragAwareEventQueue = new DragAwareEventQueue(toolkit, mask, this);
    try {
      invokeAndWait(new Runnable() {
        public void run() {
          toolkit.getSystemEventQueue().push(dragAwareEventQueue);
        }
      });
    } catch (Exception e) {
      logger.log(WARNING, "Ignoring error at EventNormalizer startup", e);
    }
  }

  void stopListening() {
    disposeDragAwareEventQueue();
    disposeWeakEventListener();
    listener = null;
  }

  private void disposeDragAwareEventQueue() {
    if (dragAwareEventQueue == null) return;
    try {
      dragAwareEventQueue.pop();
    } catch (EmptyStackException e) {}
    dragAwareEventQueue = null;
  }

  private void disposeWeakEventListener() {
    if (weakEventListener == null) return;
    weakEventListener.dispose();
    weakEventListener = null;
  }

  /** Event reception callback. */
  public void eventDispatched(AWTEvent event) {
    boolean discard = isDuplicateDispose(event);
    if (!discard && listener != null) delegate(event);
  }

  // We want to ignore consecutive event indicating window disposal; there
  // needs to be an intervening SHOWN/OPEN before we're interested again.
  private boolean isDuplicateDispose(AWTEvent event) {
    if (!(event instanceof WindowEvent)) return false;
    WindowEvent windowEvent = (WindowEvent) event;
    final int eventId = windowEvent.getID();
    if (eventId == WINDOW_CLOSING) return false;
    if (eventId == WINDOW_CLOSED) {
      Window w = windowEvent.getWindow();
      if (disposedWindows.containsKey(w)) return true;
      disposedWindows.put(w, Boolean.TRUE);
      w.addComponentListener(new DisposalMonitor(disposedWindows));
      return false;
    }
    disposedWindows.remove(windowEvent.getWindow());
    return false;
  }

  protected void delegate(AWTEvent e) {
    listener.eventDispatched(e);
  }
}