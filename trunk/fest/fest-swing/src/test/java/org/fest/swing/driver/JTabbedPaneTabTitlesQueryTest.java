/*
 * Created on Aug 10, 2008
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

import javax.swing.JTabbedPane;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.EDT_QUERY;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JTabbedPaneTabTitlesQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = EDT_QUERY)
public class JTabbedPaneTabTitlesQueryTest {

  private JTabbedPane tabbedPane;
  private String[] tabNames;
  
  @BeforeMethod public void setUp() {
    tabbedPane = createMock(JTabbedPane.class);
    tabNames = array("One", "Two", "Three");
  }
  
  public void shouldReturnTabNamesOfJTabbedPane() {
    new EasyMockTemplate(tabbedPane) {
      protected void expectations() {
        int tabCount = tabNames.length;
        expect(tabbedPane.getTabCount()).andReturn(tabCount);
        for (int i = 0; i < tabCount; i++) 
          expect(tabbedPane.getTitleAt(i)).andReturn(tabNames[i]);
      }
      
      protected void codeToTest() {
        assertThat(JTabbedPaneTabTitlesQuery.tabTitlesOf(tabbedPane)).isEqualTo(tabNames);
      }
    }.run();
  }
}
