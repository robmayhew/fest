/*
 * Created on Aug 22, 2008
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
package org.fest.swing.driver;

import javax.swing.JScrollBar;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.MethodInvocations;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JScrollBarBlockIncrementQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class JScrollBarBlockIncrementQueryTest {

  private static final int BLOCK_INCREMENT = 2;

  private Robot robot;
  private MyScrollBar scrollBar;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    scrollBar = window.scrollBar;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnBlockIncrementOfJScrollBar() {
    scrollBar.startRecording();
    assertThat(JScrollBarBlockIncrementQuery.blockIncrementOf(scrollBar)).isEqualTo(BLOCK_INCREMENT);
    scrollBar.requireInvoked("getBlockIncrement");
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final MyScrollBar scrollBar = new MyScrollBar(8, BLOCK_INCREMENT, 6, 10);

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(JScrollBarBlockIncrementQueryTest.class);
      addComponents(scrollBar);
    }
  }

  private static class MyScrollBar extends JScrollBar {
    private static final long serialVersionUID = 1L;

    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    public MyScrollBar(int value, int extent, int min, int max) {
      super(HORIZONTAL, value, extent, min, max);
    }

    @Override public int getBlockIncrement() {
      if (recording) methodInvocations.invoked("getBlockIncrement");
      return super.getBlockIncrement();
    }

    void startRecording() { recording = true; }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}