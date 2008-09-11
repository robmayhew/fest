/*
 * Created on Feb 23, 2008
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

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JList;
import javax.swing.JScrollPane;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.*;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JComponentScrollRectToVisibleTask}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, EDT_ACTION })
public class JComponentScrollRectToVisibleTaskTest {

  private MyWindow window;

  @BeforeMethod public void setUp() {
    window = MyWindow.createNew();
    window.display();
  }
  
  @AfterMethod public void tearDown() {
    window.destroy();
  }

  public void shouldScrollRectToVisible() {
    Rectangle visibleRect = window.list.getVisibleRect();
    assertThat(visibleRect.y).isEqualTo(0);
    Rectangle boundsOfLastTwoListItems = window.list.getCellBounds(6, 7);
    JComponentScrollRectToVisibleTask.scrollRectToVisible(window.list, boundsOfLastTwoListItems);
    visibleRect = window.list.getVisibleRect();
    assertThat(visibleRect.y).isGreaterThan(0);
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }

    final JList list = new JList(array("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight"));
    
    private MyWindow() {
      super(JComponentScrollRectToVisibleTaskTest.class);
      JScrollPane scrollPane = new JScrollPane(list);
      scrollPane.setPreferredSize(new Dimension(60, 80));
      addComponents(scrollPane);
    }
  }
}
