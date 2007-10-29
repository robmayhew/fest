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

import javax.swing.JTextField;

import org.fest.mocks.EasyMockTemplate;

import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.monitor.MockWindows.mock;

import org.fest.swing.TestFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link WindowVisibilityMonitor}</code>.
 *
 * @author Alex Ruiz
 */
public class WindowVisibilityMonitorTest {

  private WindowVisibilityMonitor monitor;
  
  private Windows windows;
  private TestFrame frame;

  @BeforeMethod public void setUp() throws Exception {
    frame = new TestFrame(getClass());
    windows = mock();
  }

  @AfterMethod public void tearDown() {
    frame.beDisposed();
  }
  
  @Test public void shouldAttachMonitorToWindow() {
    attachMonitor();
    assertThat(frame.getWindowListeners()).contains(monitor);
    assertThat(frame.getComponentListeners()).contains(monitor);
  }
  
  @Test(dependsOnMethods = "shouldAttachMonitorToWindow")
  public void shouldMarkWindowAsShowingIfWindowShown() {
    attachMonitor();
    new EasyMockTemplate(windows) {
      protected void expectations() {
        windows.markAsShowing(frame);
      }

      protected void codeToTest() {
        monitor.componentShown(componentEventWithWindowAsSource());
      }
    }.run();
  }

  @Test(dependsOnMethods = "shouldAttachMonitorToWindow")
  public void shouldNotMarkWindowAsShowingIfComponentShownIsNotWindow() {
    attachMonitor();
    new EasyMockTemplate(windows) {
      protected void expectations() {}

      protected void codeToTest() {
        monitor.componentShown(componentEventWithTextFieldAsSource());
      }
    }.run();
  }

  @Test(dependsOnMethods = "shouldAttachMonitorToWindow")
  public void shouldMarkWindowAsHiddenIfWindowHidden() {
    attachMonitor();
    new EasyMockTemplate(windows) {
      protected void expectations() {
        windows.markAsHidden(frame);
      }

      protected void codeToTest() {
        monitor.componentHidden(componentEventWithWindowAsSource());
      }
    }.run();
  }

  @Test(dependsOnMethods = "shouldAttachMonitorToWindow")
  public void shouldNotMarkWindowAsHiddenIfComponentHiddenIsNotWindow() {
    attachMonitor();
    new EasyMockTemplate(windows) {
      protected void expectations() {}

      protected void codeToTest() {
        monitor.componentHidden(componentEventWithTextFieldAsSource());
      }
    }.run();
  }

  @Test(dependsOnMethods = "shouldAttachMonitorToWindow")
  public void shouldRemoveItselfWhenWindowClosed() {
    attachMonitor();
    monitor.windowClosed(new WindowEvent(frame, 8));
    assertThat(frame.getWindowListeners()).excludes(monitor);
    assertThat(frame.getComponentListeners()).excludes(monitor);
  }

  private void attachMonitor() {
    monitor = WindowVisibilityMonitor.attachWindowVisibilityMonitor(frame, windows);
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
