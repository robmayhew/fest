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
package com.jtuzi.fest;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Window;
import java.util.Collection;

import javax.swing.JMenuItem;

import abbot.finder.BasicFinder;
import abbot.finder.ComponentFinder;
import abbot.finder.Hierarchy;
import abbot.finder.Matcher;
import abbot.finder.TestHierarchy;
import abbot.finder.matchers.ClassMatcher;
import abbot.finder.matchers.NameMatcher;
import abbot.tester.Robot;
import abbot.tester.WindowTracker;
import abbot.util.Bugs;
import static java.lang.System.currentTimeMillis;

/**
 * Understands simulation of user events on a GUI <code>{@link Component}</code> and check the output. Useful for 
 * creation of programmatic tests with Abbot and TestNG (or JUnit 4).
 * 
 * @author Alex Ruiz
 */
public final class RobotFixture {

  private static final int WINDOW_DELAY = 20000;

  private Robot robot;
  
  private WindowTracker windowTracker;

  /** Provides access to all the components in the hierarchy. */
  private final Hierarchy hierarchy;

  /** Looks up <code>{@link java.awt.Component}</code>s. */
  private final ComponentFinder finder;

  public RobotFixture() {
    hierarchy = new TestHierarchy();
    finder = new BasicFinder(hierarchy);
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
   * @param pack flag that indicates if the window should be packed or not.
   */
  public void showWindow(final Window w, final Dimension size, final boolean pack) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        if (pack) packAndEnsureSafePosition(w);
        if (size != null) w.setSize(size);
        w.setVisible(true);
      }
    });
    waitForWindow(w, true);
  }

  private void waitForWindow(Window w, boolean visible) {
    long start = currentTimeMillis();
    while (windowTracker.isWindowReady(w) != visible)
      checkWindowVisibility(w, visible, start);
  }

  private void checkWindowVisibility(Window w, boolean visible, long startTime) {
    long elapsed = currentTimeMillis() - startTime;
    if (elapsed > WINDOW_DELAY)
      throw new RuntimeException("Timed out waiting for Window to " + (visible ? "open" : "close")
          + " (" + elapsed + "ms)");
    robot.sleep();
  }
  
  private void packAndEnsureSafePosition(Window w) {
    w.pack();
    w.setLocation(100, 100);
  }

  public <T extends Component> T findByType(Class<T> type) {
    return type.cast(find(new ClassMatcher(type)));
  }

  public <T extends Component> T findByType(Container root, Class<T> type) {
    return type.cast(find(root, new ClassMatcher(type)));
  }

  public <T extends Component> T findByName(String name, Class<T> type) {
    return type.cast(findByName(name));
  }
  
  public Component findByName(String name) {
    return find(new NameMatcher(name));
  }

  public Component find(Matcher m) {
    try {
      return finder.find(m);
    } catch (Exception e) {
      throw new ComponentLookupException(e);
    }
  }
  
  public <T extends Component> T findByName(Container root, String name, Class<T> type) {
    return type.cast(findByName(root, name));
  }
  
  public Component findByName(Container root, String name) {
    return find(root, new NameMatcher(name));
  }

  public Component find(Container root, Matcher m) {
    try {
      return finder.find(root, m);
    } catch (Exception e) {
      throw new ComponentLookupException(e);
    }
  }

  public void focus(Component c) {
    robot.focus(c);
  }
  
  public void wait(Condition condition) {
    robot.wait(condition);
  }
  
  public void invokeLater(Component context, Runnable action) {
    robot.invokeLater(context, action);
  }
  
  public void cleanUp() {
    disposeWindows();
    mouseRelease();
    robot = null;
    windowTracker = null;
  }

  private void disposeWindows() {
    for (Window w : roots()) hierarchy.dispose(w);
  }
  
  @SuppressWarnings("unchecked") 
  private Collection<Window> roots() {
    return hierarchy.getRoots();
  }
  
  private void mouseRelease() {
    if (robot == null) return;
    int buttons = Robot.getState().getButtons();
    if (buttons != 0) robot.mouseRelease(buttons);
  }

  public void selectMenuItem(JMenuItem target) {
    robot.selectMenuItem(target);
  }
}
