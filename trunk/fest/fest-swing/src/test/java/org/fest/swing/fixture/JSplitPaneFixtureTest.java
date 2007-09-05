/*
 * Created on Sep 4, 2007
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

import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JSplitPane;

import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.*;

/**
 * Tests for <code>{@link JSplitPaneFixture}</code>.
 *
 * @author Yvonne Wang
 */
public class JSplitPaneFixtureTest extends ComponentFixtureTestCase<JSplitPane> {

  private JSplitPaneFixture fixture;
  
  private JSplitPane target;
  
  @Test public void shouldMoveDividerToGivenLocation() {
    PanesWidth before = new PanesWidth(target);
    int delta = 100;
    fixture.moveDividerTo(target.getDividerLocation() + delta);
    PanesWidth after = new PanesWidth(target);
    assertThat(after.left).isEqualTo(before.left + delta);
    assertThat(after.right).isEqualTo(before.right - delta);
  }

  
  
  private static class PanesWidth {
    final int left;
    final int right;

    PanesWidth(JSplitPane splitPane) {
      this.left = splitPane.getLeftComponent().getWidth();
      this.right = splitPane.getRightComponent().getWidth();
    }
  }
  
  protected ComponentFixture<JSplitPane> createFixture() {
    fixture = new JSplitPaneFixture(robot(), "target");
    return fixture;
  }

  protected JSplitPane createTarget() {
    target = new JSplitPane(HORIZONTAL_SPLIT, new JList(), new JList());
    target.setDividerLocation(200);
    target.setName("target");
    target.setPreferredSize(new Dimension(400, 200));
    return target;
  }

  @Override protected void afterSetUp() {
    window().setSize(new Dimension(600, 400));
  }
}
