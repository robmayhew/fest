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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.Dimension;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import static javax.swing.ScrollPaneConstants.*;
import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JScrollPaneFixture}</code>.
 *
 * @author Yvonne Wang
 *
 */
public class JScrollPaneFixtureTest extends ComponentFixtureTestCase<JScrollPane> {

  private JScrollPane target;
  private JScrollPaneFixture fixture;

  @Override protected ComponentFixture<JScrollPane> createFixture() {
    fixture = new JScrollPaneFixture(robot(), target);
    return fixture;
  }

  @Override protected JScrollPane createTarget() {
    target = new JScrollPane(new JTable(30, 20), VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_ALWAYS);
    target.setName("target");
    target.setPreferredSize(new Dimension(200, 100));
    return target;
  }

  @Test public void shouldReturnVerticalScrollBar() {
    assertReturnsVerticalScrollBar();
  }

  @Test public void shouldReturnVerticalScrollBarIfNotVisible() {
    target.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);
    assertReturnsVerticalScrollBar();
  }

  private void assertReturnsVerticalScrollBar() {
    assertReturnsScrollBar(fixture.verticalScrollBar(), target.getVerticalScrollBar());
  }

  @Test public void shouldReturnHorizontalScrollBar() {
    assertReturnsHorizontalScrollBar();
  }

  @Test public void shouldReturnHorizontalScrollBarIfNotVisible() {
    target.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
    assertReturnsHorizontalScrollBar();
  }

  private void assertReturnsHorizontalScrollBar() {
    assertReturnsScrollBar(fixture.horizontalScrollBar(), target.getHorizontalScrollBar());
  }

  private void assertReturnsScrollBar(JScrollBarFixture scrollBarFixture, JScrollBar expected) {
    assertThat(scrollBarFixture.robot).isSameAs(fixture.robot);
    assertThat(scrollBarFixture.target).isSameAs(expected);

  }
}
