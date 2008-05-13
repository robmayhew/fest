/*
 * Created on May 12, 2008
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
package org.fest.swing.fixture.bug135;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.fixture.JTableFixture;
import org.fest.swing.testing.CustomCellRenderer;
import org.fest.swing.testing.TestFrame;
import org.fest.swing.testing.TestTable;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.util.Arrays.format;
import static org.fest.util.Arrays.array;
import static org.fest.util.Strings.concat;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=135">Bug 135</a>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class TableContentsTest {

  private Robot robot;
  private MyFrame frame;
  private JTableFixture fixture;
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    frame = new MyFrame();
    robot.showWindow(frame);
    fixture = new JTableFixture(robot, frame.table);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  @Test public void shouldReturnTableContents() {
    String[][] contents = fixture.contents();
    assertThat(contents.length).isEqualTo(6);
    assertThat(contents[0].length).isEqualTo(3);
    assertThat(contents[0][0]).isEqualTo("0-0");
    assertThat(contents[0][1]).isEqualTo("first");
    assertThat(contents[0][2]).isEqualTo("false");
    assertThat(contents[1][0]).isEqualTo("1-0");
    assertThat(contents[1][1]).isEqualTo("first");
    assertThat(contents[1][2]).isEqualTo("false");
    assertThat(contents[2][0]).isEqualTo("2-0");
    assertThat(contents[2][1]).isEqualTo("first");
    assertThat(contents[2][2]).isEqualTo("false");
    assertThat(contents[3][0]).isEqualTo("3-0");
    assertThat(contents[3][1]).isEqualTo("first");
    assertThat(contents[3][2]).isEqualTo("false");
    assertThat(contents[4][0]).isEqualTo("4-0");
    assertThat(contents[4][1]).isEqualTo("first");
    assertThat(contents[4][2]).isEqualTo("false");
    assertThat(contents[5][0]).isEqualTo("5-0");
    assertThat(contents[5][1]).isEqualTo("first");
    assertThat(contents[5][2]).isEqualTo("false");
  }
  
  @Test public void shouldPassIfContentIsEqualToExpected() {
    String[][] contents = new String[][] {
        { "0-0", "first", "false" },  
        { "1-0", "first", "false" }, 
        { "2-0", "first", "false" },  
        { "3-0", "first", "false" },  
        { "4-0", "first", "false" },  
        { "5-0", "first", "false" }  
    };
    fixture.requireContents(contents);
  }

  @Test(dependsOnMethods = "shouldReturnTableContents") 
  public void shouldFailIfContentNotEqualToExpected() {
    try {
      fixture.requireContents(new String[][] { { "hello" } });
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'contents'")
                             .contains("expected:<[['hello']]>")
                             .contains(concat("but was:<", format(fixture.contents()), ">"));
    }
  }
  
  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    private final TestTable table = new TestTable("table", 6, 3);
    private final JComboBox comboBox = new JComboBox(array("first", "second"));
    private final JCheckBox checkBox = new JCheckBox(); 
    
    public MyFrame() {
      super(TableContentsTest.class);
      add(new JScrollPane(table));
      setPreferredSize(new Dimension(600, 300));
      comboBox.setSelectedIndex(0);
      updateCellRendererComponent(1, comboBox);
      updateCellRendererComponent(2, checkBox);
    }
    
    private void updateCellRendererComponent(int column, Component c) {
      table.getColumnModel().getColumn(column).setCellRenderer(new CustomCellRenderer(c));
    }
  }
}
