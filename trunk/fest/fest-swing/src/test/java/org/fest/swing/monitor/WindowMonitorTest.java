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
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.AWTEventListenerProxy;
import java.util.ArrayList;
import java.util.List;

import org.fest.mocks.EasyMockTemplate;
import org.fest.reflect.field.Invoker;

import static java.awt.AWTEvent.COMPONENT_EVENT_MASK;
import static java.awt.AWTEvent.MOUSE_EVENT_MASK;
import static java.awt.AWTEvent.MOUSE_MOTION_EVENT_MASK;
import static java.awt.AWTEvent.PAINT_EVENT_MASK;
import static java.awt.AWTEvent.WINDOW_EVENT_MASK;
import static java.awt.Toolkit.getDefaultToolkit;
import static org.easymock.EasyMock.expect;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.Reflection.field;

import static org.fest.swing.monitor.WindowVisibilityMonitors.assertWindowVisibilityMonitorCountIn;

import org.fest.swing.TestFrame;
import org.fest.swing.listener.WeakEventListener;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link WindowMonitor}</code>.
 *
 * @author Alex Ruiz
 */
public class WindowMonitorTest {

  private static final long WINDOWS_AVAILABILITY_MONITOR_EVENT_MASK = MOUSE_MOTION_EVENT_MASK | MOUSE_EVENT_MASK | PAINT_EVENT_MASK;

  private static final long CONTEXT_MONITOR_EVENT_MASK = WINDOW_EVENT_MASK | COMPONENT_EVENT_MASK;

  private WindowMonitor monitor;
  
  private Windows mockWindows;
  private Context mockContext;
  private WindowStatus mockWindowStatus;
  
  private Windows windows;
  private Context context;
  private WindowStatus windowStatus;

  private TestFrame frame;

  private boolean mocksInjected;
  
  @BeforeMethod public void setUp() {
    frame = new TestFrame(getClass());
    monitor = WindowMonitor.instance();
  }
  
  @AfterMethod public void tearDown() {
    frame.destroy();
    if (mocksInjected) restoreOriginal();
  }

  private void restoreOriginal() {
    windowsField().set(windows);
    contextField().set(context);
    windowsStatusField().set(windowStatus);
  }

  @Test public void shouldAttachMonitors() {
    assertThat(listenersInToolkit(ContextMonitor.class, CONTEXT_MONITOR_EVENT_MASK)).isEqualTo(1);
    assertThat(listenersInToolkit(WindowAvailabilityMonitor.class, WINDOWS_AVAILABILITY_MONITOR_EVENT_MASK)).isEqualTo(1);
    assertPopulatedExistingWindows();
  }

  private void assertPopulatedExistingWindows() {
    for (Frame f : Frame.getFrames()) assertPopulated(f);
  }
  
  private void assertPopulated(Window w) {
    assertWindowVisibilityMonitorCountIn(w, 1);
    for (Window owned : w.getOwnedWindows()) assertPopulated(owned);
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
    return type.isAssignableFrom(wrapper.realListener().getClass());
  }
  
  @Test(dependsOnMethods = "shouldAttachMonitors")
  public void shouldReturnWindowIsReady() throws Exception {
    createAndInjectMocks();
    new EasyMockTemplate(mockWindows, mockContext, mockWindowStatus) {
      @Override protected void expectations() {
        expect(mockWindows.isReady(frame)).andReturn(true);
      }
      
      @Override protected void codeToTest() {
        assertThat(monitor.isWindowReady(frame)).isTrue();
      }
    }.run();
  }
  
  @Test(dependsOnMethods = "shouldAttachMonitors")
  public void shouldReturnWindowIsNotReadyAndCheckWindow() throws Exception {
    createAndInjectMocks();
    new EasyMockTemplate(mockWindows, mockContext, mockWindowStatus) {
      @Override protected void expectations() {
        expect(mockWindows.isReady(frame)).andReturn(false);
        mockWindowStatus.checkIfReady(frame);
      }
      
      @Override protected void codeToTest() {
        assertThat(monitor.isWindowReady(frame)).isFalse();
      }
    }.run();    
  }
  
  @Test(dependsOnMethods = "shouldAttachMonitors")
  public void shouldReturnEventQueueForComponent() throws Exception {
    createAndInjectMocks();
    final EventQueue queue = new EventQueue();
    new EasyMockTemplate(mockWindows, mockContext, mockWindowStatus) {
      @Override protected void expectations() {
        expect(mockContext.eventQueueFor(frame)).andReturn(queue);
      }
      
      @Override protected void codeToTest() {
        assertThat(monitor.eventQueueFor(frame)).isSameAs(queue);
      }
    }.run();    
  }
  
  @Test(dependsOnMethods = "shouldAttachMonitors")
  public void shouldReturnAllEventQueues() throws Exception {
    createAndInjectMocks();
    final List<EventQueue> allQueues = new ArrayList<EventQueue>();
    new EasyMockTemplate(mockWindows, mockContext, mockWindowStatus) {
      @Override protected void expectations() {
        expect(mockContext.allEventQueues()).andReturn(allQueues);
      }
      
      @Override protected void codeToTest() {
        assertThat(monitor.allEventQueues()).isSameAs(allQueues);
      }
    }.run();    
  }
  
  @Test(dependsOnMethods = "shouldAttachMonitors")
  public void shouldReturnRootWindows() throws Exception {
    createAndInjectMocks();
    final List<Window> rootWindows = new ArrayList<Window>();
    new EasyMockTemplate(mockWindows, mockContext, mockWindowStatus) {
      @Override protected void expectations() {
        expect(mockContext.rootWindows()).andReturn(rootWindows);
      }
      
      @Override protected void codeToTest() {
        assertThat(monitor.rootWindows()).isSameAs(rootWindows);
      }
    }.run();    
  }
  
  private void createAndInjectMocks() throws Exception {
    createMocks();
    saveOriginal();
    injectMocks();    
  }
  
  private void createMocks() throws Exception {
    mockWindows = MockWindows.mock();
    mockContext = MockContext.mock();
    mockWindowStatus = MockWindowsStatus.mock();
  }

  private void saveOriginal() {
    windows = windowsField().get();
    context = contextField().get();
    windowStatus = windowsStatusField().get();
  }
  
  private void injectMocks() {
    mocksInjected = true;
    windowsField().set(mockWindows);
    contextField().set(mockContext);
    windowsStatusField().set(mockWindowStatus);
  }

  private Invoker<WindowStatus> windowsStatusField() {
    return field("windowStatus").ofType(WindowStatus.class).in(monitor);
  }

  private Invoker<Context> contextField() {
    return field("context").ofType(Context.class).in(monitor);
  }

  private Invoker<Windows> windowsField() {
    return field("windows").ofType(Windows.class).in(monitor);
  }
}
