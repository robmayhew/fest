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

import org.fest.swing.core.Robot;
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
public abstract class WindowFixture<T extends Window> extends ContainerFixture<T> implements WindowLikeContainerFixture {

  /**
   * Creates a new <code>{@link WindowFixture}</code>. This constructor creates a new <code>{@link RobotFixture}</code>
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
  public WindowFixture(Robot robot, Class<? extends T> type) {
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
  public WindowFixture(Robot robot, String name, Class<? extends T> type) {
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
  public WindowFixture(Robot robot, T target) {
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
   * Cleans up any used resources (keyboard, mouse, open windows and <code>{@link ScreenLock}</code>) used by this
   * robot.
   */
  public final void cleanUp() {
    robot.cleanUp();
  }
}
