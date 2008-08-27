/*
 * Created on Aug 7, 2008
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

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.EDT_ACTION;

/**
 * Tests for <code>{@link ContainerResizeLocationQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = EDT_ACTION)
public class ContainerResizeLocationQueryTest {

  private Container container;
  private Dimension size;
  private Insets insets;
  private ContainerResizeLocationQuery query;
  
  @BeforeMethod public void setUp() {
    container = createMock(Container.class);
    size = new Dimension(800, 600);
    insets = new Insets(2, 5, 6, 8);
    query = new ContainerResizeLocationQuery(container);
  }
  
  public void shouldReturnResizeLocationOfContainer() {
    new EasyMockTemplate(container) {
      protected void expectations() {
        expect(container.getSize()).andReturn(size);
        expect(container.getInsets()).andReturn(insets);
      }

      protected void codeToTest() {
        Point resizeLocation = query.executeInEDT();
        assertThat(resizeLocation.x).isEqualTo(796);
        assertThat(resizeLocation.y).isEqualTo(597);
      }
    }.run();
  }
}
