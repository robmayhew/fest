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

import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JScrollBarFixture}</code>.
 *
 * @author Alex Ruiz
 */
public class JScrollBarFixtureTest extends ComponentFixtureTestCase<JScrollBar> {

  private static final int VALUE = 30;
  private static final int BLOCK_INCREMENT = 10;
  
  private JScrollBar target;
  private JScrollBarFixture fixture;
  
  @Override protected ComponentFixture<JScrollBar> createFixture() {
    fixture = new JScrollBarFixture(robot(), target);
    return fixture;
  }

  @Override protected JScrollBar createTarget() {
    target = new JScrollBar();
    target.setPreferredSize(new Dimension(20, 200));
    target.setName("target");
    target.setBlockIncrement(BLOCK_INCREMENT);
    target.setValue(VALUE);
    return target;
  }

  @Test(expectedExceptions=AssertionError.class)
  public void shouldFailIfNotMatchingValue() {
    target.setValue(10);
    fixture.requireValue(60);
  }
  
  @Test public void shouldPassIfMatchingValue() {
    target.setValue(68);
    fixture.requireValue(68);
  }
  
  @Test(expectedExceptions=IllegalArgumentException.class)
  public void shouldThrowErrorIfTimesToScrollUnitUpIsNegative() {
    fixture.scrollUnitUp(-1);
  }
  
  @Test(expectedExceptions=IllegalArgumentException.class)
  public void shouldThrowErrorIfTimesToScrollUnitUpIsZero() {
    fixture.scrollUnitUp(0);
  }
  
  @Test public void shouldScrollUnitUpTheGivenNumberOfTimes() {
    fixture.scrollUnitUp(6);
    assertThat(target.getValue()).isEqualTo(VALUE + 6);
  }
  
  @Test public void shouldScrollUnitUp() {
    fixture.scrollUnitUp();
    assertThat(target.getValue()).isEqualTo(VALUE + 1);
  }
  
  @Test(expectedExceptions=IllegalArgumentException.class)
  public void shouldThrowErrorIfTimesToScrollUnitDownIsNegative() {
    fixture.scrollUnitDown(-1);
  }
  
  @Test(expectedExceptions=IllegalArgumentException.class)
  public void shouldThrowErrorIfTimesToScrollUnitDownIsZero() {
    fixture.scrollUnitDown(0);
  }
  
  @Test public void shouldScrollUnitDownTheGivenNumberOfTimes() {
    fixture.scrollUnitDown(8);
    assertThat(target.getValue()).isEqualTo(VALUE - 8);
  }
  
  @Test public void shouldScrollUnitDown() {
    fixture.scrollUnitDown();
    assertThat(target.getValue()).isEqualTo(VALUE - 1);
  }
  
  @Test(expectedExceptions=IllegalArgumentException.class)
  public void shouldThrowErrorIfTimesToScrollBlockUpIsNegative() {
    fixture.scrollBlockUp(-1);
  }
  
  @Test(expectedExceptions=IllegalArgumentException.class)
  public void shouldThrowErrorIfTimesToScrollBlockUpIsZero() {
    fixture.scrollBlockUp(0);
  }
  
  @Test public void shouldScrollBlockUpTheGivenNumberOfTimes() {
    int times = 2;
    fixture.scrollBlockUp(times);
    assertThat(target.getValue()).isEqualTo(VALUE + (BLOCK_INCREMENT * times));    
  }
  
  @Test public void shouldScrollBlockUp() {
    fixture.scrollBlockUp();
    assertThat(target.getValue()).isEqualTo(VALUE + BLOCK_INCREMENT);    
  }
  
  @Test(expectedExceptions=IllegalArgumentException.class)
  public void shouldThrowErrorIfTimesToScrollBlockDownIsNegative() {
    fixture.scrollBlockDown(-1);
  }
  
  @Test(expectedExceptions=IllegalArgumentException.class)
  public void shouldThrowErrorIfTimesToScrollBlockDownIsZero() {
    fixture.scrollBlockDown(0);
  }
  
  @Test public void shouldScrollBlockUpDownTheGivenNumberOfTimes() {
    int times = 2;
    fixture.scrollBlockDown(times);
    assertThat(target.getValue()).isEqualTo(VALUE - (BLOCK_INCREMENT * times));    
  }
  
  @Test public void shouldScrollBlockDown() {
    fixture.scrollBlockDown();
    assertThat(target.getValue()).isEqualTo(VALUE - BLOCK_INCREMENT);    
  }
}
