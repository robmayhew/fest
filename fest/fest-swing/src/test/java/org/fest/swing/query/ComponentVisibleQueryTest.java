/*
 * Created on Aug 9, 2008
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.EDT_ACTION;
import static org.fest.swing.testing.TestGroups.GUI;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.MethodInvocations;
import org.fest.swing.testing.TestWindow;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ComponentVisibleQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class ComponentVisibleQueryTest {

  private Robot robot;
  private MyWindow window;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = MyWindow.createNew();
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldIndicateIfComponentIsNotVisible() {
    window.startRecording();
    assertThat(ComponentVisibleQuery.isVisible(window)).isFalse();
    window.requireInvoked("isVisible");
  }

  public void shouldIndicateIfComponentIsVisible() {
    robot.showWindow(window);
    window.startRecording();
    assertThat(ComponentVisibleQuery.isVisible(window)).isTrue();
    window.requireInvoked("isVisible");
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(ComponentVisibleQueryTest.class);
    }

    @Override public boolean isVisible() {
      if (recording) methodInvocations.invoked("isVisible");
      return super.isVisible();
    }

    void startRecording() { recording = true; }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
