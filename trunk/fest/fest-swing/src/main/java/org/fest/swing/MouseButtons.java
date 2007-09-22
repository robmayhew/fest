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

import static java.awt.event.InputEvent.BUTTON1_MASK;
import static java.awt.event.InputEvent.BUTTON2_MASK;
import static java.awt.event.InputEvent.BUTTON3_MASK;

import java.awt.event.InputEvent;

/**
 * Understands mouse buttons.
 *
 * @author Alex Ruiz
 */
public enum MouseButtons {

  BUTTON1(BUTTON1_MASK), BUTTON2(BUTTON2_MASK), BUTTON3(BUTTON3_MASK);
  
  /**
   * The mouse button modifier.
   * @see InputEvent
   */
  public final int mask;
  
  private MouseButtons(int mask) {
    this.mask = mask;
  }
}
