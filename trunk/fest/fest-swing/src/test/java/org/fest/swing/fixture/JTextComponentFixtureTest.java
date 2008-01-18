/*
 * Created on Feb 8, 2007
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

import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.testng.annotations.Test;

import org.fest.swing.annotation.GUITest;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.fixture.ErrorMessageAssert.*;

/**
 * Tests for <code>{@link JTextComponentFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@GUITest
public class JTextComponentFixtureTest extends ComponentFixtureTestCase<JTextComponent> {

  private static final String TEXT = "text";
  private JTextComponentFixture fixture;
  
  @Test public void shouldPassIfTextFieldHasMatchingText() {
    fixture.target.setText("Second Text Field");
    fixture.requireText("Second Text Field");
  }
  
  @Test(dependsOnMethods = "shouldPassIfTextFieldHasMatchingText")  
  public void shouldFailIfTextFieldHasNotMatchingText() {
    try {
      fixture.requireText("A Text Field");
      fail();
    } catch (AssertionError e) {
      ErrorMessageAssert errorMessage = new ErrorMessageAssert(e, fixture.target);
      assertThat(errorMessage).contains(property(TEXT), expected("'A Text Field'"), actual("''"));
    }
  }
  
  @Test public void shouldReturnTextFieldText() {
    fixture.target.setText("Second Text Field");
    assertThat("Second Text Field").isEqualTo(fixture.text());
  }

  @Test public void shouldPassIfTextFieldIsEmpty() {
    fixture.target.setText("");
    fixture.requireEmpty();
  }

  @Test(dependsOnMethods = "shouldPassIfTextFieldIsEmpty", expectedExceptions = AssertionError.class) 
  public void shouldFailIfTextFieldIsNotEmpty() {
    fixture.target.setText("Some text");
    try {
      fixture.requireEmpty();
      fail();
    } catch (AssertionError e) {
      ErrorMessageAssert errorMessage = new ErrorMessageAssert(e, fixture.target);
      assertThat(errorMessage).contains(property(TEXT), expected("''"), actual("'Some text'"));
    }
  }
  
  @Test public void shouldEnterTextInTextField() {
    fixture.target.setText("");
    fixture.enterText("Text entered by FEST");
    assertThat(fixture.target.getText()).isEqualTo("Text entered by FEST");
  }
  
  @Test public void shouldDeleteTextInTextField() {
    fixture.target.setText("Some text to delete");
    fixture.deleteText();
    assertThat(fixture.target.getText()).isEmpty();
  }
  
  @Test public void shouldSelectAllTextInTextField() {
    fixture.target.setText("Some text to select");
    fixture.selectAll();
    assertThat(fixture.target.getSelectedText()).isEqualTo("Some text to select");
  }
  
  @Test(dependsOnMethods = "shouldSelectAllTextInTextField") 
  public void shouldNotSelectAllTextIfTextFieldIsEmpty() {
    fixture.target.setText("");
    fixture.selectAll();
    assertThat(fixture.target.getSelectedText()).isEmpty();
  }
  
  @Test public void shouldSelectOnlyGivenText() {
    fixture.enterText("FEST is typing this");
    fixture.select("is typing");
    assertThat(fixture.target.getSelectedText()).isEqualTo("is typing");
  }

  protected ComponentFixture<JTextComponent> createFixture() {
    fixture = new JTextComponentFixture(robot(), "target");
    return fixture;
  }

  protected JTextComponent createTarget() {
    JTextField target = new JTextField();
    target.setName("target");
    target.setColumns(10);
    return target;
  }
}
