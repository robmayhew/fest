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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import static org.fest.swing.MouseButtons.BUTTON1;
import static org.fest.swing.MouseButtons.BUTTON2;
import static org.fest.swing.MouseButtons.BUTTON3;

/**
 * Understands a mouse listener that records how many times a button is being pressed.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang 
 */
public class ClickRecordMouseListener extends MouseAdapter {
  private static final Map<Integer, MouseButtons> MOUSE_BUTTON_MAP = new HashMap<Integer, MouseButtons>(); 
  
  static {
      MOUSE_BUTTON_MAP.put(MouseEvent.BUTTON1, BUTTON1);
      MOUSE_BUTTON_MAP.put(MouseEvent.BUTTON2, BUTTON2);
      MOUSE_BUTTON_MAP.put(MouseEvent.BUTTON3, BUTTON3);
  }
  
  private MouseButtons clickedButton;
  private int clickCount;

  @Override public void mousePressed(MouseEvent e) {
    clickedButton = MOUSE_BUTTON_MAP.get(e.getButton());
    clickCount = e.getClickCount();
  }

  public MouseButtons clickedButton() { return clickedButton; }
  public int clickCount() { return clickCount; }
}