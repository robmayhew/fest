/*
 * Created on Oct 9, 2007
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

import java.awt.Window;
import java.util.Map;
import java.util.TimerTask;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.testing.TestFrame;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.swing.util.ReflectionUtils.mapField;

/**
 * Tests for <code>{@link Windows}</code>.
 *
 * @author Alex Ruiz
 */
public class WindowsTest {

  private Windows windows;
  private TestFrame frame;
  
  private Map<Window, TimerTask> pending;
  private Map<Window, Boolean> open;
  private Map<Window, Boolean> closed;
  private Map<Window, Boolean> hidden;
  
  @BeforeMethod public void setUp() {
    frame = new TestFrame(getClass());
    windows = new Windows();
    pending = mapField("pending", windows);
    open = mapField("open", windows);
    closed = mapField("closed", windows);
    hidden = mapField("hidden", windows);
  }
  
  @AfterMethod public void tearDown() {
    frame.destroy();
  }
  
  @Test(groups = GUI) public void shouldEvaluateWindowAsReadyAndNotHiddenIfVisible() {
    frame.display();
    windows.markExisting(frame);
    assertWindowIsReady();
  }

  @Test public void shouldEvaluateWindowAsReadyAndHiddenIfNotVisible() {
    frame.pack();
    windows.markExisting(frame);
    assertThat(frameClosed()).isFalse();
    assertThat(frameHidden()).isTrue();
    assertThat(frameOpen()).isTrue();
    assertThat(framePending()).isFalse();
  }

  @Test public void shouldMarkWindowAsHidden() {
    windows.markAsHidden(frame);
    assertThat(frameClosed()).isFalse();
    assertThat(frameHidden()).isTrue();
    assertThat(frameOpen()).isFalse();
    assertThat(framePending()).isFalse();
  }
  
  @Test public void shouldMarkWindowAsShowing() {
    windows.markAsShowing(frame);
    assertThat(windows.isShowingButNotReady(frame)).isTrue();
    pause(Windows.WINDOW_READY_DELAY * 2);
    assertWindowIsReady();
  }

  private void assertWindowIsReady() {
    assertThat(frameClosed()).isFalse();
    assertThat(frameHidden()).isFalse();
    assertThat(frameOpen()).isTrue();
    assertThat(framePending()).isFalse();
  }
  
  @Test public void shouldMarkWindowAsClosed() {
    windows.markAsClosed(frame);
    assertThat(frameClosed()).isTrue();
    assertThat(frameHidden()).isFalse();
    assertThat(frameOpen()).isFalse();
    assertThat(framePending()).isFalse();
  }

  private boolean framePending() { return pending.containsKey(frame); }

  private boolean frameOpen() { return open.containsKey(frame); }

  private boolean frameHidden() { return hidden.containsKey(frame); }

  private boolean frameClosed() { return closed.containsKey(frame); }

  @Test public void shouldReturnTrueIfWindowIsClosed() {
    closed.put(frame, true);
    assertThat(windows.isClosed(frame)).isTrue();
  }
  
  @Test public void shouldReturnFalseIfWindowIsNotClosed() {
    closed.remove(frame);
    assertThat(windows.isClosed(frame)).isFalse();
  }
  
  @Test public void shouldReturnTrueIfWindowIsOpenAndNotHidden() {
    open.put(frame, true);
    hidden.remove(frame);
    assertThat(windows.isReady(frame)).isTrue();
  }
  
  @Test public void shouldReturnFalseIfWindowIsOpenAndHidden() {
    open.put(frame, true);
    hidden.put(frame, true);
    assertThat(windows.isReady(frame)).isFalse();
  }
  
  @Test public void shouldReturnFalseIfWindowIsNotOpenAndHidden() {
    open.remove(frame);
    hidden.put(frame, true);
    assertThat(windows.isReady(frame)).isFalse();
  }
  
  @Test public void shouldReturnFalseIfWindowIsNotOpenAndNotHidden() {
    open.remove(frame);
    hidden.remove(frame);
    assertThat(windows.isReady(frame)).isFalse();
  }
  
  @Test public void shouldReturnTrueIfWindowIsHidden() {
    hidden.put(frame, true);
    assertThat(windows.isHidden(frame)).isTrue();
  }
  
  @Test public void shouldReturnFalseIfWindowIsNotHidden() {
    hidden.remove(frame);
    assertThat(windows.isHidden(frame)).isFalse();
  }

  @Test public void shouldReturnTrueIfWindowIsPending() {
    pending.put(frame, null);
    assertThat(windows.isShowingButNotReady(frame)).isTrue();
  }
  
  @Test public void shouldReturnFalseIfWindowIsNotPending() {
    pending.remove(frame);
    assertThat(windows.isShowingButNotReady(frame)).isFalse();
  }
}
