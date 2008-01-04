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

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.UIManager;

import static javax.swing.JFileChooser.*;

import static org.fest.swing.exception.ActionFailedException.actionFailure;

import static org.fest.util.Strings.*;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.core.Timeout;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;

/**
 * Understands simulation of user events on a <code>{@link JFileChooser}</code> and verification of the state of such
 * <code>{@link JFileChooser}</code>.
 *
 *
 * @author Yvonne Wang 
 */
public class JFileChooserFixture extends ComponentFixture<JFileChooser> {

  /**
   * Creates a new <code>{@link JFileChooserFixture}</code>.
   * @param robot performs simulation of user events on a <code>JFileChooser</code>.
   * @throws ComponentLookupException if a matching <code>JFileChooser</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JFileChooser</code> is found.
   */
  public JFileChooserFixture(RobotFixture robot) {
    super(robot, JFileChooser.class);
  }
  
  /**
   * Creates a new <code>{@link JFileChooserFixture}</code>.
   * @param robot performs simulation of user events on a <code>JFileChooser</code>.
   * @param labelName the name of the <code>JFileChooser</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JFileChooser</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JFileChooser</code> is found.
   */
  public JFileChooserFixture(RobotFixture robot, String labelName) {
    super(robot, labelName, JFileChooser.class);
  }
  
  /**
   * Creates a new <code>{@link JFileChooserFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JFileChooser</code>.
   * @param target the <code>JFileChooser</code> to be managed by this fixture.
   */
  public JFileChooserFixture(RobotFixture robot, JFileChooser target) {
    super(robot, target);
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
  public final JFileChooserFixture selectFile(final File file) {
    int mode = target.getFileSelectionMode();
    boolean isFolder = file.isDirectory();
    if (mode == FILES_ONLY && isFolder) throw actionFailure("The file chooser cannot open directories");
    if (mode == DIRECTORIES_ONLY && !isFolder) throw actionFailure("The file chooser can only open directories");
    robot.invokeAndWait(new Runnable() {
      public void run() {
        target.setSelectedFile(file);
      }
    });
    return this;
  }

  /**
   * Sets the current directory of this fixture's <code>{@link JFileChooser}</code> to the given one.
   * @param dir the directory to set as current.
   * @return this fixture.
   */
  public final JFileChooserFixture setCurrentDirectory(final File dir) {
    robot.invokeAndWait(null, new Runnable() {
      public void run() {
        target.setCurrentDirectory(dir);
      }
    });
    return this;
  }

  /**
   * Returns a fixture that manages the textToMatch field where the user can enter the name of the file to select in the
   * <code>{@link JFileChooser}</code> managed by this fixture.
   * @return the created fixture.
   * @throws ComponentLookupException if a matching textToMatch field could not be found.
   */
  public final JTextComponentFixture fileNameTextBox() {
    return new JTextComponentFixture(robot, robot.finder().findByType(target, JTextField.class));
  }

  /**
   * Simulates a user pressing the "Cancel" button in this fixture's <code>{@link JFileChooser}</code>.
   * @throws ComponentLookupException if the "Cancel" button cannot be found.
   * @throws AssertionError if the "Cancel" button is disabled.
   */
  public final void cancel() {
    cancelButton().requireEnabled().click();
  }
  
  /**
   * Finds the "Cancel" button in this fixture's <code>{@link JFileChooser}</code>.
   * @return the found "Cancel" button.
   * @throws ComponentLookupException if the "Cancel" button cannot be found.
   */
  public final JButtonFixture cancelButton() {
    String buttonText = UIManager.getString("FileChooser.cancelButtonText");
    JButton cancelButton = findButton(buttonText);
    if (cancelButton == null) throw cannotFindButton("Cancel", buttonText);
    return new JButtonFixture(robot, cancelButton);
  }

  /**
   * Simulates a user pressing the "Approve" button in this fixture's <code>{@link JFileChooser}</code>.
   * @throws ComponentLookupException if the "Approve" button cannot be found.
   * @throws AssertionError if the "Approve" button is disabled.
   */
  public final void approve() {
    approveButton().requireEnabled().click();
  }
  
  /**
   * Finds the "Approve" button in this fixture's <code>{@link JFileChooser}</code>.
   * @return the found "Approve" button.
   * @throws ComponentLookupException if the "Approve" button cannot be found.
   */
  public final JButtonFixture approveButton() {
    String buttonText = approveButtonText();
    JButton approveButton = findButton(buttonText);
    if (approveButton == null) throw cannotFindButton("Approve", buttonText);
    return new JButtonFixture(robot, approveButton);
  }

  private String approveButtonText() {
    String text = target.getApproveButtonText();
    if (isEmpty(text)) text = target.getUI().getApproveButtonText(target);
    return text;
  }

  private JButton findButton(String text) {
    return robot.finder().find(target, new JButtonMatcher(text));
  }
  
  private ComponentLookupException cannotFindButton(String name, String text) {
    throw new ComponentLookupException(concat("Unable to find ", quote(name), " button with textToMatch ", quote(text)));
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JFileChooser}</code>.
   * @return this fixture.
   */
  public final JFileChooserFixture click() {
    return (JFileChooserFixture)doClick();
  }
  
  /**
   * Simulates a user clicking this fixture's <code>{@link JFileChooser}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public final JFileChooserFixture click(MouseButton button) {
    return (JFileChooserFixture)doClick(button);
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JFileChooser}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JFileChooserFixture click(MouseClickInfo mouseClickInfo) {
    return (JFileChooserFixture)doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JFileChooser}</code>.
   * @return this fixture.
   */
  public final JFileChooserFixture rightClick() {
    return (JFileChooserFixture)doRightClick();
  }

  /**
   * Simulates a user doble-clicking this fixture's <code>{@link JFileChooser}</code>.
   * @return this fixture.
   */
  public final JFileChooserFixture doubleClick() {
    return (JFileChooserFixture)doDoubleClick();
  }

  /**
   * Gives input focus to this fixture's <code>{@link JFileChooser}</code>.
   * @return this fixture.
   */
  public final JFileChooserFixture focus() {
    return (JFileChooserFixture)doFocus();
  }

  /**
   * Simulates a user pressing and releasing the given keys on the <code>{@link JFileChooser}</code> managed by this
   * fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JFileChooserFixture pressAndReleaseKeys(int... keyCodes) {
    return (JFileChooserFixture)doPressAndReleaseKeys(keyCodes);
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JFileChooser}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JFileChooserFixture pressKey(int keyCode) {
    return (JFileChooserFixture)doPressKey(keyCode);
  }
  
  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JFileChooser}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JFileChooserFixture releaseKey(int keyCode) {
    return (JFileChooserFixture)doReleaseKey(keyCode);
  }
  
  /**
   * Asserts that this fixture's <code>{@link JFileChooser}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JFileChooser</code> is not visible.
   */
  public final JFileChooserFixture requireVisible() {
    return (JFileChooserFixture)assertVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JFileChooser}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JFileChooser</code> is visible.
   */
  public final JFileChooserFixture requireNotVisible() {
    return (JFileChooserFixture)assertNotVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JFileChooser}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JFileChooser</code> is disabled.
   */
  public final JFileChooserFixture requireEnabled() {
    return (JFileChooserFixture)assertEnabled();
  }
  
  /**
   * Asserts that this fixture's <code>{@link JFileChooser}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws WaitTimedOutError if this fixture's <code>JFileChooser</code> is never enabled.
   */
  public final JFileChooserFixture requireEnabled(Timeout timeout) {
    return (JFileChooserFixture)assertEnabled(timeout);
  }
  
  /**
   * Asserts that this fixture's <code>{@link JFileChooser}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JFileChooser</code> is enabled.
   */
  public final JFileChooserFixture requireDisabled() {
    return (JFileChooserFixture)assertDisabled();
  }
}
