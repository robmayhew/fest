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

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

/**
 * Tests for <code>{@link SetSelectedIndexTask}</code>.
 *
 * @author Alex Ruiz
 */
public class SetSelectedIndexTaskTest {

  @Test public void shouldSetSelectedIndex() {
    final JComboBox comboBox = createMock(JComboBox.class);
    final int index = 6;
    final SetSelectedIndexTask task = new SetSelectedIndexTask(comboBox, index);
    new EasyMockTemplate(comboBox) {
      protected void expectations() {
        comboBox.setSelectedIndex(index);
        expectLastCall().once();
      }

      protected void codeToTest() {
        task.run();
      }
    }.run();
  }
}
