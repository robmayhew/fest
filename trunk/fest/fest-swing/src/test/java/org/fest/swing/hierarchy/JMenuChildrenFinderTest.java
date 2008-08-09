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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link JMenuChildrenFinder}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test public class JMenuChildrenFinderTest {

  private JMenuChildrenFinder finder;
  
  @BeforeMethod public void setUp() {
    finder = new JMenuChildrenFinder();
  }
  
  public void shouldReturnEmptyCollectionIfComponentIsNotJMenu() {
    assertThat(finder.nonExplicitChildrenOf(new JTextField())).isEmpty();
  }
  
  public void shouldReturnEmptyCollectionIfComponentIsNull() {
    assertThat(finder.nonExplicitChildrenOf(null)).isEmpty();
  }
  
  public void shouldReturnPopupMenuIfComponentIsJMenu() {
    final JMenu menu = createMock(JMenu.class);
    final JPopupMenu popup = createMock(JPopupMenu.class);
    new EasyMockTemplate(menu) {
      protected void expectations() {
        expect(menu.getPopupMenu()).andReturn(popup);
      }

      protected void codeToTest() {
        List<Component> children = new ArrayList<Component>(finder.nonExplicitChildrenOf(menu));
        assertThat(children).containsOnly(popup);
      }
    }.run();
  }
}
