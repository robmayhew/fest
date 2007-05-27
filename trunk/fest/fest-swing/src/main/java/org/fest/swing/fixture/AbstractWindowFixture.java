/*
 * Created on Feb 16, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.Dimension;
import java.awt.Window;

import abbot.tester.WindowTester;
import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.RobotFixture.robotWithCurrentAwtHierarchy;

import org.fest.swing.RobotFixture;

/**
 * Understands simulation of user events and state verification of a <code>{@link Window}</code>.
 * @param <T> the type of window handled by this fixture. 
 *
 * @author Alex Ruiz
 */
public abstract class AbstractWindowFixture<T extends Window> extends AbstractContainerFixture<T> {

  /**
   * Creates a new </code>{@link AbstractWindowFixture}</code>. This constructor creates a new 
   * <code>{@link RobotFixture}</code> containing the current AWT hierarchy.
   * @param type the type of <code>Window</code> to find using the created <code>RobotFixture</code>.
   * @see org.fest.swing.ComponentFinder#findByType(Class)
   */
  public AbstractWindowFixture(Class<? extends T> type) {
    this(robotWithCurrentAwtHierarchy(), type);
  }

  /**
   * Creates a new </code>{@link AbstractWindowFixture}</code>.
   * @param robot performs simulation of user events on a <code>Window</code>.
   * @param type the type of <code>Window</code> to find using the given <code>RobotFixture</code>.
   * @see org.fest.swing.ComponentFinder#findByType(Class)
   */
  public AbstractWindowFixture(RobotFixture robot, Class<? extends T> type) {
    super(robot, type);
  }

  /**
   * Creates a new </code>{@link AbstractWindowFixture}</code>. This constructor creates a new 
   * <code>{@link RobotFixture}</code> containing the current AWT hierarchy.
   * @param name the name of the <code>Window</code> to find using the given <code>RobotFixture</code>.
   * @param type the type of <code>Window</code> to find using the created <code>RobotFixture</code>.
   * @see org.fest.swing.ComponentFinder#findByName(String, Class)
   */
  public AbstractWindowFixture(String name, Class<? extends T> type) {
    this(robotWithCurrentAwtHierarchy(), name, type);
  }
  
  /**
   * Creates a new </code>{@link AbstractWindowFixture}</code>.
   * @param robot performs simulation of user events on a <code>Window</code>.
   * @param name the name of the <code>Window</code> to find using the given <code>RobotFixture</code>.
   * @param type the type of <code>Window</code> to find using the given <code>RobotFixture</code>.
   * @see org.fest.swing.ComponentFinder#findByName(String, Class)
   */
  public AbstractWindowFixture(RobotFixture robot, String name, Class<? extends T> type) {
    super(robot, name, type);
  }

  /**
   * Creates a new </code>{@link AbstractWindowFixture}</code>. This constructor creates a new <code>{@link RobotFixture}</code>
   * containing the current AWT hierarchy.
   * @param target the window under test.
   */
  public AbstractWindowFixture(T target) {
    this(robotWithCurrentAwtHierarchy(), target);
  }
  
  /**
   * Creates a new </code>{@link AbstractWindowFixture}</code>.
   * @param robot performs simulation of user events on the given window.
   * @param target the window under test.
   */
  public AbstractWindowFixture(RobotFixture robot, T target) {
    super(robot, target);
  }

  /**
   * Shows the target window.
   * @return this fixture.
   */
  public AbstractWindowFixture<T> show() {
    robot.showWindow(target);
    return requireVisible();
  }
  
  /**
   * Shows the target window, resized to the given size.
   * @param size the given size.
   * @return this fixture.
   */
  public AbstractWindowFixture<T> show(Dimension size) {
    robot.showWindow(target, size);
    return requireVisible();
  }
  
  /**
   * Simulates a user resizing the target window horizontally.
   * @param width the width that the target window should have after being resized.
   * @return this fixture.
   */
  public AbstractWindowFixture<T> resizeWidthTo(int width) {
    return resizeTo(new Dimension(width, target.getHeight()));
  }

  /**
   * Simulates a user resizing the target window vertically.
   * @param height the height that the target window should have after being resized.
   * @return this fixture.
   */
  public AbstractWindowFixture<T> resizeHeightTo(int height) {
    return resizeTo(new Dimension(target.getWidth(), height));
  }

  /**
   * Simulates a user resizing the target window horizontally and/or vertically.
   * @param size the size (height and width) that the target window should have after being resized.
   * @return this fixture.
   */
  public AbstractWindowFixture<T> resizeTo(Dimension size) {
    windowTester().resize(target, size.width, size.height);
    return this;
  }

  /**
   * Asserts that the size of the target window is equal to given one. 
   * @param size the given size to match.
   * @return this fixture.
   * @throws AssertionError if the size of the target window is not equal to the given size. 
   */
  public AbstractWindowFixture<T> requireSize(Dimension size) {
    assertThat(target.getSize()).isEqualTo(size);
    return this;
  }

  protected final WindowTester windowTester() {
    return testerCastedTo(WindowTester.class);
  }

  @Override public AbstractWindowFixture<T> requireVisible() {
    return (AbstractWindowFixture<T>)super.requireVisible();
  }

  public final void cleanUp() {
    robot.cleanUp();
  }
}
