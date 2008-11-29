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

import java.awt.Frame;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.core.GenericTypeMatcher;

import static java.lang.String.valueOf;

import static org.fest.util.Objects.areEqual;
import static org.fest.util.Strings.*;

/**
 * Understands matching a <code>{@link Frame}</code> whose title is equal to the provided one.
 *
 * @author Alex Ruiz
 */
public class FrameByTitleMatcher extends GenericTypeMatcher<Frame> {

  /**
   * Creates a new <code>{@link FrameByTitleMatcher}</code> that matches a <code>{@link Frame}</code> whose title is
   * equal to the provided one. The <code>{@link Frame}</code> to match does not have to be showing.
   * @param title the expected title.
   * @return the created matcher.
   */
  public static FrameByTitleMatcher withTitle(String title) {
    return new FrameByTitleMatcher(title);
  }

  /**
   * Creates a new <code>{@link FrameByTitleMatcher}</code> that matches a <code>{@link Frame}</code> whose title is
   * equal to the provided one. The <code>{@link Frame}</code> <strong>should</strong> be showing.
   * @param title the expected title.
   * @return the created matcher.
   */
  public static FrameByTitleMatcher withTitleAndShowing(String title) {
    return new FrameByTitleMatcher(title, true);
  }

  private final String title;

  private FrameByTitleMatcher(String title) {
    this(title, false);
  }

  private FrameByTitleMatcher(String title, boolean requireShowing) {
    super(requireShowing);
    this.title = title;
  }

  /**
   * Indicates whether the title of the given <code>{@link Frame}</code> is equal to the one specified in this matcher.
   * <p>
   * <b>Note:</b> This method is <b>not</b> executed in the event dispatch thread (EDT.) Clients are responsible for 
   * invoking this method in the EDT.
   * </p>
   * @param frame the <code>Frame</code> to verify.
   * @return <code>true</code> if the title of the <code>Frame</code> is equal to the one specified in this matcher,
   * otherwise <code>false</code>.
   */
  @RunsInCurrentThread
  protected boolean isMatching(Frame frame) {
    return areEqual(frame.getTitle(), title);
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
