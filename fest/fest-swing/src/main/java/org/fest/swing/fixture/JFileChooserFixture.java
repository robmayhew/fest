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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.fixture;

import java.io.File;

import javax.swing.JFileChooser;

import org.fest.swing.core.Robot;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Timeout;
import org.fest.swing.driver.JFileChooserDriver;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;

/**
 * Understands simulation of user events on a <code>{@link JFileChooser}</code> and verification of the state of such
 * <code>{@link JFileChooser}</code>.
 *
 * @author Yvonne Wang 
 */
public class JFileChooserFixture extends ComponentFixture<JFileChooser> {

  private JFileChooserDriver driver;
  
  /**
   * Creates a new <code>{@link JFileChooserFixture}</code>.
   * @param robot performs simulation of user events on a <code>JFileChooser</code>.
   * @throws ComponentLookupException if a matching <code>JFileChooser</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JFileChooser</code> is found.
   */
  public JFileChooserFixture(Robot robot) {
    super(robot, JFileChooser.class);
    createDriver();
  }
  
  /**
   * Creates a new <code>{@link JFileChooserFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JFileChooser</code>.
   * @param target the <code>JFileChooser</code> to be managed by this fixture.
   */
  public JFileChooserFixture(Robot robot, JFileChooser target) {
    super(robot, target);
    createDriver();
  }
  
  /**
   * Creates a new <code>{@link JFileChooserFixture}</code>.
   * @param robot performs simulation of user events on a <code>JFileChooser</code>.
   * @param labelName the name of the <code>JFileChooser</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JFileChooser</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JFileChooser</code> is found.
   */
  public JFileChooserFixture(Robot robot, String labelName) {
    super(robot, labelName, JFileChooser.class);
    createDriver();
  }

  private void createDriver() {
    updateDriver(new JFileChooserDriver(robot));
  }

  final void updateDriver(JFileChooserDriver driver) {
    this.driver = driver;
  }
  
  /**
   * Simulates a user pressing the "Approve" button in this fixture's <code>{@link JFileChooser}</code>.
   * @throws ComponentLookupException if the "Approve" button cannot be found.
   * @throws AssertionError if the "Approve" button is disabled.
   */
  public void approve() {
    driver.clickApproveButton(target);
  }
  
  /**
   * Finds the "Approve" button in this fixture's <code>{@link JFileChooser}</code>.
   * @return the found "Approve" button.
   * @throws ComponentLookupException if the "Approve" button cannot be found.
   */
  public JButtonFixture approveButton() {
    return new JButtonFixture(robot, driver.approveButton(target));
  }

  /**
   * Simulates a user pressing the "Cancel" button in this fixture's <code>{@link JFileChooser}</code>.
   * @throws ComponentLookupException if the "Cancel" button cannot be found.
   * @throws AssertionError if the "Cancel" button is disabled.
   */
  public void cancel() {
    driver.clickCancelButton(target);
  }

  /**
   * Finds the "Cancel" button in this fixture's <code>{@link JFileChooser}</code>.
   * @return the found "Cancel" button.
   * @throws ComponentLookupException if the "Cancel" button cannot be found.
   */
  public JButtonFixture cancelButton() {
    return new JButtonFixture(robot, driver.cancelButton(target));
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JFileChooser}</code>.
   * @return this fixture.
   */
  public JFileChooserFixture click() {
    driver.click(target);
    return this;
  }
  
  /**
   * Simulates a user clicking this fixture's <code>{@link JFileChooser}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public JFileChooserFixture click(MouseButton button) {
    driver.click(target, button);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JFileChooser}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public JFileChooserFixture click(MouseClickInfo mouseClickInfo) {
    driver.click(target, mouseClickInfo.button(), mouseClickInfo.times());
    return this;
  }
  
  /**
   * Simulates a user double-clicking this fixture's <code>{@link JFileChooser}</code>.
   * @return this fixture.
   */
  public JFileChooserFixture doubleClick() {
    driver.doubleClick(target);
    return this;
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JFileChooser}</code>.
   * @return this fixture.
   */
  public JFileChooserFixture rightClick() {
    driver.rightClick(target);
    return this;
  }
  
  /**
   * Returns a fixture that manages the field where the user can enter the name of the file to select in this fixture's 
   * <code>{@link JFileChooser}</code>.
   * @return the created fixture.
   * @throws ComponentLookupException if a matching textToMatch field could not be found.
   */
  public JTextComponentFixture fileNameTextBox() {
    return new JTextComponentFixture(robot, driver.fileNameTextBox(target));
  }
  
  /**
   * Gives input focus to this fixture's <code>{@link JFileChooser}</code>.
   * @return this fixture.
   */
  public JFileChooserFixture focus() {
    driver.focus(target);
    return this;
  }

  /**
   * Simulates a user pressing and releasing the given keys on the <code>{@link JFileChooser}</code> managed by this
   * fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public JFileChooserFixture pressAndReleaseKeys(int... keyCodes) {
    driver.pressAndReleaseKeys(target, keyCodes);
    return this;
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JFileChooser}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public JFileChooserFixture pressKey(int keyCode) {
    driver.pressKey(target, keyCode);
    return this;
  }

  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JFileChooser}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public JFileChooserFixture releaseKey(int keyCode) {
    driver.releaseKey(target, keyCode);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JFileChooser}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JFileChooser</code> is enabled.
   */
  public JFileChooserFixture requireDisabled() {
    driver.requireDisabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JFileChooser}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JFileChooser</code> is disabled.
   */
  public JFileChooserFixture requireEnabled() {
    driver.requireEnabled(target);
    return this;
  }
  
  /**
   * Asserts that this fixture's <code>{@link JFileChooser}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws WaitTimedOutError if this fixture's <code>JFileChooser</code> is never enabled.
   */
  public JFileChooserFixture requireEnabled(Timeout timeout) {
    driver.requireEnabled(target, timeout);
    return this;
  }
  
  /**
   * Asserts that this fixture's <code>{@link JFileChooser}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JFileChooser</code> is visible.
   */
  public JFileChooserFixture requireNotVisible() {
    driver.requireNotVisible(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JFileChooser}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JFileChooser</code> is not visible.
   */
  public JFileChooserFixture requireVisible() {
    driver.requireVisible(target);
    return this;
  }

  /**
   * Selects the given file in this fixture's <code>{@link JFileChooser}</code>.
   * @param file the file to select.
   * @return this fixture.
   * @throws ActionFailedException if this fixture's <code>JFileChooser</code> can select directories only and the
   *         file to select is not a directory.
   * @throws ActionFailedException if this fixture's <code>JFileChooser</code> cannot select directories and the file
   *         to select is a directory.
   */
  public JFileChooserFixture selectFile(final File file) {
    driver.selectFile(target, file);
    return this;
  }
  
  /**
   * Sets the current directory of this fixture's <code>{@link JFileChooser}</code> to the given one.
   * @param dir the directory to set as current.
   * @return this fixture.
   */
  public JFileChooserFixture setCurrentDirectory(final File dir) {
    driver.setCurrentDirectory(target, dir);
    return this;
  }
}
