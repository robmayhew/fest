/*
 * Created on Aug 21, 2008
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

import javax.swing.JScrollBar;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.EDT_ACTION;

/**
 * Tests for <code>{@link JScrollBarMinAndMaxQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = EDT_ACTION)
public class JScrollBarMinAndMaxQueryTest {

  private JScrollBar scrollBar;
  private int minimum;
  private int maximum;
  private JScrollBarMinAndMaxQuery query;

  @BeforeMethod public void setUp() {
    scrollBar = createMock(JScrollBar.class);
    minimum = 6;
    maximum = 8;
    query = new JScrollBarMinAndMaxQuery(scrollBar);
  }
  
  public void shouldReturnMinimumAndMaximumValuesOfJScrollBar() {
    new EasyMockTemplate(scrollBar) {
      protected void expectations() {
        expect(scrollBar.getMinimum()).andReturn(minimum);
        expect(scrollBar.getMaximum()).andReturn(maximum);
      }

      protected void codeToTest() {
        MinimumAndMaximum minAndMax = query.executeInEDT();
        assertThat(minAndMax.minimum).isEqualTo(minimum);
        assertThat(minAndMax.maximum).isEqualTo(maximum);
      }
    }.run();
  }
}
