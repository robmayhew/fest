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

import org.fest.swing.core.RobotFixture;
import org.fest.swing.core.ScreenLock;
import org.fest.swing.exception.ComponentLookupException;

import static org.fest.swing.core.RobotFixture.robotWithCurrentAwtHierarchy;

/**
 * Understands simulation of user events on a <code>{@link Window}</code> and verification of the state of such
 * <code>{@link Window}</code>.
 * @param <T> the type of window handled by this fixture. 
 *
 * @author Alex Ruiz
 */
public abstract class WindowFixture<T extends Window> extends ContainerFixture<T> implements WindowLikeFixture {

  /**
   * Creates a new <code>{@link WindowFixture}</code>. This constructor creates a new <code>{@link org.fest.swing.core.RobotFixture}</code>
   * containing the current AWT hierarchy.
   * @param type the type of <code>Window</code> to find using the created <code>RobotFixture</code>.
   * @throws ComponentLookupException if a <code>Window</code> having a matching type could not be found.
   * @throws ComponentLookupException if more than one <code>Window</code> having a matching type is found.
   * @see RobotFixture#robotWithCurrentAwtHierarchy()
   */
  public WindowFixture(Class<? extends T> type) {
    this(robotWithCurrentAwtHierarchy(), type);
  }

  /**
   * Creates a new <code>{@link WindowFixture}</code>.
   * @param robot performs simulation of user events on a <code>Window</code>.
   * @param type the type of <code>Window</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a <code>Window</code> having a matching type could not be found.
   * @throws ComponentLookupException if more than one <code>Window</code> having a matching type is found.
   */
  public WindowFixture(RobotFixture robot, Class<? extends T> type) {
    super(robot, type);
  }

  /**
   * Creates a new <code>{@link WindowFixture}</code>. This constructor creates a new <code>{@link RobotFixture}</code>
   * containing the current AWT hierarchy.
   * @param name the name of the <code>Window</code> to find.
   * @param type the type of <code>Window</code> to find using the created <code>RobotFixture</code>.
   * @throws ComponentLookupException if a <code>Window</code> having a matching name could not be found. 
   * @throws ComponentLookupException if more than one <code>Window</code> having a matching name is found. 
   * @see RobotFixture#robotWithCurrentAwtHierarchy()
   */
  public WindowFixture(String name, Class<? extends T> type) {
    this(robotWithCurrentAwtHierarchy(), name, type);
  }
  
  /**
   * Creates a new <code>{@link WindowFixture}</code>.
   * @param robot performs simulation of user events on a <code>Window</code>.
   * @param name the name of the <code>Window</code> to find using the given <code>RobotFixture</code>.
   * @param type the type of <code>Window</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a <code>Window</code> having a matching name could not be found. 
   * @throws ComponentLookupException if more than one <code>Window</code> having a matching name is found. 
   */
  public WindowFixture(RobotFixture robot, String name, Class<? extends T> type) {
    super(robot, name, type);
  }

  /**
   * Creates a new <code>{@link WindowFixture}</code>. This constructor creates a new <code>{@link RobotFixture}</code>
   * containing the current AWT hierarchy.
   * @param target the <code>Window</code> to be managed by this fixture.
   */
  public WindowFixture(T target) {
    this(robotWithCurrentAwtHierarchy(), target);
  }
  
  /**
   * Creates a new <code>{@link WindowFixture}</code>.
   * @param robot performs simulation of user events on the given <code>Window</code>.
   * @param target the <code>Window</code> to be managed by this fixture.
   */
  public WindowFixture(RobotFixture robot, T target) {
    super(robot, target);
  }

  /**
   * Shows this fixture's <code>{@link Window}</code>.
   * @return this fixture.
   */
  protected abstract WindowFixture<T> show();

  /**
   * Shows this fixture's <code>{@link Window}</code>, resized to the given size.
   * @param size the size to resize this fixture's <code>Window</code> to.
   * @return this fixture.
   */
  protected abstract WindowFixture<T> show(Dimension size);

  /**
   * Simulates a user closing this fixture's <code>{@link Window}</code>.
   */
  public final void close() {
    robot.close(target);
  }
  
  /**
   * Shows this fixture's <code>{@link Window}</code>.
   * @return this fixture.
   */
  protected final WindowFixture<T> doShow() {
    robot.showWindow(target);
    assertVisible();
    return this;
  }

  /**
   * Shows this fixture's <code>{@link Window}</code>, resized to the given size.
   * @param size the size to resize this fixture's <code>Window</code> to.
   * @return this fixture.
   */
  protected final WindowFixture<T> doShow(Dimension size) {
    robot.showWindow(target, size);
    assertVisible();
    return this;
  }

  /**
   * Simulates a user resizing horizontally this fixture's <code>{@link Window}</code>.
   * @param width the width that this fixture's <code>Window</code> should have after being resized.
   * @return this fixture.
   */
  protected final WindowFixture<T> doResizeWidthTo(int width) {
    return doResizeTo(new Dimension(width, target.getHeight()));
  }

  /**
   * Simulates a user resizing vertically this fixture's <code>{@link Window}</code>.
   * @param height the height that this fixture's <code>Window</code> should have after being resized.
   * @return this fixture.
   */
  protected final WindowFixture<T> doResizeHeightTo(int height) {
    return doResizeTo(new Dimension(target.getWidth(), height));
  }

  /**
   * Simulates a user resizing this fixture's <code>{@link Window}</code>.
   * @param size the size that the target window should have after being resized.
   * @return this fixture.
   */
  protected final WindowFixture<T> doResizeTo(Dimension size) {
    windowTester().resize(target, size.width, size.height);
    return this;
  }

  protected final WindowTester windowTester() {
    return (WindowTester)tester();
  }

  /** 
   * Cleans up any used resources (keyboard, mouse, open windows and <code>{@link ScreenLock}</code>) used by this 
   * robot.
   */  
  public final void cleanUp() {
    robot.cleanUp();
  }
}
