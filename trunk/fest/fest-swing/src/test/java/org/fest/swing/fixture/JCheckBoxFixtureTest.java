/*
 * Created on Jun 10, 2007
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

import javax.swing.JCheckBox;

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.driver.AbstractButtonDriver;
import org.fest.swing.driver.ComponentDriver;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link JCheckBoxFixture}</code>.
 *
 * @author Alex Ruiz
 */
public class JCheckBoxFixtureTest extends JPopupMenuInvokerFixtureTestCase<JCheckBox> {

  private AbstractButtonDriver driver;
  private JCheckBox target;
  private JCheckBoxFixture fixture;
  
  void onSetUp() {
    driver = createMock(AbstractButtonDriver.class);
    target = new JCheckBox("A CheckBox");
    fixture = new JCheckBoxFixture(robot(), target);
    fixture.updateDriver(driver);
  }

  @Test public void shouldCheck() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.select(target);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.check());
      }
    }.run();
  }
  
  @Test public void shoulUncheck() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.unselect(target);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.uncheck());
      }
    }.run();
  }

  @Test public void shouldReturnText() {
    assertThat(fixture.text()).isEqualTo(target.getText());
  }
  
  @Test public void shouldRequireText() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireText(target, "A CheckBox");
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireText("A CheckBox"));
      }
    }.run();
  }
  
  @Test public void shouldRequireNotSelected() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireNotSelected(target);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireNotSelected());
      }
    }.run();
  }
  
  @Test public void shouldRequireSelected() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireSelected(target);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireSelected());
      }
    }.run();
  }

  ComponentDriver driver() { return driver; }
  JCheckBox target() { return target; }
  JPopupMenuInvokerFixture<JCheckBox> fixture() { return fixture; }
}
