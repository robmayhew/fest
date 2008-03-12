/*
 * Created on Feb 9, 2007
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

import javax.swing.JLabel;

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.JLabelDriver;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link JLabelFixture}</code>.
 *
 * @author Yvonne Wang
 */
public class JLabelFixtureTest extends JPopupMenuInvokerFixtureTestCase<JLabel> {

  private JLabelDriver driver;
  private JLabel target;
  private JLabelFixture fixture;
  
  void onSetUp() {
    driver = createMock(JLabelDriver.class);
    target = new JLabel("A Label");
    fixture = new JLabelFixture(robot(), target);
    fixture.updateDriver(driver);
  }

  @Test public void shouldReturnText() {
    assertThat(fixture.text()).isEqualTo(target.getText());
  }
  
  @Test public void shouldRequireText() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireText(target, "A Label");
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireText("A Label"));
      }
    }.run();
  }

  ComponentDriver driver() { return driver; }
  JLabel target() { return target; }
  JPopupMenuInvokerFixture<JLabel> fixture() { return fixture; }
}
