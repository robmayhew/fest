/*
 * Created on Aug 22, 2008
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

import java.awt.Rectangle;

import javax.swing.JTabbedPane;
import javax.swing.plaf.TabbedPaneUI;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.EDT_QUERY;

/**
 * Tests for <code>{@link JTabbedPaneTabBoundsQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = EDT_QUERY)
public class JTabbedPaneTabBoundsQueryTest {

  private JTabbedPane tabbedPane;
  private int index;
  private TabbedPaneUI ui;
  private Rectangle tabBounds;
  private JTabbedPaneTabBoundsQuery query;
  
  @BeforeMethod public void setUp() {
    tabbedPane = createMock(JTabbedPane.class);
    index = 8;
    ui = createMock(TabbedPaneUI.class);
    tabBounds = new Rectangle(80, 60);
    query = new JTabbedPaneTabBoundsQuery(tabbedPane, index);
  }
  
  public void shouldReturnTabBoundsInJTabbedPane() {
    new EasyMockTemplate(tabbedPane, ui) {
      protected void expectations() {
        expect(tabbedPane.getUI()).andReturn(ui);
        expect(ui.getTabBounds(tabbedPane, index)).andReturn(tabBounds);
      }

      protected void codeToTest() {
        assertThat(query.executeInEDT()).isSameAs(tabBounds);
      }
    }.run();
  }
}
