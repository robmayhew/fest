/*
 * Created on May 14, 2007
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
package org.fest.swing.core;

import java.awt.Component;
import java.awt.Container;

import abbot.finder.AWTHierarchy;
import abbot.finder.Hierarchy;
import abbot.finder.TestHierarchy;

import org.fest.swing.exception.ComponentLookupException;

/**
 * Understands GUI <code>{@link java.awt.Component}</code> lookup.
 * 
 * @author Alex Ruiz
 */
public class ComponentFinder {

  private final ComponentPrinter printer;
  private final BasicComponentFinder finder;

  /**
   * Creates a new <code>{@link ComponentFinder}</code> with a new AWT hierarchy. <code>{@link Component}</code>s
   * created before the created <code>{@link ComponentFinder}</code> cannot be accessed by the created
   * <code>{@link ComponentFinder}</code>.
   * @return the created finder.
   */
  public static ComponentFinder finderWithNewAwtHierarchy() {
    return new ComponentFinder(new TestHierarchy());
  }

  /**
   * Creates a new <code>{@link ComponentFinder}</code> that has access to all the GUI components in the AWT
   * hierarchy.
   * @return the created finder.
   */
  public static ComponentFinder finderWithCurrentAwtHierarchy() {
    return new ComponentFinder(new AWTHierarchy());
  }

  /**
   * Creates a new <code>{@link ComponentFinder}</code>.
   * @param hierarchy provides access to the components in the AWT hierarchy.
   */
  ComponentFinder(Hierarchy hierarchy) {
    printer = new ComponentPrinter(hierarchy);
    finder = new BasicComponentFinder(printer);
  }

  /**
   * Returns the <code>{@link ComponentPrinter}</code> in this finder.
   * @return the <code>ComponentPrinter</code> in this finder.
   */
  public final ComponentPrinter printer() { return printer; }
  
  /**
   * Finds a <code>{@link Component}</code> by type. For example:
   * 
   * <pre>
   * JTextField textbox = finder.findByType(JTextField.class);
   * </pre>
   * 
   * @param <T> the parameterized type of the component to find.
   * @param type the type of the component to find.
   * @return the found component.
   * @throws ComponentLookupException if a matching component could not be found.
   */
  public final <T extends Component> T findByType(Class<T> type) {
    return type.cast(find(new TypeMatcher(type)));
  }

  /**
   * <p>
   * Finds a <code>{@link Component}</code> by type in the hierarchy under the given root.
   * </p>
   * <p>
   * Let's assume we have the following <code>{@link javax.swing.JFrame}</code> containing a
   * <code>{@link javax.swing.JTextField}</code>:
   * 
   * <pre>
   * JFrame myFrame = new JFrame();
   * myFrame.add(new JTextField());
   * </pre>
   * 
   * </p>
   * <p>
   * If we want to get a reference to the <code>{@link javax.swing.JTextField}</code> in that particular
   * <code>{@link javax.swing.JFrame}</code> without going through the whole AWT component hierarchy, we could simply
   * specify:
   * 
   * <pre>
   * JTextField textbox = finder.findByType(myFrame, JTextField.class);
   * </pre>
   * 
   * </p>
   * @param <T> the parameterized type of the component to find.
   * @param root the root used as the starting point of the search.
   * @param type the type of the component to find.
   * @return the found component.
   * @throws ComponentLookupException if a matching component could not be found.
   */
  public final <T extends Component> T findByType(Container root, Class<T> type) {
    return type.cast(find(root, new TypeMatcher(type)));
  }

  /**
   * Finds a <code>{@link Component}</code> by name and type.
   * @param <T> the parameterized type of the component to find.
   * @param name the name of the component to find.
   * @param type the type of the component to find.
   * @return the found component.
   * @throws ComponentLookupException if a matching component could not be found.
   * @see #findByName(String)
   */
  public final <T extends Component> T findByName(String name, Class<T> type) {
    Component found = find(new NameAndTypeMatcher(name, type));
    return type.cast(found);
  }

  /**
   * <p>
   * Finds a <code>{@link Component}</code> by name.
   * </p>
   * <p>
   * Let's assume we have the <code>{@link javax.swing.JTextField}</code> with name "myTextBox";
   * 
   * <pre>
   * JTextField textbox = new JTextField();
   * textBox.setName(&quot;myTextBox&quot;);
   * </pre>
   * 
   * </p>
   * <p>
   * To get a reference to this <code>{@link javax.swing.JTextField}</code> by its name, we can specify:
   * 
   * <pre>
   * JTextField textBox = (JTextField) finder.findByName(&quot;myTextBox&quot;);
   * </pre>
   * 
   * </p>
   * <p>
   * Please note that you need to cast the result of the lookup to the right type. To avoid casting, please use
   * <code>{@link #findByName(String, Class)}</code> or <code>{@link #findByName(Container, String, Class)}</code>.
   * </p>
   * @param name the name of the component to find.
   * @return the found component.
   * @throws ComponentLookupException if a matching component could not be found.
   */
  public final Component findByName(String name) {
    return find(new NameMatcher(name));
  }

  /**
   * Finds a <code>{@link Component}</code> using the given <code>{@link GenericTypeMatcher}</code>.
   * @param <T> the type of component the given matcher can handle.
   * @param m the matcher to use to find the component of interest.
   * @return the found component.
   * @throws ComponentLookupException if a component matching the given criteria could not be found.
   */
  @SuppressWarnings("unchecked") 
  public final <T extends Component> T find(GenericTypeMatcher<T> m) {
    return (T)find((ComponentMatcher)m);
  }

  /**
   * Finds a <code>{@link Component}</code> using the given <code>{@link ComponentMatcher}</code>.
   * @param m the matcher to use to find the component of interest.
   * @return the found component.
   * @throws ComponentLookupException if a component matching the given criteria could not be found.
   */
  public final Component find(ComponentMatcher m) {
    return finder.find(m);
  }
  
  /**
   * Finds a <code>{@link Component}</code> by name and type in the hierarchy under the given root.
   * @param <T> the parameterized type of the component to find.
   * @param root the root used as the starting point of the search.
   * @param name the name of the component to find.
   * @param type the type of the component to find.
   * @return the found component.
   * @throws ComponentLookupException if a matching component could not be found.
   * @see #findByName(String)
   * @see #findByType(Container, Class)
   */
  public final <T extends Component> T findByName(Container root, String name, Class<T> type) {
    Component found = find(root, new NameAndTypeMatcher(name, type));
    return type.cast(found);
  }

  /**
   * Finds a <code>{@link Component}</code> by name in the hierarchy under the given root.
   * @param root the root used as the starting point of the search.
   * @param name the name of the component to find.
   * @return the found component.
   * @throws ComponentLookupException if a matching component could not be found.
   * @see #findByName(String)
   * @see #findByName(Container, String, Class)
   */
  public final Component findByName(Container root, String name) {
    return find(root, new NameMatcher(name));
  }

  /**
   * Finds a <code>{@link Component}</code> using the given <code>{@link GenericTypeMatcher}</code> in the hierarchy
   * under the given root.
   * @param <T> the type of component the given matcher can handle.
   * @param root the root used as the starting point of the search.
   * @param m the matcher to use to find the component.
   * @return the found component.
   * @throws ComponentLookupException if a matching component could not be found.
   */
  @SuppressWarnings("unchecked") 
  public final <T extends Component> T find(Container root, GenericTypeMatcher<T> m) {
    return (T)find(root, (ComponentMatcher)m);
  }

  /**
   * Finds a <code>{@link Component}</code> using the given <code>{@link ComponentMatcher}</code> in the hierarchy
   * under the given root.
   * @param root the root used as the starting point of the search.
   * @param m the matcher to use to find the component.
   * @return the found component.
   * @throws ComponentLookupException if a matching component could not be found.
   */
  public final Component find(Container root, ComponentMatcher m) {
    return finder.find(root, m);
  }
}
