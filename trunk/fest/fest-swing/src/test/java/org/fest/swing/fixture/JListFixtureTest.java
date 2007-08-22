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

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Arrays.array;
import static org.fest.util.Collections.list;

import org.testng.annotations.Test;

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
    assertThat(targetFixture.contents()).isEqualTo(array("one", "two", "three"));
  }
  
  @Test public void shouldSelectItemAtGivenIndex() {
    targetFixture.selectItem(2);
    assertThat(targetFixture.target.getSelectedValue()).equals("three");
  }

  @Test public void shouldSelectItemWithGivenText() {
    targetFixture.selectItem("two");
    assertThat(targetFixture.target.getSelectedValue()).equals("two");
  }

  @Test public void shouldReturnValueAtGivenIndex() {
    assertThat(targetFixture.valueAt(2)).isEqualTo("three");
  }
  
  @Test public void shouldDragAndDropValueUsingGivenNames() {
    targetFixture.drag("two");
    dropTargetFixture.drop("six");
    assertThat(target.elements()).isEqualTo(array("one", "three"));
    assertThat(dropTarget.elements()).isEqualTo(array("four", "five", "six", "two"));
  }
  
  @Test(/*dependsOnMethods = "shouldDragAndDropValueUsingGivenNames"*/)
  public void shouldDrop() {
    targetFixture.drag("two");
    dropTargetFixture.drop();
    assertThat(target.elements()).isEqualTo(array("one", "three"));
    assertThat(dropTarget.elements()).hasSize(4);
  }
  
  @Test public void shouldDragAndDropValueUsingGivenIndices() {
    targetFixture.drag(2);
    dropTargetFixture.drop(1);
    assertThat(target.elements()).isEqualTo(array("one", "two"));
    assertThat(dropTarget.elements()).isEqualTo(array("four", "three", "five", "six"));
  }

  protected ComponentFixture<JList> createFixture() {
    targetFixture = new JListFixture(robot(), "target");
    return targetFixture;
  }

  protected JList createTarget() {
    target = new TestList("target", list("one", "two", "three"));
    return target;
  }

  @Override protected void afterSetUp() {
    dropTarget = new TestList("dropTarget", list("four", "five", "six"));
    dropTargetFixture = new JListFixture(robot(), dropTarget);
    window().add(dropTarget);
    window().setSize(new Dimension(600, 400));
  }
  
  private static class TestList extends JList {
    private static final long serialVersionUID = 1L;

    private final DefaultListModel model = new DefaultListModel();

    TestList(String name, List<String> elements) {
      setDragEnabled(true);
      for (String e : elements) model.addElement(e);
      setModel(model);
      setName(name);
      setTransferHandler(new ListTransferHandler());
    }
    
    String[] elements() {
      List<String> elements = new ArrayList<String>();
      int count = model.getSize();
      for (int i = 0; i < count; i++) elements.add((String)model.get(i));
      return elements.toArray(new String[0]);
    }
  }
}
