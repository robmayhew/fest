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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.monitor;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.listener.WeakEventListener;
import org.fest.swing.testing.TestWindow;
import org.fest.swing.testing.ToolkitStub;

import static java.awt.AWTEvent.*;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link WindowAvailabilityMonitor}</code>.
 *
 * @author Alex Ruiz
 */
public class WindowAvailabilityMonitorTest {

  private static final long EVENT_MASK = MOUSE_MOTION_EVENT_MASK | MOUSE_EVENT_MASK | PAINT_EVENT_MASK;

  private WindowAvailabilityMonitor monitor;

  private ToolkitStub toolkit;
  private Windows windows;
  private TestWindow window;

  @BeforeMethod public void setUp() throws Exception {
    toolkit = ToolkitStub.createNew();
    window = TestWindow.createNew(getClass());
    windows = createMock(Windows.class);
    monitor = new WindowAvailabilityMonitor(windows);
  }

  @AfterMethod public void tearDown() {
    window.destroy();
  }

  @Test public void shouldAttachItSelfToToolkit() {
    monitor.attachTo(toolkit);
    List<WeakEventListener> eventListeners = toolkit.eventListenersUnderEventMask(EVENT_MASK, WeakEventListener.class);
    assertThat(eventListeners).hasSize(1);
    WeakEventListener weakEventListener = eventListeners.get(0);
    assertThat(weakEventListener.underlyingListener()).isSameAs(monitor);
  }

  @Test public void shouldMarkSourceWindowAsReadyIfEventIsMouseEvent() {
    new EasyMockTemplate(windows) {
      protected void expectations() {
        windows.markAsReady(window);
      }

      protected void codeToTest() {
        monitor.eventDispatched(mouseEvent(window));
      }
    }.run();
  }

  @Test public void shouldMarkSourceWindowAncestorAsReadyIfEventIsMouseEvent() {
    final JTextField source = new JTextField();
    window.add(source);
    new EasyMockTemplate(windows) {
      protected void expectations() {
        windows.markAsReady(window);
      }

      protected void codeToTest() {
        monitor.eventDispatched(mouseEvent(source));
      }
    }.run();
  }

  @Test public void shouldNotMarkSourceWindowAsReadyIfEventIsNotMouseEvent() {
    new EasyMockTemplate(windows) {
      protected void expectations() { /* should not call markAsReady */ }

      protected void codeToTest() {
        monitor.eventDispatched(new KeyEvent(window, 8, 9238, 0, 0, 'a'));
      }
    }.run();
  }

  private MouseEvent mouseEvent(Component source) {
    return new MouseEvent(source, 8, 8912, 0, 0, 0, 0, false, 0);
  }
}
