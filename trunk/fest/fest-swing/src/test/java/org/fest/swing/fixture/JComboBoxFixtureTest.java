/*
 * Created on Apr 9, 2007
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

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPopupMenu;

import org.easymock.classextension.EasyMock;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.JComboBoxDriver;

import static org.easymock.EasyMock.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JComboBoxFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JComboBoxFixtureTest extends ComponentFixtureTestCase<JComboBox> {

  private JComboBoxDriver driver;
  private JComboBox target;
  private JComboBoxFixture fixture;

  void onSetUp(Robot robot) {
    driver = EasyMock.createMock(JComboBoxDriver.class);
    target = new JComboBox();
    fixture = new JComboBoxFixture(robot, target);
    fixture.updateDriver(driver);
  }
  
  @Test public void shouldReturnContents() {
    final String[] contents = array("Frodo", "Sam");
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.contentsOf(target)).andReturn(contents);
      }

      protected void codeToTest() {
        String[] result = fixture.contents();
        assertThat(result).isSameAs(contents);
      }
    }.run();
  }
  
  @Test public void shouldEnterText() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.enterText(target, "Hello");
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.enterText("Hello"));
      }
    }.run();
  }

  @Test public void shouldReturnList() {
    final JList list = new JList();
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.dropDownList()).andReturn(list);
      }

      protected void codeToTest() {
        JList result = fixture.list();
        assertThat(result).isSameAs(list);
      }
    }.run();
  }

  @Test public void shouldSelectItemUnderIndex() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectItem(target, 6);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectItem(6));
      }
    }.run();
  }

  @Test public void shouldSelectItemWithText() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectItem(target, "Frodo");
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectItem("Frodo"));
      }
    }.run();
  }
  
  @Test public void shouldReturnValueAtIndex() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.text(target, 8)).andReturn("Sam");
      }

      protected void codeToTest() {
        String result = fixture.valueAt(8);
        assertThat(result).isEqualTo("Sam");
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
  JComboBox target() { return target; }
  ComponentFixture<JComboBox> fixture() { return fixture; }
}
