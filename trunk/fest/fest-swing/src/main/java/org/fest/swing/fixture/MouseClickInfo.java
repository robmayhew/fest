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
package org.fest.swing.fixture;

import static org.fest.util.Strings.concat;

import org.fest.swing.MouseButtons;

/**
 * Understands information about clicking a mouse button.
 *
 * @author Alex Ruiz
 */
public final class MouseClickInfo {

  private final MouseButtons button;
  private int times;

  /**
   * Creates a new <code>{@link MouseClickInfo}</code> that specifies that the given button should clicked once.
   * @param button the mouse button to click.
   * @return the created click info.
   */
  public static MouseClickInfo button(MouseButtons button) {
    return new MouseClickInfo(button, 1);
  }
  
  private MouseClickInfo(MouseButtons button, int times) {
    this.button = button;
    this.times = times;
  }

  MouseButtons button() { return button; }
  
  int times() { return times; }
  
  /**
   * Specifies how many times the mouse button should be clicked.
   * @param times the specified number of times to click the mouse button.
   * @return this object.
   */
  public MouseClickInfo times(int times) { 
    this.times = times;
    return this;
  }

  /**
   * Returns a <code>String</code> representation of this object.
   * @return a <code>String</code> representation of this object.
   */
  @Override public String toString() {
    StringBuilder b = new StringBuilder();
    b.append(concat(getClass().getSimpleName(), "["));
    b.append(concat("button=", button, ","));
    b.append(concat("times=", String.valueOf(times), "]"));
    return b.toString();
  }
}
