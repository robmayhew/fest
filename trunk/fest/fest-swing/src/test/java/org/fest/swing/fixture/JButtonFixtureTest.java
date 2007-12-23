/*
 * Created on Feb 8, 2007
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

import javax.swing.JButton;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

import static org.fest.swing.fixture.ErrorMessageAssert.*;

import org.fest.swing.annotation.GUITest;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JButtonFixture}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@GUITest
public class JButtonFixtureTest extends ComponentFixtureTestCase<JButton> {

  private JButtonFixture fixture;
  
  @Test public void shouldPassIfButtonHasMatchingText() {
    fixture.requireText("Target");
  }
  
  @Test(dependsOnMethods = "shouldPassIfButtonHasMatchingText") 
  public void shouldFailIfButtonHasNotMatchingText() {
    try {
      fixture.requireText("A Button");
      fail();
    } catch (AssertionError e) {
      ErrorMessageAssert errorMessage = new ErrorMessageAssert(e, fixture.target);
      assertThat(errorMessage).contains(property("text"), expected("'A Button'"), actual("'Target'"));
    }
  }
  
  @Test public void shouldReturnButtonText() {
    assertThat(fixture.text()).isEqualTo("Target");
  }
  
  protected ComponentFixture<JButton> createFixture() { 
    fixture = new JButtonFixture(robot(), "target");
    return fixture;
  }

  protected JButton createTarget() { 
    JButton target = new JButton("Target");
    target.setName("target");    
    return target;
  }
}
