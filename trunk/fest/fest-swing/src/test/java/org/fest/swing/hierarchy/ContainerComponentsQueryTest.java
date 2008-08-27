/*
 * Created on Aug 26, 2008
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
package org.fest.swing.hierarchy;

import java.awt.Component;
import java.awt.Container;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.EDT_ACTION;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link ContainerComponentsQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = EDT_ACTION)
public class ContainerComponentsQueryTest {

  private Container container;
  private Component[] components;
  private ContainerComponentsQuery query;
  
  @BeforeMethod public void setUp() {
    container = createMock(Container.class);
    components = array(createMock(Component.class), createMock(Component.class));
    query = new ContainerComponentsQuery(container);
  }
  
  public void shouldReturnComponentsOfContainer() {
    new EasyMockTemplate(container) {
      protected void expectations() {
        expect(container.getComponents()).andReturn(components);
      }

      protected void codeToTest() {
        List<Component> componentList = query.executeInEDT();
        assertThat(componentList).containsOnly(components);
      }
    }.run();
  }
}
