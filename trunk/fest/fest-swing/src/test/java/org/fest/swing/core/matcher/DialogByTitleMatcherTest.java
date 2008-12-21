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

import javax.swing.JDialog;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.lock.ScreenLock;
import org.fest.swing.test.swing.TestDialog;
import org.fest.swing.test.swing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.factory.JDialogs.dialog;
import static org.fest.swing.test.core.TestGroups.GUI;

/**
 * Tests for <code>{@link DialogByTitleMatcher}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test public class DialogByTitleMatcherTest {

  @BeforeMethod public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  public void shouldReturnTrueIfTitleIsEqualToExpected() {
    String title = "Hello";
    DialogByTitleMatcher matcher = DialogByTitleMatcher.withTitle(title);
    JDialog dialog = dialog().withTitle(title).createNew();
    assertThat(matcher.matches(dialog)).isTrue();
  }

  public void shouldReturnFalseIfTitleIsNotEqualToExpected() {
    DialogByTitleMatcher matcher = DialogByTitleMatcher.withTitle("Hello");
    JDialog dialog = dialog().withTitle("Bye").createNew();
    assertThat(matcher.matches(dialog)).isFalse();
  }

  @Test(groups = GUI)
  public void shouldReturnTrueIfDialogIsShowingAndTitleIsEqualToExpected() {
    ScreenLock.instance().acquire(this);
    String title = "Hello";
    JDialog dialog = dialog().withTitle(title).createAndShow();
    try {
      DialogByTitleMatcher matcher = DialogByTitleMatcher.withTitleAndShowing(title);
      assertThat(matcher.matches(dialog)).isTrue();
    } finally {
      ScreenLock.instance().release(this);
    }
  }

  public void shouldReturnFalseIfDialogIsNotShowingAndTitleIsEqualToExpected() {
    String title = "Hello";
    DialogByTitleMatcher matcher = DialogByTitleMatcher.withTitleAndShowing(title);
    JDialog dialog = dialog().withTitle(title).createNew();
    assertThat(matcher.matches(dialog)).isFalse();
  }

  @Test(groups = GUI)
  public void shouldReturnFalseIfDialogIsShowingAndTitleIsNotEqualToExpected() {
    ScreenLock.instance().acquire(this);
    TestWindow window = TestWindow.createAndShowNewWindow(DialogByTitleMatcher.class);
    TestDialog dialog = TestDialog.createAndShowNewDialog(window);
    try {
      DialogByTitleMatcher matcher = DialogByTitleMatcher.withTitleAndShowing("Hello");
      assertThat(matcher.matches(dialog)).isFalse();
    } finally {
      dialog.destroy();
      window.destroy();
      ScreenLock.instance().release(this);
    }
  }

  public void shouldReturnFalseIfDialogIsNotShowingAndTitleIsNotEqualToExpected() {
    DialogByTitleMatcher matcher = DialogByTitleMatcher.withTitleAndShowing("Hello");
    JDialog dialog = dialog().withTitle("Bye").createNew();
    assertThat(matcher.matches(dialog)).isFalse();
  }
}
