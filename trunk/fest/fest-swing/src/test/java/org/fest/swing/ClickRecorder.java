/*
 * Created on Sep 21, 2007
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
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import static org.fest.swing.MouseButton.LEFT_BUTTON;
import static org.fest.swing.MouseButton.MIDDLE_BUTTON;
import static org.fest.swing.MouseButton.RIGHT_BUTTON;

/**
 * Understands a mouse listener that records mouse events.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang 
 */
public class ClickRecorder extends MouseAdapter {

  public static ClickRecorder attachTo(Component target) {
    return new ClickRecorder(target);
  }
  
  private static final Map<Integer, MouseButton> MOUSE_BUTTON_MAP = new HashMap<Integer, MouseButton>(); 
  
  static {
      MOUSE_BUTTON_MAP.put(MouseEvent.BUTTON1, LEFT_BUTTON);
      MOUSE_BUTTON_MAP.put(MouseEvent.BUTTON2, MIDDLE_BUTTON);
      MOUSE_BUTTON_MAP.put(MouseEvent.BUTTON3, RIGHT_BUTTON);
  }
  
  private MouseButton clickedButton;
  private int clickCount;
  private Point pointClicked;

  private ClickRecorder(Component target) {
    attach(this, target);
  }
  
  private static void attach(ClickRecorder mouseListener, Component target) {
    target.addMouseListener(mouseListener);
    if (!(target instanceof Container)) return;
    for (Component c : ((Container)target).getComponents()) attach(mouseListener, c);
  }

  @Override public void mousePressed(MouseEvent e) {
    clickedButton = MOUSE_BUTTON_MAP.get(e.getButton());
    clickCount = e.getClickCount();
    pointClicked = e.getPoint();
  }
  
  public boolean clicked() { 
    return LEFT_BUTTON.equals(clickedButton()) && clickCount == 1; 
  }

  public boolean doubleClicked() { 
    return LEFT_BUTTON.equals(clickedButton()) && clickCount == 2; 
  }

  public boolean rightClicked() { 
    return RIGHT_BUTTON.equals(clickedButton()) && clickCount == 1; 
  }

  public MouseButton clickedButton() { return clickedButton; }
  public int clickCount() { return clickCount; }
  public Point pointClicked() { return pointClicked; }
}