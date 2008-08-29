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
import java.awt.Container;
import java.util.Collection;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GuiQuery;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.factory.JTextFields.textField;
import static org.fest.swing.testing.TestGroups.GUI;

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
    Container container = textField().createInEDT();
    assertThat(finder.nonExplicitChildrenOf(container)).isEmpty();
  }

  public void shouldReturnEmptyCollectionIfComponentIsNull() {
    assertThat(finder.nonExplicitChildrenOf(null)).isEmpty();
  }
  
  @Test(groups = GUI)
  public void shouldReturnPopupMenuIfComponentIsJMenu() {
    MyWindow window = MyWindow.showNew();
    Collection<Component> children = finder.nonExplicitChildrenOf(window.menu);
    try {
      assertThat(children).containsOnly(popupMenuOf(window.menu));
    } finally {
      window.destroy();
    }
  }
  
  private static JPopupMenu popupMenuOf(final JMenu menu) {
    return execute(new GuiQuery<JPopupMenu>() {
      protected JPopupMenu executeInEDT() {
        return menu.getPopupMenu();
      }
    }); 
  }
  
  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow showNew() {
      MyWindow window = execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
      window.display();
      return window;
    }
    
    final JMenu menu = new JMenu("Menu");
    
    MyWindow() {
      super(JMenuChildrenFinderTest.class);
      JMenuBar menuBar = new JMenuBar();
      menuBar.add(menu);
      setJMenuBar(menuBar);
    }
  }
}
