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

import static org.fest.reflect.Reflection.field;

import static org.fest.swing.monitor.MockContext.MethodToMock.ADD_CONTEXT_FOR;
import static org.fest.swing.monitor.MockContext.MethodToMock.ALL_EVENT_QUEUES;
import static org.fest.swing.monitor.MockContext.MethodToMock.EVENT_QUEUE_FOR;
import static org.fest.swing.monitor.MockContext.MethodToMock.ROOT_WINDOWS;
import static org.fest.swing.monitor.MockWindows.MethodToMock.IS_READY;
import static org.fest.swing.monitor.MockWindows.MethodToMock.MARK_EXISTING;
import static org.fest.swing.monitor.MockWindowsStatus.MethodToMock.CHECK_IF_READY;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link WindowMonitor}</code>.
 *
 * @author Alex Ruiz
 */
public class WindowMonitorTest {

  private WindowMonitor monitor;
  
  private Windows windows;
  private Context context;
  private WindowStatus windowStatus;
  
  @BeforeMethod public void setUp() throws Exception {
    monitor = WindowMonitor.instance();
    createAndInjectMocks();
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
  
  @Test public void mocksInjected() {
  }
}
