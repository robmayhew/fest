/*
 * Created on Apr 13, 2008
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
package org.fest.swing.cell;

import javax.swing.JComboBox;

/**
 * Understands reading the internal value of a cell in a <code>{@link JComboBox}</code> as expected in a test.
 *
 * @author Alex Ruiz 
 */
public interface JComboBoxCellValueReader {

  /**
   * Returns the internal value of a cell in a <code>{@link JComboBox}</code> as expected in a test.
   * @param comboBox the given <code>JComboBox</code>.
   * @param index the index of the cell.
   * @return the internal value of a cell in a <code>JComboBox</code> as expected in a test.
   */
  String valueAt(JComboBox comboBox, int index);
}
