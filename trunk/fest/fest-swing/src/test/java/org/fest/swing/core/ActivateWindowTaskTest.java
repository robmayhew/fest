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
package org.fest.swing.core;

import java.awt.Window;
import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

/**
 * Tests for <code>{@link ActivateWindowTask}</code>.
 *
 * @author Alex Ruiz
 */
public class ActivateWindowTaskTest {

  private ActivateWindowTask task;
  private Window w;
  
  @BeforeMethod public void setUp() throws Exception {
    Method toFront = Window.class.getDeclaredMethod("toFront");
    w = createMock(Window.class, new Method[] { toFront });
    task = new ActivateWindowTask(w);
  }
  
  @Test public void shouldActivateWindow() {
    new EasyMockTemplate(w) {
      protected void expectations() {
        w.toFront();
        expectLastCall().atLeastOnce();
      }

      protected void codeToTest() {
        task.run();
      }
    }.run();
  }
}
