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

import org.testng.annotations.Test;

import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link FrameByTitleMatcher}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class FrameByTitleMatcherTest {

  public void shouldReturnTrueIfTitleIsEqualToExpected() {
    String title = "Hello";
    FrameByTitleMatcher matcher = FrameByTitleMatcher.withTitle(title);
    JFrame frame = new JFrame(title);
    assertThat(matcher.matches(frame)).isTrue();
  }
  
  public void shouldReturnFalseIfTitleIsNotEqualToExpected() {
    FrameByTitleMatcher matcher = FrameByTitleMatcher.withTitle("Hello");
    JFrame frame = new JFrame("Bye");
    assertThat(matcher.matches(frame)).isFalse();
  }
  
  @Test(groups = GUI)
  public void shouldReturnTrueIfFrameIsShowingAndTitleIsEqualToExpected() {
    Class<FrameByTitleMatcher> testType = FrameByTitleMatcher.class;
    TestWindow frame = TestWindow.showInTest(testType);
    try {
      FrameByTitleMatcher matcher = FrameByTitleMatcher.withTitleAndShowing(testType.getSimpleName());
      assertThat(matcher.matches(frame)).isTrue();
    } finally {
      frame.destroy();
    }
  } 
  
  public void shouldReturnFalseIfFrameIsNotShowingAndTitleIsEqualToExpected() {
    String title = "Hello";
    FrameByTitleMatcher matcher = FrameByTitleMatcher.withTitleAndShowing(title);
    JFrame frame = new JFrame(title);
    assertThat(matcher.matches(frame)).isFalse();    
  }

  @Test(groups = GUI)
  public void shouldReturnFalseIfFrameIsShowingAndTitleIsNotEqualToExpected() {
    TestWindow frame = TestWindow.showInTest(FrameByTitleMatcher.class);
    try {
      FrameByTitleMatcher matcher = FrameByTitleMatcher.withTitleAndShowing("Hello");
      assertThat(matcher.matches(frame)).isFalse();
    } finally {
      frame.destroy();
    }
  }

  public void shouldReturnFalseIfFrameIsNotShowingAndTitleIsNotEqualToExpected() {
    FrameByTitleMatcher matcher = FrameByTitleMatcher.withTitleAndShowing("Hello");
    JFrame frame = new JFrame("Bye");
    assertThat(matcher.matches(frame)).isFalse();    
  }
}
