/*
 * Created on Sep 14, 2008
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

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.MDITestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Understands a template for test tasks that set the position of a <code>{@link JInternalFrame}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = { GUI, EDT_ACTION })
public abstract class JInternalFrameMoveTaskTestCase {

  static final int FRONT_POSITION = 0;
  static final int BACK_POSITION = 1;

  private Robot robot;
  private MyWindow window;
  private JInternalFrame frameOnFront;
  private JInternalFrame frameOnBack;

  @BeforeMethod public final void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = MyWindow.createNew(getClass());
    frameOnFront = window.internalFrame();
    frameOnBack = window.frameOnBack;
    robot.showWindow(window);
    assertThat(desktop().getComponentZOrder(frameOnFront)).isEqualTo(FRONT_POSITION);
    assertThat(desktop().getComponentZOrder(frameOnBack)).isEqualTo(BACK_POSITION);
  }

  @AfterMethod public final void tearDown() {
    robot.cleanUp();
  }

  final Robot robot() { return robot; }
  final JDesktopPane desktop() { return window.desktop(); }
  final JInternalFrame frameOnFront() { return frameOnFront; }
  final JInternalFrame frameOnBack() { return frameOnBack; }

  private static class MyWindow extends MDITestWindow {
    private static final long serialVersionUID = 1L;

    final JInternalFrame frameOnBack;

    public static MyWindow createNew(Class<?> testClass) {
      return new MyWindow(testClass);
    }

    private MyWindow(Class<?> testClass) {
      super(testClass);
      frameOnBack = createInternalFrame();
      desktop().add(frameOnBack);
    }
  }
}
