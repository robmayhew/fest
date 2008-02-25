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

import java.awt.Dimension;

import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.testing.TestFrame;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.FUNCTIONAL;

/**
 * Tests for <code>{@link JTextComponentDriver}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = FUNCTIONAL)
public class JTextComponentDriverTest {

  private RobotFixture robot;
  private MyFrame frame;
  private JTextComponentDriver driver;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    frame = new MyFrame();
    robot.showWindow(frame, new Dimension(200, 200));
    driver = new JTextComponentDriver(robot);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test public void shouldSelectOnlyGivenText() {
    driver.selectText(target(), 8, 14);
    assertThat(target().getSelectedText()).isEqualTo("a test");
  }

  @Test public void shouldReplaceText() {
    target().setText("Hi");
    driver.replaceText(target(), "Bye");
    assertThat(target().getText()).isEqualTo("Bye");
  }
  
  @Test public void shouldThrowErrorIfIndicesAreOutOfBounds() {
    try {
      driver.selectText(target(), 20, 22);
      fail();
    } catch (ActionFailedException expected) {
      assertThat(expected).message().contains("Unable to get location for index '20' in javax.swing.JTextField");
    }
  }

  private JTextField target() {
    return frame.textField;
  }

  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JTextField textField = new JTextField("This is a test");

    MyFrame() {
      super(JTextComponentDriverTest.class);
      add(textField);
    }
  }
}
