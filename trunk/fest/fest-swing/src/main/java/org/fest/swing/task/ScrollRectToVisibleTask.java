/*
 * Created on Feb 23, 2008
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
package org.fest.swing.task;

import java.awt.Rectangle;

import javax.swing.JComponent;

/**
 * Understands a request to scroll a <code>{@link JComponent}</code>'s parent to a visible
 * <code>{@link Rectangle}</code>.
 *
 * @author Alex Ruiz
 */
public class ScrollRectToVisibleTask implements Runnable {

  private final JComponent c;
  private final Rectangle r;

  /**
   * Creates a new </code>{@link ScrollRectToVisibleTask}</code>.
   * @param c the given <code>JComponent</code>.
   * @param r the given visible <code>Rectangle</code>.
   */
  public ScrollRectToVisibleTask(JComponent c, Rectangle r) {
    this.c = c;
    this.r = r;

  }

  /**
   * Forwards the <code>scrollRectToVisible</code> message to the parent of this task's
   * <code>{@link JComponent}</code>.
   */
  public void run() {
    c.scrollRectToVisible(r);
  }
}
