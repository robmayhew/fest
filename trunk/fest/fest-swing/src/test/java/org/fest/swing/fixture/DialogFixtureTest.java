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

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.Condition;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.DialogDriver;
import org.fest.swing.edt.GuiTask;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.factory.JDialogs.dialog;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link DialogFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test public class DialogFixtureTest extends CommonComponentFixtureTestCase<Dialog> {

  private DialogDriver driver;
  private Dialog target;
  private DialogFixture fixture;
  
  void onSetUp() {
    driver = createMock(DialogDriver.class);
    target = dialog().withName("dialog")
                     .createNew();
    fixture = new DialogFixture(robot(), target);
    fixture.updateDriver(driver);
  }

  public void shouldCreateFixtureWithGivenComponentName() {
    String name = "dialog";
    expectLookupByName(name, Dialog.class);
    verifyLookup(new DialogFixture(robot(), name));
  }
  
  @Test(groups = GUI) 
  public void shouldCreateFixtureWithNewRobotAndGivenTarget() {
    fixture = new DialogFixture(target);
    try {
      assertThat(fixture.robot).isInstanceOf(RobotFixture.class);
    } finally {
      fixture.cleanUp();
    }
  }

  @Test(groups = GUI) 
  public void shouldCreateFixtureWithNewRobotAndGivenTargetName() {
    packAndSetVisible(target);
    fixture = new DialogFixture("dialog");
    try {
      assertThat(fixture.robot).isInstanceOf(RobotFixture.class);
      assertThat(fixture.component()).isSameAs(target);
    } finally {
      fixture.cleanUp();
    }
  }

  private static void packAndSetVisible(final Dialog dialog) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        dialog.pack();
        dialog.setVisible(true);
      }
    }, new Condition("Dialog is showing") {
      public boolean test() {
        return dialog.isShowing();
      }
    });
  }

  public void shouldRequireModal() {
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
  
  public void shouldRequireSize() {
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

  public void shouldMoveToPoint() {
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
  
  public void shouldResizeHeight() {
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
  
  public void shouldResizeWidth() {
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

  public void shouldResizeWidthAndHeight() {
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

  public void shouldShow() {
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

  public void shouldShowWithGivenSize() {
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

  public void shouldClose() {
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
  DialogFixture fixture() { return fixture; }
}