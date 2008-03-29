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
package org.fest.swing.core.input;

import static java.awt.AWTEvent.*;
import static java.awt.event.MouseEvent.*;
import static java.awt.event.WindowEvent.*;
import static javax.swing.SwingUtilities.*;
import static org.fest.swing.listener.WeakEventListener.attachAsWeakEventListener;

import java.awt.*;
import java.awt.event.*;
import java.util.EmptyStackException;
import java.util.Map;
import java.util.WeakHashMap;

import org.fest.swing.listener.WeakEventListener;

/**
 * Understands an <code>{@link AWTEventListener}</code> which normalizes the event stream:
 * <ul>
 * <li>sends a single <code>WINDOW_CLOSED</code>, instead of one every time dispose is called
 * <li>catches <code>sun.awt.dnd.SunDropTargetEvents</code> during native drags
 * </ul>
 */
class EventNormalizer implements AWTEventListener {

  private final Map<Window, Boolean> disposedWindows = new WeakHashMap<Window, Boolean>();

  private final boolean trackDrag;

  private WeakEventListener weakEventListener;
  private AWTEventListener listener;
  private DragAwareEventQueue dragAwareEventQueue;
  private long mask;

  EventNormalizer() {
    this(false);
  }

  EventNormalizer(boolean trackDrag) {
    this.trackDrag = trackDrag;
  }

  void startListening(final Toolkit toolkit, AWTEventListener listener, long mask) {
    this.listener = listener;
    this.mask = mask;
    weakEventListener = attachAsWeakEventListener(toolkit, this, mask);
    if (!trackDrag) return;
    dragAwareEventQueue = new DragAwareEventQueue();
    try {
      invokeAndWait(new Runnable() {
        public void run() {
          toolkit.getSystemEventQueue().push(dragAwareEventQueue);
        }
      });
    } catch (Exception e) {}
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

  // TODO: (Abbot) Maybe make this an AWT event listener instead, so we can use one instance instead of one per window.
  private static class DisposalWatcher extends ComponentAdapter {
    private final Map<Window, Boolean> disposedWindows;

    DisposalWatcher(Map<Window, Boolean> disposedWindows) {
      this.disposedWindows = disposedWindows;
    }

    @Override public void componentShown(ComponentEvent e) {
      Component c = e.getComponent();
      c.removeComponentListener(this);
      disposedWindows.remove(c);
    }
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
      w.addComponentListener(new DisposalWatcher(disposedWindows));
      return false;
    }
    disposedWindows.remove(windowEvent.getWindow());
    return false;
  }

  protected void delegate(AWTEvent e) {
    listener.eventDispatched(e);
  }

  /**
   * Catches native drop target events, which are normally hidden from AWTEventListeners.
   */
  private class DragAwareEventQueue extends EventQueue {
    
    @Override public void pop() throws EmptyStackException {
      if (Toolkit.getDefaultToolkit().getSystemEventQueue() == this) super.pop();
    }

    /**
     * Dispatch native drag/drop events the same way non-native drags are reported. Enter/Exit are reported with the
     * appropriate source, while drag and release events use the drag source as the source.
     * <p>
     * TODO: implement enter/exit events TODO: change source to drag source, not mouse under
     */
    @Override protected void dispatchEvent(AWTEvent e) {
      if (e.getClass().getName().indexOf("SunDropTargetEvent") != -1) {
        MouseEvent mouseEvent = (MouseEvent) e;
        Component target = getDeepestComponentAt(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
        if (target != mouseEvent.getSource()) 
          mouseEvent = convertMouseEvent(mouseEvent.getComponent(), mouseEvent, target);
        relayDnDEvent(mouseEvent);
      }
      super.dispatchEvent(e);
    }

    private void relayDnDEvent(MouseEvent event) {
      int eventId = event.getID();
      if (eventId == MOUSE_MOVED || eventId == MOUSE_DRAGGED) {
        if ((mask & MOUSE_MOTION_EVENT_MASK) != 0) eventDispatched(event);
        return;
      }  
      if (eventId >= MOUSE_FIRST && eventId <= MOUSE_LAST) {
        if ((mask & MOUSE_EVENT_MASK) != 0) eventDispatched(event);
      }
    }
  }
}