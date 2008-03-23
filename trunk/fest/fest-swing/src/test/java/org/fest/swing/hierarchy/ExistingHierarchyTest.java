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

import javax.swing.JTextField;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.Condition;
import org.fest.swing.monitor.WindowMonitor;
import org.fest.swing.testing.TestFrame;

import static java.util.Collections.emptyList;
import static org.easymock.EasyMock.expect;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.core.Reflection.field;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.testing.TestGroups.GUI;

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
    assertThat(hierarchy.roots()).isEqualTo(rootWindows);
  }
  
  @Test public void shouldAlwaysContainGivenComponent() {
    assertThat(hierarchy.contains(new JTextField())).isTrue();
  }
  
  @Test public void shouldReturnParentOfComponent() {
    final TestFrame frame = new TestFrame(getClass());
    final JTextField textField = new JTextField();
    final ParentFinder finder = MockParentFinder.mock();
    field("parentFinder").ofType(ParentFinder.class).in(hierarchy).set(finder);
    new EasyMockTemplate(finder) {
      @Override protected void expectations() {
        expect(finder.parentOf(textField)).andReturn(frame);
      }

      @Override protected void codeToTest() {
        assertThat(hierarchy.parentOf(textField)).isSameAs(frame);
      }
    }.run();
    frame.destroy();
  }
  
  @Test public void shouldReturnSubcomponents() {
    final Component c = new JTextField();
    final ChildrenFinder finder = MockChildrenFinder.mock();
    field("childrenFinder").ofType(ChildrenFinder.class).in(hierarchy).set(finder);
    final Collection<Component> children = emptyList();
    new EasyMockTemplate(finder) {
      @Override protected void expectations() {
        expect(finder.childrenOf(c)).andReturn(children);
      }

      @Override protected void codeToTest() {
        assertThat(hierarchy.childrenOf(c)).isSameAs(children);
      }
    }.run();
  }
  
  @Test(groups = GUI) public void shouldDisposeWindow() {
    class CustomFrame extends TestFrame {
      private static final long serialVersionUID = 1L;

      boolean disposed;
      
      public CustomFrame(Class<?> testClass) {
        super(testClass);
      }

      @Override public void dispose() {
        disposed = true;
        super.dispose();
      }
    };
    
    final CustomFrame frame = new CustomFrame(getClass());
    frame.display();
    hierarchy.dispose(frame);
    pause(new Condition("Window is disposed") {
      @Override public boolean test() {
        return frame.disposed;
      }
    });
    assertThat(frame.disposed).isTrue();
  }
}
