/*
 * Created on Jul 5, 2007
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

import javax.swing.JToolBar;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.JToolBarDriver;
import org.fest.swing.fixture.JToolBarFixture.UnfloatConstraint;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.swing.fixture.JToolBarFixture.UnfloatConstraint.*;

/**
 * Tests for <code>{@link JToolBarFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JToolBarFixtureTest extends JPopupMenuInvokerFixtureTestCase<JToolBar> {

  private JToolBarDriver driver;
  private JToolBar target;
  private JToolBarFixture fixture;
  
  void onSetUp() {
    driver = createMock(JToolBarDriver.class);
    target = new JToolBar();
    fixture = new JToolBarFixture(robot(), target);
    fixture.updateDriver(driver);
  }

  @Test public void shouldCreateFixtureWithGivenComponentName() {
    new FixtureCreationByNameTemplate() {
      ComponentFixture<JToolBar> fixtureWithName(String name) {
        return new JToolBarFixture(robot(), name);
      }
    }.run();
  }

  @Test public void shouldFloatToPoint() {
    final Point p = new Point(8, 6);
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.floatTo(target, p.x, p.y);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.floatTo(p));
      }
    }.run();
  }
  
  @Test public void shouldUnfloat() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.unfloat(target);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.unfloat());
      }
    }.run();
  }

  @Test(dataProvider = "unfloatConstraints")
  public void shouldUnfloatUsingGivingConstraint(final UnfloatConstraint constraint) {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.unfloat(target, constraint.value);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.unfloat(constraint));
      }
    }.run();
  }
  
  @DataProvider(name = "unfloatConstraints") public Object[][] unfloatConstraints() {
    return new Object[][] { { NORTH }, { EAST }, { SOUTH }, { WEST } };
  }

  ComponentDriver driver() { return driver; }
  JToolBar target() { return target; }
  JPopupMenuInvokerFixture<JToolBar> fixture() { return fixture; }
}
