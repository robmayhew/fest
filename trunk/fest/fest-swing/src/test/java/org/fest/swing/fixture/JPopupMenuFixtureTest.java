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

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.RobotFixture.robotWithNewAwtHierarchy;

import static org.fest.util.Arrays.array;

import org.fest.swing.GenericTypeMatcher;
import org.fest.swing.RobotFixture;
import org.fest.swing.TestFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
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

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    frame = new MyFrame(getClass());
    robot.showWindow(frame);
    fixture = new JPopupMenuFixture(robot, robot.showPopupMenu(frame.textBox));
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  @Test public void shouldReturnMenuLabels() {
    String[] menuLabels = fixture.menuLabels();
    assertThat(menuLabels).isEqualTo(array("First", "Second", "Third"));
    frame.popupMenu.setVisible(false);
  }

  @Test public void shouldFindMenuWithGivenMatcher() {
    GenericTypeMatcher<JMenuItem> textMatcher = new GenericTypeMatcher<JMenuItem>() {
      protected boolean isMatching(JMenuItem menuItem) {
        return "First".equals(menuItem.getText());
      }
    };
    JMenuItemFixture menuItem = fixture.menuItem(textMatcher);
    assertThat(menuItem.target).isSameAs(frame.firstMenuItem);
    frame.popupMenu.setVisible(false);
  }

  @Test public void shouldFindMenuWithGivenName() {
    JMenuItemFixture menuItem = fixture.menuItem("first");
    assertThat(menuItem.target).isSameAs(frame.firstMenuItem);
    frame.popupMenu.setVisible(false);
  }
  
  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    private final JPopupMenu popupMenu = new JPopupMenu("Popup Menu");
    private final JMenuItem firstMenuItem = new JMenuItem("First");
    private final JTextField textBox = new JTextField(20);
    
    MyFrame(Class testClass) {
      super(testClass);
      add(textBox);
      textBox.setComponentPopupMenu(popupMenu);
      firstMenuItem.setName("first");
      popupMenu.add(firstMenuItem);
      popupMenu.add(new JMenuItem("Second"));
      popupMenu.add(new JMenuItem("Third"));
    }
  }
}
