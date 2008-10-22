/*
 * Created on Oct 22, 2008
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

import javax.swing.JLabel;
import javax.swing.JTree;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.*;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JTreeCellValueAsTextQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, EDT_ACTION })
public class JTreeCellValueAsTextQueryTest {

  private Robot robot;
  private JTree tree;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    tree = window.tree;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnCellRendererComponentOfJTree() {
    CellRendererComponentReaderStub reader = new CellRendererComponentReaderStub("one");
    String value = JTreeCellValueAsTextQuery.nodeValue(tree, "one", reader);
    assertThat(reader.cellRendererComponent()).isInstanceOf(JLabel.class);
    assertThat(value).isEqualTo("one");
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JTree tree = new JTree(array("one", "two", "three"));

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(JTreeCellValueAsTextQueryTest.class);
      tree.setPreferredSize(new Dimension(80, 60));
      add(tree);
    }
  }
}
