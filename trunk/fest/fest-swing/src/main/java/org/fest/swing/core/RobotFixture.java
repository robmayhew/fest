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
 * Copyright @2006 the original author or authors.
 */
package org.fest.swing.core;

import static java.awt.event.InputEvent.*;
import static java.awt.event.KeyEvent.*;
import static java.awt.event.MouseEvent.*;
import static java.lang.String.valueOf;
import static java.lang.System.currentTimeMillis;
import static javax.swing.SwingUtilities.*;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.EventMode.*;
import static org.fest.swing.core.FocusMonitor.addFocusMonitorTo;
import static org.fest.swing.core.MouseButton.*;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.hierarchy.NewHierarchy.ignoreExistingComponents;
import static org.fest.swing.keystroke.KeyStrokeMap.*;
import static org.fest.swing.util.AWT.*;
import static org.fest.swing.util.Modifiers.*;
import static org.fest.swing.util.Platform.*;
import static org.fest.swing.util.TimeoutWatch.startWatchWithTimeoutOf;
import static org.fest.util.Strings.concat;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.InvocationEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
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
import org.fest.swing.util.MouseEventTarget;
import org.fest.swing.util.TimeoutWatch;

/**
 * Understands simulation of user events on a GUI <code>{@link Component}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class RobotFixture implements Robot {

  private static final int KEY_INPUT_DELAY = 200;
  private static final int MULTI_CLICK_INTERVAL = 250; // a guess
  private static final int POPUP_DELAY = 10000;
  private static final int POPUP_TIMEOUT = 5000;
  private static final int WINDOW_DELAY = 20000;

  private static final ComponentMatcher POPUP_MATCHER = new TypeMatcher(JPopupMenu.class, true);

  private static final Runnable EMPTY_RUNNABLE = new Runnable() {
    public void run() {}
  };

  private static final int BUTTON_MASK = BUTTON1_MASK | BUTTON2_MASK | BUTTON3_MASK;

  private java.awt.Robot robot;

  private static Toolkit toolkit = Toolkit.getDefaultToolkit();
  private static WindowMonitor windowMonitor = WindowMonitor.instance();
  private static InputState inputState = new InputState(toolkit);

  /** Provides access to all the components in the hierarchy. */
  private final ComponentHierarchy hierarchy;

  /** Looks up <code>{@link java.awt.Component}</code>s. */
  private final ComponentFinder finder;

  private final Settings settings;

  private AWTEvent lastEventPosted;
  private MouseEvent lastMousePress;
  private boolean countingClicks;

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
  private RobotFixture(ComponentHierarchy hierarchy) {
    ScreenLock.instance().acquire(this);
    this.hierarchy = hierarchy;
    settings = new Settings();
    finder = new BasicComponentFinder(this.hierarchy);
    createRobot();
  }

  private void createRobot() {
    try {
      robot = new java.awt.Robot();
      settings.attachTo(robot);
      if (IS_WINDOWS || IS_OS_X) pause(500);
    } catch (AWTException e) {
      settings.eventMode(AWT);
    }
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

  private void packAndEnsureSafePosition(Window w) {
    w.pack();
    w.setLocation(100, 100);
  }

  private void waitForWindow(Window w) {
    long start = currentTimeMillis();
    while ((isRobotMode() && !windowMonitor.isWindowReady(w)) || !w.isShowing()) {
      long elapsed = currentTimeMillis() - start;
      if (elapsed > WINDOW_DELAY)
        throw new WaitTimedOutError(concat("Timed out waiting for Window to open (", String.valueOf(elapsed), "ms)"));
      pause();
    }
  }

  /** ${@inheritDoc} */
  public void close(Window w) {
    focus(w);
    if (!w.isShowing()) return;
    // Move to a corner and "pretend" to use the window manager control
    try {
      Point p = closeLocation(w);
      moveMouse(w, p.x, p.y);
    } catch (Exception ignored) {}
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
    if (IS_OS_X) return closeLocationForOSX(c);
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
    disposeWindows();
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
    int mask = button.mask;
    int modifierMask = mask & ~BUTTON_MASK;
    mask &= BUTTON_MASK;
    pressModifiers(modifierMask);
    // From Abbot: Adjust the auto-delay to ensure we actually get a multiple click
    // In general clicks have to be less than 200ms apart, although the actual setting is not readable by Java.
    int delayBetweenEvents = settings.delayBetweenEvents();
    if (times > 1 && delayBetweenEvents * 2 > 200) settings.delayBetweenEvents(0);
    mousePress(c, where, mask);
    for (int i = times; i > 1; i--) {
      robot.mouseRelease(mask);
      robot.mousePress(mask);
    }
    settings.delayBetweenEvents(delayBetweenEvents);
    robot.mouseRelease(mask);
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
    robot.mousePress(button.mask);
  }

  /** ${@inheritDoc} */
  public void pressMouse(Component c, Point where) {
    pressMouse(c, where, LEFT_BUTTON);
  }

  /** ${@inheritDoc} */
  public void pressMouse(Component c, Point where, MouseButton button) {
    mousePress(c, where, button.mask);
  }

  private void mousePress(Component c, Point where, int buttons) {
    jitter(c, where);
    moveMouse(c, where.x, where.y);
    if (isRobotMode()) {
      mousePress(buttons);
      return;
    }
    postMousePress(c, where, buttons);
  }

  private void mousePress(int buttons) {
    if (isRobotMode()) {
      robot.mousePress(buttons);
      return;
    }
    Component c = inputState.mouseComponent();
    if (c == null) return;
    Point where = inputState.mouseLocation();
    postMousePress(c, where, buttons);
  }

  private void postMousePress(Component c, Point where, int buttons) {
    long current = System.currentTimeMillis();
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
    Point center = centerOf(c);
    moveMouse(c, center.x, center.y);
  }

  /** ${@inheritDoc} */
  public void moveMouse(Component c, int x, int y) {
    if (!waitForComponentToBeReady(c, settings.timeoutToBeVisible()))
      throw actionFailure(concat("Could not obtain position of component ", format(c)));
    if (isRobotMode()) {
      try {
        Point point = locationOnScreenOf(c);
        if (point == null) return;
        point.translate(x, y);
        mouseMove(point.x, point.y);
      } catch (IllegalComponentStateException e) {}
      return;
    }
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

  // Wait the given number of milliseconds for the component to be showing and ready.
  private boolean waitForComponentToBeReady(Component c, long timeout) {
    if (isReadyForInput(c)) return true;
    TimeoutWatch watch = startWatchWithTimeoutOf(timeout);
    while (!isReadyForInput(c)) {
      if (c instanceof JPopupMenu) {
        // wiggle the mouse over the parent menu item to ensure the sub-menu shows
        Component invoker = ((JPopupMenu)c).getInvoker();
        if (invoker instanceof JMenu)
          jitter(invoker, new Point(invoker.getWidth() / 2, invoker.getHeight() / 2));
      }
      if (watch.isTimeOut()) return false;
      pause();
    }
    return true;
  }

  // Move the mouse to the given location, in screen coordinates.
  private void mouseMove(int x, int y) {
    if (isRobotMode()) robot.mouseMove(x, y);
  }

  private boolean isPointOutsideComponent(Point p, Component c) {
    int x = p.x;
    int y = p.y;
    return x < 0 || y < 0 || x >= c.getWidth() || y >= c.getHeight();
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
    int modifiers = inputState.modifiers();
    int x = position.x;
    int y = position.y;
    int clickCount = inputState.clickCount();
    postEvent(target, new MouseEvent(target, eventId, currentTimeMillis(), modifiers, x, y, clickCount, false));
  }

  /** ${@inheritDoc} */
  public void enterText(String text) {
    for (char character : text.toCharArray())
      type(character);
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
      postEvent(focus, keyEvent);
      return;
    }
    keyPressAndRelease(keyStroke.getKeyCode(), keyStroke.getModifiers());
  }

  private KeyEvent keyEventFor(Component c, char character) {
    return new KeyEvent(c, KEY_TYPED, System.currentTimeMillis(), 0, VK_UNDEFINED, character);
  }

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

  /* Usually only needed when dealing with Applets. */
  private EventQueue eventQueueFor(Component c) {
    return c != null ? windowMonitor.eventQueueFor(c) : toolkit.getSystemEventQueue();
  }

  /** ${@inheritDoc} */
  public void pressAndReleaseKey(int keyCode, int modifiers) {
    keyPressAndRelease(keyCode, modifiers);
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
      keyRelease(keyCode);
    }
    releaseModifiers(updatedModifiers);
  }

  /** ${@inheritDoc} */
  public void pressKey(int keyCode) {
    keyPress(keyCode);
    waitForIdle();
  }

  private void keyPress(int keyCode) {
    keyPress(keyCode, CHAR_UNDEFINED);
  }

  private void keyPress(int keyCode, char keyChar) {
    if (isRobotMode()) {
      try {
        robot.keyPress(keyCode);
        return;
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(concat("Invalid key code '", valueOf(keyCode), "'"));
      }
    }
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
    keyRelease(keyCode);
    waitForIdle();
  }

  private void keyRelease(int keyCode) {
    if (isRobotMode()) {
      robot.keyRelease(keyCode);
      if (!IS_OS_X) return;
      int delayBetweenEvents = settings.delayBetweenEvents();
      if (KEY_INPUT_DELAY > delayBetweenEvents) pause(KEY_INPUT_DELAY - delayBetweenEvents);
      return;
    }
    int mods = inputState.modifiers();
    if (isModifier(keyCode)) mods &= ~maskFor(keyCode);
    postKeyEvent(KEY_RELEASED, mods, keyCode, CHAR_UNDEFINED);
  }

  private void postKeyEvent(int eventId, int modifiers, int keyCode, char character) {
    Component c = focusOwner();
    if (c == null) return;
    postEvent(c, new KeyEvent(c, eventId, currentTimeMillis(), modifiers, keyCode, character));
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
    if (isAWTMode()) {
      robot.mouseRelease(buttons);
      return;
    }
    Component c = null;
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
    postEvent(c, new MouseEvent(target, MOUSE_RELEASED, when, modifiers, where.x, where.y, count, popupTrigger));
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

  private void waitForIdle(EventQueue eq) {
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
      if (postInvocationEvent(eq, toolkit, idleTimeout)) break;
      // Timed out waiting for idle event queue
      if (currentTimeMillis() - start > idleTimeout) break;
      ++count;
      // Force a yield
      pause();
      // Abbot: this does not detect invocation events (i.e. what gets posted with EventQueue.invokeLater), so if
      // someone
      // is repeatedly posting one, we might get stuck. Not too worried, since if a Runnable keeps calling invokeLater
      // on itself, *nothing* else gets much chance to run, so it seems to be a bad programming practice.
    } while (eq.peekEvent() != null);
  }

  // Indicates whether we timed out waiting for the invocation to run
  protected boolean postInvocationEvent(EventQueue eq, Toolkit toolkit, long timeout) {
    class RobotIdleLock {}
    Object lock = new RobotIdleLock();
    synchronized (lock) {
      eq.postEvent(new InvocationEvent(toolkit, EMPTY_RUNNABLE, lock, true));
      long start = currentTimeMillis();
      try {
        // NOTE: on fast linux systems when showing a dialog, if we don't provide a timeout, we're never notified, and
        // the test will wait forever (up through 1.5.0_05).
        lock.wait(timeout);
        return (currentTimeMillis() - start) >= settings.idleTimeout();
      } catch (InterruptedException e) {}
    }
    return false;
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
    if (isAWTMode()) return c.isShowing();
    Window w = ancestorOf(c);
    if (w == null) throw actionFailure(concat("Component ", format(c), " does not have a Window ancestor"));
    return c.isShowing() && windowMonitor.isWindowReady(w);
  }

  private boolean isAWTMode() {
    return AWT.equals(settings.eventMode());
  }

  private boolean isRobotMode() {
    return ROBOT.equals(settings.eventMode());
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
    } catch (ComponentLookupException expected) {}
  }

  /** ${@inheritDoc} */
  public Settings settings() {
    return settings;
  }
}
