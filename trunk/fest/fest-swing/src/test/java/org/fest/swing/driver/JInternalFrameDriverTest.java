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

import java.awt.Dimension;
import java.awt.Point;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GuiTask;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.testing.FluentDimension;
import org.fest.swing.testing.FluentPoint;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JInternalFrameAction.MAXIMIZE;
import static org.fest.swing.task.GetComponentSizeTask.sizeOf;
import static org.fest.swing.task.IsJInternalFrameIconifiedTask.isIconified;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Strings.concat;

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
    MyFrame frame = new GuiTask<MyFrame>() {
      protected MyFrame executeInEDT() {
        return new MyFrame();
      }
    }.run();
    internalFrame = frame.internalFrame;
    desktopPane = frame.desktopPane;
    robot.showWindow(frame);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldThrowErrorWithExceptionInSetPropertyTask() {
    final PropertyVetoException vetoed = new PropertyVetoException("Test", null);
    JInternalFrameAction action = MAXIMIZE;
    JInternalFrameSetPropertyTask task = new JInternalFrameSetPropertyTask(internalFrame, action) {
      public void execute() throws PropertyVetoException {
        throw vetoed;
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

  public void shouldNotIconifyAlreadyIconifiedInternalFrame() {
    setIcon(true);
    driver.iconify(internalFrame);
    assertThat(isIconified(internalFrame)).isTrue();
  }

  public void shouldNotDeiconifyAlreadyDeiconifiedInternalFrame() {
    setIcon(false);
    driver.deiconify(internalFrame);
    assertThat(isIconified(internalFrame)).isFalse();
  }

  public void shouldIconifyAndDeiconifyInternalFrame() {
    driver.iconify(internalFrame);
    assertThat(isIconified(internalFrame)).isTrue();
    driver.deiconify(internalFrame);
    assertThat(isIconified(internalFrame)).isFalse();
  }

  public void shouldThrowErrorIfIconifyingFrameThatIsNotIconifiable() {
    robot.invokeAndWait(new Runnable() {
      public void run() {
        internalFrame.setIconifiable(false);
      }
    });
    try {
      driver.iconify(internalFrame);
      fail();
    } catch (ActionFailedException e) {
      assertThat(e).message().contains("The JInternalFrame <")
                             .contains("> is not iconifiable");
    }
  }

  public void shouldMaximizeInternalFrame() {
    driver.maximize(internalFrame);
    assertThat(isMaximized()).isTrue();
  }

  public void shouldMaximizeIconifiedInternalFrame() {
    setIcon(true);
    driver.maximize(internalFrame);
    assertThat(isMaximized()).isTrue();
  }

  public void shouldThrowErrorIfMaximizingFrameThatIsNotMaximizable() {
    robot.invokeAndWait(new Runnable() {
      public void run() {
        internalFrame.setMaximizable(false);
      }
    });
    try {
      driver.maximize(internalFrame);
      fail();
    } catch (ActionFailedException e) {
      assertThat(e).message().contains("The JInternalFrame <")
                             .contains("> is not maximizable");
    }
  }

  public void shouldNormalizeInternalFrame() {
    driver.maximize(internalFrame);
    driver.normalize(internalFrame);
    assertIsNormalized();
  }

  public void shouldNormalizeIconifiedInternalFrame() {
    setIcon(true);
    driver.normalize(internalFrame);
    assertIsNormalized();
  }

  private void setIcon(boolean icon) {
    new GuiTask<Void>() {
      protected Void executeInEDT() throws PropertyVetoException {
        internalFrame.setIcon(true);
        return null;
      }
    }.run();
  }

  private void assertIsNormalized() {
    assertThat(isIconified(internalFrame)).isFalse();
    assertThat(isMaximized()).isFalse();
  }

  private boolean isMaximized() {
    return new GuiTask<Boolean>() {
      protected Boolean executeInEDT() throws Throwable {
        return internalFrame.isMaximum();
      }
    }.run();
  }

  public void shouldMoveInternalFrameToFront() {
    driver.moveToFront(internalFrame);
    assertThat(desktopPane.getComponentZOrder(internalFrame)).isEqualTo(0);
  }

  public void shouldMoveInternalFrameToBack() {
    driver.moveToBack(internalFrame);
    assertThat(desktopPane.getComponentZOrder(internalFrame)).isEqualTo(1);
  }

  public final void shouldResizeInternalFrameToGivenSize() {
    FluentDimension newSize = internalFrameSize().addToWidth(20).addToHeight(40);
    driver.resize(internalFrame, newSize.width, newSize.height);
    assertThat(sizeOf(internalFrame)).isEqualTo(newSize);
  }

  private final FluentDimension internalFrameSize() {
    return new FluentDimension(sizeOf(internalFrame));
  }

  public final void shouldMoveInternalFrame() {
    Point p = internalFrameLocation().addToX(10).addToY(10);
    driver.moveTo(internalFrame, p);
    assertThat(internalFrameLocation()).isEqualTo(p);
  }

  private FluentPoint internalFrameLocation() {
    return new FluentPoint(new GuiTask<Point>() {
      protected Point executeInEDT() throws Throwable {
        return internalFrame.getLocation();
      }
    }.run());
  }

  public void shouldCloseInternalFrame() {
    driver.close(internalFrame);
    boolean closed = new GuiTask<Boolean>() {
      protected Boolean executeInEDT() throws Throwable {
        return internalFrame.isClosed();
      }
    }.run();
    assertThat(closed).isTrue();
  }

  public void shouldThrowErrorIfClosingFrameThatIsNotClosable() {
    robot.invokeAndWait(new Runnable() {
      public void run() {
        internalFrame.setClosable(false);
      }
    });
    try {
      driver.close(internalFrame);
      fail();
    } catch (ActionFailedException e) {
      assertThat(e).message().contains("The JInternalFrame <")
                             .contains("> is not closable");
    }
  }

  public void shouldResizeInternalFrame() {
    Dimension newSize = new FluentDimension(sizeOf(internalFrame)).addToWidth(60).addToHeight(60);
    driver.resizeTo(internalFrame, newSize);
    assertThat(sizeOf(internalFrame)).isEqualTo(newSize);
  }

  public void shouldResizeWidth() {
    int newWidth = 600;
    assertThat(internalFrameWidth()).isNotEqualTo(newWidth);
    driver.resizeWidthTo(internalFrame, newWidth);
    assertThat(internalFrameWidth()).isEqualTo(newWidth);
  }

  private int internalFrameWidth() {
    return new GuiTask<Integer>() {
      protected Integer executeInEDT() throws Throwable {
        return internalFrame.getWidth();
      }
    }.run();
  }

  public void shouldResizeHeight() {
    int newHeight = 600;
    assertThat(internalFrameHeight()).isNotEqualTo(newHeight);
    driver.resizeHeightTo(internalFrame, newHeight);
    assertThat(internalFrameHeight()).isEqualTo(newHeight);
  }

  private int internalFrameHeight() {
    return new GuiTask<Integer>() {
      protected Integer executeInEDT() throws Throwable {
        return internalFrame.getHeight();
      }
    }.run();
  }

  private static class MyFrame extends TestWindow {
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
