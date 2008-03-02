/*
 * Created on Oct 31, 2007
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
package org.fest.swing.hierarchy;

import java.awt.Component;
import java.awt.Window;
import java.util.Collection;
import java.util.Map;
import java.util.WeakHashMap;

import static org.fest.swing.util.AWT.isSharedInvisibleFrame;

/**
 * Understands a filter of windows to ignore in a component hierarchy.
 * <p>
 * Adapted from <code>abbot.finder.TestHierarchy</code> from <a href="http://abbot.sourceforge.net"
 * target="_blank">Abbot</a>.
 * </p>
 * 
 * @author Alex Ruiz
 */
class WindowFilter {

  private final ParentFinder parentFinder;
  private final ChildrenFinder childrenFinder;

  WindowFilter() {
    this(new ParentFinder(), new ChildrenFinder());
  }

  WindowFilter(ParentFinder parentFinder, ChildrenFinder childrenFinder) {
    this.parentFinder = parentFinder;
    this.childrenFinder = childrenFinder;
  }
  
  // Map of components to ignore
  private final Map<Component, Boolean> filtered = new WeakHashMap<Component, Boolean>();

  // Map of components implicitly filtered; these will be implicitly un-filtered if they are re-shown.
  private final Map<Component, Boolean> implicitFiltered = new WeakHashMap<Component, Boolean>();

  boolean isImplicitFiltered(Component c) {
    return implicitFiltered.containsKey(c);
  }

  boolean isFiltered(Component c) {
    if (c == null) return false;
    // TODO if ("sun.plugin.ConsoleWindow".equals(c.getClass().getName())) return !trackAppletConsole;
    if (filtered.containsKey(c)) return true;
    if (c instanceof Window && isFiltered(c.getParent())) return true;
    return !(c instanceof Window) && isWindowFiltered(c);
  }
  
  private boolean isWindowFiltered(Component c) {
    Window w = parentFinder.windowFor(c);
    return w != null && isFiltered(w);
  }

  void implicitFilter(Component c) {
    implicitFiltered.put(c, true);
  }

  void filter(Component c) { 
    filter(c, true);
  }

  void unfilter(Component c) {
    filter(c, false);
  }

  Collection<Component> filtered() {
    return filtered.keySet();
  }

  private void filter(Component c, boolean filter) {
    // Never filter the shared frame
    if (isSharedInvisibleFrame(c)) {
      for (Component child : childrenFinder.childrenOf(c)) 
        filter(child, filter);
      return;
    }
    doFilter(c, filter);
    implicitFiltered.remove(c);
    if (!(c instanceof Window)) return;
    for (Window owned : ((Window)c).getOwnedWindows()) 
      filter(owned, filter);
  }
  
  private void doFilter(Component c, boolean filter) {
    if (filter) {
      filtered.put(c, true);
      return;
    }
    filtered.remove(c);
  }
}
