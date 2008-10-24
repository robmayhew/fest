/*
 * Created on Sep 23, 2008
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
import org.fest.swing.testing.TestWindow;

import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.*;
import static org.fest.util.Arrays.array;

/**
 * Understands a template for test cases for implementations of <code>{@link JSpinnerSetValueTaskTemplate}</code>.
 *
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public abstract class JSpinnerSetValueTaskTestCase {

  private Robot robot;
  private JSpinner spinner;

  @BeforeMethod public final void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew(getClass());
    spinner = window.spinner;
    robot.showWindow(window);
  }

  @AfterMethod public final void tearDown() {
    robot.cleanUp();
  }

  final Robot robot() { return robot; }
  final JSpinner spinner() { return spinner; }

  static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    public static MyWindow createNew(Class<? extends JSpinnerSetValueTaskTestCase> testClass) {
      return new MyWindow(testClass);
    }

    final JSpinner spinner = new JSpinner(new SpinnerListModel(array("One", "Two", "Three")));

    private MyWindow(Class<? extends JSpinnerSetValueTaskTestCase> testClass) {
      super(testClass);
      spinner.setValue("Two");
      addComponents(spinner);
    }
  }
}
