/*
 * Created on Feb 25, 2008
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

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.assertions.IntAssert;
import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.testing.TestWindow;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Arrays.array;
import static org.fest.util.Strings.concat;

/**
 * Tests for <code>{@link JTabbedPaneDriver}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class JTabbedPaneDriverTest {

  private Robot robot;
  private JTabbedPane tabbedPane;
  private JTabbedPaneDriver driver;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JTabbedPaneDriver(robot);
    MyFrame frame = new MyFrame();
    tabbedPane = frame.tabbedPane;
    robot.showWindow(frame);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldSetTabDirectlyIfLocationOfTabNotFound() {
    tabbedPane.setSelectedIndex(0);
    final JTabbedPaneLocation location = createMock(JTabbedPaneLocation.class);
    final int index = 1; 
    driver = new JTabbedPaneDriver(robot, location);
    new EasyMockTemplate(location) {
      protected void expectations() {
        expect(location.pointAt(tabbedPane, index)).andThrow(new LocationUnavailableException("Thrown on purpose"));
      }

      protected void codeToTest() {
        driver.selectTab(tabbedPane, index);
        assertThatSelectedTabIndexIsEqualTo(index);
      }
    }.run();
  }
  
  @Test(groups = GUI, dataProvider = "tabIndexProvider")
  public void shouldSelectTabWithGivenIndex(int index) {
    driver.selectTab(tabbedPane, index);
    assertThatSelectedTabIndexIsEqualTo(index);
  }
  
  public void shouldNotSelectTabWithGivenIndexIfTabbedPaneIsNotEnabled() {
    clearAndDisableTabbedPane();
    driver.selectTab(tabbedPane, 1);
    assertThatSelectedTabIndexIsEqualTo(0);
  }

  @Test(groups = GUI, dataProvider = "tabIndexProvider")
  public void shouldSetTabWithGivenIndexDirectly(int index) {
    driver.setTabDirectly(tabbedPane, index);
    pause(200);
    assertThatSelectedTabIndexIsEqualTo(index);
  }

  @DataProvider(name = "tabIndexProvider")
  public Object[][] tabIndices() {
    return new Object[][] { { 0 }, { 1 } };
  }

  @Test(groups = GUI, dataProvider = "indexOutOfBoundsProvider")
  public void shouldThrowErrorIfIndexOutOfBounds(int index) {
    try {
      driver.selectTab(tabbedPane, index);
      fail();
    } catch (ActionFailedException expected) {
      assertThat(expected).message().isEqualTo(concat(
        "Index <", index, "> is not within the JTabbedPane bounds of <0> and <1> (inclusive)"));
    }
  }

  @DataProvider(name = "indexOutOfBoundsProvider")
  public Object[][] indicesOutOfBounds() {
    return new Object[][] { { -1 }, { 2 } };
  }

  public void shouldSelectFirstTab() {
    driver.selectTab(tabbedPane, "First");
    assertThatSelectedTabIndexIsEqualTo(0);
  }

  public void shouldSelectSecondTab() {
    driver.selectTab(tabbedPane, "Second");
    assertThatSelectedTabIndexIsEqualTo(1);
  }

  public void shouldNotSelectTabWithGivenTitleIfTabbedPaneIsNotEnabled() {
    clearAndDisableTabbedPane();
    driver.selectTab(tabbedPane, "Second");
    assertThatSelectedTabIndexIsEqualTo(0);
  }
  
  private IntAssert assertThatSelectedTabIndexIsEqualTo(int expected) {
    return assertThat(tabbedPane.getSelectedIndex()).isEqualTo(expected);
  }

  public void shouldReturnTabTitles() {
    assertThat(driver.tabTitles(tabbedPane)).isEqualTo(array("First", "Second"));
  }

  private void clearAndDisableTabbedPane() {
    robot.invokeAndWait(new Runnable() {
      public void run() {
        tabbedPane.setSelectedIndex(0);
        tabbedPane.setEnabled(false);
      }
    });
    robot.waitForIdle();
  }
  
  private static class MyFrame extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JTabbedPane tabbedPane = new JTabbedPane();

    MyFrame() {
      super(JTabbedPaneDriverTest.class);
      tabbedPane.addTab("First", new JPanel());
      tabbedPane.addTab("Second", new JPanel());
      add(tabbedPane);
      setPreferredSize(new Dimension(300, 200));
    }
  }
}
