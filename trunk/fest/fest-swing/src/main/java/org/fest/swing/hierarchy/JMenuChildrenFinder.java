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

import static org.fest.swing.util.ComponentCollections.EMPTY;
import static org.fest.swing.util.ComponentCollections.components;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

/**
 * Understands how to find children components in a <code>{@link JMenu}</code>.
 *
 * @author Yvonne Wang
 */
final class JMenuChildrenFinder implements ChildrenFinderStrategy {
    
  public Collection<Component> nonExplicitChildrenOf(Container c) {
    if (!(c instanceof JMenu)) return EMPTY;
    return components(popupMenuFrom((JMenu)c));
  }

  private JPopupMenu popupMenuFrom(JMenu menu) {
    return menu.getPopupMenu();
  }
}
