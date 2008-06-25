/*
 * Created on Jun 24, 2008
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
import java.awt.event.AWTEventListener;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.listener.WeakEventListener;
import org.fest.swing.testing.ToolkitStub;

import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link EventNormalizer}</code> when configured to track drag events.
 *
 * @author Alex Ruiz
 */
@Test
public class DragTrackingEventNormalizerTest {

  private EventNormalizer eventNormalizer;
  
  @BeforeMethod public void setUp() {
    eventNormalizer = new EventNormalizer(true);
  }
  
  public void shouldReplaceEventQueueWhenStartListening() {
    ToolkitStub toolkit = ToolkitStub.createNew();
    EventQueueStub eventQueue = new EventQueueStub();
    toolkit.eventQueue(eventQueue);
    int mask = 8;
    eventNormalizer.startListening(toolkit, createMock(AWTEventListener.class), mask);
    assertEventNormalizerIsInToolkit(toolkit, mask);
    assertThat(eventQueue.pushedEventQueue).isInstanceOf(DragAwareEventQueue.class);
  }

  private void assertEventNormalizerIsInToolkit(ToolkitStub toolkit, int mask) {
    List<WeakEventListener> listeners = toolkit.eventListenersUnderEventMask(mask, WeakEventListener.class);
    assertThat(listeners).isNotNull().hasSize(1);
    WeakEventListener weakEventListener = listeners.get(0);
    assertThat(weakEventListener.underlyingListener()).isSameAs(eventNormalizer);
  }
  
  private static class EventQueueStub extends EventQueue {
    EventQueue pushedEventQueue;

    EventQueueStub() {}
    
    @Override public synchronized void push(EventQueue newEventQueue) {
      this.pushedEventQueue = newEventQueue;
      super.push(newEventQueue);
    }
  }
}
