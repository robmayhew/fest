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

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JTree;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GuiQuery;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JTreeCellRendererQuery}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = { GUI, EDT_ACTION })
public class JTreeCellRendererQueryTest {

  private MyWindow window;

  @BeforeMethod public void setUp() {
    window = MyWindow.newWindow();
    window.display();
  }

  @AfterMethod public void tearDown() {
    window.destroy();
  }

  public void shouldReturnCellRendererComponentOfJTree() {
    Component renderer = JTreeCellRendererQuery.cellRendererIn(window.tree, "one");
    assertThat(renderer).isInstanceOf(JLabel.class);
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JTree tree = new JTree(new Object[] { "one", "two", "three" });

    static MyWindow newWindow() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() { return new MyWindow(); }
      });
    }

    MyWindow() {
      super(JTreeCellRendererQueryTest.class);
      tree.setPreferredSize(new Dimension(80, 60));
      add(tree);
    }
  }
}
