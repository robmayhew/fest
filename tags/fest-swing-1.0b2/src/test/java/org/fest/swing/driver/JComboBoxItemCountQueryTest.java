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

import javax.swing.JComboBox;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.MethodInvocations;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JComboBoxItemCountQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class JComboBoxItemCountQueryTest {

  private Robot robot;
  private MyComboBox comboBox;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    comboBox = window.comboBox;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }


  public void shouldReturnItemCountOfJComboBox() {
    comboBox.startRecording();
    assertThat(JComboBoxItemCountQuery.itemCountOf(comboBox)).isEqualTo(3);
    comboBox.requireInvoked("getItemCount");
  }

  @DataProvider(name = "itemCounts") public Object[][] itemCounts() {
    return new Object[][] { { 1 }, { 6 }, { 8 }, { 26 } };
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final MyComboBox comboBox = new MyComboBox("first", "second", "third");

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(JComboBoxItemCountQueryTest.class);
      addComponents(comboBox);
    }
  }

  private static class MyComboBox extends JComboBox {
    private static final long serialVersionUID = 1L;

    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MyComboBox(Object... items) {
      super(items);
    }

    void startRecording() { recording = true; }

    @Override public int getItemCount() {
      if (recording) methodInvocations.invoked("getItemCount");
      return super.getItemCount();
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations .requireInvoked(methodName);
    }
  }
}
