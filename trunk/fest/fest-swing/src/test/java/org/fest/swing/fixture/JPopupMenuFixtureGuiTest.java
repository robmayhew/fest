/*
 * Created on Jul 18, 2008
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
package org.fest.swing.fixture;

import java.awt.Dimension;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.ClickRecorder;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.ClickRecorder.attachTo;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link JPopupMenuFixture}</code>
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI) public class JPopupMenuFixtureGuiTest {

  private Robot robot;
  private MyWindow window;
  private JPopupMenuFixture fixture;
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = MyWindow.createNew();
    robot.showWindow(window, new Dimension(200, 200));
    JTextComponentFixture textBox = new JTextComponentFixture(robot, "textField");
    fixture = textBox.showPopupMenu();
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  public void shouldFindFirstLevelMenuItemByPath() {
    ClickRecorder recorder = attachTo(window.fileMenu);
    JMenuItemFixture menuItem = fixture.menuItemWithPath("File");
    menuItem.click();
    assertThat(recorder).clicked(LEFT_BUTTON).timesClicked(1);
  }
  
  public void shouldFindSecondLevelMenuItemByPath() {
    ClickRecorder recorder = attachTo(window.openMenu);
    JMenuItemFixture menuItem = fixture.menuItemWithPath("File", "Open");
    menuItem.click();
    assertThat(recorder).clicked(LEFT_BUTTON).timesClicked(1);
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }
    
    final JPopupMenu popupMenu = new JPopupMenu();
    final JMenu fileMenu = new JMenu("File");
    final JMenuItem openMenu = new JMenuItem("Open");
    
    private MyWindow() {
      super(JPopupMenuFixtureGuiTest.class);
      JTextField textField = new JTextField(20);
      textField.setName("textField");
      textField.setComponentPopupMenu(popupMenu);
      add(textField);
      popupMenu.add(fileMenu);
      fileMenu.add(openMenu);
    }
  }
}
