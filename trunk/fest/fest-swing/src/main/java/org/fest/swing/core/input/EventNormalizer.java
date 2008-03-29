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

import static org.fest.swing.util.Modifiers.*;

import java.awt.*;
import java.awt.event.*;
import java.util.EmptyStackException;
import java.util.Map;
import java.util.WeakHashMap;

import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import org.fest.swing.listener.WeakEventListener;

/**
 * Provide an AWTEventListener which normalizes the event stream.
 * <ul>
 * <li>removes modifier key repeats on w32
 * <li>sends a single WINDOW_CLOSED, instead of one every time dispose is called
 * <li>removes some spurious key events on OSX
 * <li>catches sun.awt.dnd.SunDropTargetEvents during native drags
 * </ul>
 */
class EventNormalizer implements AWTEventListener {

  // Normally we want to ignore these (w32 generates them)
  private static boolean captureModifierRepeats = Boolean.getBoolean("abbot.capture_modifier_repeats");

  private AWTEventListener listener;
  private WeakEventListener weakListener;
  private long modifiers;
  private final Map<Window, Boolean> disposedWindows = new WeakHashMap<Window, Boolean>();
  private DragAwareEventQueue queue;
  private long mask;
  private final boolean trackDrag;

  EventNormalizer() {
    this(false);
  }

  EventNormalizer(boolean trackDrag) {
    this.trackDrag = trackDrag;
  }

  void startListening(Toolkit toolkit, AWTEventListener listener, long mask) {
    fnKeyDown = false;
    lastKeyPress = lastKeyRelease = KeyEvent.VK_UNDEFINED;
    lastKeyStroke = null;
    lastKeyChar = KeyEvent.VK_UNDEFINED;
    lastKeyComponent = null;
    modifiers = 0;
    this.listener = listener;
    this.mask = mask;
    weakListener = WeakEventListener.attachAsWeakEventListener(toolkit, this, mask);
    if (trackDrag) {
      queue = new DragAwareEventQueue();
      try {
        SwingUtilities.invokeAndWait(new Runnable() {
          public void run() {
            Toolkit.getDefaultToolkit().getSystemEventQueue().push(queue);
          }
        });
      } catch (Exception e) {}
    }
  }

  void stopListening() {
    if (queue != null) {
      try {
        queue.pop();
      } catch (EmptyStackException e) {
      }
      queue = null;
    }
    if (weakListener != null) {
      weakListener.dispose();
      weakListener = null;
    }
    listener = null;
    modifiers = 0;
  }

  /** For OSX pre-1.4 laptops... */
  private boolean fnKeyDown;
  /** These aid in culling duplicate key events, pre-1.4. */
  private int lastKeyPress = KeyEvent.VK_UNDEFINED;
  private int lastKeyRelease = KeyEvent.VK_UNDEFINED;
  private KeyStroke lastKeyStroke;
  private char lastKeyChar = KeyEvent.VK_UNDEFINED;
  private Component lastKeyComponent;

  // Returns whether the event is spurious and should be discarded.
  private boolean isSpuriousEvent(AWTEvent event) {
    return isDuplicateKeyEvent(event) || isOSXFunctionKey(event) || isDuplicateDispose(event);
  }

  // TODO: (Abbot) Maybe make this an AWT event listener instead, so we can use one instance instead of one per window.
  private static class DisposalWatcher extends ComponentAdapter {
    private final Map<Window, Boolean> map;

    DisposalWatcher(Map<Window, Boolean> map) {
      this.map = map;
    }

    @Override public void componentShown(ComponentEvent e) {
      e.getComponent().removeComponentListener(this);
      map.remove(e.getComponent());
    }
  }

  // We want to ignore consecutive event indicating window disposal; there
  // needs to be an intervening SHOWN/OPEN before we're interested again.
  private boolean isDuplicateDispose(AWTEvent event) {
    if (event instanceof WindowEvent) {
      WindowEvent we = (WindowEvent) event;
      switch (we.getID()) {
        case WindowEvent.WINDOW_CLOSED:
          Window w = we.getWindow();
          if (disposedWindows.containsKey(w)) { return true; }
          disposedWindows.put(w, Boolean.TRUE);
          w.addComponentListener(new DisposalWatcher(disposedWindows));
          break;
        case WindowEvent.WINDOW_CLOSING:
          break;
        default:
          disposedWindows.remove(we.getWindow());
          break;
      }
    }

    return false;
  }

  // Flag duplicate key events on pre-1.4 VMs, and repeated modifiers.
  private boolean isDuplicateKeyEvent(AWTEvent event) {
    int id = event.getID();
    if (id == KeyEvent.KEY_PRESSED) {
      KeyEvent ke = (KeyEvent) event;
      lastKeyRelease = KeyEvent.VK_UNDEFINED;
      int code = ke.getKeyCode();

      if (code == lastKeyPress) {
        // Discard duplicate key events; they don't add any
        // information.
        // A duplicate key event is sent to the parent frame on
        // components that don't otherwise consume it (JButton)
        if (event.getSource() != lastKeyComponent) {
          lastKeyPress = KeyEvent.VK_UNDEFINED;
          lastKeyComponent = null;
          return true;
        }
      }
      lastKeyPress = code;
      lastKeyComponent = ke.getComponent();

      // Don't pass on key repeats for modifier keys (w32)
      if (isModifier(code)) {
        int mask = maskFor(code);
        if ((mask & modifiers) != 0 && !captureModifierRepeats) { return true; }
      }
      modifiers = ke.getModifiers();
    } else if (id == KeyEvent.KEY_RELEASED) {
      KeyEvent ke = (KeyEvent) event;
      lastKeyPress = KeyEvent.VK_UNDEFINED;
      int code = ke.getKeyCode();
      if (code == lastKeyRelease) {
        if (event.getSource() != lastKeyComponent) {
          lastKeyRelease = KeyEvent.VK_UNDEFINED;
          lastKeyComponent = null;
          return true;
        }
      }
      lastKeyRelease = code;
      lastKeyComponent = ke.getComponent();
      modifiers = ke.getModifiers();
    } else if (id == KeyEvent.KEY_TYPED) {
      KeyStroke ks = KeyStroke.getKeyStrokeForEvent((KeyEvent) event);
      char ch = ((KeyEvent) event).getKeyChar();
      if (ks.equals(lastKeyStroke) || ch == lastKeyChar) {
        if (event.getSource() != lastKeyComponent) {
          lastKeyStroke = null;
          lastKeyChar = KeyEvent.VK_UNDEFINED;
          lastKeyComponent = null;
          return true;
        }
      }
      lastKeyStroke = ks;
      lastKeyChar = ch;
      lastKeyComponent = ((KeyEvent) event).getComponent();
    } else {
      lastKeyPress = lastKeyRelease = KeyEvent.VK_UNDEFINED;
      lastKeyComponent = null;
    }

    return false;
  }

  // Discard function key press/release on 1.3.1 OSX laptops. 
  private boolean isOSXFunctionKey(AWTEvent event) {
    // FIXME fn pressed after arrow keys results in a RELEASE event
    if (event.getID() == KeyEvent.KEY_RELEASED) {
      if (((KeyEvent) event).getKeyCode() == KeyEvent.VK_CONTROL && fnKeyDown) {
        fnKeyDown = false;
        return true;
      }
    } else if (event.getID() == KeyEvent.KEY_PRESSED) {
      if (((KeyEvent) event).getKeyCode() == KeyEvent.VK_CONTROL) {
        int mods = ((KeyEvent) event).getModifiers();
        if ((mods & KeyEvent.CTRL_MASK) == 0) {
          fnKeyDown = true;
          return true;
        }
      }
    }
    return false;
  }

  /** Event reception callback. */
  public void eventDispatched(AWTEvent event) {
    boolean discard = isSpuriousEvent(event);
    if (!discard && listener != null) delegate(event);
  }

  protected void delegate(AWTEvent e) {
    listener.eventDispatched(e);
  }


  /**
   * Catches native drop target events, which are normally hidden from AWTEventListeners.
   */
  private class DragAwareEventQueue extends EventQueue {
    protected void relayDnDEvent(MouseEvent e) {
      int id = e.getID();
      if (id == MouseEvent.MOUSE_MOVED || id == MouseEvent.MOUSE_DRAGGED) {
        if ((mask & InputEvent.MOUSE_MOTION_EVENT_MASK) != 0) {
          eventDispatched(e);
        }
      } else if (id >= MouseEvent.MOUSE_FIRST && id <= MouseEvent.MOUSE_LAST) {
        if ((mask & InputEvent.MOUSE_EVENT_MASK) != 0) {
          eventDispatched(e);
        }
      }
    }

    public void pop() throws EmptyStackException {
      if (Toolkit.getDefaultToolkit().getSystemEventQueue() == this) super.pop();
    }

    /**
     * Dispatch native drag/drop events the same way non-native drags are reported. Enter/Exit are reported with the
     * appropriate source, while drag and release events use the drag source as the source.
     * <p>
     * TODO: implement enter/exit events TODO: change source to drag source, not mouse under
     */
    protected void dispatchEvent(AWTEvent e) {
      if (e.getClass().getName().indexOf("SunDropTargetEvent") != -1) {
        MouseEvent me = (MouseEvent) e;
        Component target = SwingUtilities.getDeepestComponentAt(me.getComponent(), me.getX(), me.getY());
        if (target != me.getSource()) {
          me = SwingUtilities.convertMouseEvent(me.getComponent(), me, target);
        }
        relayDnDEvent(me);
      }
      super.dispatchEvent(e);
    }
  }
}