/*
 * Created on Feb 23, 2008
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

import javax.accessibility.AccessibleAction;
import javax.accessibility.AccessibleContext;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link AccessibleActionFinder}</code>.
 *
 * @author Alex Ruiz
 */
public class AccessibleActionFinderTest {

  private Component component;
  private AccessibleContext context;
  private AccessibleAction action;
  private AccessibleActionFinder finder;

  @BeforeMethod public void setUp() throws Exception {
    component = createMock(Component.class);
    context = createMock(AccessibleContext.class);
    action = createMock(AccessibleAction.class);
    finder = new AccessibleActionFinder();
  }

  @Test public void shouldFindAccessibleAction() {
    new EasyMockTemplate(component, context, action) {
      protected void expectations() {
        expect(component.getAccessibleContext()).andReturn(context);
        expect(context.getAccessibleAction()).andReturn(action);
      }

      protected void codeToTest() {
        AccessibleAction found = finder.accessibleActionFrom(component);
        assertThat(found).isSameAs(action);
      }
    }.run();
  }
}
