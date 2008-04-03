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

import static java.awt.event.MouseEvent.*;
import static java.lang.System.currentTimeMillis;
import static org.fest.swing.core.EventMode.AWT;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.util.AWT.isAWTPopupMenuBlocking;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;

import org.fest.swing.input.InputState;
import org.fest.swing.monitor.WindowMonitor;

/**
 * Understands posting <code>{@link AWTEvent}</code>s in a <code>{@link EventQueue}</code>.
 *
 * @author Yvonne Wang
 */
class AWTEventPoster {

  private final Toolkit toolkit;
  private final InputState inputState;
  private final WindowMonitor windowMonitor;
  private final Settings settings;

  private AWTEvent lastEventPosted;
  private MouseEvent lastMousePress;
  private boolean countingClicks;

  AWTEventPoster(Toolkit toolkit, InputState inputState, WindowMonitor windowMonitor, Settings settings) {
    this.toolkit = toolkit;
    this.inputState = inputState;
    this.windowMonitor = windowMonitor;
    this.settings = settings;
  }

  // Post the given event to the corresponding event queue for the given component.
  void postEvent(Component c, AWTEvent event) {
    if (isAWTMode() && isAWTPopupMenuBlocking())
      throw actionFailure("Event queue is blocked by an active AWT PopupMenu");
    // Force an update of the input state, so that we're in synch internally. Otherwise we might post more events before
    // this one gets processed and end up using stale values for those events.
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

  AWTEvent lastEventPosted() { return lastEventPosted; }

  MouseEvent lastMousePress() { return lastMousePress; }

  boolean countingClicks() { return countingClicks; }
  void countingClicks(boolean countingClicks) { this.countingClicks = countingClicks; }
}
