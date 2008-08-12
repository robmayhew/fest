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
import javax.swing.JTextField;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.GuiQuery;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.testing.TestGroups;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

/**
 * Tests for <code>{@link ComponentPerformDefaultAccessibleActionTask}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = TestGroups.GUI)
public class ComponentPerformDefaultAccessibleActionTaskTest {

  private Component component;
  private AccessibleContext context;
  private AccessibleAction action;

  @BeforeMethod public void setUp() {
    context = createMock(AccessibleContext.class);
    action = createMock(AccessibleAction.class);
    component = new GuiQuery<Component>() {
      protected Component executeInEDT() throws Throwable {
        return new MyComponent(context);
      }
    }.run();
  }

  @Test public void shouldExecuteFirstActionInAccessibleAction() {
    new EasyMockTemplate(context, action) {
      protected void expectations() {
        expect(context.getAccessibleAction()).andReturn(action);
        expect(action.getAccessibleActionCount()).andReturn(1);
        expect(action.doAccessibleAction(0)).andReturn(true);
      }

      protected void codeToTest() {
        ComponentPerformDefaultAccessibleActionTask.performDefaultAccessibleActionOf(component).executeInEDT();
      }
    }.run();
  }

  @Test(expectedExceptions = ActionFailedException.class)
  public void shouldThrowErrorIfAccessibleActionIsNull() {
    new EasyMockTemplate(context) {
      protected void expectations() {
        expect(context.getAccessibleAction()).andReturn(null);
      }

      protected void codeToTest() {
        ComponentPerformDefaultAccessibleActionTask.performDefaultAccessibleActionOf(component);
      }
    }.run();
  }

  @Test(expectedExceptions = ActionFailedException.class)
  public void shouldThrowErrorIfAccessibleActionIsEmpty() {
    new EasyMockTemplate(context, action) {
      protected void expectations() {
        expect(context.getAccessibleAction()).andReturn(action);
        expect(action.getAccessibleActionCount()).andReturn(0);
      }

      protected void codeToTest() {
        ComponentPerformDefaultAccessibleActionTask.performDefaultAccessibleActionOf(component);
      }
    }.run();
  }
  
  private static class MyComponent extends JTextField {
    private static final long serialVersionUID = 1L;
    
    private final AccessibleContext accessibleContext;

    MyComponent(AccessibleContext accessibleContext) {
      this.accessibleContext = accessibleContext;
    }

    @Override public AccessibleContext getAccessibleContext() {
      return accessibleContext;
    }
  }
}
