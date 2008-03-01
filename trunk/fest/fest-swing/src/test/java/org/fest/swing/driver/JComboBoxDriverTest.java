/*
 * Created on Feb 24, 2008
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
import javax.swing.JList;
import javax.swing.ListModel;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.testing.TestFrame;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.FUNCTIONAL;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JComboBoxDriver}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = FUNCTIONAL)
public class JComboBoxDriverTest {

  private Robot robot;
  private JComboBox comboBox;
  private JComboBoxDriver driver;
  
  @BeforeMethod public void setUp() {
    robot = RobotFixture.robotWithNewAwtHierarchy();
    driver = new JComboBoxDriver(robot);
    MyFrame frame = new MyFrame();
    comboBox = frame.comboBox;
    robot.showWindow(frame);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test public void shouldReturnComboBoxContents() {
    String[] contents = driver.contentsOf(comboBox);
    assertThat(contents).isEqualTo(array("first", "second", "third"));
  }
  
  @Test public void shouldSelectItemAtGivenIndex() {
    driver.selectItem(comboBox, 2);
    assertThat(comboBox.getSelectedItem()).isEqualTo("third");
  }

  @Test public void shouldSelectItemWithGivenText() {
    driver.selectItem(comboBox, "second");
    assertThat(comboBox.getSelectedItem()).isEqualTo("second");
  }

  @Test public void shouldReturnTextAtGivenIndex() {
    String text = driver.text(comboBox, 2);
    assertThat(text).isEqualTo("third");
  }

  @Test public void shouldReturnDropDownList() {
    driver.click(comboBox);
    JList dropDownList = driver.dropDownList();
    assertThatListContains(dropDownList, "first", "second", "third");
  }
  
  private void assertThatListContains(JList list, Object...expected) {
    int expectedSize = expected.length;
    ListModel model = list.getModel();
    assertThat(model.getSize()).isEqualTo(expectedSize);
    for (int i = 0; i < expectedSize; i++)
      assertThat(model.getElementAt(i)).isEqualTo(expected[i]);
  }
  
  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JComboBox comboBox = new JComboBox(array("first", "second", "third"));
    
    public MyFrame() {
      super(JComboBoxDriverTest.class);
      add(comboBox);
    }
  }
}
