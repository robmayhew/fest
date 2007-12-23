/*
 * Created on Aug 6, 2007
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
package org.fest.swing.core;

import javax.swing.JButton;
import javax.swing.JLabel;

import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link GenericTypeMatcher}</code>.
 *
 * @author Yvonne Wang
 */
public class GenericTypeMatcherTest {
  
  @Test public void shouldReturnTrueIfTypeAndSearchCriteriaMatch() {
    GenericTypeMatcher<JButton> matcher = new GenericTypeMatcher<JButton>() {
      @Override protected boolean isMatching(JButton component) {
        return true;
      }
    };
    assertThat(matcher.matches(new JButton())).isTrue();
  }
  
  @Test public void shouldReturnFalseIfTypeMatchesButNotSearchCriteria() {
    GenericTypeMatcher<JButton> matcher = new GenericTypeMatcher<JButton>() {
      @Override protected boolean isMatching(JButton component) {
        return false;
      }
    };
    assertThat(matcher.matches(new JButton())).isFalse();
  }

  @Test public void shouldReturnFalseIfSearchCriteriaMatchesButNotType() {
    GenericTypeMatcher<JButton> matcher = new GenericTypeMatcher<JButton>() {
      @Override protected boolean isMatching(JButton component) {
        return true;
      }
    };
    assertThat(matcher.matches(new JLabel())).isFalse();
  }

  @Test public void shouldReturnFalseIfSearchCriteriaAndTypeNotMatching() {
    GenericTypeMatcher<JButton> matcher = new GenericTypeMatcher<JButton>() {
      @Override protected boolean isMatching(JButton component) {
        return false;
      }
    };
    assertThat(matcher.matches(new JLabel())).isFalse();
  }
}
