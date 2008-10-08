/*
 * Created on Aug 8, 2008
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
import java.awt.Rectangle;

import javax.swing.JButton;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JComponentVisibleRectQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class JComponentVisibleRectQueryTest {

  private Robot robot;
  private MyButton button;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    button = window.button;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUpWithoutDisposingWindows();
  }

  public void shouldReturnVisibleRectOfJComponent() {
    Dimension size = sizeOf(button);
    Rectangle expected = new Rectangle(0, 0, size.width, size.height);
    Rectangle visibleRect = JComponentVisibleRectQuery.visibleRectOf(button);
    assertThat(visibleRect).isEqualTo(expected);
    assertThat(button.methodGetVisibleRectWasInvoked()).isTrue();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }

    final MyButton button = new MyButton("A button");

    private MyWindow() {
      super(JComponentVisibleRectQueryTest.class);
      addComponents(button);
    }
  }

  static class MyButton extends JButton {
    private static final long serialVersionUID = 1L;

    MyButton(String text) {
      super(text);
    }

    private boolean methodGetVisibleRectInvoked;

    @Override public Rectangle getVisibleRect() {
      methodGetVisibleRectInvoked = true;
      return super.getVisibleRect();
    }

    boolean methodGetVisibleRectWasInvoked() { return methodGetVisibleRectInvoked; }
  }
}
