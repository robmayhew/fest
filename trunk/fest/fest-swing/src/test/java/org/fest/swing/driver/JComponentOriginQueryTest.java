/*
 * Created on Aug 11, 2008
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

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JButton;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GuiQuery;
import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JComponentOriginQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class JComponentOriginQueryTest {

  private Robot robot;
  private MyButton button;
  private MyWindow window;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = MyWindow.createNew();
    button = window.button;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnOriginOfJComponent() {
    Point expected = execute(new GuiQuery<Point>() {
      protected Point executeInEDT()  {
        Rectangle bounds = button.getBounds();
        return new Point(bounds.x, bounds.y);
      }
    });
    Point origin = JComponentOriginQuery.originOf(button);
    assertThat(origin).isEqualTo(expected);
    assertThat(button.methodGetXWasInvoked()).isTrue();
    assertThat(button.methodGetYWasInvoked()).isTrue();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }

    final MyButton button = new MyButton("A button");

    private MyWindow() {
      super(JComponentOriginQueryTest.class);
      addComponents(button);
    }
  }

  static class MyButton extends JButton {
    private static final long serialVersionUID = 1L;

    MyButton(String text) {
      super(text);
    }

    private boolean methodGetXInvoked;
    private boolean methodGetYInvoked;

    @Override public int getX() {
      methodGetXInvoked = true;
      return super.getX();
    }

    @Override public int getY() {
      methodGetYInvoked = true;
      return super.getY();
    }

    boolean methodGetXWasInvoked() { return methodGetXInvoked; }
    boolean methodGetYWasInvoked() { return methodGetYInvoked; }
  }
}
