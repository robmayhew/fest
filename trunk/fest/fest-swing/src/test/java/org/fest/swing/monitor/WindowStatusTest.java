/*
 * Created on Oct 18, 2007
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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.monitor;

import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.testing.TestWindow;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;

/**
 * Tests for <code>{@link WindowStatus}</code>.
 *
 * @author Alex Ruiz
 */
public class WindowStatusTest {

  private WindowStatus status;

  private TestWindow window;
  private Windows windows;

  @BeforeMethod public void setUp() throws Exception {
    window = TestWindow.createNew(getClass());
    windows = createMock(Windows.class);
    status = new WindowStatus(windows);
  }

  @AfterMethod public void tearDown() {
    window.destroy();
  }

  @Test public void shouldMoveMouseToCenterWithFrameWidthGreaterThanHeight() {
    window.display();
    Point center = new WindowMetrics(window).center();
    center.x += WindowStatus.sign();
    new EasyMockTemplate(windows) {
      @Override protected void expectations() {
        expectFrameIsReady();
      }

      @Override protected void codeToTest() {
        status.checkIfReady(window);
      }
    }.run();
    assertThat(mousePointerLocation()).isEqualTo(center);
  }

  @Test public void shouldMoveMouseToCenterWithFrameHeightGreaterThanWidth() {
    window.display(new Dimension(200, 400));
    Point center = new WindowMetrics(window).center();
    center.y += WindowStatus.sign();
    new EasyMockTemplate(windows) {
      @Override protected void expectations() {
        expectFrameIsReady();
      }

      @Override protected void codeToTest() {
        status.checkIfReady(window);
      }
    }.run();
    assertThat(mousePointerLocation()).isEqualTo(center);
  }

  @Test public void shouldResizeWindowToReceiveEvents() {
    window.display(new Dimension(0 ,0));
    Dimension original = sizeOf(window);
    new EasyMockTemplate(windows) {
      @Override protected void expectations() {
        expect(windows.isShowingButNotReady(window)).andReturn(true);
      }

      @Override protected void codeToTest() {
        status.checkIfReady(window);
      }
    }.run();
    // wait till frame is resized
    pause(5000);
    assertThat(sizeOf(window).height).isGreaterThan(original.height);
  }

  @Test public void shouldNotCheckIfRobotIsNull() {
    status = new WindowStatus(windows, null);
    Point before = mousePointerLocation();
    new EasyMockTemplate(windows) {
      @Override protected void expectations() {}

      @Override protected void codeToTest() {
        status.checkIfReady(window);
      }
    }.run();
    // mouse pointer should not have moved
    assertThat(mousePointerLocation()).isEqualTo(before);
  }

  private void expectFrameIsReady() {
    expect(windows.isShowingButNotReady(window)).andReturn(false);
  }

  private Point mousePointerLocation() {
    return MouseInfo.getPointerInfo().getLocation();
  }
}
