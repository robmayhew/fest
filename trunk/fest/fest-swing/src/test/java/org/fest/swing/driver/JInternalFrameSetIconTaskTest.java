/*
 * Created on Aug 8, 2008
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

import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.swing.driver.JInternalFrameAction.DEICONIFY;

/**
 * Tests for <code>{@link JInternalFrameSetIconTask}</code>.
 *
 * @author Yvonne Wang
 */
@Test public class JInternalFrameSetIconTaskTest {

  private JInternalFrame internalFrame;
  private JInternalFrameAction action;

  @BeforeMethod public void setUp() {
    internalFrame = createMock(JInternalFrame.class);
    action = DEICONIFY;
  }

  public void shouldCloseJInternalFrame() {
    new EasyMockTemplate(internalFrame) {
      protected void expectations() {
        try {
          internalFrame.setIcon(action.value);
        } catch (PropertyVetoException e) {}
        expectLastCall().once();
      }

      protected void codeToTest() {
        try {
          JInternalFrameSetIconTask.setIcon(internalFrame, action).execute();
        } catch (PropertyVetoException e) {}
      }
    }.run();
  }

}
