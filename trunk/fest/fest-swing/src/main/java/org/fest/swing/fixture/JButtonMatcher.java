/*
 * Created on Nov 17, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.fixture;

import javax.swing.JButton;

import org.fest.swing.core.GenericTypeMatcher;

import static org.fest.util.Strings.*;

/**
 * Understands matching a <code>{@link JButton}</code> by its displayed text.
 *
 * @author Alex Ruiz
 */
final class JButtonMatcher extends GenericTypeMatcher<JButton> {

  private final String textToMatch;

  JButtonMatcher(String textToMatch) {
    this.textToMatch = textToMatch;
  }

  protected boolean isMatching(JButton button) {
    return textToMatch.equals(button.getText());
  }

  @Override public String toString() {
    return concat(
        getClass().getName(), "[",
        "textToMatch=", quote(textToMatch),
        "]"
    );
  }
}
