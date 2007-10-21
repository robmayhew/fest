/*
 * Created on Oct 18, 2007
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
package org.fest.swing.monitor;

import java.awt.Insets;
import java.awt.Point;
import java.awt.Window;

import static org.fest.swing.util.Swing.insetsFrom;

/**
 * Understands some window metrics.
 * <p>
 * Adapted from <code>abbot.tester.WindowTracker</code> from <a href="http://abbot.sourceforge.net"
 * target="_blank">Abbot</a>.
 * </p>
 * 
 * @author Alex Ruiz
 */
final class WindowMetrics {

  private final Window window;

  final Insets insets;
  
  WindowMetrics(Window w) {
    this.window = w;
    insets = insetsFrom(w);
  }
  
  Point center() {
    int x = window.getX() + insets.left + centerWidth();
    int y = window.getY() + insets.top + centerHeight();
    return new Point(x, y);
  }

  private int centerWidth() {
    return (window.getWidth() - addVerticalInsets()) / 2;
  }

  int addVerticalInsets() {
    return insets.left + insets.right;
  }

  private int centerHeight() {
    return (window.getHeight() - addHorizontalInsets()) / 2;
  }

  int addHorizontalInsets() {
    return insets.top + insets.bottom;
  }
}
