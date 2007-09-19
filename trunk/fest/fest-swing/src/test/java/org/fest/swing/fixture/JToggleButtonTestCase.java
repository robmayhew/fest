/*
 * Created on Sep 18, 2007
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

import static org.fest.assertions.Fail.fail;

import static org.fest.swing.fixture.ErrorMessages.EXPECTED_FALSE_BUT_WAS_TRUE;
import static org.fest.swing.fixture.ErrorMessages.EXPECTED_TRUE_BUT_WAS_FALSE;
import static org.fest.swing.fixture.ErrorMessages.equalsFailedMessage;

import org.testng.annotations.Test;

/**
 * Understands test methods for subclasses of <code>{@link JToggleButtonFixture}</code>.
 * @param <T> the type of component tested by this test class. 
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class JToggleButtonTestCase<T extends JToggleButton> extends ComponentFixtureTestCase<T> {

  private static final String SELECTED_PROPERTY = "selected";

  @Test public void shouldPassIfButtonHasMatchingText() {
    toggleButtonFixture().requireText("Target");
  }
  
  @Test(dependsOnMethods = "shouldPassIfButtonHasMatchingText") 
  public void shouldFailIfButtonHasNotMatchingText() {
    try {
      toggleButtonFixture().requireText("A Button");
      fail();
    } catch (AssertionError e) {
      errorMessages().assertIsCorrect(e, "text", equalsFailedMessage("'A Button'", "'Target'"));
    }
  }
  
  @Test public final void shouldPassIfButtonIsSelectedAndExpectingSelected() {
    toggleButtonFixture().target.setSelected(true);
    toggleButtonFixture().requireSelected();
  }
  
  @Test public final void shouldFailIfButtonIsNotSelectedAndExpectingSelected() {
    toggleButtonFixture().target.setSelected(false);
    try {
      toggleButtonFixture().requireSelected();
      fail();
    } catch(AssertionError e) {
      errorMessages().assertIsCorrect(e, SELECTED_PROPERTY, EXPECTED_TRUE_BUT_WAS_FALSE);
    }
  }
  
  @Test public final void shouldPassIfButtonIsNotSelectedAndExpectingNotSelected() {
    toggleButtonFixture().target.setSelected(false);
    toggleButtonFixture().requireNotSelected();
  }
  
  @Test public final void shouldFailIfButtonIsSelectedAndExpectingNotSelected() {
    toggleButtonFixture().target.setSelected(true);
    try {
      toggleButtonFixture().requireNotSelected();
      fail();
    } catch(AssertionError e) {
      errorMessages().assertIsCorrect(e, SELECTED_PROPERTY, EXPECTED_FALSE_BUT_WAS_TRUE);
    }
  }
  
  protected final JToggleButtonFixture<T> toggleButtonFixture() {
    return (JToggleButtonFixture<T>)fixture();
  }
}
