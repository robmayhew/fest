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
import java.util.Collection;

import abbot.finder.AWTHierarchy;
import abbot.finder.Hierarchy;
import abbot.finder.TestHierarchy;
import abbot.tester.Robot;
import abbot.tester.WindowTracker;
import abbot.util.Bugs;

import org.fest.swing.exception.WaitTimedOutError;

import static java.lang.System.currentTimeMillis;

import static org.fest.reflect.core.Reflection.method;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.util.Swing.centerOf;
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
   * Creates a new <code>{@link RobotFixture}</code>.
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
   * Returns the <code>{@link ComponentPrinter}</code> used by this fixture.
   * @return the <code>ComponentPrinter</code> used by this fixture.
   */
  public ComponentPrinter printer() {
    return finder().printer();
  }

  /**
   * Returns the <code>{@link ComponentFinder}</code> used by this fixture.
   * @return the object responsible for GUI component lookup and user input simulation.
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
    while ((Robot.getEventMode() == Robot.EM_ROBOT && !windowTracker.isWindowReady(w)) || !w.isShowing()) {
      long elapsed = currentTimeMillis() - start;
      if (elapsed > WINDOW_DELAY)
        throw new WaitTimedOutError(concat("Timed out waiting for Window to open (", String.valueOf(elapsed), "ms)"));
      robot.sleep();
    }
  }

  private void packAndEnsureSafePosition(Window w) {
    w.pack();
    w.setLocation(100, 100);
  }

  /**
   * Gives input focus to the given <code>{@link Component}</code>. Note that the component may not yet have focus when
   * this method returns.
   * @param c the component to give focus to.
   */
  public void focus(Component c) {
    robot.focus(c);
  }

  /**
   * Gives input focus to the given <code>{@link Component}</code> and waits until the <code>{@link Component}</code>
   * has focus.
   * @param c the component to give focus to.
   */
  public void focusAndWaitForFocusGain(Component c) {
    robot.focus(c, true);
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
   * Runs the given <code>{@link Runnable}</code> on the event dispatch thread, but don't return until it's been run.
   * @param action the <code>Runnable</code> to run.
   */
  public void invokeAndWait(Runnable action) {
    invokeAndWait(null, action);
  }

/**
   * Posts a <code>{@link Runnable}</code> on the given component's event queue and wait for it to finish.
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
    releaseMouseButtons();
  }

  /**
   * Simulates a user clicking once the given <code>{@link Component}</code> using the left mouse button.
   * @param target the <code>Component</code> to click on.
   */
  public void click(Component target) {
    click(target, LEFT_BUTTON, 1);
  }

  /**
   * Simulates a user clicking the given mouse button, the given times on the given <code>{@link Component}</code>.
   * @param target the <code>Component</code> to click on.
   * @param button the mouse button to click.
   * @param times the number of times to click the given mouse button.
   */
  public void click(Component target, MouseButton button, int times) {
    click(target, centerOf(target), button, times);
  }

  /**
   * Simulates a user clicking at the given position on the given <code>{@link Component}</code>.
   * @param target the <code>Component</code> to click on.
   * @param where the position where to click.
   */
  public void click(Component target, Point where) {
    click(target, where, LEFT_BUTTON, 1);
  }

  /**
   * Simulates a user clicking the given mouse button, the given times at the given position on the given
   * <code>{@link Component}</code>.
   * @param target the <code>Component</code> to click on.
   * @param where the position where to click.
   * @param button the mouse button to click.
   * @param times the number of times to click the given mouse button.
   */
  public void click(Component target, Point where, MouseButton button, int times) {
    robot.click(target, where.x, where.y, button.mask, times);
    waitForIdle();
  }

  /** 
   * Simulates a user pressing a mouse button. 
   * @param button the button to press.
   */
  public void mousePress(MouseButton button) {
    robot.mousePress(button.mask);
  }

  /**
   * Simulates a user pressing the left mouse button on the given <code>{@link Component}</code>.
   * @param target the <code>Component</code> to click on.
   * @param where the position where to press the left mouse button.
   */
  public void mousePress(Component target, Point where) {
    mousePress(target, where, LEFT_BUTTON);
  }

  /**
   * Simulates a user pressing the given mouse button on the given <code>{@link Component}</code>.
   * @param target the <code>Component</code> to click on.
   * @param where the position where to press the given mouse button.
   * @param button the mouse button to press.
   */
  public void mousePress(Component target, Point where, MouseButton button) {
    robot.mousePress(target, where.x, where.y, button.mask);
  }
  
  /**
   * Makes the mouse pointer show small quick jumpy movements on the given <code>{@link Component}</code>.
   * @param c the given <code>Component</code>.
   */
  public void jitter(Component c) {
    Point center = centerOf(c);
    int x = center.x;
    int y = center.y;
    mouseMove(c, (x > 0 ? x - 1 : x + 1), y);
  }

  /**
   * Simulates a user moving the mouse pointer to the center of the given <code>{@link Component}</code>.
   * @param target the given <code>Component</code>.
   */
  public void mouseMove(Component target) {
    Point center = centerOf(target);
    robot.mouseMove(target, center.x, center.y);
  }

  /**
   * Simulates a user moving the mouse pointer to the given coordinates relative to the given
   * <code>{@link Component}</code>.
   * @param target the given <code>Component</code>.
   * @param x horizontal coordinate relative to the given <code>Component</code>.
   * @param y vertical coordinate relative to the given <code>Component</code>.
   */
  public void mouseMove(Component target, int x, int y) {
    robot.mouseMove(target, x, y);
  }

  /**
   * Simulates a user entering the given text. Note that this method the key strokes to the component that has input
   * focus.
   * @param text the text to enter.
   */
  public void enterText(String text) {
    robot.keyString(text);
  }

  /**
   * Types the given character. Note that this method sends the key strokes to the component that has input focus.
   * @param character the character to type.
   */
  public void type(char character) {
    robot.keyStroke(character);
  }

  /**
   * Type the given keycode with the given modifiers. Modifiers is a mask from the available
   * <code>{@link java.awt.event.InputEvent}</code> masks.
   * @param keyCode the code of the key to press.
   * @param modifiers the given modifiers.
   */
  public void pressAndReleaseKey(int keyCode, int modifiers) {
    robot.key(keyCode, modifiers);
  }

  /**
   * Simulates a user pressing and releasing the given keys. This method does not affect the current focus.
   * @param keyCodes one or more codes of the keys to press.
   * @see java.awt.event.KeyEvent
   */
  public void pressAndReleaseKeys(int... keyCodes) {
    for (int keyCode : keyCodes) {
      robot.key(keyCode);
      waitForIdle();
    }
  }

  /**
   * Simulates a user pressing given key. This method does not affect the current focus.
   * @param keyCode the code of the key to press.
   * @see java.awt.event.KeyEvent
   */
  public void pressKey(int keyCode) {
    robot.keyPress(keyCode);
    waitForIdle();
  }

  /**
   * Simulates a user releasing the given key. This method does not affect the current focus.
   * @param keyCode the code of the key to release.
   * @see java.awt.event.KeyEvent
   */
  public void releaseKey(int keyCode) {
    robot.keyRelease(keyCode);
    waitForIdle();
  }

  /**
   * Releases the left mouse button.
   */
  public void releaseLeftMouseButton() {
    robot.mouseRelease();
  }

  /**
   * Releases any mouse button(s) used by the robot.
   */
  public void releaseMouseButtons() {
    int buttons = Robot.getState().getButtons();
    if (buttons == 0) return;
    robot.mouseRelease(buttons);
  }

  /**
   * Wait for an idle AWT event queue. Note that this is different from the implementation of
   * <code>java.awt.Robot.waitForIdle()</code>, which may have events on the queue when it returns. Do <strong>NOT</strong>
   * use this method if there are animations or other continual refreshes happening, since in that case it may never
   * return.
   */
  public void waitForIdle() {
    robot.waitForIdle();
  }

  /**
   * Indicates whether the robot is currently in a dragging operation.
   * @return <code>true</code> if the robot is currently in a dragging operation, <code>false</code> otherwise.
   */
  public boolean isDragging() {
    return Robot.getState().isDragging();
  }

  /** 
   * Indicates whether the given <code>{@link Component}</code> is ready for input. 
   * @param c the given <code>Component</code>.
   * @return <code>true</code> if the given <code>Component</code> is ready for input, <code>false</code> otherwise.
   */
  public boolean isReadyForInput(Component c) {
     return method("isReadyForInput").withReturnType(Boolean.class).withParameterTypes(Component.class).in(robot)
                                     .invoke(c);
  }

  /**
   * Simulates a user closing the given window.
   * @param w the window to close.
   */
  public void close(Window w) {
    focus(w);
    robot.close(w);
  }
}
