/*
 * Created on Sep 1, 2008
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
package org.fest.swing.query;

import java.awt.Frame;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.EDT_ACTION;

/**
 * Tests for <code>{@link FrameTitleQuery}</code>.
 *
 * @author Yvonne Wang
 */
@Test(groups = EDT_ACTION)
public class FrameTitleQueryTest {

  private Frame frame;
  private String title;
  private FrameTitleQuery query;

  @BeforeMethod public void setUp() {
    frame = createMock(Frame.class);
    title = "hello";
    query = new FrameTitleQuery(frame);
  }

  public void shouldReturnTitleOfFrame() {
    new EasyMockTemplate(frame) {
      protected void expectations() {
        expect(frame.getTitle()).andReturn(title);
      }

      protected void codeToTest() {
        assertThat(query.executeInEDT()).isSameAs(title);
      }
    }.run();
  }
}
