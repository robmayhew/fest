/*
 * Created on Aug 18, 2008
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

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.testing.BooleanProvider;
import org.fest.swing.testing.MethodInvocations;
import org.fest.swing.testing.TestWindow;
import org.fest.swing.testing.MethodInvocations.Args;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.testing.MethodInvocations.Args.args;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JTreeExpandedPathQuery}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = { GUI, EDT_ACTION })
public class JTreeExpandedPathQueryTest {

  private Robot robot;
  private MyTree tree;
  private TreePath rootPath;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    tree = window.tree;
    rootPath = new TreePath(tree.root);
    robot.showWindow(window);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(groups = { GUI, EDT_ACTION }, dataProvider = "booleans", dataProviderClass = BooleanProvider.class)
  public void shouldIndicateIfPathExpanded(final boolean expanded) {
    setPathExpanded(tree, rootPath, expanded);
    robot.waitForIdle();
    tree.startRecording();
    assertThat(JTreeExpandedPathQuery.isExpanded(tree, rootPath)).isEqualTo(expanded);
    tree.requireInvoked("isExpanded", args(rootPath));
  }

  private static void setPathExpanded(final JTree tree, final TreePath path, final boolean expanded) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        if (expanded) tree.expandPath(path);
        else tree.collapsePath(path);
      }
    });
  }
  
  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }
    
    final MyTree tree = new MyTree();
    
    private MyWindow() {
      super(JTreeExpandedPathQueryTest.class);
      addComponents(tree);
    }
  }
  
  private static class MyTree extends JTree {
    private static final long serialVersionUID = 1L;
    
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    final DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
    
    MyTree() {
      super();
      root.add(new DefaultMutableTreeNode("node"));
      setModel(new DefaultTreeModel(root, false));
    }

    @Override public boolean isExpanded(TreePath path) {
      if (recording) methodInvocations.invoked("isExpanded", args(path));
      return super.isExpanded(path);
    }

    void startRecording() { recording = true; }

    MethodInvocations requireInvoked(String methodName, Args args) {
      return methodInvocations.requireInvoked(methodName, args);
    }
  }
}
