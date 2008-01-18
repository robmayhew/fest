/*
 * Created on Dec 8, 2007
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

import javax.swing.JList;

import abbot.tester.JListLocation;

import org.fest.swing.exception.ComponentLookupException;

import static org.fest.swing.core.MouseButton.RIGHT_BUTTON;

/**
 * Understands simulation of user events on an item in a <code>{@link JList}</code> and verification of the state of
 * such list item.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JListItemFixture implements ItemFixture {

  private final JListFixture list;
  private final int index;

  /**
   * Creates a new </code>{@link JListItemFixture}</code>.
   * @param list manages the <code>JList</code> containing the list item to be managed by this fixture.
   * @param index index of the list item to be managed by this fixture.
   */
  public JListItemFixture(JListFixture list, int index) {
    this.list = list;
    this.index = index;
  }

  /**
   * Simulates a user selecting this fixture's list item.
   * @return this fixture.
   */
  public final JListItemFixture select() {
    list.selectItem(index);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's list item.
   * @return this fixture.
   */
  public final JListItemFixture click() {
    list.selectItem(index);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's list item.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JListItemFixture click(MouseClickInfo mouseClickInfo) {
    list.clickItem(location(), mouseClickInfo.button(), mouseClickInfo.times());
    return null;
  }

  /**
   * Simulates a user double-clicking this fixture's list item.
   * @return this fixture.
   */
  public final JListItemFixture doubleClick() {
    list.doubleClickItem(index);
    return this;
  }

  /**
   * Simulates a user right-clicking this fixture's list item.
   * @return this fixture.
   */
  public final JListItemFixture rightClick() {
    list.clickItem(location(), RIGHT_BUTTON, 1);
    return this;
  }

  /**
   * Shows a popup menu using this fixture's list item as the invoker of the popup menu.
   * @return a fixture that manages the displayed popup menu.
   * @throws ComponentLookupException if a popup menu cannot be found.
   */
  public final JPopupMenuFixture showPopupMenu() {
    return list.showPopupMenuAt(location().getPoint(list.target));
  }
  
  private JListLocation location() {
    return new JListLocation(index);
  }

  /**
   * Returns the value of this fixture's list item into a reasonable <code>String</code> representation, or
   * <code>null</code> if one can not be obtained.
   * @return the value of the given cell.
   */
  public final String contents() {
    return list.valueAt(index);
  }

  /**
   * Simulates a user dragging this fixture's list item.
   * @return this fixture.
   */
  public final JListItemFixture drag() {
    list.drag(index);
    return this;
  }

  /**
   * Simulates a user dropping into this fixture's list item.
   * @return this fixture.
   */
  public final JListItemFixture drop() {
    list.drop(index);
    return this;
  }

  /**
   * Simulates a user pressing and releasing the given keys on this fixture's list item. This method does not affect
   * the current focus.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JListItemFixture pressAndReleaseKeys(int... keyCodes) {
    list.pressAndReleaseKeys(keyCodes);
    return this;
  }

  /**
   * Simulates a user pressing the given key on this fixture's list item.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JListItemFixture pressKey(int keyCode) {
    list.pressKey(keyCode);
    return this;
  }

  /**
   * Simulates a user releasing the given key on this fixture's list item.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JListItemFixture releaseKey(int keyCode) {
    list.releaseKey(keyCode);
    return this;
  }
  
  /**
   * Returns the index of this fixture's list item.
   * @return the index of this fixture's list item.
   */
  public final int index() { return index; }
}
