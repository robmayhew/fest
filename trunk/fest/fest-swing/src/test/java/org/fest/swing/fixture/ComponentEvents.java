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
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.event.MouseEvent.BUTTON1;
import static java.awt.event.MouseEvent.BUTTON3;

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
  private boolean rightClicked;
  private boolean doubleClicked;
  
  private ComponentEvents(Component target) {
    attach(this, target);
  }

  private static void attach(ComponentEvents events, Component target) {
    target.addMouseListener(events);
    if (!(target instanceof Container)) return;
    for (Component c : ((Container)target).getComponents()) attach(events, c);
  }
  
  @Override public void mousePressed(MouseEvent e) { 
    int button = e.getButton();
    int clickCount = e.getClickCount();
    clicked = button == BUTTON1;
    doubleClicked = clicked && clickCount == 2;
    if (doubleClicked) return;
    rightClicked = button == BUTTON3 && clickCount == 1;
  }
  
  boolean clicked() { return clicked; }
  boolean rightClicked() { return rightClicked; }
  boolean doubleClicked() { return doubleClicked; }
  
  void reset() { clicked = false; }
}
