/*
 * Created on Aug 19, 2008
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
import javax.swing.tree.TreePath;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.swing.testing.TestGroups.EDT_QUERY;

/**
 * Tests for <code>{@link JTreeExpandPathTask}</code>.
 *
 * @author Yvonne Wang
 */
@Test(groups = EDT_QUERY)
public class JTreeExpandPathTaskTest {

  private JTree tree;
  private TreePath treePath;

  @BeforeMethod public void setUp() {
    tree = createMock(JTree.class);
    treePath = createMock(TreePath.class);
  }

  public void shouldExpandPath() {
    new EasyMockTemplate(tree) {
      protected void expectations() {
        tree.expandPath(treePath);
        expectLastCall().once();
      }

      protected void codeToTest() {
        JTreeExpandPathTask.expandPathTask(tree, treePath).executeInEDT();
      }
    }.run();
  }
}
