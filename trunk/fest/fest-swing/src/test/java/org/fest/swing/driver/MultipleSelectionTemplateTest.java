/*
 * Created on May 8, 2008
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

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.util.Platform.controlOrCommandKey;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.Robot;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link MultipleSelectionTemplate}</code>.
 *
 * @author Yvonne Wang
 */
public class MultipleSelectionTemplateTest {

  private Robot robot;
  private MultipleSelection selection;

  @BeforeMethod public void setUp() {
    robot = createMock(Robot.class);
    selection = new MultipleSelection(robot);
  }

  @Test public void shouldSelectMultipleItems() {
    final int key = controlOrCommandKey();
    new EasyMockTemplate(robot) {
      protected void expectations() {
        robot.pressKey(key);
        expectLastCall().once();
        robot.releaseKey(key);
        expectLastCall().once();
      }

      protected void codeToTest() {
        selection.multiSelect();
        assertThat(selection.selected).isTrue();
      }
    }.run();
  }

  private static class MultipleSelection extends MultipleSelectionTemplate {
    boolean selected;

    MultipleSelection(Robot robot) {
      super(robot);
    }

    void select() {
      selected = true;
    }
  }
}
