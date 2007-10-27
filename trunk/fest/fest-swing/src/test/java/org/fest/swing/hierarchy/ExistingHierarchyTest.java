/*
 * Created on Oct 20, 2007
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
import java.awt.Window;
import java.util.Collection;

import javax.swing.JInternalFrame;
import javax.swing.JTextField;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.Reflection.field;

import static org.fest.swing.hierarchy.MockChildrenFinder.mock;
import static org.fest.swing.hierarchy.MockChildrenFinder.MethodToMock.CHILDREN_OF;
import static org.fest.swing.util.ComponentCollections.empty;

import org.fest.swing.TestFrame;
import org.fest.swing.monitor.WindowMonitor;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ExistingHierarchy}</code>.
 *
 * @author Alex Ruiz
 */
public class ExistingHierarchyTest {

  private ExistingHierarchy hierarchy;
  
  @BeforeMethod public void setUp() throws Exception {
    hierarchy = new ExistingHierarchy();
  }
  
  @Test public void shouldReturnAllRootWindows() {
    Collection<Window> rootWindows = WindowMonitor.instance().rootWindows();
    assertThat(hierarchy.rootWindows()).isEqualTo(rootWindows);
  }
  
  @Test public void shouldAlwaysContainGivenComponent() {
    assertThat(hierarchy.contains(new JTextField())).isTrue();
  }
  
  @Test public void shouldReturnParentOfComponent() {
    TestFrame frame = new TestFrame(getClass());
    JTextField textField = new JTextField();
    frame.add(textField);
    assertThat(hierarchy.parentOf(textField)).isSameAs(frame.getContentPane());
    frame.beDisposed();
  }
  
  @Test public void shouldReturnParentOfInternalFrame() {
    MDIFrame frame = MDIFrame.show(getClass());
    JInternalFrame internalFrame = frame.internalFrame();
    assertThat(hierarchy.parentOf(internalFrame)).isSameAs(internalFrame.getDesktopIcon().getDesktopPane());
    frame.beDisposed();
  }
  
  @Test public void shouldReturnSubcomponents() throws Exception {
    final Component c = new JTextField();
    final ChildrenFinder finder = mock(CHILDREN_OF);
    field("childrenFinder").ofType(ChildrenFinder.class).in(hierarchy).set(finder);
    final Collection<Component> children = empty();
    new EasyMockTemplate(finder) {
      @Override protected void expectations() {
        expect(finder.childrenOf(c)).andReturn(children);
      }

      @Override protected void codeToTest() {
        assertThat(hierarchy.childrenOf(c)).isSameAs(children);
      }
    }.run();
  }
}
