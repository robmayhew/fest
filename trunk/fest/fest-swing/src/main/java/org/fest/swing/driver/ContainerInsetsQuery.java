/*
 * Created on Aug 8, 2008
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
package org.fest.swing.driver;

import java.awt.Container;
import java.awt.Insets;

import org.fest.swing.core.GuiQuery;
import org.fest.swing.core.GuiActionRunner;

/**
 * Understands an action, executed in the event dispatch thread, that returns the insets of a
 * <code>{@link Container}</code>.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class ContainerInsetsQuery extends GuiQuery<Insets> {
  
  private final Container container;

  static Insets insetsOf(Container container) {
    return GuiActionRunner.execute(new ContainerInsetsQuery(container));
  }
  
  private ContainerInsetsQuery(Container container) {
    this.container = container;
  }

  protected Insets executeInEDT() {
    return container.getInsets();
  }
}