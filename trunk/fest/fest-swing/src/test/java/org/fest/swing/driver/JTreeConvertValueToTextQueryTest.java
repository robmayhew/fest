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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.MethodInvocations;
import org.fest.swing.testing.TestWindow;
import org.fest.swing.testing.MethodInvocations.Args;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.MethodInvocations.Args.args;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JTreeConvertValueToTextQuery}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = { GUI, ACTION })
public class JTreeConvertValueToTextQueryTest {

  private Robot robot;
  private MyTree tree;
  private Object rootValue;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    tree = window.tree;
    rootValue = window.rootValue;
    robot.showWindow(window);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldIndicateIfPathExpanded() {
    tree.startRecording();
    assertThat(JTreeConvertValueToTextQuery.convertValueToText(tree, rootValue)).isEqualTo("Yoda");
    tree.requireInvoked("convertValueToText", args(rootValue, false, false, false, 0, false));
  }
  
  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }
    
    final Object rootValue = new Jedi("Yoda");
    final MyTree tree = new MyTree(rootValue);
    
    private MyWindow() {
      super(JTreeConvertValueToTextQueryTest.class);
      addComponents(tree);
    }
  }
  
  private static class MyTree extends JTree {
    private static final long serialVersionUID = 1L;
    
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MyTree(Object rootValue) {
      super(new DefaultMutableTreeNode(rootValue));
    }

    @Override public String convertValueToText(Object value, boolean selected, boolean expanded, boolean leaf, int row,
        boolean hasFocus) {
      if (recording) 
        methodInvocations.invoked("convertValueToText", args(value, selected, expanded, leaf, row, hasFocus));
      return super.convertValueToText(value, selected, expanded, leaf, row, hasFocus);
    }

    void startRecording() { recording = true; }

    MethodInvocations requireInvoked(String methodName, Args args) {
      return methodInvocations.requireInvoked(methodName, args);
    }
  }
}
