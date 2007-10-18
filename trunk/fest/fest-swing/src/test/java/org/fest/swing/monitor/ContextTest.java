/*
 * Created on Oct 14, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.monitor;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextField;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.Reflection.field;

import org.fest.swing.TestFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link Context}</code>.
 *
 * @author Alex Ruiz
 */
public class ContextTest {

  private Map<EventQueue, Map<Window, Boolean>> contexts;
  private Map<Component, WeakReference<EventQueue>> queues;
  private TestFrame frame;
  
  private Context context;
  
  @SuppressWarnings("unchecked") 
  @BeforeMethod public void setUp() {
    context = new Context();
    contexts = contextMapField("contexts");
    queues = contextMapField("queues");
    frame = new TestFrame(getClass());
  }
  
  private Map contextMapField(String name) {
    return field(name).ofType(Map.class).in(context).get();
  }
  
  @AfterMethod public void tearDown() {
    frame.beDisposed();
  }
  
  @Test public void shouldHaveSystemEventQueueInContext() {
    assertMapContainsOnlyOneKey(contexts, defaultSystemEventQueue());
  }

  @Test(dependsOnMethods = "shouldHaveSystemEventQueueInContext")
  public void shouldAddContextIfGivenComponentIsWindowAndAddQueue() {
    frame.beVisible();
    context.addContextFor(frame);
    assertMapContainsOnlyOneKey(contexts, defaultSystemEventQueue());
    Map<Window, Boolean> context = contexts.get(defaultSystemEventQueue());
    assertMapContainsOnlyOneKey(context, frame);
    assertQueueAddedFor(frame);
  }

  @Test(dependsOnMethods = "shouldHaveSystemEventQueueInContext")
  public void shouldNotAddContextIfGivenComponentIsNotWindowAndAddQueue() {
    JTextField textField = new JTextField();
    frame.add(textField);
    frame.beVisible();
    context.addContextFor(textField);
    assertMapContainsOnlyOneKey(contexts, defaultSystemEventQueue());
    assertContextExcludes(defaultSystemEventQueue(), textField);
    assertQueueAddedFor(textField);
  }

  private void assertQueueAddedFor(Component c) {
    assertMapContainsOnlyOneKey(queues, c);
    WeakReference<EventQueue> queueReference = queues.get(c);
    assertThat(queueReference.get()).isSameAs(c.getToolkit().getSystemEventQueue());
  }
  
  private void assertMapContainsOnlyOneKey(Map<?, ?> map, Object key) {
    assertThat(map.size()).isEqualTo(1);
    assertThat(map.keySet()).contains(key);
  }
  
  @Test(dependsOnMethods = "shouldHaveSystemEventQueueInContext")
  public void shouldLookupQueueForGivenComponent() {
    frame.beVisible();
    EventQueue queue = frame.getToolkit().getSystemEventQueue();
    WeakReference<EventQueue> reference = new WeakReference<EventQueue>(queue);
    queues.put(frame, reference);
    assertThat(context.lookupEventQueueFor(frame)).isSameAs(queue);
  }
  
  @Test(dependsOnMethods = { "shouldHaveSystemEventQueueInContext", 
                             "shouldAddContextIfGivenComponentIsWindowAndAddQueue" })
  public void shouldReturnAllRootWindows() {
    TestFrame anotherFrame = new TestFrame(getClass());
    anotherFrame.beVisible();
    frame.beVisible();
    context.addContextFor(frame);
    context.addContextFor(anotherFrame);
    Collection<Window> rootWindows = context.rootWindows();
    Object[] frames = Frame.getFrames();
    assertThat(rootWindows.size()).isEqualTo(frames.length);
    assertThat(rootWindows).contains(frame, anotherFrame);
    assertThat(rootWindows).contains(frames);
    anotherFrame.beDisposed();
  }
  
  @Test(dependsOnMethods = "shouldHaveSystemEventQueueInContext")
  public void shouldReturnEventQueueInMapForGivenComponent() {
    EventQueue queue = new EventQueue();
    queues.put(frame, new WeakReference<EventQueue>(queue));
    assertThat(context.eventQueueFor(frame)).isSameAs(queue);
  }

  @Test(dependsOnMethods = "shouldHaveSystemEventQueueInContext")
  public void shouldReturnComponentEventQueueIfEventQueueInMapIsNull() {
    queues.put(frame, new WeakReference<EventQueue>(null));
    assertThat(context.eventQueueFor(frame)).isSameAs(defaultSystemEventQueue());    
  }

  @Test(dependsOnMethods = "shouldHaveSystemEventQueueInContext")
  public void shouldReturnComponentEventQueueIfEventQueueReferenceInMapIsNull() {
    queues.put(frame, null);
    assertThat(context.eventQueueFor(frame)).isSameAs(defaultSystemEventQueue());    
  }
  
  @Test(dependsOnMethods = "shouldHaveSystemEventQueueInContext")
  public void shouldReturnComponentEventQueueIfComponentNotInMap() {
    queues.clear();
    assertThat(context.eventQueueFor(frame)).isSameAs(defaultSystemEventQueue());    
  }

  @Test(dependsOnMethods = "shouldHaveSystemEventQueueInContext")
  public void shouldRemoveContextForComponentMappedWithDefaultEventQueue() {
    contexts.put(defaultSystemEventQueue(), contextWith(frame));
    context.removeContextFor(frame);
    assertContextExcludes(defaultSystemEventQueue(), frame);
  }
  
  @Test(dependsOnMethods = "shouldHaveSystemEventQueueInContext")
  public void shouldRemoveContextForComponentMappedWithNotDefaultEventQueue() {
    contexts.get(defaultSystemEventQueue()).remove(frame);
    EventQueue queue = new EventQueue();
    contexts.put(queue, contextWith(frame));
    context.removeContextFor(frame);
    assertContextExcludes(queue, frame);
  }

  private Map<Window, Boolean> contextWith(Window w) {
    Map<Window, Boolean> context = new HashMap<Window, Boolean>();
    context.put(w, true);
    return context;
  }
  
  private void assertContextExcludes(EventQueue queue, Component excluded) {
    assertThat(contexts.get(queue).keySet()).excludes(excluded);
  }
  
  private EventQueue defaultSystemEventQueue() {
    return Toolkit.getDefaultToolkit().getSystemEventQueue();
  }
}
