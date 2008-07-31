/*
 * Created on Sep 29, 2006
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
 * Copyright @2006-2008 the original author or authors.
 */
package org.fest.swing.core;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.InvocationEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.Collection;

import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.hierarchy.ComponentHierarchy;
import org.fest.swing.hierarchy.ExistingHierarchy;
import org.fest.swing.input.InputState;
import org.fest.swing.monitor.WindowMonitor;
import org.fest.swing.util.TimeoutWatch;

import static java.awt.event.InputEvent.*;
import static java.awt.event.KeyEvent.*;
import static java.lang.System.currentTimeMillis;
import static javax.swing.SwingUtilities.*;

import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.EventMode.*;
import static org.fest.swing.core.FocusMonitor.addFocusMonitorTo;
import static org.fest.swing.core.FocusOwnerFinder.focusOwner;
import static org.fest.swing.core.InputModifiers.unify;
import static org.fest.swing.core.MouseButton.*;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.core.WindowAncestorFinder.ancestorOf;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.hierarchy.NewHierarchy.ignoreExistingComponents;
import static org.fest.swing.keystroke.KeyStrokeMap.keyStrokeFor;
import static org.fest.swing.task.GetJPopupMenuInvokerTask.invokerOf;
import static org.fest.swing.task.IsComponentShowingTask.isShowing;
import static org.fest.swing.util.AWT.centerOf;
import static org.fest.swing.util.Modifiers.*;
import static org.fest.swing.util.Platform.isOSX;
import static org.fest.swing.util.TimeoutWatch.startWatchWithTimeoutOf;
import static org.fest.util.Strings.*;

/**
 * Understands simulation of user events on a GUI <code>{@link Component}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class RobotFixture implements Robot {

  private static final int POPUP_DELAY = 10000;
  private static final int POPUP_TIMEOUT = 5000;
  private static final int WINDOW_DELAY = 20000;

  private static final ComponentMatcher POPUP_MATCHER = new TypeMatcher(JPopupMenu.class, true);

  private static final Runnable EMPTY_RUNNABLE = new Runnable() {
    public void run() {}
  };

  private static final int BUTTON_MASK = BUTTON1_MASK | BUTTON2_MASK | BUTTON3_MASK;

  private static Toolkit toolkit = Toolkit.getDefaultToolkit();
  private static WindowMonitor windowMonitor = WindowMonitor.instance();
  private static InputState inputState = new InputState(toolkit);

  /** Provides access to all the components in the hierarchy. */
  private final ComponentHierarchy hierarchy;

  /** Looks up <code>{@link java.awt.Component}</code>s. */
  private final ComponentFinder finder;

  private final Settings settings;

  private final AWTEventPoster eventPoster;
  private final InputEventGenerators eventGenerators;

  /**
   * Creates a new <code>{@link RobotFixture}</code> with a new AWT hierarchy. <code>{@link Component}</code>s
   * created before the created <code>{@link RobotFixture}</code> cannot be accessed by such
   * <code>{@link RobotFixture}</code>.
   * @return the created robot fixture.
   */
  public static RobotFixture robotWithNewAwtHierarchy() {
    return new RobotFixture(ignoreExistingComponents());
  }

  /**
   * Creates a new <code>{@link RobotFixture}</code> that has access to all the GUI components in the AWT hierarchy.
   * @return the created robot fixture.
   */
  public static RobotFixture robotWithCurrentAwtHierarchy() {
    return new RobotFixture(new ExistingHierarchy());
  }

  /**
   * Creates a new <code>{@link RobotFixture}</code>.
   * @param hierarchy the AWT component hierarchy to use.
   */
  RobotFixture(ComponentHierarchy hierarchy) {
    ScreenLock.instance().acquire(this);
    this.hierarchy = hierarchy;
    settings = new Settings();
    eventPoster = new AWTEventPoster(toolkit, inputState, windowMonitor, settings);
    eventGenerators = new InputEventGenerators(eventPoster);
    finder = new BasicComponentFinder(this.hierarchy);
  }

  /** ${@inheritDoc} */
  public ComponentPrinter printer() {
    return finder().printer();
  }

  /** ${@inheritDoc} */
  public ComponentFinder finder() {
    return finder;
  }

  /** ${@inheritDoc} */
  public void showWindow(Window w) {
    showWindow(w, null, true);
  }

  /** ${@inheritDoc} */
  public void showWindow(Window w, Dimension size) {
    showWindow(w, size, true);
  }

  /** ${@inheritDoc} */
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

  void packAndEnsureSafePosition(Window w) {
    w.pack();
    w.setLocation(100, 100);
  }

  private void waitForWindow(Window w) {
    long start = currentTimeMillis();
    while ((isRobotMode() && !windowMonitor.isWindowReady(w)) || !isShowing(w)) {
      long elapsed = currentTimeMillis() - start;
      if (elapsed > WINDOW_DELAY)
        throw new WaitTimedOutError(concat("Timed out waiting for Window to open (", String.valueOf(elapsed), "ms)"));
      pause();
    }
  }

  /** ${@inheritDoc} */
  public void close(Window w) {
    if (!isShowing(w)) return;
    focus(w);
    // Move to a corner and "pretend" to use the window manager control
    try {
      Point p = closeLocation(w);
      moveMouse(w, p.x, p.y);
    } catch (RuntimeException e) {}
    WindowEvent event = new WindowEvent(w, WindowEvent.WINDOW_CLOSING);
    // If the window contains an applet, send the event on the applet's queue instead to ensure a shutdown from the
    // applet's context (assists AppletViewer cleanup).
    Component applet = findAppletDescendent(w);
    EventQueue eq = windowMonitor.eventQueueFor(applet != null ? applet : w);
    eq.postEvent(event);
  }

  /**
   * Returns the <code>{@link Applet}</code> descendant of the given <code>{@link Container}</code>, if any.
   * @param c the given <code>Container</code>.
   * @return the <code>Applet</code> descendant of the given <code>Container</code>, or <code>null</code> if none
   *         is found.
   */
  private Applet findAppletDescendent(Container c) {
    try {
      return finder.findByType(c, Applet.class);
    } catch (ComponentLookupException e) {
      return null;
    }
  }

  private Point closeLocation(Container c) {
    if (isOSX()) return closeLocationForOSX(c);
    Insets insets = c.getInsets();
    return new Point(c.getSize().width - insets.right - 10, insets.top / 2);
  }

  private Point closeLocationForOSX(Container c) {
    Insets insets = c.getInsets();
    return new Point(insets.left + 15, insets.top / 2);
  }

  /** ${@inheritDoc} */
  public void focus(Component c) {
    focus(c, false);
  }

  /** ${@inheritDoc} */
  public void focusAndWaitForFocusGain(Component c) {
    focus(c, true);
  }

  private void focus(final Component c, boolean wait) {
    Component currentOwner = focusOwner();
    if (currentOwner == c) return;
    FocusMonitor focusMonitor = addFocusMonitorTo(c);
    // for pointer focus
    moveMouse(c);
    waitForIdle();
    // Make sure the correct window is in front
    Window currentOwnerAncestor = currentOwner != null ? ancestorOf(currentOwner) : null;
    Window componentAncestor = ancestorOf(c);
    if (currentOwnerAncestor != componentAncestor) {
      activate(componentAncestor);
      waitForIdle();
    }
    invokeAndWait(c, new RequestFocusTask(c));
    try {
      if (wait) {
        TimeoutWatch watch = startWatchWithTimeoutOf(settings().timeoutToBeVisible());
        while (!focusMonitor.hasFocus()) {
          if (watch.isTimeOut()) throw actionFailure(concat("Focus change to ", format(c), " failed"));
          pause();
        }
      }
    } finally {
      c.removeFocusListener(focusMonitor);
    }
  }

  /**
   * Activates the given <code>{@link Window}</code>. "Activate" means that the given window gets the keyboard focus.
   * @param w the window to activate.
   */
  private void activate(Window w) {
    invokeAndWait(w, new ActivateWindowTask(w));
    moveMouse(w); // For pointer-focus systems
  }

  /** ${@inheritDoc} */
  public void invokeAndWait(Runnable action) {
    invokeAndWait(null, action);
  }

  /** ${@inheritDoc} */
  public void invokeAndWait(Component c, Runnable action) {
    invokeLater(c, action);
    waitForIdle();
  }

  /** ${@inheritDoc} */
  public void invokeLater(Component c, Runnable action) {
    EventQueue queue = eventQueueFor(c);
    queue.postEvent(new InvocationEvent(toolkit, action));
  }

  /** ${@inheritDoc} */
  public void cleanUp() {
    cleanUp(true);
  }

  /** ${@inheritDoc} */
  public void cleanUpWithoutDisposingWindows() {
    cleanUp(false);
  }

  private void cleanUp(boolean disposeWindows) {
    if (disposeWindows) disposeWindows();
    releaseMouseButtons();
    ScreenLock.instance().release(this);
  }

  private void disposeWindows() {
    for (Container c : roots()) {
      if (!(c instanceof Window)) continue;
      Window w = (Window) c;
      hierarchy.dispose(w);
      w.setVisible(false);
      w.dispose();
    }
  }

  private Collection<? extends Container> roots() {
    return hierarchy.roots();
  }

  /**
   * Simulates a user clicking once the given <code>{@link Component}</code> using the left mouse button.
   * @param c the <code>Component</code> to click on.
   */
  public void click(Component c) {
    click(c, LEFT_BUTTON);
  }

  /** ${@inheritDoc} */
  public void rightClick(Component c) {
    click(c, RIGHT_BUTTON);
  }

  /** ${@inheritDoc} */
  public void click(Component c, MouseButton button) {
    click(c, button, 1);
  }

  /** ${@inheritDoc} */
  public void doubleClick(Component c) {
    click(c, LEFT_BUTTON, 2);
  }

  /** ${@inheritDoc} */
  public void click(Component c, MouseButton button, int times) {
    click(c, centerOf(c), button, times);
  }

  /** ${@inheritDoc} */
  public void click(Component c, Point where) {
    click(c, where, LEFT_BUTTON, 1);
  }

  /** ${@inheritDoc} */
  public void click(Component c, Point where, MouseButton button, int times) {
    focus(c);
    int mask = button.mask;
    int modifierMask = mask & ~BUTTON_MASK;
    mask &= BUTTON_MASK;
    pressModifiers(modifierMask);
    // From Abbot: Adjust the auto-delay to ensure we actually get a multiple click
    // In general clicks have to be less than 200ms apart, although the actual setting is not readable by Java.
    int delayBetweenEvents = settings.delayBetweenEvents();
    if (times > 1 && delayBetweenEvents * 2 > 200) settings.delayBetweenEvents(0);
    eventGenerator().pressMouse(c, where, mask);
    for (int i = times; i > 1; i--) {
      eventGenerator().releaseMouse(mask);
      eventGenerator().pressMouse(c, where, mask);
    }
    settings.delayBetweenEvents(delayBetweenEvents);
    eventGenerator().releaseMouse(mask);
    releaseModifiers(modifierMask);
    waitForIdle();
  }

  /** ${@inheritDoc} */
  public void pressModifiers(int modifierMask) {
    for (int modifierKey : keysFor(modifierMask))
      pressKey(modifierKey);
  }

  /** ${@inheritDoc} */
  public void releaseModifiers(int modifierMask) {
    // For consistency, release in the reverse order of press.
    int[] modifierKeys = keysFor(modifierMask);
    for (int i = modifierKeys.length - 1; i >= 0; i--)
      releaseKey(modifierKeys[i]);
  }

  /** ${@inheritDoc} */
  public void pressMouse(MouseButton button) {
    eventGenerator().pressMouse(button.mask);
  }

  /** ${@inheritDoc} */
  public void pressMouse(Component c, Point where) {
    pressMouse(c, where, LEFT_BUTTON);
  }

  /** ${@inheritDoc} */
  public void pressMouse(Component c, Point where, MouseButton button) {
    jitter(c, where);
    moveMouse(c, where.x, where.y);
    eventGenerator().pressMouse(c, where, button.mask);
  }

  /** ${@inheritDoc} */
  public void jitter(Component c) {
    jitter(c, centerOf(c));
  }

  /** ${@inheritDoc} */
  public void jitter(Component c, Point where) {
    int x = where.x;
    int y = where.y;
    moveMouse(c, (x > 0 ? x - 1 : x + 1), y);
  }

  /** ${@inheritDoc} */
  public void moveMouse(Component c) {
    moveMouse(c, centerOf(c));
  }

  /** ${@inheritDoc} */
  public void moveMouse(Component c, Point p) {
    moveMouse(c, p.x, p.y);
  }

  /** ${@inheritDoc} */
  public void moveMouse(Component c, int x, int y) {
    if (!waitForComponentToBeReady(c, settings.timeoutToBeVisible()))
      throw actionFailure(concat("Could not obtain position of component ", format(c)));
    eventGenerator().moveMouse(c, x, y);
    waitForIdle();
  }

  // Wait the given number of milliseconds for the component to be showing and ready.
  private boolean waitForComponentToBeReady(Component c, long timeout) {
    if (isReadyForInput(c)) return true;
    TimeoutWatch watch = startWatchWithTimeoutOf(timeout);
    while (!isReadyForInput(c)) {
      if (c instanceof JPopupMenu) {
        // wiggle the mouse over the parent menu item to ensure the sub-menu shows
        Component invoker = invokerOf((JPopupMenu)c);
        if (invoker instanceof JMenu)
          jitter(invoker, new Point(invoker.getWidth() / 2, invoker.getHeight() / 2));
      }
      if (watch.isTimeOut()) return false;
      pause();
    }
    return true;
  }

  /** ${@inheritDoc} */
  public void enterText(String text) {
    if (isEmpty(text)) return;
    for (char character : text.toCharArray()) type(character);
  }

  /** ${@inheritDoc} */
  public void type(char character) {
    KeyStroke keyStroke = keyStrokeFor(character);
    if (keyStroke == null) {
      Component focus = focusOwner();
      if (focus == null) return;
      KeyEvent keyEvent = keyEventFor(focus, character);
      // Allow any pending robot events to complete; otherwise we might stuff the typed event before previous
      // robot-generated events are posted.
      if (isRobotMode()) waitForIdle();
      eventPoster.postEvent(focus, keyEvent);
      return;
    }
    keyPressAndRelease(keyStroke.getKeyCode(), keyStroke.getModifiers());
  }

  private boolean isRobotMode() {
    return ROBOT.equals(settings.eventMode());
  }

  private KeyEvent keyEventFor(Component c, char character) {
    return new KeyEvent(c, KEY_TYPED, System.currentTimeMillis(), 0, VK_UNDEFINED, character);
  }


  /* Usually only needed when dealing with Applets. */
  private EventQueue eventQueueFor(Component c) {
    return c != null ? windowMonitor.eventQueueFor(c) : toolkit.getSystemEventQueue();
  }

  /** ${@inheritDoc} */
  public void pressAndReleaseKey(int keyCode, int... modifiers) {
    keyPressAndRelease(keyCode, unify(modifiers));
  }

  /** ${@inheritDoc} */
  public void pressAndReleaseKeys(int... keyCodes) {
    for (int keyCode : keyCodes) {
      keyPressAndRelease(keyCode, 0);
      waitForIdle();
    }
  }

  private void keyPressAndRelease(int keyCode, int modifiers) {
    int updatedModifiers = updateModifierWithKeyCode(keyCode, modifiers);
    pressModifiers(updatedModifiers);
    if (updatedModifiers == modifiers) {
      keyPress(keyCode);
      eventGenerator().releaseKey(keyCode);
    }
    releaseModifiers(updatedModifiers);
  }

  /** ${@inheritDoc} */
  public void pressKey(int keyCode) {
    keyPress(keyCode);
    waitForIdle();
  }

  private void keyPress(int keyCode) {
    eventGenerator().pressKey(keyCode, CHAR_UNDEFINED);
  }

  /** ${@inheritDoc} */
  public void releaseKey(int keyCode) {
    eventGenerator().releaseKey(keyCode);
    waitForIdle();
  }

  /** ${@inheritDoc} */
  public void releaseLeftMouseButton() {
    releaseMouseButton(LEFT_BUTTON);
  }

  /** ${@inheritDoc} */
  public void releaseMouseButton(MouseButton button) {
    mouseRelease(button.mask);
  }

  /** ${@inheritDoc} */
  public void releaseMouseButtons() {
    int buttons = inputState.buttons();
    if (buttons == 0) return;
    mouseRelease(buttons);
  }

  private void mouseRelease(int buttons) {
    eventGenerator().releaseMouse(buttons);
  }

  private InputEventGenerator eventGenerator() {
    return eventGenerators.current();
  }

  /** ${@inheritDoc} */
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
    int eventPostingDelay = settings.eventPostingDelay();
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

  /** ${@inheritDoc} */
  public boolean isDragging() {
    return inputState.dragInProgress();
  }

  /** ${@inheritDoc} */
  public JPopupMenu showPopupMenu(Component invoker) {
    return showPopupMenu(invoker, centerOf(invoker));
  }

  /** ${@inheritDoc} */
  public JPopupMenu showPopupMenu(Component invoker, Point location) {
    click(invoker, location, RIGHT_BUTTON, 1);
    JPopupMenu popup = findActivePopupMenu();
    if (popup == null)
      throw new ComponentLookupException(concat("Unable to show popup at ", location, " on ", format(invoker)));
    long start = currentTimeMillis();
    while (!isReadyForInput(getWindowAncestor(popup)) && currentTimeMillis() - start > POPUP_DELAY)
      pause();
    return popup;
  }

  /** ${@inheritDoc} */
  public boolean isReadyForInput(Component c) {
    if (isAWTMode()) return isShowing(c);
    Window w = ancestorOf(c);
    if (w == null) throw actionFailure(concat("Component ", format(c), " does not have a Window ancestor"));
    return isShowing(c) && windowMonitor.isWindowReady(w);
  }

  private boolean isAWTMode() {
    return AWT.equals(settings.eventMode());
  }

  /** ${@inheritDoc} */
  public JPopupMenu findActivePopupMenu() {
    JPopupMenu popup = activePopupMenu();
    if (popup != null || isEventDispatchThread()) return popup;
    TimeoutWatch watch = startWatchWithTimeoutOf(POPUP_TIMEOUT);
    while ((popup = activePopupMenu()) == null) {
      if (watch.isTimeOut()) break;
      pause(100);
    }
    return popup;
  }

  private JPopupMenu activePopupMenu() {
    try {
      return (JPopupMenu) finder().find(POPUP_MATCHER);
    } catch (ComponentLookupException e) {
      return null;
    }
  }

  /** ${@inheritDoc} */
  public void requireNoJOptionPaneIsShowing() {
    try {
      JOptionPane found = finder().findByType(JOptionPane.class, true);
      if (found == null) return;
      fail(concat("Expecting no JOptionPane to be showing, but found:<", format(found), ">"));
    } catch (ComponentLookupException e) {}
  }

  /** ${@inheritDoc} */
  public Settings settings() {
    return settings;
  }

  /** ${@inheritDoc} */
  public ComponentHierarchy hierarchy() {
    return hierarchy;
  }
}
