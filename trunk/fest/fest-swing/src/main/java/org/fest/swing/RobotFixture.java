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
package org.fest.swing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Window;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import abbot.finder.AWTHierarchy;
import abbot.finder.Hierarchy;
import abbot.finder.TestHierarchy;
import abbot.tester.ComponentLocation;
import abbot.tester.ComponentMissingException;
import abbot.tester.Robot;
import abbot.tester.WindowTracker;
import abbot.util.Bugs;
import static java.lang.System.currentTimeMillis;
import static org.fest.util.Strings.concat;

/**
 * Understands simulation of user events on a GUI <code>{@link Component}</code>.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class RobotFixture {

  private static final int WINDOW_DELAY = 20000;

  private Robot robot;
  private WindowTracker windowTracker;

  /** Provides access to all the components in the hierarchy. */
  private final Hierarchy hierarchy;

  /** Looks up <code>{@link java.awt.Component}</code>s. */
  private final ComponentFinder finder;

  /**
   * Creates a new <code>{@link RobotFixture}</code> with a new AWT hierarchy. <code>{@link Component}</code>s created
   * before the created <code>{@link RobotFixture}</code> cannot be accessed by such <code>{@link RobotFixture}</code>.
   * @return the created robot fixture.
   */
  public static RobotFixture robotWithNewAwtHierarchy() {
    return new RobotFixture(new TestHierarchy());
  }

  /**
   * Creates a new <code>{@link RobotFixture}</code> that has access to all the GUI components in the AWT hierarchy.
   * @return the created robot fixture.
   */
  public static RobotFixture robotWithCurrentAwtHierarchy() {
    return new RobotFixture(new AWTHierarchy());
  }

  /**
   * Creates a new </code>{@link RobotFixture}</code>.
   * @param hierarchy the AWT component hierarchy to use.
   */
  private RobotFixture(Hierarchy hierarchy) {
    ScreenLock.instance().acquire(this);
    this.hierarchy = hierarchy;
    finder = new ComponentFinder(this.hierarchy);
    windowTracker = WindowTracker.getTracker();
    robot = newRobot();
  }

  private Robot newRobot() {
    Robot robot = new Robot();
    robot.reset();
    if (Bugs.hasMultiClickFrameBug()) robot.delay(500);
    return robot;
  }

  /**
   * Returns the <code>{@link ComponentFinder}</code> used by this fixture.
   * @return the object responsible for GUI component lookup. 
   */
  public ComponentFinder finder() {
    return finder;
  }

  /**
   * Safely display a window with proper EDT synchronization. This method blocks until the <code>{@link Window}</code>
   * is showing and ready for input.
   * @param w the window to display.
   */
  public void showWindow(Window w) {
    showWindow(w, null, true);
  }

  /**
   * Safely display a window with proper EDT synchronization. This method blocks until the <code>{@link Window}</code>
   * is showing and ready for input.
   * @param w the window to display.
   * @param size the size of the window to display.
   */
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

  private void waitForWindow(Window w) {
    long start = currentTimeMillis();
    while ((Robot.getEventMode() == Robot.EM_ROBOT && !windowTracker.isWindowReady(w)) || w.isShowing() != true) {
      long elapsed = currentTimeMillis() - start;
      if (elapsed > WINDOW_DELAY)
        throw new RuntimeException(concat("Timed out waiting for Window to open (", String.valueOf(elapsed), "ms)"));
      robot.sleep();
    }
  }

  private void packAndEnsureSafePosition(Window w) {
    w.pack();
    w.setLocation(100, 100);
  }

  /**
   * Gives input focus to the given <code>{@link Component}</code>.
   * @param c the component to give focus to.
   */
  public void focus(Component c) {
    robot.focus(c);
  }

  /**
   * Waits until the given condition is <code>true</code>.
   * @param condition the condition to verify.
   */
  public void wait(Condition condition) {
    robot.wait(condition);
  }

  /**
   * Posts a <code>{@link Runnable}</code> on the given component's event queue. Useful to ensure an operation happens
   * on the event dispatch thread.
   * @param c the component which event queue will be used.
   * @param action the <code>Runnable</code> to post in the event queue.
   */
  public void invokeLater(Component c, Runnable action) {
    robot.invokeLater(c, action);
  }
  
  /**
   * Post a runnable on the given component's event queue and wait for it to finish.
   * @param c the component which event queue will be used.
   * @param action the <code>Runnable</code> to post in the event queue.
   */
  public void invokeAndWait(Component c, Runnable action) {
    robot.invokeAndWait(c, action);
  }

  /** 
   * Cleans up any used resources (keyboard, mouse, open windows and <code>{@link ScreenLock}</code>) used by this 
   * robot.
   */
  public void cleanUp() {
    disposeWindows();
    mouseRelease();
    robot = null;
    windowTracker = null;
    ScreenLock.instance().release(this);
  }

  private void disposeWindows() {
    for (Window w : roots()) {
      hierarchy.dispose(w);
      w.setVisible(false);
      w.dispose();
    }
  }

  @SuppressWarnings("unchecked") private Collection<Window> roots() {
    return hierarchy.getRoots();
  }

  private void mouseRelease() {
    if (robot == null) return;
    int buttons = Robot.getState().getButtons();
    if (buttons != 0) robot.mouseRelease(buttons);
  }

  /**
   * Selects the given <code>{@link JMenuItem}</code>.
   * @param target the menu item to select.
   */  
  public void selectMenuItem(JMenuItem target) {
    robot.selectMenuItem(target);
  }

  /**
   * Simulates a user clicking the given mouse button, the given times on the given <code>{@link Component}</code>.
   * @param target the <code>Component</code> to click on.
   * @param button the mouse button to click.
   * @param times the number of times to click the given mouse button.
   */
  public void click(Component target, MouseButton button, int times) {
    Point where = new ComponentLocation().getPoint(target);
    robot.click(target, where.x, where.y, button.mask, times);
    robot.waitForIdle();
  }
  
  /**
   * Shows a popup menu.
   * @param invoker the component to invoke the popup menu from.
   * @return the displayed popup menu.
   * @throws ComponentLookupException if a popup menu cannot be found.
   */
  public JPopupMenu showPopupMenu(Component invoker) {
    return showPopupMenu(invoker, new ComponentLocation().getPoint(invoker));
  }
  
  /**
   * Shows a popup menu at the given coordinates.
   * @param invoker the component to invoke the popup menu from.
   * @param location the given coordinates for the popup menu.
   * @return the displayed popup menu.
   * @throws ComponentLookupException if a popup menu cannot be found.
   */
  public JPopupMenu showPopupMenu(Component invoker, Point location) {
    try {
      return (JPopupMenu) robot.showPopupMenu(invoker, location.x, location.y);
    } catch (ComponentMissingException e) {
      throw new ComponentLookupException(e);
    }
  }

  /**
   * Sleeps for the specified time. 
   * @param timeout the quantity of time units to sleep.
   * @param unit the time units.
   * @see #delay(long)
   */
  public void delay(long timeout, TimeUnit unit) {
    if (unit == null) throw new IllegalArgumentException("Time unit cannot be null");
    delay(unit.toMillis(timeout));
  }
  
  /**
   * Sleeps for the specified time. 
   * <p>
   * To catch any <code>InterruptedException</code>s that occur,
   * <code>{@link Thread#sleep(long)}()</code> may be used instead.
   * </p>
   * @param ms the time to sleep in milliseconds.
   */
  public void delay(long ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  /**
   * Simulates a user pressing and releasing the given keys. This method does not affect the current focus.
   * @param keyCodes one or more codes of the keys to press.
   * @see java.awt.event.KeyEvent
   */
  public void pressAndReleaseKeys(int... keyCodes) {
    for (int keyCode : keyCodes) {
      robot.key(keyCode);
      robot.waitForIdle();
    }
  }
  
  /**
   * Simulates a user pressing given key. This method does not affect the current focus.
   * @param keyCode the code of the key to press.
   * @see java.awt.event.KeyEvent
   */
 public void pressKey(int keyCode) {
    robot.keyPress(keyCode);
    robot.waitForIdle();
  }
  
  /**
   * Simulates a user releasing the given key. This method does not affect the current focus.
   * @param keyCode the code of the key to release.
   * @see java.awt.event.KeyEvent
   */
  public void releaseKey(int keyCode) {
    robot.keyRelease(keyCode);
    robot.waitForIdle();
  }
}
