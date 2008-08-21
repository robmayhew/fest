/*
 * Created on Feb 25, 2008
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

import java.awt.*;

import javax.swing.JLabel;
import javax.swing.JToolBar;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.core.*;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.testing.TestWindow;

import static java.awt.BorderLayout.*;
import static javax.swing.SwingUtilities.getWindowAncestor;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.EventMode.*;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.ComponentBoundsQuery.boundsOf;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link JToolBarDriver}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class JToolBarDriverTest {

  private Robot robot;
  private MyWindow window;
  private JToolBar toolBar;
  private JToolBarDriver driver;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JToolBarDriver(robot);
    window = MyWindow.newWindow();
    toolBar = window.toolBar;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldThrowErrorWhenFloatingNotFloatableToolBar(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setNotFloatable(toolBar);
    try {
      driver.makeFloat(toolBar);
      fail();
    } catch (ActionFailedException e) {
      assertThat(e).message().contains("is not floatable");
    }
  }

  private static void setNotFloatable(final JToolBar toolBar) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        toolBar.setFloatable(false);
      }
    });
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldFloatToolbar(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    Window oldAncestor = ancestorOf(toolBar);
    driver.makeFloat(toolBar);
    Window newAncestor = ancestorOf(toolBar);
    assertThat(newAncestor).isNotSameAs(oldAncestor);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldFloatToolbarToPoint(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    Window oldAncestor = ancestorOf(toolBar);
    Point where = whereToFloatTo();
    driver.floatTo(toolBar, where.x, where.y);
    assertToolBarIsFloating(oldAncestor);
  }

  private void assertToolBarIsFloating(Window oldAncestor) {
    Window newAncestor = ancestorOf(toolBar);
    assertThat(newAncestor).isNotSameAs(oldAncestor);
    Point newAncestorLocation = newAncestor.getLocation();
    Point oldAncestorLocation = oldAncestor.getLocation();
    assertThat(newAncestorLocation.x).isGreaterThan(oldAncestorLocation.x);
    assertThat(newAncestorLocation.y).isGreaterThan(oldAncestorLocation.y);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldUnfloatToolbar(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    Window oldAncestor = ancestorOf(toolBar);
    Point where = whereToFloatTo();
    driver.floatTo(toolBar, where.x, where.y);
    driver.unfloat(toolBar);
    assertThat(ancestorOf(toolBar)).isSameAs(oldAncestor);
  }

  @Test(dataProvider = "unfloatConstraints")
  public void shouldUnfloatToolbarToGivenPosition(String constraint, EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    Window originalAncestor = ancestorOf(toolBar);
    Point where = whereToFloatTo();
    driver.floatTo(toolBar, where.x, where.y);
    driver.unfloat(toolBar, constraint);
    assertThat(ancestorOf(toolBar)).isSameAs(originalAncestor);
    assertThat(componentAt(constraint)).isSameAs(toolBar);
  }

  private Component componentAt(String constraint) {
    return window.borderLayout.getLayoutComponent(constraint);
  }

  private Point whereToFloatTo() {
    Rectangle bounds = boundsOf(ancestorOf(toolBar));
    int x = bounds.x + bounds.width + 10;
    int y = bounds.y + bounds.height + 10;
    return new Point(x, y);
  }

  @DataProvider(name = "unfloatConstraints") public Object[][] unfloatConstraints() {
    return new Object[][] {
        { NORTH, AWT },
        { NORTH, ROBOT },
        { EAST, AWT },
        { EAST, ROBOT },
        { SOUTH, AWT },
        { SOUTH, ROBOT },
        { WEST, AWT },
        { WEST, ROBOT }
      };
  }

  private static Window ancestorOf(final JToolBar toolBar) {
    return execute(new GuiQuery<Window>() {
      protected Window executeInEDT() {
        return getWindowAncestor(toolBar);
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final BorderLayout borderLayout = new BorderLayout();
    final JToolBar toolBar = new JToolBar();

    static MyWindow newWindow() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    MyWindow() {
      super(JToolBarDriverTest.class);
      toolBar.setFloatable(true);
      setLayout(borderLayout);
      add(toolBar, NORTH);
      toolBar.add(new JLabel("Hello"));
      setPreferredSize(new Dimension(300, 200));
    }
  }
}
