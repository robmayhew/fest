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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.JMenuItemDriver;

import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

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

  @Test public void shouldCreateFixtureWithGivenComponentName() {
    new EasyMockTemplate(robot(), finder()) {
      protected void expectations() {
        expect(robot().finder()).andReturn(finder());
        expect(finder().findByName("c", JMenuItem.class, false)).andReturn(target);
      }
      
      protected void codeToTest() {
        fixture = new JMenuItemFixture(robot(), "c");
        assertThat(fixture.component()).isSameAs(target);
      }
    }.run();
  }

  @Test public void shouldCreateFixtureWithGivenAction() {
    Action action = new AbstractAction("action") {
      private static final long serialVersionUID = 1L;
      public void actionPerformed(ActionEvent e) {}
    };
    fixture = new JMenuItemFixture(robot(), action);
    assertThat(fixture.component().getAction()).isSameAs(action);
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
