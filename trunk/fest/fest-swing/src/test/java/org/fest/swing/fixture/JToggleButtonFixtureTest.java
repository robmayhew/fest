/*
 * Created on Jun 10, 2007
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

import javax.swing.JToggleButton;

import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link org.fest.swing.fixture.JToggleButtonFixture}</code>.
 *
 * @author Alex Ruiz
 */
public class JToggleButtonFixtureTest extends TwoStateButtonFixtureTestCase<JToggleButton> {

  private JToggleButtonFixture fixture;

  @Test public void shouldSelectCheckBoxIfNotSelected() {
    fixture.target.setSelected(false);
    fixture.check();
    assertThat(fixture.target.isSelected()).isTrue();
  }

  @Test public void shouldNotSelectCheckboxIfAlreadySelected() {
    fixture.target.setSelected(true);
    fixture.check();
    assertThat(fixture.target.isSelected()).isTrue();
  }

  @Test public void shouldUnselectCheckBoxIfSelected() {
    fixture.target.setSelected(true);
    fixture.uncheck();
    assertThat(fixture.target.isSelected()).isFalse();
  }

  @Test public void shouldNotUnselectCheckboxIfAlreadyUnselected() {
    fixture.target.setSelected(false);
    fixture.uncheck();
    assertThat(fixture.target.isSelected()).isFalse();
  }

  protected ComponentFixture<JToggleButton> createFixture() {
    fixture = new JToggleButtonFixture(robot(), "target");
    return fixture;
  }

  protected JToggleButton createTarget() {
    JToggleButton target = new JToggleButton("Target");
    target.setName("target");
    return target;
  }
}