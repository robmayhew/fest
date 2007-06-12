/*
 * Created on Jun 11, 2007
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
package org.fest.swing.fixture;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Understands a mouse listener that verifies that a component was clicked.
 *
 * @author Alex Ruiz
 */
final class ComponentEvents extends MouseAdapter {

  static ComponentEvents attachTo(Component target) {
    return new ComponentEvents(target);
  }
  
  private boolean clicked;
  
  private ComponentEvents(Component target) {
    target.addMouseListener(this);
  }

  @Override public void mouseClicked(MouseEvent e) {
    clicked = true;
  }
  
  boolean clicked() { return clicked; }
  
  void reset() { clicked = false; }
}
