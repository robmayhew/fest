/*
 * Created on Jan 10, 2008
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
import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.lock.ScreenLock;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link NameAndTypeMatcher}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class NameAndTypeMatcherTest {
  
  private static final String NAME = "my button";

  private MyWindow window;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    ScreenLock.instance().acquire(this);
    window = MyWindow.createNew();
  }

  @AfterMethod public void tearDown() {
    try {
      window.destroy();
    } finally {
       ScreenLock.instance().release(this);
    }
  }

  @Test public void shouldReturnTrueIfNameMatches() {
    NameAndTypeMatcher matcher = new NameAndTypeMatcher(NAME);
    assertThat(matcher.matches(window.button)).isTrue();
  }

  @Test public void shouldReturnFalseIsNameDoesNotMatch() {
    NameAndTypeMatcher matcher = new NameAndTypeMatcher("Hello");
    assertThat(matcher.matches(window.button)).isFalse();
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfNameIsNull() {
    new NameAndTypeMatcher(null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfNameIsEmpty() {
    new NameAndTypeMatcher("");
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfTypeIsNull() {
    new NameAndTypeMatcher(NAME, null);
  }

  public void shouldReturnTrueIfNameMatchesWhenNotRequiringShowing() {
    window.display();
    NameAndTypeMatcher matcher = new NameAndTypeMatcher(NAME);
    assertThat(matcher.matches(window.button)).isTrue();
  }

  public void shouldReturnFalseIfNameDoesNotMatchAndWhenRequiringShowing() {
    window.display();
    NameAndTypeMatcher matcher = new NameAndTypeMatcher("b", true);
    assertThat(matcher.matches(window.button)).isFalse();
  }

  public void shouldReturnFalseIfNameMatchesAndIsShowingDoesNotMatch() {
    NameAndTypeMatcher matcher = new NameAndTypeMatcher(NAME, true);
    assertThat(matcher.matches(window.button)).isFalse();
  }

  public void shouldReturnFalseIfNameAndIsShowingDoNotMatch() {
    NameAndTypeMatcher matcher = new NameAndTypeMatcher("b", true);
    assertThat(matcher.matches(window.button)).isFalse();
  }

  public void shouldReturnTrueIfNameAndTypeMatchWhenNotRequiringShowing() {
    window.display();
    NameAndTypeMatcher matcher = new NameAndTypeMatcher(NAME, JButton.class);
    assertThat(matcher.matches(window.button)).isTrue();
  }

  public void shouldReturnFalseIfTypeDoesNotMatchAndWhenRequiringShowing() {
    window.display();
    NameAndTypeMatcher matcher = new NameAndTypeMatcher("b", JTextField.class, true);
    assertThat(matcher.matches(window.button)).isFalse();
  }

  public void shouldReturnFalseIfNameAndTypeMatchAndIsShowingDoesNotMatch() {
    NameAndTypeMatcher matcher = new NameAndTypeMatcher(NAME, JButton.class, true);
    assertThat(matcher.matches(window.button)).isFalse();
  }

  public void shouldReturnFalseIfNothingMatches() {
    NameAndTypeMatcher matcher = new NameAndTypeMatcher("b", JTextField.class, true);
    assertThat(matcher.matches(window.button)).isFalse();
  }
  
  public void shouldImplementToString() {
    NameAndTypeMatcher matcher = new NameAndTypeMatcher(NAME);
    assertThat(matcher.toString()).contains("name='my button'")
                                  .contains("type=java.awt.Component")
                                  .contains("requireShowing=false");
  }

  protected static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JButton button = new JButton("A Button");

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(NameAndTypeMatcherTest.class);
      addComponents(button);
      button.setName(NAME);
    }
  }
}
