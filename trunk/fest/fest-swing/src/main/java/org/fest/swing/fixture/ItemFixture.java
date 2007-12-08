/*
 * Created on Dec 7, 2007
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

import java.awt.Component;

import org.fest.swing.exception.ComponentLookupException;

/**
 * Understands simulation of user events on an belonging to a fixture's <code>{@link Component}</code> and
 * verification of the state of such .
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public interface ItemFixture {

  /**
   * Simulates a user selecting this fixture's item.
   * @return this fixture.
   */
  ItemFixture select();

  /**
   * Simulates a user clicking this fixture's item.
   * @return this fixture.
   */
  ItemFixture click();

  /**
   * Simulates a user clicking this fixture's item.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  ItemFixture click(MouseClickInfo mouseClickInfo);

  /**
   * Simulates a user right-clicking this fixture's item.
   * @return this fixture.
   */
  ItemFixture rightClick();

  /**
   * Simulates a user double-clicking this fixture's item.
   * @return this fixture.
   */
  ItemFixture doubleClick();

  /**
   * Returns the value of item managed by this fixture into a reasonable <code>String</code> representation, or
   * <code>null</code> if one can not be obtained.
   * @return the value of the given cell.
   */
  String contents();

  /**
   * Simulates a user dragging this fixture's item.
   * @return this fixture.
   */
  ItemFixture drag();

  /**
   * Simulates a user dropping into this fixture's item.
   * @return this fixture.
   */
  ItemFixture drop();

  /**
   * Simulates a user pressing and releasing the given keys on this fixture's item. This method does not affect
   * the current focus.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  ItemFixture pressAndReleaseKeys(int... keyCodes);

  /**
   * Simulates a user pressing the given key on this fixture's item.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  ItemFixture pressKey(int keyCode);

  /**
   * Simulates a user releasing the given key on this fixture's item.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  ItemFixture releaseKey(int keyCode);

  /**
   * Shows a popup menu using this fixture's item as the invoker of the popup menu.
   * @return a fixture that manages the displayed popup menu.
   * @throws ComponentLookupException if a popup menu cannot be found.
   */
  JPopupMenuFixture showPopupMenu();
}