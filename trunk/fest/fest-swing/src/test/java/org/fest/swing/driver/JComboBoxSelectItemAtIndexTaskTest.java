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

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

/**
 * Tests for <code>{@link JComboBoxSelectItemAtIndexTask}</code>.
 *
 * @author Alex Ruiz
 */
public class JComboBoxSelectItemAtIndexTaskTest {

  private JComboBox comboBox;
  private int index;

  @BeforeMethod public void setUp() {
    comboBox = createMock(JComboBox.class);
    index = 6;
  }
  
  @Test public void shouldSetSelectedIndex() {
    new EasyMockTemplate(comboBox) {
      protected void expectations() {
        comboBox.setSelectedIndex(index);
        expectLastCall().once();
      }

      protected void codeToTest() {
        JComboBoxSelectItemAtIndexTask.selectItemIn(comboBox, index).executeInEDT();
      }
    }.run();
  }
}
