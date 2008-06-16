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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithCurrentAwtHierarchy;
import static org.fest.swing.finder.WindowFinder.findFrame;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestFrame;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test for <a href="http://code.google.com/p/fest/issues/detail?id=157" target="_blank">issue 157</a>.
 * 
 * @author Andriy Tsykholyas
 * @author Yvonne Wang
 */
public class MacOsxMenuBarTest {
  
  private Robot robot;
  private MyFrame frame;
  private JMenuItemFixture menuItemFixture;
  
  @BeforeMethod public void setUp() {
    System.setProperty("apple.laf.useScreenMenuBar", "true");
    robot = robotWithCurrentAwtHierarchy();
    frame = new MyFrame();
    frame.display();
    FrameFixture frameFixture = findFrame("myFrame").withTimeout(2000).using(robot);
    menuItemFixture = frameFixture.menuItem("menu");
  }
  
  @AfterMethod public void tearDown() {
    frame.destroy();
    robot.cleanUp();
  }
  
  @Test public void shouldSelectMenu() {
    final boolean[] selected = new boolean[1];
    JMenuItem menu = menuItemFixture.target;
    menu.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        System.out.println("selected");
        selected[0] = true;
      }
    });
    menuItemFixture.select();
    assertThat(selected[0]).isTrue();
  }

  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    MyFrame() {
      super(MacOsxMenuBarTest.class);
      setName("myFrame");
      JMenuItem menuItem = new JMenuItem("Menu Item");
      menuItem.setName("menuItem");
      JMenu menu = new JMenu("Menu");
      menu.setName("menu");
      menu.add(menuItem);
      JMenuBar menuBar = new JMenuBar();
      menuBar.add(menu);
      setJMenuBar(menuBar);
      setPreferredSize(new Dimension(200, 100));
    }
  }
}
