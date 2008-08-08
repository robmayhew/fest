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

import java.awt.Dialog;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link IsDialogModalTask}</code>.
 *
 * @author Alex Ruiz
 */
public class IsDialogModalTaskTest {

  private Dialog dialog;
  
  @BeforeMethod public void setUp() {
    dialog = createMock(Dialog.class);
  }
  
  @Test(dataProvider = "modalValues")
  public void shouldIndicateWhetherDialogIsModal(final boolean modal) {
    new EasyMockTemplate(dialog) {
      protected void expectations() {
        expect(dialog.isModal()).andReturn(modal);
      }

      protected void codeToTest() {
        assertThat(IsDialogModalTask.isModal(dialog)).isEqualTo(modal);
      }
    }.run();
  }
  
  @DataProvider(name = "modalValues") public Object[][] modalValues() {
    return new Object[][] { { true }, { false } };
  }
  
}
