/*
 * Created on Oct 18, 2007
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

import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.AWTEventListenerProxy;
import java.util.ArrayList;
import java.util.List;

import org.fest.mocks.EasyMockTemplate;

import static java.awt.AWTEvent.COMPONENT_EVENT_MASK;
import static java.awt.AWTEvent.MOUSE_EVENT_MASK;
import static java.awt.AWTEvent.MOUSE_MOTION_EVENT_MASK;
import static java.awt.AWTEvent.PAINT_EVENT_MASK;
import static java.awt.AWTEvent.WINDOW_EVENT_MASK;
import static java.awt.Toolkit.getDefaultToolkit;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.Reflection.field;

import static org.fest.swing.monitor.MockContext.MethodToMock.ADD_CONTEXT_FOR;
import static org.fest.swing.monitor.MockContext.MethodToMock.ALL_EVENT_QUEUES;
import static org.fest.swing.monitor.MockContext.MethodToMock.EVENT_QUEUE_FOR;
import static org.fest.swing.monitor.MockContext.MethodToMock.ROOT_WINDOWS;
import static org.fest.swing.monitor.MockWindows.MethodToMock.IS_READY;
import static org.fest.swing.monitor.MockWindows.MethodToMock.MARK_EXISTING;
import static org.fest.swing.monitor.MockWindowsStatus.MethodToMock.CHECK_IF_READY;

import org.fest.swing.TestFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.easymock.classextension.EasyMock.*;

/**
 * Tests for <code>{@link WindowMonitor}</code>.
 *
 * @author Alex Ruiz
 */
public class WindowMonitorTest {

  private static final long WINDOWS_AVAILABILITY_MONITOR_EVENT_MASK = MOUSE_MOTION_EVENT_MASK | MOUSE_EVENT_MASK | PAINT_EVENT_MASK;

  private static final long CONTEXT_MONITOR_EVENT_MASK = WINDOW_EVENT_MASK | COMPONENT_EVENT_MASK;

  private WindowMonitor monitor;
  
  private Windows windows;
  private Context context;
  private WindowStatus windowStatus;
  
  private TestFrame frame;
  
  @BeforeMethod public void setUp() {
    monitor = WindowMonitor.instance();
    frame = new TestFrame(getClass());
  }
  
  @AfterMethod public void tearDown() {
    frame.beDisposed();
  }

  @Test public void shouldAttachMonitors() {
    assertThat(listenersInToolkit(ContextMonitor.class, CONTEXT_MONITOR_EVENT_MASK)).isEqualTo(1);
    assertThat(listenersInToolkit(WindowAvailabilityMonitor.class, WINDOWS_AVAILABILITY_MONITOR_EVENT_MASK)).isEqualTo(1);
    // TODO Test 'populateExistingWindows'
  }

  private static int listenersInToolkit(Class<? extends AWTEventListener> targetType, long eventMask) {
    int count = 0;
    for (AWTEventListener l : getDefaultToolkit().getAWTEventListeners(eventMask))
      if (isInstanceOf(l, targetType)) count++;
    return count;
  }
  
  private static boolean isInstanceOf(AWTEventListener l, Class<? extends AWTEventListener> type) {
    if (type.isAssignableFrom(l.getClass())) return true;
    if (!(l instanceof AWTEventListenerProxy)) return false;
    AWTEventListenerProxy proxy = (AWTEventListenerProxy)l;
    WeakEventListener wrapper = (WeakEventListener)proxy.getListener();
    return type.isAssignableFrom(wrapper.listenerReference.get().getClass());
  }
  
  @Test public void shouldReturnWindowIsReady() throws Exception {
    createAndInjectMocks();
    new EasyMockTemplate(windows, context, windowStatus) {
      @Override protected void expectations() {
        expect(windows.isReady(frame)).andReturn(true);
      }
      
      @Override protected void codeToTest() {
        assertThat(monitor.isWindowReady(frame)).isTrue();
      }
    }.run();
  }
  
  @Test public void shouldReturnWindowIsNotReadyAndCheckWindow() throws Exception {
    createAndInjectMocks();
    new EasyMockTemplate(windows, context, windowStatus) {
      @Override protected void expectations() {
        expect(windows.isReady(frame)).andReturn(false);
        windowStatus.checkIfReady(frame);
      }
      
      @Override protected void codeToTest() {
        assertThat(monitor.isWindowReady(frame)).isFalse();
      }
    }.run();    
  }
  
  @Test public void shouldReturnEventQueueForComponent() throws Exception {
    createAndInjectMocks();
    final EventQueue queue = new EventQueue();
    new EasyMockTemplate(windows, context, windowStatus) {
      @Override protected void expectations() {
        expect(context.eventQueueFor(frame)).andReturn(queue);
      }
      
      @Override protected void codeToTest() {
        assertThat(monitor.eventQueueFor(frame)).isSameAs(queue);
      }
    }.run();    
  }
  
  @Test public void shouldReturnAllEventQueues() throws Exception {
    createAndInjectMocks();
    final List<EventQueue> allQueues = new ArrayList<EventQueue>();
    new EasyMockTemplate(windows, context, windowStatus) {
      @Override protected void expectations() {
        expect(context.allEventQueues()).andReturn(allQueues);
      }
      
      @Override protected void codeToTest() {
        assertThat(monitor.allEventQueues()).isSameAs(allQueues);
      }
    }.run();    
  }
  
  @Test public void shouldReturnRootWindows() throws Exception {
    createAndInjectMocks();
    final List<Window> rootWindows = new ArrayList<Window>();
    new EasyMockTemplate(windows, context, windowStatus) {
      @Override protected void expectations() {
        expect(context.rootWindows()).andReturn(rootWindows);
      }
      
      @Override protected void codeToTest() {
        assertThat(monitor.rootWindows()).isSameAs(rootWindows);
      }
    }.run();    
  }
  
  private void createAndInjectMocks() throws Exception {
    createMocks();
    injectMocks();
  }
  
  private void createMocks() throws Exception {
    windows = MockWindows.mock(IS_READY, MARK_EXISTING);
    context = MockContext.mock(ADD_CONTEXT_FOR, ALL_EVENT_QUEUES, EVENT_QUEUE_FOR, ROOT_WINDOWS);
    windowStatus = MockWindowsStatus.mock(CHECK_IF_READY);
  }
  
  private void injectMocks() {
    field("windows").ofType(Windows.class).in(monitor).set(windows);
    field("context").ofType(Context.class).in(monitor).set(context);
    field("windowStatus").ofType(WindowStatus.class).in(monitor).set(windowStatus);
  }
}
