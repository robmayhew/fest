/*
 * Created on Oct 20, 2008
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

import javax.swing.JTable;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.testing.StopWatch;
import org.fest.swing.testing.TableDialogEditDemo;
import org.fest.swing.testing.TestWindow;

import static org.fest.swing.data.TableCell.row;
import static org.fest.swing.testing.TestGroups.*;
import static org.fest.util.Strings.concat;

/**
 * Tests for <a href="http://code.google.com/p/fest/issues/detail?id=216" target="_blank">Bug 216</a>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { BUG, GUI })
public class Bug216_PerformanceIssuesTest {

  private MyWindow window;
  private Robot robot;
  
  @BeforeMethod public void setUp() {
    GuiActionRunner.executeInEDT(true);
    window = MyWindow.createNew();
    window.display();
  }
  
  @AfterMethod public void tearDown() {
    if (robot != null) robot.cleanUp();
    //GuiActionRunner.executeInEDT(true);
  }
  
  public void shouldFindFrame() {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    robot = RobotFixture.robotWithCurrentAwtHierarchy();
    stopWatch.stop();
    printTiming("Created robot", stopWatch);
    stopWatch.start();
    FrameFixture frameFixture = WindowFinder.findFrame(Bug216_PerformanceIssuesTest.class.getSimpleName()).withTimeout(2000).using(robot);
    stopWatch.stop();
    printTiming("Frame found", stopWatch);
    stopWatch.start();
    frameFixture.table("table").enterValue(row(0).column(3), "332342");
    stopWatch.stop();
    printTiming("Text entered", stopWatch);
  }

  private static void printTiming(String message, StopWatch stopWatch) {
    System.out.println(concat(message, " in ", stopWatch.ellapsedTime(), " ms"));
  }
  
  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }

    final JTable table;
    
    private MyWindow() {
      super(Bug216_PerformanceIssuesTest.class);
      setName(getTitle());
      TableDialogEditDemo newContentPane = new TableDialogEditDemo();
      newContentPane.setOpaque(true); // content panes must be opaque
      setContentPane(newContentPane);
      table = newContentPane.table;
      table.setName("table");
    }
  }
}
