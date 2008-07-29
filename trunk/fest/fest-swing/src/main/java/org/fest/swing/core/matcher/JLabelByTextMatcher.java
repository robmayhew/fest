/*
 * Created on Jul 17, 2008
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

import org.fest.swing.core.GenericTypeMatcher;

import static org.fest.swing.task.GetJLabelTextTask.textOf;
import static org.fest.util.Objects.areEqual;

/**
 * Understands matching a <code>{@link JLabel}</code> whose text is equal to the provided one.
 *
 * @author Alex Ruiz 
 */
public class JLabelByTextMatcher extends GenericTypeMatcher<JLabel> {

  /**
   * Creates a new <code>{@link JLabelByTextMatcher}</code> that matches a <code>{@link JLabel}</code> whose text is
   * equal to the provided one. The <code>{@link JLabel}</code> to match does not have to be showing.
   * @param text the expected text.
   * @return the created matcher.
   */
  public static JLabelByTextMatcher withText(String text) {
    return new JLabelByTextMatcher(text);
  }
  
  /**
   * Creates a new <code>{@link JLabelByTextMatcher}</code> that matches a <code>{@link JLabel}</code> whose text is
   * equal to the provided one. The <code>{@link JLabel}</code> <strong>should</strong> be showing.
   * @param text the expected text.
   * @return the created matcher.
   */
  public static JLabelByTextMatcher withTextAndShowing(String text) {
    return new JLabelByTextMatcher(text, true);
  }

  private final String text;

  private JLabelByTextMatcher(String text) {
    this(text, false);
  }

  private JLabelByTextMatcher(String text, boolean requireShowing) {
    super(requireShowing);
    this.text = text;
  }

  /**
   * Indicates whether the text of the given <code>{@link JLabel}</code> is equal to the text in this matcher.
   * @param label the <code>JLabel</code> to match.
   * @return <code>true</code> if the text in the <code>JLabel</code> is equal to the text in this matcher, 
   * <code>false</code> otherwise.
   */
  protected boolean isMatching(JLabel label) {
    return areEqual(textOf(label), text);
  }
}
