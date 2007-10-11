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

import static org.fest.assertions.Assertions.assertThat;

import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;

import javax.swing.JTextField;

import static org.fest.swing.monitor.WindowsUtils.waitForWindowToBeMarkedAsReady;

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

  @BeforeMethod public void setUp() {
    frame = new TestFrame(getClass());
    windows = new Windows();
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
    monitor.componentShown(componentEventWithWindowAsSource());
    waitForWindowToBeMarkedAsReady();
    assertThat(windows.isReady(frame)).isTrue();
  }

  @Test(dependsOnMethods = "shouldAttachMonitorToWindow")
  public void shouldNotMarkWindowAsShowingIfComponentShownIsNotWindow() {
    attachMonitor();
    monitor.componentShown(componentEventWithTextFieldAsSource());
    waitForWindowToBeMarkedAsReady();
    assertThat(windows.isReady(frame)).isFalse();
  }

  @Test(dependsOnMethods = "shouldAttachMonitorToWindow")
  public void shouldMarkWindowAsHiddenIfWindowHidden() {
    attachMonitor();
    monitor.componentHidden(componentEventWithWindowAsSource());
    assertThat(windows.isHidden(frame)).isTrue();
  }

  @Test(dependsOnMethods = "shouldAttachMonitorToWindow")
  public void shouldNotMarkWindowAsHiddenIfComponentHiddenIsNotWindow() {
    attachMonitor();
    monitor.componentHidden(componentEventWithTextFieldAsSource());
    assertThat(windows.isHidden(frame)).isFalse();
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
