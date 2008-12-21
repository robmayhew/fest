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

import javax.swing.JButton;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.lock.ScreenLock;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.factory.JButtons.button;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link JButtonByTextMatcher}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test public class JButtonByTextMatcherTest {

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  public void shouldReturnTrueIfTitleIsEqualToExpected() {
    String text = "Hello";
    JButtonByTextMatcher matcher = JButtonByTextMatcher.withText(text);
    JButton button = button().withText(text).createNew();
    assertThat(matcher.matches(button)).isTrue();
  }

  public void shouldReturnFalseIfTitleIsNotEqualToExpected() {
    JButtonByTextMatcher matcher = JButtonByTextMatcher.withText("Hello");
    JButton button = button().withText("Bye").createNew();
    assertThat(matcher.matches(button)).isFalse();
  }

  @Test(groups = GUI)
  public void shouldReturnTrueIfFrameIsShowingAndTitleIsEqualToExpected() {
    ScreenLock.instance().acquire(this);
    MyWindow window = MyWindow.createAndShow();
    try {
      JButtonByTextMatcher matcher = JButtonByTextMatcher.withTextAndShowing("Hello");
      assertThat(matcher.matches(window.button)).isTrue();
    } finally {
      window.destroy();
      ScreenLock.instance().release(this);
    }
  }

  public void shouldReturnFalseIfFrameIsNotShowingAndTitleIsEqualToExpected() {
    String text = "Hello";
    JButtonByTextMatcher matcher = JButtonByTextMatcher.withTextAndShowing(text);
    JButton button = button().withText(text).createNew();
    assertThat(matcher.matches(button)).isFalse();
  }

  @Test(groups = GUI)
  public void shouldReturnFalseIfFrameIsShowingAndTextIsNotEqualToExpected() {
    ScreenLock.instance().acquire(this);
    MyWindow window = MyWindow.createAndShow();
    try {
      JButtonByTextMatcher matcher = JButtonByTextMatcher.withTextAndShowing("Bye");
      assertThat(matcher.matches(window.button)).isFalse();
    } finally {
      window.destroy();
      ScreenLock.instance().release(this);
    }
  }

  public void shouldReturnFalseIfFrameIsNotShowingAndTitleIsNotEqualToExpected() {
    JButtonByTextMatcher matcher = JButtonByTextMatcher.withTextAndShowing("Hello");
    JButton button = button().withText("Bye").createNew();
    assertThat(matcher.matches(button)).isFalse();
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

    final JButton button = new JButton("Hello");

    private MyWindow() {
      super(JButtonByTextMatcherTest.class);
      addComponents(button);
    }
  }
}
