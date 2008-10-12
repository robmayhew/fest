/*
 * Created on Oct 11, 2008
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

import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.MethodInvocations;
import org.fest.swing.testing.TestWindow;
import org.fest.swing.testing.MethodInvocations.Args;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.MethodInvocations.Args.args;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JSpinnerSetValueTask}</code>
 *
 * @author Alex Ruiz 
 */
@Test(groups = { GUI, EDT_ACTION })
public class JSpinnerSetValueTaskTest {

  private Robot robot;
  private MySpinner spinner;

  @BeforeMethod public final void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    spinner = window.spinner;
    robot.showWindow(window);
  }

  @AfterMethod public final void tearDown() {
    robot.cleanUp();
  }

  public void shouldSetValue() {
    String value = "Three";
    spinner.startRecording();
    JSpinnerSetValueTask.setValue(spinner, value);
    robot.waitForIdle();
    assertThat(JSpinnerValueQuery.valueOf(spinner)).isEqualTo(value);
    spinner.requireInvoked("setValue", args(value));
  }
  
  final Robot robot() { return robot; }
  final JSpinner spinner() { return spinner; }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    public static MyWindow createNew() {
      return new MyWindow();
    }

    final MySpinner spinner = new MySpinner("One", "Two", "Three");

    private MyWindow() {
      super(JSpinnerSetValueTask.class);
      spinner.setValue("Two");
      addComponents(spinner);
    }
  }
  
  private static class MySpinner extends JSpinner {
    private static final long serialVersionUID = 1L;

    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();
    
    MySpinner(Object...values) {
      super(new SpinnerListModel(values));
    }

    @Override public void setValue(Object value) {
      if (recording) methodInvocations.invoked("setValue", args(value));
      super.setValue(value);
    }
    
    void startRecording() { recording = true; }

    MethodInvocations requireInvoked(String methodName, Args args) {
      return methodInvocations.requireInvoked(methodName, args);
    }
  }
}
