/*
 * Created on Feb 23, 2008
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
package org.fest.swing.task;

import static java.awt.Frame.NORMAL;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

import java.awt.Frame;
import java.lang.reflect.Method;

import org.fest.mocks.EasyMockTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link SetExtendedStateTask}</code>.
 *
 * @author Alex Ruiz
 */
public class SetExtendedStateTaskTest {

  private Frame f;
  private int state;
  private SetExtendedStateTask task;

  @BeforeMethod public void setUp() throws Exception {
    Method setExtendedState = Frame.class.getDeclaredMethod("setExtendedState", int.class);
    f = createMock(Frame.class, new Method[] { setExtendedState });
    state = NORMAL;
    task = new SetExtendedStateTask(f, state);
  }

  @Test public void shouldSetExtendedState() {
    new EasyMockTemplate(f) {
      @Override protected void expectations() {
        f.setExtendedState(state);
        expectLastCall();
      }

      @Override protected void codeToTest() {
        task.run();
      }
    }.run();
  }
}
