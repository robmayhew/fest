/*
 * Created on Feb 26, 2008
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

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;

import static javax.swing.JFileChooser.*;

import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.util.Strings.*;

/**
 * Understands simulation of user input on a <code>{@link JFileChooser}</code>. Unlike
 * <code>JFileChooserFixture</code>, this driver only focuses on behavior present only in
 * <code>{@link JFileChooser}</code>s. This class is intended for internal use only.
 * 
 * @author Yvonne Wang 
 * @author Alex Ruiz
 */
public class JFileChooserDriver extends JComponentDriver {

  /**
   * Creates a new </code>{@link JFileChooserDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public JFileChooserDriver(Robot robot) {
    super(robot);
  }

  /**
   * Selects the given file in the <code>{@link JFileChooser}</code>.
   * @param fileChooser the target <code>JFileChooser</code>.
   * @param file the file to select.
   * @throws ActionFailedException if the <code>JFileChooser</code> can select directories only and the file to select 
   *         is not a directory.
   * @throws ActionFailedException if the <code>JFileChooser</code> cannot select directories and the file to select is 
   *         a directory.
   */
  public void selectFile(JFileChooser fileChooser, File file) {
    int mode = fileChooser.getFileSelectionMode();
    boolean isFolder = file.isDirectory();
    if (mode == FILES_ONLY && isFolder) 
      throw cannotSelectFile(file, "the file chooser cannot open directories");
    if (mode == DIRECTORIES_ONLY && !isFolder) 
      throw cannotSelectFile(file, "the file chooser can only open directories");
    robot.invokeAndWait(new SelectFileTask(fileChooser, file));
  }

  private ActionFailedException cannotSelectFile(File file, String reason) {
    throw actionFailure(concat("Unable to select file ", file, ": ", reason));
  }

  private static class SelectFileTask implements Runnable {
    private final JFileChooser fileChooser;
    private final File file;

    SelectFileTask(JFileChooser fileChooser, File file) {
      this.fileChooser = fileChooser;
      this.file = file;
    }

    public void run() {
      fileChooser.setSelectedFile(file);
    }
  }
  
  /**
   * Sets the current directory in the <code>{@link JFileChooser}</code> to the given one.
   * @param fileChooser the target <code>JFileChooser</code>.
   * @param dir the directory to set as current.
   */
  public void setCurrentDirectory(JFileChooser fileChooser, File dir) {
    robot.invokeAndWait(new SetCurrentDirectoryTask(fileChooser, dir));
  }

  private static class SetCurrentDirectoryTask implements Runnable {
    private final JFileChooser fileChooser;
    private final File dir;

    SetCurrentDirectoryTask(JFileChooser fileChooser, File dir) {
      this.fileChooser = fileChooser;
      this.dir = dir;
    }

    public void run() {
      fileChooser.setCurrentDirectory(dir);
    }
  }

  /**
   * Returns the text field where the user can enter the name of the file to select.
   * @param fileChooser the target <code>JFileChooser</code>.
   * @return the found text field.
   * @throws ComponentLookupException if a matching text field could not be found.
   */
  public JTextField fileNameTextBox(JFileChooser fileChooser) {
    return robot.finder().findByType(fileChooser, JTextField.class);
  }

  /**
   * Finds and clicks the "Cancel" button in the given <code>{@link JFileChooser}</code>.
   * @param fileChooser the target <code>JFileChooser</code>.
   * @throws ComponentLookupException if the "Cancel" button cannot be found.
   */
  public void clickCancelButton(JFileChooser fileChooser) {
    clickButton(cancelButton(fileChooser));    
  }

  /**
   * Finds the "Cancel" button in the given <code>{@link JFileChooser}</code>.
   * @param fileChooser the target <code>JFileChooser</code>.
   * @return the found "Cancel" button.
   * @throws ComponentLookupException if the "Cancel" button cannot be found.
   */
  public JButton cancelButton(JFileChooser fileChooser) {
    String buttonText = UIManager.getString("FileChooser.cancelButtonText");
    JButton cancelButton = findButton(fileChooser, buttonText);
    if (cancelButton == null) throw cannotFindButton("Cancel", buttonText);
    return cancelButton;
  }

  /**
   * Finds and clicks the "Approve" button in the given <code>{@link JFileChooser}</code>.
   * @param fileChooser the target <code>JFileChooser</code>.
   * @throws ComponentLookupException if the "Approve" button cannot be found.
   */
  public void clickApproveButton(JFileChooser fileChooser) {
    clickButton(approveButton(fileChooser));
  }

  /**
   * Finds the "Approve" button in the given <code>{@link JFileChooser}</code>.
   * @param fileChooser the target <code>JFileChooser</code>.
   * @return the found "Approve" button.
   * @throws ComponentLookupException if the "Approve" button cannot be found.
   */
  public JButton approveButton(JFileChooser fileChooser) {
    String buttonText = approveButtonText(fileChooser);
    JButton approveButton = findButton(fileChooser, buttonText);
    if (approveButton == null) throw cannotFindButton("Approve", buttonText);
    return approveButton;
  }

  private String approveButtonText(JFileChooser fileChooser) {
    String text = fileChooser.getApproveButtonText();
    if (isEmpty(text)) text = fileChooser.getUI().getApproveButtonText(fileChooser);
    return text;
  }

  private JButton findButton(JFileChooser fileChooser, String text) {
    return robot.finder().find(fileChooser, new JButtonMatcher(text, true));
  }
  
  private ComponentLookupException cannotFindButton(String name, String text) {
    throw new ComponentLookupException(concat(
        "Unable to find ", quote(name), " button with text ", quote(text)));
  }
  
  private void clickButton(JButton button) {
    requireEnabled(button);
    click(button);
  }
}
