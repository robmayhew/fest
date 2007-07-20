/*
 * Created on Apr 10, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.text.JTextComponent;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Strings.join;

import org.fest.swing.ComponentLookupException;
import org.fest.swing.ComponentMatcher;
import org.fest.swing.RobotFixture;

/**
 * Understands lookup of <code>{@link Component}</code>s contained in a <code>{@link Container}</code>.
 * @param <T> the type of container handled by this fixture.
 * 
 * @author Alex Ruiz
 */
public abstract class ContainerFixture<T extends Container> extends ComponentFixture<T> {

  private static class JMenuItemMatcher extends abbot.finder.matchers.JMenuItemMatcher implements ComponentMatcher {
    public JMenuItemMatcher(String label) {
      super(label);
    }
  }

  /**
   * Creates a new </code>{@link ContainerFixture}</code>.
   * @param robot performs simulation of user events on a <code>Container</code>.
   * @param type the type of the <code>Container</code> to find using the given <code>RobotFixture</code>.
   * @see org.fest.swing.ComponentFinder#findByType(Class)
   */
  public ContainerFixture(RobotFixture robot, Class<? extends T> type) {
    super(robot, robot.finder().findByType(type));
  }

  /**
   * Creates a new </code>{@link ContainerFixture}</code>.
   * @param robot performs simulation of user events on a <code>Container</code>.
   * @param name the name of the <code>Container</code> to find using the given <code>RobotFixture</code>.
   * @param type the type of the <code>Container</code> to find using the given <code>RobotFixture</code>.
   * @see org.fest.swing.ComponentFinder#findByName(String, Class)
   */
  public ContainerFixture(RobotFixture robot, String name, Class<? extends T> type) {
    super(robot, robot.finder().findByName(name, type));
  }

  /**
   * Creates a new </code>{@link ContainerFixture}</code>.
   * @param robot performs simulation of user events on the given <code>Container</code>.
   * @param target the <code>Container</code> to be managed by this fixture.
   */
  public ContainerFixture(RobotFixture robot, T target) {
    super(robot, target);
  }

  /**
   * Finds a <code>{@link JButton}</code>, contained in the <code>{@link Container}</code> managed by this fixture, 
   * which name matches the specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JButton</code> found.
   * @throws ComponentLookupException if a <code>JButton</code> having a matching name could not be found.
   */
  public final JButtonFixture button(String name) {
    return new JButtonFixture(robot, robot.finder().findByName(target, name, JButton.class));
  }

  /**
   * Finds a <code>{@link JCheckBox}</code>, contained in the <code>{@link Container}</code> managed by this fixture, 
   * which name matches the specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JCheckBox</code> found.
   * @throws ComponentLookupException if a <code>JCheckBox</code> having a matching name could not be found.
   */
  public final JCheckBoxFixture checkBox(String name) {
    return new JCheckBoxFixture(robot, robot.finder().findByName(target, name, JCheckBox.class));
  }

  /**
   * Finds a <code>{@link JComboBox}</code>, contained in the <code>{@link Container}</code> managed by this fixture, 
   * which name matches the specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JComboBox</code> found.
   * @throws ComponentLookupException if a <code>JComboBox</code> having a matching name could not be found.
   */
  public final JComboBoxFixture comboBox(String name) {
    return new JComboBoxFixture(robot, robot.finder().findByName(target, name, JComboBox.class));
  }

  /**
   * Finds a <code>{@link Dialog}</code>, contained in the <code>{@link Container}</code> managed by this fixture, 
   * which name matches the specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>Dialog</code> found.
   * @throws ComponentLookupException if a <code>Dialog</code> having a matching name could not be found.
   */
  public final DialogFixture dialog(String name) {
    return new DialogFixture(robot, robot.finder().findByName(target, name, Dialog.class));
  }

  /**
   * Finds a <code>{@link JLabel}</code>, contained in the <code>{@link Container}</code> managed by this fixture, 
   * which name matches the specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JLabel</code> found.
   * @throws ComponentLookupException if a <code>JLabel</code> having a matching name could not be found.
   */
  public final JLabelFixture label(String name) {
    return new JLabelFixture(robot, robot.finder().findByName(target, name, JLabel.class));
  }

  /**
   * Finds a <code>{@link JMenuItem}</code>, contained in the <code>{@link Container}</code> managed by this fixture, 
   * which name matches the specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JMenuItem</code> found.
   * @throws ComponentLookupException if a <code>JMenuItem</code> having a matching name could not be found.
   */
  public final JMenuItemFixture menuItem(String name) {
    return new JMenuItemFixture(robot, robot.finder().findByName(target, name, JMenuItem.class));
  }

  /**
   * Finds a <code>{@link JMenuItem}</code>, contained in the <code>{@link Container}</code> managed by this fixture, 
   * which path matches the given one. 
   * <p>
   * For example, if we are looking for the menu with text "New" contained under the menu with text "File", we can 
   * simply call
   * <pre>
   * JMenuItemFixture menuItem = container.<strong>findMenuItem("File", "Menu")</strong>;
   * </pre>
   * </p>
   * @param path the path of the menu to find.
   * @return a fixture that manages the <code>JMenuItem</code> found.
   * @throws ComponentLookupException if a <code>JMenuItem</code> under the given path could not be found.
   * @throws AssertionError if the <code>Component</code> found under the given path is not a <code>JMenuItem</code>.
   */
  public final JMenuItemFixture menuItem(String... path) {
    ComponentMatcher m = new JMenuItemMatcher(join(path).with("|"));
    Component item = robot.finder().find(target, m);
    assertThat(item).isInstanceOf(JMenuItem.class);
    return new JMenuItemFixture(robot, (JMenuItem) item);
  }

  /**
   * Finds a <code>{@link JOptionPane}</code>.
   * @return a fixture that manages the <code>JOptionPane</code> found.
   * @throws ComponentLookupException if a <code>JOptionPane</code> could not be found.
   */
  public final JOptionPaneFixture optionPane() {
    return new JOptionPaneFixture(robot);
  }

  /**
   * Finds a <code>{@link JSlider}</code>, contained in the <code>{@link Container}</code> managed by this fixture, 
   * which name matches the specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JSlider</code> found.
   * @throws ComponentLookupException if a <code>JSlider</code> having a matching name could not be found.
   */
  public final JSliderFixture slider(String name) {
    return new JSliderFixture(robot, robot.finder().findByName(target, name, JSlider.class));
  }
  
  /**
   * Finds a <code>{@link JSpinner}</code>, contained in the <code>{@link Container}</code> managed by this fixture, 
   * which name matches the specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JSpinner</code> found.
   * @throws ComponentLookupException if a <code>JSpinner</code> having a matching name could not be found.
   */
  public final JSpinnerFixture spinner(String name) {
    return new JSpinnerFixture(robot, robot.finder().findByName(target, name, JSpinner.class));
  }
  
  /**
   * Finds a <code>{@link JTabbedPane}</code>, contained in the <code>{@link Container}</code> managed by this fixture, 
   * which name matches the specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JTabbedPane</code> found.
   * @throws ComponentLookupException if a <code>JTabbedPane</code> having a matching name could not be found.
   */
  public final JTabbedPaneFixture tabbedPane(String name) {
    return new JTabbedPaneFixture(robot, robot.finder().findByName(target, name, JTabbedPane.class));
  }
  
  /**
   * Finds a <code>{@link JTextComponent}</code>, contained in the <code>{@link Container}</code> managed by this 
   * fixture, which name matches the specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JTextComponent</code> found.
   * @throws ComponentLookupException if a <code>JTextComponent</code> having a matching name could not be found.
   */
  public final JTextComponentFixture textBox(String name) {
    return new JTextComponentFixture(robot, robot.finder().findByName(target, name, JTextComponent.class));
  }
  
  /**
   * Finds a <code>{@link JToolBar}</code>, contained in the <code>{@link Container}</code> managed by this fixture, 
   * which name matches the specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JToolBar</code> found.
   * @throws ComponentLookupException if a <code>JToolBar</code> having a matching name could not be found.
   */
  public final JToolBarFixture toolBar(String name) {
    return new JToolBarFixture(robot, robot.finder().findByName(target, name, JToolBar.class));
  }
  
  /**
   * Finds a <code>{@link JTree}</code>, contained in the <code>{@link Container}</code> managed by this fixture, 
   * which name matches the specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JTree</code> found.
   * @throws ComponentLookupException if a <code>JTree</code> having a matching name could not be found.
   */
  public final JTreeFixture tree(String name) {
    return new JTreeFixture(robot, robot.finder().findByName(target, name, JTree.class));
  }
}
