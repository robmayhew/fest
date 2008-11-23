/*
 * Created on Nov 14, 2008
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

import org.testng.annotations.*;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JComboBoxCellReader;
import org.fest.swing.core.Robot;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.edt.CheckThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JComboBoxMatchingItemQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, ACTION })
public class JComboBoxMatchingItemQueryTest {
  
  private static final String[] ITEMS = { "first", "second", "third" };

  private Robot robot;
  private JComboBox comboBox;
  private JComboBoxCellReader cellReader;
  
  @BeforeClass public void setUpOnce() {
    CheckThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = RobotFixture.robotWithNewAwtHierarchy();
    cellReader = new BasicJComboBoxCellReader();
    MyWindow window = MyWindow.createNew();
    comboBox = window.comboBox;
    robot.showWindow(window);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  public void shouldReturnMatchingIndex() {
    for (int i = 0; i < 3; i++)
      assertThat(JComboBoxMatchingItemQuery.matchingItemIndex(comboBox, ITEMS[i], cellReader)).isEqualTo(i);
  }
  
  public void shouldReturnNegativeOneIfNoMatchingIndexFound() {
    assertThat(JComboBoxMatchingItemQuery.matchingItemIndex(comboBox, "Hello", cellReader)).isEqualTo(-1);
  }
  
  @DataProvider(name = "indices") public Object[][] indices() {
    return new Object[][] { { 0 }, { 1 }, { 2 } };
  }
  
  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JComboBox comboBox = new JComboBox(ITEMS);

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JComboBoxMatchingItemQuery.class);
      addComponents(comboBox);
    }
  }

}
