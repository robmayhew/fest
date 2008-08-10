/*
 * Created on Aug 9, 2008
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

import javax.swing.JInternalFrame;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.testing.BooleanProvider;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link JInternalFrameIconQuery}</code>.
 *
 * @author Alex Ruiz
 */
public class JInternalFrameIconQueryTest {

  private JInternalFrame internalFrame;

  @BeforeMethod public void setUp() {
    internalFrame = createMock(JInternalFrame.class);
  }
  
  @Test(dataProvider = "booleans", dataProviderClass = BooleanProvider.class)
  public void shouldIndicateIfJInternalFrameIsIconified(final boolean iconified) {
    new EasyMockTemplate(internalFrame) {
      protected void expectations() {
        expect(internalFrame.isIcon()).andReturn(iconified);
      }

      protected void codeToTest() {
        assertThat(JInternalFrameIconQuery.isIconified(internalFrame)).isEqualTo(iconified);
      }
    }.run();
  }

}
