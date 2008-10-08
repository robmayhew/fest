/*
 * Created on Jun 22, 2008
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

import javax.swing.JButton;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link AbstractButtonClickTask}</code>.
 *
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class AbstractButtonClickTaskTest {

  private Robot robot;
  private MyButton button;

  @BeforeClass public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    button = window.button;
    robot.showWindow(window);
  }

  @AfterClass public void tearDown() {
    robot.cleanUp();
  }

  public void shouldClickButton() {
    AbstractButtonClickTask.doClick(button);
    robot.waitForIdle();
    assertThat(button.wasClicked()).isTrue();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final MyButton button = new MyButton("Hello");

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(AbstractButtonClickTaskTest.class);
    }
  }

  private static class MyButton extends JButton {
    private static final long serialVersionUID = 1L;

    private boolean clicked;

    static MyButton createNew(String text) {
      return new MyButton(text);
    }

    private MyButton(String text) {
      super(text);
    }

    @Override public void doClick() {
      clicked = true;
      super.doClick();
    }

    boolean wasClicked() { return clicked; }
  }
}
