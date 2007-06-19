/*
 * Created on May 14, 2007
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
package org.fest.swing;

import java.awt.Component;
import java.awt.Container;

import abbot.finder.BasicFinder;
import abbot.finder.Hierarchy;

/**
 * Understands GUI component lookup.
 *
 * @author Alex Ruiz
 */
public class ComponentFinder {

  private final abbot.finder.ComponentFinder finder;

  /**
   * Creates a new </code>{@link ComponentFinder}</code>.
   * @param hierarchy provides access to the components in the AWT hierarchy. 
   */
  ComponentFinder(Hierarchy hierarchy) {
    finder = new BasicFinder(hierarchy);
  }

  /**
   * Finds a <code>{@link Component}</code> by type.
   * @param <T> the parameterized type of the component to find.
   * @param type the type of the component to find.
   * @return the found component.
   * @throws ComponentLookupException if a component of the given type could not be found.
   */
  public <T extends Component> T findByType(Class<T> type) {
    return type.cast(find(new TypeMatcher(type)));
  }

  /**
   * Finds a <code>{@link Component}</code> by type in the hierarchy under the given root.
   * @param <T> the parameterized type of the component to find.
   * @param root the root used as the starting point of the search.
   * @param type the type of the component to find.
   * @return the found component.
   * @throws ComponentLookupException if a component of the given type could not be found.
   */
  public <T extends Component> T findByType(Container root, Class<T> type) {
    return type.cast(find(root, new TypeMatcher(type)));
  }

  /**
   * Finds a <code>{@link Component}</code> by name and type.
   * @param <T> the parameterized type of the component to find.
   * @param name the name of the component to find.
   * @param type the type of the component to find.
   * @return the found component.
   * @throws ComponentLookupException if a component with the given name and of the given type could not be found.
   */
  public <T extends Component> T findByName(String name, Class<T> type) {
    return type.cast(findByName(name));
  }
  
  /**
   * Finds a <code>{@link Component}</code> by name.
   * @param name the name of the component to find.
   * @return the found component.
   * @throws ComponentLookupException if a component with the given name could not be found.
   */
  public Component findByName(String name) {
    return find(new NameMatcher(name));
  }

  /**
   * Finds a <code>{@link Component}</code> using the given <code>{@link ComponentMatcher}</code>.
   * @param m the matcher to use to find the component of interest.
   * @return the found component.
   * @throws ComponentLookupException if a component matching the given criteria could not be found.
   */
  public Component find(ComponentMatcher m) {
    try {
      return finder.find(m);
    } catch (Exception e) {
      throw new ComponentLookupException(e);
    }
  }
  
  /**
   * Finds a <code>{@link Component}</code> by name and type in the hierarchy under the given root.
   * @param <T> the parameterized type of the component to find.
   * @param root the root used as the starting point of the search.
   * @param name the name of the component to find.
   * @param type the type of the component to find.
   * @return the found component.
   * @throws ComponentLookupException if a component with the given name and of the given type could not be found.
   */
  public <T extends Component> T findByName(Container root, String name, Class<T> type) {
    return type.cast(findByName(root, name));
  }
  
  /**
   * Finds a <code>{@link Component}</code> by name in the hierarchy under the given root.
   * @param root the root used as the starting point of the search.
   * @param name the name of the component to find.
   * @return the found component.
   * @throws ComponentLookupException if a component with the given name could not be found.
   */
  public Component findByName(Container root, String name) {
    return find(root, new NameMatcher(name));
  }

  /**
   * Finds a <code>{@link Component}</code> using the given <code>{@link ComponentMatcher}</code> in the hierarchy
   * under the given root.
   * @param root the root used as the starting point of the search.
   * @param m the matcher to use to find the component.
   * @return the found component.
   * @throws ComponentLookupException if a component matching the given criteria could not be found.
   */
  public Component find(Container root, ComponentMatcher m) {
    try {
      return finder.find(root, m);
    } catch (Exception e) {
      throw new ComponentLookupException(e);
    }
  }
}
