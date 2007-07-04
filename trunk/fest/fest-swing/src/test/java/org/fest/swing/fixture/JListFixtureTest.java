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

import javax.swing.JList;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Arrays.array;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JListFixture}</code>.
 *
 * @author Alex Ruiz 
 */
public class JListFixtureTest extends ComponentFixtureTestCase<JList> {

  private JListFixture fixture;

  @Test public void shouldReturnListContents() {
    assertThat(fixture.contents()).isEqualTo(array("one", "two", "three"));
  }
  
  @Test public void shouldSelectItemAtGivenIndex() {
    fixture.selectItem(2);
    assertThat(fixture.target.getSelectedValue()).equals("three");
  }

  @Test public void shouldSelectItemWithGivenText() {
    fixture.selectItem("two");
    assertThat(fixture.target.getSelectedValue()).equals("two");
  }

  @Test public void shouldReturnValueAtGivenIndex() {
    assertThat(fixture.valueAt(2)).isEqualTo("three");
  }

  protected ComponentFixture<JList> createFixture() {
    fixture = new JListFixture(robot(), "target");
    return fixture;
  }

  protected JList createTarget() {
    JList target = new JList(array("one", "two", "three"));
    target.setName("target");
    return target;
  }
}
