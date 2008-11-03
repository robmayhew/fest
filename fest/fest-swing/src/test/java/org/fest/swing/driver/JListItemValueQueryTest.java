/*
 * Created on Nov 3, 2008
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

import javax.swing.JList;
import javax.swing.JScrollPane;

import org.testng.annotations.*;

import org.fest.swing.cell.JListCellReader;
import org.fest.swing.core.Robot;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.edt.CheckThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JListItemValueQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JListItemValueQueryTest {

  private Robot robot;
  private JList list;
  private JListCellReader cellReader;

  @BeforeClass public void setUpOnce() {
    CheckThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = RobotFixture.robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    list = window.list;
    robot.showWindow(window);
    cellReader = new BasicJListCellReader();
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(dataProvider = "items", groups = GUI)
  public void shouldReturnItemValueAsText(final int index, String expectedValue) {
    String actualValue = execute(new GuiQuery<String>() {
      protected String executeInEDT() {
        return JListItemValueQuery.itemValue(list, index, cellReader);
      }
    });
    assertThat(actualValue).isEqualTo(expectedValue);
  }
    
  @DataProvider(name = "items") public Object[][] items() {
    return new Object[][] {
        { 0, "Yoda" },
        { 1, "Luke" }
    };
  }
  
  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;
    private static final Dimension LIST_SIZE = new Dimension(80, 40);

    final JList list = new JList(array(new Jedi("Yoda"), new Jedi("Luke")));

    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JListItemValueQuery.class);
      addComponents(decorate(list));
    }

    private static JScrollPane decorate(JList list) {
      JScrollPane scrollPane = new JScrollPane(list);
      scrollPane.setPreferredSize(LIST_SIZE);
      return scrollPane;
    }
  }
}
