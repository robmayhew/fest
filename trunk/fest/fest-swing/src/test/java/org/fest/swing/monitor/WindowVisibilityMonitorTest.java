/*
 * Created on Oct 10, 2007
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
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTextField;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

/**
 * Tests for <code>{@link WindowVisibilityMonitor}</code>.
 *
 * @author Alex Ruiz
 */
public class WindowVisibilityMonitorTest {

  private WindowVisibilityMonitor monitor;
  
  private Windows windows;
  private JFrame frame;

  @BeforeMethod public void setUp() throws Exception {
    frame = createMock(JFrame.class);
    windows = createMock(Windows.class);
    createAndAttachMonitor();
  }

  private void createAndAttachMonitor() {
    monitor = new WindowVisibilityMonitor(windows);
  }

  
  @Test public void shouldMarkWindowAsShowingIfWindowShown() {
    new EasyMockTemplate(windows) {
      protected void expectations() {
        windows.markAsShowing(frame);
      }

      protected void codeToTest() {
        monitor.componentShown(componentEventWithWindowAsSource());
      }
    }.run();
  }

  @Test public void shouldNotMarkWindowAsShowingIfComponentShownIsNotWindow() {
    new EasyMockTemplate(windows) {
      protected void expectations() {}

      protected void codeToTest() {
        monitor.componentShown(componentEventWithTextFieldAsSource());
      }
    }.run();
  }

  @Test public void shouldMarkWindowAsHiddenIfWindowHidden() {
    new EasyMockTemplate(windows) {
      protected void expectations() {
        windows.markAsHidden(frame);
      }

      protected void codeToTest() {
        monitor.componentHidden(componentEventWithWindowAsSource());
      }
    }.run();
  }

  @Test public void shouldNotMarkWindowAsHiddenIfComponentHiddenIsNotWindow() {
    new EasyMockTemplate(windows) {
      protected void expectations() {}

      protected void codeToTest() {
        monitor.componentHidden(componentEventWithTextFieldAsSource());
      }
    }.run();
  }

  @Test public void shouldRemoveItselfWhenWindowClosed() {
    new EasyMockTemplate(windows) {
      protected void expectations() {
        frame.removeWindowListener(monitor);
        expectLastCall().once();
        frame.removeComponentListener(monitor);
        expectLastCall().once();
      }

      protected void codeToTest() {
        monitor.windowClosed(new WindowEvent(frame, 8));
      }
    }.run();
  }
  
  private ComponentEvent componentEventWithWindowAsSource() {
    return componentEvent(frame);
  }
  
  private ComponentEvent componentEventWithTextFieldAsSource() {
    return componentEvent(new JTextField());
  }

  private ComponentEvent componentEvent(Component source) {
    return new ComponentEvent(source, 8);
  }
}
