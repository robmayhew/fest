/*
 * Created on Feb 24, 2008
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JInternalFrameDriver.Action.MAXIMIZE;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Strings.concat;

import java.awt.Dimension;
import java.awt.Point;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JInternalFrameDriver.Action;
import org.fest.swing.driver.JInternalFrameDriver.SetPropertyTask;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.testing.FluentDimension;
import org.fest.swing.testing.FluentPoint;
import org.fest.swing.testing.TestFrame;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JInternalFrameDriver}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class JInternalFrameDriverTest {

  private Robot robot;
  private JInternalFrame internalFrame;
  private JDesktopPane desktopPane;
  private JInternalFrameDriver driver;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JInternalFrameDriver(robot);
    MyFrame frame = new MyFrame();
    internalFrame = frame.internalFrame;
    desktopPane = frame.desktopPane;
    robot.showWindow(frame);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test public void shouldThrowErrorWithExceptionInSetPropertyTask() {
    final PropertyVetoException vetoed = new PropertyVetoException("Test", null);
    Action action = MAXIMIZE;
    SetPropertyTask task = new SetPropertyTask(internalFrame, action) {
      public void run() {
        veto.cause = vetoed;
      }
    };
    try {
      driver.setProperty(task);
      fail();
    } catch (ActionFailedException e) {
      assertThat(e).message().contains(action.name)
                             .contains("was vetoed: <Test>");
    }
  }

  @Test public void shouldNotIconifyAlreadyIconifiedInternalFrame() throws PropertyVetoException {
    internalFrame.setIcon(true);
    driver.iconify(internalFrame);
    assertThat(internalFrame.isIcon()).isTrue();
  }

  @Test public void shouldNotDeiconifyAlreadyDeiconifiedInternalFrame() throws PropertyVetoException {
    internalFrame.setIcon(false);
    driver.deiconify(internalFrame);
    assertThat(internalFrame.isIcon()).isFalse();
  }

  @Test public void shouldIconifyAndDeiconifyInternalFrame() {
    driver.iconify(internalFrame);
    assertThat(internalFrame.isIcon()).isTrue();
    driver.deiconify(internalFrame);
    assertThat(internalFrame.isIcon()).isFalse();
  }

  @Test public void shouldThrowErrorIfIconifyingFrameThatIsNotIconifiable() {
    internalFrame.setIconifiable(false);
    try {
      driver.iconify(internalFrame);
      fail();
    } catch (ActionFailedException e) {
      assertThat(e).message().contains("The JInternalFrame <")
                             .contains("> is not iconifiable");
    }
  }

  @Test public void shouldMaximizeInternalFrame() {
    driver.maximize(internalFrame);
    assertThat(internalFrame.isMaximum()).isTrue();
  }

  @Test public void shouldMaximizeIconifiedInternalFrame() throws PropertyVetoException {
    internalFrame.setIcon(true);
    driver.maximize(internalFrame);
    assertThat(internalFrame.isMaximum()).isTrue();
  }

  @Test public void shouldThrowErrorIfMaximizingFrameThatIsNotMaximizable() {
    internalFrame.setMaximizable(false);
    try {
      driver.maximize(internalFrame);
      fail();
    } catch (ActionFailedException e) {
      assertThat(e).message().contains("The JInternalFrame <")
                             .contains("> is not maximizable");
    }
  }

  @Test public void shouldNormalizeInternalFrame() {
    driver.maximize(internalFrame);
    driver.normalize(internalFrame);
    assertIsNormalized();
  }

  @Test public void shouldNormalizeIconifiedInternalFrame() throws PropertyVetoException {
    internalFrame.setIcon(true);
    driver.normalize(internalFrame);
    assertIsNormalized();
  }

  private void assertIsNormalized() {
    assertThat(internalFrame.isIcon()).isFalse();
    assertThat(internalFrame.isMaximum()).isFalse();
  }

  @Test public void shouldMoveInternalFrameToFront() {
    driver.moveToFront(internalFrame);
    assertThat(desktopPane.getComponentZOrder(internalFrame)).isEqualTo(0);
  }

  @Test public void shouldMoveInternalFrameToBack() {
    driver.moveToBack(internalFrame);
    assertThat(desktopPane.getComponentZOrder(internalFrame)).isEqualTo(1);
  }

  @Test public final void shouldResizeInternalFrameToGivenSize() {
    FluentDimension newSize = internalFrameSize().addToWidth(20).addToHeight(40);
    driver.resize(internalFrame, newSize.width, newSize.height);
    assertThat(internalFrame.getSize()).isEqualTo(newSize);
  }

  private final FluentDimension internalFrameSize() {
    return new FluentDimension(internalFrame.getSize());
  }

  @Test public final void shouldMoveInternalFrame() {
    Point p = internalFrameLocation().addToX(10).addToY(10);
    driver.moveTo(internalFrame, p);
    assertThat(internalFrameLocation()).isEqualTo(p);
  }

  private FluentPoint internalFrameLocation() {
    return new FluentPoint(internalFrame.getLocation());
  }

  @Test public void shouldCloseInternalFrame() {
    driver.close(internalFrame);
    assertThat(internalFrame.isClosed()).isTrue();
  }

  @Test public void shouldThrowErrorIfClosingFrameThatIsNotClosable() {
    internalFrame.setClosable(false);
    try {
      driver.close(internalFrame);
      fail();
    } catch (ActionFailedException e) {
      assertThat(e).message().contains("The JInternalFrame <")
                             .contains("> is not closable");
    }
  }

  @Test public void shouldResizeInternalFrame() {
    Dimension newSize = new FluentDimension(internalFrame.getSize()).addToWidth(60).addToHeight(60);
    driver.resizeTo(internalFrame, newSize);
    assertThat(internalFrame.getSize()).isEqualTo(newSize);
  }

  @Test public void shouldResizeWidth() {
    int newWidth = 600;
    assertThat(internalFrame.getWidth()).isNotEqualTo(newWidth);
    driver.resizeWidthTo(internalFrame, newWidth);
    assertThat(internalFrame.getWidth()).isEqualTo(newWidth);
  }

  @Test public void shouldResizeHeight() {
    int newHeight = 600;
    assertThat(internalFrame.getHeight()).isNotEqualTo(newHeight);
    driver.resizeHeightTo(internalFrame, newHeight);
    assertThat(internalFrame.getHeight()).isEqualTo(newHeight);
  }

  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    private final JDesktopPane desktopPane;
    private final JInternalFrame internalFrame;

    MyFrame() {
      super(JInternalFrameDriverTest.class);
      MyInternalFrame.resetIndex();
      desktopPane = new JDesktopPane();
      internalFrame = new MyInternalFrame();
      addInternalFrames();
      setContentPane(desktopPane);
      setPreferredSize(new Dimension(400, 300));
    }

    private void addInternalFrames() {
      addInternalFrame(new MyInternalFrame());
      addInternalFrame(internalFrame);
    }

    private void addInternalFrame(JInternalFrame f) {
      desktopPane.add(f);
      f.toFront();
      f.setVisible(true);
    }
  }

  private static class MyInternalFrame extends JInternalFrame {
    private static final long serialVersionUID = 1L;

    private static int index;

    static void resetIndex() {
      index = 0;
    }

    MyInternalFrame() {
      super(concat("Internal Frame ", String.valueOf(index++)), true, true, true, true);
      setSize(200, 100);
    }
  }
}
