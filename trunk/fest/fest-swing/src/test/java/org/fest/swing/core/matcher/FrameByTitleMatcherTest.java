/*
 * Created on Jul 16, 2008
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
package org.fest.swing.core.matcher;

import javax.swing.JFrame;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.lock.ScreenLock;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.factory.JFrames.frame;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link FrameByTitleMatcher}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test public class FrameByTitleMatcherTest {

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  public void shouldReturnTrueIfTitleIsEqualToExpected() {
    String title = "Hello";
    FrameByTitleMatcher matcher = FrameByTitleMatcher.withTitle(title);
    JFrame frame = frame().withTitle(title).createNew();
    assertThat(matcher.matches(frame)).isTrue();
  }

  public void shouldReturnFalseIfTitleIsNotEqualToExpected() {
    FrameByTitleMatcher matcher = FrameByTitleMatcher.withTitle("Hello");
    JFrame frame = frame().withTitle("Bye").createNew();
    assertThat(matcher.matches(frame)).isFalse();
  }

  @Test(groups = GUI)
  public void shouldReturnTrueIfFrameIsShowingAndTitleIsEqualToExpected() {
    ScreenLock.instance().acquire(this);
    Class<FrameByTitleMatcher> testType = FrameByTitleMatcher.class;
    TestWindow frame = TestWindow.createAndShowNewWindow(testType);
    try {
      FrameByTitleMatcher matcher = FrameByTitleMatcher.withTitleAndShowing(testType.getSimpleName());
      assertThat(matcher.matches(frame)).isTrue();
    } finally {
      try {
        frame.destroy();
      } catch (RuntimeException e) {}
      ScreenLock.instance().release(this);
    }
  }

  public void shouldReturnFalseIfFrameIsNotShowingAndTitleIsEqualToExpected() {
    String title = "Hello";
    FrameByTitleMatcher matcher = FrameByTitleMatcher.withTitleAndShowing(title);
    JFrame frame = frame().withTitle(title).createNew();
    assertThat(matcher.matches(frame)).isFalse();
  }

  @Test(groups = GUI)
  public void shouldReturnFalseIfFrameIsShowingAndTitleIsNotEqualToExpected() {
    ScreenLock.instance().acquire(this);
    TestWindow frame = TestWindow.createAndShowNewWindow(FrameByTitleMatcher.class);
    try {
      FrameByTitleMatcher matcher = FrameByTitleMatcher.withTitleAndShowing("Hello");
      assertThat(matcher.matches(frame)).isFalse();
    } finally {
      try {
        frame.destroy();
      } catch (RuntimeException e) {}
      ScreenLock.instance().release(this);
    }
  }

  public void shouldReturnFalseIfFrameIsNotShowingAndTitleIsNotEqualToExpected() {
    FrameByTitleMatcher matcher = FrameByTitleMatcher.withTitleAndShowing("Hello");
    JFrame frame = frame().withTitle("Bye").createNew();
    assertThat(matcher.matches(frame)).isFalse();
  }
}
