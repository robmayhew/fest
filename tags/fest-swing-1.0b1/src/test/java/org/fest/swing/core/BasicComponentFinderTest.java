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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.core;

import java.awt.Component;
import java.util.Collection;

import javax.swing.*;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link BasicComponentFinder}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class BasicComponentFinderTest {

  protected static class MainWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JButton button = new JButton("A Button");
    final JTextField textField = new JTextField("A TextField");
    final JTextField anotherTextField = new JTextField("Another TextField");

    static MainWindow beVisible() {
      MainWindow window = new MainWindow();
      window.display();
      return window;
    }
    
    MainWindow() {
      super(BasicComponentFinderTest.class);
      add(button);
      add(textField);
      add(anotherTextField);
      button.setName("button");
    }
  }

  private ComponentFinder finder;
  private MainWindow window;
  private MainWindow anotherWindow;
  
  @BeforeMethod public void setUp() {
    finder = BasicComponentFinder.finderWithNewAwtHierarchy();
    window = MainWindow.beVisible();
  }
  
  @AfterMethod public void tearDown() {
    window.destroy();
    if (anotherWindow != null) anotherWindow.destroy();
  }
  
  public void shouldFindComponentByType() {
    JButton button = finder.findByType(JButton.class);
    assertThat(button).isSameAs(window.button);
  }
  
  public void shouldThrowExceptionIfComponentNotFoundByType() {
    try {
      finder.findByType(JTree.class);
      fail();
    } catch (ComponentLookupException e) {
      assertThat(e).message().contains("type=javax.swing.JTree").contains(format(window));
    }
  }

  public void shouldFindComponentByTypeAndContainer() {
    anotherWindow = MainWindow.beVisible();
    JButton button = finder.findByType(anotherWindow, JButton.class);
    assertThat(button).isSameAs(anotherWindow.button);
  }
  
  public void shouldThrowExceptionIfComponentNotFoundByTypeAndContainer() {
    try {
      finder.findByType(window, JList.class);
      fail();
    } catch (ComponentLookupException e) { 
      assertThat(e).message().contains("type=javax.swing.JList").contains(format(window));
    }
  }
  
  public void shouldFindComponentByName() {
    Component button = finder.findByName("button");
    assertThat(button).isSameAs(window.button);
  }
  
  public void shouldThrowExceptionIfComponentNotFoundByName() {
    try {
      finder.findByName("list");
      fail();
    } catch (ComponentLookupException e) {
      assertThat(e).message().contains("name='list'").contains(format(window));
    }
  }
  
  public void shouldFindComponentByNameAndContainer() {
    anotherWindow = MainWindow.beVisible();
    anotherWindow.button.setName("anotherButton");
    Component button = finder.findByName(anotherWindow, "anotherButton");
    assertThat(button).isSameAs(anotherWindow.button);
  }
  
  public void shouldThrowExceptionIfComponentNotFoundByNameAndContainer() {
    try {
      finder.findByName(window, "label");
      fail();
    } catch (ComponentLookupException e) {
      assertThat(e).message().contains("name='label'").contains(format(window));
    }
  }
  
  public void shouldFindComponentByNameAndType() {
    JButton button = finder.findByName("button", JButton.class);
    assertThat(button).isSameAs(window.button);
  }
  
  public void shouldThrowExceptionIfComponentNotFoundByNameAndType() {
    try {
      finder.findByName("list", JLabel.class);
      fail();
    } catch (ComponentLookupException e) { 
      assertThat(e).message().contains("name='list'").contains("type=javax.swing.JLabel")
                             .contains(format(window));
    }
  }
  
  public void shouldThrowExceptionIfComponentFoundByNameAndNotByType() {
    try {
      finder.findByName("button", JLabel.class);
      fail();
    } catch (ComponentLookupException e) { 
      assertThat(e).message().contains("name='button'").contains("type=javax.swing.JLabel")
                             .contains(format(window));
    }
  }
  
  public void shouldFindComponentByNameAndTypeAndContainer() {
    anotherWindow = MainWindow.beVisible();
    JButton button = finder.findByName(anotherWindow, "button", JButton.class);
    assertThat(button).isSameAs(anotherWindow.button);
  }
  
  public void shouldThrowExceptionIfComponentNotFoundByNameAndTypeAndContainer() {
    try {
      finder.findByName(window, "list", JLabel.class);
      fail();
    } catch (ComponentLookupException e) { 
      assertThat(e).message().contains("name='list'").contains("type=javax.swing.JLabel")
                             .contains(format(window));
    }
  }
  
  public void shouldThrowExceptionIfComponentFoundByNameAndContainerAndNotByType() {
    try {
      finder.findByName(window, "button", JLabel.class);
      fail();
    } catch (ComponentLookupException e) { 
      assertThat(e).message().contains("name='button'").contains("type=javax.swing.JLabel")
                             .contains(format(window));
    }
  }
  
  public void shouldFindComponentUsingGenericTypeMatcher() {
    JButton foundButton = finder.find(new GenericTypeMatcher<JButton>() {
      protected boolean isMatching(JButton button) {
        return "A Button".equals(button.getText());
      }
    });
    assertThat(foundButton).isSameAs(window.button);
  }

  @Test(groups = GUI, expectedExceptions = ComponentLookupException.class) 
  public void shouldThrowExceptionIfGenericMatcherNeverMatchesComponent() {
    finder.find(new GenericTypeMatcher<JButton>() {
      @Override protected boolean isMatching(JButton component) {
        return false;
      }
    });
  }
  
  @Test(groups = GUI, expectedExceptions = ComponentLookupException.class) 
  public void shouldThrowExceptionIfGenericMatcherMatchesWrongType() {
    finder.find(window, new GenericTypeMatcher<JLabel>() {
      @Override protected boolean isMatching(JLabel component) {
        return true;
      }
    });
  }
  
  public void shouldFindComponentByContainerUsingGenericTypeMatcher() {
    JButton foundButton = finder.find(window, new GenericTypeMatcher<JButton>() {
      protected boolean isMatching(JButton button) {
        return "A Button".equals(button.getText());
      }
    });
    assertThat(foundButton).isSameAs(window.button);
  }

  @Test(groups = GUI, expectedExceptions = ComponentLookupException.class) 
  public void shouldThrowExceptionIfGenericMatcherNeverMatchesComponentInContainer() {
    finder.find(window, new GenericTypeMatcher<JButton>() {
      @Override protected boolean isMatching(JButton component) {
        return false;
      }
    });
  }
  
  @Test(groups = GUI, expectedExceptions = ComponentLookupException.class) 
  public void shouldThrowExceptionIfGenericMatcherMatchesWrongTypeInContainer() {
    finder.find(window, new GenericTypeMatcher<JList>() {
      @Override protected boolean isMatching(JList component) {
        return true;
      }
    });
  }
  
  public void shouldThrowErrorIfMoreThanOneComponentMatch() {
    try {
      finder.find(new TypeMatcher(JTextField.class));
      fail();
    } catch (ComponentLookupException e) { 
      assertThat(e).message().contains("type=javax.swing.JTextField")
                             .contains(format(window.textField))
                             .contains(format(window.anotherTextField));
    }    
  }

  public void shouldThrowExceptionWithoutComponentHierarchyAsConfigured() {
    finder.includeHierarchyIfComponentNotFound(false);
    try {
      finder.findByName(window, "button", JLabel.class);
      fail();
    } catch (ComponentLookupException e) { 
      assertThat(e).message().contains("name='button'").contains("type=javax.swing.JLabel")
                             .excludes(format(window));
    }
  }
  
  public void shouldReturnAllMatchingComponents() {
    Collection<Component> found = finder.findAll(new ComponentMatcher() {
      public boolean matches(Component c) {
        return c instanceof JTextField;
      }
    });
    assertThat(found).containsOnly(window.textField, window.anotherTextField);
  }
  
  public void shouldReturnAllMatchingComponentsInGivenRoot() {
    anotherWindow = MainWindow.beVisible();
    Collection<Component> found = finder.findAll(anotherWindow, new ComponentMatcher() {
      public boolean matches(Component c) {
        return c instanceof JButton;
      }
    });
    assertThat(found).containsOnly(anotherWindow.button);
  }
}
