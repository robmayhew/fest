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

import javax.swing.JSlider;

import static javax.swing.SwingConstants.HORIZONTAL;
import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JSliderFixture}</code>.
 *
 * @author Yvonne Wang
 */
public class JSliderFixtureTest extends ComponentFixtureTestCase<JSlider> {
  
  private JSliderFixture fixture;

  @Test public void shouldIncrementValue() {
    fixture.increment();
    assertThat(fixture.target.getValue()).isGreaterThan(15);
  }

  @Test public void shouldDecrementValue() {
    fixture.decrement();
    assertThat(fixture.target.getValue()).isLessThan(15);
  }

  protected ComponentFixture<JSlider> createFixture() {
    fixture = new JSliderFixture(robot(), "target");
    return fixture;
  }

  protected JSlider createTarget() {
    JSlider target = new JSlider();
    target.setName("target");
    target.setOrientation(HORIZONTAL);
    target.setMinimum(0);
    target.setMaximum(30);
    target.setValue(15);
    return target;
  }


}
