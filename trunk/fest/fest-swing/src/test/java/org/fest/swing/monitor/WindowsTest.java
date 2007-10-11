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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.monitor;

import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.monitor.WindowsUtils.waitForWindowToBeMarkedAsReady;

import org.fest.swing.TestFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link Windows}</code>.
 *
 * @author Alex Ruiz
 */
public class WindowsTest {

  private Windows windows;
  private TestFrame frame;
  
  @BeforeMethod public void setUp() {
    frame = new TestFrame(getClass());
    windows = new Windows();
  }

  @AfterMethod public void tearDown() {
    frame.beDisposed();
  }
  
  @Test public void shouldEvaluateWindowAsReadyAndNotHiddenIfVisible() {
    frame.beVisible();
    windows.evaluate(frame);
    assertWindowIsReady();
  }

  @Test public void shouldEvaluateWindowAsReadyAndHiddenIfNotVisible() {
    frame.pack();
    windows.evaluate(frame);
    assertWindowIsHidden();
  }
  
  @Test public void shouldMarkWindowAsHidden() {
    windows.markAsHidden(frame);
    assertWindowIsHidden();
  }

  private void assertWindowIsHidden() {
    assertThat(windows.isClosed(frame)).isFalse();
    assertThat(windows.isHidden(frame)).isTrue();
    assertThat(windows.isReady(frame)).isFalse();
    assertThat(windows.isShowingButNotReady(frame)).isFalse();
  }
  
  @Test public void shouldMarkWindowAsShowing() {
    windows.markAsShowing(frame);
    assertThat(windows.isShowingButNotReady(frame)).isTrue();
    waitForWindowToBeMarkedAsReady();
    assertWindowIsReady();
  }

  private void assertWindowIsReady() {
    assertThat(windows.isClosed(frame)).isFalse();
    assertThat(windows.isHidden(frame)).isFalse();
    assertThat(windows.isReady(frame)).isTrue();
    assertThat(windows.isShowingButNotReady(frame)).isFalse();
  }
  
  @Test public void shouldMarkWindowAsClosed() {
    windows.markAsClosed(frame);
    assertThat(windows.isClosed(frame)).isTrue();
    assertThat(windows.isHidden(frame)).isFalse();
    assertThat(windows.isReady(frame)).isFalse();
    assertThat(windows.isShowingButNotReady(frame)).isFalse();
  }
}
