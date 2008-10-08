/*
 * Created on Aug 9, 2008
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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link ComponentVisibleQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class ComponentVisibleQueryTest {

  private Robot robot;
  private MyWindow window;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = MyWindow.createNew();
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldIndicateIfComponentIsNotVisible() {
    assertThat(ComponentVisibleQuery.isVisible(window)).isFalse();
    assertThat(window.methodIsVisibleWasInvoked()).isTrue();
  }

  public void shouldIndicateIfComponentIsVisible() {
    robot.showWindow(window);
    assertThat(ComponentVisibleQuery.isVisible(window)).isTrue();
    assertThat(window.methodIsVisibleWasInvoked()).isTrue();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }

    private boolean methodIsVisibleInvoked;

    private MyWindow() {
      super(ComponentVisibleQueryTest.class);
    }

    @Override public boolean isVisible() {
      methodIsVisibleInvoked = true;
      return super.isVisible();
    }

    boolean methodIsVisibleWasInvoked() { return methodIsVisibleInvoked; }
  }
}
