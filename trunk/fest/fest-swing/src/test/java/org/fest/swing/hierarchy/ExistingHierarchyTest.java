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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.hierarchy;

import java.awt.Component;
import java.awt.Window;
import java.util.Collection;

import javax.swing.JTextField;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.GuiQuery;
import org.fest.swing.monitor.WindowMonitor;
import org.fest.swing.testing.TestWindow;

import static java.util.Collections.emptyList;
import static org.easymock.EasyMock.expect;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.factory.JTextFields.textField;
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
    Component component = textField().createInEDT();
    assertThat(hierarchy.contains(component)).isTrue();
  }

  @Test public void shouldReturnParentOfComponent() {
    final TestWindow frame = newTestWindow();
    final JTextField textField = textField().createInEDT();
    final ParentFinder parentFinder = MockParentFinder.mock();
    hierarchy = new ExistingHierarchy(parentFinder, new ChildrenFinder());
    new EasyMockTemplate(parentFinder) {
      @Override protected void expectations() {
        expect(parentFinder.parentOf(textField)).andReturn(frame);
      }

      @Override protected void codeToTest() {
        assertThat(hierarchy.parentOf(textField)).isSameAs(frame);
      }
    }.run();
    frame.destroy();
  }

  private static TestWindow newTestWindow() {
    return execute(new GuiQuery<TestWindow>() {
      protected TestWindow executeInEDT() {
        return new TestWindow(ExistingHierarchyTest.class);
      }
    });
  }
  
  @Test public void shouldReturnSubcomponents() {
    final Component c = textField().createInEDT();
    final ChildrenFinder childrenFinder = MockChildrenFinder.mock();
    hierarchy = new ExistingHierarchy(new ParentFinder(), childrenFinder);
    final Collection<Component> children = emptyList();
    new EasyMockTemplate(childrenFinder) {
      @Override protected void expectations() {
        expect(childrenFinder.childrenOf(c)).andReturn(children);
      }

      @Override protected void codeToTest() {
        assertThat(hierarchy.childrenOf(c)).isSameAs(children);
      }
    }.run();
  }
  
  @Test(groups = GUI) public void shouldDisposeWindow() {
    final MyWindow window = MyWindow.createInEDT();
    window.display();
    hierarchy.dispose(window);
    assertThat(disposed(window)).isTrue();
  }

  static boolean disposed(final MyWindow w) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return w.disposed;
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    boolean disposed;
    
    static MyWindow createInEDT() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() { return new MyWindow(); }
      });
    }
    
    public MyWindow() {
      super(ExistingHierarchyTest.class);
    }

    @Override public void dispose() {
      disposed = true;
      super.dispose();
    }    
  };
}
