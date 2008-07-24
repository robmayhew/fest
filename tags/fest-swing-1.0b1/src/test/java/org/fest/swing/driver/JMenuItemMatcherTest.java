/*
 * Created on Apr 17, 2008
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
package org.fest.swing.driver;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.ComponentFinder;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicComponentFinder.finderWithNewAwtHierarchy;

/**
 * Tests for <code>{@link JMenuItemMatcher}</code>.
 *
 * @author Alex Ruiz
 */
public class JMenuItemMatcherTest {

  private ComponentFinder finder;
  private MyFrame frame;
  private JMenuItemMatcher matcher;
  
  @BeforeMethod public void setUp() {
    finder = finderWithNewAwtHierarchy();
    frame = new MyFrame();
    frame.display();
    matcher = new JMenuItemMatcher("Logout", "Logout");
  }
  
  @AfterMethod public void tearDown() {
    frame.destroy();
  }
  
  @Test public void shouldFindSecondLogoutMenu() {
    Component found = finder.find(frame, matcher);
    assertThat(found).isSameAs(frame.logoutMenuItem);
  }
  
  @Test public void shouldShowPathInToString() {
    assertThat(matcher.toString()).contains("label='Logout|Logout'");
  }
  
  private static class MyFrame extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JMenuItem logoutMenuItem = new JMenuItem("Logout"); 
    
    public MyFrame() {
      super(JMenuItemMatcherTest.class);
      JMenuBar menuBar = new JMenuBar();
      JMenu logoutMenu = new JMenu("Logout");
      logoutMenu.add(logoutMenuItem);
      logoutMenu.add(new JMenuItem("Exit"));
      menuBar.add(logoutMenu);
      setJMenuBar(menuBar);
      setPreferredSize(new Dimension(200, 200));
    }
  }
}
