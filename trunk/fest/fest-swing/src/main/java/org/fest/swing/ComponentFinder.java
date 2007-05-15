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
import abbot.finder.Matcher;
import abbot.finder.matchers.ClassMatcher;
import abbot.finder.matchers.NameMatcher;

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

  public <T extends Component> T findByType(Class<T> type) {
    return type.cast(find(new ClassMatcher(type)));
  }

  public <T extends Component> T findByType(Container root, Class<T> type) {
    return type.cast(find(root, new ClassMatcher(type)));
  }

  public <T extends Component> T findByName(String name, Class<T> type) {
    return type.cast(findByName(name));
  }
  
  public Component findByName(String name) {
    return find(new NameMatcher(name));
  }

  public Component find(Matcher m) {
    try {
      return finder.find(m);
    } catch (Exception e) {
      throw new ComponentLookupException(e);
    }
  }
  
  public <T extends Component> T findByName(Container root, String name, Class<T> type) {
    return type.cast(findByName(root, name));
  }
  
  public Component findByName(Container root, String name) {
    return find(root, new NameMatcher(name));
  }

  public Component find(Container root, Matcher m) {
    try {
      return finder.find(root, m);
    } catch (Exception e) {
      throw new ComponentLookupException(e);
    }
  }

}
