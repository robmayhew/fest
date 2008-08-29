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
import org.fest.swing.core.KeyPressInfo;
import org.fest.swing.core.Timeout;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.JMenuItemDriver;

import static java.awt.event.InputEvent.SHIFT_MASK;
import static java.awt.event.KeyEvent.*;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.KeyPressInfo.keyCode;
import static org.fest.swing.core.Timeout.timeout;
import static org.fest.swing.factory.JMenuItems.menuItem;

/**
 * Tests for <code>{@link JMenuItemFixture}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class JMenuItemFixtureTest extends ComponentFixtureTestCase<JMenuItem> implements
    KeyboardInputSimulationFixtureTestCase, StateVerificationFixtureTestCase {

  private JMenuItemDriver driver;
  private JMenuItem target;
  private JMenuItemFixture fixture;
  
  void onSetUp() {
    driver = createMock(JMenuItemDriver.class);
    target = menuItem().withText("A MenuItem").createInEDT();
    fixture = new JMenuItemFixture(robot(), target);
    fixture.updateDriver(driver);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfGivenActionIsNull() {
    Action action = null;
    new JMenuItemFixture(robot(), action);
  }
  
  @Test public void shouldCreateFixtureWithGivenComponentName() {
    String name = "menuItem";
    expectLookupByName(name, JMenuItem.class, false);
    verifyLookup(new JMenuItemFixture(robot(), name));
  }

  @Test public void shouldCreateFixtureWithGivenAction() {
    Action action = new AbstractAction("action") {
      private static final long serialVersionUID = 1L;
      public void actionPerformed(ActionEvent e) {}
    };
    fixture = new JMenuItemFixture(robot(), action);
    assertThat(fixture.component().getAction()).isSameAs(action);
  }
  
  @Test public void shouldClickMenuItem() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.click(target);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.click());
      }
    }.run();
  }

  @Test public void shouldPressAndReleaseKey() {
    final KeyPressInfo keyPressInfo = keyCode(VK_A).modifiers(SHIFT_MASK);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.pressAndReleaseKey(target, keyPressInfo);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.pressAndReleaseKey(keyPressInfo));
      }
    }.run();
  }

  @Test public void shouldPressAndReleaseKeys() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.pressAndReleaseKeys(target, VK_A, VK_B, VK_C);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.pressAndReleaseKeys(VK_A, VK_B, VK_C));
      }
    }.run();
  }

  @Test public void shouldPressKey() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.pressKey(target, VK_A);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.pressKey(VK_A));
      }
    }.run();
  }

  @Test public void shouldReleaseKey() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.releaseKey(target, VK_A);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.releaseKey(VK_A));
      }
    }.run();
  }

  @Test public void shouldRequireDisabled() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireDisabled(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireDisabled());
      }
    }.run();
  }
  
  @Test public void shouldRequireEnabled() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireEnabled(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireEnabled());
      }
    }.run();
  }

  @Test public void shouldRequireEnabledUsingTimeout() {
    final Timeout timeout = timeout(2000);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireEnabled(target, timeout);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireEnabled(timeout));
      }
    }.run();
  }

  @Test public void shouldRequireNotVisible() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireNotVisible(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireNotVisible());
      }
    }.run();
  }

  @Test public void shouldRequireVisible() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireVisible(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireVisible());
      }
    }.run();
  }

  private void assertThatReturnsThis(JMenuItemFixture result) {
    assertThat(result).isSameAs(fixture);
  }

  ComponentDriver driver() { return driver; }

  JMenuItem target() { return target; }
}
