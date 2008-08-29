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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.fixture;

import javax.swing.JScrollPane;

import org.testng.annotations.Test;

import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.JComponentDriver;

import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.factory.JLists.list;
import static org.fest.swing.factory.JScrollPanes.scrollPane;

/**
 * Tests for <code>{@link JScrollPaneFixture}</code>.
 *
 * @author Yvonne Wang
 */
public class JScrollPaneFixtureTest extends CommonComponentFixtureTestCase<JScrollPane> {

  private JComponentDriver driver;
  private JScrollPane target;
  private JScrollPaneFixture fixture;
  
  void onSetUp() {
    driver = createMock(JComponentDriver.class);
    target = scrollPane().withView(list().createInEDT())
                         .createInEDT();
    fixture = new JScrollPaneFixture(robot(), target);
    fixture.updateDriver(driver);
  }

  @Test public void shouldCreateFixtureWithGivenComponentName() {
    String name = "scrollPane";
    expectLookupByName(name, JScrollPane.class);
    verifyLookup(new JScrollPaneFixture(robot(), name));
  }

  @Test public void shouldReturnHorizontalScrollBar() {
    JScrollBarFixture scrollBar = fixture.horizontalScrollBar();
    assertThat(scrollBar.target).isSameAs(target.getHorizontalScrollBar());
  }
  
  @Test public void shouldReturnVerticalalScrollBar() {
    JScrollBarFixture scrollBar = fixture.verticalScrollBar();
    assertThat(scrollBar.target).isSameAs(target.getVerticalScrollBar());
  }
  
  ComponentDriver driver() { return driver; }
  JScrollPane target() { return target; }
  JScrollPaneFixture fixture() { return fixture; }
}
