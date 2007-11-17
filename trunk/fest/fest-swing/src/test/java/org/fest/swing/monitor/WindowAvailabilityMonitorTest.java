/*
 * Created on Oct 11, 2007
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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.fest.assertions.Assertions.assertThat;
import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.testing.TestFrame;
import org.fest.swing.listener.WeakEventListener;
import static org.fest.swing.listener.WeakEventListener.createWithoutAttaching;
import static org.fest.swing.monitor.MockWindows.mock;
import static org.fest.swing.util.ToolkitUtils.isListenerInToolkit;

import javax.swing.JTextField;
import static java.awt.AWTEvent.*;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Tests for <code>{@link WindowAvailabilityMonitor}</code>.
 *
 * @author Alex Ruiz
 */
public class WindowAvailabilityMonitorTest {

  private static final long EVENT_MASK = MOUSE_MOTION_EVENT_MASK | MOUSE_EVENT_MASK | PAINT_EVENT_MASK;

  private WindowAvailabilityMonitor monitor;
  
  private Windows windows;
  private TestFrame frame;
  
  @BeforeMethod public void setUp() throws Exception {
    frame = new TestFrame(getClass());
    windows = mock();
  }

  @AfterMethod public void tearDown() {
    frame.destroy();
  }

  @Test public void shouldAttachItSelfToToolkit() {
    monitor = WindowAvailabilityMonitor.attachWindowAvailabilityMonitor(windows);
    WeakEventListener l = createWithoutAttaching(monitor);
    assertThat(isListenerInToolkit(l, EVENT_MASK)).isTrue();
  }

  @Test public void shouldMarkSourceWindowAsReadyIfEventIsMouseEvent() {
    createMonitor();
    new EasyMockTemplate(windows) {
      protected void expectations() {
        windows.markAsReady(frame);
      }

      protected void codeToTest() {
        monitor.eventDispatched(mouseEvent(frame));        
      }
    }.run();
  }
  
  @Test public void shouldMarkSourceWindowAncestorAsReadyIfEventIsMouseEvent() {
    createMonitor();
    final JTextField source = new JTextField();
    frame.add(source);
    new EasyMockTemplate(windows) {
      protected void expectations() {
        windows.markAsReady(frame);
      }

      protected void codeToTest() {
        monitor.eventDispatched(mouseEvent(source));        
      }
    }.run();
  }
  
  @Test public void shouldNotMarkSourceWindowAsReadyIfEventIsNotMouseEvent() {
    createMonitor();
    new EasyMockTemplate(windows) {
      protected void expectations() { /* should not call markAsReady */ }

      protected void codeToTest() {
        monitor.eventDispatched(new KeyEvent(frame, 8, 9238, 0, 0, 'a'));
      }
    }.run();
  }

  private void createMonitor() {
    monitor = new WindowAvailabilityMonitor(windows);
  }

  private MouseEvent mouseEvent(Component source) {
    return new MouseEvent(source, 8, 8912, 0, 0, 0, 0, false, 0);
  }
}
