/*
 * Created on Jun 22, 2008
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
package org.fest.swing.input;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;

/**
 * Tests for <code>{@link ToolkitPushDragAwereEventQueueTask}</code>.
 *
 * @author Alex Ruiz 
 */
@Test
public class ToolkitPushDragAwereEventQueueTaskTest {

  private Toolkit toolkit;
  private DragAwareEventQueue dragAwareEventQueue;
  private EventQueue eventQueue;
  private ToolkitPushDragAwereEventQueueTask task;
  
  @BeforeMethod public void setUp() {
    toolkit = createMock(Toolkit.class);
    dragAwareEventQueue = new DragAwareEventQueue(toolkit, 0L, createMock(AWTEventListener.class));
    eventQueue = createMock(EventQueue.class);
    task = new ToolkitPushDragAwereEventQueueTask(toolkit, dragAwareEventQueue);
  }
  
  public void shouldReplaceEventQueue() {
    new EasyMockTemplate(toolkit, eventQueue) {
      protected void expectations() {
        expect(toolkit.getSystemEventQueue()).andReturn(eventQueue);
        eventQueue.push(dragAwareEventQueue);
        expectLastCall().once();
      }

      protected void codeToTest() {
        task.executeInEDT();
      }
    }.run();
  }
}
