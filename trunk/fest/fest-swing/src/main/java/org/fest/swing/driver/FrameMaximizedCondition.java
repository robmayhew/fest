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

import java.awt.Frame;

import org.fest.swing.core.Condition;

import static java.awt.Frame.MAXIMIZED_BOTH;

/**
 * Understands a condition that verifies that a <code>{@link Frame}</code> has been fully maximized.
 *
 * @author Alex Ruiz 
 */
class FrameMaximizedCondition extends Condition {
  private final Frame frame;

  static FrameMaximizedCondition untilMaximized(Frame frame) {
    return new FrameMaximizedCondition(frame);
  }
  
  private FrameMaximizedCondition(Frame frame) {
    super("frame being maximized");
    this.frame = frame;
  }

  public boolean test() {
    return (frame.getExtendedState() & MAXIMIZED_BOTH) == MAXIMIZED_BOTH;
  }
}