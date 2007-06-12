/*
 * Created on Jun 12, 2007
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
 * Understands simulation of user events and state verification of a <code>{@link Component}</code> that contains a 
 * group of items.
 * @param <T> the type of component handled by this fixture. 
 *
 * @author Alex Ruiz 
 */
public interface ItemGroupFixture<T extends Component> {

  /**
   * @return the elements in the managed component.
   */
  String[] contents();

  /**
   * Simulates a user selecting the item located at the given index. 
   * @param index the given index to match.
   * @return this fixture.
   */
  ItemGroupFixture<T> selectItemAt(int index);

  /**
   * Simulates a user selecting the item that contains the given text. 
   * @param text the given text to match.
   * @return this fixture.
   */
  ItemGroupFixture<T> selectItemWithText(String text);

  /**
   * Returns the String representation of the item under the given index. If such representation is not meaningful, this
   * method will return <code>null</code>.
   * @param index the given index.
   * @return the String reprentation of the item uder the given index, or <code>null</code> if nothing meaningful.
   */
  String valueAt(int index);
}