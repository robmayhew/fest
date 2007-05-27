/*
 * Created on Oct 20, 2006
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
 * Copyright @2006 the original author or authors.
 */
package org.fest.swing.fixture;

import javax.swing.Action;
import javax.swing.JMenuItem;

import org.fest.swing.RobotFixture;



/**
 * Understands simulation of user events and state verification of a <code>{@link JMenuItem}</code>.
 *
 * @author Alex Ruiz
 */
public class JMenuItemFixture extends AbstractComponentFixture<JMenuItem> {

  /**
   * Creates a new </code>{@link JMenuItemFixture}</code>.
   * @param robot performs simulation of user events on a <code>JMenuItem</code>.
   * @param menuItemName the name of the menu item to find using the given <code>RobotFixture</code>.
   * @see org.fest.swing.ComponentFinder#findByName(String, Class)
   */
  public JMenuItemFixture(RobotFixture robot, String menuItemName) {
    super(robot, menuItemName, JMenuItem.class);
  }
  
  /**
   * Creates a new </code>{@link JMenuItemFixture}</code>. It uses the given <code>{@link Action}</code> to create a new 
   * <code>{@link JMenuItem}</code> as the target menu item.
   * @param robot allows simulation of user events on the target menu item.
   * @param action the <code>Action</code> to assign to the created menu.
   */
  public JMenuItemFixture(RobotFixture robot, Action action) {
    this(robot, new JMenuItem(action));
  }
  
  /**
   * Creates a new </code>{@link JMenuItemFixture}</code>.
   * @param robot allows simulation of user events on the target menu item.
   * @param target the target menu item.
   */
  public JMenuItemFixture(RobotFixture robot, JMenuItem target) {
    super(robot, target);
  }
  
  /**
   * Selects the target menu item.
   * @return this fixture.
   */
  public final JMenuItemFixture select() {
    robot.selectMenuItem(target);
    return this;
  }

  /** {@inheritDoc} */
  @Override public final JMenuItemFixture click() {
    return (JMenuItemFixture)super.click();
  }

  /** {@inheritDoc} */
  @Override public final JMenuItemFixture focus() {
    return (JMenuItemFixture)super.focus();
  }

  /** {@inheritDoc} */
  @Override public final JMenuItemFixture requireVisible() {
    return (JMenuItemFixture)super.requireVisible();
  }

  /** {@inheritDoc} */
  @Override public final JMenuItemFixture requireNotVisible() {
    return (JMenuItemFixture)super.requireNotVisible();
  }

  /** {@inheritDoc} */
  @Override public final JMenuItemFixture requireEnabled() {
    return (JMenuItemFixture)super.requireEnabled();
  }
  
  /** {@inheritDoc} */  
  @Override public final JMenuItemFixture requireDisabled() {
    return (JMenuItemFixture)super.requireDisabled();
  }
}
