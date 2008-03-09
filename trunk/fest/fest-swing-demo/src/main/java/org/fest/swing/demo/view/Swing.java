/*
 * Created on Mar 5, 2008
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
package org.fest.swing.demo.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;

/**
 * Understands SOMETHING DUMMY.
 *
 * @author Alex Ruiz
 */
final class Swing {

  static void center(Window window) {
    Container parent = window.getParent();
    Point topLeft = parent.getLocationOnScreen();
    Dimension parentSize = parent.getSize();
    Dimension dialogSize = window.getSize();
    int parentWidth = parentSize.width;
    int dialogWidth = dialogSize.width;
    int x = parentWidth > dialogWidth ? ((parentWidth - dialogWidth) / 2) + topLeft.x : topLeft.x;
    int parentHeight = parentSize.height;
    int dialogHeight = dialogSize.height;
    int y = parentHeight > dialogHeight ? ((parentHeight - dialogHeight) / 2) + topLeft.y : topLeft.y;
    window.setLocation(x, y);
  }

  private Swing() {}
}
