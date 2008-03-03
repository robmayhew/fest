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

import javax.swing.JPopupMenu;

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.FrameDriver;

import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;


/**
 * Tests for <code>{@link FrameFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class FrameFixtureTest extends ComponentFixtureTestCase<Frame> {

  private FrameDriver driver;
  private Frame target;
  private FrameFixture fixture;
  
  void onSetUp(Robot robot) {
    driver = createMock(FrameDriver.class);
    target = new Frame("A Label");
    fixture = new FrameFixture(robot, target);
    fixture.updateDriver(driver);
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
  Frame target() { return target; }
  ComponentFixture<Frame> fixture() { return fixture; }
}
