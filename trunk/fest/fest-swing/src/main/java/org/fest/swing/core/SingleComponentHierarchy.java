/*
 * Created on Dec 22, 2007
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
import java.awt.Window;
import java.util.ArrayList;
import java.util.Collection;

import abbot.finder.Hierarchy;
import static javax.swing.SwingUtilities.isDescendingFrom;

/**
 * Understands a component hierarchy created with a specified <code>{@link Component}</code> as root.
 * 
 * @author Alex Ruiz
 */
final class SingleComponentHierarchy implements abbot.finder.Hierarchy {

  private final Container root;
  private final ArrayList<Component> list = new ArrayList<Component>();
  private final Hierarchy hierarchy;

  /**
   * Creates a new </code>{@link SingleComponentHierarchy}</code>.
   * @param root the root component of this hierarchy
   * @param hierarchy the base component hierarchy.
   */
  public SingleComponentHierarchy(Container root, Hierarchy hierarchy) {
    this.root = root;
    this.hierarchy = hierarchy;
    list.add(root);
  }

  Container root() { return root; }
  
  /** 
   * Returns the parent component for the given <code>{@link Component}</code>.
   * @param c the given <code>Component</code>.
   * @return the parent component for the given <code>{@link Component}</code>.  
   */
  public Container getParent(Component c) {
    return hierarchy.getParent(c);
  }

  /**
   * Returns a collection containing only the root <code>{@link Component}</code> in this hierarchy.
   * @return a collection containing only the root <code>{@link Component}</code> in this hierarchy.
   */
  public Collection getRoots() {
    return list;
  }

  /**
   * Returns all sub-components of the given <code>{@link Component}</code>.
   * @return all sub-components of the given <code>{@link Component}</code>.
   */
  public Collection getComponents(Component c) {
    return hierarchy.getComponents(c);
  }

  /** 
   * Returns whether the hierarchy contains the given <code>{@link Component}</code>. 
   * @return whether the hierarchy contains the given <code>{@link Component}</code>. 
   */
  public boolean contains(Component c) {
    return hierarchy.contains(c) && isDescendingFrom(c, root);
  }

  /**
   * Provides proper disposal of the given <code>{@link Window}</code>, appropriate to this hierarchy. After disposal, 
   * the <code>{@link Window}</code> and its descendents will no longer be reachable from this hierarchy.
   * @param w the window to dispose.
   */
  public void dispose(Window w) {
    hierarchy.dispose(w);
  }
}
