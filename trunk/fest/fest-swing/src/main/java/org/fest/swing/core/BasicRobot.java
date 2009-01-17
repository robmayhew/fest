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
 * Copyright @2006-2009 the original author or authors.
 */
package org.fest.swing.core;

import java.awt.Component;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.hierarchy.ComponentHierarchy;
import org.fest.swing.hierarchy.ExistingHierarchy;
import org.fest.swing.util.Pair;
import org.fest.swing.util.TimeoutWatch;

import static java.awt.event.InputEvent.*;
import static java.awt.event.KeyEvent.*;
import static java.lang.System.currentTimeMillis;
import static javax.swing.SwingUtilities.*;

import static org.fest.assertions.Fail.fail;
import static org.fest.swing.awt.AWT.*;
import static org.fest.swing.core.ActivateWindowTask.activateWindow;
import static org.fest.swing.core.ComponentRequestFocusTask.giveFocusTo;
import static org.fest.swing.core.FocusOwnerFinder.*;
import static org.fest.swing.core.MouseButton.*;
import static org.fest.swing.core.WindowAncestorFinder.windowAncestorOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.format.Formatting.*;
import static org.fest.swing.hierarchy.NewHierarchy.ignoreExistingComponents;
import static org.fest.swing.keystroke.KeyStrokeMap.keyStrokeFor;
import static org.fest.swing.timing.Pause.pause;
import static org.fest.swing.util.TimeoutWatch.startWatchWithTimeoutOf;
import static org.fest.util.Strings.concat;

/**
 * Understands simulation of user events on a GUI <code>{@link Component}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class BasicRobot extends RobotTemplate implements Robot {

  private static final int POPUP_DELAY = 10000;
  private static final int POPUP_TIMEOUT = 5000;

  private static final ComponentMatcher POPUP_MATCHER = new TypeMatcher(JPopupMenu.class, true);

  private static final int BUTTON_MASK = BUTTON1_MASK | BUTTON2_MASK | BUTTON3_MASK;

  private final AWTEventPoster eventPoster;

  /**
   * Creates a new <code>{@link Robot}</code> with a new AWT hierarchy. The created <code>Robot</code> will not be able
   * to access any components that were created before it. 
   * @return the created <code>Robot</code>.
   */
  public static Robot robotWithNewAwtHierarchy() {
    return new BasicRobot(ignoreExistingComponents());
  }

  /**
   * Creates a new <code>{@link Robot}</code> that has access to all the GUI components in the AWT hierarchy.
   * @return the created <code>Robot</code>.
   */
  public static Robot robotWithCurrentAwtHierarchy() {
    return new BasicRobot(new ExistingHierarchy());
  }

  /**
   * Creates a new <code>{@link BasicRobot}</code>.
   * @param hierarchy the component hierarchy to use.
   */
  protected BasicRobot(ComponentHierarchy hierarchy) {
    super(hierarchy);
    eventPoster = new AWTEventPoster(toolkit, inputState, windowMonitor, settings());
  }

  /** {@inheritDoc} */
  public ComponentPrinter printer() {
    return finder().printer();
  }

  /** {@inheritDoc} */
  @RunsInEDT
  public void focusAndWaitForFocusGain(Component c) {
    focus(c, true);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  public void focus(Component c) {
    focus(c, false);
  }

  @RunsInEDT
  private void focus(Component target, boolean wait) {
    Component currentOwner = inEdtFocusOwner();
    if (currentOwner == target) return;
    FocusMonitor focusMonitor = FocusMonitor.attachTo(target);
    // for pointer focus
    moveMouse(target);
    // Make sure the correct window is in front
    activateWindowOfFocusTarget(target, currentOwner);
    giveFocusTo(target);
    try {
      if (wait) {
        TimeoutWatch watch = startWatchWithTimeoutOf(settings().timeoutToBeVisible());
        while (!focusMonitor.hasFocus()) {
          if (watch.isTimeOut()) throw actionFailure(concat("Focus change to ", format(target), " failed"));
          pause();
        }
      }
    } finally {
      target.removeFocusListener(focusMonitor);
    }
  }

  @RunsInEDT
  private void activateWindowOfFocusTarget(Component target, Component currentOwner) {
    Pair<Window, Window> windowAncestors = windowAncestorsOf(currentOwner, target);
    Window currentOwnerAncestor = windowAncestors.i;
    Window targetAncestor = windowAncestors.ii;
    if (currentOwnerAncestor == targetAncestor) return;
    activate(targetAncestor);
    waitForIdle();
  }

  @RunsInEDT
  private static Pair<Window, Window> windowAncestorsOf(final Component one, final Component two) {
    return execute(new GuiQuery<Pair<Window, Window>>() {
      protected Pair<Window, Window> executeInEDT() throws Throwable {
        return new Pair<Window, Window>(windowAncestor(one), windowAncestor(two));
      }

      private Window windowAncestor(Component c) {
        return (c != null) ? windowAncestorOf(c) : null;
      }
    });
  }

  /**
   * Activates the given <code>{@link Window}</code>. "Activate" means that the given window gets the keyboard focus.
   * @param w the window to activate.
   */
  @RunsInEDT
  private void activate(Window w) {
    activateWindow(w);
    moveMouse(w); // For pointer-focus systems
  }

  /** {@inheritDoc} */
  @RunsInEDT
  public void click(Component c) {
    click(c, LEFT_BUTTON);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  public void rightClick(Component c) {
    click(c, RIGHT_BUTTON);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  public void click(Component c, MouseButton button) {
    click(c, button, 1);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  public void doubleClick(Component c) {
    click(c, LEFT_BUTTON, 2);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  public void click(Component c, MouseButton button, int times) {
    click(c, visibleCenterOf(c), button, times);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  public void click(Component c, Point where) {
    click(c, where, LEFT_BUTTON, 1);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  public void click(Point where, MouseButton button, int times) {
    click(where, button.mask, times);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  public void click(Component c, Point where, MouseButton button, int times) {
    if (c != null) focus(c);
    int mask = button.mask;
    int modifierMask = mask & ~BUTTON_MASK;
    mask &= BUTTON_MASK;
    pressModifiers(modifierMask);
    // From Abbot: Adjust the auto-delay to ensure we actually get a multiple click
    // In general clicks have to be less than 200ms apart, although the actual setting is not readable by Java.
    int delayBetweenEvents = settings().delayBetweenEvents();
    if (shouldSetDelayBetweenEventsToZeroWhenClicking(times)) settings().delayBetweenEvents(0);
    eventGenerator().pressMouse(c, where, mask);
    for (int i = times; i > 1; i--) {
      eventGenerator().releaseMouse(mask);
      eventGenerator().pressMouse(c, where, mask);
    }
    settings().delayBetweenEvents(delayBetweenEvents);
    eventGenerator().releaseMouse(mask);
    releaseModifiers(modifierMask);
    waitForIdle();
  }

  /** {@inheritDoc} */
  @RunsInEDT
  public void moveMouse(Component c) {
    moveMouse(c, visibleCenterOf(c));
  }

  /** {@inheritDoc} */
  @RunsInEDT
  public void moveMouse(Component c, Point p) {
    moveMouse(c, p.x, p.y);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  public void moveMouse(Component c, int x, int y) {
    if (!waitForComponentToBeReady(c, settings().timeoutToBeVisible()))
      throw actionFailure(concat("Could not obtain position of component ", format(c)));
    eventGenerator().moveMouse(c, x, y);
    waitForIdle();
  }
  
  /** {@inheritDoc} */
  public void pressMouse(MouseButton button) {
    pressMouse(button.mask);
  }

  /** {@inheritDoc} */
  public void pressMouse(Component c, Point where) {
    pressMouse(c, where, LEFT_BUTTON);
  }

  /** {@inheritDoc} */
  public void pressMouse(Component c, Point where, MouseButton button) {
    jitter(c, where);
    moveMouse(c, where.x, where.y);
    eventGenerator().pressMouse(c, where, button.mask);
  }

  /** {@inheritDoc} */
  public void pressMouse(Point where, MouseButton button) {
    pressMouse(where, button.mask);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  public void releaseMouse(MouseButton button) {
    releaseMouse(button.mask);
  }

  /** {@inheritDoc} */
  public void rotateMouseWheel(Component c, int amount) {
    moveMouse(c);
    rotateMouseWheel(amount);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  public void jitter(Component c) {
    jitter(c, visibleCenterOf(c));
  }

  /** {@inheritDoc} */
  @RunsInEDT
  public void jitter(Component c, Point where) {
    int x = where.x;
    int y = where.y;
    moveMouse(c, (x > 0 ? x - 1 : x + 1), y);
  }

  // Wait the given number of milliseconds for the component to be showing and ready.
  @RunsInEDT
  private boolean waitForComponentToBeReady(Component c, long timeout) {
    if (isReadyForInput(c)) return true;
    TimeoutWatch watch = startWatchWithTimeoutOf(timeout);
    while (!isReadyForInput(c)) {
      if (c instanceof JPopupMenu) {
        // wiggle the mouse over the parent menu item to ensure the sub-menu shows
        Pair<Component, Point> invokerAndCenterOfInvoker = invokerAndCenterOfInvoker((JPopupMenu)c); 
        Component invoker = invokerAndCenterOfInvoker.i;
        if (invoker instanceof JMenu) jitter(invoker, invokerAndCenterOfInvoker.ii);
      }
      if (watch.isTimeOut()) return false;
      pause();
    }
    return true;
  }
  
  @RunsInEDT
  private static Pair<Component, Point> invokerAndCenterOfInvoker(final JPopupMenu popupMenu) {
    return execute(new GuiQuery<Pair<Component, Point>>() {
      protected Pair<Component, Point> executeInEDT() {
        Component invoker = popupMenu.getInvoker();
        return new Pair<Component, Point>(invoker, centerOf(invoker));
      }
    });
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override public void type(char character) {
    KeyStroke keyStroke = keyStrokeFor(character);
    if (keyStroke == null) {
      Component focus = focusOwner();
      if (focus == null) return;
      KeyEvent keyEvent = keyEventFor(focus, character);
      // Allow any pending robot events to complete; otherwise we might stuff the typed event before previous
      // robot-generated events are posted.
      waitForIdle();
      eventPoster.postEvent(focus, keyEvent);
      return;
    }
    keyPressAndRelease(keyStroke.getKeyCode(), keyStroke.getModifiers());
  }

  private KeyEvent keyEventFor(Component c, char character) {
    return new KeyEvent(c, KEY_TYPED, System.currentTimeMillis(), 0, VK_UNDEFINED, character);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  public JPopupMenu showPopupMenu(Component invoker) {
    return showPopupMenu(invoker, visibleCenterOf(invoker));
  }

  /** {@inheritDoc} */
  @RunsInEDT
  public JPopupMenu showPopupMenu(Component invoker, Point location) {
    click(invoker, location, RIGHT_BUTTON, 1);
    JPopupMenu popup = findActivePopupMenu();
    if (popup == null)
      throw new ComponentLookupException(concat("Unable to show popup at ", location, " on ", inEdtFormat(invoker)));
    long start = currentTimeMillis();
    while (!isWindowAncestorReadyForInput(popup) && currentTimeMillis() - start > POPUP_DELAY)
      pause();
    return popup;
  }

  @RunsInEDT
  private boolean isWindowAncestorReadyForInput(final JPopupMenu popup) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return isReadyForInput(getWindowAncestor(popup));
      }
    });
  }

  /**
   * Indicates whether the given <code>{@link Component}</code> is ready for input.
   * <p>
   * <b>Note:</b> This method is <b>not</b> executed in the event dispatch thread (EDT.) Clients are responsible for 
   * invoking this method in the EDT.
   * </p>
   * @param c the given <code>Component</code>.
   * @return <code>true</code> if the given <code>Component</code> is ready for input, <code>false</code> otherwise.
   * @throws ActionFailedException if the given <code>Component</code> does not have a <code>Window</code> ancestor.
   */
  @RunsInCurrentThread
  public boolean isReadyForInput(Component c) {
    Window w = windowAncestorOf(c);
    if (w == null) throw actionFailure(concat("Component ", format(c), " does not have a Window ancestor"));
    return c.isShowing() && windowMonitor.isWindowReady(w);
  }

  /** {@inheritDoc} */
  @RunsInEDT
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

  @RunsInEDT
  private JPopupMenu activePopupMenu() {
    List<Component> found = new ArrayList<Component>(finder().findAll(POPUP_MATCHER));
    if (found.size() == 1) return (JPopupMenu)found.get(0);
    return null;
  }

  /** {@inheritDoc} */
  @RunsInEDT
  public void requireNoJOptionPaneIsShowing() {
    List<Component> found = new ArrayList<Component>(finder().findAll(new TypeMatcher(JOptionPane.class, true)));
    if (found.isEmpty()) return;
    unexpectedJOptionPanesFound(found);
  }

  private void unexpectedJOptionPanesFound(List<Component> found) {
    StringBuilder message = new StringBuilder();
    message.append("Expecting no JOptionPane to be showing, but found:<[");
    int size = found.size();
    for (int i = 0; i < size; i++) {
      message.append(format(found.get(i)));
      if (i != size - 1) message.append(", ");
    }
    message.append("]>");
    fail(message.toString());
  }
}
