/*
 * Created on Dec 22, 2007
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
import java.util.logging.Logger;

import javax.swing.JButton;

import abbot.finder.AWTHierarchy;
import abbot.finder.Hierarchy;
import static java.util.logging.Level.INFO;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.testing.TestFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link BasicComponentFinder}</code>.
 *
 * @author Alex Ruiz
 */
public class BasicComponentFinderTest {

  private static Logger logger = Logger.getAnonymousLogger();
  
  private BasicComponentFinder finder;
  private MainWindow window;
  
  @BeforeMethod public void setUp() {
    Hierarchy hierarchy = new AWTHierarchy();
    finder = new BasicComponentFinder(new ComponentPrinter(hierarchy));
    window = MainWindow.show(getClass());
  }
  
  @AfterMethod public void tearDown() {
    window.destroy();
  }
  
  @Test public void shouldFindComponentUsingMatcher() {
    Component found = finder.find(new NameMatcher("firstButton", true));
    assertThat(found).isSameAs(window.firstButton);
  }

  @Test public void shouldFindComponentUsingMatcherStartingFromGivenRoot() {
    MainWindow anotherWindow = MainWindow.show(getClass());
    Component found = finder.find(anotherWindow, new NameMatcher("firstButton"));
    assertThat(found).isSameAs(anotherWindow.firstButton);
    anotherWindow.destroy();
  }

  @Test public void shouldThrowErrorIfComponentNotFound() {
    try {
      finder.find(new NameMatcher("thirdButton"));
      fail();
    } catch (ComponentLookupException e) { log(e); }
  }

  @Test public void shouldThrowErrorIfMoreThanOneComponentMatch() {
    try {
      finder.find(new TypeMatcher(JButton.class));
      fail();
    } catch (ComponentLookupException e) { log(e); }    
  }
  
  private void log(ComponentLookupException e) {
    logger.log(INFO, e.getMessage(), e);
  }

  protected static class MainWindow extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JButton firstButton = new JButton("First Button");
    final JButton secondButton = new JButton("Second Button");
    
    static MainWindow show(Class<?> testClass) {
      MainWindow window = new MainWindow(testClass);
      window.display();
      return window;
    }
    
    MainWindow(Class<?> testClass) {
      super(testClass);
      addComponents(firstButton, secondButton);
      firstButton.setName("firstButton");
      secondButton.setName("secondButton");
    }
  }
}
