/*
 * Created on Oct 22, 2007
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
import java.util.Collection;

/**
 * Understands finding children components in a <code>{@link Container}</code>.
 * @param <T> the type of the container.
 *
 * @author Yvonne Wang
 */
interface ChildrenFinder<T extends Container> {
  
  /**
   * Returns non-explicit children of a container.
   * @param container the container whose children we are looking for.
   * @return a collection containing the non-explicit children found.
   */
  Collection<Component> nonExplicitChildrenOf(T container);
}
