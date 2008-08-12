/*
 * Created on Aug 12, 2008
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

import javax.swing.JSpinner;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;

/**
 * Tests for <code>{@link JSpinnerDecrementValueTask}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class JSpinnerDecrementValueTaskTest {

  private JSpinner spinner;
  private Object value;
  
  @BeforeMethod public void setUp() {
    spinner = createMock(JSpinner.class);
    value = "Hello";
  }
  
  public void shouldDecrementValue() {
    new EasyMockTemplate(spinner) {
      protected void expectations() {
        expect(spinner.getPreviousValue()).andReturn(value);
        spinner.setValue(value);
        expectLastCall().once();
      }

      protected void codeToTest() {
        JSpinnerDecrementValueTask.decrementValueOf(spinner).executeInEDT();
      }
    }.run();
  }
  
  public void shouldNotDecrementValueIfPreviousValueIsNull() {
    new EasyMockTemplate(spinner) {
      protected void expectations() {
        expect(spinner.getPreviousValue()).andReturn(null);
      }

      protected void codeToTest() {
        JSpinnerDecrementValueTask.decrementValueOf(spinner).executeInEDT();
      }
    }.run();
  }
}
