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

import javax.swing.JToolBar;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestFrame;

import static java.awt.BorderLayout.*;
import static javax.swing.SwingUtilities.getWindowAncestor;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
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
  private MyFrame frame;
  private JToolBarDriver driver;
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JToolBarDriver(robot);
    frame = new MyFrame();
    robot.showWindow(frame);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  @Test public void shouldFloatToolbar() {
    Window oldAncestor = toolbarAncestor();
    Point where = whereToFloatTo();
    driver.floatTo(toolBar(), where.x, where.y);
    assertToolBarIsFloating(oldAncestor);
  }
  
  private void assertToolBarIsFloating(Window oldAncestor) {
    Window newAncestor = toolbarAncestor();
    assertThat(newAncestor).isNotSameAs(oldAncestor);
    Point newAncestorLocation = newAncestor.getLocation();
    Point oldAncestorLocation = oldAncestor.getLocation();
    assertThat(newAncestorLocation.x).isGreaterThan(oldAncestorLocation.x);
    assertThat(newAncestorLocation.y).isGreaterThan(oldAncestorLocation.y);
  }

  @Test(dependsOnMethods = "shouldFloatToolbar")
  public void shouldUnfloatToolbar() {
    Window oldAncestor = toolbarAncestor();
    Point where = whereToFloatTo();
    driver.floatTo(toolBar(), where.x, where.y);
    driver.unfloat(toolBar());
    assertThat(toolbarAncestor()).isSameAs(oldAncestor);
  }
  
  @Test(dependsOnMethods = "shouldFloatToolbar", dataProvider = "unfloatConstraints")
  public void shouldUnfloatToolbarToGivenPosition(String constraint) {
    Window originalAncestor = toolbarAncestor();
    Point where = whereToFloatTo();
    driver.floatTo(toolBar(), where.x, where.y);
    driver.unfloat(toolBar(), constraint);
    assertThat(toolbarAncestor()).isSameAs(originalAncestor);
    assertThat(componentAt(constraint)).isSameAs(toolBar());
  }

  private Component componentAt(String constraint) {
    return frame.borderLayout.getLayoutComponent(constraint);
  }

  private Point whereToFloatTo() {
    Rectangle bounds = toolbarAncestor().getBounds();
    int x = bounds.x + bounds.width + 10;
    int y = bounds.y + bounds.height + 10;
    return new Point(x, y);
  }
  
  @DataProvider(name = "unfloatConstraints") public Object[][] unfloatConstraints() {
    return new Object[][] { { NORTH }, { EAST }, { SOUTH }, { WEST } };  
  }
  
  private Window toolbarAncestor() {
    return getWindowAncestor(toolBar());
  }

  private JToolBar toolBar() {
    return frame.toolBar;
  }
  
  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    final BorderLayout borderLayout = new BorderLayout();
    final JToolBar toolBar = new JToolBar();

    MyFrame() {
      super(JToolBarDriverTest.class);
      toolBar.setFloatable(true);
      setLayout(borderLayout);
      add(toolBar, NORTH);
      setPreferredSize(new Dimension(300, 200));
    }
  }
}
