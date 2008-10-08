/*
 * Created on Jul 8, 2008
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

import javax.swing.*;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.EventMode;
import org.fest.swing.core.EventModeProvider;
import org.fest.swing.core.Robot;
import org.fest.swing.testing.ClickRecorder;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.factory.JFrames.frame;
import static org.fest.swing.testing.ClickRecorder.attachTo;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for bug <a href="http://code.google.com/p/fest/issues/detail?id=159" target="_blank">159</a>.
 *
 * @author Alex Ruiz
 */
public class MoveParentToFrontWhenClickingMenuTest {

  private static final int DELAY_BEFORE_SHOWING_MENU = 2000;

  private Robot robot;
  private JFrame frameToFocus;
  private MyWindow window;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = MyWindow.createNew();
    window.display();
    frameToFocus = frame().withTitle("To Focus").createNew();
    robot.showWindow(frameToFocus, new Dimension(300, 200));
    robot.focus(frameToFocus);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(groups = { GUI, BUG }, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSelectMenuFromMenuBar(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    JMenuItem menuItem = window.menuItemFromMenuBar;
    JMenuItemFixture fixture = fixtureFor(menuItem);
    pause(DELAY_BEFORE_SHOWING_MENU);
    ClickRecorder clickRecorder = attachTo(menuItem);
    fixture.click();
    assertThat(clickRecorder).wasClicked();
  }

  @Test(groups = { GUI, BUG }, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSelectMenuPopupMenu(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    JMenuItem menuItem = window.menuItemFromPopupMenu;
    JMenuItemFixture fixture = fixtureFor(menuItem);
    pause(DELAY_BEFORE_SHOWING_MENU);
    robot.showPopupMenu(window.textField);
    ClickRecorder clickRecorder = attachTo(menuItem);
    fixture.click();
    assertThat(clickRecorder).wasClicked();
  }

  private JMenuItemFixture fixtureFor(JMenuItem menuItem) {
    return new JMenuItemFixture(robot, menuItem);
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }

    final JMenuItem menuItemFromMenuBar = new JMenuItem("New");
    final JMenuItem menuItemFromPopupMenu = new JMenuItem("Cut");
    final JTextField textField;

    private MyWindow() {
      super(MoveParentToFrontWhenClickingMenuTest.class);
      setJMenuBar(new JMenuBar());
      JMenu menuFile = new JMenu("File");
      menuFile.add(menuItemFromMenuBar);
      getJMenuBar().add(menuFile);
      setPreferredSize(new Dimension(200, 100));
      textField = new JTextField(20);
      textField.setComponentPopupMenu(popupMenu());
      add(textField);
    }

    private JPopupMenu popupMenu() {
      JPopupMenu popupMenu = new JPopupMenu();
      JMenu menuEdit = new JMenu("Edit");
      menuEdit.add(menuItemFromPopupMenu);
      popupMenu.add(menuEdit);
      popupMenu.setName("popupMenu");
      return popupMenu;
    }
  }
}
