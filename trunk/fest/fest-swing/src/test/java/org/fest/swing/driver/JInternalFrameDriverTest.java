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

import org.fest.swing.core.Condition;
import org.fest.swing.core.EventMode;
import org.fest.swing.core.EventModeProvider;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.testing.FluentDimension;
import org.fest.swing.testing.FluentPoint;
import org.fest.swing.testing.TestWindow;

import static java.lang.String.valueOf;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.ComponentLocationQuery.locationOf;
import static org.fest.swing.driver.JInternalFrameAction.MAXIMIZE;
import static org.fest.swing.driver.JInternalFrameIconQuery.isIconified;
import static org.fest.swing.edt.GuiActionRunner.execute;
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
    MyWindow window = MyWindow.createNew();
    internalFrame = window.internalFrame;
    desktopPane = window.desktopPane;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldThrowErrorWithExceptionInSetPropertyTask() {
    final PropertyVetoException vetoed = new PropertyVetoException("Test", null);
    JInternalFrameAction action = MAXIMIZE;
    JInternalFrameSetPropertyTaskTemplate task = new JInternalFrameSetPropertyTaskTemplate(internalFrame, action) {
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
    setIcon(internalFrame, true);
    driver.iconify(internalFrame);
    assertThat(isIconified(internalFrame)).isTrue();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotDeiconifyAlreadyDeiconifiedInternalFrame(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setIcon(internalFrame, false);
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
    setNotIconifiable(internalFrame);
    try {
      driver.iconify(internalFrame);
      fail();
    } catch (ActionFailedException e) {
      assertThat(e).message().contains("The JInternalFrame <")
                             .contains("> is not iconifiable");
    }
  }

  private static void setNotIconifiable(final JInternalFrame internalFrame) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        internalFrame.setIconifiable(false);
      }
    }, new Condition("JInternalFrame is not iconifiable") {
      public boolean test() {
        return !internalFrame.isIconifiable();
      }
    });
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldMaximizeInternalFrame(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.maximize(internalFrame);
    assertThat(isMaximized(internalFrame)).isTrue();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldMaximizeIconifiedInternalFrame(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setIcon(internalFrame, true);
    driver.maximize(internalFrame);
    assertThat(isMaximized(internalFrame)).isTrue();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldThrowErrorIfMaximizingFrameThatIsNotMaximizable(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setNotMaximizable(internalFrame);
    try {
      driver.maximize(internalFrame);
      fail();
    } catch (ActionFailedException e) {
      assertThat(e).message().contains("The JInternalFrame <")
                             .contains("> is not maximizable");
    }
  }

  private static void setNotMaximizable(final JInternalFrame internalFrame) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        internalFrame.setMaximizable(false);
      }
    }, new Condition("JInternalFrame is not maximizable") {
      public boolean test() {
        return !internalFrame.isMaximizable();
      }
    });
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
    setIcon(internalFrame, true);
    driver.normalize(internalFrame);
    assertIsNormalized();
  }

  private void setIcon(final JInternalFrame internalFrame, final boolean icon) {
    execute(new GuiTask() {
      protected void executeInEDT() throws PropertyVetoException {
        internalFrame.setIcon(icon);
      }
    }, new Condition(concat("JInternalFrame's 'icon' property is ", icon)) {
      public boolean test() {
        return internalFrame.isIcon() == icon;
      } 
    });
  }

  private void assertIsNormalized() {
    assertThat(isIconified(internalFrame)).isFalse();
    assertThat(isMaximized(internalFrame)).isFalse();
  }

  private static final boolean isMaximized(final JInternalFrame internalFrame) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() throws Throwable {
        return internalFrame.isMaximum();
      }
    });
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
    return new FluentPoint(locationOf(internalFrame));
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldCloseInternalFrame(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.close(internalFrame);
    boolean closed = isClosed(internalFrame);
    assertThat(closed).isTrue();
  }

  private static Boolean isClosed(final JInternalFrame internalFrame) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() throws Throwable {
        return internalFrame.isClosed();
      }
    });
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldThrowErrorIfClosingFrameThatIsNotClosable(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setNotClosable(internalFrame);
    try {
      driver.close(internalFrame);
      fail();
    } catch (ActionFailedException e) {
      assertThat(e).message().contains("The JInternalFrame <")
                             .contains("> is not closable");
    }
  }

  private static void setNotClosable(final JInternalFrame internalFrame) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        internalFrame.setClosable(false);
      }
    }, new Condition("JInternalFrame is not closable") {
      public boolean test() {
        return !internalFrame.isClosable();
      }
    });
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
    assertThat(widthOf(internalFrame)).isNotEqualTo(newWidth);
    driver.resizeWidthTo(internalFrame, newWidth);
    assertThat(widthOf(internalFrame)).isEqualTo(newWidth);
  }

  private static int widthOf(final JInternalFrame internalFrame) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() throws Throwable {
        return internalFrame.getWidth();
      }
    });
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldResizeHeight(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    int newHeight = 600;
    assertThat(heightOf(internalFrame)).isNotEqualTo(newHeight);
    driver.resizeHeightTo(internalFrame, newHeight);
    assertThat(heightOf(internalFrame)).isEqualTo(newHeight);
  }

  private static int heightOf(JInternalFrame internalFrame) {
    return sizeOf(internalFrame).height;
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JDesktopPane desktopPane;
    final JInternalFrame internalFrame;

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
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
      super(concat("Internal Frame ", valueOf(index++)), true, true, true, true);
      setSize(200, 100);
    }
  }
}
