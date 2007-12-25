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

import javax.swing.*;
import javax.swing.text.JTextComponent;

import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.format.Formatting.format;

import static org.fest.util.Strings.join;

import org.fest.swing.core.ComponentMatcher;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Understands lookup of <code>{@link Component}</code>s contained in a <code>{@link Container}</code>.
 * @param <T> the type of container handled by this fixture.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class ContainerFixture<T extends Container> extends JMenuItemContainerFixture<T> {

  /**
   * Creates a new <code>{@link ContainerFixture}</code>.
   * @param robot performs simulation of user events on a <code>Container</code>.
   * @param type the type of the <code>Container</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching component could not be found.
   * @throws ComponentLookupException if more than one matching component is found.
   * @see org.fest.swing.core.ComponentFinder#findByType(Class)
   */
  public ContainerFixture(RobotFixture robot, Class<? extends T> type) {
    super(robot, robot.finder().findByType(type));
  }

  /**
   * Creates a new <code>{@link ContainerFixture}</code>.
   * @param robot performs simulation of user events on a <code>Container</code>.
   * @param name the name of the <code>Container</code> to find using the given <code>RobotFixture</code>.
   * @param type the type of the <code>Container</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching component could not be found.
   * @throws ComponentLookupException if more than one matching component is found.
   * @see org.fest.swing.core.ComponentFinder#findByName(String, Class)
   */
  public ContainerFixture(RobotFixture robot, String name, Class<? extends T> type) {
    super(robot, robot.finder().findByName(name, type));
  }

  /**
   * Creates a new <code>{@link ContainerFixture}</code>.
   * @param robot performs simulation of user events on the given <code>Container</code>.
   * @param target the <code>Container</code> to be.
   */
  public ContainerFixture(RobotFixture robot, T target) {
    super(robot, target);
  }

  /**
   * Returns a <code>{@link JButton}</code> found in this fixture's <code>{@link Container}</code>.
   * @return a fixture that manages the <code>JButton</code> found.
   * @throws ComponentLookupException if a <code>JButton</code> could not be found.
   * @throws ComponentLookupException if more than one <code>JButton</code> is found.
   */
  public final JButtonFixture button() {
    return new JButtonFixture(robot, findByType(JButton.class));
  }

  /**
   * Finds a <code>{@link JButton}</code> in this fixture's <code>{@link Container}</code>, that matches the
   * specified search criteria.
   * @param matcher contains the search criteria for finding a <code>JButton</code>.
   * @return a fixture that manages the <code>JButton</code> found.
   * @throws ComponentLookupException if a <code>JButton</code> that matches the given search criteria could not be found.
   * @throws ComponentLookupException if more than one <code>JButton</code> that matches the given search criteria is found.
   */
  public final JButtonFixture button(GenericTypeMatcher<? extends JButton> matcher) {
    return new JButtonFixture(robot, find(matcher));
  }

  /**
   * Finds a <code>{@link JButton}</code> in this fixture's <code>{@link Container}</code>, which name matches the
   * specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JButton</code> found.
   * @throws ComponentLookupException if a <code>JButton</code> having a matching name could not be found.
   * @throws ComponentLookupException if more than one <code>JButton</code> having a matching name is found.
   */
  public final JButtonFixture button(String name) {
    return new JButtonFixture(robot, findByName(name, JButton.class));
  }

  /**
   * Returns a <code>{@link JCheckBox}</code> found in this fixture's <code>{@link Container}</code>.
   * @return a fixture that manages the <code>JCheckBox</code> found.
   * @throws ComponentLookupException if a <code>JCheckBox</code> could not be found.
   * @throws ComponentLookupException if more than one <code>JCheckBox</code> is found.
   */
  public final JCheckBoxFixture checkBox() {
    return new JCheckBoxFixture(robot, findByType(JCheckBox.class));
  }

  /**
   * Finds a <code>{@link JCheckBox}</code> in this fixture's <code>{@link Container}</code>, that matches the
   * specified search criteria.
   * @param matcher contains the search criteria for finding a <code>JCheckBox</code>.
   * @return a fixture that manages the <code>JCheckBox</code> found.
   * @throws ComponentLookupException if a <code>JCheckBox</code> that matches the given search criteria could not be found.
   * @throws ComponentLookupException if more than one <code>JCheckBox</code> that matches the given search criteria is found.
   */
  public final JCheckBoxFixture checkBox(GenericTypeMatcher<? extends JCheckBox> matcher) {
    return new JCheckBoxFixture(robot, find(matcher));
  }

  /**
   * Finds a <code>{@link JCheckBox}</code> in this fixture's <code>{@link Container}</code>, which name matches
   * the specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JCheckBox</code> found.
   * @throws ComponentLookupException if a <code>JCheckBox</code> having a matching name could not be found.
   */
  public final JCheckBoxFixture checkBox(String name) {
    return new JCheckBoxFixture(robot, findByName(name, JCheckBox.class));
  }

  /**
   * Returns a <code>{@link JComboBox}</code> found in this fixture's <code>{@link Container}</code>.
   * @return a fixture that manages the <code>JComboBox</code> found.
   * @throws ComponentLookupException if a <code>JComboBox</code> could not be found.
   * @throws ComponentLookupException if more than one <code>JComboBox</code> is found.
   */
  public final JComboBoxFixture comboBox() {
    return new JComboBoxFixture(robot, findByType(JComboBox.class));
  }

  /**
   * Finds a <code>{@link JComboBox}</code> in this fixture's <code>{@link Container}</code>, that matches the
   * specified search criteria.
   * @param matcher contains the search criteria for finding a <code>JComboBox</code>.
   * @return a fixture that manages the <code>JComboBox</code> found.
   * @throws ComponentLookupException if a <code>JComboBox</code> that matches the given search criteria could not be found.
   * @throws ComponentLookupException if more than one <code>JComboBox</code> that matches the given search criteria is found.
   */
  public final JComboBoxFixture comboBox(GenericTypeMatcher<? extends JComboBox> matcher) {
    return new JComboBoxFixture(robot, find(matcher));
  }

  /**
   * Finds a <code>{@link JComboBox}</code> in this fixture's <code>{@link Container}</code>, which name matches
   * the specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JComboBox</code> found.
   * @throws ComponentLookupException if a <code>JComboBox</code> having a matching name could not be found.
   * @throws ComponentLookupException if more than one <code>JComboBox</code> having a matching name is found.
   */
  public final JComboBoxFixture comboBox(String name) {
    return new JComboBoxFixture(robot, findByName(name, JComboBox.class));
  }

  /**
   * Returns a <code>{@link Dialog}</code> found in this fixture's <code>{@link Container}</code>.
   * @return a fixture that manages the <code>Dialog</code> found.
   * @throws ComponentLookupException if a <code>Dialog</code> could not be found.
   * @throws ComponentLookupException if more than one <code>Dialog</code> is found.
   */
  public final DialogFixture dialog() {
    return new DialogFixture(robot, findByType(Dialog.class));
  }

  /**
   * Finds a <code>{@link Dialog}</code> owned by this fixture's <code>{@link Container}</code>, that matches the
   * specified search criteria.
   * @param matcher contains the search criteria for finding a <code>Dialog</code>.
   * @return a fixture that manages the <code>Dialog</code> found.
   * @throws ComponentLookupException if a <code>Dialog</code> that matches the given search criteria could not be found.
   * @throws ComponentLookupException if more than one <code>Dialog</code> that matches the given search criteria is found.
   */
  public final DialogFixture dialog(GenericTypeMatcher<? extends Dialog> matcher) {
    return new DialogFixture(robot, find(matcher));
  }

  /**
   * Finds a <code>{@link Dialog}</code> owned by this fixture's <code>{@link Container}</code>, which name matches the
   * specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>Dialog</code> found.
   * @throws ComponentLookupException if a <code>Dialog</code> having a matching name could not be found.
   * @throws ComponentLookupException if more than one <code>Dialog</code> having a matching name is found.
   */
  public final DialogFixture dialog(String name) {
    return new DialogFixture(robot, findByName(name, Dialog.class));
  }

  /**
   * Returns a <code>{@link JFileChooser}</code> found in this fixture's <code>{@link Container}</code>.
   * @return a fixture that manages the <code>JFileChooser</code> found.
   * @throws ComponentLookupException if a <code>JFileChooser</code> could not be found.
   * @throws ComponentLookupException if more <code>JFileChooser</code> could not be found.
   */
  public final JFileChooserFixture fileChooser() {
    return new JFileChooserFixture(robot, findByType(JFileChooser.class));
  }

  /**
   * Finds a <code>{@link JFileChooser}</code> in this fixture's <code>{@link Container}</code>, that matches the
   * specified search criteria.
   * @param matcher contains the search criteria for finding a <code>JFileChooser</code>.
   * @return a fixture that manages the <code>JFileChooser</code> found.
   * @throws ComponentLookupException if a <code>JFileChooser</code> that matches the given search criteria could not be found.
   * @throws ComponentLookupException if more than one <code>JFileChooser</code> that matches the given search criteria is found.
   */
  public final JFileChooserFixture fileChooser(GenericTypeMatcher<? extends JFileChooser> matcher) {
    return new JFileChooserFixture(robot, find(matcher));
  }

  /**
   * Finds a <code>{@link JFileChooser}</code> in this fixture's <code>{@link Container}</code>, which name matches
   * the specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JFileChooser</code> found.
   * @throws ComponentLookupException if a <code>JFileChooser</code> having a matching name could not be found.
   * @throws ComponentLookupException if more than one <code>JFileChooser</code> having a matching name is found.
   */
  public final JFileChooserFixture fileChooser(String name) {
    return new JFileChooserFixture(robot, findByName(name, JFileChooser.class));
  }

  /**
   * Returns a <code>{@link JLabel}</code> found in this fixture's <code>{@link Container}</code>.
   * @return a fixture that manages the <code>JLabel</code> found.
   * @throws ComponentLookupException if a <code>JLabel</code> could not be found.
   * @throws ComponentLookupException if more than one <code>JLabel</code> is found.
   */
  public final JLabelFixture label() {
    return new JLabelFixture(robot, findByType(JLabel.class));
  }

  /**
   * Finds a <code>{@link JLabel}</code> in this fixture's <code>{@link Container}</code>, that matches the
   * specified search criteria.
   * @param matcher contains the search criteria for finding a <code>JLabel</code>.
   * @return a fixture that manages the <code>JLabel</code> found.
   * @throws ComponentLookupException if a <code>JLabel</code> that matches the given search criteria could not be found.
   * @throws ComponentLookupException if more than one <code>JLabel</code> that matches the given search criteria is found.
   */
  public final JLabelFixture label(GenericTypeMatcher<? extends JLabel> matcher) {
    return new JLabelFixture(robot, find(matcher));
  }

  /**
   * Finds a <code>{@link JLabel}</code> in this fixture's <code>{@link Container}</code>, which name matches the
   * specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JLabel</code> found.
   * @throws ComponentLookupException if a <code>JLabel</code> having a matching name could not be found.
   * @throws ComponentLookupException if more than one <code>JLabel</code> having a matching name could is found.
   */
  public final JLabelFixture label(String name) {
    return new JLabelFixture(robot, findByName(name, JLabel.class));
  }

  /**
   * Returns a <code>{@link JList}</code> found in this fixture's <code>{@link Container}</code>.
   * @return a fixture that manages the <code>JList</code> found.
   * @throws ComponentLookupException if a <code>JList</code> could not be found.
   * @throws ComponentLookupException if more than one <code>JList</code> is found.
   */
  public final JListFixture list() {
    return new JListFixture(robot, findByType(JList.class));
  }

  /**
   * Finds a <code>{@link JList}</code> in this fixture's <code>{@link Container}</code>, that matches the
   * specified search criteria.
   * @param matcher contains the search criteria for finding a <code>JList</code>.
   * @return a fixture that manages the <code>JList</code> found.
   * @throws ComponentLookupException if a <code>JList</code> that matches the given search criteria could not be found.
   * @throws ComponentLookupException if more than one <code>JList</code> that matches the given search criteria is found.
   */
  public final JListFixture list(GenericTypeMatcher<? extends JList> matcher) {
    return new JListFixture(robot, find(matcher));
  }

  /**
   * Finds a <code>{@link JList}</code> in this fixture's <code>{@link Container}</code>, which name matches the
   * specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JList</code> found.
   * @throws ComponentLookupException if a <code>JList</code> having a matching name could not be found.
   * @throws ComponentLookupException if more than one <code>JList</code> having a matching name is found.
   */
  public final JListFixture list(String name) {
    return new JListFixture(robot, findByName(name, JList.class));
  }

  /**
   * Finds a <code>{@link JMenuItem}</code> in this fixture's <code>{@link Container}</code>, which path matches
   * the given one.
   * <p>
   * For example, if we are looking for the menu with text "New" contained under the menu with text "File", we can
   * simply call
   *
   * <pre>
   * JMenuItemFixture menuItem = container.&lt;strong&gt;findMenuItem(&quot;File&quot;, &quot;Menu&quot;)&lt;/strong&gt;;
   * </pre>
   *
   * </p>
   * @param path the path of the menu to find.
   * @return a fixture that manages the <code>JMenuItem</code> found.
   * @throws ComponentLookupException if a <code>JMenuItem</code> under the given path could not be found.
   * @throws AssertionError if the <code>Component</code> found under the given path is not a <code>JMenuItem</code>.
   */
  public final JMenuItemFixture menuItem(String... path) {
    ComponentMatcher m = new JMenuItemMatcher(join(path).with("|"));
    Component item = finder().find(target, m);
    assertThat(item).as(format(item)).isInstanceOf(JMenuItem.class);
    return new JMenuItemFixture(robot, (JMenuItem) item);
  }

  /**
   * Finds a <code>{@link JOptionPane}</code>.
   * @return a fixture that manages the <code>JOptionPane</code> found.
   * @throws ComponentLookupException if a <code>JOptionPane</code> could not be found.
   * @throws ComponentLookupException if more than one <code>JOptionPane</code> is found.
   */
  public final JOptionPaneFixture optionPane() {
    return new JOptionPaneFixture(robot);
  }

  /**
   * Returns a <code>{@link JPanel}</code> found in this fixture's <code>{@link Container}</code>.
   * @return a fixture that manages the <code>JPanel</code> found.
   * @throws ComponentLookupException if a <code>JPanel</code> could not be found.
   * @throws ComponentLookupException if more than one <code>JPanel</code> is found.
   */
  public final JPanelFixture panel() {
    return new JPanelFixture(robot, findByType(JPanel.class));
  }

  /**
   * Finds a <code>{@link JPanel}</code> in this fixture's <code>{@link Container}</code>, that matches the
   * specified search criteria.
   * @param matcher contains the search criteria for finding a <code>JPanel</code>.
   * @return a fixture that manages the <code>JPanel</code> found.
   * @throws ComponentLookupException if a <code>JPanel</code> that matches the given search criteria could not be found.
   * @throws ComponentLookupException if more than one <code>JPanel</code> that matches the given search criteria is found.
   */
  public final JPanelFixture panel(GenericTypeMatcher<? extends JPanel> matcher) {
    return new JPanelFixture(robot, find(matcher));
  }

  /**
   * Finds a <code>{@link JPanel}</code> in this fixture's <code>{@link Container}</code>, which name matches
   * the specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JPanel</code> found.
   * @throws ComponentLookupException if a <code>JPanel</code> having a matching name could not be found.
   */
  public final JPanelFixture panel(String name) {
    return new JPanelFixture(robot, findByName(name, JPanel.class));
  }

  /**
   * Returns a <code>{@link JRadioButton}</code> found in this fixture's <code>{@link Container}</code>.
   * @return a fixture that manages the <code>JRadioButton</code> found.
   * @throws ComponentLookupException if a <code>JRadioButton</code> could not be found.
   * @throws ComponentLookupException if more than one <code>JRadioButton</code> is found.
   */
  public final JRadioButtonFixture radioButton() {
    return new JRadioButtonFixture(robot, findByType(JRadioButton.class));
  }

  /**
   * Finds a <code>{@link JRadioButton}</code> in this fixture's <code>{@link Container}</code>, that matches the
   * specified search criteria.
   * @param matcher contains the search criteria for finding a <code>JRadioButton</code>.
   * @return a fixture that manages the <code>JRadioButton</code> found.
   * @throws ComponentLookupException if a <code>JRadioButton</code> that matches the given search criteria could not be found.
   * @throws ComponentLookupException if more than one <code>JRadioButton</code> that matches the given search criteria is found.
   */
  public final JRadioButtonFixture radioButton(GenericTypeMatcher<? extends JRadioButton> matcher) {
    return new JRadioButtonFixture(robot, find(matcher));
  }

  /**
   * Finds a <code>{@link JRadioButton}</code> in this fixture's <code>{@link Container}</code>, which name matches
   * the specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JRadioButton</code> found.
   * @throws ComponentLookupException if a <code>JRadioButton</code> having a matching name could not be found.
   * @throws ComponentLookupException if more than one <code>JRadioButton</code> having a matching name is found.
   */
  public final JRadioButtonFixture radioButton(String name) {
    return new JRadioButtonFixture(robot, findByName(name, JRadioButton.class));
  }

  /**
   * Returns a <code>{@link JScrollBar}</code> found in this fixture's <code>{@link Container}</code>.
   * @return a fixture that manages the <code>JScrollBar</code> found.
   * @throws ComponentLookupException if a <code>JScrollBar</code> could not be found.
   * @throws ComponentLookupException if more than one <code>JScrollBar</code> is found.
   */
  public final JScrollBarFixture scrollBar() {
    return new JScrollBarFixture(robot, findByType(JScrollBar.class));
  }

  /**
   * Finds a <code>{@link JScrollBar}</code> in this fixture's <code>{@link Container}</code>, that matches the
   * specified search criteria.
   * @param matcher contains the search criteria for finding a <code>JScrollBar</code>.
   * @return a fixture that manages the <code>JScrollBar</code> found.
   * @throws ComponentLookupException if a <code>JScrollBar</code> that matches the given search criteria could not be found.
   * @throws ComponentLookupException if more than one <code>JScrollBar</code> that matches the given search criteria is found.
   */
  public final JScrollBarFixture scrollBar(GenericTypeMatcher<? extends JScrollBar> matcher) {
    return new JScrollBarFixture(robot, find(matcher));
  }

  /**
   * Finds a <code>{@link JScrollBar}</code> in this fixture's <code>{@link Container}</code>, which name matches the
   * specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JScrollBar</code> found.
   * @throws ComponentLookupException if a <code>JScrollBar</code> having a matching name could not be found.
   * @throws ComponentLookupException if more than one <code>JScrollBar</code> having a matching name is found.
   */
  public final JScrollBarFixture scrollBar(String name) {
    return new JScrollBarFixture(robot, findByName(name, JScrollBar.class));
  }

  /**
   * Returns a <code>{@link JScrollPane}</code> found in this fixture's <code>{@link Container}</code>.
   * @return a fixture that manages the <code>JScrollPane</code> found.
   * @throws ComponentLookupException if a <code>JScrollPane</code> could not be found.
   * @throws ComponentLookupException if more than one <code>JScrollPane</code> is found.
   */
  public final JScrollPaneFixture scrollPane() {
    return new JScrollPaneFixture(robot, findByType(JScrollPane.class));
  }

  /**
   * Finds a <code>{@link JScrollPane}</code> in this fixture's <code>{@link Container}</code>, that matches the
   * specified search criteria.
   * @param matcher contains the search criteria for finding a <code>JScrollPane</code>.
   * @return a fixture that manages the <code>JScrollPane</code> found.
   * @throws ComponentLookupException if a <code>JScrollPane</code> that matches the given search criteria could not be found.
   * @throws ComponentLookupException if more than one <code>JScrollPane</code> that matches the given search criteria is found.
   */
  public final JScrollPaneFixture scrollPane(GenericTypeMatcher<? extends JScrollPane> matcher) {
    return new JScrollPaneFixture(robot, find(matcher));
  }

  /**
   * Finds a <code>{@link JScrollPane}</code> in this fixture's <code>{@link Container}</code>, which name matches the
   * specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JScrollPane</code> found.
   * @throws ComponentLookupException if a <code>JScrollPane</code> having a matching name could not be found.
   * @throws ComponentLookupException if more than one <code>JScrollPane</code> having a matching name is found.
   */
  public final JScrollPaneFixture scrollPane(String name) {
    return new JScrollPaneFixture(robot, findByName(name, JScrollPane.class));
  }

  /**
   * Returns a <code>{@link JSlider}</code> found in this fixture's <code>{@link Container}</code>.
   * @return a fixture that manages the <code>JSlider</code> found.
   * @throws ComponentLookupException if a <code>JSlider</code> could not be found.
   * @throws ComponentLookupException if more than one <code>JSlider</code> is found.
   */
  public final JSliderFixture slider() {
    return new JSliderFixture(robot, findByType(JSlider.class));
  }

  /**
   * Finds a <code>{@link JSlider}</code> in this fixture's <code>{@link Container}</code>, that matches the
   * specified search criteria.
   * @param matcher contains the search criteria for finding a <code>JSlider</code>.
   * @return a fixture that manages the <code>JSlider</code> found.
   * @throws ComponentLookupException if a <code>JSlider</code> that matches the given search criteria could not be found.
   * @throws ComponentLookupException if more than one <code>JSlider</code> that matches the given search criteria is found.
   */
  public final JSliderFixture slider(GenericTypeMatcher<? extends JSlider> matcher) {
    return new JSliderFixture(robot, find(matcher));
  }

  /**
   * Finds a <code>{@link JSlider}</code> in this fixture's <code>{@link Container}</code>, which name matches the
   * specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JSlider</code> found.
   * @throws ComponentLookupException if a <code>JSlider</code> having a matching name could not be found.
   * @throws ComponentLookupException if more than one <code>JSlider</code> having a matching name is found.
   */
  public final JSliderFixture slider(String name) {
    return new JSliderFixture(robot, findByName(name, JSlider.class));
  }

  /**
   * Returns a <code>{@link JSpinner}</code> found in this fixture's <code>{@link Container}</code>.
   * @return a fixture that manages the <code>JSpinner</code> found.
   * @throws ComponentLookupException if a <code>JSpinner</code> could not be found.
   * @throws ComponentLookupException if more than one <code>JSpinner</code> is found.
   */
  public final JSpinnerFixture spinner() {
    return new JSpinnerFixture(robot, findByType(JSpinner.class));
  }

  /**
   * Finds a <code>{@link JSpinner}</code> in this fixture's <code>{@link Container}</code>, that matches the
   * specified search criteria.
   * @param matcher contains the search criteria for finding a <code>JSpinner</code>.
   * @return a fixture that manages the <code>JSpinner</code> found.
   * @throws ComponentLookupException if a <code>JSpinner</code> that matches the given search criteria could not be found.
   * @throws ComponentLookupException if more than one <code>JSpinner</code> that matches the given search criteria is found.
   */
  public final JSpinnerFixture spinner(GenericTypeMatcher<? extends JSpinner> matcher) {
    return new JSpinnerFixture(robot, find(matcher));
  }

  /**
   * Finds a <code>{@link JSpinner}</code> in this fixture's <code>{@link Container}</code>, which name matches the
   * specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JSpinner</code> found.
   * @throws ComponentLookupException if a <code>JSpinner</code> having a matching name could not be found.
   * @throws ComponentLookupException if more than one <code>JSpinner</code> having a matching name is found.
   */
  public final JSpinnerFixture spinner(String name) {
    return new JSpinnerFixture(robot, findByName(name, JSpinner.class));
  }

  /**
   * Returns the <code>{@link JSplitPane}</code> found in this fixture's <code>{@link Container}</code>.
   * @return a fixture that manages the <code>JSplitPane</code> found.
   * @throws ComponentLookupException if a <code>JSplitPane</code> could not be found.
   * @throws ComponentLookupException if more than one <code>JSplitPane</code> is found.
   */
  public final JSplitPaneFixture splitPane() {
    return new JSplitPaneFixture(robot, findByType(JSplitPane.class));
  }

  /**
   * Finds a <code>{@link JSplitPane}</code> in this fixture's <code>{@link Container}</code>, that matches the
   * specified search criteria.
   * @param matcher contains the search criteria for finding a <code>JSplitPane</code>.
   * @return a fixture that manages the <code>JSplitPane</code> found.
   * @throws ComponentLookupException if a <code>JSplitPane</code> that matches the given search criteria could not be found.
   * @throws ComponentLookupException if more than one <code>JSplitPane</code> that matches the given search criteria is found.
   */
  public final JSplitPaneFixture splitPane(GenericTypeMatcher<? extends JSplitPane> matcher) {
    return new JSplitPaneFixture(robot, find(matcher));
  }

  /**
   * Finds a <code>{@link JSplitPane}</code> in this fixture's <code>{@link Container}</code>, which name matches
   * the specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JSplitPane</code> found.
   * @throws ComponentLookupException if a <code>JSplitPane</code> having a matching name could not be found.
   * @throws ComponentLookupException if more than one <code>JSplitPane</code> having a matching name is found.
   */
  public final JSplitPaneFixture splitPane(String name) {
    return new JSplitPaneFixture(robot, findByName(name, JSplitPane.class));
  }

  /**
   * Returns a <code>{@link JTabbedPane}</code> found in this fixture's <code>{@link Container}</code>.
   * @return a fixture that manages the <code>JTabbedPane</code> found.
   * @throws ComponentLookupException if a <code>JTabbedPane</code> could not be found.
   * @throws ComponentLookupException if more than one <code>JTabbedPane</code> is found.
   */
  public final JTabbedPaneFixture tabbedPane() {
    return new JTabbedPaneFixture(robot, findByType(JTabbedPane.class));
  }

  /**
   * Finds a <code>{@link JTabbedPane}</code> in this fixture's <code>{@link Container}</code>, that matches the
   * specified search criteria.
   * @param matcher contains the search criteria for finding a <code>JTabbedPane</code>.
   * @return a fixture that manages the <code>JTabbedPane</code> found.
   * @throws ComponentLookupException if a <code>JTabbedPane</code> that matches the given search criteria could not be found.
   * @throws ComponentLookupException if more than one <code>JTabbedPane</code> that matches the given search criteria is found.
   */
  public final JTabbedPaneFixture tabbedPane(GenericTypeMatcher<? extends JTabbedPane> matcher) {
    return new JTabbedPaneFixture(robot, find(matcher));
  }

  /**
   * Finds a <code>{@link JTabbedPane}</code> in this fixture's <code>{@link Container}</code>, which name matches
   * the specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JTabbedPane</code> found.
   * @throws ComponentLookupException if a <code>JTabbedPane</code> having a matching name could not be found.
   * @throws ComponentLookupException if more than one <code>JTabbedPane</code> having a matching name is found.
   */
  public final JTabbedPaneFixture tabbedPane(String name) {
    return new JTabbedPaneFixture(robot, findByName(name, JTabbedPane.class));
  }

  /**
   * Returns a <code>{@link JTable}</code> found in this fixture's <code>{@link Container}</code>.
   * @return a fixture that manages the <code>JTable</code> found.
   * @throws ComponentLookupException if a <code>JTable</code> having a matching name could not be found.
   * @throws ComponentLookupException if more than one <code>JTable</code> having a matching name is found.
   */
  public final JTableFixture table() {
    return new JTableFixture(robot, findByType(JTable.class));
  }

  /**
   * Finds a <code>{@link JTable}</code> in this fixture's <code>{@link Container}</code>, that matches the
   * specified search criteria.
   * @param matcher contains the search criteria for finding a <code>JTable</code>.
   * @return a fixture that manages the <code>JTable</code> found.
   * @throws ComponentLookupException if a <code>JTable</code> that matches the given search criteria could not be found.
   * @throws ComponentLookupException if more than one <code>JTable</code> that matches the given search criteria is found.
   */
  public final JTableFixture table(GenericTypeMatcher<? extends JTable> matcher) {
    return new JTableFixture(robot, find(matcher));
  }

  /**
   * Finds a <code>{@link JTable}</code> in this fixture's <code>{@link Container}</code>, which name matches the
   * specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JTable</code> found.
   * @throws ComponentLookupException if a <code>JTable</code> having a matching name could not be found.
   * @throws ComponentLookupException if more than one <code>JTable</code> having a matching name is found.
   */
  public final JTableFixture table(String name) {
    return new JTableFixture(robot, findByName(name, JTable.class));
  }

  /**
   * Returns a <code>{@link JTextComponent}</code> found in this fixture's <code>{@link Container}</code>.
   * @return a fixture that manages the <code>JTextComponent</code> found.
   * @throws ComponentLookupException if a <code>JTextComponent</code> having a matching name could not be found.
   * @throws ComponentLookupException if more than one <code>JTextComponent</code> having a matching name is found.
   */
  public final JTextComponentFixture textBox() {
    return new JTextComponentFixture(robot, findByType(JTextComponent.class));
  }

  /**
   * Finds a <code>{@link JTextComponent}</code> in this fixture's <code>{@link Container}</code> managed by this
   * fixture, that matches the specified search criteria.
   * @param matcher contains the search criteria for finding a <code>JTextComponent</code>.
   * @return a fixture that manages the <code>JTextComponent</code> found.
   * @throws ComponentLookupException if a <code>JTextComponent</code> that matches the given search criteria could not be found.
   * @throws ComponentLookupException if more than one <code>JTextComponent</code> that matches the given search criteria is found.
   */
  public final JTextComponentFixture textBox(GenericTypeMatcher<? extends JTextComponent> matcher) {
    return new JTextComponentFixture(robot, find(matcher));
  }

  /**
   * Finds a <code>{@link JTextComponent}</code> in this fixture's <code>{@link Container}</code> managed by this
   * fixture, which name matches the specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JTextComponent</code> found.
   * @throws ComponentLookupException if a <code>JTextComponent</code> having a matching name could not be found.
   * @throws ComponentLookupException if more than one <code>JTextComponent</code> having a matching name is found.
   */
  public final JTextComponentFixture textBox(String name) {
    return new JTextComponentFixture(robot, findByName(name, JTextComponent.class));
  }

  /**
   * Returns a <code>{@link JToggleButton}</code> found in this fixture's <code>{@link Container}</code>.
   * @return a fixture that manages the <code>JToggleButton</code> found.
   * @throws ComponentLookupException if a <code>JToggleButton</code> could not be found.
   * @throws ComponentLookupException if more than one <code>JToggleButton</code> is found.
   */
  public final JToggleButtonFixture toggleButton() {
    return new JToggleButtonFixture(robot, findByType(JToggleButton.class));
  }

  /**
   * Finds a <code>{@link JToggleButton}</code> in this fixture's <code>{@link Container}</code>, that matches the
   * specified search criteria.
   * @param matcher contains the search criteria for finding a <code>JToggleButton</code>.
   * @return a fixture that manages the <code>JToggleButton</code> found.
   * @throws ComponentLookupException if a <code>JToggleButton</code> that matches the given search criteria could not be found.
   * @throws ComponentLookupException if more than one <code>JToggleButton</code> that matches the given search criteria is found.
   */
  public final JToggleButtonFixture toggleButton(GenericTypeMatcher<? extends JToggleButton> matcher) {
    return new JToggleButtonFixture(robot, find(matcher));
  }

  /**
   * Finds a <code>{@link JToggleButton}</code> in this fixture's <code>{@link Container}</code>, which name matches
   * the specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JToggleButton</code> found.
   * @throws ComponentLookupException if a <code>JToggleButton</code> having a matching name could not be found.
   * @throws ComponentLookupException if more than one <code>JToggleButton</code> having a matching name is found.
   */
  public final JToggleButtonFixture toggleButton(String name) {
    return new JToggleButtonFixture(robot, findByName(name, JToggleButton.class));
  }

  /**
   * Returns a <code>{@link JToolBar}</code> found in this fixture's <code>{@link Container}</code>.
   * @return a fixture that manages the <code>JToolBar</code> found.
   * @throws ComponentLookupException if a <code>JToolBar</code> having a matching name could not be found.
   * @throws ComponentLookupException if more than one <code>JToolBar</code> having a matching name could is found.
   */
  public final JToolBarFixture toolBar() {
    return new JToolBarFixture(robot, findByType(JToolBar.class));
  }

  /**
   * Finds a <code>{@link JToolBar}</code> in this fixture's <code>{@link Container}</code>, that matches the
   * specified search criteria.
   * @param matcher contains the search criteria for finding a <code>JToolBar</code>.
   * @return a fixture that manages the <code>JToolBar</code> found.
   * @throws ComponentLookupException if a <code>JToolBar</code> that matches the given search criteria could not be found.
   * @throws ComponentLookupException if more than one <code>JToolBar</code> that matches the given search criteria is found.
   */
  public final JToolBarFixture toolBar(GenericTypeMatcher<? extends JToolBar> matcher) {
    return new JToolBarFixture(robot, find(matcher));
  }

  /**
   * Finds a <code>{@link JToolBar}</code> in this fixture's <code>{@link Container}</code>, which name matches the
   * specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JToolBar</code> found.
   * @throws ComponentLookupException if a <code>JToolBar</code> having a matching name could not be found.
   * @throws ComponentLookupException if more than one <code>JToolBar</code> having a matching name is found.
   */
  public final JToolBarFixture toolBar(String name) {
    return new JToolBarFixture(robot, findByName(name, JToolBar.class));
  }

  /**
   * Returns a <code>{@link JTree}</code> found in this fixture's <code>{@link Container}</code>.
   * @return a fixture that manages the <code>JTree</code> found.
   * @throws ComponentLookupException if a <code>JTree</code> having a matching name could not be found.
   * @throws ComponentLookupException if more than one <code>JTree</code> having a matching name is found.
   */
  public final JTreeFixture tree() {
    return new JTreeFixture(robot, findByType(JTree.class));
  }

  /**
   * Finds a <code>{@link JTree}</code> in this fixture's <code>{@link Container}</code>, that matches the
   * specified search criteria.
   * @param matcher contains the search criteria for finding a <code>JTree</code>.
   * @return a fixture that manages the <code>JTree</code> found.
   * @throws ComponentLookupException if a <code>JTree</code> that matches the given search criteria could not be found.
   * @throws ComponentLookupException if more than one <code>JTree</code> that matches the given search criteria is found.
   */
  public final JTreeFixture tree(GenericTypeMatcher<? extends JTree> matcher) {
    return new JTreeFixture(robot, find(matcher));
  }

  /**
   * Finds a <code>{@link JTree}</code> in this fixture's <code>{@link Container}</code>, which name matches the
   * specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JTree</code> found.
   * @throws ComponentLookupException if a <code>JTree</code> having a matching name could not be found.
   * @throws ComponentLookupException if more than one <code>JTree</code> having a matching name is found.
   */
  public final JTreeFixture tree(String name) {
    return new JTreeFixture(robot, findByName(name, JTree.class));
  }

  protected final <C extends Component> C findByType(Class<C> type) {
    return finder().findByType(target, type);
  }
}
