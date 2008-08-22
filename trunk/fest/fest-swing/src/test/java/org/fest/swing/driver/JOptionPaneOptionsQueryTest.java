/*
 * Created on Aug 21, 2008
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

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.EDT_QUERY;
import static org.fest.util.Arrays.array;

import javax.swing.JOptionPane;

import org.fest.mocks.EasyMockTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JOptionPaneOptionsQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = EDT_QUERY)
public class JOptionPaneOptionsQueryTest {

  private JOptionPane optionPane;
  private Object[] options;
  private JOptionPaneOptionsQuery query;

  @BeforeMethod public void setUp() {
    optionPane = createMock(JOptionPane.class);
    options = array(new Object());
    query = new JOptionPaneOptionsQuery(optionPane);
  }

  public void shouldReturnMessageOfJOptionPane() {
    new EasyMockTemplate(optionPane) {
      protected void expectations() {
        expect(optionPane.getOptions()).andReturn(options);
      }

      protected void codeToTest() {
        assertThat(query.executeInEDT()).isSameAs(options);
      }
    }.run();
  }

}
