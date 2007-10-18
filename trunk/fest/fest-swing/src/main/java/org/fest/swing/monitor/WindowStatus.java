/*
 * Created on Oct 17, 2007
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

import java.awt.AWTException;
import java.awt.Insets;
import java.awt.Robot;
import java.awt.Window;

import javax.swing.SwingUtilities;

import static java.lang.Math.max;

import static org.fest.swing.util.AWT.insetsFrom;

/**
 * Understands verification of the state of a window.
 * <p>
 * Adapted from <code>abbot.tester.WindowTracker</code> from <a href="http://abbot.sourceforge.net"
 * target="_blank">Abbot</a>.
 * </p>
 *
 * @author Alex Ruiz
 */
class WindowStatus {

  private static int sign = 1;

  private final Windows windows;
  private Robot robot;
  
  WindowStatus(Windows windows) {
    this.windows = windows;
    createRobot();
  }

  private void createRobot() {
    try {
      robot = new java.awt.Robot();
    } catch (AWTException e) {}
  }

  /**
   * Checks whether the given window is ready for input.
   * @param w the given window.
   */
  void checkIfReady(Window w) {
    if (robot == null) return;
    // Must avoid frame borders, which are insensitive to mouse motion (at least on w32).
    Insets insets = insetsFrom(w);
    int width = w.getWidth();
    int height = w.getHeight();
    int x = w.getX() + insets.left + (width - (insets.left + insets.right)) / 2;
    int y = w.getY() + insets.top + (height - (insets.top + insets.bottom)) / 2;
    if (x != 0 && y != 0) {
      robot.mouseMove(x, y);
      if (width > height) robot.mouseMove(x + sign, y);
      else robot.mouseMove(x, y + sign);
      sign = -sign;
    }
    if (windows.isShowingButNotReady(w) && isEmptyFrame(w)) 
      makeLargeEnoughToReceiveEvents(w, insets, width, height);
  }

  private void makeLargeEnoughToReceiveEvents(final Window w, Insets insets, int width, int height) {
    final int newWidth = max(width, insets.left + insets.right + 3);
    final int newHeight = max(height, insets.top + insets.bottom + 3);
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        w.setSize(newWidth, newHeight);
      }
    });
  }
  
  private boolean isEmptyFrame(Window w) {
    Insets insets = insetsFrom(w);
    return insets.top + insets.bottom == w.getHeight() || insets.left + insets.right == w.getWidth();
  }
}
