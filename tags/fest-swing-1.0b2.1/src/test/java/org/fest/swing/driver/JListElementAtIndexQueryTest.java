/*
 * Created on Aug 7, 2008
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
import org.fest.swing.core.RobotFixture;
import org.fest.swing.testing.MethodInvocations;
import org.fest.swing.testing.TestListModel;
import org.fest.swing.testing.TestWindow;
import org.fest.swing.testing.MethodInvocations.Args;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.MethodInvocations.Args.args;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JListElementAtIndexQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class JListElementAtIndexQueryTest {

  private Robot robot;
  private MyList list;
  private MyListModel listModel;

  @BeforeMethod public void setUp() {
    robot = RobotFixture.robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    list = window.list;
    listModel = list.model;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnElementAtIndexInJList() {
    int index = 1;
    listModel.startRecording();
    assertThat(JListElementAtIndexQuery.elementAt(list, index)).isEqualTo("Two");
    listModel.requireInvoked("getElementAt", args(index));
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }

    final MyList list = new MyList("One", "Two", "Three");

    private MyWindow() {
      super(JListElementAtIndexQueryTest.class);
      addComponents(list);
    }
  }

  private static class MyList extends JList {
    private static final long serialVersionUID = 1L;

    final MyListModel model;

    MyList(Object... elements) {
      model = new MyListModel(elements);
      setModel(model);
    }
  }

  private static class MyListModel extends TestListModel {
    private static final long serialVersionUID = 1L;

    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MyListModel(Object... elements) {
      super(elements);
    }

    void startRecording() { recording = true; }

    @Override public Object getElementAt(int index) {
      if (recording) methodInvocations.invoked("getElementAt", args(index));
      return super.getElementAt(index);
    }

    MethodInvocations requireInvoked(String methodName, Args args) {
      return methodInvocations.requireInvoked(methodName, args);
    }
  }
}
