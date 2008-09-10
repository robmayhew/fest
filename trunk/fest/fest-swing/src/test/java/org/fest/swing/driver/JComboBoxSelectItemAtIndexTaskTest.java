/*
 * Created on Jul 21, 2008
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

import javax.swing.JComboBox;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.factory.JComboBoxes.comboBox;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JComboBoxSelectItemAtIndexTask}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, EDT_ACTION })
public class JComboBoxSelectItemAtIndexTaskTest {

  private JComboBox comboBox;
  private int index;

  @BeforeMethod public void setUp() {
    comboBox = comboBox().withItems("One", "Two", "Three").createNew();
    index = 1;
  }
  
  public void shouldSetSelectedIndex() {
    assertThat(comboBox.getSelectedIndex()).isNotEqualTo(index);
    JComboBoxSelectItemAtIndexTask.selectItemAtIndex(comboBox, index);
    assertThat(comboBox.getSelectedIndex()).isEqualTo(index);
  }
}
