/*
 * Created on Aug 8, 2008
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
package org.fest.swing.query;

import static javax.swing.BorderFactory.createEmptyBorder;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.EDT_ACTION;
import static org.fest.swing.testing.TestGroups.GUI;

import java.awt.Insets;

import javax.swing.JButton;

import org.fest.swing.core.ScreenLock;
import org.fest.swing.testing.MethodInvocations;
import org.fest.swing.testing.TestWindow;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ContainerInsetsQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class ContainerInsetsQueryTest {

  private MyWindow window;
  private MyButton button;

  @BeforeMethod public void setUp() {
    ScreenLock.instance().acquire(this);
    window = MyWindow.createNew();
    button = window.button;
    window.display();
  }

  @AfterMethod public void tearDown() {
    window.destroy();
    ScreenLock.instance().release(this);
  }

  public void shouldReturnInsetsOfJComponent() {
    button.startRecording();
    Insets insets = ContainerInsetsQuery.insetsOf(button);
    assertThat(insets).isEqualTo(new Insets(6, 7, 8, 9));
    button.requireInvoked("getInsets");
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }

    final MyButton button = new MyButton("A button");

    private MyWindow() {
      super(ContainerInsetsQueryTest.class);
      button.setBorder(createEmptyBorder(6, 7, 8, 9));
      add(button);
    }
  }

  private static class MyButton extends JButton {
    private static final long serialVersionUID = 1L;

    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    public MyButton(String text) {
      super(text);
    }

    @Override public Insets getInsets() {
      if (recording) methodInvocations.invoked("getInsets");
      return super.getInsets();
    }

    void startRecording() { recording = true; }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
