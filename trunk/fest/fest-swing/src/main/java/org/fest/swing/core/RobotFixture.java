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

import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import abbot.finder.AWTHierarchy;
import abbot.finder.Hierarchy;
import abbot.finder.TestHierarchy;
import abbot.tester.WindowTracker;
import abbot.util.Bugs;

import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.util.TimeoutWatch;

import static abbot.tester.Robot.*;
import static java.lang.System.currentTimeMillis;
import static javax.swing.SwingUtilities.*;

import static org.fest.assertions.Fail.fail;
import static org.fest.reflect.core.Reflection.method;
import static org.fest.swing.core.MouseButton.*;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.util.AWT.centerOf;
import static org.fest.swing.util.TimeoutWatch.startWatchWithTimeoutOf;
import static org.fest.util.Strings.concat;

/**
 * Understands simulation of user events on a GUI <code>{@link Component}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class RobotFixture implements Robot {

  private static final int WINDOW_DELAY = 20000;

  private static final int POPUP_TIMEOUT = 5000;
  private static final int POPUP_DELAY = 10000;

  private static final ComponentMatcher POPUP_MATCHER = new TypeMatcher(JPopupMenu.class, true);
  

  private abbot.tester.Robot robot;
  private WindowTracker windowTracker;

  /** Provides access to all the components in the hierarchy. */
  private final Hierarchy hierarchy;

  /** Looks up <code>{@link java.awt.Component}</code>s. */
  private final ComponentFinder finder;
  
  private final Settings settings;

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
    settings = new Settings();
    finder = new BasicComponentFinder(this.hierarchy);
    windowTracker = WindowTracker.getTracker();
    robot = newRobot();
  }

  private abbot.tester.Robot newRobot() {
    abbot.tester.Robot robot = new abbot.tester.Robot();
    robot.reset();
    if (Bugs.hasMultiClickFrameBug()) robot.delay(500);
    return robot;
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

  private void waitForWindow(Window w) {
    long start = currentTimeMillis();
    while ((getEventMode() == EM_ROBOT && !windowTracker.isWindowReady(w)) || !w.isShowing()) {
      long elapsed = currentTimeMillis() - start;
      if (elapsed > WINDOW_DELAY)
        throw new WaitTimedOutError(concat("Timed out waiting for Window to open (", String.valueOf(elapsed), "ms)"));
      robot.sleep();
    }
  }

  /** ${@inheritDoc} */
  public void close(Window w) {
    robot.focus(w);
    robot.close(w);
  }

  private void packAndEnsureSafePosition(Window w) {
    w.pack();
    w.setLocation(100, 100);
  }

  /** ${@inheritDoc} */
  public void invokeLater(Component c, Runnable action) {
    robot.invokeLater(c, action);
  }

  /** ${@inheritDoc} */
  public void invokeAndWait(Runnable action) {
    invokeAndWait(null, action);
  }

/** ${@inheritDoc} */
  public void invokeAndWait(Component c, Runnable action) {
    robot.invokeAndWait(c, action);
  }

  /** ${@inheritDoc} */
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
    robot.click(target, where.x, where.y, button.mask, times);
    waitForIdle();
  }

  /** ${@inheritDoc} */
  public void mousePress(MouseButton button) {
    robot.mousePress(button.mask);
  }

  /** ${@inheritDoc} */
  public void mousePress(Component target, Point where) {
    mousePress(target, where, LEFT_BUTTON);
  }

  /** ${@inheritDoc} */
  public void mousePress(Component target, Point where, MouseButton button) {
    robot.mousePress(target, where.x, where.y, button.mask);
  }
  
  /** ${@inheritDoc} */
  public void jitter(Component c) {
    Point center = centerOf(c);
    int x = center.x;
    int y = center.y;
    mouseMove(c, (x > 0 ? x - 1 : x + 1), y);
  }

  /** ${@inheritDoc} */
  public void mouseMove(Component target) {
    Point center = centerOf(target);
    mouseMove(target, center.x, center.y);
  }

  /** ${@inheritDoc} */
  public void mouseMove(Component target, int x, int y) {
    robot.mouseMove(target, x, y);
  }

  /** ${@inheritDoc} */
  public void enterText(String text) {
    robot.keyString(text);
  }

  /** ${@inheritDoc} */
  public void type(char character) {
    robot.keyStroke(character);
  }

  /** ${@inheritDoc} */
  public void pressAndReleaseKey(int keyCode, int modifiers) {
    robot.key(keyCode, modifiers);
  }

  /** ${@inheritDoc} */
  public void pressAndReleaseKeys(int... keyCodes) {
    for (int keyCode : keyCodes) {
      robot.key(keyCode);
      waitForIdle();
    }
  }

  /** ${@inheritDoc} */
  public void pressKey(int keyCode) {
    robot.keyPress(keyCode);
    waitForIdle();
  }

  /** ${@inheritDoc} */
  public void releaseKey(int keyCode) {
    robot.keyRelease(keyCode);
    waitForIdle();
  }

  /** ${@inheritDoc} */
  public void releaseLeftMouseButton() {
    robot.mouseRelease();
  }

  /** ${@inheritDoc} */
  public void releaseMouseButtons() {
    int buttons = getState().getButtons();
    if (buttons == 0) return;
    robot.mouseRelease(buttons);
  }

  /** ${@inheritDoc} */
  public void waitForIdle() {
    robot.waitForIdle();
  }

  /** ${@inheritDoc} */
  public boolean isDragging() {
    return getState().isDragging();
  }

  /** ${@inheritDoc} */
  public boolean isReadyForInput(Component c) {
     return method("isReadyForInput").withReturnType(Boolean.class).withParameterTypes(Component.class).in(robot)
                                     .invoke(c);
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
