/*
 * Created on Aug 11, 2008
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

import javax.swing.text.JTextComponent;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.EDT_ACTION;

/**
 * Tests for <code>{@link JTextComponentSelectionStartQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = EDT_ACTION)
public class JTextComponentSelectionStartQueryTest {

  private JTextComponent textBox;
  private int selectionStart;
  private JTextComponentSelectionStartQuery query;
  
  @BeforeMethod public void setUp() {
    textBox = createMock(JTextComponent.class);
    selectionStart = 8;
    query = new JTextComponentSelectionStartQuery(textBox);
  }
  
  public void shouldReturnSelectionStartOfJTextComponent() {
    new EasyMockTemplate(textBox) {
      protected void expectations() {
        expect(textBox.getSelectionStart()).andReturn(selectionStart);
      }

      protected void codeToTest() {
        assertThat(query.executeInEDT()).isEqualTo(selectionStart);
      }
    }.run();
  }
}
