/*
 * Created on Jul 26, 2008
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
package org.fest.swing.query;

import java.awt.Component;

/**
 * Understands an action that returns the name of a <code>{@link Component}</code>. This query is <strong>not</strong>
 * executed in the event dispatch thread.
 * @see Component#getName()
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class ComponentNameQuery {

  /**
   * Returns the name of the given <code>{@link Component}</code>. This action is executed in the event dispatch thread.
   * @param component the given <code>Component</code>.
   * @return the name of the given <code>Component</code>.
   * @see Component#getName()
   */
  public static String nameOf(Component component) {
    return component.getName();
  }

  private ComponentNameQuery() {}
}