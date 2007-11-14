/*
 * Created on Jun 12, 2007
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

import org.testng.annotations.Test;
import static org.fest.assertions.Assertions.assertThat;
import org.fest.swing.ClickRecorder;
import static org.fest.swing.MouseButton.LEFT_BUTTON;
import static org.fest.util.Collections.list;

import javax.swing.JList;
import java.awt.Dimension;

/**
 * Tests for <code>{@link JListFixture}</code>.
 *
 * @author Alex Ruiz 
 * @author Yvonne Wang
 */
public class JListFixtureTest extends ComponentFixtureTestCase<JList> {

  private TestList target;
  private JListFixture targetFixture;

  private TestList dropTarget;
  private JListFixture dropTargetFixture;
  
  @Test public void shouldReturnListContents() {
    assertThat(targetFixture.contents()).containsOnly("one", "two", "three");
  }
  
  @Test public void shouldSelectItemAtGivenIndex() {
    targetFixture.selectItem(2);
    assertThat(targetFixture.target.getSelectedValue()).isEqualTo("three");
  }

  @Test public void shouldSelectItemWithGivenText() {
    targetFixture.selectItem("two");
    assertThat(targetFixture.target.getSelectedValue()).isEqualTo("two");
  }

  @Test public void shouldDoubleClickItemAtGivenIndex() {
    ClickRecorder recorder = ClickRecorder.attachTo(targetFixture.target);
    targetFixture.doubleClickItem(2);
    assertThat(targetFixture.target.getSelectedValue()).isEqualTo("three");
    assertThat(recorder.clickCount()).isEqualTo(2);
    assertThat(recorder.clickedButton()).isEqualTo(LEFT_BUTTON);
  }

  @Test public void shouldDoubleClickItemWithGivenText() {
    ClickRecorder recorder = ClickRecorder.attachTo(targetFixture.target);
    targetFixture.doubleClickItem("two");
    assertThat(targetFixture.target.getSelectedValue()).isEqualTo("two");
    assertThat(recorder.clickCount()).isEqualTo(2);
    assertThat(recorder.clickedButton()).isEqualTo(LEFT_BUTTON);
  }

  @Test public void shouldReturnValueAtGivenIndex() {
    assertThat(targetFixture.valueAt(2)).isEqualTo("three");
  }
  
  @Test public void shouldDragAndDropValueUsingGivenNames() {
    targetFixture.drag("two");
    dropTargetFixture.drop("six");
    assertThat(target.elements()).containsOnly("one", "three");
    assertThat(dropTarget.elements()).containsOnly("four", "five", "six", "two");
  }
  
  @Test public void shouldDrop() {
    targetFixture.drag("two");
    dropTargetFixture.drop();
    assertThat(target.elements()).containsOnly("one", "three");
    assertThat(dropTarget.elements()).hasSize(4);
  }
  
  @Test public void shouldDragAndDropValueUsingGivenIndices() {
    targetFixture.drag(2);
    dropTargetFixture.drop(1);
    assertThat(target.elements()).containsOnly("one", "two");
    assertThat(dropTarget.elements()).containsOnly("four", "five", "three", "six");
  }

  protected ComponentFixture<JList> createFixture() {
    targetFixture = new JListFixture(robot(), "target");
    return targetFixture;
  }

  protected JList createTarget() {
    target = new TestList("target", list("one", "two", "three"));
    target.setPreferredSize(listSize());
    return target;
  }

  @Override protected void afterSetUp() {
    dropTarget = new TestList("dropTarget", list("four", "five", "six"));
    dropTarget.setPreferredSize(listSize());
    dropTargetFixture = new JListFixture(robot(), dropTarget);
    window().add(dropTarget);
    window().setSize(new Dimension(600, 400));
  }

  private Dimension listSize() { return new Dimension(50, 100); }
}
