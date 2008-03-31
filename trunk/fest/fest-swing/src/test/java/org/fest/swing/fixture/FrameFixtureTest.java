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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.FrameDriver;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link FrameFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class FrameFixtureTest extends JPopupMenuInvokerFixtureTestCase<Frame> {

  private FrameDriver driver;
  private Frame target;
  private FrameFixture fixture;
  
  void onSetUp() {
    driver = createMock(FrameDriver.class);
    target = new Frame("A Label");
    fixture = new FrameFixture(robot(), target);
    fixture.updateDriver(driver);
  }

  @Test public void shouldCreateFixtureWithGivenComponentName() {
    new FixtureCreationByNameTemplate() {
      ComponentFixture<Frame> fixtureWithName(String name) {
        return new FrameFixture(robot(), name);
      }
    }.run();
  }
  
  @Test(groups = GUI) public void shouldCreateFixtureWithNewRobotAndGivenTarget() {
    fixture = new FrameFixture(target);
    assertThat(fixture.robot).isInstanceOf(RobotFixture.class);
    fixture.cleanUp();
  }

  @Test(groups = GUI) public void shouldCreateFixtureWithNewRobotAndGivenTargetName() {
    target.setName("frame");
    target.pack();
    target.setVisible(true);
    fixture = new FrameFixture("frame");
    assertThat(fixture.robot).isInstanceOf(RobotFixture.class);
    assertThat(fixture.component()).isSameAs(target);
    fixture.cleanUp();
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

  @Test public void shouldIconify() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.iconify(target);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.iconify());
      }
    }.run();
  }
  
  @Test public void shouldDeiconify() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.deiconify(target);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.deiconify());
      }
    }.run();
  }

  @Test public void shouldMaximize() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.maximize(target);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.maximize());
      }
    }.run();
  }

  @Test public void shouldNormalize() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.normalize(target);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.normalize());
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
  Frame target() { return target; }
  JPopupMenuInvokerFixture<Frame> fixture() { return fixture; }
}
