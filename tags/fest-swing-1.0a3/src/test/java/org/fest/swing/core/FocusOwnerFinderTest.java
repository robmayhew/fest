/*
 * Created on Apr 1, 2008
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

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.testing.TestDialog;
import org.fest.swing.testing.TestFrame;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.FocusSetter.setFocusOn;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link FocusOwnerFinder}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class FocusOwnerFinderTest {

  private TestFrame frame;
  private JTextField textField;

  @BeforeMethod public void setUp() {
    frame = new TestFrame(FocusOwnerFinder.class);
    textField = new JTextField(20);
    frame.add(textField);
    frame.display();
  }

  @AfterMethod public void tearDown() {
    frame.destroy();
  }

  public void shouldFindFocusOwner() {
    setFocusOn(textField);
    Component focusOwner = FocusOwnerFinder.focusOwner();
    assertThat(focusOwner).isSameAs(textField);
  }

  public void shouldFindFocusOwnerInHierarchy() {
    setFocusOn(textField);
    Component focusOwner = FocusOwnerFinder.focusOwnerInHierarchy();
    assertThat(focusOwner).isSameAs(textField);
  }

  public void shouldFindFocusInOwnedWindow() {
    TestDialog dialog = new TestDialog(frame);
    JButton button = new JButton("Click me");
    dialog.add(button);
    dialog.display();
    setFocusOn(button);
    Component focusOwner = FocusOwnerFinder.focusOwnerInHierarchy();
    assertThat(focusOwner).isSameAs(button);
    dialog.destroy();
  }
}
