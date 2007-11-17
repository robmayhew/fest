/*
 * Created on Aug 7, 2007
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
package org.fest.swing.core;

import java.awt.Component;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTree;
import javax.swing.UIManager;

import static org.fest.assertions.Assertions.assertThat;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.core.ComponentFinder;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.testing.TestFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link org.fest.swing.core.ComponentFinder}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentFinderTest {

  protected static class MainWindow extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JButton button = new JButton("A Button");

    MainWindow(Class testClass) {
      super(testClass);
      add(button);
      button.setName("button");
      lookNative();
    }
    
    private void lookNative() {
      try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception ignored) {}
    }
  }

  private ComponentFinder finder;
  private MainWindow window;
  private MainWindow anotherWindow;
  
  @BeforeMethod public void setUp() {
    finder = ComponentFinder.finderWithNewAwtHierarchy();
    window = showWindow();
  }
  
  @Test public void shouldFindComponentByType() {
    JButton button = finder.findByType(JButton.class);
    assertThat(button).isSameAs(window.button);
  }
  
  @Test(expectedExceptions = ComponentLookupException.class) 
  public void shouldThrowExceptionIfComponentNotFoundByType() {
    finder.findByType(JTree.class);
  }

  @Test public void shouldFindComponentByTypeAndContainer() {
    anotherWindow = showWindow();
    JButton button = finder.findByType(anotherWindow, JButton.class);
    assertThat(button).isSameAs(anotherWindow.button);
  }
  
  @Test(expectedExceptions = ComponentLookupException.class) 
  public void shouldThrowExceptionIfComponentNotFoundByTypeAndContainer() {
    finder.findByType(window, JList.class);
  }
  
  @Test public void shouldFindComponentByName() {
    Component button = finder.findByName("button");
    assertThat(button).isSameAs(window.button);
  }
  
  @Test(expectedExceptions = ComponentLookupException.class) 
  public void shouldThrowExceptionIfComponentNotFoundByName() {
    finder.findByName("list");
  }
  
  @Test public void shouldFindComponentByNameAndContainer() {
    anotherWindow = showWindow();
    anotherWindow.button.setName("anotherButton");
    Component button = finder.findByName(anotherWindow, "anotherButton");
    assertThat(button).isSameAs(anotherWindow.button);
  }
  
  @Test(expectedExceptions = ComponentLookupException.class) 
  public void shouldThrowExceptionIfComponentNotFoundByNameAndContainer() {
    finder.findByName(window, "label");
  }
  
  @Test public void shouldFindComponentByNameAndType() {
    JButton button = finder.findByName("button", JButton.class);
    assertThat(button).isSameAs(window.button);
  }
  
  @Test(expectedExceptions = ComponentLookupException.class) 
  public void shouldThrowExceptionIfComponentNotFoundByNameAndType() {
    finder.findByName("list", JLabel.class);
  }
  
  @Test(expectedExceptions = ComponentLookupException.class) 
  public void shouldThrowExceptionIfComponentFoundByNameAndNotByType() {
    finder.findByName("button", JLabel.class);
  }
  
  @Test public void shouldFindComponentByNameAndTypeAndContainer() {
    anotherWindow = showWindow();
    JButton button = finder.findByName(anotherWindow, "button", JButton.class);
    assertThat(button).isSameAs(anotherWindow.button);
  }
  
  @Test(expectedExceptions = ComponentLookupException.class) 
  public void shouldThrowExceptionIfComponentNotFoundByNameAndTypeAndContainer() {
    finder.findByName(window, "list", JLabel.class);
  }
  
  @Test(expectedExceptions = ComponentLookupException.class)
  public void shouldThrowExceptionIfComponentFoundByNameAndContainerAndNotByType() {
    finder.findByName(window, "button", JLabel.class);
  }

  
  @Test public void shouldFindComponentUsingGenericTypeMatcher() {
    JButton button = finder.find(new GenericTypeMatcher<JButton>() {
      protected boolean isMatching(JButton button) {
        return "A Button".equals(button.getText());
      }
    });
    assertThat(button).isSameAs(window.button);
  }

  @Test(expectedExceptions = ComponentLookupException.class) 
  public void shouldThrowExceptionIfGenericMatcherNeverMatchesComponent() {
    finder.find(new GenericTypeMatcher<JButton>() {
      @Override protected boolean isMatching(JButton component) {
        return false;
      }
    });
  }
  
  @Test(expectedExceptions = ComponentLookupException.class) 
  public void shouldThrowExceptionIfGenericMatcherMatchesWrongType() {
    finder.find(window, new GenericTypeMatcher<JLabel>() {
      @Override protected boolean isMatching(JLabel component) {
        return true;
      }
    });
  }
  
  @Test public void shouldFindComponentByContainerUsingGenericTypeMatcher() {
    JButton button = finder.find(window, new GenericTypeMatcher<JButton>() {
      protected boolean isMatching(JButton button) {
        return "A Button".equals(button.getText());
      }
    });
    assertThat(button).isSameAs(window.button);
  }

  @Test(expectedExceptions = ComponentLookupException.class) 
  public void shouldThrowExceptionIfGenericMatcherNeverMatchesComponentInContainer() {
    finder.find(window, new GenericTypeMatcher<JButton>() {
      @Override protected boolean isMatching(JButton component) {
        return false;
      }
    });
  }
  
  @Test(expectedExceptions = ComponentLookupException.class) 
  public void shouldThrowExceptionIfGenericMatcherMatchesWrongTypeInContainer() {
    finder.find(window, new GenericTypeMatcher<JList>() {
      @Override protected boolean isMatching(JList component) {
        return true;
      }
    });
  }
  
  @AfterMethod public void tearDown() {
    dispose(window);
    dispose(anotherWindow);
  }

  private MainWindow showWindow() {
    MainWindow w = new MainWindow(getClass());
    w.pack();
    w.setVisible(true);
    return w;
  }
  
  private void dispose(Window w) {
    if (w == null) return;
    w.setVisible(false);
    w.dispose();
  }
}
