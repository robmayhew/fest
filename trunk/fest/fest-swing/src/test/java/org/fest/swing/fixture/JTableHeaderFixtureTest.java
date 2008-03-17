/*
 * Created on Mar 16, 2008
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
package org.fest.swing.fixture;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;

import javax.swing.table.JTableHeader;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.JTableHeaderDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTableHeaderFixture}</code>.
 *
 * @author Yvonne Wang
 */
public class JTableHeaderFixtureTest {

  private JTableHeaderDriver driver;
  private JTableHeader tableHeader;
  private JTableHeaderFixture fixture;

  @BeforeMethod public void setUp() {
    Robot robot = createMock(Robot.class);
    tableHeader = new JTableHeader();
    fixture = new JTableHeaderFixture(robot, tableHeader);
    driver = createMock(JTableHeaderDriver.class);
    fixture.updateDriver(driver);
  }

  @Test public void shouldClickColumnUnderIndex() {
    final int index = 0;
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.clickColumn(tableHeader, index);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.clickColumn(index));
      }

    }.run();
  }

  @Test public void shouldClickColumnUnderIndexUsingGivingMouseButtonAndTimes() {
    final int index = 0;
    final MouseButton mouseButton = LEFT_BUTTON;
    final int times = 2;
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.clickColumn(tableHeader, index, mouseButton, times);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.clickColumn(index, mouseButton, times));
      }

    }.run();
  }

  @Test public void shouldClickColumnWithMatchingName() {
    final String name = "first";
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.clickColumn(tableHeader, name);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.clickColumn(name));
      }

    }.run();
  }

  @Test public void shouldClickColumnWithMatchingNameUsingGivingMouseButtonAndTimes() {
    final String name = "first";
    final MouseButton mouseButton = LEFT_BUTTON;
    final int times = 2;
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.clickColumn(tableHeader, name, mouseButton, times);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.clickColumn(name, mouseButton, times));
      }

    }.run();
  }

  private void assertThatReturnsThis(JTableHeaderFixture result) {
    assertThat(result).isSameAs(fixture);
  }
}
