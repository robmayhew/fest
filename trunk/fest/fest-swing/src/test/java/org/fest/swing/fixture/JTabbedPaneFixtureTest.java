/*
 * Created on Apr 3, 2007
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

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Arrays.array;

import org.fest.swing.annotation.GUITest;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTabbedPaneFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@GUITest
public class JTabbedPaneFixtureTest extends ComponentFixtureTestCase<JTabbedPane> {

  private JTabbedPaneFixture fixture;
  
  @Test(dataProvider = "tabIndexProvider") 
  public void shouldSelectTabWithGivenIndex(int index) {
    fixture.selectTab(index);
    assertThat(fixture.target.getSelectedIndex()).isEqualTo(index);
  }
  
  @DataProvider(name = "tabIndexProvider")
  public Object[][] tabIndexProvider() {
   return new Object[][] { { 0 }, { 1 } };
  }
  
  @Test(dataProvider = "tabTextProvider") 
  public void shouldSelectTabWithGivenText(String tabName, int expectedIndex) {
    fixture.selectTab(tabName);
    assertThat(fixture.target.getSelectedIndex()).isEqualTo(expectedIndex);
  }
  
  @DataProvider(name = "tabTextProvider")
  public Object[][] tabTextProvider() {
   return new Object[][] { { "First", 0 }, { "Second", 1 } };
  }

  @Test public void shouldReturnAllTabs() {
    assertThat(fixture.tabTitles()).isEqualTo(array("First", "Second"));
  }

  protected ComponentFixture<JTabbedPane> createFixture() {
    fixture = new JTabbedPaneFixture(robot(), "target");
    return fixture;
  }

  protected JTabbedPane createTarget() {
    JTabbedPane target = new JTabbedPane();
    target.setName("target");
    target.addTab("First", new JPanel());
    target.addTab("Second", new JPanel());
    return target;
  }
}
