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

import javax.swing.text.JTextComponent;

import org.fest.swing.core.GenericTypeMatcher;

import static org.fest.swing.query.JTextComponentTextQuery.textOf;
import static org.fest.util.Objects.areEqual;

/**
 * Understands matching a <code>{@link JTextComponent}</code> whose text is equal to the provided one.
 *
 * @author Alex Ruiz
 */
public class JTextComponentByTextMatcher extends GenericTypeMatcher<JTextComponent> {

  /**
   * Creates a new <code>{@link JTextComponentByTextMatcher}</code> that matches a <code>{@link JTextComponent}</code>
   * whose text is equal to the provided one. The <code>{@link JTextComponent}</code> to match does not have to be
   * showing.
   * @param text the expected text.
   * @return the created matcher.
   */
  public static JTextComponentByTextMatcher withText(String text) {
    return new JTextComponentByTextMatcher(text);
  }

  /**
   * Creates a new <code>{@link JTextComponentByTextMatcher}</code> that matches a <code>{@link JTextComponent}</code>
   * whose text is equal to the provided one. The <code>{@link JTextComponent}</code> <strong>should</strong> be
   * showing.
   * @param text the expected text.
   * @return the created matcher.
   */
  public static JTextComponentByTextMatcher withTextAndShowing(String text) {
    return new JTextComponentByTextMatcher(text, true);
  }

  private final String text;

  private JTextComponentByTextMatcher(String text) {
    this(text, false);
  }

  private JTextComponentByTextMatcher(String text, boolean requireShowing) {
    super(requireShowing);
    this.text = text;
  }

  /**
   * Indicates whether the text of the given <code>{@link JTextComponent}</code> is equal to the text in this matcher.
   * @param textComponent the <code>JTextComponent</code> to match.
   * @return <code>true</code> if the text in the <code>JTextComponent</code> is equal to the text in this matcher,
   * <code>false</code> otherwise.
   */
  protected boolean isMatching(JTextComponent textComponent) {
    return areEqual(textOf(textComponent), text);
  }
}
