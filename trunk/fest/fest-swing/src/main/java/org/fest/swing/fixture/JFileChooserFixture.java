/*
 * Created on Jul 9, 2007
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

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

import org.fest.swing.ComponentLookupException;
import org.fest.swing.ComponentMatcher;
import org.fest.swing.RobotFixture;

import static org.fest.util.Strings.*;

/**
 * Understands simulation of user events on a <code>{@link JFileChooser}</code> and verification of the state of such
 * <code>{@link JFileChooser}</code>.
 *
 *
 * @author Yvonne Wang 
 */
public class JFileChooserFixture extends ComponentFixture<JFileChooser> {

  /**
   * Creates a new </code>{@link JFileChooserFixture}</code>.
   * @param robot performs simulation of user events on a <code>JFileChooser</code>.
   * @throws ComponentLookupException if a matching <code>JFileChooser</code> could not be found.
   */
  public JFileChooserFixture(RobotFixture robot) {
    super(robot, JFileChooser.class);
  }
  
  /**
   * Creates a new </code>{@link JFileChooserFixture}</code>.
   * @param robot performs simulation of user events on a <code>JFileChooser</code>.
   * @param labelName the name of the <code>JFileChooser</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JFileChooser</code> could not be found.
   */
  public JFileChooserFixture(RobotFixture robot, String labelName) {
    super(robot, labelName, JFileChooser.class);
  }
  
  /**
   * Creates a new </code>{@link JFileChooserFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JFileChooser</code>.
   * @param target the <code>JFileChooser</code> to be managed by this fixture.
   */
  public JFileChooserFixture(RobotFixture robot, JFileChooser target) {
    super(robot, target);
  }

  /**
   * Simulates a user pressing the "Cancel" button in the <code>{@link JFileChooser}</code> managed by this fixture.
   * @return this fixture.
   * @throws ComponentLookupException if the "Cancel" button cannot be found.
   * @throws AssertionError if the "Cancel" button is disabled.
   */
  public final JFileChooserFixture cancel() {
    cancelButton().requireEnabled().click();
    return this;
  }
  
  /**
   * Finds the "Cancel" button in the <code>{@link JFileChooser}</code> managed by this fixture.
   * @return the found "Cancel" button.
   * @throws ComponentLookupException if the "Cancel" button cannot be found.
   */
  public JButtonFixture cancelButton() {
    return new JButtonFixture(robot, findButton(UIManager.getString("FileChooser.cancelButtonText")));
  }

  private JButton findButton(final String text) {
    JButton button = (JButton) robot.finder().find(target, new ComponentMatcher() {
      public boolean matches(Component c) {
        return (c instanceof JButton && text.equals(((JButton) c).getText()));
      }
    });
    if (button == null)
      throw new ComponentLookupException(concat("Unable to find a JButton with the text ", quote(text)));
    return button;
  }
}
