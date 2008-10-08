/*
 * Created on Aug 26, 2008
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
package org.fest.swing.hierarchy;

import javax.swing.JInternalFrame;
import javax.swing.JInternalFrame.JDesktopIcon;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.ScreenLock;
import org.fest.swing.testing.MDITestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.hierarchy.JInternalFrameIconifyTask.iconify;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JDesktopIconInternalFrameQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class JDesktopIconInternalFrameQueryTest {

  private MDITestWindow window;
  private JInternalFrame internalFrame;

  @BeforeMethod public void setUp() {
    ScreenLock.instance().acquire(this);
    window = MDITestWindow.showInTest(getClass());
    internalFrame = window.internalFrame();
  }

  @AfterMethod public void tearDown() {
    window.destroy();
    ScreenLock.instance().release(this);
  }

  public void shouldReturnInternalFrameOfJDesktopIcon() {
    iconify(internalFrame);
    JDesktopIcon desktopIcon = internalFrame.getDesktopIcon();
    JInternalFrame actual = JDesktopIconInternalFrameQuery.internalFrameOf(desktopIcon);
    assertThat(actual).isSameAs(internalFrame);
  }
}
