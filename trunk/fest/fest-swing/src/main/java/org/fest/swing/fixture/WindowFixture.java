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

import org.fest.swing.ComponentLookupException;
import org.fest.swing.RobotFixture;
import org.fest.swing.ScreenLock;

/**
 * Understands simulation of user events on a <code>{@link Window}</code> and verification of the state of such
 * <code>{@link Window}</code>.
 * @param <T> the type of window handled by this fixture. 
 *
 * @author Alex Ruiz
 */
public abstract class WindowFixture<T extends Window> extends ContainerFixture<T> {

  /**
   * Creates a new </code>{@link WindowFixture}</code>. This constructor creates a new <code>{@link RobotFixture}</code>
   * containing the current AWT hierarchy.
   * @param type the type of <code>Window</code> to find using the created <code>RobotFixture</code>.
   * @throws ComponentLookupException if a <code>Window</code> having a matching name could not be found. 
   * @see RobotFixture#robotWithCurrentAwtHierarchy()
   */
  public WindowFixture(Class<? extends T> type) {
    this(robotWithCurrentAwtHierarchy(), type);
  }

  /**
   * Creates a new </code>{@link WindowFixture}</code>.
   * @param robot performs simulation of user events on a <code>Window</code>.
   * @param type the type of <code>Window</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a dialog having a matching name could not be found. 
   */
  public WindowFixture(RobotFixture robot, Class<? extends T> type) {
    super(robot, type);
  }

  /**
   * Creates a new </code>{@link WindowFixture}</code>. This constructor creates a new <code>{@link RobotFixture}</code>
   * containing the current AWT hierarchy.
   * @param name the name of the <code>Window</code> to find using the given <code>RobotFixture</code>.
   * @param type the type of <code>Window</code> to find using the created <code>RobotFixture</code>.
   * @throws ComponentLookupException if a <code>Window</code> having a matching name could not be found. 
   * @see RobotFixture#robotWithCurrentAwtHierarchy()
   */
  public WindowFixture(String name, Class<? extends T> type) {
    this(robotWithCurrentAwtHierarchy(), name, type);
  }
  
  /**
   * Creates a new </code>{@link WindowFixture}</code>.
   * @param robot performs simulation of user events on a <code>Window</code>.
   * @param name the name of the <code>Window</code> to find using the given <code>RobotFixture</code>.
   * @param type the type of <code>Window</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a dialog having a matching name could not be found. 
   */
  public WindowFixture(RobotFixture robot, String name, Class<? extends T> type) {
    super(robot, name, type);
  }

  /**
   * Creates a new </code>{@link WindowFixture}</code>. This constructor creates a new <code>{@link RobotFixture}</code>
   * containing the current AWT hierarchy.
   * @param target the <code>Window</code> to be managed by this fixture.
   */
  public WindowFixture(T target) {
    this(robotWithCurrentAwtHierarchy(), target);
  }
  
  /**
   * Creates a new </code>{@link WindowFixture}</code>.
   * @param robot performs simulation of user events on the given <code>Window</code>.
   * @param target the <code>Window</code> to be managed by this fixture.
   */
  public WindowFixture(RobotFixture robot, T target) {
    super(robot, target);
  }

  /**
   * Shows the <code>{@link Window}</code> managed by this fixture.
   * @return this fixture.
   */
  public WindowFixture<T> show() {
    robot.showWindow(target);
    return requireVisible();
  }
  
  /**
   * Shows the <code>{@link Window}</code> managed by this fixture, resized to the given size.
   * @param size the size to resize the managed <code>Window</code> to.
   * @return this fixture.
   */
  public WindowFixture<T> show(Dimension size) {
    robot.showWindow(target, size);
    return requireVisible();
  }
  
  /**
   * Simulates a user resizing horizontally the <code>{@link Window}</code> managed by this fixture.
   * @param width the width that the managed <code>Window</code> should have after being resized.
   * @return this fixture.
   */
  public WindowFixture<T> resizeWidthTo(int width) {
    return resizeTo(new Dimension(width, target.getHeight()));
  }

  /**
   * Simulates a user resizing vertically the <code>{@link Window}</code> managed by this fixture.
   * @param height the height that the managed <code>Window</code> should have after being resized.
   * @return this fixture.
   */
  public WindowFixture<T> resizeHeightTo(int height) {
    return resizeTo(new Dimension(target.getWidth(), height));
  }

  /**
   * Simulates a user resizing the <code>{@link Window}</code> managed by this fixture.
   * @param size the size that the target window should have after being resized.
   * @return this fixture.
   */
  public WindowFixture<T> resizeTo(Dimension size) {
    windowTester().resize(target, size.width, size.height);
    return this;
  }

  /**
   * Asserts that the size of the <code>{@link Window}</code> managed by this fixture is equal to given one. 
   * @param size the given size to match.
   * @return this fixture.
   * @throws AssertionError if the size of the managed <code>Window</code> is not equal to the given size. 
   */
  public WindowFixture<T> requireSize(Dimension size) {
    assertThat(target.getSize()).isEqualTo(size);
    return this;
  }

  protected final WindowTester windowTester() {
    return testerCastedTo(WindowTester.class);
  }

  /**
   * Asserts that the <code>{@link Window}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>Window</code> is not visible.
   */
  @Override public WindowFixture<T> requireVisible() {
    return (WindowFixture<T>)super.requireVisible();
  }

  /** 
   * Cleans up any used resources (keyboard, mouse, open windows and <code>{@link ScreenLock}</code>) used by this 
   * robot.
   */  
  public final void cleanUp() {
    robot.cleanUp();
  }
}
