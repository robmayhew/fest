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

import org.fest.swing.core.EventMode;
import org.fest.swing.core.EventModeProvider;
import org.fest.swing.core.GuiQuery;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.testing.FluentDimension;
import org.fest.swing.testing.FluentPoint;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JInternalFrameAction.MAXIMIZE;
import static org.fest.swing.driver.JInternalFrameIconQuery.isIconified;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
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
    MyFrame frame = new GuiQuery<MyFrame>() {
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

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotIconifyAlreadyIconifiedInternalFrame(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setIcon(true);
    driver.iconify(internalFrame);
    assertThat(isIconified(internalFrame)).isTrue();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotDeiconifyAlreadyDeiconifiedInternalFrame(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setIcon(false);
    driver.deiconify(internalFrame);
    assertThat(isIconified(internalFrame)).isFalse();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldIconifyAndDeiconifyInternalFrame(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.iconify(internalFrame);
    assertThat(isIconified(internalFrame)).isTrue();
    driver.deiconify(internalFrame);
    assertThat(isIconified(internalFrame)).isFalse();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldThrowErrorIfIconifyingFrameThatIsNotIconifiable(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
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

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldMaximizeInternalFrame(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.maximize(internalFrame);
    assertThat(isMaximized()).isTrue();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldMaximizeIconifiedInternalFrame(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setIcon(true);
    driver.maximize(internalFrame);
    assertThat(isMaximized()).isTrue();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldThrowErrorIfMaximizingFrameThatIsNotMaximizable(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
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

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNormalizeInternalFrame(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.maximize(internalFrame);
    driver.normalize(internalFrame);
    assertIsNormalized();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNormalizeIconifiedInternalFrame(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setIcon(true);
    driver.normalize(internalFrame);
    assertIsNormalized();
  }

  private void setIcon(boolean icon) {
    new GuiQuery<Void>() {
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
    return new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() throws Throwable {
        return internalFrame.isMaximum();
      }
    }.run();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldMoveInternalFrameToFront(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.moveToFront(internalFrame);
    assertThat(desktopPane.getComponentZOrder(internalFrame)).isEqualTo(0);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldMoveInternalFrameToBack(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.moveToBack(internalFrame);
    assertThat(desktopPane.getComponentZOrder(internalFrame)).isEqualTo(1);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public final void shouldResizeInternalFrameToGivenSize(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    FluentDimension newSize = internalFrameSize().addToWidth(20).addToHeight(40);
    driver.resize(internalFrame, newSize.width, newSize.height);
    assertThat(sizeOf(internalFrame)).isEqualTo(newSize);
  }

  private final FluentDimension internalFrameSize() {
    return new FluentDimension(sizeOf(internalFrame));
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public final void shouldMoveInternalFrame(EventMode eventModel) {
    robot.settings().eventMode(eventModel);
    Point p = internalFrameLocation().addToX(10).addToY(10);
    driver.moveTo(internalFrame, p);
    assertThat(internalFrameLocation()).isEqualTo(p);
  }

  private FluentPoint internalFrameLocation() {
    return new FluentPoint(new GuiQuery<Point>() {
      protected Point executeInEDT() throws Throwable {
        return internalFrame.getLocation();
      }
    }.run());
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldCloseInternalFrame(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.close(internalFrame);
    boolean closed = new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() throws Throwable {
        return internalFrame.isClosed();
      }
    }.run();
    assertThat(closed).isTrue();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldThrowErrorIfClosingFrameThatIsNotClosable(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
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

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldResizeInternalFrame(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    Dimension newSize = new FluentDimension(sizeOf(internalFrame)).addToWidth(60).addToHeight(60);
    driver.resizeTo(internalFrame, newSize);
    assertThat(sizeOf(internalFrame)).isEqualTo(newSize);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldResizeWidth(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    int newWidth = 600;
    assertThat(internalFrameWidth()).isNotEqualTo(newWidth);
    driver.resizeWidthTo(internalFrame, newWidth);
    assertThat(internalFrameWidth()).isEqualTo(newWidth);
  }

  private int internalFrameWidth() {
    return new GuiQuery<Integer>() {
      protected Integer executeInEDT() throws Throwable {
        return internalFrame.getWidth();
      }
    }.run();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldResizeHeight(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    int newHeight = 600;
    assertThat(internalFrameHeight()).isNotEqualTo(newHeight);
    driver.resizeHeightTo(internalFrame, newHeight);
    assertThat(internalFrameHeight()).isEqualTo(newHeight);
  }

  private int internalFrameHeight() {
    return new GuiQuery<Integer>() {
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
