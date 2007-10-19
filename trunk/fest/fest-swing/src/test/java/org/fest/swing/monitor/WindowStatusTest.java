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
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.monitor.MockWindows.mock;
import static org.fest.swing.monitor.MockWindows.MethodToMock.IS_SHOWING_BUT_NOT_READY;

import org.fest.swing.TestFrame;

import org.testng.annotations.AfterTest;
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
    frame.beVisible();
    windows = mock(IS_SHOWING_BUT_NOT_READY);
    status = new WindowStatus(windows);
  }
  
  @AfterTest public void tearDown() {
    frame.beDisposed();
  }
  
  @Test public void shouldMoveMouseToCenterWithFrameWidthGreaterThanHeight() {
    frame.setSize(new Dimension(400, 200));
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
    assertThat(mouseLocation()).isEqualTo(center);
  }
  
  @Test public void shouldMoveMouseToCenterWithFrameHeightGreaterThanWidth() {
    frame.setSize(new Dimension(200, 400));
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
    assertThat(mouseLocation()).isEqualTo(center);
  }
  
  @Test public void shouldResizeWindowToReceiveEvents() {
    Insets insets = frame.getInsets();
    frame.setSize(new Dimension(insets.left + insets.right, insets.top + insets.bottom));
    Dimension original = frame.getSize();
    new EasyMockTemplate(windows) {
      @Override protected void expectations() {
        expect(windows.isShowingButNotReady(frame)).andReturn(true);
      }

      @Override protected void codeToTest() {
        status.checkIfReady(frame);
      }
    }.run();
    assertThat(frame.getHeight()).isGreaterThan(original.height);
  }

  private void expectFrameIsReady() {
    expect(windows.isShowingButNotReady(frame)).andReturn(false);
  }

  private Point mouseLocation() {
    return MouseInfo.getPointerInfo().getLocation();
  }
}
