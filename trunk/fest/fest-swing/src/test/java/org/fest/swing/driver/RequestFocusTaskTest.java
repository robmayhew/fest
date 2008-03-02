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
import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.driver.RequestFocusTask;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

/**
 * Tests for <code>{@link RequestFocusTask}</code>.
 *
 * @author Alex Ruiz
 */
public class RequestFocusTaskTest {

  private RequestFocusTask task;
  private Component component;
  
  @BeforeMethod public void setUp() throws Exception {
    Method requestFocusInWindow = Component.class.getDeclaredMethod("requestFocusInWindow");
    component = createMock(Component.class, new Method[] { requestFocusInWindow });
    task = new RequestFocusTask(component);
  }
  
  @Test public void shouldCallRequestFocusInWindow() {
    new EasyMockTemplate(component) {

      protected void expectations() {
        expect(component.requestFocusInWindow()).andReturn(true);
      }
      
      protected void codeToTest() {
        task.run();
      }
    }.run();
  }
}
