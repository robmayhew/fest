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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.monitor;

import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.monitor.MockWindows.mock;

import org.fest.swing.TestFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link WindowStatus}</code>.
 *
 * @author Alex Ruiz
 */
public class WindowStatusTest {

  private WindowStatus status;

  private TestFrame frame;
  private Windows windows;
  
  @BeforeMethod public void setUp() throws Exception {
    frame = new TestFrame(getClass());
    windows = mock();
    status = new WindowStatus(windows);
  }
  
  @AfterMethod public void tearDown() {
    frame.beDisposed();
  }
  
  @Test public void shouldMoveMouseToCenterWithFrameWidthGreaterThanHeight() {
    frame.beVisible();
    Point center = new WindowMetrics(frame).center();
    center.x += WindowStatus.sign();
    new EasyMockTemplate(windows) {
      @Override protected void expectations() {
        expectFrameIsReady();
      }

      @Override protected void codeToTest() {
        status.checkIfReady(frame);
      }
    }.run();
    assertThat(mousePointerLocation()).isEqualTo(center);
  }
  
  @Test public void shouldMoveMouseToCenterWithFrameHeightGreaterThanWidth() {
    frame.beVisible(new Dimension(200, 400));
    Point center = new WindowMetrics(frame).center();
    center.y += WindowStatus.sign();
    new EasyMockTemplate(windows) {
      @Override protected void expectations() {
        expectFrameIsReady();
      }

      @Override protected void codeToTest() {
        status.checkIfReady(frame);
      }
    }.run();
    assertThat(mousePointerLocation()).isEqualTo(center);
  }
  
  @Test public void shouldResizeWindowToReceiveEvents() {
    frame.beVisible(new Dimension(0 ,0));
    Dimension original = frame.getSize();
    new EasyMockTemplate(windows) {
      @Override protected void expectations() {
        expect(windows.isShowingButNotReady(frame)).andReturn(true);
      }

      @Override protected void codeToTest() {
        status.checkIfReady(frame);
      }
    }.run();
    // wait till frame is resized
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {}
    assertThat(frame.getHeight()).isGreaterThan(original.height);
  }
  
  @Test public void shouldNotCheckIfRobotIsNull() {
    status = new WindowStatus(windows, null);
    Point before = mousePointerLocation();
    new EasyMockTemplate(windows) {
      @Override protected void expectations() {}

      @Override protected void codeToTest() {
        status.checkIfReady(frame);
      }
    }.run();
    // mouse pointer should not have moved
    assertThat(mousePointerLocation()).isEqualTo(before);
  }

  private void expectFrameIsReady() {
    expect(windows.isShowingButNotReady(frame)).andReturn(false);
  }

  private Point mousePointerLocation() {
    return MouseInfo.getPointerInfo().getLocation();
  }
}
