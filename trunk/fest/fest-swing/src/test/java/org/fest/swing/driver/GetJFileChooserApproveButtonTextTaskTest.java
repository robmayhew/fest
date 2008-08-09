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

import javax.swing.JFileChooser;
import javax.swing.plaf.FileChooserUI;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link GetJFileChooserApproveButtonTextTask}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class GetJFileChooserApproveButtonTextTaskTest {

  private JFileChooser fileChooser;
  private FileChooserUI ui;
  private String text;

  @BeforeMethod public void setUp() {
    fileChooser = createMock(JFileChooser.class);
    ui = createMock(FileChooserUI.class);
    text = "Open";
  }
  
  public void shouldReturnApproveButtonTextFromJFileChooser() {
    new EasyMockTemplate(fileChooser) {
      protected void expectations() {
        expect(fileChooser.getApproveButtonText()).andReturn(text);
      }

      protected void codeToTest() {
        assertThat(GetJFileChooserApproveButtonTextTask.approveButtonTextFrom(fileChooser)).isSameAs(text);
      }
    }.run();
  }
  
  public void shouldReturnApproveButtonTextFromUIIfJFileChooserDoesNotHaveIt() {
    new EasyMockTemplate(fileChooser, ui) {
      protected void expectations() {
        expect(fileChooser.getApproveButtonText()).andReturn(null);
        expect(fileChooser.getUI()).andReturn(ui);
        expect(ui.getApproveButtonText(fileChooser)).andReturn(text);
      }

      protected void codeToTest() {
        assertThat(GetJFileChooserApproveButtonTextTask.approveButtonTextFrom(fileChooser)).isSameAs(text);
      }
    }.run();
  }
}
