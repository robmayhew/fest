/*
 * Created on Feb 23, 2008
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
package org.fest.swing.task;

import java.awt.Component;

import javax.accessibility.AccessibleAction;

/**
 * Understands finding <code>{@link AccessibleAction}</code>s associated to <code>{@link Component}</code>s.
 *
 * @author Alex Ruiz
 */
class AccessibleActionFinder {

  /**
   * Returns the <code>{@link AccessibleAction}</code> associated to the given <code>{@link Component}</code>.
   * @param c the given <code>Component</code>.
   * @return the <code>AccessibleAction</code> associated to the given <code>Component</code>.
   */
  AccessibleAction accessibleActionFrom(Component c) {
    return c.getAccessibleContext().getAccessibleAction();
  }
}
