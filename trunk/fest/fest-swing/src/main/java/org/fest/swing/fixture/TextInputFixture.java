/*
 * Created on Apr 10, 2007
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

/**
 * Simulates user events on a given <code>{@link Component}</code> that accepts text from the user, and verifies the 
 * state of such <code>{@link Component}</code>.
 * @param <T> the type of <code>{@link Component}</code> that this fixture can manage. 
 *
 * @author Alex Ruiz
 */
public interface TextInputFixture<T extends Component> extends TextDisplayFixture<T> {

  /**
   * Simulates a user entering the given text in the <code>{@link Component}</code> managed by this fixture.
   * @param text the text to ener.
   * @return this fixture.
   */
  TextInputFixture<T> enterText(String text);

  /**
   * Simulates a user deleting all the text in the <code>{@link Component}</code> managed by this fixture.
   * @return this fixture.
   */
  TextInputFixture<T> deleteText();
  
  /**
   * Simulates a user selecting all the text contained in the <code>{@link Component}</code> managed by this fixture. 
   * @return this fixture.
   */
  TextInputFixture<T> selectAll();

  /**
   * Simulates a user pressing the given keys in the <code>{@link Component}</code> managed by this fixture.
   * @param keyCodes the codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  TextInputFixture<T> pressKeys(int...keyCodes);
  
  /**
   * Simulates a user selecting a portion of the text contained in the <code>{@link Component}</code> managed by this
   * fixture.
   * @param start index where selection should start.
   * @param end index where selection should end.
   * @return this fixture.
   */
  TextInputFixture<T> selectText(int start, int end);
  
  /**
   * Simulate a user selecting the given text contained in the <code>{@link Component}</code> managed by this fixture.
   * @param text the text to select.
   * @return this fixture.
   */
  TextInputFixture<T> select(String text);
}
