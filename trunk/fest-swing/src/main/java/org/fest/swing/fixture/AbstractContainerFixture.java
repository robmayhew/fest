/*
 * Created on Apr 10, 2007
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

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.text.JTextComponent;

import org.fest.swing.RobotFixture;


import abbot.finder.Matcher;
import abbot.finder.matchers.JMenuItemMatcher;


import static org.fest.swing.assertions.Assertions.assertThat;
import static org.fest.swing.util.Strings.join;


/**
 * @param <T> the type of container handled by this fixture. 
 *
 * @author Alex Ruiz
 */
public abstract class AbstractContainerFixture<T extends Container> extends AbstractComponentFixture<T> {

  /**
   * Creates a new </code>{@link AbstractContainerFixture}</code>.
   * @param robot performs simulation of user events on a <code>Container</code>.
   * @param type the type of <code>Container</code> to find using the given <code>AbbotFixture</code>.
   * @see RobotFixture#findByName(String, Class)
   */
  public AbstractContainerFixture(RobotFixture robot, Class<? extends T> type) {
    super(robot, robot.findByType(type));
  }

  /**
   * Creates a new </code>{@link AbstractContainerFixture}</code>.
   * @param robot performs simulation of user events on a <code>Component</code>.
   * @param name the name of Container <code>Component</code> to find using the given <code>AbbotFixture</code>.
   * @param type the type of <code>Container</code> to find using the given <code>AbbotFixture</code>.
   * @see RobotFixture#findByName(String, Class)
   */
  public AbstractContainerFixture(RobotFixture robot, String name, Class<? extends T> type) {
    super(robot, robot.findByName(name, type));
  }
  
  /**
   * Creates a new </code>{@link AbstractContainerFixture}</code>.
   * @param robot performs simulation of user events on the given component.
   * @param target the component under test.
   */
  public AbstractContainerFixture(RobotFixture robot, T target) {
    super(robot, target);
  }

  /**
   * Finds a <code>{@link JLabel}</code> in the target container having the given name.
   * @param name the name to match.
   * @return a fixture wrapping the found component.
   */
  public final JLabelFixture findLabel(String name) {
    return new JLabelFixture(robot, robot.findByName(target, name, JLabel.class));
  }

  /**
   * Finds a <code>{@link JButton}</code> in the target container having the given name.
   * @param name the name to match.
   * @return a fixture wrapping the found component.
   */
  public final JButtonFixture findButton(String name) {
    return new JButtonFixture(robot, robot.findByName(target, name, JButton.class));
  }

  /**
   * Finds a <code>{@link Dialog}</code> in the target container having the given name.
   * @param name the name to match.
   * @return a fixture wrapping the found component.
   */
  public final DialogFixture findDialog(String name) {
    return new DialogFixture(robot, robot.findByName(target, name, Dialog.class));
  }

  /**
   * Finds a <code>{@link JMenuItem}</code> in the target container having the given name.
   * @param name the name to match.
   * @return a fixture wrapping the found component.
   */
  public final JMenuItemFixture findMenuItem(String name) {
    return new JMenuItemFixture(robot, robot.findByName(target, name, JMenuItem.class));
  }

  /**
   * Finds a <code>{@link JMenuItem}</code> in the target container, which path matches the given one.
   * @param path the path of the menu to find. For example, if we are looking for the submenu "New" contained in the
   *          menu "File", we shoul call <code>findMenuItem("File", "Menu")</code>.
   * @return a fixture wrapping the found component.
   */
  public final JMenuItemFixture findMenuItem(String...path) {
    Matcher m = new JMenuItemMatcher(join("|", path));
    Component item = robot.find(target, m);
    assertThat(item).isInstanceOf(JMenuItem.class);
    return new JMenuItemFixture(robot, (JMenuItem)item);
  }

  /**
   * Finds a <code>{@link JOptionPane}</code> in the target container
   * @return a fixture wrapping the found component.
   */
  public final JOptionPaneFixture findOptionPane() {
    return new JOptionPaneFixture(robot);
  }

  /**
   * Finds a <code>{@link JTextComponent}</code> in the target container having the given name.
   * @param name the name to match.
   * @return a fixture wrapping the found component.
   */
  public final JTextComponentFixture findTextComponent(String name) {
    return new JTextComponentFixture(robot, robot.findByName(target, name, JTextComponent.class));
  }

  /**
   * Finds a <code>{@link JComboBox}</code> in the target container having the given name.
   * @param name the name to match.
   * @return a fixture wrapping the found component.
   */
  public final JComboBoxFixture findComboBox(String name) {
    return new JComboBoxFixture(robot, robot.findByName(target, name, JComboBox.class));
  }

  /**
   * Finds a <code>{@link JTabbedPane}</code> in the target container having the given name.
   * @param name the name to match.
   * @return a fixture wrapping the found component.
   */
  public final JTabbedPaneFixture findTabbedPane(String name) {
    return new JTabbedPaneFixture(robot, robot.findByName(target, name, JTabbedPane.class));
  }
}
