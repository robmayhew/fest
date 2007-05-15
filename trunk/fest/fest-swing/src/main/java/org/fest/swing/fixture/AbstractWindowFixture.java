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
 * Template for implementations of <code>{@link WindowFixture}</code>
 * @param <T> the type of window handled by this fixture. 
 *
 * @author Alex Ruiz
 */
public abstract class AbstractWindowFixture<T extends Window> extends AbstractContainerFixture<T> implements WindowFixture<T> {

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

  protected final void doShow() {
    robot.showWindow(target);
    assertIsVisible();
  }
  
  protected final void doShow(Dimension size) {
    robot.showWindow(target, size);
    assertIsVisible();
  }

  protected final void doResizeWidthTo(int width) {
    doResizeTo(new Dimension(width, target.getHeight()));
  }

  protected final void doResizeHeightTo(int height) {
    doResizeTo(new Dimension(target.getWidth(), height));
  }

  protected final void doResizeTo(Dimension size) {
    windowTester().resize(target, size.width, size.height);
  }
  
  protected final WindowTester windowTester() {
    return testerCastedTo(WindowTester.class);
  }

  protected final void assertEqualSize(Dimension size) {
    assertThat(target.getSize()).isEqualTo(size);
  }
  
  public final void cleanUp() {
    robot.cleanUp();
  }
}
