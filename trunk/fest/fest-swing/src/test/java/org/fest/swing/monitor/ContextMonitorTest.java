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

import java.applet.Applet;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Window;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTextField;

import org.fest.mocks.EasyMockTemplate;

import static java.awt.AWTEvent.COMPONENT_EVENT_MASK;
import static java.awt.AWTEvent.WINDOW_EVENT_MASK;
import static java.awt.event.WindowEvent.WINDOW_CLOSED;
import static java.awt.event.WindowEvent.WINDOW_CLOSING;
import static java.awt.event.WindowEvent.WINDOW_FIRST;
import static java.awt.event.WindowEvent.WINDOW_LAST;
import static java.awt.event.WindowEvent.WINDOW_OPENED;
import static org.easymock.EasyMock.expect;
import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.listener.WeakEventListener.createWithoutAttaching;
import static org.fest.swing.monitor.WindowVisibilityMonitors.assertWindowVisibilityMonitorCountIn;
import static org.fest.swing.util.ToolkitUtils.toolkitHasListenerUnderEventMask;

import org.fest.swing.TestFrame;
import org.fest.swing.listener.WeakEventListener;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ContextMonitor}</code>.
 *
 * @author Alex Ruiz
 */
public class ContextMonitorTest {
  
  private static final long EVENT_MASK = WINDOW_EVENT_MASK | COMPONENT_EVENT_MASK;

  private ContextMonitor monitor;
  
  private Windows windows;
  private Context context;
  private TestFrame frame;

  @BeforeMethod public void setUp() throws Exception {
    frame = new TestFrame(getClass());
    windows = MockWindows.mock();
    context = MockContext.mock();
  }

  @AfterMethod public void tearDown() {
    frame.destroy();
  }

  @Test public void shouldAttachItSelfToToolkit() {
    monitor = ContextMonitor.attachContextMonitor(new Windows(), new Context());
    WeakEventListener l = createWithoutAttaching(monitor);
    assertThat(toolkitHasListenerUnderEventMask(l, EVENT_MASK)).isTrue();
  }

  @Test public void shouldNotProcessEventIfComponentIsNotWindowOrApplet() {
    createMonitor();
    new EasyMockTemplate(windows, context) {
      @Override protected void expectations() {}

      @Override protected void codeToTest() {
        monitor.eventDispatched(new ComponentEvent(new JTextField(), 8));
      }
    }.run();
  }

  @Test public void shouldProcessEventWithIdEqualToWindowOpen() {
    createMonitor();
    assertWindowVisibilityMonitorCountIn(frame, 0);
    new EasyMockTemplate(windows, context) {
      @Override protected void expectations() {
        context.addContextFor(frame);
        windows.markAsShowing(frame);
        expectEventQueueLookupFor(frame);
      }
    
      @Override protected void codeToTest() {
        dispatchWindowOpenedEventToMonitor(frame);
      }
    }.run();
    assertWindowVisibilityMonitorCountIn(frame, 1);
  }
  
  @Test public void shouldProcessEventWithIdEqualToWindowOpenedAndMarkWindowAsReadyIfFileDialog() {
    createMonitor();
    final Window w = new FileDialog(frame);
    assertWindowVisibilityMonitorCountIn(w, 0);
    new EasyMockTemplate(windows, context) {
      @Override protected void expectations() {
        context.addContextFor(w);
        windows.markAsShowing(w);
        windows.markAsReady(w);
        expectEventQueueLookupFor(w);
      }
    
      @Override protected void codeToTest() {
        dispatchWindowOpenedEventToMonitor(w);
      }
    }.run();
    assertWindowVisibilityMonitorCountIn(w, 1);
  }

  @Test public void shouldProcessEventWithIdEqualToWindowClosedAndWithRootWindow() {
    createMonitor();
    new EasyMockTemplate(windows, context) {
      @Override protected void expectations() {
        context.removeContextFor(frame);
        windows.markAsClosed(frame);
        expectEventQueueLookupFor(frame);
      }
    
      @Override protected void codeToTest() {
        dispatchWindowClosedEventToMonitor(frame);
      }
    }.run();
  }

  @Test public void shouldProcessEventWithIdEqualToWindowClosedAndWithNotRootWindow() {
    final Applet applet = new Applet();
    frame.add(applet); 
    createMonitor();
    new EasyMockTemplate(windows, context) {
      @Override protected void expectations() {
        expectEventQueueLookupFor(applet);
      }
    
      @Override protected void codeToTest() {
        dispatchWindowClosedEventToMonitor(applet);
      }
    }.run();
  }

  @Test public void shouldNotProcessEventWithIdWindowClosing() {
    createMonitor();
    new EasyMockTemplate(windows, context) {
      @Override protected void expectations() {
        expectEventQueueLookupFor(frame);
      }
    
      @Override protected void codeToTest() {
        dispatchWindowClosingEventToMonitor(frame);
      }      
    }.run();
  }
  
  @Test public void shouldAddToContextIfComponentEventQueueNotEqualToSystemEventQueue() {
    createMonitor();
    new EasyMockTemplate(windows, context) {
      @Override protected void expectations() {
        expect(context.lookupEventQueueFor(frame)).andReturn(new EventQueue());
        context.addContextFor(frame);
      }
    
      @Override protected void codeToTest() {
        dispatchWindowClosingEventToMonitor(frame);
      }      
    }.run();
  }
  
  @Test(dataProvider = "eventsBetweenWindowFirstAndWindowLast") 
  public void shouldProcessEventWithIdInBetweenWindowFirstAndWindowLastAndWindowNotInContext(final int eventId) {
    createMonitor();
    assertWindowVisibilityMonitorCountIn(frame, 0);
    new EasyMockTemplate(windows, context) {
      @Override protected void expectations() {
        expect(context.rootWindows()).andReturn(new ArrayList<Window>());
        context.addContextFor(frame);
        windows.markAsShowing(frame);
        expectEventQueueLookupFor(frame);
      }
    
      @Override protected void codeToTest() {
        dispatchEventToMonitor(frame, eventId);
      }
    }.run();
    assertWindowVisibilityMonitorCountIn(frame, 1);
  }
  
  @Test(dataProvider = "eventsBetweenWindowFirstAndWindowLast") 
  public void shouldProcessEventWithIdInBetweenWindowFirstAndWindowLastAndWindowInContextAndClosed(final int eventId) {
    createMonitor();
    assertWindowVisibilityMonitorCountIn(frame, 0);
    new EasyMockTemplate(windows, context) {
      @Override protected void expectations() {
        expect(context.rootWindows()).andReturn(frameInList());
        expect(windows.isClosed(frame)).andReturn(true);
        context.addContextFor(frame);
        windows.markAsShowing(frame);
        expectEventQueueLookupFor(frame);
      }
    
      @Override protected void codeToTest() {
        dispatchEventToMonitor(frame, eventId);
      }
    }.run();
    assertWindowVisibilityMonitorCountIn(frame, 1);
  }

  @Test(dataProvider = "eventsBetweenWindowFirstAndWindowLast") 
  public void shouldProcessEventWithIdInBetweenWindowFirstAndWindowLastAndWindowInContextAndNotClosed(final int eventId) {
    createMonitor();
    assertWindowVisibilityMonitorCountIn(frame, 0);
    new EasyMockTemplate(windows, context) {
      @Override protected void expectations() {
        expect(context.rootWindows()).andReturn(frameInList());
        expect(windows.isClosed(frame)).andReturn(false);
        expectEventQueueLookupFor(frame);
      }
    
      @Override protected void codeToTest() {
        dispatchEventToMonitor(frame, eventId);
      }
    }.run();
    assertWindowVisibilityMonitorCountIn(frame, 0);
  }

  @DataProvider(name = "eventsBetweenWindowFirstAndWindowLast")
  public Iterator<Object[]> eventsBetweenWindowFirstAndWindowLast() {
    List<Object[]> ids = new ArrayList<Object[]>();
    for (int id = WINDOW_FIRST; id <= WINDOW_LAST; id++) {
      if (id == WINDOW_OPENED || id == WINDOW_CLOSED || id == WINDOW_CLOSING) continue;
      ids.add(new Object[] { id });
    }
    return ids.iterator();
  }
  
  private void createMonitor() {
    monitor = new ContextMonitor(windows, context);
  }
    
  private void expectEventQueueLookupFor(Component c) {
    expect(context.lookupEventQueueFor(c)).andReturn(c.getToolkit().getSystemEventQueue());
  }
  
  private void dispatchWindowOpenedEventToMonitor(Component c) {
    dispatchEventToMonitor(c, WINDOW_OPENED);
  }
  
  private void dispatchWindowClosedEventToMonitor(Component c) {
    dispatchEventToMonitor(c, WINDOW_CLOSED);
  }
  
  private void dispatchWindowClosingEventToMonitor(Component c) {
    dispatchEventToMonitor(c, WINDOW_CLOSING);
  }
  
  private void dispatchEventToMonitor(Component c, int eventId) {
    monitor.eventDispatched(new ComponentEvent(c, eventId));    
  }

  private List<Window> frameInList() {
    List<Window> rootWindows = new ArrayList<Window>();
    rootWindows.add(frame);
    return rootWindows;
  }
}
