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
import javax.swing.UIManager;

import static javax.swing.JFileChooser.DIRECTORIES_ONLY;
import static javax.swing.JFileChooser.FILES_ONLY;
import static javax.swing.JFileChooser.OPEN_DIALOG;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Files.newTemporaryFile;
import static org.fest.util.Files.newTemporaryFolder;
import static org.fest.util.Files.temporaryFolder;
import static org.fest.util.Strings.isEmpty;

import org.fest.swing.testing.ClickRecorder;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JFileChooserFixture}</code>.
 *
 * @author Yvonne Wang
 */
public class JFileChooserFixtureTest extends ComponentFixtureTestCase<JFileChooser> {

  private JFileChooserFixture fixture;

  @Test public void shouldFindCancelButton() {
    JButtonFixture cancelButton = fixture.cancelButton();
    assertThat(cancelButton.target).isNotNull();
    cancelButton.requireText(UIManager.getString("FileChooser.cancelButtonText"));
  }
  
  @Test(dependsOnMethods = "shouldFindCancelButton")
  public void shouldCancelFileSelection() {
    JButton cancelButton = fixture.cancelButton().target;
    ClickRecorder recorder = ClickRecorder.attachTo(cancelButton);
    fixture.cancel();
    assertThat(recorder).wasClicked();
  }
  
  @Test public void shouldSelectFile() {
    File temporaryFile = newTemporaryFile();
    fixture.selectFile(temporaryFile);
    assertThat(fixture.target.getSelectedFile()).isSameAs(temporaryFile);
    temporaryFile.delete();
  }
  
  @Test(dependsOnMethods = "shouldSelectFile", expectedExceptions = AssertionError.class)
  public void shouldFailIfChooserCanOnlySelectFoldersAndFileToSelectIsFile() {
    File temporaryFile = newTemporaryFile();
    fixture.target.setFileSelectionMode(DIRECTORIES_ONLY);
    fixture.selectFile(temporaryFile);
    temporaryFile.delete();
  }
  
  @Test(dependsOnMethods = "shouldSelectFile", expectedExceptions = AssertionError.class)
  public void shouldFailIfChooserCanOnlySelectFilesAndFileToSelectIsFolder() {
    File temporaryFolder = newTemporaryFolder();
    fixture.target.setFileSelectionMode(FILES_ONLY);
    fixture.selectFile(temporaryFolder);
    temporaryFolder.delete();
  }
  
  @Test public void shouldFindApproveButton() {
    JButtonFixture approveButton = fixture.approveButton();
    assertThat(approveButton.target).isNotNull();
    approveButton.requireText(approveButtonText());
  }
  
  @Test(dependsOnMethods = { "shouldSelectFile", "shouldFindApproveButton" })
  public void shouldApproveFileSelection() {
    File temporaryFile = newTemporaryFile();
    fixture.selectFile(temporaryFile);
    JButton approveButton = fixture.approveButton().target;
    ClickRecorder recorder = ClickRecorder.attachTo(approveButton);
    fixture.approve();
    assertThat(recorder).wasClicked();
    temporaryFile.delete();
  }

  private String approveButtonText() {
    JFileChooser fileChooser = fixture.target;
    String text = fileChooser.getApproveButtonText();
    if (!isEmpty(text)) return text;
    return fileChooser.getUI().getApproveButtonText(fileChooser);
  }
  
  @Test public void shouldFindFileNameTextBox() {
    JTextComponentFixture fileNameTextBox = fixture.fileNameTextBox();
    assertThat(fileNameTextBox).isNotNull();
  }
  
  @Test public void shouldSetCurrentDirectory() {
    String homePath = System.getProperty("user.home");
    File userHome = new File(homePath);
    assertThat(userHome.isDirectory()).isTrue();
    assertThat(userHome.getAbsolutePath()).isNotEqualTo(fixture.target.getCurrentDirectory().getAbsolutePath());
    fixture.setCurrentDirectory(userHome);
    assertThat(userHome.getAbsolutePath()).isEqualTo(fixture.target.getCurrentDirectory().getAbsolutePath());
  }
  
  protected JFileChooser createTarget() {
    JFileChooser target = new JFileChooser();
    target.setName("target");
    target.setCurrentDirectory(temporaryFolder());
    target.setDialogType(OPEN_DIALOG);
    return target;
  }

  protected ComponentFixture<JFileChooser> createFixture() {
    fixture = new JFileChooserFixture(robot(), "target");
    return fixture;
  }
}
