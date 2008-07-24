/*
 * Created on Apr 5, 2008
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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;

import javax.swing.JButton;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JButtonMatcher}</code>.
 *
 * @author Yvonne Wang
 */
public class JButtonMatcherTest {

  private JButtonMatcher matcher;

  @BeforeMethod public void setUp() {
    matcher = new JButtonMatcher("Hello");
  }

  @Test public void shouldReturnTrueIfTextMatches() {
    boolean matching = matcher.isMatching(new JButton("Hello"));
    assertThat(matching).isTrue();
  }

  @Test public void shouldReturnFalseIfTextDoesNotMatch() {
    boolean matching = matcher.isMatching(new JButton("Hi"));
    assertThat(matching).isFalse();
  }

  @Test public void shouldIncludeTextToMatchInToString() {
    String s = matcher.toString();
    assertThat(s).contains("textToMatch='Hello'");
  }
}
