/*
 * Created on Apr 9, 2007
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

import javax.swing.JComboBox;
import javax.swing.JTextField;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Arrays.array;

import org.fest.swing.annotation.GUITest;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JComboBoxFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@GUITest
public class JComboBoxFixtureTest extends ComponentFixtureTestCase<JComboBox> {

  private JComboBoxFixture fixture;
  
  @Test public void shouldReturnComboBoxContents() {
    assertThat(fixture.contents()).isEqualTo(array("first", "second", "third"));
  }
  
  @Test public void shouldSelectItemAtGivenIndex() {
    fixture.selectItem(2);
    assertThat(fixture.target.getSelectedItem()).isEqualTo("third");
  }

  @Test public void shouldSelectItemWithGivenText() {
    fixture.selectItem("second");
    assertThat(fixture.target.getSelectedItem()).isEqualTo("second");
  }

  @Test public void shouldReturnValueAtGivenIndex() {
    assertThat(fixture.valueAt(2)).isEqualTo("third");
  }

  @Test public void shouldEnterTextInEditableComboBox() {
    fixture.target.setEditable(true);
    fixture.enterText("Text entered by FEST");
    assertThat(targetEditor().getText()).contains("Text entered by FEST");
  }
  
  @Test public void shouldNotEnterTextInNonEditableComboBox() {
    fixture.target.setEditable(false);
    fixture.enterText("Text entered by FEST");
    assertThat(targetEditor().getText()).isEmpty();
  }

  private JTextField targetEditor() {
    return (JTextField)fixture.target.getEditor().getEditorComponent();
  }

  protected ComponentFixture<JComboBox> createFixture() {
    fixture = new JComboBoxFixture(robot(), "target");
    return fixture;
  }

  protected JComboBox createTarget() {
    JComboBox target = new JComboBox(array("first", "second", "third"));
    target.setName("target");
    return target;
  }
}
