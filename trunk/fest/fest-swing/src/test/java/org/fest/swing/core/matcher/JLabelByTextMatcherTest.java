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

import org.testng.annotations.Test;

import org.fest.swing.core.GuiQuery;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link JLabelByTextMatcher}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class JLabelByTextMatcherTest {

  public void shouldReturnTrueIfTitleIsEqualToExpected() {
    String text = "Hello";
    JLabelByTextMatcher matcher = JLabelByTextMatcher.withText(text);
    JLabel label = new JLabel(text);
    assertThat(matcher.matches(label)).isTrue();
  }
  
  public void shouldReturnFalseIfTitleIsNotEqualToExpected() {
    JLabelByTextMatcher matcher = JLabelByTextMatcher.withText("Hello");
    JLabel label = new JLabel("Bye");
    assertThat(matcher.matches(label)).isFalse();
  }
  
  @Test(groups = GUI)
  public void shouldReturnTrueIfFrameIsShowingAndTitleIsEqualToExpected() {
    MyWindow window = MyWindow.showNew();
    try {
      JLabelByTextMatcher matcher = JLabelByTextMatcher.withTextAndShowing("Hello");
      assertThat(matcher.matches(window.label)).isTrue();
    } finally {
      window.destroy();
    }
  } 
  
  public void shouldReturnFalseIfFrameIsNotShowingAndTitleIsEqualToExpected() {
    String text = "Hello";
    JLabelByTextMatcher matcher = JLabelByTextMatcher.withTextAndShowing(text);
    JLabel label = new JLabel(text);
    assertThat(matcher.matches(label)).isFalse();    
  }

  @Test(groups = GUI)
  public void shouldReturnFalseIfFrameIsShowingAndTitleIsNotEqualToExpected() {
    MyWindow window = MyWindow.showNew();
    try {
      JLabelByTextMatcher matcher = JLabelByTextMatcher.withTextAndShowing("Bye");
      assertThat(matcher.matches(window.label)).isFalse();
    } finally {
      window.destroy();
    }
  }
  
  public void shouldReturnFalseIfFrameIsNotShowingAndTitleIsNotEqualToExpected() {
    JLabelByTextMatcher matcher = JLabelByTextMatcher.withTextAndShowing("Hello");
    JLabel label = new JLabel("Bye");
    assertThat(matcher.matches(label)).isFalse();    
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow showNew() {
      MyWindow window = execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() { return new MyWindow(); }
      });
      window.display();
      return window;
    }
    
    final JLabel label = new JLabel("Bye");
    
    MyWindow() {
      super(JLabelByTextMatcherTest.class);
      addComponents(label);
    }
  }
}
