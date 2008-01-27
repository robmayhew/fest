/*
 * Created on Jan 26, 2008
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

import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextField;

import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.testing.TestFrame;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTextComponentDriver}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTextComponentDriverTest {

  private RobotFixture robot;
  private MyFrame frame;
  private JTextComponentDriver driver;

  private static Logger logger = Logger.getAnonymousLogger();

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    frame = new MyFrame(getClass());
    robot.showWindow(frame, new Dimension(200, 200));
    driver = new JTextComponentDriver(robot, frame.textField);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test public void shouldSelectOnlyGivenText() {
    driver.selectText(8, 14);
    assertThat(frame.textField.getSelectedText()).isEqualTo("a test");
  }

  @Test public void shouldThrowErrorIfIndicesAreOutOfBounds() {
    try {
      driver.selectText(20, 22);
      fail();
    } catch (ActionFailedException expected) {
      logger.log(Level.INFO, expected.getMessage(), expected);
    }
  }

  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JTextField textField = new JTextField("This is a test");

    MyFrame(Class<?> testClass) {
      super(testClass);
      add(textField);
    }
  }
}
