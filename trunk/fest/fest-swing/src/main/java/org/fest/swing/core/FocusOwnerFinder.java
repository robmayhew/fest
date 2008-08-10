/*
 * Created on Mar 30, 2008
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
import java.awt.Container;
import java.awt.KeyboardFocusManager;
import java.awt.Window;

import org.fest.swing.hierarchy.ExistingHierarchy;

import static org.fest.reflect.core.Reflection.staticField;
import static org.fest.swing.query.ComponentShowingQuery.isShowing;

/**
 * Understands lookup of a <code>{@link Component}</code> owning the input focus.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class FocusOwnerFinder {

  /**
   * Returns the focus owner.
   * @return the focus owner.
   */
  public static Component focusOwner() {
    try {
      return staticField("focusOwner").ofType(Component.class).in(KeyboardFocusManager.class).get();
    } catch (Exception e) {
      return focusOwnerInHierarchy();
    }
  }

  static Component focusOwnerInHierarchy() {
    Component focus = null;
    for (Container c : new ExistingHierarchy().roots()) {
      if (!(c instanceof Window)) continue;
      Window w = (Window) c;
      if (isShowing(w) && (focus = focusOwner(w)) != null) break;
    }
    return focus;
  }
  
  private static Component focusOwner(Window w) {
    Component focus = w.getFocusOwner();
    if (focus != null) return focus;
    Window[] owned = w.getOwnedWindows();
    for (int i = 0; i < owned.length; i++)
      if ((focus = owned[i].getFocusOwner()) != null) return focus;
    return focus;
  }

  private FocusOwnerFinder() {}
}
