/*
 * Created on Nov 20, 2007
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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.testing.TestFrame;

import static org.easymock.EasyMock.*;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link SingleComponentHierarchy}</code>.
 *
 * @author Alex Ruiz
 */
public class SingleComponentHierarchyTest {

  private SingleComponentHierarchy hierarchy;
  private ComponentHierarchy delegate;
  private CustomFrame root;

  @BeforeMethod public void setUp() {
    root = new CustomFrame(getClass());
    delegate = createMock(ComponentHierarchy.class);
    hierarchy = new SingleComponentHierarchy(root, delegate);
  }

  @AfterMethod public void tearDown() {
    root.destroy();
  }

  @Test public void shouldReturnOnlyOneRoot() {
    assertThat(hierarchy.roots()).containsOnly(root);
  }

  @Test public void shouldReturnChildrenUsingDelegate() {
    final List<Component> children = new ArrayList<Component>();
    new EasyMockTemplate(delegate) {
      protected void expectations() {
        expect(delegate.childrenOf(root)).andReturn(children);    
      }

      protected void codeToTest() {
        assertThat(hierarchy.childrenOf(root)).isSameAs(children);
      }
    }.run();
  }

  @Test public void shouldReturnParentUsingDelegate() {
    new EasyMockTemplate(delegate) {
      protected void expectations() {
        expect(delegate.parentOf(root.textField)).andReturn(root);
      }

      protected void codeToTest() {
        assertThat(hierarchy.parentOf(root.textField)).isSameAs(root);
      }
    }.run();
  }

  @Test public void shouldReturnTrueIfDelegateContainsComponentAndDescendsFromRoot() {
    new EasyMockTemplate(delegate) {
      protected void expectations() {
        expect(delegate.contains(root.textField)).andReturn(true);
      }

      protected void codeToTest() {
        assertThat(hierarchy.contains(root.textField)).isTrue();
      }
    }.run();
  }

  @Test public void shouldReturnFalseIfDelegateContainsComponentAndDoesNotDescendFromRoot() {
    final JComboBox comboBox = new JComboBox();
    new EasyMockTemplate(delegate) {
      protected void expectations() {
        expect(delegate.contains(comboBox)).andReturn(true);
      }

      protected void codeToTest() {
        assertThat(hierarchy.contains(comboBox)).isFalse();
      }
    }.run();
  }

  @Test public void shouldReturnFalseIfDelegateDoesNotContainComponentDescendsFromRoot() {
    new EasyMockTemplate(delegate) {
      protected void expectations() {
        expect(delegate.contains(root.textField)).andReturn(false);
      }

      protected void codeToTest() {
        assertThat(hierarchy.contains(root.textField)).isFalse();
      }
    }.run();
  }

  @Test public void shouldReturnFalseIfDelegateDoesNotContainComponentAndDoesNotDescendFromRoot() {
    final JComboBox comboBox = new JComboBox();
    new EasyMockTemplate(delegate) {
      protected void expectations() {
        expect(delegate.contains(comboBox)).andReturn(false);
      }

      protected void codeToTest() {
        assertThat(hierarchy.contains(comboBox)).isFalse();
      }
    }.run();
  }

  @Test public void shouldDisposeComponentUsingDelegate() {
    new EasyMockTemplate(delegate) {
      protected void expectations() {
        delegate.dispose(root);
        expectLastCall();
      }

      protected void codeToTest() {
        hierarchy.dispose(root);
      }
    }.run();
  }

  private static class CustomFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JTextField textField = new JTextField();

    public CustomFrame(Class testClass) {
      super(testClass);
      add(textField);
    }
  }
}
