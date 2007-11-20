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

import abbot.tester.WindowTester;
import static org.fest.assertions.Assertions.assertThat;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.core.RobotFixture;
import static org.fest.swing.core.RobotFixture.robotWithCurrentAwtHierarchy;
import org.fest.swing.core.ScreenLock;

import java.awt.*;

/**
 * Understands simulation of user events on a <code>{@link Window}</code> and verification of the state of such
 * <code>{@link Window}</code>.
 * @param <T> the type of window handled by this fixture. 
 *
 * @author Alex Ruiz
 */
public abstract class WindowFixture<T extends Window> extends ContainerFixture<T> {

  /**
   * Creates a new <code>{@link WindowFixture}</code>. This constructor creates a new <code>{@link org.fest.swing.core.RobotFixture}</code>
   * containing the current AWT hierarchy.
   * @param type the type of <code>Window</code> to find using the created <code>RobotFixture</code>.
   * @throws org.fest.swing.exception.ComponentLookupException if a <code>Window</code> having a matching name could not be found.
   * @see RobotFixture#robotWithCurrentAwtHierarchy()
   */
  public WindowFixture(Class<? extends T> type) {
    this(robotWithCurrentAwtHierarchy(), type);
  }

  /**
   * Creates a new <code>{@link WindowFixture}</code>.
   * @param robot performs simulation of user events on a <code>Window</code>.
   * @param type the type of <code>Window</code> to find using the given <code>RobotFixture</code>.
   * @throws org.fest.swing.exception.ComponentLookupException if a dialog having a matching name could not be found.
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
   * @throws ComponentLookupException if a dialog having a matching name could not be found. 
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
   * Shows the <code>{@link Window}</code> managed by this fixture.
   * @return this fixture.
   */
  protected abstract WindowFixture<T> show();

  /**
   * Shows the <code>{@link Window}</code> managed by this fixture, resized to the given size.
   * @param size the size to resize the managed <code>Window</code> to.
   * @return this fixture.
   */
  protected abstract WindowFixture<T> show(Dimension size);

  /**
   * Simulates a user closing the <code>{@link Window}</code> managed by this fixture.
   */
  public void close() {
    robot.close(target);
  }
  
  /**
   * Simulates a user resizing horizontally the <code>{@link Window}</code> managed by this fixture.
   * @param width the width that the managed <code>Window</code> should have after being resized.
   * @return this fixture.
   */
  protected abstract WindowFixture<T> resizeWidthTo(int width);

  /**
   * Simulates a user resizing vertically the <code>{@link Window}</code> managed by this fixture.
   * @param height the height that the managed <code>Window</code> should have after being resized.
   * @return this fixture.
   */
  protected abstract WindowFixture<T> resizeHeightTo(int height);

  /**
   * Simulates a user resizing the <code>{@link Window}</code> managed by this fixture.
   * @param size the size that the target window should have after being resized.
   * @return this fixture.
   */
  protected abstract WindowFixture<T> resizeTo(Dimension size);

  /**
   * Asserts that the size of the <code>{@link Window}</code> managed by this fixture is equal to given one. 
   * @param size the given size to match.
   * @return this fixture.
   * @throws AssertionError if the size of the managed <code>Window</code> is not equal to the given size. 
   */
  protected abstract WindowFixture<T> requireSize(Dimension size);

  /**
   * Shows the <code>{@link Window}</code> managed by this fixture.
   * @return this fixture.
   */
  protected final WindowFixture<T> doShow() {
    robot.showWindow(target);
    assertVisible();
    return this;
  }

  /**
   * Shows the <code>{@link Window}</code> managed by this fixture, resized to the given size.
   * @param size the size to resize the managed <code>Window</code> to.
   * @return this fixture.
   */
  protected final WindowFixture<T> doShow(Dimension size) {
    robot.showWindow(target, size);
    assertVisible();
    return this;
  }

  /**
   * Simulates a user resizing horizontally the <code>{@link Window}</code> managed by this fixture.
   * @param width the width that the managed <code>Window</code> should have after being resized.
   * @return this fixture.
   */
  protected final WindowFixture<T> doResizeWidthTo(int width) {
    return doResizeTo(new Dimension(width, target.getHeight()));
  }

  /**
   * Simulates a user resizing vertically the <code>{@link Window}</code> managed by this fixture.
   * @param height the height that the managed <code>Window</code> should have after being resized.
   * @return this fixture.
   */
  protected final WindowFixture<T> doResizeHeightTo(int height) {
    return doResizeTo(new Dimension(target.getWidth(), height));
  }

  /**
   * Simulates a user resizing the <code>{@link Window}</code> managed by this fixture.
   * @param size the size that the target window should have after being resized.
   * @return this fixture.
   */
  protected final WindowFixture<T> doResizeTo(Dimension size) {
    windowTester().resize(target, size.width, size.height);
    return this;
  }

  /**
   * Asserts that the size of the <code>{@link Window}</code> managed by this fixture is equal to given one. 
   * @param size the given size to match.
   * @return this fixture.
   * @throws AssertionError if the size of the managed <code>Window</code> is not equal to the given size. 
   */
  protected final WindowFixture<T> assertEqualSize(Dimension size) {
    assertThat(target.getSize()).as(formattedPropertyName("size")).isEqualTo(size);
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
