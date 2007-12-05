/*
 * Created on Feb 10, 2007
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

import java.awt.Dialog;
import java.awt.Dimension;

import javax.swing.JDialog;

import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

import static org.fest.swing.fixture.ErrorMessageAssert.actual;
import static org.fest.swing.fixture.ErrorMessageAssert.expected;
import static org.fest.swing.fixture.ErrorMessageAssert.property;

import org.fest.swing.annotation.GUITest;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link DialogFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@GUITest
public class DialogFixtureTest extends WindowFixtureTestCase<Dialog> {

  private DialogFixture fixture;
  private JDialog target;

  @Test public void shouldPassIfModalAndExpectingModal() {
    target.setModal(true);
    fixture.requireModal();
  }

  @Test public void shouldFailIfNotModalAndExpectingModal() {
    target.setModal(false);
    try {
      fixture.requireModal();
      fail();
    } catch(AssertionError e) {
      ErrorMessageAssert errorMessage = new ErrorMessageAssert(e, target);
      assertThat(errorMessage).contains(property("modal"), expected("true"), actual("false"));
    }
  }
  
  protected void afterSetUp() {
    fixture.show();
    target.setSize(new Dimension(400, 200));
  }

  protected ComponentFixture<Dialog> createFixture() {
    fixture = new DialogFixture(robot(), target);
    return fixture;
  }

  protected Dialog createTarget() {
    target = new JDialog(window());
    target.setDefaultCloseOperation(HIDE_ON_CLOSE);
    return target;
  }
}
