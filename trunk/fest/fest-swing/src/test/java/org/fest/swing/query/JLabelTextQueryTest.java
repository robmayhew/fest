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
package org.fest.swing.query;

import javax.swing.JLabel;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JLabelTextQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI,  EDT_ACTION })
public class JLabelTextQueryTest {

  private Robot robot;
  private MyLabel label;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    label = window.label;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnTextOfJLabel() {
    assertThat(JLabelTextQuery.textOf(label)).isEqualTo("Hello");
    assertThat(label.methodGetTextWasInvoked()).isTrue();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final MyLabel label = new MyLabel("Hello");

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(JLabelTextQueryTest.class);
      addComponents(label);
    }
  }

  private static class MyLabel extends JLabel {
    private static final long serialVersionUID = 1L;

    private boolean methodGetTextInvoked;

    MyLabel(String text) {
      super(text);
    }

    @Override public String getText() {
      methodGetTextInvoked = true;
      return super.getText();
    }

    boolean methodGetTextWasInvoked() { return methodGetTextInvoked; }
  }
}
