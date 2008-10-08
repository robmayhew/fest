/*
 * Created on Aug 10, 2008
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
import javax.swing.JSplitPane;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JSplitPaneSizeAndDividerLocationQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class JSplitPaneSizeAndDividerLocationQueryTest {

  private static final int DIVIDER_LOCATION = 50;
  private static final Dimension SIZE = new Dimension(200, 100);

  private Robot robot;
  private MySplitPane splitPane;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    splitPane = window.splitPane;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnSizeAndDividerLocationOfJSplitPane() {
    JSplitPaneSizeAndDividerLocation sizeAndDividerLocation =
      JSplitPaneSizeAndDividerLocationQuery.sizeAndDividerLocationOf(splitPane);
    assertThat(sizeAndDividerLocation.size()).isEqualTo(SIZE);
    assertThat(sizeAndDividerLocation.dividerLocation()).isEqualTo(DIVIDER_LOCATION);
    assertThat(splitPane.methodGetDividerLocationWasInvoked()).isTrue();
    assertThat(splitPane.methodGetSizeWasInvoked()).isTrue();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final MySplitPane splitPane = new MySplitPane();

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(JSplitPaneSizeAndDividerLocationQueryTest.class);
      splitPane.setDividerLocation(DIVIDER_LOCATION);
      splitPane.setPreferredSize(new Dimension(SIZE));
      addComponents(splitPane);
    }
  }

  private static class MySplitPane extends JSplitPane {
    private static final long serialVersionUID = 1L;

    private boolean methodGetDividerLocationInvoked;
    private boolean methodGetSizeInvoked;

    MySplitPane() {
      super(HORIZONTAL_SPLIT, new JPanel(), new JPanel());
    }

    @Override public int getDividerLocation() {
      methodGetDividerLocationInvoked = true;
      return super.getDividerLocation();
    }

    @Override public Dimension getSize() {
      methodGetSizeInvoked = true;
      return super.getSize();
    }

    boolean methodGetDividerLocationWasInvoked() { return methodGetDividerLocationInvoked; }
    boolean methodGetSizeWasInvoked() { return methodGetSizeInvoked; }
  }
}
