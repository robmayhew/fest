/*
 * Created on Sep 5, 2007
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

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.JPopupMenuDriver;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JPopupMenuFixture}</code>.
 *
 * @author Yvonne Wang
 */
public class JPopupMenuFixtureTest extends ComponentFixtureTestCase<JPopupMenu> {

  private JPopupMenuDriver driver;
  private JPopupMenu target;
  private JPopupMenuFixture fixture;
  
  void onSetUp() {
    driver = createMock(JPopupMenuDriver.class);
    target = new JPopupMenu();
    fixture = new JPopupMenuFixture(robot(), target);
    fixture.updateDriver(driver);
  }

  @Test public void shouldReturnMenuLabels() {
    final String[] labels = array("Ben", "Leia"); 
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.menuLabelsOf(target)).andReturn(labels);
      }
      
      protected void codeToTest() {
        String[] result = fixture.menuLabels();
        assertThat(result).isSameAs(labels);
      }
    }.run();
  }

  @Test public void shouldReturnMenuItemByName() {
    final JMenuItem menuItem = new JMenuItem();
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.menuItem(target, "menuItem")).andReturn(menuItem);
      }
      
      protected void codeToTest() {
        JMenuItemFixture result = fixture.menuItem("menuItem");
        assertThat(result.target).isSameAs(menuItem);
      }
    }.run();
  }
  
  @SuppressWarnings("unchecked") 
  @Test public void shouldReturnMenuItemUsingSearchCriteria() {
    final GenericTypeMatcher<? extends JMenuItem> matcher = createMock(GenericTypeMatcher.class);
    final JMenuItem menuItem = new JMenuItem();
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.menuItem(target, matcher)).andReturn(menuItem);
      }
      
      protected void codeToTest() {
        JMenuItemFixture result = fixture.menuItem(matcher);
        assertThat(result.target).isSameAs(menuItem);
      }
    }.run();
  }

  ComponentDriver driver() { return driver; }
  JPopupMenu target() { return target; }
  ComponentFixture<JPopupMenu> fixture() { return fixture; }  
}
