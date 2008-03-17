/*
 * Created on Mar 16, 2008
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
package org.fest.swing.fixture.bug116;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.GUI;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.fest.swing.core.Robot;
import org.fest.swing.fixture.JComboBoxFixture;
import org.fest.swing.testing.TestFrame;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=116">Bug 116</a>.
 *
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class SelectJComboBoxItemTest {

  private Robot robot;
  private JComboBox target;
  private JComboBoxFixture fixture;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyFrame frame = new MyFrame();
    target = frame.comboBox;
    fixture = new JComboBoxFixture(robot, target);
    robot.showWindow(frame);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test public void shouldScrollToSelectLastItem() {
    int toSelect = 99;
    fixture.selectItem(toSelect);
    assertThat(target.getSelectedIndex()).isEqualTo(toSelect);
  }

  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JComboBox comboBox = new JComboBox();

    public MyFrame() {
      super(SelectJComboBoxItemTest.class);
      add(comboBox);
      int itemCount = 100;
      Object[] content = new Object[itemCount];
      for (int i = 0; i < itemCount; i++) content[i] = String.valueOf(i + 1);
      comboBox.setModel(new DefaultComboBoxModel(content));
    }
  }

}
