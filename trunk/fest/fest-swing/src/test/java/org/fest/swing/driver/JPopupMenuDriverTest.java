/*
 * Created on Sep 5, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.driver;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.testing.TestFrame;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link JPopupMenuDriver}</code>.
 *
 * @author Alex Ruiz
 */
public class JPopupMenuDriverTest {

  private RobotFixture robot;
  private JPopupMenuDriver driver;
  private MyFrame frame;
  
  @BeforeMethod public void setUp() {
    robot = RobotFixture.robotWithCurrentAwtHierarchy();
    frame = new MyFrame(getClass());
    robot.showWindow(frame);
    assertThat(frame.isVisible()).isTrue();
    driver = new JPopupMenuDriver(robot);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  @Test public void shouldShowPopupMenuInTextFieldContainingPopupMenu() {
    JPopupMenu menu = driver.showPopupMenu(frame.textBoxWithPopupMenu);
    assertThat(menu).isSameAs(frame.popupMenu);
  }

  @Test(expectedExceptions = ComponentLookupException.class)
  public void shouldNotShowPopupMenuInTextFieldNotContainingPopupMenu() {
    driver.showPopupMenu(frame.textBox);
  }

  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    private final JPopupMenu popupMenu = new JPopupMenu("Popup Menu");
    private final JTextField textBoxWithPopupMenu = new JTextField(20);
    private final JTextField textBox = new JTextField(20);
    
    MyFrame(Class<?> testClass) {
      super(testClass);
      add(textBoxWithPopupMenu);
      add(textBox);
      textBoxWithPopupMenu.setComponentPopupMenu(popupMenu);
      popupMenu.add(new JMenuItem("First"));
      popupMenu.add(new JMenuItem("Second"));
    }
  }
}
