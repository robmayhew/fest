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
package org.fest.swing.fixture.bug138;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.RobotFixture;
import org.fest.swing.core.ScreenLock;
import org.fest.swing.core.TestRobotFixture;
import org.fest.swing.hierarchy.ComponentHierarchy;

import static org.easymock.classextension.EasyMock.createMock;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=138">Bug 138</a>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class WindowDisposalTest {

  private ComponentHierarchy hierarchy;
  private RobotFixture robot;
  
  @BeforeMethod public void setUp() {
    hierarchy = createMock(ComponentHierarchy.class);
  }

  @Test public void shouldDisposeWindows() {
    try {
      robot = new TestRobotFixture(hierarchy);
    } finally {
      if (robot == null) return;
      try {
        ScreenLock.instance().release(robot);
      } catch (Exception e) {}
    }
  }
  
}
