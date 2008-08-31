/*
 * Created on Jun 15, 2008
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GuiQuery;
import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.core.RobotFixture.robotWithCurrentAwtHierarchy;
import static org.fest.swing.finder.WindowFinder.findFrame;

/**
 * Test for <a href="http://code.google.com/p/fest/issues/detail?id=157" target="_blank">issue 157</a>.
 * 
 * @author Andriy Tsykholyas
 * @author Yvonne Wang
 */
public class MacOsxMenuBarTest {
  
  private Robot robot;
  private JMenuItemFixture menuItemFixture;
  
  @BeforeMethod public void setUp() {
    System.setProperty("apple.laf.useScreenMenuBar", "true");
    robot = robotWithCurrentAwtHierarchy();
    robot.showWindow(MyWindow.createInEDT());
    FrameFixture frameFixture = findFrame("myWindow").withTimeout(2000).using(robot);
    menuItemFixture = frameFixture.menuItem("menuItem");
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  @Test public void shouldSelectMenu() {
    final boolean[] selected = new boolean[1];
    JMenuItem menu = menuItemFixture.target;
    menu.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        selected[0] = true;
      }
    });
    menuItemFixture.click();
    assertThat(selected[0]).isTrue();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;
    
    public static MyWindow createInEDT() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() { return new MyWindow(); }
      });
    }

    MyWindow() {
      super(MacOsxMenuBarTest.class);
      setName("myWindow");
      setJMenuBar(menuBar(menu(menuItem())));
      setPreferredSize(new Dimension(200, 100));
    }

    private JMenuItem menuItem() {
      JMenuItem menuItem = new JMenuItem("Menu Item");
      menuItem.setName("menuItem");
      return menuItem;
    }
    
    private JMenu menu(JMenuItem menuItem) {
      JMenu menu = new JMenu("Menu");
      menu.setName("menu");
      menu.add(menuItem);
      return menu;
    }

    private JMenuBar menuBar(JMenu menu) {
      JMenuBar menuBar = new JMenuBar();
      menuBar.add(menu);
      return menuBar;
    }
  }
}
