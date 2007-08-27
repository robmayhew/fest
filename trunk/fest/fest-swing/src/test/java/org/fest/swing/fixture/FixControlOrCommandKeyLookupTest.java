/*
 * Created on Aug 27, 2007
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

import javax.swing.JComponent;
import javax.swing.JLabel;

import abbot.Platform;
import static java.awt.event.KeyEvent.VK_CONTROL;
import static java.awt.event.KeyEvent.VK_META;
import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.RobotFixture;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Fix for <a href="http://code.google.com/p/fest/issues/detail?id=19&can=2&q=" target="_blank">issue 19</a>.
 *
 * @author Alex Ruiz
 */
public class FixControlOrCommandKeyLookupTest {

  private ComponentFixture<JComponent> fixture;
  
  @BeforeTest public void setUp() {
    fixture = new ComponentFixture<JComponent>(RobotFixture.robotWithCurrentAwtHierarchy(), new JLabel()) {};
  }
  
  @AfterTest public void tearDown() {
    fixture.robot.cleanUp();
  }
  
  @Test public void shouldReturnControlKeyForNonMacOS() {
    if (Platform.isOSX())
      assertThat(fixture.controlOrCommandKey()).isEqualTo(VK_META);
    else  
      assertThat(fixture.controlOrCommandKey()).isEqualTo(VK_CONTROL);
  }
}
