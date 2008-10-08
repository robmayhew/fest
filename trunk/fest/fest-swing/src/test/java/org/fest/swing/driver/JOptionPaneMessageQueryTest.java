/*
 * Created on Aug 6, 2008
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

import javax.swing.JOptionPane;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.JOptionPaneLauncher.launch;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JOptionPaneMessageQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class JOptionPaneMessageQueryTest {

  private static final String MESSAGE = "Hello World";

  private Robot robot;
  private MyOptionPane optionPane;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    optionPane = MyOptionPane.createNew(MESSAGE);
    launch(optionPane);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnMessageOfJOptionPane() {
    assertThat(JOptionPaneMessageQuery.messageOf(optionPane)).isEqualTo(MESSAGE);
    assertThat(optionPane.methodGetMessageWasInvoked()).isTrue();
  }

  private static class MyOptionPane extends JOptionPane {
    private static final long serialVersionUID = 1L;

    private boolean methodGetMessageInvoked;

    static MyOptionPane createNew(String message) {
      return new MyOptionPane(message);
    }

    private MyOptionPane(String message) {
      super(message, INFORMATION_MESSAGE);
    }

    @Override public Object getMessage() {
      methodGetMessageInvoked = true;
      return super.getMessage();
    }

    boolean methodGetMessageWasInvoked() { return methodGetMessageInvoked; }
  }
}
