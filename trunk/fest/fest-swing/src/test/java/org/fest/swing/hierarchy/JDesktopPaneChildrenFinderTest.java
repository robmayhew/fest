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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.hierarchy;

import java.awt.Component;
import java.util.Collection;

import javax.swing.JTextField;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.hierarchy.MDIFrame.showInTest;

/**
 * Tests for <code>{@link JDesktopPaneChildrenFinder}</code>.
 *
 * @author Alex Ruiz
 */
public class JDesktopPaneChildrenFinderTest {

  private JDesktopPaneChildrenFinder finder;
  
  @BeforeMethod public void setUp() {
    finder = new JDesktopPaneChildrenFinder();
  }
  
  @Test public void shouldReturnEmptyCollectionIfComponentIsNotJDesktopPane() {
    assertThat(finder.nonExplicitChildrenOf(new JTextField())).isEmpty();
  }
  
  @Test public void shouldReturnEmptyCollectionIfComponentIsNull() {
    assertThat(finder.nonExplicitChildrenOf(null)).isEmpty();
  }
  
  @Test public void shouldReturnIconifiedInternalFramesIfComponentIsJDesktopPane() throws Exception {
    MDIFrame frame = showInTest(getClass());
    frame.internalFrame().setIcon(true);
    Collection<Component> children = finder.nonExplicitChildrenOf(frame.desktop());
    assertThat(children).containsOnly(frame.internalFrame());
    frame.destroy();
  }
}
