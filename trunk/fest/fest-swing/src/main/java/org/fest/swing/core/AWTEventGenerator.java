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
import static javax.swing.SwingUtilities.convertPoint;
import static org.fest.swing.core.FocusOwnerFinder.focusOwner;
import static org.fest.swing.keystroke.KeyStrokeMap.charFor;
import static org.fest.swing.util.AWT.*;
import static org.fest.swing.util.Modifiers.*;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.KeyStroke;

import org.fest.swing.input.InputState;
import org.fest.swing.util.MouseEventTarget;

/**
 * Understands input event generation by posting <code>{@link AWTEvent}</code>s in a <code>{@link EventQueue}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class AWTEventGenerator implements InputEventGenerator {

  private static final int MULTI_CLICK_INTERVAL = 250; // a guess

  private final InputState inputState;
  private final AWTEventPoster eventPoster;

  AWTEventGenerator(InputState inputState, AWTEventPoster eventPoster) {
    this.inputState = inputState;
    this.eventPoster = eventPoster;
  }

  /** ${@inheritDoc} */
  public void moveMouse(Component c, int x, int y) {
    Component target = c;
    Component eventSource = c;
    Point p = new Point(x, y);
    int eventId = MOUSE_MOVED;
    boolean outside = false;
    if (inputState.dragInProgress()) {
      eventId = MOUSE_DRAGGED;
      eventSource = inputState.dragSource();
    } else {
      MouseEventTarget newTarget = retargetMouseEvent(eventSource, eventId, p);
      eventSource = target = newTarget.source;
      p.setLocation(newTarget.position);
      outside = isPointOutsideComponent(p, target);
    }
    Component current = inputState.mouseComponent();
    if (current != target) {
      if (outside && current != null) {
        postMouseMotion(current, MOUSE_EXITED, convertPoint(target, p.x, p.y, current));
        return;
      }
      postMouseMotion(target, MOUSE_ENTERED, new Point(p.x ,p.y));
    }
    Point dragPosition = new Point(p.x, p.y);
    // drag coordinates are relative to drag source component
    if (eventId == MOUSE_DRAGGED) dragPosition = convertPoint(target, dragPosition, eventSource);
    postMouseMotion(eventSource, eventId, dragPosition);
    // Add an exit event if warranted
    if (outside) postMouseMotion(target, MOUSE_EXITED, p);
  }

  private boolean isPointOutsideComponent(Point p, Component c) {
    int x = p.x;
    int y = p.y;
    return x < 0 || y < 0 || x >= c.getWidth() || y >= c.getHeight();
  }

  /** ${@inheritDoc} */
  public void releaseMouse(int buttons) {
    Component c = null;
    MouseEvent lastMousePress = eventPoster.lastMousePress();
    if (inputState.dragInProgress()) c = inputState.dragSource();
    else if (lastMousePress != null) c = lastMousePress.getComponent();
    else c = inputState.mouseComponent();
    Point where = inputState.mouseLocation();
    if (c == null) return;
    if (where == null) {
      if (lastMousePress == null) return;
      where = lastMousePress.getPoint();
    }
    postMouseRelease(c, where.x, where.y, buttons);
  }

  // Post a mouse release event to the AWT event queue for the given component.
  private void postMouseRelease(Component c, int x, int y, int mask) {
    int count = inputState.clickCount();
    MouseEventTarget newTarget = retargetMouseEvent(c, MOUSE_PRESSED, new Point(x, y));
    Component target = newTarget.source;
    Point where = newTarget.position;
    long when = currentTimeMillis();
    int modifiers = inputState.keyModifiers() | mask;
    boolean popupTrigger = !popupOnPress() && (mask & popupMask()) != 0;
    MouseEvent mouseEvent = new MouseEvent(target, MOUSE_RELEASED, when, modifiers, where.x, where.y, count, popupTrigger);
    eventPoster.postEvent(c, mouseEvent);
  }

  /*
   * Generate a mouse enter/exit/move/drag for the destination component. Abbot: The VM automatically usually generates
   * exit events; need a test to define the behavior, though.
   */
  private void postMouseMotion(Component c, int eventId, Point where) {
    Component target = c;
    Point position = where;
    // The VM auto-generates exit events as needed (1.3, 1.4)
    if (eventId != MouseEvent.MOUSE_DRAGGED) {
      MouseEventTarget newTarget = retargetMouseEvent(target, eventId, where);
      target = newTarget.source;
      position = newTarget.position;
    }
    // Avoid multiple moves to the same location
    if (inputState.mouseComponent() == target && position.equals(inputState.mouseLocation())) return;
    long when = currentTimeMillis();
    int modifiers = inputState.modifiers();
    int x = position.x;
    int y = position.y;
    int clickCount = inputState.clickCount();
    eventPoster.postEvent(target, new MouseEvent(target, eventId, when, modifiers, x, y, clickCount, false));
  }

  /** ${@inheritDoc} */
  public void pressMouse(int buttons) {
    Component c = inputState.mouseComponent();
    if (c == null) return;
    Point where = inputState.mouseLocation();
    pressMouse(c, where, buttons);
  }

  /** ${@inheritDoc} */
  public void pressMouse(Component c, Point where, int buttons) {
    long current = currentTimeMillis();
    MouseEvent lastMousePress = eventPoster.lastMousePress();
    long last = lastMousePress != null ? lastMousePress.getWhen() : 0;
    int count = 1;
    MouseEventTarget target = retargetMouseEvent(c, MOUSE_PRESSED, where);
    Component source = target.source;
    if (eventPoster.countingClicks() && source == lastMousePress.getComponent())
      if ((current - last) < MULTI_CLICK_INTERVAL) count = inputState.clickCount() + 1;
    int modifiers = inputState.keyModifiers() | buttons;
    int x = target.position.x;
    int y = target.position.y;
    boolean popupTrigger = popupOnPress() && (buttons & popupMask()) != 0;
    eventPoster.postEvent(source, new MouseEvent(source, MOUSE_PRESSED, current, modifiers, x, y, count, popupTrigger));
  }

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

  /** ${@inheritDoc} */
  public void releaseKey(int keyCode) {
    int mods = inputState.modifiers();
    if (isModifier(keyCode)) mods &= ~maskFor(keyCode);
    postKeyEvent(KEY_RELEASED, mods, keyCode, CHAR_UNDEFINED);
  }

  private void postKeyEvent(int eventId, int modifiers, int keyCode, char character) {
    Component c = focusOwner();
    if (c == null) return;
    eventPoster.postEvent(c, new KeyEvent(c, eventId, currentTimeMillis(), modifiers, keyCode, character));
  }
}
