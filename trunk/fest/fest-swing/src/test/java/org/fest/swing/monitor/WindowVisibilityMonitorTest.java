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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.monitor;

import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.testing.MethodInvocations;
import org.fest.swing.testing.MethodInvocations.Args;

import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.MethodInvocations.Args.args;

/**
 * Tests for <code>{@link WindowVisibilityMonitor}</code>.
 *
 * @author Alex Ruiz
 */
public class WindowVisibilityMonitorTest {

  private WindowVisibilityMonitor monitor;

  private MyFrame frame;
  private Windows windows;

  @BeforeMethod public void setUp() throws Exception {
    frame = new MyFrame();
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
    frame.startRecording();
    new EasyMockTemplate(windows) {
      protected void expectations() {}

      protected void codeToTest() {
        monitor.windowClosed(new WindowEvent(frame, 8));
        assertThat(frame.requireInvoked("removeWindowListener", args(monitor)));
        assertThat(frame.requireInvoked("removeComponentListener", args(monitor)));
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

  private static class MyFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MyFrame() {}

    void startRecording() {
      recording = true;
    }

    @Override
    public synchronized void removeWindowListener(WindowListener l) {
      if (recording) methodInvocations.invoked("removeWindowListener", args(l));
      super.removeWindowListener(l);
    }

    @Override
    public synchronized void removeComponentListener(ComponentListener l) {
      if (recording) methodInvocations.invoked("removeComponentListener", args(l));
      super.removeComponentListener(l);
    }

    MethodInvocations requireInvoked(String methodName, Args args) {
      return methodInvocations.requireInvoked(methodName, args);
    }
  }
}
