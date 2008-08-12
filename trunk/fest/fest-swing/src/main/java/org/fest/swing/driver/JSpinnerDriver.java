/*
 * Created on Jan 26, 2008
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


import javax.swing.JSpinner;
import javax.swing.text.JTextComponent;

import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.UnexpectedException;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.JSpinnerCommitEditTask.commitEditIn;
import static org.fest.swing.driver.JSpinnerDecrementValueTask.decrementValueOf;
import static org.fest.swing.driver.JSpinnerIncrementValueTask.incrementValueOf;
import static org.fest.swing.driver.JSpinnerValueQuery.valueOf;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.query.ComponentEnabledQuery.isEnabled;
import static org.fest.util.Strings.concat;

/**
 * Understands simulation of user input on a <code>{@link JSpinner}</code>. Unlike <code>JSpinnerFixture</code>, this
 * driver only focuses on behavior present only in <code>{@link JSpinner}</code>s. This class is intended for internal
 * use only.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JSpinnerDriver extends JComponentDriver {

  private static final String VALUE_PROPERTY = "value";
  private final JTextComponentDriver textComponentDriver;

  /**
   * Creates a new </code>{@link JSpinnerDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public JSpinnerDriver(Robot robot) {
    super(robot);
    textComponentDriver = new JTextComponentDriver(robot);
  }

  /**
   * Increments the value of the <code>{@link JSpinner}</code> the given number of times.
   * @param spinner the target <code>JSpinner</code>.
   * @param times how many times the value of this fixture's <code>JSpinner</code> should be incremented.
   * @throws ActionFailedException if <code>times</code> is less than or equal to zero.
   */
  public void increment(JSpinner spinner, int times) {
    validate(times, "increment the value");
    for (int i = 0; i < times; i++) increment(spinner);
  }

  /**
   * Increments the value of the <code>{@link JSpinner}</code>.
   * @param spinner the target <code>JSpinner</code>.
   */
  public void increment(JSpinner spinner) {
    if (!isEnabled(spinner)) return;
    robot.invokeAndWait(incrementValueOf(spinner));
  }

  /**
   * Decrements the value of the <code>{@link JSpinner}</code> the given number of times.
   * @param spinner the target <code>JSpinner</code>.
   * @param times how many times the value of this fixture's <code>JSpinner</code> should be decremented.
   * @throws ActionFailedException if <code>times</code> is less than or equal to zero.
   */
  public void decrement(JSpinner spinner, int times) {
    validate(times, "decrement the value");
    for (int i = 0; i < times; i++) decrement(spinner);
  }

  private void validate(int times, String action) {
    if (times > 0) return;
    throw actionFailure(concat(
        "The number of times to ", action, " should be greater than zero, but was <", times, ">"));
  }

  /**
   * Decrements the value of the <code>{@link JSpinner}</code>.
   * @param spinner the target <code>JSpinner</code>.
   */
  public void decrement(JSpinner spinner) {
    if (!isEnabled(spinner)) return;
    robot.invokeAndWait(decrementValueOf(spinner));
  }

  /**
   * Enters and commits the given text in the <code>{@link JSpinner}</code>, assuming its editor has a
   * <code>{@link JTextComponent}</code> under it.
   * @param spinner the target <code>JSpinner</code>.
   * @param text the text to enter.
   * @throws ActionFailedException if the editor of the <code>JSpinner</code> is not a <code>JTextComponent</code> or
   * cannot be found.
   * @throws UnexpectedException if the entering the text in the <code>JSpinner</code>'s editor fails.
   */
  public void enterTextAndCommit(JSpinner spinner, String text) {
    if (!isEnabled(spinner)) return;
    enterText(spinner, text);
    robot.invokeAndWait(commitEditIn(spinner));
  }

  /**
   * Enters the given text in the <code>{@link JSpinner}</code>, assuming its editor has a
   * <code>{@link JTextComponent}</code> under it. This method does not commit the value to the <code>JSpinner</code>.
   * @param spinner the target <code>JSpinner</code>.
   * @param text the text to enter.
   * @throws ActionFailedException if the editor of the <code>JSpinner</code> is not a <code>JTextComponent</code> or
   * cannot be found.
   * @throws UnexpectedException if the entering the text in the <code>JSpinner</code>'s editor fails.
   * @see #enterTextAndCommit(JSpinner, String)
   */
  public void enterText(JSpinner spinner, String text) {
    if (!isEnabled(spinner)) return;
    try {
      textComponentDriver.replaceText(editor(spinner), text);
    } catch (ComponentLookupException e) {
      throw actionFailure(concat("Unable to find editor for ", format(spinner)));
    }
  }

  /**
   * Returns the <code>{@link JTextComponent}</code> used as editor in the given <code>{@link JSpinner}</code>.
   * @param spinner the target <code>JSpinner</code>.
   * @return the <code>JTextComponent</code> used as editor in the given <code>JSpinner</code>.
   * @throws ComponentLookupException if the given <code>JSpinner</code> does not have a <code>JTextComponent</code> as
   * editor.
   */
  public JTextComponent editor(JSpinner spinner) {
    return robot.finder().findByType(spinner, JTextComponent.class);
  }

  /**
   * Verifies that the value of the <code>{@link JSpinner}</code> is equal to the given one.
   * @param spinner the target <code>JSpinner</code>.
   * @param value the expected value of this fixture's <code>JSpinner</code>.
   * @throws AssertionError if the value of the <code>JSpinner</code> is not equal to the given one.
   */
  public void requireValue(JSpinner spinner, Object value) {
    assertThat(valueOf(spinner)).as(propertyName(spinner, VALUE_PROPERTY)).isEqualTo(value);
  }
}
