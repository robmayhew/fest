/*
 * Created on Apr 1, 2008
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
package org.fest.swing.core;

import javax.swing.JButton;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.testing.TestFrame;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link NameMatcher}</code>.
 *
 * @author Alex Ruiz
 */
public class NameMatcherTest {

  private static final String NAME = "c";
  
  private JButton button;
  private NameMatcher matcher;
  
  @BeforeMethod public void setUp() {
    button = new JButton("Click Me");
    button.setName(NAME);
    matcher = new NameMatcher(NAME);
    assertThat(matcher.requireShowing()).isFalse();
  }

  @Test public void shouldReturnTrueIfNameMatches() {
    assertThat(matcher.matches(button)).isTrue();
  }
  
  @Test public void shouldReturnFalseIsNameNotMatching() {
    button.setName("b");
    assertThat(matcher.matches(button)).isFalse();
  }
  
  @Test(expectedExceptions = NullPointerException.class) 
  public void shouldThrowErrorIfNameIsNull() {
    new NameMatcher(null);
  }
  
  @Test(expectedExceptions = IllegalArgumentException.class) 
  public void shouldThrowErrorIfNameIsEmpty() {
    new NameMatcher("");
  }

  @Test(groups = GUI) 
  public void shouldReturnTrueIfNameMatchingAndIsShowing() {
    TestFrame frame = new TestFrame(NameMatcherTest.class);
    frame.add(button);
    frame.display();
    matcher = new NameMatcher(NAME, true);
    assertThat(matcher.matches(button)).isTrue();
    frame.destroy();
  }

  @Test(groups = GUI) 
  public void shouldReturnFalseIfNameNotMatchingAndIsShowing() {
    TestFrame frame = new TestFrame(NameMatcherTest.class);
    frame.add(button);
    frame.display();
    matcher = new NameMatcher("b", true);
    assertThat(matcher.matches(button)).isFalse();
    frame.destroy();
  }
  
  @Test public void shouldReturnFalseIfNameMatchingAndIsNotShowing() {
    matcher = new NameMatcher(NAME, true);
    assertThat(matcher.matches(button)).isFalse();
  }

  @Test public void shouldReturnFalseIfNameNotMatchingAndIsNotShowing() {
    matcher = new NameMatcher("b", true);
    assertThat(matcher.matches(button)).isFalse();
  }
  
  @Test public void shouldReturnNameAndIsShowingInToString() {
    assertThat(matcher.toString()).contains("name='c'").contains("requireShowing=false");
  }
}
