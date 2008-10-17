/*
 * Created on Aug 6, 2008
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
package org.fest.swing.query;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.EDT_ACTION;
import static org.fest.swing.testing.TestGroups.GUI;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.MethodInvocations;
import org.fest.swing.testing.TestDialog;
import org.fest.swing.testing.TestWindow;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link DialogTitleQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class DialogTitleQueryTest {

  private Robot robot;
  private MyDialog dialog;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    dialog = MyDialog.createNew();
    robot.showWindow(dialog);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnTitleOfDialog() {
    dialog.startRecording();
    assertThat(DialogTitleQuery.titleOf(dialog)).isEqualTo("Hello World");
    dialog.requireInvoked("getTitle");
  }

  private static class MyDialog extends TestDialog {
    private static final long serialVersionUID = 1L;

    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    static MyDialog createNew() {
      return new MyDialog();
    }

    private MyDialog() {
      super(TestWindow.createNew(DialogTitleQueryTest.class));
      setTitle("Hello World");
    }

    @Override public String getTitle() {
      if (recording) methodInvocations.invoked("getTitle");
      return super.getTitle();
    }

    void startRecording() { recording = true; }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
