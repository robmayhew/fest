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
import java.util.logging.Logger;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.ScreenLock;
import org.fest.swing.testing.TestWindow;

import static java.lang.String.valueOf;
import static java.util.logging.Logger.getAnonymousLogger;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.swing.util.ReflectionUtils.mapField;
import static org.fest.util.Strings.concat;

/**
 * Tests for <code>{@link Windows}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class WindowsTest {

  private static Logger logger = getAnonymousLogger();

  private Windows windows;
  private TestWindow frame;

  private Map<Window, TimerTask> pending;
  private Map<Window, Boolean> open;
  private Map<Window, Boolean> closed;
  private Map<Window, Boolean> hidden;

  @BeforeMethod public void setUp() {
    frame = TestWindow.createNew(getClass());
    windows = new Windows();
    // TODO use real maps
    pending = mapField("pending", windows);
    open = mapField("open", windows);
    closed = mapField("closed", windows);
    hidden = mapField("hidden", windows);
  }

  @AfterMethod public void tearDown() {
    frame.destroy();
    ScreenLock screenLock = ScreenLock.instance();
    if (screenLock.acquiredBy(this)) screenLock.release(this);
  }

  @Test(groups = GUI)
  public void shouldEvaluateWindowAsReadyAndNotHiddenIfVisible() {
    ScreenLock.instance().acquire(this);
    frame.display();
    windows.markExisting(frame);
    assertWindowIsReady();
  }

  public void shouldEvaluateWindowAsReadyAndHiddenIfNotVisible() {
    // TODO call from EDT
    frame.pack();
    windows.markExisting(frame);
    assertThat(frameClosed()).isFalse();
    assertThat(frameHidden()).isTrue();
    assertThat(frameOpen()).isTrue();
    assertThat(framePending()).isFalse();
  }

  public void shouldMarkWindowAsHidden() {
    windows.markAsHidden(frame);
    assertThat(frameClosed()).isFalse();
    assertThat(frameHidden()).isTrue();
    assertThat(frameOpen()).isFalse();
    assertThat(framePending()).isFalse();
  }

  public void shouldMarkWindowAsShowing() {
    windows.markAsShowing(frame);
    assertThat(windows.isShowingButNotReady(frame)).isTrue();
    int timeToPause = Windows.WINDOW_READY_DELAY * 2;
    logger.info(concat("Pausing for ", valueOf(timeToPause), " ms"));
    pause(timeToPause);
    assertWindowIsReady();
  }

  private void assertWindowIsReady() {
    assertThat(frameClosed()).isFalse();
    assertThat(frameHidden()).isFalse();
    assertThat(frameOpen()).isTrue();
    assertThat(framePending()).isFalse();
  }

  public void shouldMarkWindowAsClosed() {
    windows.markAsClosed(frame);
    assertThat(frameClosed()).isTrue();
    assertThat(frameHidden()).isFalse();
    assertThat(frameOpen()).isFalse();
    assertThat(framePending()).isFalse();
  }

  private boolean framePending() { return pending.containsKey(frame); }
  private boolean frameOpen()    { return open.containsKey(frame);    }
  private boolean frameHidden()  { return hidden.containsKey(frame);  }
  private boolean frameClosed()  { return closed.containsKey(frame);  }

  public void shouldReturnTrueIfWindowIsClosed() {
    closed.put(frame, true);
    assertThat(windows.isClosed(frame)).isTrue();
  }

  public void shouldReturnFalseIfWindowIsNotClosed() {
    closed.remove(frame);
    assertThat(windows.isClosed(frame)).isFalse();
  }

  public void shouldReturnTrueIfWindowIsOpenAndNotHidden() {
    open.put(frame, true);
    hidden.remove(frame);
    assertThat(windows.isReady(frame)).isTrue();
  }

  public void shouldReturnFalseIfWindowIsOpenAndHidden() {
    open.put(frame, true);
    hidden.put(frame, true);
    assertThat(windows.isReady(frame)).isFalse();
  }

  public void shouldReturnFalseIfWindowIsNotOpenAndHidden() {
    open.remove(frame);
    hidden.put(frame, true);
    assertThat(windows.isReady(frame)).isFalse();
  }

  public void shouldReturnFalseIfWindowIsNotOpenAndNotHidden() {
    open.remove(frame);
    hidden.remove(frame);
    assertThat(windows.isReady(frame)).isFalse();
  }

  public void shouldReturnTrueIfWindowIsHidden() {
    hidden.put(frame, true);
    assertThat(windows.isHidden(frame)).isTrue();
  }

  public void shouldReturnFalseIfWindowIsNotHidden() {
    hidden.remove(frame);
    assertThat(windows.isHidden(frame)).isFalse();
  }

  public void shouldReturnTrueIfWindowIsPending() {
    pending.put(frame, null);
    assertThat(windows.isShowingButNotReady(frame)).isTrue();
  }

  public void shouldReturnFalseIfWindowIsNotPending() {
    pending.remove(frame);
    assertThat(windows.isShowingButNotReady(frame)).isFalse();
  }
}
