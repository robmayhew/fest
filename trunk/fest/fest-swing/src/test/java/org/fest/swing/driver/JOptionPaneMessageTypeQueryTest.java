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

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.JOptionPaneLauncher.launch;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JOptionPaneMessageTypeQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class JOptionPaneMessageTypeQueryTest {

  private Robot robot;
  private MyOptionPane optionPane;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    optionPane = MyOptionPane.createNew();
    launch(optionPane);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnMessageTypeOfJOptionPane() {
    assertThat(JOptionPaneMessageTypeQuery.messageTypeOf(optionPane)).isEqualTo(INFORMATION_MESSAGE);
    assertThat(optionPane.methodGetMessageTypeWasInvoked()).isTrue();
  }

  private static class MyOptionPane extends JOptionPane {
    private static final long serialVersionUID = 1L;
    private boolean methodGetMessageTypeInvoked;

    static MyOptionPane createNew() {
      return new MyOptionPane();
    }

    private MyOptionPane() {
      super("Hello World", INFORMATION_MESSAGE);
    }

    @Override public int getMessageType() {
      methodGetMessageTypeInvoked = true;
      return super.getMessageType();
    }

    boolean methodGetMessageTypeWasInvoked() { return methodGetMessageTypeInvoked; }
  }
}
