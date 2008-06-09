/*
 * Created on Jun 1, 2008
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
package org.fest.swing.core;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.swing.testing.TestGroups.*;

import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.core.ScreenLock;
import org.fest.swing.hierarchy.ComponentHierarchy;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=138">Bug 138</a>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, BUG })
public class WindowDisposalTest {

  private ComponentHierarchy hierarchy;
  private RobotFixture robot;

  @BeforeMethod public void setUp() {
    hierarchy = createMock(ComponentHierarchy.class);
    robot = new TestRobotFixture(hierarchy);
  }

  @AfterMethod public void tearDown() {
    ScreenLock screenLock = ScreenLock.instance();
    if (!screenLock.acquiredBy(robot)) return;
    try {
      screenLock.release(robot);
    } catch (Exception e) {}
  }

  @Test public void shouldDisposeWindows() {
    final List<Container> roots = new ArrayList<Container>();
    final JFrame frame = new JFrame("Hello");
    roots.add(frame);
    new EasyMockTemplate(hierarchy) {
      protected void expectations() {
        hierarchy.roots();
        expectLastCall().andReturn(roots);
        hierarchy.dispose(frame);
      }

      protected void codeToTest() {
        robot.cleanUp();
      }
    }.run();
  }

  @Test public void shouldNotDisposeWindows() {
    new EasyMockTemplate(hierarchy) {
      protected void expectations() {}

      protected void codeToTest() {
        robot.cleanUpWithoutDisposingWindows();
      }
    }.run();
  }

}
