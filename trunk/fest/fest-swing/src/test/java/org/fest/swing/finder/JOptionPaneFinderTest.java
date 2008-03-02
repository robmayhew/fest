/*
 * Created on Oct 29, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.finder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Robot;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.fixture.JOptionPaneFixture;
import org.fest.swing.testing.TestFrame;

import static java.util.concurrent.TimeUnit.SECONDS;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.testing.TestGroups.FUNCTIONAL;
import static org.fest.swing.util.AWT.centerOf;

/**
 * Tests for <code>{@link JOptionPaneFinder}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = FUNCTIONAL)
public class JOptionPaneFinderTest {

  private Robot robot;
  private MyFrame frame;

  @BeforeMethod public void setUp() {
    robot = RobotFixture.robotWithNewAwtHierarchy();
    frame = new MyFrame();
    robot.showWindow(frame);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test public void shouldFindFileChooser() {
    clickMessageButton();
    JOptionPaneFixture found = JOptionPaneFinder.findOptionPane().using(robot);
    assertThat(found.target).isNotNull();
  }

  @Test public void shouldFindFileChooserBeforeGivenTimeoutExpires() {
    new Thread() {
      @Override public void run() {
        pause(2000);
        clickMessageButton();
      }
    }.start();
    JOptionPaneFixture found = JOptionPaneFinder.findOptionPane().withTimeout(5, SECONDS).using(robot);
    assertThat(found.target).isNotNull();
  }

  private void clickMessageButton() {
    JButton button = frame.messageButton;
    robot.click(button, centerOf(button), MouseButton.LEFT_BUTTON, 1);
  }

  @Test(expectedExceptions = WaitTimedOutError.class)
  public void shouldFailIfFileChooserNotFound() {
    JFileChooserFinder.findFileChooser().using(robot);
  }

  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JButton messageButton = new JButton("Message");

    public MyFrame() {
      super(JOptionPaneFinderTest.class);
      setUp();
    }

    private void setUp() {
      messageButton.setName("message");
      messageButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          JOptionPane.showMessageDialog(MyFrame.this, "A message");
        }
      });
      add(messageButton);
    }
  }

}