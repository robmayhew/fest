/*
 * Created on Apr 5, 2008
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
import static org.fest.swing.testing.TestGroups.GUI;

import java.awt.Dimension;

import javax.swing.JLabel;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestFrame;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JLabelDriver}</code>.
 *
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class JLabelDriverTest {

  private Robot robot;
  private JLabel label;
  private JLabelDriver driver;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JLabelDriver(robot);
    MyFrame frame = new MyFrame();
    label = frame.label;
    robot.showWindow(frame);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test public void shouldPassIfHasExpectedText() {
    label.setText("Hi");
    driver.requireText(label, "Hi");
  }

  @Test public void shouldFailIfDoesNotHaveExpectedText() {
    label.setText("Hi");
    try {
      driver.requireText(label, "Bye");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'text'").contains("expected:<'Bye'> but was:<'Hi'>");
    }
  }

  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JLabel label = new JLabel("This is a test");

    MyFrame() {
      super(JTextComponentDriverTest.class);
      add(label);
      setPreferredSize(new Dimension(200, 200));
    }
  }

}
