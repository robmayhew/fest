/*
 * Created on Nov 1, 2007
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
package org.fest.swing.hierarchy;

import javax.swing.JInternalFrame;
import javax.swing.JTextField;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.testing.TestFrame;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.hierarchy.MDIFrame.showInTest;

/**
 * Tests for <code>{@link ParentFinder}</code>.
 *
 * @author Alex Ruiz
 */
public class ParentFinderTest {
  
  private ParentFinder finder;
  
  @BeforeMethod public void setUp() {
    finder = new ParentFinder();
  }

  @Test public void shouldReturnParentOfComponent() {
    TestFrame frame = new TestFrame(getClass());
    JTextField textField = new JTextField();
    frame.add(textField);
    assertThat(finder.parentOf(textField)).isSameAs(frame.getContentPane());
    frame.destroy();
  }
  
  @Test public void shouldReturnParentOfInternalFrame() {
    MDIFrame frame = showInTest(getClass());
    JInternalFrame internalFrame = frame.internalFrame();
    assertThat(finder.parentOf(internalFrame)).isSameAs(internalFrame.getDesktopIcon().getDesktopPane());
    frame.destroy();
  }
}
