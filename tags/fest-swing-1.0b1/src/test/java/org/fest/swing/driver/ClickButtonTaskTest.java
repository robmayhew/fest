/*
 * Created on Jun 22, 2008
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

import javax.swing.AbstractButton;

import org.fest.mocks.EasyMockTemplate;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ClickButtonTask}</code>.
 *
 * @author Yvonne Wang
 */
public class ClickButtonTaskTest {

  private AbstractButton button;
  private ClickButtonTask task;

  @BeforeClass public void setUp() {
    button = createMock(AbstractButton.class);
    task = new ClickButtonTask(button);
  }

  @Test public void shouldClickButton() {
    new EasyMockTemplate(button) {
      protected void expectations() {
        button.doClick();
        expectLastCall().once();
      }

      protected void codeToTest() {
        task.run();
      }
    }.run();
  }
}
