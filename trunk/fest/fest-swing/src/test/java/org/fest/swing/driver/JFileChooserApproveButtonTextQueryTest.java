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
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.BooleanProvider;
import org.fest.swing.testing.MethodInvocations;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JFileChooserApproveButtonTextQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class JFileChooserApproveButtonTextQueryTest {

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

  @Test(dataProvider = "booleans", dataProviderClass = BooleanProvider.class, groups = { GUI, EDT_ACTION })
  public void shouldReturnApproveButtonText(boolean nullApproveButtonText) {
    fileChooser.shouldReturnNullApproveButtonText(nullApproveButtonText);
    fileChooser.startRecording();
    String approveButtonText = JFileChooserApproveButtonTextQuery.approveButtonTextFrom(fileChooser);
    assertThat(approveButtonText).isNotEmpty();
    fileChooser.requireInvoked("getApproveButtonText");
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final MyFileChooser fileChooser = new MyFileChooser();

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(JFileChooserApproveButtonTextQueryTest.class);
      addComponents(fileChooser);
    }
  }

  private static class MyFileChooser extends JFileChooser {
    private static final long serialVersionUID = 1L;

    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    private boolean shouldReturnNullApproveButtonText;

    void shouldReturnNullApproveButtonText(boolean value) {
      shouldReturnNullApproveButtonText = value;
    }

    void startRecording() { recording = true; }

    @Override public String getApproveButtonText() {
      if (recording) methodInvocations.invoked("getApproveButtonText");
      if (shouldReturnNullApproveButtonText) return null;
      return super.getApproveButtonText();
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
