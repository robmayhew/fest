/*
 * Created on Sep 2, 2008
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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.swing.testing.TestGroups.EDT_ACTION;

/**
 * Tests for <code>{@link WindowHideAndDisposeTask}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = EDT_ACTION)
public class WindowHideAndDisposeTaskTest {

  private Window window;
  private WindowHideAndDisposeTask task;
  
  @BeforeMethod public void setUp() {
    window = createMock(Window.class);
    task = new WindowHideAndDisposeTask(window);
  }
  
  public void shouldHideAndDisposeWindow() {
    new EasyMockTemplate(window) {
      protected void expectations() {
        window.setVisible(false);
        expectLastCall().once();
        window.dispose();
        expectLastCall().once();
      }

      protected void codeToTest() {
        task.executeInEDT();
      }
    }.run();
  }
}
