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
package org.fest.swing.query;

import java.awt.Rectangle;

import javax.swing.JComponent;

import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the "visible rectangle" of a
 * <code>{@link JComponent}</code>.
 * @see JComponent#getVisibleRect()
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class JComponentVisibleRectQuery {

  /**
   * Returns the "visible rectangle" of a <code>{@link JComponent}</code>.
   * @param component the given <code>JComponent</code>.
   * @return the "visible rectangle" of a <code>JComponent</code>.
   * @see JComponent#getVisibleRect()
   */
  public static Rectangle visibleRectOf(final JComponent component) {
    return execute(new GuiQuery<Rectangle>() {
      protected Rectangle executeInEDT() {
        return component.getVisibleRect();
      }
    });
  }

  private JComponentVisibleRectQuery() {}
}