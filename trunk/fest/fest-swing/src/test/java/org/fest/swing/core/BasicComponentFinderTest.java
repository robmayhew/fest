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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.CheckThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.testing.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link BasicComponentFinder}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class BasicComponentFinderTest {

  private BasicComponentFinder finder;
  private MyWindow windowOne;
  private MyWindow windowTwo;

  @BeforeClass public void setUpOnce() {
    CheckThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    ScreenLock.instance().acquire(this);
    finder = (BasicComponentFinder)BasicComponentFinder.finderWithNewAwtHierarchy();
    windowOne = MyWindow.createAndShow();
  }

  @AfterMethod public void tearDown() {
    try {
      windowOne.destroy();
      if (windowTwo != null) windowTwo.destroy();
    } finally {
      ScreenLock.instance().release(this);
    }
  }

  public void shouldFindComponentByType() {
    JButton button = finder.findByType(JButton.class);
    assertThat(button).isSameAs(windowOne.button);
  }

  public void shouldThrowExceptionIfComponentNotFoundByType() {
    try {
      finder.findByType(JTree.class);
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e).message().contains("type=javax.swing.JTree").contains(format(windowOne));
    }
  }

  public void shouldFindComponentByTypeAndContainer() {
    windowTwo = MyWindow.createAndShow();
    JButton button = finder.findByType(windowTwo, JButton.class);
    assertThat(button).isSameAs(windowTwo.button);
  }

  public void shouldThrowExceptionIfComponentNotFoundByTypeAndContainer() {
    try {
      finder.findByType(windowOne, JList.class);
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e).message().contains("type=javax.swing.JList").contains(format(windowOne));
    }
  }

  public void shouldFindComponentByName() {
    Component button = finder.findByName("button");
    assertThat(button).isSameAs(windowOne.button);
  }

  public void shouldThrowExceptionIfComponentNotFoundByName() {
    try {
      finder.findByName("list");
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e).message().contains("name='list'").contains(format(windowOne));
    }
  }

  public void shouldFindComponentByNameAndContainer() {
    windowTwo = MyWindow.createAndShow();
    windowTwo.button.setName("anotherButton");
    Component button = finder.findByName(windowTwo, "anotherButton");
    assertThat(button).isSameAs(windowTwo.button);
  }

  public void shouldThrowExceptionIfComponentNotFoundByNameAndContainer() {
    try {
      finder.findByName(windowOne, "label");
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e).message().contains("name='label'").contains(format(windowOne));
    }
  }

  public void shouldFindComponentByNameAndType() {
    JButton button = finder.findByName("button", JButton.class);
    assertThat(button).isSameAs(windowOne.button);
  }

  public void shouldThrowExceptionIfComponentNotFoundByNameAndType() {
    try {
      finder.findByName("list", JLabel.class);
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e).message().contains("name='list'").contains("type=javax.swing.JLabel")
                             .contains(format(windowOne));
    }
  }

  public void shouldThrowExceptionIfComponentFoundByNameAndNotByType() {
    try {
      finder.findByName("button", JLabel.class);
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e).message().contains("name='button'").contains("type=javax.swing.JLabel")
                             .contains(format(windowOne));
    }
  }

  public void shouldFindComponentByNameAndTypeAndContainer() {
    windowTwo = MyWindow.createAndShow();
    JButton button = finder.findByName(windowTwo, "button", JButton.class);
    assertThat(button).isSameAs(windowTwo.button);
  }

  public void shouldThrowExceptionIfComponentNotFoundByNameAndTypeAndContainer() {
    try {
      finder.findByName(windowOne, "list", JLabel.class);
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e).message().contains("name='list'").contains("type=javax.swing.JLabel")
                             .contains(format(windowOne));
    }
  }

  public void shouldThrowExceptionIfComponentFoundByNameAndContainerAndNotByType() {
    try {
      finder.findByName(windowOne, "button", JLabel.class);
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e).message().contains("name='button'").contains("type=javax.swing.JLabel")
                             .contains(format(windowOne));
    }
  }

  public void shouldFindComponentUsingGenericTypeMatcher() {
    JButton foundButton = finder.find(new GenericTypeMatcher<JButton>() {
      protected boolean isMatching(JButton button) {
        return "A Button".equals(button.getText());
      }
    });
    assertThat(foundButton).isSameAs(windowOne.button);
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
    finder.find(windowOne, new GenericTypeMatcher<JLabel>() {
      @Override protected boolean isMatching(JLabel component) {
        return true;
      }
    });
  }

  public void shouldFindComponentByContainerUsingGenericTypeMatcher() {
    JButton foundButton = finder.find(windowOne, new GenericTypeMatcher<JButton>() {
      protected boolean isMatching(JButton button) {
        return "A Button".equals(button.getText());
      }
    });
    assertThat(foundButton).isSameAs(windowOne.button);
  }

  @Test(groups = GUI, expectedExceptions = ComponentLookupException.class)
  public void shouldThrowExceptionIfGenericMatcherNeverMatchesComponentInContainer() {
    finder.find(windowOne, new GenericTypeMatcher<JButton>() {
      @Override protected boolean isMatching(JButton component) {
        return false;
      }
    });
  }

  @Test(groups = GUI, expectedExceptions = ComponentLookupException.class)
  public void shouldThrowExceptionIfGenericMatcherMatchesWrongTypeInContainer() {
    finder.find(windowOne, new GenericTypeMatcher<JList>() {
      @Override protected boolean isMatching(JList component) {
        return true;
      }
    });
  }

  public void shouldThrowErrorIfMoreThanOneComponentMatch() {
    try {
      finder.find(new TypeMatcher(JTextField.class));
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e).message().contains("type=javax.swing.JTextField")
                             .contains(format(windowOne.textField))
                             .contains(format(windowOne.anotherTextField));
    }
  }

  public void shouldThrowExceptionWithoutComponentHierarchyAsConfigured() {
    finder.includeHierarchyIfComponentNotFound(false);
    try {
      finder.findByName(windowOne, "button", JLabel.class);
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e).message().contains("name='button'").contains("type=javax.swing.JLabel")
                             .excludes(format(windowOne));
    }
  }

  public void shouldReturnAllMatchingComponents() {
    Collection<Component> found = finder.findAll(new ComponentMatcher() {
      public boolean matches(Component c) {
        return c instanceof JTextField;
      }
    });
    assertThat(found).containsOnly(windowOne.textField, windowOne.anotherTextField);
  }

  public void shouldReturnAllMatchingComponentsInGivenRoot() {
    windowTwo = MyWindow.createAndShow();
    Collection<Component> found = finder.findAll(windowTwo, new ComponentMatcher() {
      public boolean matches(Component c) {
        return c instanceof JButton;
      }
    });
    assertThat(found).containsOnly(windowTwo.button);
  }

  protected static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JButton button = new JButton("A Button");
    final JTextField textField = new JTextField("A TextField");
    final JTextField anotherTextField = new JTextField("Another TextField");

    @RunsInEDT
    static MyWindow createAndShow() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          MyWindow window = new MyWindow();
          window.displayInCurrentThread();
          return window;
        }
      });
    }

    private void displayInCurrentThread() {
      TestWindow.display(this);
    }

    private MyWindow() {
      super(BasicComponentFinderTest.class);
      addComponents(button, textField, anotherTextField);
      button.setName("button");
    }
  }
}
