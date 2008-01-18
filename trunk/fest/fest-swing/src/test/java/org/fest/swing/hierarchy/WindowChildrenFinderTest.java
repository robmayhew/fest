/*
 * Created on Oct 25, 2007
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

import java.awt.Component;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JTextField;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.testing.TestDialog;
import org.fest.swing.testing.TestFrame;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link WindowChildrenFinder}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class WindowChildrenFinderTest {

  private WindowChildrenFinder finder;
  
  @BeforeMethod public void setUp() {
    finder = new WindowChildrenFinder();
  }
  
  @Test public void shouldReturnEmptyCollectionIfComponentIsNotWindow() {
    assertThat(finder.nonExplicitChildrenOf(new JTextField())).isEmpty();
  }
  
  @Test public void shouldReturnEmptyCollectionIfComponentIsNull() {
    assertThat(finder.nonExplicitChildrenOf(null)).isEmpty();
  }
  
  @Test public void shouldReturnEmptyCollectionIfWindowNotHavingOwnedWindows() {
    assertThat(finder.nonExplicitChildrenOf(new JFrame())).isEmpty();
  }
  
  @Test public void shouldReturnOwnedWindowsIfComponentIsWindow() {
    TestFrame frame = TestFrame.showInTest(getClass());
    TestDialog dialog = TestDialog.showInTest(frame);
    dialog.display();
    Collection<Component> children = finder.nonExplicitChildrenOf(frame);
    assertThat(children).containsOnly(dialog);
    dialog.destroy();
    frame.destroy();
  }
}
