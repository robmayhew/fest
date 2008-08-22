/*
 * Created on Aug 8, 2008
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

import javax.swing.JList;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.EDT_QUERY;

/**
 * Tests for <code>{@link JListCellBoundsQuery}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = EDT_QUERY)
public class JListCellBoundsQueryTest {

  private JList list;
  private int index;
  private Rectangle rectangle;
  private JListCellBoundsQuery query;

  @BeforeMethod public void setUp() {
    list = createMock(JList.class);
    index = 8;
    rectangle = new Rectangle(800, 600);
    query = new JListCellBoundsQuery(list, index);
  }

  public void shouldReturnCellBoundsOfJList() {
    new EasyMockTemplate(list) {
      protected void expectations() {
        expect(list.getCellBounds(index, index)).andReturn(rectangle);
      }

      protected void codeToTest() {
        assertThat(query.executeInEDT()).isSameAs(rectangle);
      }
    }.run();
  }

}
