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
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.swing.driver;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;
import org.fest.swing.timing.Condition;

import static javax.swing.JFileChooser.*;
import static javax.swing.SwingUtilities.invokeLater;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.AbstractButtonTextQuery.textOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.*;
import static org.fest.swing.test.core.TestGroups.GUI;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;
import static org.fest.swing.test.task.ComponentSetVisibleTask.hide;
import static org.fest.swing.timing.Pause.pause;
import static org.fest.util.Files.*;
import static org.fest.util.Strings.isEmpty;

/**
 * Tests for <code>{@link JFileChooserDriver}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JFileChooserDriverTest {

  private Robot robot;
  private JFileChooserDriver driver;
  private MyWindow window;
  private JFileChooser fileChooser;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JFileChooserDriver(robot);
    window = MyWindow.createNew();
    fileChooser = window.fileChooser;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldFindCancelButton() {
    JButton cancelButton = driver.cancelButton(fileChooser);
    assertThat(cancelButton).isNotNull();
    assertThat(textOf(cancelButton)).isEqualTo(cancelButtonText());
  }

  private String cancelButtonText() {
    return UIManager.getString("FileChooser.cancelButtonText");
  }

  public void shouldSelectFile() {
    File temporaryFile = newTemporaryFile();
    driver.selectFile(fileChooser, temporaryFile);
    File selectedFile = selectedFileIn(fileChooser);
    assertThat(selectedFile).isSameAs(temporaryFile);
    temporaryFile.delete();
  }

  @RunsInEDT
  private static File selectedFileIn(final JFileChooser fileChooser) {
    return execute(new GuiQuery<File>() {
      protected File executeInEDT() {
        return fileChooser.getSelectedFile();
      }
    });
  }

  public void shouldThrowErrorWhenSelectingFileInDisabledJFileChooser() {
    File temporaryFile = newTemporaryFile();
    disableFileChooser();
    try {
      driver.selectFile(fileChooser, temporaryFile);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    } finally {
      temporaryFile.delete();
    }
  }
  
  public void shouldThrowErrorIfFileToSelectIsNull() {
    try {
      driver.selectFile(fileChooser, null);
      failWhenExpectingException();
    } catch (NullPointerException e) {
      assertThat(e.getMessage()).isEqualTo("The file to select should not be null");
    }
  }

  public void shouldThrowErrorWhenSelectingFileInNotShowingJFileChooser() {
    File temporaryFile = newTemporaryFile();
    hideWindow();
    try {
      driver.selectFile(fileChooser, temporaryFile);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    } finally {
      temporaryFile.delete();
    }
  }

  public void shouldThrowErrorIfJFileChooserCanOnlySelectFoldersAndFileToSelectIsFile() {
    File temporaryFile = newTemporaryFile();
    setFileSelectionMode(fileChooser, DIRECTORIES_ONLY);
    try {
      driver.selectFile(fileChooser, temporaryFile);
      failWhenExpectingException();
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).contains("the file chooser can only open directories");
    } finally {
      temporaryFile.delete();
    }
  }

  public void shouldThrowErrorIfJFileChooserCanOnlySelectFilesAndFileToSelectIsFolder() {
    File temporaryFolder = newTemporaryFolder();
    setFileSelectionMode(fileChooser, FILES_ONLY);
    try {
      driver.selectFile(fileChooser, temporaryFolder);
      failWhenExpectingException();
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).contains("the file chooser cannot open directories");
    } finally {
      temporaryFolder.delete();
    }
  }

  private static void setFileSelectionMode(final JFileChooser fileChooser, final int mode) {
    invokeLater(new Runnable() {
      public void run() {
        fileChooser.setFileSelectionMode(mode);       
      }
    });
    pause(new Condition("JFileChooser's selection mode is set") {
      public boolean test() {
        return fileChooser.getFileSelectionMode() == mode;
      }
    }); 
  }

  public void shouldThrowErrorIfFilesToSelectIsNull() {
    try {
      driver.selectFiles(fileChooser, null);
      failWhenExpectingException();
    } catch (NullPointerException e) {
      assertThat(e.getMessage()).isEqualTo("The files to select should not be null");
    }
  }

  public void shouldThrowErrorIfFilesToSelectIsEmpty() {
    try {
      driver.selectFiles(fileChooser, null);
      failWhenExpectingException();
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).isEqualTo("The array of files to select should not be empty");
    }
  }

  public void shouldFindApproveButton() {
    JButton approveButton = driver.approveButton(fileChooser);
    assertThat(approveButton).isNotNull();
    assertThat(textOf(approveButton)).isEqualTo(approveButtonText(fileChooser));
  }

  private static String approveButtonText(final JFileChooser fileChooser) {
    String text = fileChooser.getApproveButtonText();
    if (!isEmpty(text)) return text;
    return fileChooser.getUI().getApproveButtonText(fileChooser);
  }

  public void shouldFindFileNameTextBox() {
    JTextField fileNameTextBox = driver.fileNameTextBox(fileChooser);
    assertThat(fileNameTextBox).isNotNull();
  }

  public void shouldSetCurrentDirectory() {
    File userHome = userHomeDirectory();
    driver.setCurrentDirectory(fileChooser, userHome);
    assertThat(currentDirectoryAbsolutePath(fileChooser)).isEqualTo(userHome.getAbsolutePath());
  }

  public void shouldThrowErrorWhenSettingCurrentDirectoryInDisabledJFileChooser() {
    disableFileChooser();
    try {
      driver.setCurrentDirectory(fileChooser, userHomeDirectory());
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  @RunsInEDT
  private void disableFileChooser() {
    disable(fileChooser);
    robot.waitForIdle();
  }
  
  public void shouldThrowErrorWhenSettingCurrentDirectoryInNotShowingJFileChooser() {
    hideWindow();
    try {
      driver.setCurrentDirectory(fileChooser, userHomeDirectory());
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToNotShowingComponent(e);
    }
  }
  
  private File userHomeDirectory() {
    String homePath = System.getProperty("user.home");
    File userHome = new File(homePath);
    assertThat(userHome.isDirectory()).isTrue();
    return userHome;
  }

  @RunsInEDT
  private void hideWindow() {
    hide(window);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static String currentDirectoryAbsolutePath(final JFileChooser fileChooser) {
    File currentDirectory = execute(new GuiQuery<File>() {
      protected File executeInEDT() {
        return fileChooser.getCurrentDirectory();
      }
    });
    return currentDirectory.getAbsolutePath();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JFileChooser fileChooser = new JFileChooser();

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }
    
    private MyWindow() {
      super(JFileChooserDriverTest.class);
      fileChooser.setCurrentDirectory(temporaryFolder());
      fileChooser.setDialogType(OPEN_DIALOG);
      addComponents(fileChooser);
    }
  }
}
