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
package org.fest.swing.driver;

import java.awt.Component;
import java.awt.Dimension;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

/**
 * Test for <code>{@link ComponentSetSizeTask}</code>.
 *
 * @author Alex Ruiz
 */
public class ComponentSetSizeTaskTest {

  private Component c;
  private Dimension size;

  @BeforeMethod public void setUp() throws Exception {
    c = createMock(Component.class);
    size = new Dimension(80, 60);
  }

  @Test public void shouldSetSize() {
    new EasyMockTemplate(c) {
      protected void expectations() {
        c.setSize(size);
        expectLastCall();
      }

      protected void codeToTest() {
        ComponentSetSizeTask.setSize(c, size).executeInEDT();
      }
    }.run();
  }
}
