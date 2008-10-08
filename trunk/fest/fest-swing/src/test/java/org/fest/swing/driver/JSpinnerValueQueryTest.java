/*
 * Created on Aug 9, 2008
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JSpinnerValueQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class JSpinnerValueQueryTest {

  private Robot robot;
  private MySpinner spinner;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    spinner = window.spinner;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnValueOfJSpinner() {
    assertThat(JSpinnerValueQuery.valueOf(spinner)).isEqualTo("Two");
    assertThat(spinner.methodGetValueWasInvoked()).isTrue();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final MySpinner spinner = new MySpinner("One", "Two");

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(JSpinnerValueQueryTest.class);
      spinner.setValue("Two");
      addComponents(spinner);
    }
  }

  private static class MySpinner extends JSpinner {
    private static final long serialVersionUID = 1L;

    private boolean methodGetValueInvoked;

    public MySpinner(Object...values) {
      super(new SpinnerListModel(values));
    }

    @Override public Object getValue() {
      methodGetValueInvoked = true;
      return super.getValue();
    }

    boolean methodGetValueWasInvoked() { return methodGetValueInvoked; }
  }
}
