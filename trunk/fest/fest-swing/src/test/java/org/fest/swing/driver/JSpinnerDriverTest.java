/*
 * Created on Feb 25, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.driver;

import java.awt.Dimension;

import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.testing.TestFrame;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.FUNCTIONAL;
import static org.fest.util.Arrays.array;
import static org.fest.util.Strings.concat;

/**
 * Tests for <code>{@link JSpinnerDriver}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = FUNCTIONAL)
public class JSpinnerDriverTest {

  private Robot robot;
  private JSpinner spinner;
  private JSpinnerDriver driver;
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JSpinnerDriver(robot);
    MyFrame frame = new MyFrame();
    spinner = frame.spinner;
    robot.showWindow(frame);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  @Test public void shouldIncrementValue() {
    assertThatSpinnerValueIsEqualTo("Frodo");
    driver.increment(spinner);
    assertThatSpinnerValueIsEqualTo("Sam");
  }

  @Test public void shouldIncrementValueTheGivenTimes() {
    assertThatSpinnerValueIsEqualTo("Frodo");
    driver.increment(spinner, 2);
    assertThatSpinnerValueIsEqualTo("Gandalf");
  }
  
  @Test(dataProvider = "zeroAndNegative", dataProviderClass = ZeroAndNegativeProvider.class)
  public void shouldThrowErrorIfTimesToIncrementIsZeroOrNegative(int times) {
    try {
      driver.increment(spinner, times);
      fail();
    } catch (ActionFailedException expected) {
      String message = concat(
          "The number of times to increment the value should be greater than zero, but was <", times, ">");
      assertThat(expected).message().isEqualTo(message);
    }
  }
  
  @Test public void shouldDecrementValue() {
    driver.increment(spinner);
    driver.decrement(spinner);
    assertThatSpinnerValueIsEqualTo("Frodo");
  }
  
  @Test public void shouldDecrementValueTheGivenTimes() {
    spinner.setValue("Gandalf");
    driver.decrement(spinner, 2);
    assertThatSpinnerValueIsEqualTo("Frodo");
  }
  
  @Test(dataProvider = "zeroAndNegative", dataProviderClass = ZeroAndNegativeProvider.class)
  public void shouldThrowErrorIfTimesToDecrementIsZeroOrNegative(int times) {
    try {
      driver.decrement(spinner, times);
      fail();
    } catch (ActionFailedException expected) {
      String message = concat(
          "The number of times to decrement the value should be greater than zero, but was <", times, ">");
      assertThat(expected).message().isEqualTo(message);
    }
  }
  
  @Test public void shouldEnterText() {
    driver.enterText(spinner, "Gandalf");
    assertThatSpinnerValueIsEqualTo("Gandalf");
  }
  
  private void assertThatSpinnerValueIsEqualTo(Object expected) {
    assertThat(spinner.getValue()).isEqualTo(expected);
  }

  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JSpinner spinner = new JSpinner(new SpinnerListModel(array("Frodo", "Sam", "Gandalf")));
    
    MyFrame() {
      super(JSpinnerDriverTest.class);
      add(spinner);
      setPreferredSize(new Dimension(160, 80));
    }
  }
}
