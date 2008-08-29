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

import javax.swing.JTabbedPane;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.LocationUnavailableException;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.factory.JTabbedPanes.tabbedPane;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link JTabbedPaneLocation}</code>.
 *
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class JTabbedPaneLocationTest {

  private JTabbedPane tabbedPane;
  private JTabbedPaneLocation location;

  @BeforeMethod public void setUp() {
    tabbedPane = tabbedPane().withTabs("one", "two")
                             .createInEDT();
    location = new JTabbedPaneLocation();
  }

  public void shouldReturnIndexOfTabTitle() {
    int index = location.indexOf(tabbedPane, "two");
    assertThat(index).isEqualTo(1);
  }

  public void shouldThrowErrorIfCannotFindTabWithGivenTitle() {
    try {
      location.indexOf(tabbedPane, "three");
      fail();
    } catch (LocationUnavailableException e) {
      assertThat(e).message().isEqualTo("Unable to find a tab with title 'three'");
    }
  }

  public void shouldPassIfIndexIfValid() {
    location.validateIndex(tabbedPane, 0);
  }

  public void shouldFailIfIndexIsNegative() {
    try {
      location.validateIndex(tabbedPane, -1);
      fail();
    } catch (ActionFailedException e) {
      assertThat(e).message().isEqualTo("Index <-1> is not within the JTabbedPane bounds of <0> and <1> (inclusive)");
    }
  }

  public void shouldFailIfIndexIsGreaterThanLastTabIndex() {
    try {
      location.validateIndex(tabbedPane, 2);
      fail();
    } catch (ActionFailedException e) {
      assertThat(e).message().isEqualTo("Index <2> is not within the JTabbedPane bounds of <0> and <1> (inclusive)");
    }
  }
}
