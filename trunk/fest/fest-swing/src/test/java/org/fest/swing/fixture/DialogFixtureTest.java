/*
 * Created on Feb 10, 2007
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

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.DialogDriver;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link DialogFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class DialogFixtureTest extends JPopupMenuInvokerFixtureTestCase<Dialog> {

  private DialogDriver driver;
  private Dialog target;
  private DialogFixture fixture;
  
  void onSetUp() {
    driver = createMock(DialogDriver.class);
    target = new Dialog(new JFrame());
    fixture = new DialogFixture(robot(), target);
    fixture.updateDriver(driver);
  }

  @Test public void shouldCreateFixtureWithGivenComponentName() {
    new FixtureCreationByNameTemplate() {
      ComponentFixture<Dialog> fixtureWithName(String name) {
        return new DialogFixture(robot(), name);
      }
    }.run();
  }
  
  @Test(groups = GUI) public void shouldCreateFixtureWithNewRobotAndGivenTarget() {
    fixture = new DialogFixture(target);
    assertThat(fixture.robot).isInstanceOf(RobotFixture.class);
    fixture.cleanUp();
  }

  @Test(groups = GUI) public void shouldCreateFixtureWithNewRobotAndGivenTargetName() {
    target.setName("dialog");
    target.pack();
    target.setVisible(true);
    fixture = new DialogFixture("dialog");
    assertThat(fixture.robot).isInstanceOf(RobotFixture.class);
    assertThat(fixture.component()).isSameAs(target);
    fixture.cleanUp();
  }

  @Test public void shouldRequireModal() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireModal(target);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireModal());
      }
    }.run();
  }
  
  @Test public void shouldRequireSize() {
    final Dimension size = new Dimension(800, 600);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireSize(target, size);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireSize(size));
      }
    }.run();
  }

  @Test public void shouldMoveToPoint() {
    final Point p = new Point(6, 8);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.moveTo(target, p);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.moveTo(p));
      }
    }.run();
  }
  
  @Test public void shouldResizeHeight() {
    final int height = 68;
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.resizeHeightTo(target, height);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.resizeHeightTo(height));
      }
    }.run();
  }
  
  @Test public void shouldResizeWidth() {
    final int width = 68;
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.resizeWidthTo(target, width);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.resizeWidthTo(width));
      }
    }.run();
  }

  @Test public void shouldResizeWidthAndHeight() {
    final Dimension size = new Dimension(800, 600);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.resizeTo(target, size);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.resizeTo(size));
      }
    }.run();
  }

  @Test public void shouldShow() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.show(target);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.show());
      }
    }.run();
  }

  @Test public void shouldShowWithGivenSize() {
    final Dimension size = new Dimension(800, 600);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.show(target, size);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.show(size));
      }
    }.run();
  }

  @Test public void shouldClose() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.close(target);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        fixture.close();
      }
    }.run();
  }

  ComponentDriver driver() { return driver; }
  Dialog target() { return target; }
  JPopupMenuInvokerFixture<Dialog> fixture() { return fixture; }
}