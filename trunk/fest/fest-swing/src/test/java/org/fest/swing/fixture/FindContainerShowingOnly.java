/*
 * Created on Feb 2, 2008
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
package org.fest.swing.fixture;

import java.awt.Dimension;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestFrame;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.*;
import static org.fest.util.Strings.concat;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=108">Bug 108</a>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, BUG })
public class FindContainerShowingOnly {

  private MyFrame myFrame;
  private Robot robot;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();;
    myFrame = new MyFrame();
    robot.showWindow(myFrame, new Dimension(400, 300));
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldFindOnlyVisibleContainer() {
    myFrame.notShowing.setVisible(false);
    JInternalFrameFixture fixture = new JInternalFrameFixture(robot, "target");
    assertThat(fixture.target).isSameAs(myFrame.showing);
  }

  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    private final JDesktopPane desktop = new JDesktopPane();
    private final MyInternalFrame notShowing = new MyInternalFrame();
    private final MyInternalFrame showing = new MyInternalFrame();

    public MyFrame() {
      super(FindContainerShowingOnly.class);
      setContentPane(desktop);
      addToDesktop(notShowing);
      addToDesktop(showing);
    }

    private void addToDesktop(MyInternalFrame frame) {
      frame.setVisible(true);
      desktop.add(frame);
      frame.toFront();
    }
  }

  private static class MyInternalFrame extends JInternalFrame {
    private static final long serialVersionUID = 1L;

    private static int index;

    MyInternalFrame() {
      super(concat("Internal Frame ", String.valueOf(index++)), true, true, true, true);
      setName("target");
      setSize(200, 100);
    }
  }
}
