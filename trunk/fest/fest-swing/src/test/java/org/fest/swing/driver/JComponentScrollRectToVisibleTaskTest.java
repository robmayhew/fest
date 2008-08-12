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

import java.awt.Rectangle;
import java.lang.reflect.Method;

import javax.swing.JComponent;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

/**
 * Tests for <code>{@link JComponentScrollRectToVisibleTask}</code>.
 *
 * @author Alex Ruiz
 */
public class JComponentScrollRectToVisibleTaskTest {

  private JComponent c;
  private Rectangle rectangle;
  private JComponentScrollRectToVisibleTask task;

  @BeforeMethod public void setUp() throws Exception {
    Method scrollRectToVisible = JComponent.class.getDeclaredMethod("scrollRectToVisible", Rectangle.class);
    c = createMock(JComponent.class, new Method[] { scrollRectToVisible });
    rectangle = new Rectangle(80, 60);
    task = JComponentScrollRectToVisibleTask.scrollRectToVisible(c, rectangle);
  }

  @Test public void shouldScrollRectToVisible() {
    new EasyMockTemplate(c) {
      protected void expectations() {
        c.scrollRectToVisible(rectangle);
        expectLastCall();
      }

      protected void codeToTest() {
        task.executeInEDT();
      }
    }.run();
  }

}
