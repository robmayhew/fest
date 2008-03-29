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

import java.awt.*;
import java.awt.event.InvocationEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Collection;

import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import org.fest.swing.core.input.InputState;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.hierarchy.ComponentHierarchy;
import org.fest.swing.hierarchy.ExistingHierarchy;
import org.fest.swing.monitor.WindowMonitor;
import org.fest.swing.util.MouseEventTarget;
import org.fest.swing.util.TimeoutWatch;

import static abbot.tester.Robot.*;
import static java.awt.event.InputEvent.*;
import static java.awt.event.KeyEvent.*;
import static java.awt.event.MouseEvent.*;
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
import static org.fest.swing.keystroke.KeyStrokeMap.keyStrokeFor;
import static org.fest.swing.util.AWT.*;
import static org.fest.swing.util.Modifiers.*;
import static org.fest.swing.util.Platform.*;
import static org.fest.swing.util.TimeoutWatch.startWatchWithTimeoutOf;
import static org.fest.util.Strings.concat;

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
  
  private static final int BUTTON_MASK = BUTTON1_MASK | BUTTON2_MASK | BUTTON3_MASK;
  
  private abbot.tester.Robot abbotRobot;
  private java.awt.Robot robot;
 
  private static Toolkit toolkit = Toolkit.getDefaultToolkit();
  private static WindowMonitor windowMonitor = WindowMonitor.instance();
  private static InputState inputState = new InputState();

  /** Provides access to all the components in the hierarchy. */
  private final ComponentHierarchy hierarchy;

  /** Looks up <code>{@link java.awt.Component}</code>s. */
  private final ComponentFinder finder;
  
  private final Settings settings;

  private AWTEvent lastEventPosted;
  private MouseEvent lastMousePress;
  private boolean countingClicks;
  
  /**
   * Creates a new <code>{@link RobotFixture}</code> with a new AWT hierarchy. <code>{@link Component}</code>s created
   * before the created <code>{@link RobotFixture}</code> cannot be accessed by such <code>{@link RobotFixture}</code>.
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
    abbotRobot = newAbbotRobot();
    createRobot();
  }

  private abbot.tester.Robot newAbbotRobot() {
    abbot.tester.Robot robot = new abbot.tester.Robot();
    robot.reset();
    if (IS_WINDOWS || IS_OS_X) pause(500);
    return robot;
  }
  
  private void createRobot() {
    try {
      robot = new java.awt.Robot();
      settings.attachTo(robot);
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
    while ((getEventMode() == EM_ROBOT && !windowMonitor.isWindowReady(w)) || !w.isShowing()) {
      long elapsed = currentTimeMillis() - start;
      if (elapsed > WINDOW_DELAY)
        throw new WaitTimedOutError(concat("Timed out waiting for Window to open (", String.valueOf(elapsed), "ms)"));
      pause();
    }
  }

  /** ${@inheritDoc} */
  public void close(Window w) {
    focus(w);
    abbotRobot.close(w);
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
    abbotRobot = null;
    ScreenLock.instance().release(this);
  }

  private void disposeWindows() {
    for (Container c : roots()) {
      if (!(c instanceof Window)) continue;
      Window w = (Window)c;
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
  public void click(Component target, Point where) {
    click(target, where, LEFT_BUTTON, 1);
  }

  /** ${@inheritDoc} */
  public void click(Component target, Point where, MouseButton button, int times) {
    int mask = button.mask;
    int modifierMask = mask & ~BUTTON_MASK;
    mask &= BUTTON_MASK;
    pressModifiers(modifierMask);
    // From Abbot: Adjust the auto-delay to ensure we actually get a multiple click 
    // In general clicks have to be less than 200ms apart, although the actual setting is not readable by Java.
    int delayBetweenEvents = settings.delayBetweenEvents();
    if (times > 1 && delayBetweenEvents * 2 > 200) settings.delayBetweenEvents(0);
    pressMouse(target, where, mask);
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
    for (int modifierKey : keysFor(modifierMask)) pressKey(modifierKey);
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
  public void pressMouse(Component target, Point where) {
    pressMouse(target, where, LEFT_BUTTON);
  }

  /** ${@inheritDoc} */
  public void pressMouse(Component target, Point where, MouseButton button) {
    pressMouse(target, where, button.mask);
  }
  
  private void pressMouse(Component comp, Point where, int buttons) {
    jitter(comp, where);
    moveMouse(comp, where.x, where.y);
    if (isRobotMode()) {
      mousePress(buttons);
      return;
    }
    postMousePress(comp, where, buttons);
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
  public void moveMouse(Component target) {
    Point center = centerOf(target);
    moveMouse(target, center.x, center.y);
  }

  /** ${@inheritDoc} */
  public void moveMouse(Component target, int x, int y) {
    try {
      Point point = locationOnScreenOf(target);
      if (point == null) return;
      point.translate(x, y);
      robot.mouseMove(point.x, point.y);
    } catch (IllegalComponentStateException e) {}
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
  public void enterText(String text) {
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
    MouseEvent mouseEvent = (MouseEvent)event;
    updateMousePressWith(mouseEvent);
    // Generate a click if there are no events between press/release.
    if (isAWTMode() && event.getID() == MOUSE_RELEASED && previous.getID() == MOUSE_PRESSED) {
      long when = System.currentTimeMillis();
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
    robot.keyPress(keyCode);
  }
  
  /** ${@inheritDoc} */
  public void releaseKey(int keyCode) {
    keyRelease(keyCode);
    waitForIdle();
  }

  private void keyRelease(int keyCode) {
    robot.keyRelease(keyCode);
    if (!IS_OS_X) return;
    int delayBetweenEvents = settings.delayBetweenEvents();
    if (KEY_INPUT_DELAY > delayBetweenEvents) pause(KEY_INPUT_DELAY - delayBetweenEvents);
  }

  /** ${@inheritDoc} */
  public void releaseLeftMouseButton() {
    robot.mouseRelease(BUTTON1_MASK);
  }

  /** ${@inheritDoc} */
  public void releaseMouseButtons() {
    int buttons = inputState.buttons();
    if (buttons == 0) return;
    robot.mouseRelease(buttons);
  }

  /** ${@inheritDoc} */
  public void waitForIdle() {
    abbotRobot.waitForIdle();
  }

  /** ${@inheritDoc} */
  public boolean isDragging() {
    return inputState.isDragging();
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
    while (!isReadyForInput(getWindowAncestor(popup)) && currentTimeMillis() - start > POPUP_DELAY) pause();
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
      return (JPopupMenu)finder().find(POPUP_MATCHER);
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
