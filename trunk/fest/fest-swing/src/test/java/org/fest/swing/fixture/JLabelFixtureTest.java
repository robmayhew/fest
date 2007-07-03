/*
 * Created on Feb 9, 2007
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

import javax.swing.JLabel;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.GUITest;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JLabelFixture}</code>.
 *
 * @author Yvonne Wang
 */
@GUITest public class JLabelFixtureTest extends AbstractComponentFixtureTest<JLabel> {

  private JLabelFixture fixture;
  
  @Test public void shouldPassIfLabelHasMatchingText() {
    fixture.requireText("Target");
  }
  
  @Test(dependsOnMethods = "shouldPassIfLabelHasMatchingText", expectedExceptions = AssertionError.class) 
  public void shouldFailIfLabelHasNotMatchingText() {
    fixture.requireText("A Label");
  }
  
  @Test public void shouldReturnLabelText() {
    assertThat(fixture.text()).isEqualTo("Target");
  }

  protected void afterSetUp() {
    fixture = (JLabelFixture)fixture();
  }

  protected ComponentFixture<JLabel> createFixture() {
    return new JLabelFixture(robot(), "target");
  }

  protected JLabel createTarget() {
    JLabel target = new JLabel("Target");
    target.setName("target");
    return target;
  }
  
}
