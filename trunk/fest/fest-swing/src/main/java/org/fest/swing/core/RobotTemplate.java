/*
 * Created on Jan 16, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.core;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.InvocationEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.KeyStroke;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.hierarchy.ComponentHierarchy;
import org.fest.swing.input.InputState;
import org.fest.swing.lock.ScreenLock;
import org.fest.swing.monitor.WindowMonitor;

import static java.awt.event.InputEvent.*;
import static java.awt.event.KeyEvent.CHAR_UNDEFINED;
import static java.awt.event.WindowEvent.WINDOW_CLOSING;
import static java.lang.System.currentTimeMillis;

import static org.fest.swing.core.InputModifiers.unify;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.keystroke.KeyStrokeMap.keyStrokeFor;
import static org.fest.swing.query.ComponentShowingQuery.isShowing;
import static org.fest.swing.timing.Pause.pause;
import static org.fest.swing.util.Modifiers.*;
import static org.fest.util.Strings.*;

/**
 * Understands a template for robots that simulate user events.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class RobotTemplate {

  private static final int WINDOW_DELAY = 20000;

  private static final Runnable EMPTY_RUNNABLE = new Runnable() {
    public void run() {}
  };

  private static final int BUTTON_MASK = BUTTON1_MASK | BUTTON2_MASK | BUTTON3_MASK;

  protected static Toolkit toolkit = Toolkit.getDefaultToolkit();
  protected static WindowMonitor windowMonitor = WindowMonitor.instance();
  protected static InputState inputState = new InputState(toolkit);

  /** Provides access to all the components in the hierarchy. */
  private final ComponentHierarchy hierarchy;

  /** Looks up <code>{@link java.awt.Component}</code>s. */
  private final ComponentFinder finder;

  private final Settings settings;

  private final InputEventGenerator eventGenerator;

  /**
   * Creates a new <code>{@link RobotTemplate}</code>.
   * @param hierarchy the component hierarchy to use.
   */
  protected RobotTemplate(ComponentHierarchy hierarchy) {
    ScreenLock.instance().acquire(this);
    this.hierarchy = hierarchy;
    settings = new Settings();
    eventGenerator = new RobotEventGenerator(settings);
    finder = new BasicComponentFinder(this.hierarchy);
  }

  /**
   * Safely display a window with proper EDT synchronization. This method blocks until the <code>{@link Window}</code>
   * is showing and ready for input.
   * @param w the window to display.
   */
  @RunsInEDT
  public void showWindow(Window w) {
    showWindow(w, null, true);
  }

  /**
   * Safely display a window with proper EDT synchronization. This method blocks until the <code>{@link Window}</code>
   * is showing and ready for input.
   * @param w the window to display.
   * @param size the size of the window to display.
   */
  @RunsInEDT
  public void showWindow(Window w, Dimension size) {
    showWindow(w, size, true);
  }

  /**
   * <p>
   * Safely display a window with proper EDT synchronization. This method blocks until the window is showing. This
   * method will return even when the window is a modal dialog, since the show method is called on the event dispatch
   * thread. The window will be packed if the pack flag is set, and set to the given size if it is non-<code>null</code>.
   * </p>
   * Modal dialogs may be shown with this method without blocking.
   * @param w the window to display.
   * @param size the size of the window to display.
   * @param pack flag that indicates if the window should be packed or not. By packed we mean calling
   * <code>w.pack()</code>.
   */
  @RunsInEDT
  public void showWindow(final Window w, final Dimension size, final boolean pack) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        if (pack) packAndEnsureSafePosition(w);
        if (size != null) w.setSize(size);
        w.setVisible(true);
      }
    });
    waitForWindow(w);
  }

  @RunsInCurrentThread
  private void packAndEnsureSafePosition(Window w) {
    w.pack();
    w.setLocation(100, 100);
  }

  @RunsInEDT
  private void waitForWindow(Window w) {
    long start = currentTimeMillis();
    while (!windowMonitor.isWindowReady(w) || !isShowing(w)) {
      long elapsed = currentTimeMillis() - start;
      if (elapsed > WINDOW_DELAY)
        throw new WaitTimedOutError(concat("Timed out waiting for Window to open (", String.valueOf(elapsed), "ms)"));
      pause();
    }
  }

  /**
   * Simulates a user closing the given window.
   * @param w the window to close.
   */
  @RunsInEDT
  public void close(Window w) {
    WindowEvent event = new WindowEvent(w, WINDOW_CLOSING);
    // If the window contains an applet, send the event on the applet's queue instead to ensure a shutdown from the
    // applet's context (assists AppletViewer cleanup).
    Component applet = findAppletDescendent(w);
    EventQueue eventQueue = windowMonitor.eventQueueFor(applet != null ? applet : w);
    eventQueue.postEvent(event);
    waitForIdle();
  }

  /**
   * Returns the <code>{@link Applet}</code> descendant of the given <code>{@link Container}</code>, if any.
   * @param c the given <code>Container</code>.
   * @return the <code>Applet</code> descendant of the given <code>Container</code>, or <code>null</code> if none
   * is found.
   */
  @RunsInEDT
  private Applet findAppletDescendent(Container c) {
    List<Component> found = new ArrayList<Component>(finder.findAll(c, new TypeMatcher(Applet.class)));
    if (found.size() == 1) return (Applet)found.get(0);
    return null;
  }

  /**
   * Cleans up any used resources (keyboard, mouse, open windows and <code>{@link ScreenLock}</code>) used by this
   * robot.
   */
  @RunsInEDT
  public void cleanUp() {
    cleanUp(true);
  }

  /**
   * Cleans up any used resources (keyboard, mouse and <code>{@link ScreenLock}</code>) used by this robot. This method
   * <strong>does not</strong> dispose any open windows.
   * <p>
   * <strong>Note:</strong> The preferred method to use to clean up resources is <code>{@link #cleanUp()}</code>. Using
   * <code>{@link #cleanUpWithoutDisposingWindows()}</code> may leave many windows open after each test. Use it on very
   * special cases. Please read <a href="http://code.google.com/p/fest/issues/detail?id=138" target="_blank">bug 138</a>
   * for more details.
   * </p>
   */
  @RunsInEDT
  public void cleanUpWithoutDisposingWindows() {
    cleanUp(false);
  }

  @RunsInEDT
  private void cleanUp(boolean disposeWindows) {
    try {
      if (disposeWindows) disposeWindows(hierarchy);
      releaseMouseButtons();
    } finally {
      ScreenLock.instance().release(this);
    }
  }

  @RunsInEDT
  private static void disposeWindows(final ComponentHierarchy hierarchy) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        for (Container c : hierarchy.roots()) if (c instanceof Window) dispose(hierarchy, (Window)c);
      }
    });
  }
  
  @RunsInCurrentThread
  private static void dispose(final ComponentHierarchy hierarchy, Window w) {
    hierarchy.dispose(w);
    w.setVisible(false);
    w.dispose();
  }

  /**
   * Simulates a user clicking the given mouse button, the given times at the given absolute coordinates.
   * @param where the coordinates where to click.
   * @param buttonMask the mask of the mouse button to click.
   * @param times the number of times to click the given mouse button.
   */
  @RunsInEDT
  protected void click(Point where, int buttonMask, int times) {
    int mask = buttonMask;
    int modifierMask = mask & ~BUTTON_MASK;
    mask &= BUTTON_MASK;
    pressModifiers(modifierMask);
    // From Abbot: Adjust the auto-delay to ensure we actually get a multiple click
    // In general clicks have to be less than 200ms apart, although the actual setting is not readable by Java.
    int delayBetweenEvents = settings.delayBetweenEvents();
    if (shouldSetDelayBetweenEventsToZeroWhenClicking(times)) settings.delayBetweenEvents(0);
    eventGenerator.pressMouse(where, mask);
    for (int i = times; i > 1; i--) {
      eventGenerator.releaseMouse(mask);
      eventGenerator.pressMouse(where, mask);
    }
    settings.delayBetweenEvents(delayBetweenEvents);
    eventGenerator.releaseMouse(mask);
    releaseModifiers(modifierMask);
    waitForIdle();
  }

  protected final boolean shouldSetDelayBetweenEventsToZeroWhenClicking(int times) {
    return times > 1 && settings.delayBetweenEvents() * 2 > 200;
  }

  /**
   * Presses the appropriate modifiers corresponding to the given mask.
   * @param modifierMask the given mask.
   */
  public void pressModifiers(int modifierMask) {
    for (int modifierKey : keysFor(modifierMask))
      pressKey(modifierKey);
  }

  /**
   * Releases the appropriate modifiers corresponding to the given mask.
   * @param modifierMask the given mask.
   */
  public void releaseModifiers(int modifierMask) {
    // For consistency, release in the reverse order of press.
    int[] modifierKeys = keysFor(modifierMask);
    for (int i = modifierKeys.length - 1; i >= 0; i--)
      releaseKey(modifierKeys[i]);
  }

  /**
   * Simulates a user moving the mouse pointer to the given coordinates.
   * @param p the given coordinates.
   */
  public void moveMouse(Point p) {
    moveMouse(p.x, p.y);
  }
  
  /**
   * Simulates a user moving the mouse pointer to the given coordinates.
   * @param x X coordinate.
   * @param y Y coordinate.
   */
  public void moveMouse(int x, int y) {
    eventGenerator.moveMouse(x, y);
  }

  /**
   * Simulates a user pressing a mouse button.
   * @param buttonMask the mask of the mouse button to press.
   */
  protected void pressMouse(int buttonMask) {
    eventGenerator.pressMouse(buttonMask);
  }

  /**
   * Simulates a user pressing the given mouse button on the given coordinates.
   * @param where the position where to press the given mouse button.
   * @param buttonMask the mask of the mouse button to press.
   */
  protected void pressMouse(Point where, int buttonMask) {
    eventGenerator.pressMouse(where, buttonMask);
  }

  /**
   * Releases the given mouse button.
   * @param buttonMask the mask of the mouse button to press.
   */
  @RunsInEDT
  protected void releaseMouse(int buttonMask) {
    mouseRelease(buttonMask);
  }

  /**
   * Releases any mouse button(s) used by the robot.
   */
  @RunsInEDT
  public void releaseMouseButtons() {
    int buttons = inputState.buttons();
    if (buttons == 0) return;
    mouseRelease(buttons);
  }

  /**
   * Rotates the scroll wheel on wheel-equipped mice.
   * @param amount number of "notches" to move the mouse wheel. Negative values indicate movement up/away from the user,
   * while positive values indicate movement down/towards the user.
   */
  public void rotateMouseWheel(int amount) {
    eventGenerator.rotateMouseWheel(amount);
    waitForIdle();
  }

  /**
   * Simulates a user entering the given text.
   * focus.
   * @param text the text to enter.
   */
  @RunsInEDT
  public void enterText(String text) {
    if (isEmpty(text)) return;
    for (char character : text.toCharArray()) type(character);
  }

  /**
   * Types the given character.
   * @param character the character to type.
   * @throws ActionFailedException if a key stroke for the given characters is not found.
   */
  @RunsInEDT
  public void type(char character) {
    KeyStroke keyStroke = keyStrokeFor(character);
    if (keyStroke == null) 
      throw actionFailure(concat("Unable to find key stroke for character", character));
    keyPressAndRelease(keyStroke.getKeyCode(), keyStroke.getModifiers());
  }

  /**
   * Type the given key code with the given modifiers. Modifiers is a mask from the available
   * <code>{@link java.awt.event.InputEvent}</code> masks.
   * @param keyCode the code of the key to press.
   * @param modifiers the given modifiers.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   */
  @RunsInEDT
  public void pressAndReleaseKey(int keyCode, int... modifiers) {
    keyPressAndRelease(keyCode, unify(modifiers));
    waitForIdle();
  }

  /**
   * Simulates a user pressing and releasing the given keys. This method does not affect the current focus.
   * @param keyCodes one or more codes of the keys to press.
   * @see java.awt.event.KeyEvent
   * @throws IllegalArgumentException if any of the given codes is not a valid key code.
   */
  @RunsInEDT
  public void pressAndReleaseKeys(int... keyCodes) {
    for (int keyCode : keyCodes) {
      keyPressAndRelease(keyCode, 0);
      waitForIdle();
    }
  }

  /**
   * Simulates a user pressing the given key with the given modifiers.
   * @param keyCode the code of the key to press.
   * @param modifiers the code of the modifier to press.
   */
  @RunsInEDT
  protected void keyPressAndRelease(int keyCode, int modifiers) {
    int updatedModifiers = updateModifierWithKeyCode(keyCode, modifiers);
    pressModifiers(updatedModifiers);
    if (updatedModifiers == modifiers) {
      doPressKey(keyCode);
      eventGenerator.releaseKey(keyCode);
    }
    releaseModifiers(updatedModifiers);
  }

  /**
   * Simulates a user pressing given key. This method does not affect the current focus.
   * @param keyCode the code of the key to press.
   * @see java.awt.event.KeyEvent
   * @throws IllegalArgumentException if the given code is not a valid key code.
   */
  @RunsInEDT
  public void pressKey(int keyCode) {
    doPressKey(keyCode);
    waitForIdle();
  }

  @RunsInEDT
  private void doPressKey(int keyCode) {
    eventGenerator.pressKey(keyCode, CHAR_UNDEFINED);
  }

  /**
   * Simulates a user releasing the given key. This method does not affect the current focus.
   * @param keyCode the code of the key to release.
   * @see java.awt.event.KeyEvent
   * @throws IllegalArgumentException if the given code is not a valid key code.
   */
  @RunsInEDT
  public void releaseKey(int keyCode) {
    eventGenerator.releaseKey(keyCode);
    waitForIdle();
  }

  @RunsInEDT
  private void mouseRelease(int buttons) {
    eventGenerator.releaseMouse(buttons);
  }

  /**
   * Wait for an idle AWT event queue. Note that this is different from the implementation of
   * <code>java.awt.Robot.waitForIdle()</code>, which may have events on the queue when it returns. Do <strong>NOT</strong>
   * use this method if there are animations or other continual refreshes happening, since in that case it may never
   * return.
   * @throws IllegalThreadStateException if this method is called from the event dispatch thread.
   */
  @RunsInEDT
  public void waitForIdle() {
    waitIfNecessary();
    Collection<EventQueue> queues = windowMonitor.allEventQueues();
    if (queues.size() == 1) {
      waitForIdle(toolkit.getSystemEventQueue());
      return;
    }
    // FIXME this resurrects dead event queues
    for (EventQueue queue : queues) waitForIdle(queue);
  }

  private void waitIfNecessary() {
    int delayBetweenEvents = settings.delayBetweenEvents();
    int eventPostingDelay  = settings.eventPostingDelay();
    if (eventPostingDelay > delayBetweenEvents) pause(eventPostingDelay - delayBetweenEvents);
  }

  private void waitForIdle(EventQueue eventQueue) {
    if (EventQueue.isDispatchThread())
      throw new IllegalThreadStateException("Cannot call method from the event dispatcher thread");
    // Abbot: as of Java 1.3.1, robot.waitForIdle only waits for the last event on the queue at the time of this
    // invocation to be processed. We need better than that. Make sure the given event queue is empty when this method
    // returns.
    // We always post at least one idle event to allow any current event dispatch processing to finish.
    long start = currentTimeMillis();
    int count = 0;
    do {
      // Timed out waiting for idle
      int idleTimeout = settings.idleTimeout();
      if (postInvocationEvent(eventQueue, idleTimeout)) break;
      // Timed out waiting for idle event queue
      if (currentTimeMillis() - start > idleTimeout) break;
      ++count;
      // Force a yield
      pause();
      // Abbot: this does not detect invocation events (i.e. what gets posted with EventQueue.invokeLater), so if
      // someone is repeatedly posting one, we might get stuck. Not too worried, since if a Runnable keeps calling
      // invokeLater on itself, *nothing* else gets much chance to run, so it seems to be a bad programming practice.
    } while (eventQueue.peekEvent() != null);
  }

  // Indicates whether we timed out waiting for the invocation to run
  @RunsInEDT
  private boolean postInvocationEvent(EventQueue eventQueue, long timeout) {
    Object lock = new RobotIdleLock();
    synchronized (lock) {
      eventQueue.postEvent(new InvocationEvent(toolkit, EMPTY_RUNNABLE, lock, true));
      long start = currentTimeMillis();
      try {
        // NOTE: on fast linux systems when showing a dialog, if we don't provide a timeout, we're never notified, and
        // the test will wait forever (up through 1.5.0_05).
        lock.wait(timeout);
        return (currentTimeMillis() - start) >= settings.idleTimeout();
      } catch (InterruptedException e) {}
      return false;
    }
  }

  private static class RobotIdleLock {
    RobotIdleLock() {}
  }

  /**
   * Indicates whether the robot is currently in a dragging operation.
   * @return <code>true</code> if the robot is currently in a dragging operation, <code>false</code> otherwise.
   */
  public boolean isDragging() {
    return inputState.dragInProgress();
  }

  /**
   * Returns the configuration settings for this robot.
   * @return the configuration settings for this robot.
   */
  public Settings settings() {
    return settings;
  }
  
  /**
   * Returns the <code>{@link ComponentFinder}</code> being used by this robot.
   * @return the <code>ComponentFinder</code> being used by this robot.
   */
  public ComponentFinder finder() {
    return finder;
  }

  /**
   * Returns the <code>{@link ComponentHierarchy}</code> being used by this robot.
   * @return the <code>ComponentHierarchy</code> being used by this robot.
   */
  public ComponentHierarchy hierarchy() {
    return hierarchy;
  }
  
  /**
   * Returns the event generator used by this robot.
   * @return the event generator used by this robot.
   */
  protected InputEventGenerator eventGenerator() { return eventGenerator; }
}
