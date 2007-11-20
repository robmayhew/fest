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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.hierarchy;

import static org.fest.swing.listener.WeakEventListener.attachAsWeakEventListener;
import static org.fest.swing.util.ComponentCollections.empty;

import java.awt.Component;
import java.awt.Window;
import static java.awt.event.WindowEvent.COMPONENT_EVENT_MASK;
import static java.awt.event.WindowEvent.WINDOW_EVENT_MASK;
import java.util.Collection;

/**
 * Understands isolation of a component hierarchy to limit to only those components created during the lifetime of this
 * hierarchy. Existing components (and any subsequently generated subwindows) are ignored by default.
 * <p>
 * Implicitly auto-filters windows which are disposed (i.e. generate a
 * <code>{@link java.awt.event.WindowEvent#WINDOW_CLOSED WINDOW_CLOSED}</code> event), but also implicitly un-filters
 * them if they should be shown again. Any window explicitly disposed with <code>{@link #dispose(Window)}</code< will be
 * ignored permanently.
 * </p>
 * <p>
 * Adapted from <code>abbot.finder.TestHierarchy</code> from <a href="http://abbot.sourceforge.net"
 * target="_blank">Abbot</a>.
 * </p>
 * 
 * @author Alex Ruiz
 */
public class NewHierarchy extends ExistingHierarchy {

  private final WindowFilter filter;

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
    this.filter = new WindowFilter(parentFinder, childrenFinder);
    setUp(ignoreExisting);
  }

  NewHierarchy(WindowFilter filter, boolean ignoreExisting) {
    this.filter = filter;
    setUp(ignoreExisting);    
  }

  private void setUp(boolean ignoreExisting) {
    if (ignoreExisting) ignoreExisting();
    attachAsWeakEventListener(new TransientWindowListener(filter), WINDOW_EVENT_MASK | COMPONENT_EVENT_MASK);
  }

  /**
   * Make all currently extisting components invisible to this hierarchy, without affecting their current state.
   */
  public void ignoreExisting() {
    for (Window w : rootWindows())
      filter.filter(w);
  }

  /**
   * Returns all sub-components of the given component, omitting those which are currently filtered.
   * @param c the given component.
   * @return all sub-components of the given component, omitting those which are currently filtered.
   */
  @Override public Collection<Component> childrenOf(Component c) {
    if (filter.isFiltered(c)) return empty();
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
   * Returns all available root windows, excluding those which have been filtered.
   * @return  all available root windows, excluding those which have been filtered.
   */
  @Override public Collection<Window> rootWindows() {
    Collection<Window> roots = super.rootWindows();
    roots.removeAll(filter.filtered());
    return roots;
  }
}
