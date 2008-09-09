/*
 * Created on Aug 21, 2008
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.assertions.Assertions;
import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestWindow;

import static javax.swing.JOptionPane.*;

import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JOptionPaneTitleQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, EDT_ACTION })
public class JOptionPaneTitleQueryTest {

  private Robot robot;
  private JOptionPane optionPane;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    robot.showWindow(window);
    robot.click(window.button);
    pause(100);
    optionPane = robot.finder().findByType(JOptionPane.class);
  }
  
  public void shouldReturnTitleOfJOptionPane() {
    String title = JOptionPaneTitleQuery.titleOf(optionPane);
    Assertions.assertThat(title).isEqualTo("Title");
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JButton button = new JButton("Click Me");

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(JPopupMenuDriverTest.class);
      add(button);
      button.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          showMessageDialog(MyWindow.this, "Hello", "Title", DEFAULT_OPTION);
        }
      });
    }
  }

}
