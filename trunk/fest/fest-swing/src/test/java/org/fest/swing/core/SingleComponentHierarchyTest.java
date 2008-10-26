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
import java.awt.Container;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.hierarchy.ComponentHierarchy;
import org.fest.swing.testing.TestWindow;

import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link SingleComponentHierarchy}</code>.
 *
 * @author Alex Ruiz
 */
public class SingleComponentHierarchyTest {

  private ComponentHierarchy delegate;
  private TestWindow root;
  private SingleComponentHierarchy hierarchy;
  
  @BeforeMethod public void setUp() {
    delegate = createMock(ComponentHierarchy.class);
    root = TestWindow.createNewWindow(SingleComponentHierarchyTest.class);
    hierarchy = new SingleComponentHierarchy(root, delegate);
  }
  
  @Test public void shouldReturnRoot() {
    assertThat(hierarchy.root()).isSameAs(root);
  }
  
  @Test public void shouldReturnParentOfComponent() {
    final JFrame parent = new JFrame();
    final JButton child = new JButton();
    new EasyMockTemplate(delegate) {
      protected void expectations() {
        expect(delegate.parentOf(child)).andReturn(parent);
      }

      protected void codeToTest() {
        Container foundParent = hierarchy.parentOf(child);
        assertThat(foundParent).isSameAs(parent);
      }
    }.run();
  }
  
  @Test public void shouldReturnRoots() {
    assertThat(hierarchy.roots()).containsOnly(root);
  }
  
  @Test public void shouldReturnChildrenOfComponent() {
    final JFrame parent = new JFrame();
    final List<Component> children = new ArrayList<Component>();
    children.add(new JButton());
    new EasyMockTemplate(delegate) {
      protected void expectations() {
        expect(delegate.childrenOf(parent)).andReturn(children);
      }

      protected void codeToTest() {
        Collection<Component> foundChildren = hierarchy.childrenOf(parent);
        assertThat(foundChildren).isSameAs(children);
      }
    }.run();
  }
  
  @Test public void shouldReturnTrueIfDelegateContainsComponentAndComponentIsInRoot() {
    final JButton button = new JButton();
    root.add(button);
    new EasyMockTemplate(delegate) {
      protected void expectations() {
        expect(delegate.contains(button)).andReturn(true);
      }

      protected void codeToTest() {
        assertThat(hierarchy.contains(button)).isTrue();
      }
    }.run();
  }

  @Test public void shouldReturnFalseIfDelegateContainsComponentAndComponentIsNotInRoot() {
    final JButton button = new JButton();
    new EasyMockTemplate(delegate) {
      protected void expectations() {
        expect(delegate.contains(button)).andReturn(true);
      }

      protected void codeToTest() {
        assertThat(hierarchy.contains(button)).isFalse();
      }
    }.run();
  }

  @Test public void shouldReturnFalseIfDelegateDoesNotContainComponentAndComponentIsInRoot() {
    final JButton button = new JButton();
    root.add(button);
    new EasyMockTemplate(delegate) {
      protected void expectations() {
        expect(delegate.contains(button)).andReturn(false);
      }

      protected void codeToTest() {
        assertThat(hierarchy.contains(button)).isFalse();
      }
    }.run();
  }

  @Test public void shouldReturnFalseIfDelegateDoesNotContainComponentAndComponentIsNotInRoot() {
    final JButton button = new JButton();
    new EasyMockTemplate(delegate) {
      protected void expectations() {
        expect(delegate.contains(button)).andReturn(false);
      }

      protected void codeToTest() {
        assertThat(hierarchy.contains(button)).isFalse();
      }
    }.run();
  }

  @Test public void shouldDisposeWindow() {
    final JFrame window = new JFrame();
    new EasyMockTemplate(delegate) {
      protected void expectations() {
        delegate.dispose(window);
        expectLastCall().once();
      }

      protected void codeToTest() {
        hierarchy.dispose(window);
      }
    }.run();
  }
}
