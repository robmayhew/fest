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

import javax.swing.JRadioButton;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.annotation.GUITest;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JRadioButtonFixture}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@GUITest
public class JRadioButtonFixtureTest extends TwoStateButtonFixtureTestCase<JRadioButton> {

  private JRadioButtonFixture fixture;
  
  @Test public void shouldReturnButtonText() {
    assertThat(fixture.text()).isEqualTo("Target");
  }
  
  protected ComponentFixture<JRadioButton> createFixture() { 
    fixture = new JRadioButtonFixture(robot(), "target");
    return fixture;
  }

  protected JRadioButton createTarget() { 
    JRadioButton target = new JRadioButton("Target");
    target.setName("target");    
    return target;
  }
}
