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

import javax.swing.JList;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestListModel;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JListSelectedIndicesQuery}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = { GUI, EDT_ACTION })
public class JListSelectedIndicesQueryTest {

  private Robot robot;
  private MyList list;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    list = window.list;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnSelectedIndicesOfJList() {
    assertThat(JListSelectedIndicesQuery.selectedIndicesOf(list)).containsOnly(0, 2);
    assertThat(list.methodGetSelectedIndicesWasInvoked()).isTrue();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }

    final MyList list = new MyList("One", "Two", "Three");

    private MyWindow() {
      super(JListSelectedIndicesQueryTest.class);
      addComponents(list);
      list.setSelectedIndices(new int[] { 0, 2 });
    }
  }

  private static class MyList extends JList {
    private static final long serialVersionUID = 1L;

    private boolean methodGetSelectedIndicesInvoked;

    MyList(Object... elements) {
      setModel(new TestListModel(elements));
    }

    @Override public int[] getSelectedIndices() {
      methodGetSelectedIndicesInvoked = true;
      return super.getSelectedIndices();
    }

    boolean methodGetSelectedIndicesWasInvoked() { return methodGetSelectedIndicesInvoked; }
  }
}
