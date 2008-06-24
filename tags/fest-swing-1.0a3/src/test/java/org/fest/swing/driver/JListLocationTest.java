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

import javax.swing.JList;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.testing.TestFrame;
import org.fest.swing.testing.TestList;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link JListLocation}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JListLocationTest {

  private Robot robot;
  private JList list;
  private JListLocation location;
  
  @BeforeMethod public void setUp() {
    location = new JListLocation();
    robot = robotWithNewAwtHierarchy();
    MyFrame frame = new MyFrame();
    list = frame.list;
    robot.showWindow(frame);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  public void shouldReturnLocationOfIndex() {
    Point p = location.pointAt(list, 2);
    int index = list.locationToIndex(p);
    assertThat(index).isEqualTo(2);
  }
  
  public void shouldThrowErrorIfIndexOutOfBoundsWhenLookingForLocation() {
    try {
      location.pointAt(list, 8);
      fail();
    } catch (LocationUnavailableException expected) {
      assertThat(expected).message().isEqualTo("Item index (8) should be between [0] and [2] (inclusive)");
    }
  }
  
  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    final TestList list = new TestList("one", "two", "three");
    
    MyFrame() {
      super(JListLocationTest.class);
      add(list);
      list.setPreferredSize(new Dimension(60, 80));
      setPreferredSize(new Dimension(100, 120));
    }
  }

}
