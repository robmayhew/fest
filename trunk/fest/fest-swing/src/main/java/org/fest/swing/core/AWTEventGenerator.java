/*
 * Created on Apr 2, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.core;

import static java.awt.event.KeyEvent.*;
import static java.awt.event.MouseEvent.*;
import static java.lang.System.currentTimeMillis;
import static org.fest.swing.core.EventMode.AWT;
import static org.fest.swing.core.FocusOwnerFinder.focusOwner;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.keystroke.KeyStrokeMap.charFor;
import static org.fest.swing.util.AWT.*;
import static org.fest.swing.util.Modifiers.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.KeyStroke;

import org.fest.swing.input.InputState;
import org.fest.swing.monitor.WindowMonitor;
import org.fest.swing.util.MouseEventTarget;

/**
 * Understands SOMETHING DUMMY.
 *
 * @author 
 *
 */
class AWTEventGenerator implements InputEventGenerator {

  private static final int MULTI_CLICK_INTERVAL = 250; // a guess
  
  private AWTEvent lastEventPosted;
  private MouseEvent lastMousePress;
  private boolean countingClicks;

  private final Toolkit toolkit;
  private final WindowMonitor windowMonitor;
  private final InputState inputState;
  private final Settings settings;

  AWTEventGenerator(Toolkit toolkit, WindowMonitor windowMonitor, InputState inputState, Settings settings) {
    this.toolkit = toolkit;
    this.windowMonitor = windowMonitor;
    this.inputState = inputState;
    this.settings = settings;
  }
  
  /** ${@inheritDoc} */
  public void moveMouse(Component c, int x, int y) {}

  /** ${@inheritDoc} */
  public void pressKey(int keyCode, char keyChar) {
    int modifiers = inputState.modifiers();
    if (isModifier(keyCode)) modifiers |= maskFor(keyCode);
    postKeyEvent(KEY_PRESSED, modifiers, keyCode, CHAR_UNDEFINED);
    // Auto-generate KEY_TYPED events, as best we can
    int mask = inputState.modifiers();
    if (keyChar == CHAR_UNDEFINED) keyChar = charFor(KeyStroke.getKeyStroke(keyCode, mask));
    if (keyChar != CHAR_UNDEFINED) postKeyEvent(KEY_TYPED, mask, VK_UNDEFINED, keyChar);
  }

  private void postKeyEvent(int eventId, int modifiers, int keyCode, char character) {
    Component c = focusOwner();
    if (c == null) return;
    postEvent(c, new KeyEvent(c, eventId, currentTimeMillis(), modifiers, keyCode, character));
  }

  /** ${@inheritDoc} */
  public void pressMouse(Component c, Point where, int buttons) {
    long current = currentTimeMillis();
    long last = lastMousePress != null ? lastMousePress.getWhen() : 0;
    int count = 1;
    MouseEventTarget target = retargetMouseEvent(c, MOUSE_PRESSED, where);
    Component source = target.source;
    if (countingClicks && source == lastMousePress.getComponent())
      if ((current - last) < MULTI_CLICK_INTERVAL) count = inputState.clickCount() + 1;
    int modifiers = inputState.keyModifiers() | buttons;
    int x = target.position.x;
    int y = target.position.y;
    boolean popupTrigger = popupOnPress() && (buttons & popupMask()) != 0;
    postEvent(source, new MouseEvent(source, MOUSE_PRESSED, current, modifiers, x, y, count, popupTrigger));
  }

  /** ${@inheritDoc} */
  public void releaseKey(int keyCode) {}

  /** ${@inheritDoc} */
  public void releaseMouse(int buttons) {}

  // Post the given event to the corresponding event queue for the given component.
  private void postEvent(Component c, AWTEvent event) {
    if (isAWTMode() && isAWTPopupMenuBlocking())
      throw actionFailure("Event queue is blocked by an active AWT PopupMenu");
    // Force an update of the input state, so that we're in synch
    // internally. Otherwise we might post more events before this
    // one gets processed and end up using stale values for those events.
    inputState.update(event);
    EventQueue q = eventQueueFor(c);
    q.postEvent(event);
    pause(settings.delayBetweenEvents());
    verifyPostedEvent(c, event);
  }

  private boolean isAWTMode() {
    return AWT.equals(settings.eventMode());
  }

  /* Usually only needed when dealing with Applets. */
  private EventQueue eventQueueFor(Component c) {
    return c != null ? windowMonitor.eventQueueFor(c) : toolkit.getSystemEventQueue();
  }

  private void verifyPostedEvent(Component c, AWTEvent event) {
    AWTEvent previous = lastEventPosted;
    lastEventPosted = event;
    if (!(event instanceof MouseEvent)) return;
    MouseEvent mouseEvent = (MouseEvent) event;
    updateMousePressWith(mouseEvent);
    // Generate a click if there are no events between press/release.
    if (isAWTMode() && event.getID() == MOUSE_RELEASED && previous.getID() == MOUSE_PRESSED) {
      long when = currentTimeMillis();
      int modifiers = mouseEvent.getModifiers();
      int x = mouseEvent.getX();
      int y = mouseEvent.getY();
      int clickCount = mouseEvent.getClickCount();
      postEvent(c, new MouseEvent(c, MOUSE_CLICKED, when, modifiers, x, y, clickCount, false));
    }
  }

  private void updateMousePressWith(MouseEvent event) {
    int eventId = event.getID();
    if (eventId == MOUSE_PRESSED) {
      lastMousePress = event;
      countingClicks = true;
      return;
    }
    if (eventId != MOUSE_RELEASED && eventId != MOUSE_CLICKED) countingClicks = false;
  }
}
