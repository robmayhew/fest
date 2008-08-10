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

import java.awt.Component;

import javax.swing.ComboBoxEditor;
import javax.swing.JComboBox;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link JComboBoxEditorQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class JComboBoxEditorQueryTest {

  private JComboBox comboBox;
  private ComboBoxEditor editor;
  private Component editorComponent;
  
  @BeforeMethod public void setUp() {
    comboBox = createMock(JComboBox.class);
    editor = createMock(ComboBoxEditor.class);
    editorComponent = createMock(Component.class);
  }
  
  public void shouldReturnEditorComponentFromJComboBox() {
    new EasyMockTemplate(comboBox, editor) {
      protected void expectations() {
        expect(comboBox.getEditor()).andReturn(editor);
        expect(editor.getEditorComponent()).andReturn(editorComponent);
      }

      protected void codeToTest() {
        assertThat(JComboBoxEditorQuery.editorOf(comboBox)).isSameAs(editorComponent);
      }
    }.run();
  }
}
