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

import javax.swing.JComboBox;

import org.testng.annotations.*;

import org.fest.swing.core.Robot;
import org.fest.swing.edt.CheckThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.testing.TestGroups.*;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JComboBoxEditorAccessibleQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class JComboBoxEditorAccessibleQueryTest {

  private Robot robot;
  private MyWindow window;
  private JComboBox comboBox;

  @BeforeClass public void setUpOnce() {
    CheckThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = MyWindow.createNew();
    comboBox = window.comboBox;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(dataProvider = "accessible", groups = { GUI, EDT_ACTION })
  public void shouldReturnIndicateIfJComboBoxEditorIsAccessible(boolean editable, boolean enabled) {
    setEditableAndEnabled(comboBox, editable, enabled);
    robot.waitForIdle();
    boolean accessible = editable && enabled;
    assertThat(isEditorAccessible()).isEqualTo(accessible);
  }

  private boolean isEditorAccessible() {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return JComboBoxEditorAccessibleQuery.isEditorAccessible(comboBox);
      }
    });
  }

  @DataProvider(name = "accessible") public Object[][] accessible() {
    return new Object[][] {
        { true , true  },
        { true , false },
        { false, true  },
        { false, false },
    };
  }
  
  private static void setEditableAndEnabled(final JComboBox comboBox, final boolean editable, final boolean enabled) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        comboBox.setEditable(editable);
        comboBox.setEnabled(enabled);
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JComboBox comboBox = new JComboBox(array("first", "second", "third"));

    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JComboBoxEditorAccessibleQueryTest.class);
      add(comboBox);
    }
  }
}
