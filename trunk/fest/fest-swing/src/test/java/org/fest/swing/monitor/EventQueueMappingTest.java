/*
 * Created on Mar 22, 2008
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
package org.fest.swing.monitor;

import java.awt.Component;
import java.awt.EventQueue;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.testing.ToolkitStub;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link EventQueueMapping}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class EventQueueMappingTest {

  private EventQueue eventQueue;
  private ToolkitStub toolkit;
  private ComponentWithCustomEventQueue component;
  private EventQueueMapping mapping;
  private Map<Component, WeakReference<EventQueue>> queueMap;
  
  @BeforeMethod public void setUp() {
    eventQueue = new EventQueue();
    toolkit = new ToolkitStub(eventQueue);
    component = new ComponentWithCustomEventQueue(toolkit);
    mapping = new EventQueueMapping();
    queueMap = mapping.queueMap;
  }
  
  @Test public void shouldAddEventQueue() {
    mapping.addQueueFor(component);
    EventQueue storedEventQueue = queueMap.get(component).get();
    assertThat(storedEventQueue).isSameAs(eventQueue);
  }

  @Test(dependsOnMethods = "shouldAddEventQueue")
  public void shouldReturnEventQueue() {
    mapping.addQueueFor(component);
    EventQueue storedEventQueue = mapping.queueFor(component);
    assertThat(storedEventQueue).isSameAs(eventQueue);
  }

  @Test(dependsOnMethods = "shouldReturnEventQueue")
  public void shouldReturnEventQueueInComponentIfNoMappingFound() {
    assertThat(queueMap.keySet()).excludes(eventQueue);
    EventQueue storedEventQueue = mapping.queueFor(component);
    assertThat(storedEventQueue).isSameAs(eventQueue);
  }

  @Test(dependsOnMethods = "shouldAddEventQueue")
  public void shouldReturnStoredEventQueue() {
    mapping.addQueueFor(component);
    EventQueue storedEventQueue = mapping.storedQueueFor(component);
    assertThat(storedEventQueue).isSameAs(eventQueue);
  }
  
  @Test(dependsOnMethods = "shouldReturnStoredEventQueue")
  public void shouldReturnNullIfEventQueueNotStored() {
    assertThat(queueMap.keySet()).excludes(eventQueue);
    EventQueue storedEventQueue = mapping.storedQueueFor(component);
    assertThat(storedEventQueue).isNull();
  }
  
  @Test(dependsOnMethods = "shouldReturnNullIfEventQueueNotStored") 
  public void shouldReturnNullIfEventQueueReferenceIsNull() {
    queueMap.put(component, null);
    EventQueue storedEventQueue = mapping.storedQueueFor(component);
    assertThat(storedEventQueue).isNull();
  }

  @Test(dependsOnMethods = "shouldAddEventQueue")
  public void shouldReturnAllQueues() {
    EventQueue anotherEventQueue = new EventQueue();
    ToolkitStub anotherToolkit = new ToolkitStub(anotherEventQueue);
    ComponentWithCustomEventQueue anotherComponent = new ComponentWithCustomEventQueue(anotherToolkit);
    mapping.addQueueFor(component);
    mapping.addQueueFor(anotherComponent);
    Collection<EventQueue> allEventQueues = mapping.eventQueues();
    assertThat(allEventQueues).containsOnly(eventQueue, anotherEventQueue);
  }
}
