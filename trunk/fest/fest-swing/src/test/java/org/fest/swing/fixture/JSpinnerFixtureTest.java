/*
 * Created on Jul 1, 2007
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

import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;

import org.fest.util.Collections;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

import static org.fest.swing.fixture.ErrorMessageAssert.actual;
import static org.fest.swing.fixture.ErrorMessageAssert.expected;
import static org.fest.swing.fixture.ErrorMessageAssert.property;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JSpinnerFixture}</code>.
 *
 * @author Yvonne Wang
 */
public class JSpinnerFixtureTest extends ComponentFixtureTestCase<JSpinner> {

  private JSpinnerFixture fixture;

  @Test public void shouldPassIfMatchingValue() {
    fixture.target.setValue("Gandalf");
    fixture.requireValue("Gandalf");
  }
  
  @Test public void shouldFailIfNotMatchingValue() {
    assertThat(spinnerValue()).isEqualTo("Frodo");
    try {
      fixture.requireValue("Sam");
      fail();
    } catch (AssertionError e) {
      ErrorMessageAssert errorMessage = new ErrorMessageAssert(e, fixture().target);
      assertThat(errorMessage).contains(property("value"), expected("'Sam'"), actual("'Frodo'"));
    }
  }
  
  @Test public void shouldIncrementValue() {
    assertThat(spinnerValue()).isEqualTo("Frodo");
    fixture.increment();
    assertThat(spinnerValue()).isEqualTo("Sam");
  }

  @Test public void shouldIncrementValueTheGivenTimes() {
    assertThat(spinnerValue()).isEqualTo("Frodo");
    fixture.increment(2);
    assertThat(spinnerValue()).isEqualTo("Gandalf");
  }
  
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfTimesToIncrementIsZero() {
    fixture.increment(0);
  }
  
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfTimesToIncrementIsNegative() {
    fixture.increment(-1);
  }
  
  @Test public void shouldDecrementValue() {
    fixture.increment();
    fixture.decrement();
    assertThat(spinnerValue()).isEqualTo("Frodo");
  }
  
  @Test public void shouldDecrementValueTheGivenTimes() {
    fixture.target.setValue("Gandalf");
    fixture.decrement(2);
    assertThat(spinnerValue()).isEqualTo("Frodo");
  }
  
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfTimesToDecrementIsZero() {
    fixture.decrement(0);
  }
  
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfTimesToDecrementIsNegative() {
    fixture.decrement(-1);
  }

  @Test public void shouldEnterText() {
    fixture.enterText("Gandalf");
    assertThat(spinnerValue()).isEqualTo("Gandalf");
  }

  private Object spinnerValue() {
    return fixture.target.getValue();
  }

  protected ComponentFixture<JSpinner> createFixture() {
    fixture = new JSpinnerFixture(robot(), "target");
    return fixture;
  }

  protected JSpinner createTarget() {
    JSpinner target = new JSpinner(new SpinnerListModel(Collections.list("Frodo", "Sam", "Gandalf")));
    target.setName("target");
    return target;
  }
}
