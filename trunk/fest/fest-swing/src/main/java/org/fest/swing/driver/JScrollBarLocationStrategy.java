/*
 * Created on Jul 31, 2008
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

import java.awt.Point;

import javax.swing.JScrollBar;

import org.fest.swing.core.GuiTask;

/**
 * Understands encapsulation of a location in a <code>{@link JScrollBar}</code> in a orientation-specific way.
 *
 * @author Alex Ruiz
 */
interface JScrollBarLocationStrategy {

  int arrow(JScrollBar scrollBar);
  
  Point thumbLocation(JScrollBar scrollBar, double fraction);
  
  Point blockLocation(JScrollBar scrollBar, Point unitLocation, int offset);
  
  Point unitLocationToScrollUp(JScrollBar scrollBar);
  
  static class GetWidthTask extends GuiTask<Integer> {
    private final JScrollBar scrollBar;

    GetWidthTask(JScrollBar scrollBar) {
      this.scrollBar = scrollBar;
    }

    protected Integer executeInEDT() throws Throwable {
      return scrollBar.getWidth();
    }
  }
  
  static class GetHeightTask extends GuiTask<Integer> {
    private final JScrollBar scrollBar;

    GetHeightTask(JScrollBar scrollBar) {
      this.scrollBar = scrollBar;
    }

    protected Integer executeInEDT() throws Throwable {
      return scrollBar.getHeight();
    }
  }
}
