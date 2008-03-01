/*
 * Created on Dec 25, 2007
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

import java.awt.Point;

import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.JScrollBarDriver;

import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link JScrollBarFixture}</code>.
 *
 * @author Alex Ruiz
 */
public class JScrollBarFixtureTest extends ComponentFixtureTestCase<JScrollBar> {

  private JScrollBarDriver driver;
  private JScrollBar target;
  private JScrollBarFixture fixture;
  
  void onSetUp(Robot robot) {
    driver = createMock(JScrollBarDriver.class);
    target = new JScrollBar();
    fixture = new JScrollBarFixture(robot, target);
    fixture.updateDriver(driver);
  }

  @Test public void shouldRequireValue() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireValue(target, 8);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireValue(8));
      }
    }.run();
  }

  @Test public void shouldScrollBlockDown() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.scrollBlockDown(target);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.scrollBlockDown());
      }
    }.run();
  }
  
  @Test public void shouldScrollBlockDownTheGivenTimes() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.scrollBlockDown(target, 6);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.scrollBlockDown(6));
      }
    }.run();
  }

  @Test public void shouldScrollBlockUp() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.scrollBlockUp(target);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.scrollBlockUp());
      }
    }.run();
  }
  
  @Test public void shouldScrollBlockUpTheGivenTimes() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.scrollBlockUp(target, 6);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.scrollBlockUp(6));
      }
    }.run();
  }

  @Test public void shouldScrollToPosition() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.scrollTo(target, 8);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.scrollTo(8));
      }
    }.run();
  }

  @Test public void shouldScrollUnitDown() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.scrollUnitDown(target);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.scrollUnitDown());
      }
    }.run();
  }
  
  @Test public void shouldScrollUnitDownTheGivenTimes() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.scrollUnitDown(target, 6);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.scrollUnitDown(6));
      }
    }.run();
  }

  @Test public void shouldScrollUnitUp() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.scrollUnitUp(target);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.scrollUnitUp());
      }
    }.run();
  }
  
  @Test public void shouldScrollUnitUpTheGivenTimes() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.scrollUnitUp(target, 6);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.scrollUnitUp(6));
      }
    }.run();
  }

  @Test public void shouldShowJPopupMenu() {
    final JPopupMenu popup = new JPopupMenu(); 
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.showPopupMenu(target)).andReturn(popup);
      }
      
      protected void codeToTest() {
        JPopupMenuFixture result = fixture.showPopupMenu();
        assertThat(result.target).isSameAs(popup);
      }
    }.run();
  }
  
  @Test public void shouldShowJPopupMenuAtPoint() {
    final Point p = new Point(8, 6);
    final JPopupMenu popup = new JPopupMenu(); 
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.showPopupMenu(target, p)).andReturn(popup);
      }
      
      protected void codeToTest() {
        JPopupMenuFixture result = fixture.showPopupMenuAt(p);
        assertThat(result.target).isSameAs(popup);
      }
    }.run();
  }

  ComponentDriver driver() { return driver; }
  JScrollBar target() { return target; }
  ComponentFixture<JScrollBar> fixture() { return fixture; }
}
