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

import javax.swing.JTextField;

import org.testng.annotations.Test;

import org.fest.swing.core.ScreenLock;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.factory.JTextFields.textField;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link JTextComponentByTextMatcher}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test public class JTextComponentByTextMatcherTest {

  public void shouldReturnTrueIfTitleIsEqualToExpected() {
    String text = "Hello";
    JTextComponentByTextMatcher matcher = JTextComponentByTextMatcher.withText(text);
    JTextField textField = textField().withText(text).createNew();
    assertThat(matcher.matches(textField)).isTrue();
  }

  public void shouldReturnFalseIfTitleIsNotEqualToExpected() {
    JTextComponentByTextMatcher matcher = JTextComponentByTextMatcher.withText("Hello");
    JTextField textField = textField().withText("Bye").createNew();
    assertThat(matcher.matches(textField)).isFalse();
  }

  @Test(groups = GUI)
  public void shouldReturnTrueIfFrameIsShowingAndTitleIsEqualToExpected() {
    ScreenLock.instance().acquire(this);
    MyWindow window = MyWindow.createAndShow();
    try {
      JTextComponentByTextMatcher matcher = JTextComponentByTextMatcher.withTextAndShowing("Hello");
      assertThat(matcher.matches(window.textField)).isTrue();
    } finally {
      window.destroy();
      ScreenLock.instance().release(this);
    }
  }

  public void shouldReturnFalseIfFrameIsNotShowingAndTitleIsEqualToExpected() {
    String text = "Hello";
    JTextComponentByTextMatcher matcher = JTextComponentByTextMatcher.withTextAndShowing(text);
    JTextField textField = textField().withText(text).createNew();
    assertThat(matcher.matches(textField)).isFalse();
  }

  @Test(groups = GUI)
  public void shouldReturnFalseIfFrameIsShowingAndTitleIsNotEqualToExpected() {
    ScreenLock.instance().acquire(this);
    MyWindow window = MyWindow.createAndShow();
    try {
      JTextComponentByTextMatcher matcher = JTextComponentByTextMatcher.withTextAndShowing("Bye");
      assertThat(matcher.matches(window.textField)).isFalse();
    } finally {
      window.destroy();
      ScreenLock.instance().release(this);
    }
  }

  public void shouldReturnFalseIfFrameIsNotShowingAndTitleIsNotEqualToExpected() {
    JTextComponentByTextMatcher matcher = JTextComponentByTextMatcher.withTextAndShowing("Hello");
    JTextField textField = textField().withText("Bye").createNew();
    assertThat(matcher.matches(textField)).isFalse();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createAndShow() {
      MyWindow window = new MyWindow();
      window.display();
      return window;
    }

    final JTextField textField = new JTextField("Hello");

    private MyWindow() {
      super(JLabelByTextMatcherTest.class);
      addComponents(textField);
    }
  }
}
