/*
 * Created on Sep 1, 2008
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.query;

import java.awt.Frame;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the title of a
 * <code>{@link Frame}</code>.
 * @see Frame#getTitle()
 *
 * @author Yvonne Wang
 */
public final class FrameTitleQuery {

  /**
   * Returns the title of the given <code>{@link Frame}</code>. This action is executed in the event dispatch thread.
   * @param frame the given <code>Frame</code>.
   * @return the title of the given <code>Frame</code>.
   * @see Frame#getTitle()
   */
  public static String titleOf(final Frame frame) {
    return execute(new GuiQuery<String>() {
      protected String executeInEDT() {
        return frame.getTitle();
      }
    });
  }

  private FrameTitleQuery() {}
}