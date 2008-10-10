/*
 * Created on Aug 26, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.hierarchy;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.EDT_ACTION;
import static org.fest.swing.testing.TestGroups.GUI;

import java.awt.Component;
import java.util.List;

import javax.swing.JButton;

import org.fest.swing.core.ScreenLock;
import org.fest.swing.testing.MethodInvocations;
import org.fest.swing.testing.TestWindow;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ContainerComponentsQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class ContainerComponentsQueryTest {

  private MyWindow window;

  @BeforeMethod public void setUp() {
    ScreenLock.instance().acquire(this);
    window = MyWindow.createNew();
    window.display();
  }

  @AfterMethod public void tearDown() {
    window.destroy();
    ScreenLock.instance().release(this);
  }

  public void shouldReturnComponentsOfContainer() {
    window.startRecording();
    List<Component> components = ContainerComponentsQuery.componentsOf(window.getContentPane());
    assertThat(components).containsOnly(window.button);
    window.requireInvoked("getComponents");
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JButton button = new JButton("A button");

    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(ContainerComponentsQueryTest.class);
      addComponents(button);
    }

    @Override public Component[] getComponents() {
      if (recording) methodInvocations.invoked("getComponents");
      return super.getComponents();
    }

    void startRecording() { recording = true; }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
