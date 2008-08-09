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

import org.testng.annotations.Test;

import org.fest.swing.core.GuiQuery;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link JButtonByTextMatcher}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class JButtonByTextMatcherTest {

  public void shouldReturnTrueIfTitleIsEqualToExpected() {
    String text = "Hello";
    JButtonByTextMatcher matcher = JButtonByTextMatcher.withText(text);
    JButton button = new JButton(text);
    assertThat(matcher.matches(button)).isTrue();
  }
  
  public void shouldReturnFalseIfTitleIsNotEqualToExpected() {
    JButtonByTextMatcher matcher = JButtonByTextMatcher.withText("Hello");
    JButton button = new JButton("Bye");
    assertThat(matcher.matches(button)).isFalse();
  }
  
  @Test(groups = GUI)
  public void shouldReturnTrueIfFrameIsShowingAndTitleIsEqualToExpected() {
    final String text = "Hello";
    JButton button = button(text);
    TestWindow frame = frameWith(button);
    try {
      frame.display();
      JButtonByTextMatcher matcher = JButtonByTextMatcher.withTextAndShowing(text);
      assertThat(matcher.matches(button)).isTrue();
    } finally {
      frame.destroy();
    }
  }

  public void shouldReturnFalseIfFrameIsNotShowingAndTitleIsEqualToExpected() {
    String text = "Hello";
    JButtonByTextMatcher matcher = JButtonByTextMatcher.withTextAndShowing(text);
    JButton button = new JButton(text);
    assertThat(matcher.matches(button)).isFalse();    
  }

  @Test(groups = GUI)
  public void shouldReturnFalseIfFrameIsShowingAndTitleIsNotEqualToExpected() {
    JButton button = button("Bye");
    TestWindow frame = frameWith(button);
    try {
      frame.display();
      JButtonByTextMatcher matcher = JButtonByTextMatcher.withTextAndShowing("Hello");
      assertThat(matcher.matches(button)).isFalse();
    } finally {
      frame.destroy();
    }
  }

  private JButton button(final String text) {
    return new GuiQuery<JButton>() {
      protected JButton executeInEDT() {
        return new JButton(text);
      }
    }.run();
  } 

  private TestWindow frameWith(final JButton button) {
    return new GuiQuery<TestWindow>() {
      protected TestWindow executeInEDT() {
        TestWindow f = new TestWindow(JButtonByTextMatcher.class);
        f.add(button);
        return f;
      }
    }.run();
  }

  public void shouldReturnFalseIfFrameIsNotShowingAndTitleIsNotEqualToExpected() {
    JButtonByTextMatcher matcher = JButtonByTextMatcher.withTextAndShowing("Hello");
    JButton button = new JButton("Bye");
    assertThat(matcher.matches(button)).isFalse();    
  }
}
