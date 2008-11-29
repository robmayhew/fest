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

import java.awt.Dialog;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.core.GenericTypeMatcher;

import static java.lang.String.valueOf;

import static org.fest.util.Objects.areEqual;
import static org.fest.util.Strings.*;

/**
 * Understands matching a <code>{@link Dialog}</code> whose title is equal to the provided one.
 *
 * @author Alex Ruiz 
 */
public class DialogByTitleMatcher extends GenericTypeMatcher<Dialog> {

  /**
   * Creates a new <code>{@link DialogByTitleMatcher}</code> that matches a <code>{@link Dialog}</code> whose title is
   * equal to the provided one. The <code>{@link Dialog}</code> to match does not have to be showing.
   * @param title the expected title.
   * @return the created matcher.
   */
  public static DialogByTitleMatcher withTitle(String title) {
    return new DialogByTitleMatcher(title);
  }
  
  /**
   * Creates a new <code>{@link DialogByTitleMatcher}</code> that matches a <code>{@link Dialog}</code> whose title is
   * equal to the provided one. The <code>{@link Dialog}</code> <strong>should</strong> be showing.
   * @param title the expected title.
   * @return the created matcher.
   */
  public static DialogByTitleMatcher withTitleAndShowing(String title) {
    return new DialogByTitleMatcher(title, true);
  }
  
  private final String title;

  private DialogByTitleMatcher(String title) {
    this(title, false);
  }

  private DialogByTitleMatcher(String title, boolean requireShowing) {
    super(requireShowing);
    this.title = title;
  }

  /** 
   * Indicates whether the title of the given <code>{@link Dialog}</code> is equal to the one specified in this matcher.
   * <p>
   * <b>Note:</b> This method is <b>not</b> executed in the event dispatch thread (EDT.) Clients are responsible for 
   * invoking this method in the EDT.
   * </p>
   * @param dialog the <code>Dialog</code> to verify.
   * @return <code>true</code> if the title of the <code>Dialog</code> is equal to the one specified in this matcher,
   * otherwise <code>false</code>. 
   */
  @RunsInCurrentThread
  protected boolean isMatching(Dialog dialog) {
    return areEqual(dialog.getTitle(), title);
  }

  @Override public String toString() {
    return concat(
        getClass().getName(), "[",
        "title=", quote(title), ", ",
        "requireShowing=", valueOf(requireShowing()), 
        "]"
    );
  }
}
