/*
 * Created on Aug 18, 2008
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

import java.awt.Component;
import java.awt.Rectangle;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.EDT_QUERY;

/**
 * Tests for <code>{@link ComponentBoundsQuery}</code>.
 *
 * @author Yvonne Wang
 */
@Test(groups = EDT_QUERY)
public class ComponentBoundsQueryTest {

  private Component component;
  private Rectangle bounds;
  private ComponentBoundsQuery query;

  @BeforeMethod public void setUp() {
    component = createMock(Component.class);
    bounds = new Rectangle(80, 60);
    query = new ComponentBoundsQuery(component);
  }

  public void shouldReturnBoundsOfComponent() {
    new EasyMockTemplate(component) {
      protected void expectations() {
        expect(component.getBounds()).andReturn(bounds);
      }

      protected void codeToTest() {
        assertThat(query.executeInEDT()).isEqualTo(bounds);
      }
    }.run();
  }

}