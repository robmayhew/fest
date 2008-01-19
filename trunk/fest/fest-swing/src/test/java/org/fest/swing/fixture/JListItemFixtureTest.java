/*
 * Created on Sep 10, 2007
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
package org.fest.swing.fixture;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.assertions.AssertExtension;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.testing.ClickRecorder;
import org.fest.swing.testing.TestFrame;
import org.fest.util.Collections;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link JListItemFixture}</code>.
 *
 * @author Alex Ruiz
 */
public class JListItemFixtureTest {

  private static final int ITEM_INDEX = 1;

  private RobotFixture robot;
  private JListItemFixture item;
  private MainWindow window;
  
  @BeforeMethod public void setUp() {
    robot = RobotFixture.robotWithNewAwtHierarchy();
    window = new MainWindow(getClass());
    robot.showWindow(window);
    item = new JListItemFixture(listFixture(), ITEM_INDEX);
    assertThat(item()).isNotSelected();
  }

  private JListFixture listFixture() {
    return new JListFixture(robot, window.list);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  @Test public void shouldSelectCell() {
    item.select();
    assertThat(item()).isSelected();
  }

  @Test public void shouldClickCell() {
    ClickRecorder recorder = ClickRecorder.attachTo(window.list);
    item.click();
    assertThat(recorder).wasClicked();
    assertThat(item()).isAt(recorder.pointClicked());
  }
  
  @Test public void shouldDoubleClickCell() {
    ClickRecorder recorder = ClickRecorder.attachTo(window.list);
    item.doubleClick();
    assertThat(recorder).wasDoubleClicked();
    assertThat(item()).isAt(recorder.pointClicked());
  }
  
  @Test public void shouldRightClickCell() {
    ClickRecorder recorder = ClickRecorder.attachTo(window.list);
    item.rightClick();
    assertThat(recorder).wasRightClicked();
    assertThat(item()).isAt(recorder.pointClicked());
  }

  @Test public void shouldReturnCellContent() {
    assertThat(item.contents()).isEqualTo(window.list.itemValue(ITEM_INDEX));
  }
  
  @Test public void shouldShowPopupMenuFromCell() {
    window.list.addMouseListener(new MouseAdapter() {
      @Override public void mouseReleased(MouseEvent e) {
        assertThat(e.isPopupTrigger()).isTrue();
        assertThat(item()).isAt(e.getPoint());
      }      
    });
    item.showPopupMenu();
    assertThat(window.popupMenu.isVisible()).isTrue();
  }
  
  private ItemAssert item() {
    return new ItemAssert(window.list, item);
  }
  
  private static class ItemAssert implements AssertExtension {
    private final JList list;
    private final JListItemFixture item;

    ItemAssert(JList list, JListItemFixture item) {
      this.list = list;
      this.item = item;
    }

    ItemAssert isAt(Point p) {
      assertThat(item.index()).isEqualTo(list.locationToIndex(p));
      return this;
    }
    
    ItemAssert isNotSelected() {
      assertThat(selectedIndex()).isEqualTo(-1);
      return this;
    }

    ItemAssert isSelected() {
      assertThat(selectedIndex()).isEqualTo(item.index());
      return this;
    }

    private int selectedIndex() { return list.getSelectedIndex(); }
  }
  
  private static class MainWindow extends TestFrame {
    private static final long serialVersionUID = 1L;

    final TestList list = new TestList("list", Collections.list("first", "second", "third"));
    final JPopupMenu popupMenu = new JPopupMenu();
    
    MainWindow(Class<?> testClass) {
      super(testClass);
      addList(list);
      list.setComponentPopupMenu(popupMenu);
      popupMenu.add(new JMenuItem("First"));
    }
    
    private void addList(TestList list) {
      JScrollPane scrollPane = new JScrollPane(list);
      scrollPane.setPreferredSize(new Dimension(400, 200));
      add(scrollPane);
    }
  }
}
