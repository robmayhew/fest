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
import java.awt.event.ComponentEvent;

import javax.swing.JTextField;

import org.fest.mocks.EasyMockTemplate;

import static java.awt.AWTEvent.COMPONENT_EVENT_MASK;
import static java.awt.AWTEvent.WINDOW_EVENT_MASK;
import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.monitor.MockContext.MethodToMock.ADD_CONTEXT_FOR;
import static org.fest.swing.monitor.MockContext.MethodToMock.LOOKUP_EVENT_QUEUE_FOR;
import static org.fest.swing.monitor.MockContext.MethodToMock.REMOVE_CONTEXT_FOR;
import static org.fest.swing.monitor.MockWindows.MethodToMock.IS_CLOSED;
import static org.fest.swing.monitor.MockWindows.MethodToMock.MARK_AS_CLOSED;
import static org.fest.swing.monitor.MockWindows.MethodToMock.MARK_AS_READY;
import static org.fest.swing.util.ToolkitUtils.toolkitHasListenerUnderEventMask;

import org.fest.swing.TestFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
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
    windows = MockWindows.mock(IS_CLOSED, MARK_AS_CLOSED, MARK_AS_READY);
    context = MockContext.mock(LOOKUP_EVENT_QUEUE_FOR, ADD_CONTEXT_FOR, REMOVE_CONTEXT_FOR);
  }

  @AfterMethod public void tearDown() {
    frame.beDisposed();
  }

  @Test public void shouldAttachItSelfToToolkit() {
    monitor = ContextMonitor.attachContextMonitor(new Windows(), new Context());
    WeakEventListener l = new WeakEventListener(monitor);
    assertThat(toolkitHasListenerUnderEventMask(l, EVENT_MASK)).isTrue();
  }

  @Test public void shouldNotProcessEventIfComponentIsNotWindowOrApplet() {
    createMonitor();
    new EasyMockTemplate(windows, context) {
      @Override protected void expectations() {}

      @Override protected void codeToTest() {
        monitor.eventDispatched(componentEvent(new JTextField()));
      }
    }.run();
  }

  private void createMonitor() {
    monitor = new ContextMonitor(windows, context);
  } 

  private ComponentEvent componentEvent(Component source) {
    return new ComponentEvent(source, 8);
  }
}
