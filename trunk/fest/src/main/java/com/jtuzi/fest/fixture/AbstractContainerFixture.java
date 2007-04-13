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
package com.jtuzi.fest.fixture;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

import com.jtuzi.fest.AbbotFixture;

import abbot.finder.Matcher;
import abbot.finder.matchers.JMenuItemMatcher;

import static com.jtuzi.fest.assertions.Assertions.assertThat;
import static com.jtuzi.fest.util.Strings.join;


/**
 * @param <T> the type of container handled by this fixture. 
 *
 * @author Alex Ruiz
 */
public abstract class AbstractContainerFixture<T extends Container> extends AbstractComponentFixture<T> {

  /**
   * Creates a new </code>{@link AbstractContainerFixture}</code>.
   * @param abbot performs simulation of user events on a <code>Container</code>.
   * @param type the type of <code>Container</code> to find using the given <code>AbbotFixture</code>.
   * @see AbbotFixture#findByName(String, Class)
   */
  public AbstractContainerFixture(AbbotFixture abbot, Class<? extends T> type) {
    super(abbot, abbot.findByType(type));
  }

  /**
   * Creates a new </code>{@link AbstractContainerFixture}</code>.
   * @param abbot performs simulation of user events on a <code>Component</code>.
   * @param name the name of Container <code>Component</code> to find using the given <code>AbbotFixture</code>.
   * @param type the type of <code>Container</code> to find using the given <code>AbbotFixture</code>.
   * @see AbbotFixture#findByName(String, Class)
   */
  public AbstractContainerFixture(AbbotFixture abbot, String name, Class<? extends T> type) {
    super(abbot, abbot.findByName(name, type));
  }
  
  /**
   * Creates a new </code>{@link AbstractContainerFixture}</code>.
   * @param abbot performs simulation of user events on the given component.
   * @param target the component under test.
   */
  public AbstractContainerFixture(AbbotFixture abbot, T target) {
    super(abbot, target);
  }

  /**
   * Finds a <code>{@link JLabel}</code> in the target container having the given name.
   * @param name the name to match.
   * @return a fixture wrapping the found component.
   */
  public final JLabelFixture findLabel(String name) {
    return new JLabelFixture(abbot, abbot.findByName(target, name, JLabel.class));
  }

  /**
   * Finds a <code>{@link JButton}</code> in the target container having the given name.
   * @param name the name to match.
   * @return a fixture wrapping the found component.
   */
  public final JButtonFixture findButton(String name) {
    return new JButtonFixture(abbot, abbot.findByName(target, name, JButton.class));
  }

  /**
   * Finds a <code>{@link Dialog}</code> in the target container having the given name.
   * @param name the name to match.
   * @return a fixture wrapping the found component.
   */
  public final DialogFixture findDialog(String name) {
    return new DialogFixture(abbot, abbot.findByName(target, name, Dialog.class));
  }

  /**
   * Finds a <code>{@link JMenuItem}</code> in the target container having the given name.
   * @param name the name to match.
   * @return a fixture wrapping the found component.
   */
  public final JMenuItemFixture findMenuItem(String name) {
    return new JMenuItemFixture(abbot, abbot.findByName(target, name, JMenuItem.class));
  }

  /**
   * Finds a <code>{@link JMenuItem}</code> in the target container, which path matches the given one.
   * @param path the path of the menu to find. For example, if we are looking for the submenu "New" contained in the
   *          menu "File", we shoul call <code>findMenuItem("File", "Menu")</code>.
   * @return a fixture wrapping the found component.
   */
  public final JMenuItemFixture findMenuItem(String...path) {
    Matcher m = new JMenuItemMatcher(join("|", path));
    Component item = abbot.find(target, m);
    assertThat(item).isInstanceOf(JMenuItem.class);
    return new JMenuItemFixture(abbot, (JMenuItem)item);
  }

  /**
   * Finds a <code>{@link JOptionPane}</code> in the target container
   * @return a fixture wrapping the found component.
   */
  public final JOptionPaneFixture findOptionPane() {
    return new JOptionPaneFixture(abbot);
  }

  /**
   * Finds a <code>{@link JTextComponent}</code> in the target container having the given name.
   * @param name the name to match.
   * @return a fixture wrapping the found component.
   */
  public final JTextComponentFixture findTextComponent(String name) {
    return new JTextComponentFixture(abbot, abbot.findByName(target, name, JTextComponent.class));
  }
}
