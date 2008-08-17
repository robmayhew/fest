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

import java.awt.Dimension;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GuiQuery;
import org.fest.swing.core.GuiTask;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.testing.TestWindow;

import static javax.swing.JFileChooser.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.query.AbstractButtonTextQuery.textOf;
import static org.fest.swing.testing.TestGroups.GUI;
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
  private JFileChooser fileChooser;
  private JFileChooserDriver driver;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JFileChooserDriver(robot);
    MyWindow window = MyWindow.newWindow();
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

  private static File selectedFileIn(final JFileChooser fileChooser) {
    return execute(new GuiQuery<File>() {
      protected File executeInEDT() {
        return fileChooser.getSelectedFile();
      }
    });
  }

  public void shouldThrowErrorIfChooserCanOnlySelectFoldersAndFileToSelectIsFile() {
    File temporaryFile = newTemporaryFile();
    setFileSelectionMode(fileChooser, DIRECTORIES_ONLY);
    try {
      driver.selectFile(fileChooser, temporaryFile);
      fail();
    } catch (ActionFailedException expected) {
      assertThat(expected).message().contains("the file chooser can only open directories");
    } finally {
      temporaryFile.delete();
    }
  }

  public void shouldThrowErrorIfChooserCanOnlySelectFilesAndFileToSelectIsFolder() {
    File temporaryFolder = newTemporaryFolder();
    setFileSelectionMode(fileChooser, FILES_ONLY);
    try {
      driver.selectFile(fileChooser, temporaryFolder);
      fail();
    } catch (ActionFailedException expected) {
      assertThat(expected).message().contains("the file chooser cannot open directories");
    } finally {
      temporaryFolder.delete();
    }
  }

  private static void setFileSelectionMode(final JFileChooser fileChooser, final int mode) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        fileChooser.setFileSelectionMode(mode);
      }
    });
  }

  public void shouldFindApproveButton() {
    JButton approveButton = driver.approveButton(fileChooser);
    assertThat(approveButton).isNotNull();
    assertThat(textOf(approveButton)).isEqualTo(approveButtonText(fileChooser));
  }

  private static String approveButtonText(final JFileChooser fileChooser) {
    return execute(new GuiQuery<String>() {
      protected String executeInEDT() {
        String text = fileChooser.getApproveButtonText();
        if (!isEmpty(text)) return text;
        return fileChooser.getUI().getApproveButtonText(fileChooser);
      }
    });
  }

  public void shouldFindFileNameTextBox() {
    JTextField fileNameTextBox = driver.fileNameTextBox(fileChooser);
    assertThat(fileNameTextBox).isNotNull();
  }

  public void shouldSetCurrentDirectory() {
    String homePath = System.getProperty("user.home");
    File userHome = new File(homePath);
    assertThat(userHome.isDirectory()).isTrue();
    String userHomeAbsolutePath = userHome.getAbsolutePath();
    driver.setCurrentDirectory(fileChooser, userHome);
    assertThat(userHomeAbsolutePath).isEqualTo(currentDirectoryAbsolutePath(fileChooser));
  }

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

    static MyWindow newWindow() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() { return new MyWindow(); }
      });
    }
    
    MyWindow() {
      super(JFileChooserDriverTest.class);
      fileChooser.setCurrentDirectory(temporaryFolder());
      fileChooser.setDialogType(OPEN_DIALOG);
      add(fileChooser);
      setPreferredSize(new Dimension(600, 600));
    }
  }
}
