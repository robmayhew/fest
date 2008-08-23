/*
 * Created on Aug 11, 2008
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

import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.exception.UnexpectedException.unexpected;
import static org.fest.swing.testing.TestGroups.EDT_QUERY;

/**
 * Tests for <code>{@link JTextComponentModelToViewQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = EDT_QUERY)
public class JTextComponentModelToViewQueryTest {

  private JTextComponent textBox;
  private int position;
  private Rectangle rectangle;
  private JTextComponentModelToViewQuery query;

  @BeforeMethod public void setUp() {
    textBox = createMock(JTextComponent.class);
    position = 8;
    rectangle = new Rectangle(80, 60);
    query = new JTextComponentModelToViewQuery(textBox, position);
  }
  
  public void shouldReturnModelToViewInJTextComponent() {
    new EasyMockTemplate(textBox) {
      protected void expectations() {
        try {
          expect(textBox.modelToView(position)).andReturn(rectangle);
        } catch (BadLocationException e) {
          throw unexpected(e);
        }
      }

      protected void codeToTest() {
        try {
          assertThat(query.executeInEDT()).isSameAs(rectangle);
        } catch (BadLocationException e) {
          throw unexpected(e);
        }
      }
    }.run();
  }
}
