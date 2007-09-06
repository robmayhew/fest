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
package org.fest.swing.fixture;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Arrays.array;

import org.fest.swing.GenericTypeMatcher;
import org.fest.swing.RobotFixture;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JPopupMenuFixture}</code>.
 *
 * @author Yvonne Wang
 */
public class JPopupMenuFixtureTest {

  private JPopupMenuFixture fixture;
  private RobotFixture robot;
  private MyFrame frame;

  @BeforeTest public void setUp() {
    robot = RobotFixture.robotWithNewAwtHierarchy();
    frame = new MyFrame();
    robot.showWindow(frame);
    fixture = new JPopupMenuFixture(robot, robot.showPopupMenu(frame.textBox));
  }
  
  @AfterTest public void tearDown() {
    robot.cleanUp();
  }
  
  @Test public void shouldReturnMenuLabels() {
    String[] menuLabels = fixture.menuLabels();
    assertThat(menuLabels).isEqualTo(array("First", "Second", "Third"));
  }

  @Test public void shouldFindMenuWithGivenMatcher() {
    GenericTypeMatcher<JMenuItem> textMatcher = new GenericTypeMatcher<JMenuItem>() {
      protected boolean isMatching(JMenuItem menuItem) {
        return "First".equals(menuItem.getText());
      }
    };
    JMenuItemFixture menuItem = fixture.menuItem(textMatcher);
    assertThat(menuItem.target).isSameAs(frame.firstMenuItem);
  }

  @Test public void shouldFindMenuWithGivenName() {
    JMenuItemFixture menuItem = fixture.menuItem("first");
    assertThat(menuItem.target).isSameAs(frame.firstMenuItem);
  }
  
  private static class MyFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private final JPopupMenu popupMenu = new JPopupMenu("Popup Menu");
    private final JMenuItem firstMenuItem = new JMenuItem("First");
    private final JTextField textBox = new JTextField(20);
    
    MyFrame() {
      setLayout(new FlowLayout());
      add(textBox);
      textBox.setComponentPopupMenu(popupMenu);
      firstMenuItem.setName("first");
      popupMenu.add(firstMenuItem);
      popupMenu.add(new JMenuItem("Second"));
      popupMenu.add(new JMenuItem("Third"));
    }
  }
}
