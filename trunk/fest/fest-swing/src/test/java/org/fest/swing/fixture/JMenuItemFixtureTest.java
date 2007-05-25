/*
 * Created on Mar 4, 2007
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
package org.fest.swing.fixture;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.RobotFixture.robotWithNewAwtHierarchy;

import org.fest.swing.GUITest;
import org.fest.swing.RobotFixture;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JMenuItemFixture}</code>.
 *
 * @author Alex Ruiz
 */
@GUITest public class JMenuItemFixtureTest {

  private static class CustomWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    
    final JMenu fileMenu = new JMenu("File");
    final JMenuItem newMenu = new JMenuItem("New");

    boolean newMenuSelected;
    
    CustomWindow() {
      setJMenuBar(new JMenuBar());
      newMenu.setName("new");
      newMenu.addMouseListener(new MouseAdapter() {
        @Override public void mousePressed(MouseEvent e) {
          newMenuSelected = true;
        }
      });
      fileMenu.setName("file");
      fileMenu.add(newMenu);
      getJMenuBar().add(fileMenu);
    }
  }
  
  private CustomWindow window;
  private RobotFixture robot;
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = new CustomWindow();
    robot.showWindow(window);
  }
  
  @Test public void shouldFindMenuByName() {
    JMenuItemFixture fixture = new JMenuItemFixture(robot, "new");
    assertThat(fixture.target).isSameAs(window.newMenu);
  }
  
  @Test(dependsOnMethods = "shouldFindMenuByName")
  public void shouldSelectMenu() {
    JMenuItemFixture fixture = new JMenuItemFixture(robot, "new");
    fixture.select();
    assertThat(window.newMenuSelected).isTrue();
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
}
