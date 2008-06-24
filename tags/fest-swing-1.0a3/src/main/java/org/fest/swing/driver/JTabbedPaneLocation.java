/*
 * Created on Jan 27, 2008
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
package org.fest.swing.driver;

import static java.lang.String.valueOf;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.util.Strings.match;
import static org.fest.util.Strings.*;

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JTabbedPane;

import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.LocationUnavailableException;

/**
 * Understands encapsulation of a location on a <code>{@link JTabbedPane}</code> (notably a tab).
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class JTabbedPaneLocation {

  /**
   * Returns the index of the first tab that matches the given <code>String</code>.
   * @param tabbedPane the target <code>JTabbedPane</code>.
   * @param title the title to match.
   * @return the index of the first tab that matches the given <code>String</code>.
   * @throws LocationUnavailableException if a tab matching the given title could not be found.
   */
  public int indexOf(JTabbedPane tabbedPane, String title) {
    for (int i = 0; i < tabbedPane.getTabCount(); i++)
      if (match(title, tabbedPane.getTitleAt(i))) return i;
    throw new LocationUnavailableException(concat("Unable to find a tab with title ", quote(title)));
  }

  /**
   * Returns the coordinates of the tab under the given index.
   * @param tabbedPane the target <code>JTabbedPane</code>.
   * @param index the given index.
   * @return the coordinates of the tab under the given index.
   * @throws ActionFailedException if the given index is negative or out of bounds.
   * @throws LocationUnavailableException if the tab under the given index is not visible.
   */
  public Point pointAt(JTabbedPane tabbedPane, int index) {
    validateIndex(tabbedPane, index);
    Rectangle rect = tabbedPane.getUI().getTabBounds(tabbedPane, index);
    // From Abbot: TODO figure out the effects of tab layout policy sometimes tabs are not directly visible
    if (rect == null || rect.x < 0)
      throw new LocationUnavailableException(concat("The tab '", valueOf(index), "' is not visible"));
    return new Point(rect.x + rect.width / 2, rect.y + rect.height / 2);
  }

  void validateIndex(JTabbedPane tabbedPane, int index) {
    int max = tabbedPane.getTabCount() - 1;
    if (index >= 0 && index <= max) return;
    throw actionFailure(concat(
        "Index <", valueOf(index), "> is not within the JTabbedPane bounds of <0> and <", valueOf(max), "> (inclusive)"));
  }
}
