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

import org.fest.swing.core.GuiTask;
import org.fest.swing.core.Robot;

import static org.fest.assertions.Assertions.assertThat;

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
  public void requireText(AbstractButton button, String expected) {
    String text = new GetTextTask(button).run();
    assertThat(text).as(propertyName(button, TEXT_PROPERTY)).isEqualTo(expected);
  }

  /**
   * Selects the given button only it is not already selected.
   * @param button the target button.
   */
  public void select(AbstractButton button) {
    if (isButtonSelected(button)) return;
    click(button);
  }

  /**
   * Unselects the given button only if it is selected.
   * @param button the target button.
   */
  public void unselect(AbstractButton button) {
    if (!isButtonSelected(button)) return;
    click(button);
  }
  
  /**
   * Verifies that the button is selected.
   * @param button the given button.
   * @throws AssertionError if the button is not selected.
   */
  public void requireSelected(AbstractButton button) {
    assertButtonIsSelected(button, true);
  }
  
  /**
   * Verifies that the button is not selected.
   * @param button the given button.
   * @throws AssertionError if the button is selected.
   */
  public void requireNotSelected(AbstractButton button) {
    assertButtonIsSelected(button, false);
  }

  private void assertButtonIsSelected(AbstractButton button, boolean selected) {
    assertThat(isButtonSelected(button)).as(selectedProperty(button)).isEqualTo(selected);
  }
  
  private boolean isButtonSelected(AbstractButton button) {
    return new IsSelectedTask(button).run();
  }

  private static String selectedProperty(AbstractButton button) {
    return propertyName(button, SELECTED_PROPERTY);
  }

  private static class GetTextTask extends GuiTask<String> {
    private final AbstractButton button;

    GetTextTask(AbstractButton button) {
      this.button = button;
    }

    protected String executeInEDT() {
      return button.getText();
    }
  }

  private static class IsSelectedTask extends GuiTask<Boolean> {
    private final AbstractButton button;

    IsSelectedTask(AbstractButton button) {
      this.button = button;
    }

    protected Boolean executeInEDT() {
      return button.isSelected();
    }
  }
}
