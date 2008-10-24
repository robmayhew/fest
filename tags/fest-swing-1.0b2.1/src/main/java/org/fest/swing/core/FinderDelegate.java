/*
 * Created on Jun 5, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.core;

import java.awt.Component;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.fest.swing.hierarchy.ComponentHierarchy;

/**
 * Finds all the components in a <code>{@link ComponentHierarchy}</code> that match the search criteria specified in a
 * <code>{@link ComponentMatcher}</code>.
 *
 * @author Alex Ruiz
 */
final class FinderDelegate {

  Collection<Component> find(ComponentHierarchy h, ComponentMatcher m)  {
    Set<Component> found = new HashSet<Component>();
    for (Object o : h.roots()) find(h, m, (Component)o, found);
    return found;
  }

  private static void find(ComponentHierarchy h, ComponentMatcher m, Component root, Set<Component> found) {
    for (Object o : h.childrenOf(root)) 
      find(h, m, (Component)o, found);
    if (m.matches(root)) found.add(root);
  }

}
