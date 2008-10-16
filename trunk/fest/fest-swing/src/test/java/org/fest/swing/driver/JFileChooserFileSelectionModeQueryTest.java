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

import javax.swing.JFileChooser;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.testing.MethodInvocations;
import org.fest.swing.testing.TestWindow;

import static javax.swing.JFileChooser.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JFileChooserFileSelectionModeQuery}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = { GUI, EDT_ACTION })
public class JFileChooserFileSelectionModeQueryTest {

  private Robot robot;
  private MyFileChooser fileChooser;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    fileChooser = window.fileChooser;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(dataProvider = "selectionModes", groups = { GUI, EDT_ACTION })
  public void shouldReturnApproveButtonTextFromJFileChooser(final int mode) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        fileChooser.setFileSelectionMode(mode);
      }
    });
    robot.waitForIdle();
    fileChooser.startRecording();
    int actual = JFileChooserFileSelectionModeQuery.fileSelectionModeOf(fileChooser);
    assertThat(actual).isEqualTo(mode);
    fileChooser.requireInvoked("getFileSelectionMode");
  }

  @DataProvider(name = "selectionModes") public Object[][] selectionModes() {
    return new Object[][] { { FILES_ONLY }, { DIRECTORIES_ONLY }, { FILES_AND_DIRECTORIES } };
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final MyFileChooser fileChooser = new MyFileChooser();

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(JFileChooserFileSelectionModeQueryTest.class);
      addComponents(fileChooser);
    }
  }

  private static class MyFileChooser extends JFileChooser {
    private static final long serialVersionUID = 1L;

    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    void startRecording() { recording = true; }

    @Override public int getFileSelectionMode() {
      if (recording) methodInvocations.invoked("getFileSelectionMode");
      return super.getFileSelectionMode();
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
