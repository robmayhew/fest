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

import java.awt.Component;
import java.lang.reflect.Method;

import javax.accessibility.AccessibleAction;
import javax.accessibility.AccessibleContext;

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

/**
 * Tests for <code>{@link PerformAccessibleActionTask}</code>.
 *
 * @author Alex Ruiz
 */
public class PerformAccessibleActionTaskTest {

  private Component component;
  private AccessibleContext accessibleContext;
  private AccessibleAction accessibleAction;
  private PerformAccessibleActionTask task;
  
  @Test public void shouldExecuteFirstActionInAccessibleAction() throws Exception {
    Method getAccessibleContext = Component.class.getDeclaredMethod("getAccessibleContext");
    component = createMock(Component.class, new Method[] { getAccessibleContext });
    accessibleContext = createMock(AccessibleContext.class);
    accessibleAction = createMock(AccessibleAction.class);
    new EasyMockTemplate(component, accessibleContext, accessibleAction) {
      protected void expectations() {
        expect(component.getAccessibleContext()).andReturn(accessibleContext);
        expect(accessibleContext.getAccessibleAction()).andReturn(accessibleAction);
        expect(accessibleAction.getAccessibleActionCount()).andReturn(1);
        expect(accessibleAction.doAccessibleAction(0)).andReturn(true);
      }

      protected void codeToTest() {
        task = new PerformAccessibleActionTask(component);
        task.run();
      }
    }.run();
  }
}
