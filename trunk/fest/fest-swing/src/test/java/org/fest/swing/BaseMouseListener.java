/*
 * Created on Sep 22, 2007
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
package org.fest.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseAdapter;

/**
 * Understands a mouse listener that attaches itself to the given component and its subcomponents.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class BaseMouseListener extends MouseAdapter {

  protected BaseMouseListener(Component target) {
    attach(this, target);
  }

  private static void attach(BaseMouseListener mouseListener, Component target) {
    target.addMouseListener(mouseListener);
    if (!(target instanceof Container)) return;
    for (Component c : ((Container)target).getComponents()) attach(mouseListener, c);
  }
}
