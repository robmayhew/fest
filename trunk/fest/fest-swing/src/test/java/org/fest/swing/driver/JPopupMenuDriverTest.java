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

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Arrays.array;

import javax.swing.*;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.testing.TestFrame;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JPopupMenuDriver}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Price
 */
@Test(groups = GUI)
public class JPopupMenuDriverTest {

  private Robot robot;
  private JPopupMenuDriver driver;
  private MyFrame frame;

  @BeforeMethod public void setUp() {
    robot = RobotFixture.robotWithCurrentAwtHierarchy();
    frame = new MyFrame();
    robot.showWindow(frame);
    driver = new JPopupMenuDriver(robot);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test public void shouldReturnTextOfMenuItem() {
    String s = JPopupMenuDriver.asString(new JMenuItem("Hello"));
    assertThat(s).isEqualTo("Hello");
  }

  @Test public void shouldReturnDashIfNotMenuItem() {
    final MenuElement menuElement = createMock(MenuElement.class);
    final JButton button = new JButton();
    new EasyMockTemplate(menuElement) {
      protected void expectations() {
        expect(menuElement.getComponent()).andReturn(button);
      }

      protected void codeToTest() {
        assertThat(JPopupMenuDriver.asString(menuElement)).isEqualTo("-");
      }
    }.run();
  }

  @Test public void shouldReturnsPopupLabels() {
    String[] labels = driver.menuLabelsOf(popupMenu());
    assertThat(labels).isEqualTo(array("First", "Second"));
  }

  @Test public void shouldFindMenuItemByName() {
    JMenuItem found = driver.menuItem(popupMenu(), "first");
    assertThat(found).isSameAs(frame.firstMenuItem);
  }

  @Test public void shouldFindMenuItemWithGivenMatcher() {
    JMenuItem found = driver.menuItem(popupMenu(), new GenericTypeMatcher<JMenuItem>() {
      protected boolean isMatching(JMenuItem menuItem) {
        return "Second".equals(menuItem.getText());
      }
    });
    assertThat(found).isSameAs(frame.secondMenuItem);
  }

  private JPopupMenu popupMenu() {
    return frame.popupMenu;
  }

  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    private final JMenuItem firstMenuItem = new JMenuItem("First");
    private final JMenuItem secondMenuItem = new JMenuItem("Second");
    private final JTextField withPopup = new JTextField("With Pop-up Menu");
    private final JPopupMenu popupMenu = new JPopupMenu("Pop-up Menu");

    MyFrame() {
      super(JPopupMenuDriverTest.class);
      add(withPopup);
      withPopup.setComponentPopupMenu(popupMenu);
      popupMenu.add(firstMenuItem);
      firstMenuItem.setName("first");
      popupMenu.add(secondMenuItem);
    }
  }
}
