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

import javax.swing.JLabel;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.lock.ScreenLock;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.factory.JLabels.label;
import static org.fest.swing.test.core.TestGroups.GUI;

/**
 * Tests for <code>{@link JLabelByTextMatcher}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class JLabelByTextMatcherTest {

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  public void shouldReturnTrueIfTitleIsEqualToExpected() {
    String text = "Hello";
    JLabelByTextMatcher matcher = JLabelByTextMatcher.withText(text);
    JLabel label = label().withText(text).createNew();
    assertThat(matcher.matches(label)).isTrue();
  }

  public void shouldReturnFalseIfTitleIsNotEqualToExpected() {
    JLabelByTextMatcher matcher = JLabelByTextMatcher.withText("Hello");
    JLabel label = label().withText("Bye").createNew();
    assertThat(matcher.matches(label)).isFalse();
  }

  @Test(groups = GUI)
  public void shouldReturnTrueIfFrameIsShowingAndTitleIsEqualToExpected() {
    ScreenLock.instance().acquire(this);
    MyWindow window = MyWindow.createAndShow();
    try {
      JLabelByTextMatcher matcher = JLabelByTextMatcher.withTextAndShowing("Hello");
      assertThat(matcher.matches(window.label)).isTrue();
    } finally {
      window.destroy();
      ScreenLock.instance().release(this);
    }
  }

  public void shouldReturnFalseIfFrameIsNotShowingAndTitleIsEqualToExpected() {
    String text = "Hello";
    JLabelByTextMatcher matcher = JLabelByTextMatcher.withTextAndShowing(text);
    JLabel label = label().withText(text).createNew();
    assertThat(matcher.matches(label)).isFalse();
  }

  @Test(groups = GUI)
  public void shouldReturnFalseIfFrameIsShowingAndTitleIsNotEqualToExpected() {
    ScreenLock.instance().acquire(this);
    MyWindow window = MyWindow.createAndShow();
    try {
      JLabelByTextMatcher matcher = JLabelByTextMatcher.withTextAndShowing("Bye");
      assertThat(matcher.matches(window.label)).isFalse();
    } finally {
      window.destroy();
      ScreenLock.instance().release(this);
    }
  }

  public void shouldReturnFalseIfFrameIsNotShowingAndTitleIsNotEqualToExpected() {
    JLabelByTextMatcher matcher = JLabelByTextMatcher.withTextAndShowing("Hello");
    JLabel label = label().withText("Bye").createNew();
    assertThat(matcher.matches(label)).isFalse();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    @RunsInEDT
    static MyWindow createAndShow() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          MyWindow window = new MyWindow();
          TestWindow.display(window);
          return window;
        }
      });
    }

    final JLabel label = new JLabel("Hello");

    private MyWindow() {
      super(JLabelByTextMatcherTest.class);
      addComponents(label);
    }
  }
}
