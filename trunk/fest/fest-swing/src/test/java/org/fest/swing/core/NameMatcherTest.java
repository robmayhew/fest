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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link NameMatcher}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class NameMatcherTest {

  private static final String NAME = "my button";

  private NameMatcher matcher;
  private MyWindow window;

  @BeforeMethod public void setUp() {
    ScreenLock.instance().acquire(this);
    window = MyWindow.createNew();
    matcher = new NameMatcher(NAME);
    assertThat(matcher.requireShowing()).isFalse();
  }

  @AfterMethod public void tearDown() {
    window.destroy();
    ScreenLock.instance().release(this);
  }

  @Test public void shouldReturnTrueIfNameMatches() {
    assertThat(matcher.matches(window.button)).isTrue();
  }

  @Test public void shouldReturnFalseIsNameNotMatching() {
    execute(new GuiTask() {
      protected void executeInEDT() {
        window.button.setName("Hello");
      }
    });
    assertThat(matcher.matches(window.button)).isFalse();
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfNameIsNull() {
    new NameMatcher(null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfNameIsEmpty() {
    new NameMatcher("");
  }

  public void shouldReturnTrueIfNameMatchingAndIsShowing() {
    window.display();
    assertThat(matcher.matches(window.button)).isTrue();
  }

  public void shouldReturnFalseIfNameNotMatchingAndIsShowing() {
    window.display();
    matcher = new NameMatcher("b", true);
    assertThat(matcher.matches(window.button)).isFalse();
  }

  public void shouldReturnFalseIfNameMatchingAndIsNotShowing() {
    matcher = new NameMatcher(NAME, true);
    assertThat(matcher.matches(window.button)).isFalse();
  }

  public void shouldReturnFalseIfNameNotMatchingAndIsNotShowing() {
    matcher = new NameMatcher("b", true);
    assertThat(matcher.matches(window.button)).isFalse();
  }

  public void shouldReturnNameAndIsShowingInToString() {
    assertThat(matcher.toString()).contains("name='my button'")
                                  .contains("requireShowing=false");
  }

  protected static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JButton button = new JButton("A Button");

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(NameMatcherTest.class);
      addComponents(button);
      button.setName(NAME);
    }
  }
}
