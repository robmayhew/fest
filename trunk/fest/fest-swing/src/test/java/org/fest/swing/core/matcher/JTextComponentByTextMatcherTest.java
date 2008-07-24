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

import org.fest.swing.core.GuiTask;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link JTextComponentByTextMatcher}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class JTextComponentByTextMatcherTest {

  public void shouldReturnTrueIfTitleIsEqualToExpected() {
    String text = "Hello";
    JTextComponentByTextMatcher matcher = JTextComponentByTextMatcher.withText(text);
    JTextField textField = textField(text);
    assertThat(matcher.matches(textField)).isTrue();
  }
  
  public void shouldReturnFalseIfTitleIsNotEqualToExpected() {
    JTextComponentByTextMatcher matcher = JTextComponentByTextMatcher.withText("Hello");
    JTextField textField = new JTextField("Bye");
    assertThat(matcher.matches(textField)).isFalse();
  }
  
  @Test(groups = GUI)
  public void shouldReturnTrueIfFrameIsShowingAndTitleIsEqualToExpected() {
    String text = "Hello";
    JTextField textField = textField(text);
    TestWindow frame = windowWith(textField);
    try {
      frame.display();
      JTextComponentByTextMatcher matcher = JTextComponentByTextMatcher.withTextAndShowing(text);
      assertThat(matcher.matches(textField)).isTrue();
    } finally {
      frame.destroy();
    }
  } 
  
  public void shouldReturnFalseIfFrameIsNotShowingAndTitleIsEqualToExpected() {
    String text = "Hello";
    JTextComponentByTextMatcher matcher = JTextComponentByTextMatcher.withTextAndShowing(text);
    JTextField textField = textField(text);
    assertThat(matcher.matches(textField)).isFalse();    
  }

  @Test(groups = GUI)
  public void shouldReturnFalseIfFrameIsShowingAndTitleIsNotEqualToExpected() {
    JTextField textField = textField("Bye");
    TestWindow frame = windowWith(textField);
    try {
      frame.display();
      JTextComponentByTextMatcher matcher = JTextComponentByTextMatcher.withTextAndShowing("Hello");
      assertThat(matcher.matches(textField)).isFalse();
    } finally {
      frame.destroy();
    }
  }

  private JTextField textField(final String text) {
    return new GuiTask<JTextField>() {
      protected JTextField executeInEDT() {
        return new JTextField(text);
      }
    }.run();
  }
  
  private TestWindow windowWith(final JTextField textField) {
    return new GuiTask<TestWindow>() {
      protected TestWindow executeInEDT() {
        TestWindow f = new TestWindow(JTextComponentByTextMatcher.class);
        f.add(textField);
        return f;
      }
    }.run();
  }

  public void shouldReturnFalseIfFrameIsNotShowingAndTitleIsNotEqualToExpected() {
    JTextComponentByTextMatcher matcher = JTextComponentByTextMatcher.withTextAndShowing("Hello");
    JTextField textField = new JTextField("Bye");
    assertThat(matcher.matches(textField)).isFalse();    
  }
}
