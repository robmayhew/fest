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

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JSpinnerFixture}</code>.
 *
 * @author Yvonne Wang
 */
public class JSpinnerFixtureTest extends ComponentFixtureTestCase<JSpinner> {

  private JSpinnerFixture fixture;
  
  @Test public void shouldIncrementValue() {
    assertThat(fixture.target.getValue()).isEqualTo("Frodo");
    fixture.increment();
    assertThat(fixture.target.getValue()).isEqualTo("Sam");
  }
  
  @Test(dependsOnMethods = "shouldIncrementValue")
  public void shouldDecrementValue() {
    fixture.increment();
    fixture.decrement();
    assertThat(fixture.target.getValue()).isEqualTo("Frodo");
  }
  
  @Test public void shouldEnterText() {
    fixture.enterText("Gandalf");
    assertThat(fixture.target.getValue()).isEqualTo("Gandalf");
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
