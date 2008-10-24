/*
 * Created on Aug 7, 2008
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

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.swing.query.ContainerInsetsQuery.insetsOf;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link ContainerResizeLocationQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class ContainerResizeLocationQueryTest {

  private static final int WIDTH = 200;
  private static final int HEIGHT = 100;

  private Robot robot;
  private MyWindow window;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = MyWindow.createNew();
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnResizeLocationOfContainer() {
    // TODO find a better way to test this without duplicating what the class under test is doing
    Dimension size = sizeOf(window);
    Insets insets = insetsOf(window);
    Point expected = new Point(size.width - insets.right / 2, size.height - insets.bottom / 2);
    assertThat(ContainerResizeLocationQuery.resizeLocationOf(window)).isEqualTo(expected);
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(ContainerResizeLocationQueryTest.class);
      setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }
  }
}
