/*
 * Created on Sep 1, 2008
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
package org.fest.swing.task;

import javax.swing.JButton;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Condition;
import org.fest.swing.core.ScreenLock;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.query.ComponentHasFocusQuery.hasFocus;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link ComponentRequestFocusTask}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class ComponentRequestFocusTaskTest {

  private MyWindow window;

  @BeforeMethod public void setUp() {
    ScreenLock.instance().acquire(this);
    window = MyWindow.createNew();
    window.display();
  }

  @AfterMethod public void tearDown() {
    window.destroy();
    ScreenLock.instance().release(this);
  }

  public void shouldCallRequestFocusInWindow() {
    final JButton toReceiveFocus = window.buttonTwo;
    assertThat(hasFocus(toReceiveFocus)).isFalse();
    ComponentRequestFocusTask.giveFocusTo(toReceiveFocus);
    pause(new Condition("Component gets focus") {
      public boolean test() {
        return hasFocus(toReceiveFocus);
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }

    final JButton buttonOne = new JButton("One");
    final JButton buttonTwo = new JButton("Two");

    private MyWindow() {
      super(ComponentRequestFocusTaskTest.class);
      addComponents(buttonOne, buttonTwo);
    }
  }
}
