/*
 * Created on Oct 24, 2007
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
package org.fest.swing.util;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;

import static org.fest.util.Collections.list;

/**
 * Understands utility methods related to collection of <code>{@link Component}</code>s.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class ComponentCollections {

  public static final Collection<Component> EMPTY = empty();
  
  /**
   * Returns an empty collection of <code>{@link Component}</code>s.
   * @return an empty collection of components;
   */
  public static Collection<Component> empty() {
    return new ArrayList<Component>();
  }

  /**
   * Returns a collection containing the given <code>{@link Component}</code>s.
   * @param components the components to store in a collection.
   * @return a collection containing the given components.
   */
  public static Collection<Component> components(Component...components) {
    return list(components);
  }
  
  private ComponentCollections() {}
}
