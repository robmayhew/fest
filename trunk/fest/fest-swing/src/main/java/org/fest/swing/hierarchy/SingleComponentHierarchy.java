/*
 * Created on Nov 20, 2007
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
package org.fest.swing.hierarchy;

import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.swing.SwingUtilities.isDescendingFrom;

/**
 * Understands a component hierarchy having only one component as root.
 * <p>
 * Adapted from <code>abbot.finder.BasicFinder#SingleComponentHierarchy</code> from <a
 * href="http://abbot.sourceforge.net" target="_blank">Abbot</a>.
 * </p>
 *
 * @author Alex Ruiz
 */
public class SingleComponentHierarchy implements ComponentHierarchy {

  private final Container root;
  private final ComponentHierarchy delegate;

  private final List<Container> roots = new ArrayList<Container>();

  /**
   * Creates a new <code>{@link org.fest.swing.hierarchy.SingleComponentHierarchy}</code>
   * @param root the root of this hierarchy.
   * @param delegate the component hierarchy to be used as delegate.
   */
  public SingleComponentHierarchy(Container root, ComponentHierarchy delegate) {
    this.root = root;
    this.delegate = delegate;
    roots.add(root);
  }

  /** {@inheritDoc} */
  public Collection<? extends Container> roots() {
    return roots;
  }

  /** {@inheritDoc} */
  public Collection<Component> childrenOf(Component c) {
    return delegate.childrenOf(c);
  }

  /** {@inheritDoc} */
  public Container parentOf(Component c) {
    return delegate.parentOf(c);
  }

  /** {@inheritDoc} */
  public boolean contains(Component c) {
    return delegate.contains(c) && isDescendingFrom(c, root);
  }

  /** {@inheritDoc} */
  public void dispose(Window w) {
    delegate.dispose(w);
  }
}
