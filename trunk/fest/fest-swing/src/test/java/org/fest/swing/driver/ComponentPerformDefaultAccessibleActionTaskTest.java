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
import java.util.Locale;

import javax.accessibility.*;
import javax.swing.JTextField;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.GuiQuery;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.UnexpectedException;
import org.fest.swing.testing.TestGroups;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Tests for <code>{@link ComponentPerformDefaultAccessibleActionTask}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = TestGroups.GUI)
public class ComponentPerformDefaultAccessibleActionTaskTest {

  private AccessibleAction accessibleAction;
  private AccessibleContextStub accessibleContext;
  private Component component;
  private ComponentPerformDefaultAccessibleActionTask task;

  @BeforeMethod public void setUp() {
    accessibleAction = createMock(AccessibleAction.class);
    accessibleContext = new AccessibleContextStub(accessibleAction);
    component = MyComponent.newComponent(accessibleContext);
    task = ComponentPerformDefaultAccessibleActionTask.performDefaultAccessibleActionTask(component);
  }

  public void shouldExecuteFirstActionInAccessibleAction() {
    new EasyMockTemplate(accessibleAction) {
      protected void expectations() {
        expect(accessibleAction.getAccessibleActionCount()).andReturn(1);
        expect(accessibleAction.doAccessibleAction(0)).andReturn(true);
      }

      protected void codeToTest() {
        execute(task);
      }
    }.run();
  }

  public void shouldThrowErrorIfAccessibleActionIsNull() {
    accessibleContext.accessibleAction(null);
    try {
      new EasyMockTemplate(accessibleAction) {
        protected void expectations() {}
  
        protected void codeToTest() {
          execute(task);
        }
      }.run();
      fail();
    } catch (UnexpectedException e) {
      assertActionFailedThrown(e);
    }
  }

  public void shouldThrowErrorIfAccessibleActionIsEmpty() {
    try {
      new EasyMockTemplate(accessibleAction) {
        protected void expectations() {
          expect(accessibleAction.getAccessibleActionCount()).andReturn(0);
        }
  
        protected void codeToTest() {
          execute(task);
        }
      }.run();
      fail();
    } catch (UnexpectedException e) {
      assertActionFailedThrown(e);
    }
  }

  private void assertActionFailedThrown(UnexpectedException e) {
    assertThat(e.getCause()).isInstanceOf(ActionFailedException.class)
                            .message().contains("Unable to perform accessible action for");
  }
  
  private static class MyComponent extends JTextField {
    private static final long serialVersionUID = 1L;
    
    private final AccessibleContext accessibleContext;

    static MyComponent newComponent(final AccessibleContext accessibleContext) {
      return execute(new GuiQuery<MyComponent>() {
        protected MyComponent executeInEDT() {
          return new MyComponent(accessibleContext);
        }
      });
    }
    
    MyComponent(AccessibleContext accessibleContext) {
      this.accessibleContext = accessibleContext;
    }

    @Override public AccessibleContext getAccessibleContext() {
      return accessibleContext;
    }
  }
  
  private static class AccessibleContextStub extends AccessibleContext {
    private AccessibleAction accessibleAction;

    AccessibleContextStub(AccessibleAction newAccessibleAction) {
      accessibleAction(newAccessibleAction);
    }
    
    void accessibleAction(AccessibleAction newAccessibleAction) {
      this.accessibleAction = newAccessibleAction; 
    }

    @Override public AccessibleAction getAccessibleAction() {
      return accessibleAction;
    }

    public Accessible getAccessibleChild(int i) { return null; }
    public int getAccessibleChildrenCount() { return 0; }
    public int getAccessibleIndexInParent() { return 0; }
    public AccessibleRole getAccessibleRole() { return null; }
    public AccessibleStateSet getAccessibleStateSet() { return null; }
    public Locale getLocale() { return null; }
  }
}
