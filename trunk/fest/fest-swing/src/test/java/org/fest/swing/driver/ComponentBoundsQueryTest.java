/*
 * Created on Aug 18, 2008
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

import java.awt.Rectangle;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GuiTask;
import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link ComponentBoundsQuery}</code>.
 *
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class ComponentBoundsQueryTest {

  private static final Rectangle WINDOW_BOUNDS = new Rectangle(10, 20, 500, 300);

  private Robot robot;
  private MyWindow window;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = MyWindow.createNew();
    robot.showWindow(window);
    execute(new GuiTask() {
      protected void executeInEDT() {
        window.setBounds(new Rectangle(WINDOW_BOUNDS));
      }
    });
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnBoundsOfComponent() {
    assertThat(ComponentBoundsQuery.boundsOf(window)).isEqualTo(WINDOW_BOUNDS);
    assertThat(window.methodGetBoundsWasInvoked()).isTrue();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }

    private boolean methodGetBoundsInvoked;

    private MyWindow() {
      super(ComponentBoundsQueryTest.class);
    }

    @Override public Rectangle getBounds() {
      methodGetBoundsInvoked = true;
      return super.getBounds();
    }

    boolean methodGetBoundsWasInvoked() { return methodGetBoundsInvoked; }
  }
}
