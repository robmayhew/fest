/*
 * Created on Mar 11, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.Point;

import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.easymock.EasyMock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.Robot;

import static org.easymock.EasyMock.expect;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.factory.JPopupMenus.popupMenu;
import static org.fest.swing.factory.JTextFields.textField;

/**
 * Tests for <code>{@link JPopupMenuInvokerFixture}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JPopupMenuInvokerFixtureTest {

  private Robot robot;
  private JPopupMenuInvokerFixture<JTextComponent> fixture;
  private JTextField target;
  
  @BeforeMethod public void setUp() {
    robot = EasyMock.createMock(Robot.class);
    target = textField().createNew();
    fixture = new JPopupMenuInvokerFixture<JTextComponent>(robot, target) {};
  }
  
  @Test public void shouldShowJPopupMenu() {
    final JPopupMenu popup = popupMenu().createNew(); 
    new EasyMockTemplate(robot) {
      protected void expectations() {
        expect(robot.showPopupMenu(target)).andReturn(popup);
      }
      
      protected void codeToTest() {
        JPopupMenuFixture result = fixture.showPopupMenu();
        assertThat(result.target).isSameAs(popup);
      }
    }.run();
  }
  
  @Test public void shouldShowJPopupMenuAtPoint() {
    final Point p = new Point(8, 6);
    final JPopupMenu popup = popupMenu().createNew(); 
    new EasyMockTemplate(robot) {
      protected void expectations() {
        expect(robot.showPopupMenu(target, p)).andReturn(popup);
      }
      
      protected void codeToTest() {
        JPopupMenuFixture result = fixture.showPopupMenuAt(p);
        assertThat(result.target).isSameAs(popup);
      }
    }.run();
  }
}
