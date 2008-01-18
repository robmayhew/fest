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

import org.testng.annotations.Test;

import org.fest.swing.annotation.GUITest;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.fixture.ErrorMessageAssert.*;

/**
 * Tests for <code>{@link JLabelFixture}</code>.
 *
 * @author Yvonne Wang
 */
@GUITest
public class JLabelFixtureTest extends ComponentFixtureTestCase<JLabel> {

  private JLabelFixture fixture;
  
  @Test public void shouldPassIfLabelHasMatchingText() {
    fixture.requireText("Target");
  }
  
  @Test(dependsOnMethods = "shouldPassIfLabelHasMatchingText") 
  public void shouldFailIfLabelHasNotMatchingText() {
    try {
      fixture.requireText("A Label");
      fail();
    } catch (AssertionError e) {
      ErrorMessageAssert errorMessage = new ErrorMessageAssert(e, fixture.target);
      assertThat(errorMessage).contains(property("text"), expected("'A Label'"), actual("'Target'"));
    }
  }
  
  @Test public void shouldReturnLabelText() {
    assertThat(fixture.text()).isEqualTo("Target");
  }

  protected ComponentFixture<JLabel> createFixture() {
    fixture = new JLabelFixture(robot(), "target");
    return fixture;
  }

  protected JLabel createTarget() {
    JLabel target = new JLabel("Target");
    target.setName("target");
    return target;
  }
  
}
