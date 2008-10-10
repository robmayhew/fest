/*
 * Created on Aug 8, 2008
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

import javax.swing.JFileChooser;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GuiQuery;
import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.*;
import static org.fest.util.Files.*;

/**
 * Tests for <code>{@link JFileChooserSelectFileTask}</code>.
 *
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class JFileChooserSelectFileTaskTest {

  private Robot robot;
  private JFileChooser fileChooser;
  private File fileToSelect;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    fileToSelect = newTemporaryFile();
    MyWindow window = MyWindow.createNew();
    fileChooser = window.fileChooser;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
    fileToSelect.delete();
  }

  public void shouldSelectFile() {
    JFileChooserSelectFileTask.setSelectedFile(fileChooser, fileToSelect);
    robot.waitForIdle();
    assertThat(selectedFile()).isEqualTo(fileToSelect);
  }

  private File selectedFile() {
    return selectedFileOf(fileChooser);
  }

  private static File selectedFileOf(final JFileChooser fileChooser) {
    return execute(new GuiQuery<File>() {
      protected File executeInEDT() {
        return fileChooser.getSelectedFile();
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }

    final JFileChooser fileChooser = new JFileChooser(temporaryFolder());

    private MyWindow() {
      super(JFileChooserSelectFileTask.class);
      add(fileChooser);
    }
  }
}
