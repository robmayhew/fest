/*
 * Created on Mar 4, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.fixture;

import javax.swing.JMenuItem;

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.JMenuItemDriver;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

/**
 * Tests for <code>{@link JMenuItemFixture}</code>.
 *
 * @author Alex Ruiz
 */
public class JMenuItemFixtureTest extends ComponentFixtureTestCase<JMenuItem> {

  private JMenuItemDriver driver;
  private JMenuItem target;
  private JMenuItemFixture fixture;
  
  void onSetUp() {
    driver = createMock(JMenuItemDriver.class);
    target = new JMenuItem("A Button");
    fixture = new JMenuItemFixture(robot(), target);
    fixture.updateDriver(driver);
  }

  @Test public void shouldSelectMenuItem() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectMenuItem(target);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.select());
      }
    }.run();
  }

  ComponentDriver driver() { return driver; }
  JMenuItem target() { return target; }
  ComponentFixture<JMenuItem> fixture() { return fixture; }
}
