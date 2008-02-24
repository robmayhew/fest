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
package org.fest.swing.task;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import java.awt.Component;

import javax.accessibility.AccessibleAction;
import javax.swing.JTextField;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.exception.ActionFailedException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link PerformDefaultAccessibleActionTask}</code>.
 *
 * @author Alex Ruiz
 */
public class PerformDefaultAccessibleActionTaskTest {

  private Component component;
  private AccessibleActionFinder finder;
  private AccessibleAction action;
  private PerformDefaultAccessibleActionTask task;

  @BeforeMethod public void setUp() {
    component = new JTextField();
    finder = createMock(AccessibleActionFinder.class);
    action = createMock(AccessibleAction.class);
  }

  @Test public void shouldExecuteFirstActionInAccessibleAction() {
    new EasyMockTemplate(finder, action) {
      protected void expectations() {
        expect(finder.accessibleActionFrom(component)).andReturn(action);
        expect(action.getAccessibleActionCount()).andReturn(1);
        expect(action.doAccessibleAction(0)).andReturn(true);
      }

      protected void codeToTest() {
        task = new PerformDefaultAccessibleActionTask(finder, component);
        task.run();
      }
    }.run();
  }

  @Test(expectedExceptions = ActionFailedException.class)
  public void shouldThrowErrorIfAccessibleActionIsNull() {
    new EasyMockTemplate(finder, action) {
      protected void expectations() {
        expect(finder.accessibleActionFrom(component)).andReturn(null);
      }

      protected void codeToTest() {
        task = new PerformDefaultAccessibleActionTask(finder, component);
      }
    }.run();
  }

  @Test(expectedExceptions = ActionFailedException.class)
  public void shouldThrowErrorIfAccessibleActionIsEmpty() {
    new EasyMockTemplate(finder, action) {
      protected void expectations() {
        expect(finder.accessibleActionFrom(component)).andReturn(action);
        expect(action.getAccessibleActionCount()).andReturn(0);
      }

      protected void codeToTest() {
        task = new PerformDefaultAccessibleActionTask(finder, component);
      }
    }.run();
  }
}
