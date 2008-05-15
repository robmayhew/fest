/*
 * Created on Oct 31, 2007
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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.hierarchy;

import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.Collection;

import static java.awt.AWTEvent.*;
import static java.util.Collections.emptyList;

import static org.fest.swing.listener.WeakEventListener.attachAsWeakEventListener;

/**
 * Understands isolation of a component hierarchy to limit to only those components created during the lifetime of this
 * hierarchy. Existing components (and any subsequently generated subwindows) are ignored by default.
 * <p>
 * Implicitly auto-filters windows which are disposed (i.e. generate a
 * <code>{@link java.awt.event.WindowEvent#WINDOW_CLOSED WINDOW_CLOSED}</code> event), but also implicitly un-filters
 * them if they should be shown again. Any window explicitly disposed with <code>{@link ComponentHierarchy#dispose(java.awt.Window)}</code< will be
 * ignored permanently.
 * </p>
 * 
 * @author Alex Ruiz
 */
public class NewHierarchy extends ExistingHierarchy {

  private final WindowFilter filter;
  private final TransientWindowListener transientWindowListener;

  /**
   * Creates a new <code>{@link NewHierarchy}</code> which does not contain any existing GUI components.
   * @return the created hierarchy.
   */
  public static NewHierarchy ignoreExistingComponents() {
    return new NewHierarchy(true);
  }

  /**
   * Creates a new <code>{@link NewHierarchy}</code> which contains existing GUI components.
   * @return the created hierarchy.
   */
  public static NewHierarchy includeExistingComponents() {
    return new NewHierarchy(false);
  }

  NewHierarchy(boolean ignoreExisting) {
    this(Toolkit.getDefaultToolkit(), ignoreExisting);
  }

  NewHierarchy(Toolkit toolkit, boolean ignoreExisting) {
    this.filter = new WindowFilter(parentFinder, childrenFinder);
    transientWindowListener = new TransientWindowListener(filter);
    setUp(toolkit, ignoreExisting);
  }

  NewHierarchy(Toolkit toolkit, WindowFilter filter, boolean ignoreExisting) {
    this.filter = filter;
    transientWindowListener = new TransientWindowListener(filter);
    setUp(toolkit, ignoreExisting);    
  }

  private void setUp(Toolkit toolkit, boolean ignoreExisting) {
    if (ignoreExisting) ignoreExisting();
    attachAsWeakEventListener(toolkit, transientWindowListener, WINDOW_EVENT_MASK | COMPONENT_EVENT_MASK);
  }

  /**
   * Make all currently existing components invisible to this hierarchy, without affecting their current state.
   */
  public void ignoreExisting() {
    for (Container c : roots())
      filter.filter(c);
  }

  /**
   * Returns all sub-components of the given component, omitting those which are currently filtered.
   * @param c the given component.
   * @return all sub-components of the given component, omitting those which are currently filtered.
   */
  @Override public Collection<Component> childrenOf(Component c) {
    if (filter.isFiltered(c)) return emptyList();
    Collection<Component> children = super.childrenOf(c);
    // this only removes those components which are directly filtered, not necessarily those which have a filtered 
    // ancestor.
    children.removeAll(filter.filtered());
    return children;
  }

  /**
   * Returns <code>true</code> if the given component is not filtered.
   * @param c the given component.
   * @return <code>true</code> if the given component is not filtered, <code>false</code> otherwise.
   */
  @Override public boolean contains(Component c) {
    return super.contains(c) && !filter.isFiltered(c);
  }

  /**
   * Dispose of the given window, but only if it currently exists within the hierarchy.  It will no longer appear in
   * this hierarchy or be reachable in a hierarchy walk.
   * @param w the window to dispose.
   */
  @Override public void dispose(Window w) {
    if (!contains(w)) return;
    super.dispose(w);
    filter.filter(w);
  }

  /**
   * Returns all available root containers, excluding those which have been filtered.
   * @return  all available root containers, excluding those which have been filtered.
   */
  @Override public Collection<? extends Container> roots() {
    Collection<? extends Container> roots = super.roots();
    roots.removeAll(filter.filtered());
    return roots;
  }
}
