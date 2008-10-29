/*
 * Created on Feb 28, 2008
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

import javax.swing.AbstractButton;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.UnexpectedException;
import org.fest.swing.query.AbstractButtonTextQuery;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.ComponentStateValidator.validateIsEnabled;
import static org.fest.swing.edt.GuiActionExecutionType.RUN_IN_CURRENT_THREAD;
import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands simulation of user input on an <code>{@link AbstractButton}</code>. This class is intended for internal
 * use only.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class AbstractButtonDriver extends JComponentDriver {

  private static final String SELECTED_PROPERTY = "selected";
  private static final String TEXT_PROPERTY = "text";

  /**
   * Creates a new </code>{@link AbstractButtonDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public AbstractButtonDriver(Robot robot) {
    super(robot);
  }

  /**
   * Asserts that the text in the given button is equal to the specified <code>String</code>.
   * @param button the given button.
   * @param expected the text to match.
   * @throws AssertionError if the text of the button is not equal to the given one.
   */
  @RunsInEDT
  public void requireText(AbstractButton button, String expected) {
    assertThat(textOf(button)).as(propertyName(button, TEXT_PROPERTY)).isEqualTo(expected);
  }

  /**
   * Returns the text of the given button.
   * @param button the given button.
   * @return the text of the given button.
   */
  @RunsInEDT
  public String textOf(AbstractButton button) {
    return AbstractButtonTextQuery.textOf(button);
  }

  /**
   * Selects the given button only it is not already selected.
   * @param button the target button.
   * @throws ActionFailedException if the button is disabled.
   */
  @RunsInEDT
  public void select(AbstractButton button) {
    boolean ready = false;
    try {
      ready = !isSelectedAndEnabled(button);
    } catch (UnexpectedException unexpected) {
      throw unexpected.bomb();
    }
    if (!ready) return;
    click(button);
  }

  /**
   * Unselects the given button only if it is selected.
   * @param button the target button.
   * @throws ActionFailedException if the button is disabled.
   */
  @RunsInEDT
  public void unselect(AbstractButton button) {
    boolean ready = false;
    try {
      ready = isSelectedAndEnabled(button);
    } catch (UnexpectedException unexpected) {
      throw unexpected.bomb();
    }
    if (!ready) return;
    click(button);
  }

  private static boolean isSelectedAndEnabled(final AbstractButton button) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        validateIsEnabled(button, RUN_IN_CURRENT_THREAD);
        return button.isSelected();
      }
    });
  }

  /**
   * Verifies that the button is selected.
   * @param button the given button.
   * @throws AssertionError if the button is not selected.
   */
  @RunsInEDT
  public void requireSelected(AbstractButton button) {
    assertButtonIsSelected(button, true);
  }

  /**
   * Verifies that the button is not selected.
   * @param button the given button.
   * @throws AssertionError if the button is selected.
   */
  @RunsInEDT
  public void requireNotSelected(AbstractButton button) {
    assertButtonIsSelected(button, false);
  }

  private void assertButtonIsSelected(AbstractButton button, boolean selected) {
    assertThat(isSelected(button)).as(selectedProperty(button)).isEqualTo(selected);
  }

  private static boolean isSelected(final AbstractButton button) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return button.isSelected();
      }
    });
  }

  private static String selectedProperty(AbstractButton button) {
    return propertyName(button, SELECTED_PROPERTY);
  }
}
